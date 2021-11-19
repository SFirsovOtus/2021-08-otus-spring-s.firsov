package ru.otus.spring.quiz.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.config.LocaleConfig;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.exception.QuestionsReadingException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionDaoImpl implements QuestionDao {

    private final String csvPath;
    private final String csvPathDefault;
    private final String csvDelimiter;

    public QuestionDaoImpl(@Value("${resources.csv-data-storage.path}") String csvPath,
                           @Value("${resources.csv-data-storage.delimiter}") String csvDelimiter,
                           LocaleConfig localeConfig) {
        this.csvPath = String.format(csvPath, localeConfig.getLocaleSuffix());
        this.csvPathDefault = String.format(csvPath, StringUtils.EMPTY);
        this.csvDelimiter = csvDelimiter;
    }


    private String readAllCsvText(String csvPath, String csvPathDefault) throws IOException {
        try (InputStream csvStream = new ClassPathResource(csvPath)
                .getInputStream()) {
            return IOUtils.toString(csvStream, StandardCharsets.UTF_8);
        } catch (IOException exception) {

            try (InputStream csvStreamDefault = new ClassPathResource(csvPathDefault)
                    .getInputStream()) {
                return IOUtils.toString(csvStreamDefault, StandardCharsets.UTF_8);
            }

        }
    }

    private List<CSVRecord> convertCsvTextToRecords(String csvText) throws IOException {
        CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setDelimiter(csvDelimiter)
                .build();

        return CSVParser.parse(csvText, csvFormat)
                .getRecords();
    }

    private Question mapCsvRecordToQuestion(CSVRecord csvRecord) {
        String questionFormulation = csvRecord.get(0);

        List<Answer.Variant> answerVariants = new ArrayList<>();

        if (csvRecord.size() > 1) {
            String answerVariantFormulation = null;
            int counter = 0;

            for (int i = 1; i < csvRecord.size(); i++) {
                counter++;
                if (counter == 1) {
                    answerVariantFormulation = csvRecord.get(i);
                } else {
                    boolean answerVariantIsRight = csvRecord.get(i).equalsIgnoreCase("Y");
                    answerVariants.add(new Answer.Variant(answerVariantFormulation, answerVariantIsRight));
                    counter = 0;
                }
            }
        }

        Answer answer = new Answer(answerVariants);
        return new Question(questionFormulation, answer);
    }


    @Override
    public List<Question> readAll() throws QuestionsReadingException {
        List<CSVRecord> csvRecords;

        try {
            String csvText = readAllCsvText(this.csvPath, this.csvPathDefault);
            csvRecords = convertCsvTextToRecords(csvText);
        } catch (IOException exception) {
            throw new QuestionsReadingException(this.csvPath, this.csvPathDefault, exception);
        }

        List<Question> questions = new ArrayList<>();
        csvRecords.forEach(csvRecord -> questions.add(mapCsvRecordToQuestion(csvRecord)));

        return questions;
    }

}

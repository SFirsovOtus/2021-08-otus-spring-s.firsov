package ru.otus.spring.quiz.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    private final String csvPath;

    public QuestionDaoImpl(String csvPath) {
        this.csvPath = csvPath;
    }


    private String readAllCsvText(String csvPath) throws IOException {
        InputStream csvStream = new ClassPathResource(csvPath)
                .getInputStream();

        return IOUtils.toString(csvStream, StandardCharsets.UTF_8);
    }

    private List<CSVRecord> convertCsvTextToRecords(String csvText) throws IOException {
        CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setDelimiter(';')
                .build();

        return CSVParser.parse(csvText, csvFormat)
                .getRecords();
    }

    private Question mapCsvRecordToQuestion(CSVRecord csvRecord) {
        String questionFormulation = csvRecord.get(0);

        List<Answer.Variant> answerVariants = new ArrayList<>();

        if (csvRecord.size() > 1) {
            String answerVariantFormulation = null;
            Boolean answerVariantIsRight = null;
            int counter = 0;

            for (int i = 1; i < csvRecord.size(); i++) {
                counter++;
                if (counter == 1) {
                    answerVariantFormulation = csvRecord.get(i);
                } else {
                    answerVariantIsRight = csvRecord.get(i).equalsIgnoreCase("Y") ? Boolean.TRUE : Boolean.FALSE;
                    answerVariants.add(new Answer.Variant(answerVariantFormulation, answerVariantIsRight));

                    answerVariantFormulation = null;
                    answerVariantIsRight = null;
                    counter = 0;
                }
            }
        }

        Answer answer = new Answer(answerVariants);
        return new Question(questionFormulation, answer);
    }


    public List<Question> readAll() {
        List<CSVRecord> csvRecords = null;

        try {
            String csvText = readAllCsvText(this.csvPath);
            csvRecords = convertCsvTextToRecords(csvText);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(255);
        }

        List<Question> questions = new ArrayList<>();
        csvRecords.forEach(csvRecord -> questions.add(mapCsvRecordToQuestion(csvRecord)));

        return questions;
    }

}

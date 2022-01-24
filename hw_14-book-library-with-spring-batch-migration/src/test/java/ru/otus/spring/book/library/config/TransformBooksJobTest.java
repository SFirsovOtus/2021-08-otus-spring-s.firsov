package ru.otus.spring.book.library.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.book.library.domain.BookForMongo;
import ru.otus.spring.book.library.service.CleanUpService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static ru.otus.spring.book.library.config.JobConfig.*;

@SpringBatchTest
@SpringBootTest
class TransformBooksJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    private CleanUpService cleanUpService;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void transformBooksJobShouldMigrateExpectedData() throws Exception {
        List<BookForMongo> expectedBooks = List.of(
                new BookForMongo("Компьютерные сети", "Эндрю Таненбаум", List.of("Компьютерная литература")),
                new BookForMongo("Философские тетради", "В. И. Ленин", List.of("Биографии и мемуары", "Философия")),
                new BookForMongo("Человек-невидимка", "Герберт Уэллс", List.of("Литература ужасов", "Роман", "Фантастика"))
        );

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(TRANSFORM_BOOKS_JOB_NAME);


        doNothing().when(cleanUpService).cleanUp();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<BookForMongo> actualBooks = mongoTemplate.findAll(BookForMongo.class)
                .stream()
                .sorted(Comparator.comparing(BookForMongo::getName))
                .collect(Collectors.toList());

        assertThat(actualBooks.size()).isEqualTo(expectedBooks.size());
        for (int i = 0; i < actualBooks.size(); i++) {
            assertThat(actualBooks.get(i).getName()).isEqualTo(expectedBooks.get(i).getName());
            assertThat(actualBooks.get(i).getAuthor()).isEqualTo(expectedBooks.get(i).getAuthor());

            assertThat(actualBooks.get(i).getGenres().size()).isEqualTo(expectedBooks.get(i).getGenres().size());
            for (int j = 0; j < actualBooks.get(i).getGenres().size(); j++) {
                assertThat(actualBooks.get(i).getGenres().get(j)).isEqualTo(expectedBooks.get(i).getGenres().get(j));
            }
        }
    }

}

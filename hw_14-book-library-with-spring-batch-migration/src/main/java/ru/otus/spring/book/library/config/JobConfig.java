package ru.otus.spring.book.library.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.BookForMongo;
import ru.otus.spring.book.library.service.CleanUpService;
import ru.otus.spring.book.library.service.EntityConverterService;

import javax.persistence.EntityManagerFactory;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final CleanUpService cleanUpService;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final Logger logger;

    public static final String BOOK_WRITER_COLLECTION_NAME = "books";
    public static final String TRANSFORM_BOOKS_JOB_NAME = "transformBooksJob";

    private static final String PRE_CLEAN_UP_STEP_NAME = "dropCollection";
    private static final String CLEAN_UP_METHOD_NAME = "cleanUp";
    private static final String BOOK_READER_NAME = "bookReader";
    private static final String TRANSFORM_BOOKS_STEP_NAME = "transformBooksStep";
    private static final int CHUNK_SIZE = 4;


    @Bean
    public MethodInvokingTaskletAdapter cleanUpTasklet() {
        MethodInvokingTaskletAdapter methodInvokingTaskletAdapter = new MethodInvokingTaskletAdapter();

        methodInvokingTaskletAdapter.setTargetObject(cleanUpService);
        methodInvokingTaskletAdapter.setTargetMethod(CLEAN_UP_METHOD_NAME);

        return methodInvokingTaskletAdapter;
    }

    @Bean
    public Step preCleanUpStep(MethodInvokingTaskletAdapter cleanUpTasklet) {
        return this.stepBuilderFactory.get(PRE_CLEAN_UP_STEP_NAME)
                .tasklet(cleanUpTasklet)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Book> bookReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Book>()
                .name(BOOK_READER_NAME)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select b from Book b join fetch b.author")
                .build();
    }

    @Bean
    public ItemProcessor<Book, BookForMongo> bookConverter(EntityConverterService entityConverterService) {
        return entityConverterService::toBookForMongo;
    }

    @Bean
    public MongoItemWriter<BookForMongo> bookWriter(MongoOperations mongoOperations) {
        return new MongoItemWriterBuilder<BookForMongo>()
                .template(mongoOperations)
                .collection(BOOK_WRITER_COLLECTION_NAME)
                .build();
    }

    @Bean
    public Step transformBooksStep(ItemReader<Book> bookReader, ItemProcessor<Book, BookForMongo> bookConverter, ItemWriter<BookForMongo> bookWriter) {
        return stepBuilderFactory.get(TRANSFORM_BOOKS_STEP_NAME)
                .<Book, BookForMongo>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookConverter)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Job transformBooksJob(Step preCleanUpStep, Step transformBooksStep) {
        return jobBuilderFactory.get(TRANSFORM_BOOKS_JOB_NAME)
                .start(preCleanUpStep)
                .next(transformBooksStep)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Миграция книг начата...");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Миграция книг закончена.");
                    }
                })
                .build();
    }

}

package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobLauncher jobLauncher;
    private final Job transformBooksJob;
    private final Logger logger;


    @Override
    public void runMigration() {
        try {
            JobExecution jobExecution = jobLauncher.run(transformBooksJob, new JobParameters());
        } catch (Exception exception) {
            logger.error(String.format("Error while execution of job %s%s%s",
                    transformBooksJob.getName(), System.lineSeparator(), ExceptionUtils.getStackTrace(exception)));
        }
    }

}

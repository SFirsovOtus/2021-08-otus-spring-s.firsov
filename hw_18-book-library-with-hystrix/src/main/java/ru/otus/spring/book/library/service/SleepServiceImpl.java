package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SleepServiceImpl implements SleepService {

    private final Logger logger;


    @Override
    public void sleepRandomly(int seconds) {
        int secondsRandomly = RandomUtils.nextInt(0, seconds + 1);
        try {
            logger.info("Sleep {} second(s)", secondsRandomly);
            Thread.sleep(secondsRandomly * 1000L);
        } catch (InterruptedException exception) {
            //exception.printStackTrace();
        }

    }

}

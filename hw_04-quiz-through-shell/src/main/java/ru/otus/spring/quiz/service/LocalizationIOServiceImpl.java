package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocalizationIOServiceImpl implements LocalizationIOService, IOService, LocalizationService {

    private final IOService ioService;
    private final LocalizationService localizationService;


    @Override
    public void print(Object object) {
        ioService.print(object);
    }

    @Override
    public String scan() {
        return ioService.scan();
    }

    @Override
    public String getMessage(String property, String ... args) {
        return localizationService.getMessage(property, args);
    }

    @Override
    public void printPropertyValue(String property, String ... args) {
        ioService.print(localizationService.getMessage(property, args));
    }

}

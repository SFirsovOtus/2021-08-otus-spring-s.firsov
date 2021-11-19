package ru.otus.spring.quiz.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.service.IOService;
import ru.otus.spring.quiz.service.L10nService;

@Service
@AllArgsConstructor
public class L10nIOFacadeImpl implements L10nIOFacade {

    private final IOService ioService;
    private final L10nService l10nService;


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
        return l10nService.getMessage(property, args);
    }

    @Override
    public void printPropertyValue(String property, String ... args) {
        ioService.print(l10nService.getMessage(property, args));
    }

}

package ru.otus.spring.integration.dish.competition.service;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ToStringServiceImpl implements ToStringService {

    @Override
    public String printAddedIngredients(Map<String, Integer> addedIngredients,
                                        Map<String, Integer> necessaryIngredients) {
        return addedIngredients.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .map(ai -> ai + " - " + addedIngredients.get(ai) + "/" + necessaryIngredients.get(ai))
                .collect(Collectors.joining(", "));
    }

}

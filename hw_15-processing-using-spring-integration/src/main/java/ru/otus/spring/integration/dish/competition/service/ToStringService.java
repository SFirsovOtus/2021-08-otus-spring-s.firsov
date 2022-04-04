package ru.otus.spring.integration.dish.competition.service;

import java.util.Map;

public interface ToStringService {

    String printAddedIngredients(Map<String, Integer> addedIngredients,
                                 Map<String, Integer> necessaryIngredients);

}

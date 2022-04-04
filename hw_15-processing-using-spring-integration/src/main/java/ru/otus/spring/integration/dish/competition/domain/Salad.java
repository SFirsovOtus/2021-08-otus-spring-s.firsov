package ru.otus.spring.integration.dish.competition.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class Salad {

    private static final String INGREDIENT_TOMATO = "tomato";
    private static final String INGREDIENT_CUCUMBER = "cucumber";
    private static final String INGREDIENT_BELL_PEPPER = "bell pepper";
    private static final String INGREDIENT_RADISH = "radish";
    private static final String INGREDIENT_ONION = "onion";

    public static final Map<String, Integer> NECESSARY_INGREDIENTS =
            Map.of(
                    INGREDIENT_TOMATO, 1,
                    INGREDIENT_CUCUMBER, 1,
                    INGREDIENT_BELL_PEPPER, 1,
                    INGREDIENT_RADISH, 1,
                    INGREDIENT_ONION, 1
            );

    private Map<String, Integer> addedIngredients;

    public Salad() {
        addedIngredients = new HashMap<>();
        NECESSARY_INGREDIENTS.keySet().forEach(ingredientName -> addedIngredients.put(ingredientName, 0));
    }


    public boolean isDishReady() {
        for (Map.Entry <String, Integer> ingredient : addedIngredients.entrySet()) {
            if (ingredient.getValue() < NECESSARY_INGREDIENTS.get(ingredient.getKey())) {
                return false;
            }
        }

        return true;
    }

    public boolean needProvidedIngredient(String ingredient) {
        return NECESSARY_INGREDIENTS.containsKey(ingredient) &&
                addedIngredients.get(ingredient) < NECESSARY_INGREDIENTS.get(ingredient);
    }

}

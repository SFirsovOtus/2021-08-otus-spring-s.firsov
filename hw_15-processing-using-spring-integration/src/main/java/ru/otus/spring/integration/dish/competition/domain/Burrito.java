package ru.otus.spring.integration.dish.competition.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class Burrito {

    private static final String INGREDIENT_TOMATO = "tomato";
    private static final String INGREDIENT_BELL_PEPPER = "bell pepper";
    private static final String INGREDIENT_BEANS = "beans";
    private static final String INGREDIENT_CHICKEN = "chicken";
    private static final String INGREDIENT_TORTILLA = "tortilla";

    public static final Map<String, Integer> NECESSARY_INGREDIENTS =
            Map.of(
                    INGREDIENT_TOMATO, 1,
                    INGREDIENT_BELL_PEPPER, 1,
                    INGREDIENT_BEANS, 1,
                    INGREDIENT_CHICKEN, 1,
                    INGREDIENT_TORTILLA, 1
            );

    private Map<String, Integer> addedIngredients;

    public Burrito() {
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

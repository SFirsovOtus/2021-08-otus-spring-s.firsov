package ru.otus.spring.integration.dish.competition.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class Charlotte {

    private static final String INGREDIENT_FLOUR = "flour";
    private static final String INGREDIENT_EGG = "egg";
    private static final String INGREDIENT_APPLE = "apple";
    private static final String INGREDIENT_SUGAR = "sugar";

    public static final Map<String, Integer> NECESSARY_INGREDIENTS =
            Map.of(
                    INGREDIENT_FLOUR, 1,
                    INGREDIENT_EGG, 2,
                    INGREDIENT_APPLE, 1,
                    INGREDIENT_SUGAR, 1
            );

    private Map<String, Integer> addedIngredients;

    public Charlotte() {
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

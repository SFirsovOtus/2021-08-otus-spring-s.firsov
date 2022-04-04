package ru.otus.spring.integration.dish.competition.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class FriedEggs {

    private static final String INGREDIENT_EGG = "egg";

    public static final Map<String, Integer> NECESSARY_INGREDIENTS =
            Map.of(
                    INGREDIENT_EGG, 5
            );

    private Map<String, Integer> addedIngredients;

    public FriedEggs() {
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

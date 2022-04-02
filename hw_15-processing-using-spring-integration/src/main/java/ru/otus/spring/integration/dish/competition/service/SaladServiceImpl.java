package ru.otus.spring.integration.dish.competition.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.dish.competition.domain.Dishes;
import ru.otus.spring.integration.dish.competition.domain.Salad;

@Service("saladService")
@RequiredArgsConstructor
public class SaladServiceImpl implements SaladService {

    private final ToStringService toStringService;


    @Override
    public Dishes takeIngredient(Dishes dishes) {
        Salad salad = dishes.getSalad();
        String ingredient = dishes.getProvidedIngredient();

        if (salad.needProvidedIngredient(ingredient)) {
            salad.getAddedIngredients().put(ingredient, salad.getAddedIngredients().get(ingredient) + 1);
            System.out.println("Salad took " + ingredient + ": "
                    + toStringService.printAddedIngredients(salad.getAddedIngredients(), Salad.NECESSARY_INGREDIENTS));
        }

        return dishes;
    }

}

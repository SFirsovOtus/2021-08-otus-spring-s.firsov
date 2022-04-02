package ru.otus.spring.integration.dish.competition.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.dish.competition.domain.Dishes;
import ru.otus.spring.integration.dish.competition.domain.FriedEggs;

@Service("friedEggsService")
@RequiredArgsConstructor
public class FriedEggsServiceImpl implements FriedEggsService {

    private final ToStringService toStringService;


    @Override
    public Dishes takeIngredient(Dishes dishes) {
        FriedEggs friedEggs = dishes.getFriedEggs();
        String ingredient = dishes.getProvidedIngredient();

        if (friedEggs.needProvidedIngredient(ingredient)) {
            friedEggs.getAddedIngredients().put(ingredient, friedEggs.getAddedIngredients().get(ingredient) + 1);
            System.out.println("FriedEggs took " + ingredient + ": "
                    + toStringService.printAddedIngredients(friedEggs.getAddedIngredients(), FriedEggs.NECESSARY_INGREDIENTS));
        }

        return dishes;
    }

}

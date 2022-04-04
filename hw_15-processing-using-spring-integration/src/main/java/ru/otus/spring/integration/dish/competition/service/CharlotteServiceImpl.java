package ru.otus.spring.integration.dish.competition.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.dish.competition.domain.Charlotte;
import ru.otus.spring.integration.dish.competition.domain.Dishes;

@Service("charlotteService")
@RequiredArgsConstructor
public class CharlotteServiceImpl implements CharlotteService {

    private final ToStringService toStringService;


    @Override
    public Dishes takeIngredient(Dishes dishes) {
        Charlotte charlotte = dishes.getCharlotte();
        String ingredient = dishes.getProvidedIngredient();

        if (charlotte.needProvidedIngredient(ingredient)) {
            charlotte.getAddedIngredients().put(ingredient, charlotte.getAddedIngredients().get(ingredient) + 1);
            System.out.println("Charlotte took " + ingredient + ": "
                    + toStringService.printAddedIngredients(charlotte.getAddedIngredients(), Charlotte.NECESSARY_INGREDIENTS));
        }

        return dishes;
    }

}

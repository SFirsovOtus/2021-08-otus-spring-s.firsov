package ru.otus.spring.integration.dish.competition.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.dish.competition.domain.Burrito;
import ru.otus.spring.integration.dish.competition.domain.Dishes;

@Service("burritoService")
@RequiredArgsConstructor
public class BurritoServiceImpl implements BurritoService {

    private final ToStringService toStringService;


    @Override
    public Dishes takeIngredient(Dishes dishes) {
        Burrito burrito = dishes.getBurrito();
        String ingredient = dishes.getProvidedIngredient();

        if (burrito.needProvidedIngredient(ingredient)) {
            burrito.getAddedIngredients().put(ingredient, burrito.getAddedIngredients().get(ingredient) + 1);
            System.out.println("Burrito took " + ingredient + ": "
                    + toStringService.printAddedIngredients(burrito.getAddedIngredients(), Burrito.NECESSARY_INGREDIENTS));
        }

        return dishes;
    }

}

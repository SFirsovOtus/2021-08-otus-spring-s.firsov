package ru.otus.spring.integration.dish.competition.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.dish.competition.CompetitionGateway;
import ru.otus.spring.integration.dish.competition.domain.Burrito;
import ru.otus.spring.integration.dish.competition.domain.Charlotte;
import ru.otus.spring.integration.dish.competition.domain.Dishes;
import ru.otus.spring.integration.dish.competition.domain.FriedEggs;
import ru.otus.spring.integration.dish.competition.domain.Salad;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionGateway competitionGateway;


    @Override
    public void run() {
        Dishes dishes = new Dishes();

        List<String> ingredientNames = Stream.of(
                Burrito.NECESSARY_INGREDIENTS.keySet(),
                Charlotte.NECESSARY_INGREDIENTS.keySet(),
                FriedEggs.NECESSARY_INGREDIENTS.keySet(),
                Salad.NECESSARY_INGREDIENTS.keySet()
        ).flatMap(Set::stream)
                .collect(Collectors.toSet())
                .stream()
                .sorted()
                .collect(Collectors.toList());

        String generatedIngredient;
        int ingredientCounter = 0;
        while (true) {

            if (areAnyOfDishesReady(dishes)) {
                return;
            }

            generatedIngredient = ingredientNames.get(RandomUtils.nextInt(0, ingredientNames.size()));
            dishes.setProvidedIngredient(generatedIngredient);
            System.out.println(System.lineSeparator() + "Ingredient " + (++ingredientCounter) + " is " + generatedIngredient);

            competitionGateway.provideIngredient(dishes);

            try {
                Thread.sleep(1000L);
            } catch (Exception exception) {
                exception.printStackTrace();
                return;
            }
        }
    }


    private boolean areAnyOfDishesReady(Dishes dishes) {
        boolean dishReadyFlag = false;

        if (dishes.getBurrito().isDishReady()) {
            System.out.println(Burrito.class.getSimpleName() + " wins!");
            dishReadyFlag = true;
        }

        if (dishes.getCharlotte().isDishReady()) {
            System.out.println(Charlotte.class.getSimpleName() + " wins!");
            dishReadyFlag = true;
        }

        if (dishes.getFriedEggs().isDishReady()) {
            System.out.println(FriedEggs.class.getSimpleName() + " wins!");
            dishReadyFlag = true;
        }

        if (dishes.getSalad().isDishReady()) {
            System.out.println(Salad.class.getSimpleName() + " wins!");
            dishReadyFlag = true;
        }

        return dishReadyFlag;
    }

}

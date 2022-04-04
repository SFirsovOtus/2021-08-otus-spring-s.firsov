package ru.otus.spring.integration.dish.competition.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Dishes {

    private Burrito burrito = new Burrito();

    private Charlotte charlotte = new Charlotte();

    private FriedEggs friedEggs = new FriedEggs();

    private Salad salad = new Salad();

    private String providedIngredient;

}

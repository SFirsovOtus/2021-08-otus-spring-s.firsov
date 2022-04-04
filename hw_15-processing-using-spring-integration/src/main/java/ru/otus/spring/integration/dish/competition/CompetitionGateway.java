package ru.otus.spring.integration.dish.competition;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.dish.competition.domain.Dishes;

@MessagingGateway
public interface CompetitionGateway {

    @Gateway(requestChannel = "ingredientChannel")
    void provideIngredient(Dishes dishes);

}

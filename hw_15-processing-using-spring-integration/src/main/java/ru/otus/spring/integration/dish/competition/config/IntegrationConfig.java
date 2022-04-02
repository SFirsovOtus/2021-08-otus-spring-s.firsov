package ru.otus.spring.integration.dish.competition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
@IntegrationComponentScan
public class IntegrationConfig {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100)
                .get();
    }

    @Bean
    public QueueChannel ingredientChannel() {
        return MessageChannels.queue(10)
                .get();
    }

    @Bean
    public IntegrationFlow competitionFlow() {
        return IntegrationFlows.from("ingredientChannel")
                .handle("burritoService", "takeIngredient")
                .handle("charlotteService", "takeIngredient")
                .handle("friedEggsService", "takeIngredient")
                .handle("saladService", "takeIngredient")
                .get();
    }

}

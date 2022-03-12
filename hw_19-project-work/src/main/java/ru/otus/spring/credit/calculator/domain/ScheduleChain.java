package ru.otus.spring.credit.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static ru.otus.spring.credit.calculator.changelog.InitCollectionsChangeLog.COLLECTION_SCHEDULE_CHAINS;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = COLLECTION_SCHEDULE_CHAINS)
public class ScheduleChain {

    private String username;

    @Id
    private String id;

    private CreditParameters creditParameters;

    private List<Schedule> schedules;


    public ScheduleChain(String username,
                         CreditParameters creditParameters,
                         List<Schedule> schedules) {
        this.username = username;
        this.creditParameters = creditParameters;
        this.schedules = schedules;
    }

}

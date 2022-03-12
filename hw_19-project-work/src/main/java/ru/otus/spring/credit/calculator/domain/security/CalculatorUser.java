package ru.otus.spring.credit.calculator.domain.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static ru.otus.spring.credit.calculator.changelog.InitCollectionsChangeLog.COLLECTION_USERS;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = COLLECTION_USERS)
public class CalculatorUser {

    @Id
    private String username;

    private String password;

}

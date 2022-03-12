package ru.otus.spring.credit.calculator.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.credit.calculator.domain.security.CalculatorUser;
import ru.otus.spring.credit.calculator.repository.security.CalculatorUserRepository;

@ChangeLog(order = "001")
@RequiredArgsConstructor
public class InitCollectionsChangeLog {

    public static final String COLLECTION_USERS = "users";
    public static final String COLLECTION_SCHEDULE_CHAINS = "schedule_chains";

    @ChangeSet(order = "000", id = "dropCollections", author = "CollectionDestroyer", runAlways = true)
    public void dropCollections(MongoDatabase mongoDatabase){
        mongoDatabase.getCollection(COLLECTION_USERS).drop();
        mongoDatabase.getCollection(COLLECTION_SCHEDULE_CHAINS).drop();
    }

    @ChangeSet(order = "001", id = "initUsers", author = "UserCreator", runAlways = true)
    public void initUsers(CalculatorUserRepository calculatorUserRepository) {
        calculatorUserRepository.save(new CalculatorUser("first", "f123"));
        calculatorUserRepository.save(new CalculatorUser("second", "s123"));
        calculatorUserRepository.save(new CalculatorUser("third", "t123"));
    }

}

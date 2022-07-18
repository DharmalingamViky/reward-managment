package com.stellar.database;

import com.stellar.database.exception.AccessFailedException;
import com.stellar.database.mongodb.entity.reward.RewardDBAccess;
import org.bson.BsonValue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;


/**
 * Created by G513930 on 9/9/2020.
 */
public class RewardDBAccessTest {
    @InjectMocks
    private RewardDBAccess rewardDBAccess;

    @Mock
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}

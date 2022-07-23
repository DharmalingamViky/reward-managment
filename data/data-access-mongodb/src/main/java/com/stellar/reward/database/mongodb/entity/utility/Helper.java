package com.stellar.reward.database.mongodb.entity.utility;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import static com.stellar.reward.database.mongodb.entity.utility.ApiConstants.MONGO_TRANSACTION_COLLECTION_NAME_PAR_TRANSACTION_TIME;

public class Helper {

    private static final int DAYS_1_MONTH = 30; // for 1 month 1* 30 || 2 *30


    public static Query getRetriveCustomerQuery(String customerId, Long startTime, Long endTime) {
        Query query = new Query();
        query.addCriteria((Criteria.where(ApiConstants.MONGO_TRANSACTION_COLLECTION_NAME_PAR_PHONE_NUM).is(customerId)));

        // Todo in case of more than 1L transaction for three month than might be throws memory issue ..
        //Todo  introduce batch reading to avoid memory issue
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where(MONGO_TRANSACTION_COLLECTION_NAME_PAR_TRANSACTION_TIME).gt(startTime),
                Criteria.where(MONGO_TRANSACTION_COLLECTION_NAME_PAR_TRANSACTION_TIME).lt(endTime)
        );
        query.addCriteria(criteria);

        return query;
    }
}

package com.stellar.reward.database.mongodb.entity.utility;


public class ApiConstants {

    public static final String SLASH = "/";
    public static final String COLON = ":";

    public static final int DAYS_30 = 30;
    public static final Long REWARD_POINT_0 = 0L;


    public static final String MONGO_TRANSACTION_COLLECTION_NAME_PAR_ID = "_id";
    public static final String MONGO_TRANSACTION_COLLECTION_NAME_PAR_PHONE_NUM = "phoneNumber";
    public static final String MONGO_TRANSACTION_COLLECTION_NAME_PAR_CUSTOMERTYPE = "customerType";
    public static final String MONGO_TRANSACTION_COLLECTION_NAME_PAR_CUSTOMER_NAME = "customerName";
    public static final String MONGO_TRANSACTION_COLLECTION_NAME_PAR_AMOUNT_OF_PURCHASE = "totalAmountOfPurchase";
    public static final String MONGO_TRANSACTION_COLLECTION_NAME_PAR_TRANSACTION_TIME = "transactionTime";


    public static final String DB_COLLECTION_NAME = "transaction";

    private ApiConstants() {

    }
}

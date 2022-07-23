package com.stellar.reward.database.mongodb.entity.reward;


import com.stellar.reward.database.db.IRewardManagerBAccess;
import com.stellar.reward.database.exception.AccessFailedException;
import com.stellar.reward.database.exception.ObjectNotFoundException;
import com.stellar.reward.database.model.RewardHistory;
import com.stellar.reward.database.model.RewardPoint;
import com.stellar.reward.database.model.Summary;
import com.stellar.reward.database.model.Transaction;
import com.stellar.reward.database.mongodb.entity.config.RewardApiConfig;
import com.stellar.reward.database.mongodb.entity.utility.ApiConstants;
import com.stellar.reward.database.mongodb.entity.utility.Helper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.stellar.reward.database.mongodb.entity.utility.ApiConstants.*;

@Repository
public class RewardDBAccess implements IRewardManagerBAccess {
    Logger logger = LoggerFactory.getLogger(RewardDBAccess.class);

    private String accessFailedMessage = "Failed to access reward collection in database";
    private String objectNotFoundMessage = "Could not find reward object in database for id: ";

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RewardApiConfig rewardApiConfig;


    private int rewardRuleLowerMargin = 50;
    private int rewardRuleHigherMargin = 50;

    @Override
    public String insert(Transaction transaction) throws AccessFailedException {
        TransactionEntity rewardEntity = deserialize(transaction);
        try {
            logger.debug("insert: " + transaction.toString());
            TransactionEntity insertResponse = mongoTemplate.insert(rewardEntity, DB_COLLECTION_NAME);// Always take first reward
            logger.info("transaction persisted successfully");
            return insertResponse.getPhoneNumber();
        } catch (Exception me) {
            logger.error("Exception occurred while inserting transaction details" + me.getMessage());
            throw new AccessFailedException(accessFailedMessage, me);
        }
    }

    @Override
    public String update(Transaction transaction) throws AccessFailedException, ObjectNotFoundException {
        try {
            logger.debug("update: " + transaction.toString());
            Query query = new Query();
            query.addCriteria((Criteria.where(MONGO_TRANSACTION_COLLECTION_NAME_PAR_PHONE_NUM).is(transaction.getPhoneNumber())));

            Update update = new Update();
            update.set(MONGO_TRANSACTION_COLLECTION_NAME_PAR_CUSTOMERTYPE, transaction.getCustomerType());
            update.set(MONGO_TRANSACTION_COLLECTION_NAME_PAR_CUSTOMER_NAME, transaction.getCustomerName());

            mongoTemplate.upsert(query, update, TransactionEntity.class, DB_COLLECTION_NAME);// Always take first reward
            logger.info("transaction updated successfully");
            return transaction.getPhoneNumber();
        } catch (Exception me) {
            logger.error("Exception occurred while updating customer details" + me.getMessage());
            throw new AccessFailedException(accessFailedMessage, me);
        }
    }

    @Override
    public RewardHistory getReward(String customerId) throws AccessFailedException, ObjectNotFoundException {
        if (customerId == null) {
            throw new ObjectNotFoundException(objectNotFoundMessage + customerId);
        }
        try {
            logger.debug("getReward: " + customerId);

            RewardHistory rewardHistory = new RewardHistory();
            RewardPoint rewardPoint = new RewardPoint();
            List<Summary> summaryOfReward = new ArrayList<Summary>();

            // ToDo create Recursive method to perform for x months

            Long endTime = System.currentTimeMillis(); // current Time with server Time zone
            Long startTime;
            for (int month = 0; month < rewardApiConfig.getNumberOfSummary(); month++) {
                startTime = endTime - TimeUnit.DAYS.toMillis(DAYS_30); // last three month Data ( current - 90 days)

                try {
                    List<TransactionEntity> transactionEntities = mongoTemplate.find(Helper.getRetriveCustomerQuery(customerId, startTime, endTime), TransactionEntity.class, DB_COLLECTION_NAME);
                    logger.info("retrieved reward info for : {}", customerId);
                    serializerWithList(transactionEntities, rewardPoint);
                    summaryOfReward.add(month, getTransactionSummary(transactionEntities, startTime, endTime));
                } catch (Exception e) {
                    logger.error("Exception occurred while retrieving customer details with reward point information" + e.getMessage());
                }
                // last month

                endTime = startTime;
            }
            rewardPoint.setSummaryOfReward(summaryOfReward);
            rewardHistory.setRewardPoint(rewardPoint);
            return rewardHistory;
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving customer details with reward point information" + e.getMessage());
            throw new AccessFailedException(accessFailedMessage, e);
        }
    }

    @Override
    public boolean delete(String customerId) throws AccessFailedException, ObjectNotFoundException {
        try {
            logger.debug("delete: " + customerId);
            Query query = new Query();
            query.addCriteria((Criteria.where(ApiConstants.MONGO_TRANSACTION_COLLECTION_NAME_PAR_PHONE_NUM).is(customerId)));
            mongoTemplate.remove(query, DB_COLLECTION_NAME);
            return true;
        } catch (Exception e) {
            throw new AccessFailedException(accessFailedMessage, e);
        }
    }

    @Override
    public String checkStatus() throws AccessFailedException {
        return null;
    }


    // helper to implementation started
    private TransactionEntity isCustomerExist(Transaction transaction) throws AccessFailedException {
        try {
            Query query = new Query();
            query.addCriteria((Criteria.where(MONGO_TRANSACTION_COLLECTION_NAME_PAR_PHONE_NUM).is(transaction.getPhoneNumber())));
            return mongoTemplate.findOne(query, TransactionEntity.class, DB_COLLECTION_NAME);
        } catch (Exception me) {
            throw new AccessFailedException(accessFailedMessage, me);
        }
    }

    // Data model conversion
    // for inserting into database
    private TransactionEntity deserialize(Transaction transaction) {

        TransactionEntity rewardEntity = new TransactionEntity();

        if (!StringUtils.isAllBlank(transaction.getCustomerName())) {
            rewardEntity.setCustomerName(transaction.getCustomerName());
        }

        if (transaction.getCustomerType() != null) {
            rewardEntity.setCustomerType(transaction.getCustomerType());
        }

        if (transaction.getPhoneNumber() != null) {
            rewardEntity.setPhoneNumber(transaction.getPhoneNumber());
        }

        if (transaction.getAmountOfTransaction() != null) {
            rewardEntity.setTotalAmountOfPurchase(transaction.getAmountOfTransaction());
        }

        if (transaction.getTransactionTime() != null) {
            rewardEntity.setTransactionTime(transaction.getTransactionTime());
        }

        return rewardEntity;
    }

    private void serializerWithList(List<TransactionEntity> transactionEntities, RewardPoint rewardPoint) {
        for (TransactionEntity transactionEntity : transactionEntities) {
            serialize(transactionEntity, rewardPoint);
        }
    }

    private Summary getTransactionSummary(List<TransactionEntity> rewardEntity, Long startTime, Long endTime) {
        Summary summary = new Summary();
        try {
            if (rewardEntity.size() > 0) {
                List<Long> listOfValues = rewardEntity.stream().map(value -> value.getTotalAmountOfPurchase()).collect(Collectors.toList());

                Long sum = listOfValues.stream().mapToLong(i -> i).sum();
                if (sum > rewardRuleLowerMargin) {
                    Long totalRewardPoint = (sum - rewardRuleLowerMargin) * 1 + (sum - rewardRuleHigherMargin) * 1;
                    summary.setReward(totalRewardPoint);
                    summary.setTotalPurchase(sum);
                } else {
                    summary.setReward(REWARD_POINT_0);
                    summary.setTotalPurchase(REWARD_POINT_0);
                }
                summary.startTime(startTime);
                summary.endTime(endTime);
            } else {
                logger.info("no records found for between startTime" + startTime + " endTime"+ endTime);
            }
        } catch (Exception e) {
            logger.error("error occurred while converting to reward point" + e.getMessage());
        }

        return summary;
    }

    private RewardHistory serialize(TransactionEntity rewardEntity, RewardPoint rewardPoint) {
        RewardHistory rewardHistory = new RewardHistory();

        if (rewardEntity.getPhoneNumber() != null) {
            rewardPoint.setId(rewardEntity.getPhoneNumber());
        }

        if (!StringUtils.isAllBlank(rewardEntity.getCustomerName())) {
            rewardPoint.setCustomerName(rewardEntity.getCustomerName());
        }

        if (rewardEntity.getCustomerType() != null) {
            rewardPoint.setCustomerType(rewardEntity.getCustomerType());
        }

        if (rewardEntity.getPhoneNumber() != null) {
            rewardPoint.setPhoneNumber(rewardEntity.getPhoneNumber());
        }

        rewardHistory.setRewardPoint(rewardPoint);

        return rewardHistory;
    }
}
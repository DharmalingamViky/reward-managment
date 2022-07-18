package com.stellar.database.mongodb.entity.reward;


import com.stellar.database.db.IRewardManagerBAccess;
import com.stellar.database.exception.AccessFailedException;
import com.stellar.database.exception.ObjectNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import static com.stellar.database.mongodb.entity.utility.ApiConstants.DB_COLLECTION_NAME;
import static com.stellar.database.mongodb.entity.utility.ApiConstants.MONGO_FULE_COLLECTION_NAME_PAR_ID;

@Repository
public class RewardDBAccess implements IRewardManagerBAccess {
    Logger logger = LoggerFactory.getLogger(RewardDBAccess.class);

    private String accessFailedMessage = "Failed to access reward collection in database";
    private String objectNotFoundMessage = "Could not find reward object in database for id: ";

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public String insert(com.stellar.reward.api.model.Reward reward) throws AccessFailedException {
        RewardEntity rewardEntity = deserialize(reward);
        try {
            RewardEntity insertResponse = mongoTemplate.insert(rewardEntity, DB_COLLECTION_NAME);// Always take first reward
            logger.info("transaction inserted");
            return insertResponse.get_id();
        } catch (Exception me) {
            throw new AccessFailedException(accessFailedMessage, me);
        }
    }

    @Override
    public String update(com.stellar.reward.api.model.Reward reward) throws AccessFailedException, ObjectNotFoundException {
        return null;
    }

    @Override
    public com.stellar.reward.api.model.Reward getReward(String customerId) throws AccessFailedException, ObjectNotFoundException {
        if (customerId == null) {
            throw new ObjectNotFoundException(objectNotFoundMessage + customerId);
        }
        try {
            Query query = new Query();
            query.addCriteria((Criteria.where(MONGO_FULE_COLLECTION_NAME_PAR_ID).is(customerId)));
            RewardEntity rewardEntity = mongoTemplate.findOne(query, RewardEntity.class, DB_COLLECTION_NAME);
            logger.info("retrieved reward info for : {}", customerId);
            return serialize(rewardEntity);
        } catch (Exception e) {
            throw new AccessFailedException(accessFailedMessage, e);
        }
    }


    @Override
    public boolean delete(String customerId) throws AccessFailedException, ObjectNotFoundException {
        return false;
    }

    @Override
    public String checkStatus() throws AccessFailedException {
        return null;
    }


    // Data model conversion

    private RewardEntity deserialize(com.stellar.reward.api.model.Reward reward) {

        RewardEntity rewardEntity = new RewardEntity();

        if (!StringUtils.isAllBlank(reward.getCustomerName())) {
            rewardEntity.setCustomerName(reward.getCustomerName());
        }

        if (reward.getCustomerType() != null) {
            rewardEntity.setCustomerType(reward.getCustomerType());
        }

        if (rewardEntity.getRewardPoint() != null) {
            rewardEntity.setRewardPoint(reward.getRewardPoint());
        }

        return rewardEntity;
    }

    private com.stellar.reward.api.model.Reward serialize(RewardEntity rewardEntity) {
        com.stellar.reward.api.model.Reward reward = new com.stellar.reward.api.model.Reward();

        if (!StringUtils.isAllBlank(rewardEntity.getCustomerName())) {
            reward.setCustomerName(rewardEntity.getCustomerName());
        }

        if (rewardEntity.getCustomerType() != null) {
            reward.setCustomerType(rewardEntity.getCustomerType());
        }

        if (rewardEntity.getRewardPoint() != null) {
            reward.setRewardPoint(rewardEntity.getRewardPoint());
        }

        return reward;


    }
}
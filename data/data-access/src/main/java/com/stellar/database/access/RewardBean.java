package com.stellar.database.access;


import com.stellar.database.db.IRewardManagerBAccess;
import com.stellar.database.exception.AccessFailedException;
import com.stellar.database.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RewardBean  {

    @Autowired
    protected IRewardManagerBAccess rewardApiRepositoryImp;


    public String checkStatus() throws AccessFailedException {
        return rewardApiRepositoryImp.checkStatus();
    }

    public String insert(com.stellar.reward.api.model.Reward reward) throws AccessFailedException {
        return rewardApiRepositoryImp.insert(reward);
    }

    public String updateReward(com.stellar.reward.api.model.Reward reward) throws AccessFailedException, ObjectNotFoundException {
        return rewardApiRepositoryImp.update(reward);
    }

    public com.stellar.reward.api.model.Reward getReward(String customerId) throws AccessFailedException, ObjectNotFoundException {
        return rewardApiRepositoryImp.getReward(customerId);
    }
    public boolean delete(String customerId) throws AccessFailedException, ObjectNotFoundException {
        return rewardApiRepositoryImp.delete(customerId);
    }

}

package com.stellar.reward.database.access;


import com.stellar.reward.database.db.IRewardManagerBAccess;
import com.stellar.reward.database.exception.AccessFailedException;
import com.stellar.reward.database.exception.ObjectNotFoundException;
import com.stellar.reward.database.model.RewardHistory;
import com.stellar.reward.database.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RewardBean  {

    @Autowired
    protected IRewardManagerBAccess rewardApiRepositoryImp;


    public String checkStatus() throws AccessFailedException {
        return rewardApiRepositoryImp.checkStatus();
    }

    public String insert(Transaction transaction) throws AccessFailedException {
        return rewardApiRepositoryImp.insert(transaction);
    }

    public String updateReward(Transaction transaction) throws AccessFailedException, ObjectNotFoundException {
        return rewardApiRepositoryImp.update(transaction);
    }

    public RewardHistory getReward(String customerId) throws AccessFailedException, ObjectNotFoundException {
        return rewardApiRepositoryImp.getReward(customerId);
    }
    public boolean delete(String customerId) throws AccessFailedException, ObjectNotFoundException {
        return rewardApiRepositoryImp.delete(customerId);
    }

}

package com.stellar.reward.database.db;

import com.stellar.reward.database.exception.AccessFailedException;
import com.stellar.reward.database.exception.ObjectNotFoundException;
import com.stellar.reward.database.model.RewardHistory;
import com.stellar.reward.database.model.Transaction;


public interface IRewardManagerBAccess {

    public String insert(Transaction transaction) throws AccessFailedException;
    public String update(Transaction transaction) throws AccessFailedException, ObjectNotFoundException;
    public RewardHistory getReward(String customerId) throws AccessFailedException, ObjectNotFoundException;
    public boolean delete(String customerId) throws AccessFailedException, ObjectNotFoundException;
    public String checkStatus() throws AccessFailedException;


}

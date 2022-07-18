package com.stellar.database.db;

import com.stellar.database.exception.AccessFailedException;
import com.stellar.database.exception.ObjectNotFoundException;

/**
 * Created by G522298 on 6/4/2020.
 */
public interface IRewardManagerBAccess {

    String insert(com.stellar.reward.api.model.Reward reward) throws AccessFailedException;
    String update(com.stellar.reward.api.model.Reward reward) throws AccessFailedException, ObjectNotFoundException;
    com.stellar.reward.api.model.Reward getReward(String customerId) throws AccessFailedException, ObjectNotFoundException;
    boolean delete(String customerId) throws AccessFailedException, ObjectNotFoundException;
    String checkStatus() throws AccessFailedException;


}

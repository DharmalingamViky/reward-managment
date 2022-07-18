package com.stellar.reward.api.service;

import com.stellar.database.access.RewardBean;
import com.stellar.database.exception.AccessFailedException;
import com.stellar.database.exception.ObjectNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import org.owasp.encoder.Encode;

@Service
public class RewardApiService {

    Logger logger = LoggerFactory.getLogger(RewardApiService.class);

    private static final String RULE_METADATA_MISSING = "Customer metadata are missing";
    private static final String NOT_FOUND = " is not found";
    private static final String RULE_NOT_FOUND = "could not find any reward:";

    @Autowired
    public RewardBean rewardApiDao;


    public ResponseEntity<com.stellar.reward.api.model.RewardGetResponse> getReward(String customerId) {
        try {
            com.stellar.reward.api.model.RewardGetResponse rewardGetResponse = new com.stellar.reward.api.model.RewardGetResponse();
            com.stellar.reward.api.model.Reward reward = new com.stellar.reward.api.model.Reward();
            if (customerId != null) {
                reward = rewardApiDao.getReward(customerId);
            }
            com.stellar.reward.api.model.RewardGetResponse response = new com.stellar.reward.api.model.RewardGetResponse();
            response.setReward(reward);
            return new ResponseEntity(response.toString(), HttpStatus.OK);

        } catch (AccessFailedException e) {
            return new ResponseEntity("could not get reward infor for customer id : " + Encode.forHtml(customerId), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (ObjectNotFoundException e) {
            return new ResponseEntity("cannot get reward since customer id  : " + Encode.forHtml(customerId) + NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<com.stellar.reward.api.model.RewardCreationResponse> insertCustomer(com.stellar.reward.api.model.RewardCreationRequest rewardCreationRequest) {
        try {
            com.stellar.reward.api.model.RewardCreationResponse response = new com.stellar.reward.api.model.RewardCreationResponse();
            if (StringUtils.isNotBlank(rewardApiDao.insert(rewardCreationRequest.getReward()))) {
                response.setReward(rewardCreationRequest.getReward());
                return new ResponseEntity(response.toString(), HttpStatus.OK);
            } else {
                response.setReward(rewardCreationRequest.getReward());
                return new ResponseEntity(response.toString(), HttpStatus.NO_CONTENT);
            }
        } catch (AccessFailedException e) {
            return new ResponseEntity("could not get reward infor for customer id : ", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    public ResponseEntity<com.stellar.reward.api.model.ResponseBase> deleteCustomer(String customerId) {
        com.stellar.reward.api.model.ResponseBase response = new com.stellar.reward.api.model.ResponseBase();
        try {
            if (rewardApiDao.delete(customerId)) {
                response.setMessage("deleted successfully");
                return new ResponseEntity(response.toString(), HttpStatus.OK);
            } else {
                response.setMessage("failed to delete");
                return new ResponseEntity(response.toString(), HttpStatus.NO_CONTENT);
            }
        } catch (AccessFailedException e) {
            return new ResponseEntity("could not get reward infor for customer id : " + Encode.forHtml(customerId), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (ObjectNotFoundException e) {
            return new ResponseEntity("cannot get reward since customer id   : " + Encode.forHtml(customerId) + NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }


    /*** Helper Code Started ***/

    // Todo : Write data validation code for input and thows the invalid data

}

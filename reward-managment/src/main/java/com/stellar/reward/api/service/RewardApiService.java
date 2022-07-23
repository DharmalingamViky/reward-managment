package com.stellar.reward.api.service;

import com.stellar.reward.database.access.RewardBean;
import com.stellar.reward.database.exception.AccessFailedException;
import com.stellar.reward.database.exception.ObjectNotFoundException;

import com.stellar.reward.database.model.ResponseBase;
import com.stellar.reward.database.model.RewardHistory;
import com.stellar.reward.database.model.Transaction;
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


    public ResponseEntity<RewardHistory> getReward(String customerId) {
        try {
            RewardHistory history = rewardApiDao.getReward(customerId);
            return new ResponseEntity(history.toString(), HttpStatus.OK);
        } catch (AccessFailedException e) {
            return new ResponseEntity("could not get reward information for customer id : " + Encode.forHtml(customerId), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (ObjectNotFoundException e) {
            return new ResponseEntity("could not get reward information for customer id  : " + Encode.forHtml(customerId) + NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseBase> insertTransaction(Transaction transaction) {

        ResponseBase response = new ResponseBase();
        try {
            if (!validateInputs(transaction)) {
                response.setCode(500);
                response.setSuccess(false);
                response.setMessage("invalid inpuur : ");
                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }

            rewardApiDao.insert(transaction);
            response.setCode(200);
            response.setMessage("success");
            response.setSuccess(true);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (AccessFailedException e) {
            response.setCode(500);
            response.setSuccess(false);
            response.setMessage("not able to insert new transaction : " + e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<ResponseBase> deleteCustomer(String customerId) {
        ResponseBase response = new ResponseBase();
        try {
            rewardApiDao.delete(customerId);
            response.setCode(200);
            response.setMessage("success");
            response.setSuccess(true);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (AccessFailedException e) {
            response.setCode(403);
            response.setMessage("could not delete customer id : " + e.getMessage());
            return new ResponseEntity("not able to delete with customer id : " + Encode.forHtml(customerId), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (ObjectNotFoundException e) {
            response.setCode(404);
            response.setSuccess(false);
            response.setMessage("could not delete customer id  : " + e.getMessage());
            return new ResponseEntity("not able to delete with customer id  : " + Encode.forHtml(customerId) + NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }


    /*** Helper Code Started ***/

    // Todo : Write data validation code for input and thows the invalid data
    // Throw 404 for invalid values
    private boolean validateInputs(Transaction transaction) {
        if (StringUtils.isNotEmpty(transaction.getPhoneNumber()) && transaction.getAmountOfTransaction() != null) {
            return true;
        } else {
            return false;
        }
    }

}

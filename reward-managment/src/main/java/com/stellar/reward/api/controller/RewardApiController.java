package com.stellar.reward.api.controller;

import com.stellar.reward.api.RewardCrUdApi;
import com.stellar.reward.api.service.RewardApiService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;



@RestController
public class RewardApiController implements RewardCrUdApi {
    Logger logger = LoggerFactory.getLogger(RewardApiController.class);

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RewardApiController(NativeWebRequest request) {
        this.request = request;
    }


    @Autowired
    private RewardApiService rewardApiService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }


    @Override
    public ResponseEntity<com.stellar.reward.api.model.RewardCreationResponse> insertReward(@ApiParam(value = "reward to insert") @Valid @RequestBody com.stellar.reward.api.model.RewardCreationRequest rewardCreationRequest){
        return  rewardApiService.insertCustomer(rewardCreationRequest);
    }

    @Override
    public ResponseEntity<com.stellar.reward.api.model.ResponseBase> deleteCustomer(@ApiParam(value = "id of the customer to delete", required = true) @PathVariable("customerId") String customerId) {
        return  rewardApiService.deleteCustomer(customerId);

    }

    @Override
    public ResponseEntity<com.stellar.reward.api.model.RewardGetResponse> getReward(@ApiParam(value = "id of the customer", required = true) @PathVariable("customerId") String customerId) {
       return  rewardApiService.getReward(customerId);
    }

}

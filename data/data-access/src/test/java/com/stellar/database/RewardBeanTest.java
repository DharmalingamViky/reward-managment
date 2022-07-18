package com.stellar.database;

import com.stellar.database.access.RewardBean;
import com.stellar.database.db.IRewardManagerBAccess;
import com.stellar.database.exception.AccessFailedException;
import com.stellar.database.exception.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class RewardBeanTest {
    // more test cases needs to be added
   @InjectMocks
    private RewardBean rewardBean;

    @Mock
    private IRewardManagerBAccess rewardApiRepositoryImp;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateInsertRule() throws AccessFailedException {
        com.stellar.reward.api.model.Reward reward = new  com.stellar.reward.api.model.Reward();
        reward.setCustomerName("DemoId");
        when(rewardApiRepositoryImp.insert(reward)).thenReturn("DemoId");
        assertThat(reward.getCustomerName(), is(equalTo(rewardBean.insert(reward))));
    }

    @Test
    public void validateUpdateRule() throws AccessFailedException, ObjectNotFoundException {
        com.stellar.reward.api.model.Reward reward = new  com.stellar.reward.api.model.Reward();
        reward.setCustomerName("DemoId");
        String listOfUpdatedId = ("DemoId");
        when(rewardApiRepositoryImp.update(reward)).thenReturn(listOfUpdatedId);
        assertThat(listOfUpdatedId, is(equalTo(rewardBean.updateReward(reward))));
    }

    @Test
    public void validateGetRule() throws AccessFailedException, ObjectNotFoundException {
        com.stellar.reward.api.model.Reward reward = new  com.stellar.reward.api.model.Reward();
        reward.setCustomerName("DemoId");
        when(rewardApiRepositoryImp.getReward(anyString())).thenReturn(reward);
        assertThat(reward, is(equalTo(rewardBean.getReward("DemoId"))));
    }

    @Test
    public void validateDeleteRule() throws AccessFailedException , ObjectNotFoundException{
        com.stellar.reward.api.model.Reward reward = new  com.stellar.reward.api.model.Reward();
        reward.setCustomerName("DemoId");
        when(rewardApiRepositoryImp.delete(anyString())).thenReturn(true);
        assertThat(true, is(equalTo(rewardBean.delete("DemoId"))));
    }
}

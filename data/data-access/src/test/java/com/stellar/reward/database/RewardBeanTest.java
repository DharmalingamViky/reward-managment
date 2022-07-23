package com.stellar.reward.database;

import com.stellar.reward.database.access.RewardBean;
import com.stellar.reward.database.db.IRewardManagerBAccess;
import com.stellar.reward.database.exception.AccessFailedException;
import com.stellar.reward.database.exception.ObjectNotFoundException;
import com.stellar.reward.database.model.RewardHistory;
import com.stellar.reward.database.model.Transaction;
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
    RewardHistory rewardHistory;

    @Mock
    private IRewardManagerBAccess rewardApiRepositoryImp;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateInsertRule() throws AccessFailedException {
        Transaction transaction = new Transaction();
        transaction.setCustomerName("DemoId");
        when(rewardApiRepositoryImp.insert(transaction)).thenReturn("DemoId");
        assertThat(transaction.getCustomerName(), is(equalTo(rewardBean.insert(transaction))));
    }

    @Test
    public void validateUpdateRule() throws AccessFailedException, ObjectNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setCustomerName("DemoId");
        String listOfUpdatedId = ("DemoId");
        when(rewardApiRepositoryImp.update(transaction)).thenReturn(listOfUpdatedId);
        assertThat(listOfUpdatedId, is(equalTo(rewardBean.updateReward(transaction))));
    }

    @Test
    public void validateGetRule() throws AccessFailedException, ObjectNotFoundException {

        Transaction transaction = new Transaction();
        transaction.setCustomerName("DemoId");
        when(rewardApiRepositoryImp.getReward(anyString())).thenReturn(rewardHistory);
        assertThat(rewardHistory, is(equalTo(rewardBean.getReward("DemoId"))));
    }

    @Test
    public void validateDeleteRule() throws AccessFailedException, ObjectNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setCustomerName("DemoId");
        when(rewardApiRepositoryImp.delete(anyString())).thenReturn(true);
        assertThat(true, is(equalTo(rewardBean.delete("DemoId"))));
    }
}

package com.aforo255.msaccount.service.impl;

import com.aforo255.msaccount.dao.AccountDao;
import com.aforo255.msaccount.model.domain.Transaction;
import com.aforo255.msaccount.util.TestUtil;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void whenGetAccountsThenReturnAccounts() {

        Mockito.when(accountDao.getAccounts())
                .thenReturn(Observable.just(TestUtil.buildAccount()));

        var testObserver = accountService.findAll().test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete()
                .assertNoErrors();
    }

    @Test
    void whenSearchAccountByIdThenReturnAccount() {

        Mockito.when(accountDao.findAccountById(Mockito.anyInt()))
                .thenReturn(Single.just(TestUtil.buildAccount()));

        var testObserver = accountService.findById(1).test();
        testObserver.assertComplete()
                .assertNoErrors();
    }

    @Test
    void whenSaveAccountDepositThenReturnSuccessful() {

        Mockito.when(accountDao.findAccountById(Mockito.anyInt()))
                .thenReturn(Single.just(TestUtil.buildAccount()));

        Mockito.when(accountDao.saveAccount(Mockito.any()))
                .thenReturn(Completable.complete());

        var testObserver = accountService.save(buildTransaction("deposit")).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete().assertNoErrors();
    }

    @Test
    void whenSaveAccountWithdrawalThenReturnSuccessful() {

        Mockito.when(accountDao.findAccountById(Mockito.anyInt()))
                .thenReturn(Single.just(TestUtil.buildAccount()));

        Mockito.when(accountDao.saveAccount(Mockito.any()))
                .thenReturn(Completable.complete());

        var testObserver = accountService.save(buildTransaction("withdrawal")).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete().assertNoErrors();
    }

    private Transaction buildTransaction(String type) {
        return Transaction.builder()
                .accountId(1)
                .amount(100)
                .creationDate("01/01/2020")
                .id(1)
                .type(type)
                .build();
    }
}

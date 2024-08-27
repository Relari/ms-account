package com.aforo255.msaccount.dao.impl;

import com.aforo255.msaccount.dao.repository.AccountRepository;
import com.aforo255.msaccount.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountDaoImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountDaoImpl accountDao;

    @Test
    void whenGetAccountsThenReturnAccounts() {

        Mockito.when(accountRepository.findAll())
                .thenReturn(Collections.singletonList(TestUtil.buildAccountEntity()));

        var testObserver = accountDao.getAccounts().test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete()
                .assertNoErrors();
    }

    @Test
    void whenSearchAccountByIdThenReturnAccount() {

        Mockito.when(accountRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(TestUtil.buildAccountEntity()));

        var testObserver = accountDao.findAccountById(1).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete()
                .assertNoErrors();
    }

    @Test
    void whenSaveAccountDepositThenReturnSuccessful() {

        Mockito.when(accountRepository.save(Mockito.any()))
                .thenReturn(TestUtil.buildAccountEntity());

        var testObserver = accountDao.saveAccount(TestUtil.buildAccount()).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete().assertNoErrors();
    }
}

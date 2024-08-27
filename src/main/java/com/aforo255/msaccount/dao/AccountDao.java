package com.aforo255.msaccount.dao;

import com.aforo255.msaccount.model.domain.Account;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface AccountDao {

    Observable<Account> getAccounts();

    Completable saveAccount(Account accountEntity);

    Single<Account> findAccountById(Integer id);
}

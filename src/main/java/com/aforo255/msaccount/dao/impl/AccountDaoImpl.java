package com.aforo255.msaccount.dao.impl;

import com.aforo255.msaccount.dao.repository.AccountRepository;
import com.aforo255.msaccount.model.domain.Account;
import com.aforo255.msaccount.model.domain.Customer;
import com.aforo255.msaccount.model.entity.AccountEntity;
import com.aforo255.msaccount.dao.AccountDao;
import com.aforo255.msaccount.model.entity.CustomerEntity;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Observable<Account> getAccounts() {
        return Observable.fromIterable(accountRepository.findAll())
                .subscribeOn(Schedulers.io())
                .map(this::mapAccount);
    }

    private Account mapAccount(AccountEntity accountEntity) {
        return Account.builder()
                .idAccount(accountEntity.getIdAccount())
                .totalAmount(accountEntity.getTotalAmount())
                .customer(new Customer(
                        accountEntity.getCustomer().getIdCustomer(),
                        accountEntity.getCustomer().getFullName())
                )
                .build();
    }

    @Override
    public Completable saveAccount(Account account) {
        return Single.fromCallable(() -> this.mapAccountEntity(account))
                .map(accountRepository::save)
                .subscribeOn(Schedulers.io())
                .ignoreElement();
    }

    private AccountEntity mapAccountEntity(Account account) {
        return AccountEntity.builder()
                .idAccount(account.getIdAccount())
                .totalAmount(account.getTotalAmount())
                .customer(new CustomerEntity(
                        account.getCustomer().getIdCustomer(),
                        account.getCustomer().getFullName())
                )
                .build();
    }

    @Override
    public Single<Account> findAccountById(Integer id) {
        return Single.fromCallable(() ->
                accountRepository.findById(id).orElse(null))
                .subscribeOn(Schedulers.io())
                .map(this::mapAccount);
    }
}

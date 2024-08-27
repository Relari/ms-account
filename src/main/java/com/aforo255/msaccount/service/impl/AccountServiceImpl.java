package com.aforo255.msaccount.service.impl;

import com.aforo255.msaccount.dao.AccountDao;
import com.aforo255.msaccount.model.domain.Account;
import com.aforo255.msaccount.model.domain.Transaction;
import com.aforo255.msaccount.service.IAccountService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public Observable<Account> findAll() {
		return accountDao.getAccounts();
	}

	@Override
	public Single<Account> findById(Integer id) {
		return accountDao.findAccountById(id);
	}

	@Override
	public Completable save(Transaction transaction) {
		return accountDao.findAccountById(transaction.getAccountId())
				.map(account -> {
					double newAmount = 0;

					switch (transaction.getType()) {

						case "deposit":
							newAmount = account.getTotalAmount() + transaction.getAmount();
							break;

						case "withdrawal":
							newAmount = account.getTotalAmount() - transaction.getAmount();
							break;
					}
					account.setTotalAmount(newAmount);
					return account;
				}).flatMapCompletable(accountDao::saveAccount);
	}


}

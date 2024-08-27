package com.aforo255.msaccount.service;

import com.aforo255.msaccount.model.domain.Account;
import com.aforo255.msaccount.model.domain.Transaction;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IAccountService {

	Observable<Account> findAll();
	Single<Account> findById (Integer id );
	Completable save (Transaction event);
	
}

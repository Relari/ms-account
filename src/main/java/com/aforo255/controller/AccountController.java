package com.aforo255.controller;

import com.aforo255.msaccount.model.api.AccountResponse;
import com.aforo255.msaccount.model.api.CustomerResponse;
import com.aforo255.msaccount.model.domain.Account;
import com.aforo255.msaccount.service.IAccountService;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/accounts")
public class AccountController {

	@Autowired
	private IAccountService service;

	@GetMapping
	public Observable<AccountResponse> listar() {
		return service.findAll()
				.map(this::mapAccountResponse);
	}

	@GetMapping("/{id}")
	public Single<AccountResponse> detalle(@PathVariable Integer id) {
		return service.findById(id)
				.map(this::mapAccountResponse);
	}

	private AccountResponse mapAccountResponse(Account account) {
		return AccountResponse.builder()
				.idAccount(account.getIdAccount())
				.totalAmount(account.getTotalAmount())
				.customer(new CustomerResponse(
						account.getCustomer().getIdCustomer(),
						account.getCustomer().getFullName())
				)
				.build();
	}
}

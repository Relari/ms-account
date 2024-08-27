package com.aforo255.controller;

import com.aforo255.msaccount.service.IAccountService;
import com.aforo255.msaccount.util.TestUtil;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

	@Mock
	private IAccountService accountService;

	@InjectMocks
	private AccountController accountController;

	@Test
	void whenGetAccountsThenReturnAccounts() {

		Mockito.when(accountService.findAll())
				.thenReturn(Observable.just(TestUtil.buildAccount()));

		var testObserver = accountController.listar().test();
		testObserver.awaitTerminalEvent();
		testObserver.assertComplete()
				.assertNoErrors();
	}

	@Test
	void whenSearchAccountByIdThenReturnAccount() {

		Mockito.when(accountService.findById(Mockito.anyInt()))
				.thenReturn(Single.just(TestUtil.buildAccount()));

		var testObserver = accountController.detalle(1).test();
		testObserver.assertComplete()
				.assertNoErrors();
	}

}

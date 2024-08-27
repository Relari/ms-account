package com.aforo255.msaccount.util;

import com.aforo255.msaccount.model.domain.Account;
import com.aforo255.msaccount.model.domain.Customer;
import com.aforo255.msaccount.model.entity.AccountEntity;
import com.aforo255.msaccount.model.entity.CustomerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtil {

    public static Account buildAccount() {
        var customer = new Customer(1, "fullName");
        return Account.builder()
                .customer(customer)
                .idAccount(1)
                .totalAmount(100)
                .build();
    }

    public static AccountEntity buildAccountEntity() {
        var customer = new CustomerEntity(1, "fullName");
        return AccountEntity.builder()
                .customer(customer)
                .idAccount(1)
                .totalAmount(100)
                .build();
    }
}

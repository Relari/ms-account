package com.aforo255.msaccount.model.api;


import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Integer idAccount ;
    private double totalAmount ;
    private CustomerResponse customer;
}

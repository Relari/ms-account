package com.aforo255.msaccount.model.domain;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Integer idAccount ;
    private double totalAmount ;
    private Customer customer;

}

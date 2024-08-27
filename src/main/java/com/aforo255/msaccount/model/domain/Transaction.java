package com.aforo255.msaccount.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id ;
	private double amount ;
	private String type;
	private String creationDate ;
	private int accountId;

}

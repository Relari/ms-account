package com.aforo255.msaccount.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="account")
public class AccountEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAccount ;
	@Column(name="total_amount")
	private double totalAmount ;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_customer" , insertable =false , updatable = false)
	@Fetch(FetchMode.JOIN)
	private CustomerEntity customer;

}
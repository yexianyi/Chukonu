package com.yxy.chukonu.mybatis.mapper.account;

import lombok.Data;
/**
 * 
 create table accounts(
  id varchar(255) primary key,
  acc_number varchar(255) not null,
  acc_balance float not null,
  acc_cus_ref_id varchar(255) not null,
  FOREIGN KEY (acc_cus_ref_id) REFERENCES customer(id)
)

 * @author xianyiye
 *
 */

@Data
public class Account {

	private String id ;
	
	private String acc_number ;
	
	private float acc_balance ;
	
	private String acc_cus_ref_id ;
	
}

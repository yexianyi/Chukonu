package com.yxy.chukonu.mybatis.mapper.account;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {
	
	public Account findAccount(String acc_uuid);
	
	public float getBalance(String usrId);
	
	public void deposit(@Param(value="amount") float amount, @Param(value="usrId") String usrId) ;
	
	public void withdraw(@Param(value="amount") float amount, @Param(value="usrId") String usrId) ;

}

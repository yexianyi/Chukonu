package com.yxy.chukonu.mybatis.mapper.account;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {
	
	public Account findAccount(String acc_uuid);
	
	

}

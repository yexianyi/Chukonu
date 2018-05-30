package com.yxy.chukonu.redis.optimistic.lock;

import com.yxy.chukonu.redis.model.dao.RedisDao;

public class Main {

	public static final String KEY = "RED_PACKAGE_NUM" ;
	

	private static void initRedisKey() {
		RedisDao dao = new RedisDao() ;
		dao.clearDB();
		dao.saveUpdateString(KEY, "1");
		
	}
	
	
	public static void main(String[] args) {
		initRedisKey() ;
		
		int num = 1000 ;
		for(int i=0; i<num; i++) {
			new RequestThread().start();
		}
		

	}

}

package com.yxy.chukonu.redis.model.dao;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class BaseDao {
	private static volatile JedisPool pool ;
	
	public BaseDao() {
		getInstance() ;
	} 
	
	private static void getInstance(){
		if(pool==null){
			synchronized(BaseDao.class){
				if(pool==null){
					JedisPoolConfig config = new JedisPoolConfig() ;
					pool = new JedisPool(config, "192.168.99.101");
				}
			}
		}
		
	}
	
	
	protected Jedis getResource() {
		return pool.getResource() ;
	} 

}

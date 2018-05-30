package com.yxy.chukonu.redis.optimistic.lock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxy.chukonu.redis.model.dao.RedisDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RequestThread2 extends Thread {
	
	private RedisDao dao = new RedisDao() ;

	@Override
	public void run() {
		Jedis jedis = dao.getJedis() ;
		int num = Integer.parseInt(jedis.get(Main.KEY));
		if(num>1) {
			System.out.println("Red package is out.");
		}else {
			try {
				long newNum = jedis.incr(Main.KEY);
				if (newNum>1) {
					System.out.println("Red package is out.");
				} else {
					TestService ts = new TestService() ;
					ts.handleRequest(Thread.currentThread().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jedis.close();
			}
			
		}
		
	}


}

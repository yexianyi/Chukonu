package com.yxy.chukonu.redis.optimistic.lock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxy.chukonu.redis.model.dao.RedisDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RequestThread extends Thread {
	
	private RedisDao dao = new RedisDao() ;

	@Override
	public void run() {
		Jedis jedis = dao.getJedis() ;
			try {
				jedis.watch(Main.KEY);
				int num = Integer.parseInt(jedis.get(Main.KEY));
				if(num>0) {
					Transaction transaction = jedis.multi();
					transaction.set(Main.KEY, String.valueOf(num-1));
					List<Object> result = transaction.exec();
					
					if (result == null || result.isEmpty()) {
						System.out.println("Red package is out.");
					} else {
						TestService ts = new TestService() ;
						ts.handleRequest(Thread.currentThread().getName());
					}
				}else {
					System.out.println("Red package is out.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jedis.unwatch();
				jedis.close();
			}
		
	}


}

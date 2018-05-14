package com.yxy.chukonu.redis;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Hello world!
 *
 */
public class Client 
{
	public static void main(String args[]) {
//		 Jedis jedis = new Jedis("192.168.99.101", 6379);  
////		 jedis.auth("redis") ;
//			System.out.println(jedis);  
//			 jedis.ping();  

		
		JedisPoolConfig config = new JedisPoolConfig() ;
		JedisPool pool = new JedisPool(config, "192.168.99.101");

		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  /// ... do stuff here ... for example
		  jedis.set("foo", "bar");
		  String foobar = jedis.get("foo");
		  System.out.println(foobar);
		  
		  jedis.zadd("sose", 3.2, "car"); jedis.zadd("sose", 4.6, "bike"); 
		  Set<String> sose = jedis.zrange("sose", 0, -1);
		  for(String s:sose) {
			  System.out.println(s);
		  }
		  
		//添加元素
			jedis.sadd("set02", "one");
			jedis.sadd("set02", "two");
			jedis.sadd("set02", "three");
			jedis.sadd("set02", "four");
			//读取
			Set set = jedis.smembers("set02");
			//遍历打印输出
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				System.out.println("key为set02中的元素有："+obj);
			}

		  
		  
			jedis.set("name", "张三"); // 设置
			jedis.set("age", "23");

			String name = jedis.get("name");// 获取
			String age = jedis.get("age");
			System.out.println("name:" + name + ",age:" + age);

			jedis.set("name", "李四"); // 覆盖
			name = jedis.get("name");
			System.out.println("name:" + name);

			jedis.append("name", "-尼古拉斯"); // 追加
			name = jedis.get("name");// 获取
			age = jedis.get("age");
			System.out.println("name:" + name + ",age:" + age);

			jedis.del("name"); // 删除
			name = jedis.get("name");
			System.out.println("name:" + name);

			jedis.del("name", "age"); // 删除多个key
			name = jedis.get("name");// 获取
			age = jedis.get("age");
			System.out.println("name:" + name + ",age:" + age);

			
			System.out.println("===========新增键值对防止覆盖原先值==============");
		    System.out.println(jedis.setnx("key1", "value1"));
		    System.out.println(jedis.setnx("key2", "value2"));
		    System.out.println(jedis.setnx("key2", "value2-new"));
		    System.out.println("key1:" + jedis.get("key1"));
		    System.out.println("key2:" + jedis.get("key2"));
			
		    
			System.out.println("===========新增键值对并设置有效时间=============");
			System.out.println(jedis.setex("key3", 2, "value3"));
			System.out.println(jedis.get("key3"));
			System.out.println("key3:" + jedis.get("key3"));
			
			
		    System.out.println("===========获取原值，更新为新值==========");
		    //GETSET is an atomic set this value and return the old value command.
			System.out.println(jedis.getSet("key2", "key2GetSet"));
			System.out.println("key2:" + jedis.get("key2"));

			
			System.out.println("截取key2的值：" + jedis.getrange("key2", 2, 4));
			
			jedis.close();

		  
		} finally {
		  // You have to close jedis object. If you don't close then
		  // it doesn't release back to pool and you can't get a new
		  // resource from pool.
		  if (jedis != null) {
		    jedis.close();
		  }
		}
		/// ... when closing your application:
		pool.close();
	}

}

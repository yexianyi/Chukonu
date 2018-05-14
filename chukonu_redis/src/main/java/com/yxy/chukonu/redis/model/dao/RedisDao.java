package com.yxy.chukonu.redis.model.dao;

import java.util.HashSet;
import java.util.Set;

import com.yxy.chukonu.redis.util.SerializeUtil;

import redis.clients.jedis.Jedis;

public class RedisDao extends BaseDao {

	protected Jedis jedis;

	public RedisDao() {
		super();
	}

	public void clearDB() {
		try {
			jedis = getResource();
			jedis.flushDB();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void insertSet(byte[] key, Object object) {
		try {
			jedis = getResource();
			jedis.sadd(key, SerializeUtil.serialize(object));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Set<?> getSet(byte[] key) {
		try {
			jedis = getResource();
			Set<byte[]> bytes = jedis.smembers(key);
			Set<Object> result = new HashSet<Object>() ;
			for(byte[] r:bytes) {
				result.add(SerializeUtil.unserialize(r)) ;
			}
			return result;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	public void insertSet(String key, String val) {
		try {
			jedis = getResource();
			jedis.sadd(key, val);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Set<?> getSet(String key) {
		try {
			jedis = getResource();
			return jedis.smembers(key.getBytes());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	
	public void insertSet(byte[] key, Set set) {
		try {
			jedis = getResource();
			for(Object obj:set) {
				jedis.sadd(key, SerializeUtil.serialize(obj));
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	
	public void insertSortedSet(String key, double score, String val) {
		try {
			jedis = getResource();
			jedis.zadd(key, score, val) ;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	public Set<String> getSortedSet(String key) {
		try {
			jedis = getResource();
			return jedis.zrange(key, 0, -1) ;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void insertMap(String key, String val) {
		try {
			jedis = getResource();
			jedis.sadd(key, val);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

}

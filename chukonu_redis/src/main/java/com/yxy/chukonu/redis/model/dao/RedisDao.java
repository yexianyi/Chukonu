package com.yxy.chukonu.redis.model.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.yxy.chukonu.redis.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

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
	
	
	public void saveUpdateHashMap(String key, String hashKey, String hashValue) {
		try {
			jedis = getResource();
			Map<String, String> map =new HashMap<String, String>() ;
			map.put(hashKey, hashValue) ;
			jedis.hmset(key, map) ;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Map<String, Map<String, String>> getHashMaps(String key) {
		try {
			jedis = getResource();
			Set<String> keys = jedis.keys(key+"*") ;
			if(keys.size()>0) {
				Map<String, Map<String, String>> resMap = new HashMap<String, Map<String, String>>() ;
				for(String k:keys) {
					resMap.put(k, jedis.hgetAll(k))  ;
				}
				return resMap ;
			}
			
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		
		return null ;
	}
	
	public Map<String, String> getHashMap(String key) {
		try {
			jedis = getResource();
			Map<String, String> resMap = jedis.hgetAll(key) ;
			return resMap.size()==0 ? null : resMap ;
			
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}
	
	
	public String getHashMapValue(String key, String fieldKey) {
		try {
			jedis = getResource();
			return jedis.hget(key, fieldKey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}
	
	
	
	public Set<String> getHashMapKeys(String keyPattern) {
		try {
			jedis = getResource();
			Set<String> keys = jedis.keys(keyPattern) ;
			return keys ;
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
	
	
	public Set<Tuple> getSortedSetWithScore(String key) {
		try {
			jedis = getResource();
			return jedis.zrangeWithScores(key, 0, -1) ;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Set<String> getSortedSet(String key, int from, int to) {
		try {
			jedis = getResource();
			return jedis.zrange(key, from, to) ;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Set<String> popSortedSet(String key, int from, int to) {
		try {
			jedis = getResource();
			Set<String> res = jedis.zrange(key, from, to) ;
			jedis.zremrangeByRank(key, from, to) ;
			return res ;
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

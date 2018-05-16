package com.yxy.chukonu.redis.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.yxy.chukonu.redis.model.dao.RedisDao;

import redis.clients.jedis.Tuple;

public class RedisDaoTest {

	private RedisDao dao ;
	
	@Before
	public void before() {
		dao = new RedisDao() ;
		dao.clearDB();
	}
	
	public void after() {
	}
	
	
	@Test
	public void testInsertHashMaps() {
		dao.saveUpdateHashMap("NodeServer_192.168.99.101", "name", "vm1");
		dao.saveUpdateHashMap("NodeServer_192.168.99.101", "age", "12");
	}
	
	@Test
	public void testGetHashMaps() {
		dao.saveUpdateHashMap("NodeServer_192.168.99.101", "name", "vm1");
		dao.saveUpdateHashMap("NodeServer_192.168.99.101", "age", "12");
		dao.saveUpdateHashMap("NodeServer_192.168.99.102", "name", "vm2");
		dao.saveUpdateHashMap("NodeServer_192.168.99.103", "age", "20");
		Map<String, Map<String, String>> map = dao.getHashMaps("NodeServer_") ;

		Iterator<Map.Entry<String, Map<String, String>>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Map<String, String>> entry = entries.next();
			System.out.println(entry.getKey());
			Map<String, String> m = entry.getValue() ;
			Iterator<Map.Entry<String, String>> items = m.entrySet().iterator();
			while (items.hasNext()) {
				Map.Entry<String, String> item = items.next();
				System.out.println("	Key = " + item.getKey() + ", Value = " + item.getValue());
			}
		}
		
	}
	
	
	@Test
	public void testGetHashMap() {
		dao.saveUpdateHashMap("NodeServer_192.168.99.101", "name", "vm1");
		Map<String, String> map = dao.getHashMap("NodeServer_192.168.99.101");
		assertNotNull(map) ;
		Map<String, String> map2 = dao.getHashMap("NodeServer_192.168.99.102");
		assertNull(map2) ;
	}
	
	@Test
	public void testGetHashMapAllKeys() {
		dao.saveUpdateHashMap("NodeServer_192.168.99.101", "name", "vm1");
		dao.saveUpdateHashMap("NodeServer_192.168.99.101", "age", "12");
		dao.saveUpdateHashMap("NodeServer_192.168.99.102", "name", "vm2");
		dao.saveUpdateHashMap("NodeServer_192.168.99.103", "age", "20");
		Set<String> set = dao.getHashMapKeys("NodeServer_*") ;
		assertEquals(3, set.size()) ;
		
	}
	
	
	
	@Test
	public void testInsertStringToSet() {
		dao.insertSet("servers", "192.168.99.101");
		dao.insertSet("servers", "192.168.99.102");
		dao.insertSet("servers", "192.168.99.103");
		assertEquals(3, dao.getSet("servers").size()) ;
	}
	
	@Test
	public void testInsertEntityToSet() {
		Entity entity1 = new Entity("server1", "normal");
		Entity entity2 = new Entity("server2", "abnormal");

		dao.insertSet("servers".getBytes(), entity1);
		dao.insertSet("servers".getBytes(), entity2);
		assertEquals(2, dao.getSet("servers").size());

//		Set<Entity> servers = (Set<Entity>) dao.getSet("servers".getBytes());
//		for (Entity s : servers) {
//			System.out.println(s.getName() + " | " + s.getStatus());
//		}
		
	}
	
	
	@Test
	public void testInsertSetToSet() {
		Entity entity1 = new Entity("server1", "normal");
		Entity entity2 = new Entity("server2", "abnormal");

		Set<Entity> entities = new HashSet<Entity>();
		entities.add(entity1);
		entities.add(entity2);

		dao.insertSet("servers".getBytes(), entities);
		assertEquals(2, dao.getSet("servers".getBytes()).size());

//		Set<Entity> servers = (Set<Entity>) dao.getSet("servers".getBytes());
//		for (Entity s : servers) {
//			System.out.println(s.getName() + " | " + s.getStatus());
//		}
		
	}
	
	@Test
	public void testInsertSortedSet() {
		dao.insertSortedSet("mydatasource", 0.46, "192.168.99.101");
		dao.insertSortedSet("mydatasource", 0.32, "192.168.99.102");
		dao.insertSortedSet("mydatasource", 0.38, "192.168.99.103");
		assertEquals(3, dao.getSortedSet("mydatasource").size());
		
		Set<String> res1 = dao.getSortedSet("mydatasource", 0, 0) ;
		assertEquals(1, res1.size());
		assertEquals("192.168.99.102", res1.iterator().next()) ;
		
		Set<String> res2 = dao.getSortedSet("mydatasource", 1, 1) ;
		assertEquals(1, res2.size());
		assertEquals("192.168.99.103", res2.iterator().next()) ;
		
		Set<String> res3 = dao.getSortedSet("mydatasource", 2, 2) ;
		assertEquals(1, res3.size());
		assertEquals("192.168.99.101", res3.iterator().next()) ;
	}
	
	
	@Test
	public void testGetFromSortedSet() {
		dao.insertSortedSet("mydatasource", 0.46, "192.168.99.101");
		dao.insertSortedSet("mydatasource", 0.32, "192.168.99.102");
		dao.insertSortedSet("mydatasource", 0.38, "192.168.99.103");
		
		Set<String> res1 = dao.getSortedSet("mydatasource", 0, 0) ;
		assertEquals(1, res1.size());
		assertEquals("192.168.99.102", res1.iterator().next()) ;
		
		Set<String> res2 = dao.getSortedSet("mydatasource", 1, 1) ;
		assertEquals(1, res2.size());
		assertEquals("192.168.99.103", res2.iterator().next()) ;
		
		Set<String> res3 = dao.getSortedSet("mydatasource", 2, 2) ;
		assertEquals(1, res3.size());
		assertEquals("192.168.99.101", res3.iterator().next()) ;
	}
	
	
	@Test
	public void testPopSortedSet() {
		dao.insertSortedSet("mydatasource", 0.46, "192.168.99.101");
		dao.insertSortedSet("mydatasource", 0.32, "192.168.99.102");
		dao.insertSortedSet("mydatasource", 0.38, "192.168.99.103");
		
		Set<String> res1 = dao.popSortedSet("mydatasource", 1, 1) ;
		assertEquals(1, res1.size());
		assertEquals("192.168.99.103", res1.iterator().next()) ;
		
		Set<String> res2 = dao.getSortedSet("mydatasource", 0, -1) ;
		assertEquals(2, res2.size());
		

	}
	
	
	@Test
	public void getSortedSetWithScore() {
		dao.insertSortedSet("mydatasource", 0.46, "192.168.99.101");
		dao.insertSortedSet("mydatasource", 0.32, "192.168.99.102");
		dao.insertSortedSet("mydatasource", 0.38, "192.168.99.103");
		
		Set<Tuple> set = dao.getSortedSetWithScore("mydatasource") ;
		assertEquals(3, set.size());
		assertEquals("192.168.99.102", set.iterator().next().getElement());
		assertEquals(0.32d, set.iterator().next().getScore(), 0.0);
		

	}
	
	
}

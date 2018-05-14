package com.yxy.chukonu.redis.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.yxy.chukonu.redis.model.dao.RedisDao;

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

		Set<Entity> servers = (Set<Entity>) dao.getSet("servers".getBytes());
		for (Entity s : servers) {
			System.out.println(s.getName() + " | " + s.getStatus());
		}
		
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

		Set<Entity> servers = (Set<Entity>) dao.getSet("servers".getBytes());
		for (Entity s : servers) {
			System.out.println(s.getName() + " | " + s.getStatus());
		}
		
	}
	
	@Test
	public void testInsertSortedSet() {
		dao.insertSortedSet("mydatasource", 0.46, "192.168.99.101");
		dao.insertSortedSet("mydatasource", 0.32, "192.168.99.102");
		dao.insertSortedSet("mydatasource", 0.38, "192.168.99.103");
		assertEquals(3, dao.getSortedSet("mydatasource").size());

		Set<String> sose = dao.getSortedSet("mydatasource");
		for (String s : sose) {
			System.out.println(s);
		}
	}
	
	
	
}

package com.yxy.chukonu.test.dao;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.constants.Operation;
import com.yxy.chukonu.mybatis.mapper.record.RecordMapper;
import com.yxy.chukonu.mybatis.util.TimeUtil;
import com.yxy.chukonu.mybatis.util.UUIDUtil;

public class RecordTestByMapperScanner {
	
	private RecordMapper mapper ;

	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		mapper = ctx.getBean(RecordMapper.class) ;
	}
	
	@Test
	public void testCreateLog() {
		mapper.createLog(UUIDUtil.next(), Operation.TX_COMMIT.toString(), "test", TimeUtil.currentTime());
	}
}

package com.yxy.chukonu.test.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)  
@Suite.SuiteClasses({	AccountTestByMapperFactoryBean.class,
						AccountTestByMapperScanner.class,
						AccountTestBySqlSessionTemplate.class,
						CustomerTestByMapperFactoryBean.class,
						CustomerTestByMapperScanner.class,
						CustomerTestBySqlSessionTemplate.class})  

public class TestAll {

}

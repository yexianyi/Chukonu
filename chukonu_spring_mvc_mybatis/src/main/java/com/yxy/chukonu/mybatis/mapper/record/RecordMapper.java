package com.yxy.chukonu.mybatis.mapper.record;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordMapper {

	public void createLog(@Param(value="uuid") String uuid, @Param(value="operation") String operation, @Param(value="content") String content, @Param(value="updateTime") Timestamp updateTime) ;
	
}

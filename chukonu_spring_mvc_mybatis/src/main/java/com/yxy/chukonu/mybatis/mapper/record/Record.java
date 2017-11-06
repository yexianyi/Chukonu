package com.yxy.chukonu.mybatis.mapper.record;

import java.sql.Timestamp;

import lombok.Data;

/**
 CREATE TABLE public."LOGS"
(
    "ID" character varying(36) COLLATE pg_catalog."default" NOT NULL,
    "OPERATION" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "CONTENT" character varying COLLATE pg_catalog."default" NOT NULL,
    "UPDATE_TIME" timestamp without time zone NOT NULL,
    CONSTRAINT "LOGS_pkey" PRIMARY KEY ("ID")
)

 * @author xianyiye
 *
 */

public class Record {
	private String id ;
	
	private String operation;
	
	private String content;
	
	private Timestamp updateTime ;
	
}

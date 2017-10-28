package com.yxy.chukonu.mybatis.mapper.customer;

import lombok.Data;

/**
-- Table: public."CUSTOMERS"

-- DROP TABLE public."CUSTOMERS";

CREATE TABLE public."CUSTOMERS"
(
    "ID" character varying(36) COLLATE pg_catalog."default" NOT NULL,
    "NAME" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "IDENTIFICATION" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "CUSTOMERS_pkey" PRIMARY KEY ("ID")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."CUSTOMERS"
    OWNER to postgres; 

INSERT INTO "CUSTOMERS" VALUES ('652300f0-2a8b-4728-8704-3968a8819a5a', 'aaa', 'idenfication_aaa');
INSERT INTO "CUSTOMERS" VALUES ('e92c3808-bea4-4fc3-9877-c71c31c5709d', 'bbb', 'identification_bbb');


 * @author xianyiye
 *
 */

@Data
public class Customer {
	
	//attribute name should be same with the table column name. Otherwise, additional config must be applied.
	private String id ;
	
	private String name ;
	
//	private String user_idenfication ;

	
	
	

}

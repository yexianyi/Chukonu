/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.mybatis.mapper.account;

import lombok.Data;
/**
 * 
-- Table: public."ACCOUNTS"

-- DROP TABLE public."ACCOUNTS";

CREATE TABLE public."ACCOUNTS"
(
    "ID" character varying(36) COLLATE pg_catalog."default" NOT NULL,
    "ACC_NUMBER" character varying(16) COLLATE pg_catalog."default" NOT NULL,
    "ACC_BALANCE" numeric(38,2) NOT NULL,
    "ACC_CUS_REF_ID" character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "ACCOUNTS_pkey" PRIMARY KEY ("ID"),
    CONSTRAINT "ACC_CUS_FK" FOREIGN KEY ("ACC_CUS_REF_ID")
        REFERENCES public."CUSTOMERS" ("ID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."ACCOUNTS"
    OWNER to postgres;

-- Index: fki_ACC_CUS_FK

-- DROP INDEX public."fki_ACC_CUS_FK";

CREATE INDEX "fki_ACC_CUS_FK"
    ON public."ACCOUNTS" USING btree
    ("ACC_CUS_REF_ID" COLLATE pg_catalog."default")
    TABLESPACE pg_default;

INSERT INTO "ACCOUNTS" VALUES ('aa889216-2dec-437b-87b0-1272d12a80c8', '1000100010001000', 200.00, '652300f0-2a8b-4728-8704-3968a8819a5a');
INSERT INTO "ACCOUNTS" VALUES ('89ab5a34-0e07-43e2-a5e6-8b0ad8446a26', '2000200020002000', 100.00, 'e92c3808-bea4-4fc3-9877-c71c31c5709d');


 * @author xianyiye
 *
 */

@Data
public class Account {

	private String id ;
	
	private String acc_number ;
	
	private float acc_balance ;
	
	private String acc_cus_ref_id ;
	
}

package net.yxy.chukonu.spring.security.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * CREATE TABLE `MEDICAL_RECORDS` ( `ID` bigint(20) NOT NULL AUTO_INCREMENT, `REC_ID` varchar(45) DEFAULT NULL,
 * `DEPARTMENT` varchar(20) DEFAULT NULL, `TYPE` varchar(10) DEFAULT NULL, `AGE` varchar(10) DEFAULT NULL, `GENDER`
 * varchar(10) DEFAULT NULL, `CHIEF_COMPLAINT` text, `CURR_HISTORY` text, `BODY_EXAM` text, `DIAG_EXAM` text,
 * `PERSONAL_HISTORY` text, `PAST_HISTORY` text, `FINAL_DIAG` text, `UPDATE_TIME` datetime DEFAULT NULL, PRIMARY KEY
 * (`ID`), UNIQUE KEY `ID_UNIQUE` (`ID`), UNIQUE KEY `REC_ID_UNIQUE` (`REC_ID`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 * 
 * @author yexianyi
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    Long id;
    String name;
    String type; 
    Float price;
    Integer quota;
    String updateBy ;
    Date updateTime;

}

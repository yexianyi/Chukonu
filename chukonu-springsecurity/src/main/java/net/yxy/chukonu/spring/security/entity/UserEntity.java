package net.yxy.chukonu.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
  CREATE TABLE `USERS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL DEFAULT '',
  `PASSWORD` varchar(64) NOT NULL,
  `ROLE` varchar(10) NOT NULL DEFAULT 'viewer',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `USERNAME_UNIQUE` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 * @author yexianyi
 *
 */
@Data
@AllArgsConstructor
public class UserEntity {
 
    private Long id;
    private String username;
    private String password;
    private String role ;
 
}
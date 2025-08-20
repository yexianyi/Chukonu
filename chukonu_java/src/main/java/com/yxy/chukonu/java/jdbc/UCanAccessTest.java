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
package com.yxy.chukonu.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UCanAccessTest {

	public static void main(String[] args) throws Exception {
		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver"); /* often not required for Java 6 and later (JDBC 4.x) */
//		 Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src/main/resources/test.accdb","", "");
		 Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src/main/resources/test.accdb;jackcessOpener=com.yxy.chukonu.java.jdbc.JackcessWithCharsetUTF8Opener","", "");
		 
		 Statement stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT RESUME FROM HUMANRESOURCES_JOBCANDIDATE WHERE JOBCANDIDATEID = 1";
	      ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	         String content = rs.getString("RESUME");

	         //Display values
	         System.out.println(content);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();

	}

}

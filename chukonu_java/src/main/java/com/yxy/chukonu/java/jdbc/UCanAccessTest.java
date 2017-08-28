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

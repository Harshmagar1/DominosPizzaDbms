package com.psl.bean;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	
	
	public static Connection con;

	public static Connection getConn() {

		try {
			
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dominos", "root", "harshal");
		//	System.out.println(con);

		} catch (Exception e) {
		e.printStackTrace();
		}

		return con;
	}
}

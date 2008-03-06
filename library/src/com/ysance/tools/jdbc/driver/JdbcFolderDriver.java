package com.ysance.tools.jdbc.driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcFolderDriver implements Driver {
	
	static {
		try {
			java.sql.DriverManager.registerDriver(new JdbcFolderDriver());
			
			Class.forName("com.ysance.tools.jdbc.driver.JdbcFolderDriver");	
		} catch (Exception ex) {
			ex.printStackTrace();			
		}
	}

	private final String DUMMY_URL = "jdbc:folder";
	
	public static final int DRIVER_MAJOR_VERSION = 0;
	public static final int DRIVER_MINOR_VERSION = 1;

	public static final String DRIVER_NAME = "JdbcFolder";
	
	
	public boolean acceptsURL(String aUrl) throws SQLException {
		//  System.out.println("JdbcFolderDriver.acceptsURL");		
		return DUMMY_URL.equals(aUrl);
	}

	public Connection connect(String aUrl, Properties aProprietes) throws SQLException {
		//  System.out.println("JdbcFolderDriver.connect");
		return new JdbcFolderConnection(aUrl, aProprietes);
	}

	public int getMajorVersion() {
		return DRIVER_MAJOR_VERSION;
	}

	public int getMinorVersion() {
		return DRIVER_MINOR_VERSION;
	}

	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		//  System.out.println("JdbcFolderDriver.getPropertyInfo");
		return null;
	}

	public boolean jdbcCompliant() {
		//  System.out.println("JdbcFolderDriver.jdbcCompliant");
		return true;
	}

	
	 
}

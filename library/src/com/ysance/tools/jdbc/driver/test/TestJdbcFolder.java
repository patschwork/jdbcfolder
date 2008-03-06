package com.ysance.tools.jdbc.driver.test;

/**
 * todo :
 *   Récupérer un parser SQL
 */

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ysance.tools.jdbc.driver.JdbcFolderStatement;

public class TestJdbcFolder {
	/**
	 * @param args
	 */
  public static void main(String[] args) {
  	testPreparedStatement();
    
    /*System.setOut(TestJdbcFolder.out);
    System.setErr(TestJdbcFolder.err);
    console.close();
    */
  }

	private static void testCreateStatement() {
		try {
		  // redirect console I/O to specified file.
		  /*TestJdbcFolder.out = System.out;
		  TestJdbcFolder.err = System.err;
		  console = new PrintStream(new FileOutputStream(LOG_PATH, true));
		  System.setOut(console);
		  System.setErr(console);*/    	
			
		  Driver instanceDriver = new com.ysance.tools.jdbc.driver.JdbcFolderDriver();
		  DriverManager.registerDriver(instanceDriver);
			
		  Class.forName("com.ysance.tools.jdbc.driver.JdbcFolderDriver");	
		  String url = "jdbc:folder";	
		  Connection con = DriverManager.getConnection(url);	
		  String query = JdbcFolderStatement.DUMMY_REQUEST;
		  ResultSet results;
		  Statement stmt = con.createStatement();
		  results = stmt.executeQuery(query);	  
		  System.out.println("TestJdbcFolder.main()");
		  while( results.next() ) { // Point result set to next row
		    System.out.println("results.getString(filename) "+results.getString("filename"));
		    System.out.println("results.getString(0) "+results.getString(0));
		    System.out.println("results.getString(filename_2) "+results.getString("filename_2"));
		    System.out.println(" ");
		  }	  
		} catch(Exception e) {
		  e.printStackTrace();
		}
	}
	
	private static void testPreparedStatement() {
		try {
		  // redirect console I/O to specified file.
		  /*TestJdbcFolder.out = System.out;
		  TestJdbcFolder.err = System.err;
		  console = new PrintStream(new FileOutputStream(LOG_PATH, true));
		  System.setOut(console);
		  System.setErr(console);*/    	
			
		  Driver instanceDriver = new com.ysance.tools.jdbc.driver.JdbcFolderDriver();
		  DriverManager.registerDriver(instanceDriver);
			
		  Class.forName("com.ysance.tools.jdbc.driver.JdbcFolderDriver");	
		  String url = "jdbc:folder";	
		  Connection con = DriverManager.getConnection(url);
		  DatabaseMetaData conMetaData = con.getMetaData(); 
		  //String query = JdbcFolderStatement.DUMMY_REQUEST;
		  String query = " select filename, size,extension, filename, size,extension, filename, size,extension from /temp where extension = 'pdf' ";
		  PreparedStatement stmt = con.prepareStatement(query);
		  stmt.setFetchSize(10);
		  ResultSet results = stmt.executeQuery();
		  //results = stmt.executeQuery(query);	  
		  while( results.next() ) { // Point result set to next row
		    System.out.println("results.getString(filename) "+results.getString("filename"));
		    //System.out.println("results.getString(1) "+results.getString(1));
		    System.out.println("results.getLong(size) "+results.getLong("size"));
		    System.out.println("results.getString(extension) "+results.getString("extension"));
		    System.out.println("results.getString(filename_2) "+results.getString("filename_2"));
		    //System.out.println("results.getString(1) "+results.getString(1));
		    System.out.println("results.getLong(size_2) "+results.getLong("size_2"));
		    System.out.println("results.getString(extension_2) "+results.getString("extension_2"));
		    System.out.println(" ");
		  }	  
		} catch(Exception e) {
		  e.printStackTrace();
		}
		/*
			JdbcFolderDriver.acceptsURL
			JdbcFolderDriver.connect
			JdbcFolderConnection.JdbcFolderConnection()
			JdbcFolderConnection.getMetaData()
			JdbcFolderDatabaseMetadata.supportsTransactions()
			JdbcFolderConnection.getTransactionIsolation()
			JdbcFolderConnection.getMetaData()
			JdbcFolderConnection.prepareStatement(String sql)
			JdbcFolderConnection.prepareStatement(String sql, int resultSe
			JdbcFolderStatement.setFetchSize()
			JdbcFolderStatement.executeQuery()
			JdbcFolderStatement.executeQuery()
			JdbcFolderResultSet.getMetaData()
			Youpi
			JdbcFolderResultSet.getMetaData()
			FolderResultSetMetaData.getColumnType() 1 12
			FolderResultSetMetaData.getColumnName() 1 FILENAME
			FolderResultSetMetaData.getPrecision() 1 255
			FolderResultSetMetaData.getScale() 1 255
			FolderResultSetMetaData.getColumnDisplaySize() 1 255
			JdbcFolderConnection.getMetaData()
			JdbcFolderAbstractResultSet.close()
			JdbcFolderStatement.close()
		 */
	}	
}




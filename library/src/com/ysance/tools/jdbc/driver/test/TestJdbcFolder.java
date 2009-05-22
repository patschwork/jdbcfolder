package com.ysance.tools.jdbc.driver.test;

/**
 * todo 
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
import java.util.logging.*;

import com.ysance.tools.jdbc.driver.JdbcFolderStatement;

public class TestJdbcFolder {
	/**
	 * @todo DriverManager.setLogWriter pour debuggage driver
	 * 
	 * @param args
	 */
  public static void main(String[] args) {
		  
  	testPreparedStatement();

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
		  logger.info("TestJdbcFolder.main()");
		  while( results.next() ) { // Point result set to next row
		    logger.info("results.getString(filename) "+results.getString("filename"));
		    logger.info("results.getString(0) "+results.getString(0));
		    logger.info("results.getString(filename_2) "+results.getString("filename_2"));
		    logger.info(" ");
		  }	  
		} catch(Exception e) {
		  e.printStackTrace();
		}
	}

    private static Logger logger = Logger.getLogger("com.ysance.tools.jdbc.driver.test.TestJdbcFolder");
    private static ConsoleHandler ch = new ConsoleHandler();
	
	private static void testPreparedStatement() {
        // Send logger output to our FileHandler.
        logger.addHandler(ch);
        // Request that every detail gets logged.
        logger.setLevel(Level.ALL);
        // Log a simple INFO message.
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

		  int numeroRequete = -1;
		  //String query = " select  filename + 1 as toto ,  size + 1 ,extension ,filename, size,extension, filename, size,extension from /temp where extension <> 'log'"; numeroRequete= 1;  		  
		  //String query = " select  filename + 'E' from /temp ";  		   numeroRequete= 2;
		  //String query = " select  filename + 'E' as toto ,  size + 1 ,extension ,filename, size,extension, filename, size,extension from /temp where extension <> 'log'";  numeroRequete= 3;  		  
		  //String query = " select * from /temp";   numeroRequete= 4;
		  String query = " select  filename.toUpperCase() + size + 1 from /temp ";  		   numeroRequete= 2;

		  
		  PreparedStatement stmt = con.prepareStatement(query);
		  stmt.setFetchSize(10);
		  
		  ResultSet results = stmt.executeQuery();
		  
		  System.out.println(stmt.toString());
		  
		  //results = stmt.executeQuery(query);	  
		  logger.info(" ****************************  récup valeurs resultset ");		  
		  while( results.next() ) { // Point result set to next row
			switch (numeroRequete) {
			case 1: System.out.println("results.getString(filename) "+results.getString("filename") +	" getString(1) "+results.getString(1) +	" getString(2) "+results.getString(2)+	" getDouble(2) "+results.getDouble(2) + " getString(size) "+results.getString("size") +	" getString(filename) "+results.getString("filename")			  ); break;
			case 2:	System.out.println(" getString(1) "+results.getString(1) );	break;
			case 3: System.out.println(" getString(1) "+results.getString(1) + " getString(2) "+results.getString(2) ); break;
			case 4: 
				for (int i = 0; i < results.getMetaData().getColumnCount(); i++) {
					System.out.print(" getString("+i+") "+results.getString(i + 1)  );					
				}				
				System.out.println(" "  ); break;
			default:
				break;
			}
		  }	  
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
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




package com.ysance.tools.jdbc.driver;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
//JSE 6 import java.sql.NClob;
import java.sql.PreparedStatement;
//JSE 6 import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
//JSE 6 import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import com.ysance.tools.jdbc.driver.metadata.JdbcFolderDatabaseMetadata;

public class JdbcFolderConnection implements Connection {
	
	private boolean opened = false;
	private boolean autocommit = false;
	
	Properties proprietes;

	public JdbcFolderConnection(String aUrl, Properties aProprietes) {
	 //  //  System.out.println("JdbcFolderConnection.JdbcFolderConnection()");
	  this.proprietes = new Properties(aProprietes);
	  this.opened = true;
	}
	
	public void clearWarnings() throws SQLException {
	 //  //  System.out.println("JdbcFolderConnection.clearWarnings");
	}

	public void close() throws SQLException {
	 //  //  System.out.println("JdbcFolderConnection.close()");
	  this.opened = false;
	}

	public void commit() throws SQLException {
	 //  //  System.out.println("JdbcFolderConnection.commit");
	}

	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
	 //  //  System.out.println("JdbcFolderConnection.createArrayOf");
  	  return null;
	}

	public Blob createBlob() throws SQLException {
	 //  //  System.out.println("JdbcFolderConnection.createBlob");
	  return null;
	}

	public Clob createClob() throws SQLException {
	 //  //  System.out.println("JdbcFolderConnection.createClob");
  	  return null;
	}
//	JSE 6 
	/*
	public NClob createNClob() throws SQLException {
		//  System.out.println("JdbcFolderConnection.createNClob");
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		//  System.out.println("JdbcFolderConnection.createSQLXML()");
		return null;
	}
*/
	public Statement createStatement() throws SQLException {
		//  System.out.println("JdbcFolderConnection.createStatement()");
		return createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)  throws SQLException {
		//  System.out.println("JdbcFolderConnection.createStatement(int resultSetType, int resultSetConcurrency) ");
		return createStatement(resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		//  System.out.println("JdbcFolderConnection.createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) ");
		return new JdbcFolderStatement();
	}

	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		//  System.out.println("JdbcFolderConnection.createStruct()");
		return null;
	}

	public boolean getAutoCommit() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getAutoCommit()");
		return autocommit;
	}

	public String getCatalog() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getCatalog()");
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getClientInfo()");
		return null;
	}

	public String getClientInfo(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.getClientInfo()");
		return null;
	}

	public int getHoldability() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getHoldability()");
		return 0;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getMetaData()");
		return new JdbcFolderDatabaseMetadata(this);
	}

	public int getTransactionIsolation() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getTransactionIsolation()");
		return Connection.TRANSACTION_NONE;
	}

	public Map getTypeMap() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getTypeMap()");
		return null;
	}

	public SQLWarning getWarnings() throws SQLException {
		//  System.out.println("JdbcFolderConnection.getWarnings()");
		return null;
	}

	public boolean isClosed() throws SQLException {
		//  System.out.println("JdbcFolderConnection.isClosed()");
		return this.opened;
	}

	public boolean isReadOnly() throws SQLException {
		//  System.out.println("JdbcFolderConnection.isReadOnly()");
		return true;
	}

	public boolean isValid(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.isValid()");
		return false;
	}

	public String nativeSQL(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.nativeSQL()");
		return "nativeSQL";
	}

	public CallableStatement prepareCall(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareCall()");
		return null;
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2)throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareCall()");
		return null;
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareCall()");
		return null;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareStatement(String sql)");
		return prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareStatement(String sql, int autoGeneratedKeys)");
		return prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)	throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareStatement(String sql, int[] columnIndexes)");
		return prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareStatement(String sql, String[] columnNames)");
		return prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)	throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareStatement((String sql, int resultSetType, int resultSetConcurrency)");
		return prepareStatement(sql, resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		//  System.out.println("JdbcFolderConnection.prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)");
		return  new JdbcFolderStatement(sql);
	}

	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.releaseSavepoint()");

	}

	public void rollback() throws SQLException {
		//  System.out.println("JdbcFolderConnection.rollback()");

	}

	public void rollback(Savepoint arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.rollback()");

	}

	/**
	 * chage the autocommit value
	 * @param newAutocommit : a new value for autocommit
	 */
	public void setAutoCommit(boolean newAutocommit) throws SQLException {
		//  System.out.println("JdbcFolderConnection.setAutoCommit()");
		this.autocommit = newAutocommit;
	}

	public void setCatalog(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.setCatalog()");

	}

//	JSE 6 
/*
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		//  System.out.println("JdbcFolderConnection.setClientInfo()");

	}

	public void setClientInfo(String arg0, String arg1)	throws SQLClientInfoException {
		//  System.out.println("JdbcFolderConnection.setClientInfo()");

	}
*/

	public void setHoldability(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.setHoldability()");

	}

	public void setReadOnly(boolean arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.setReadOnly()");

	}

	public Savepoint setSavepoint() throws SQLException {
		//  System.out.println("JdbcFolderConnection.setSavepoint()");
		return null;
	}

	public Savepoint setSavepoint(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.setSavepoint()");
		return null;
	}

	public void setTransactionIsolation(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.setTransactionIsolation()");

	}

	public void setTypeMap(Map arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.setTypeMap()");

	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.isWrapperFor()");
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		//  System.out.println("JdbcFolderConnection.unwrap()");
		return null;
	}
	
}

package com.ysance.tools.jdbc.driver.resultsets.row;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;


public class Row {

	protected HashMap fieldsData;
	protected ArrayList fieldsList;
	
	public Row() {
		fieldsData = new HashMap();
		fieldsList = new ArrayList();
	}

	public Row(java.sql.ResultSetMetaData aResultSetMetaData) throws SQLException {
		this();
		for (int indexChamps = 1; indexChamps <= aResultSetMetaData.getColumnCount(); indexChamps++) {
			fieldsList.add(aResultSetMetaData.getColumnLabel(indexChamps));
		}
		
	}
	
	public Object getData(String aFieldName) throws SQLException {
		if (fieldsList.contains(aFieldName)) {
			return fieldsData.get(aFieldName);
		} else {
			throw new JdbcFolderExceptions.FieldNotFoundException(aFieldName);			
		}
	}
	
	public void setData(String aFieldName, Object aData) throws SQLException {
		if (fieldsList.contains(aFieldName)) {
			fieldsData.put(aFieldName, aData);
			System.out.println("Classe donnée ligne "+aData.getClass().getName());
		} else {
			throw new JdbcFolderExceptions.FieldNotFoundException(aFieldName);			
		}
	}		
}

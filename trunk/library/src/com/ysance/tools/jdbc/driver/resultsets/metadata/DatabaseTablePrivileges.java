package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;

public class DatabaseTablePrivileges extends JdbcFolderAbstractResultSet {

	class TablePrivilege {
		
	}
	
	public DatabaseTablePrivileges() {
		super();
		this.tableauLignes = new TablePrivilege[0];
	}
	
}

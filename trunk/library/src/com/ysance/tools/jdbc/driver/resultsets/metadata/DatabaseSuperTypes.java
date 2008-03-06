package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;

public class DatabaseSuperTypes extends JdbcFolderAbstractResultSet {
	
	class SuperType {
		
	}
	
	public DatabaseSuperTypes() {
		super();
		this.tableauLignes = new SuperType[0];
	}

}

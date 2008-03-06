package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;

public class DatabaseSchemas extends JdbcFolderAbstractResultSet {
	
	class Schema {
		
	}
	
	public DatabaseSchemas() {
		super();
		this.tableauLignes = new Schema[0];
	}

}

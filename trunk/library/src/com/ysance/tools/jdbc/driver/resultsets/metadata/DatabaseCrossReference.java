package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;

public class DatabaseCrossReference extends JdbcFolderAbstractResultSet {
	
	class CrossReference {
		
	}

	/**
	 * 
	 */
	public DatabaseCrossReference() {
		super();
		this.tableauLignes = new CrossReference[0];
	}
	
	

}

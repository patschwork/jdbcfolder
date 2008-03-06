package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;

public class DatabaseBestRowIdentifier extends JdbcFolderAbstractResultSet {

	class BestRowIdentifier {
		
	}
	
	public DatabaseBestRowIdentifier() {
		super();
		this.tableauLignes = new BestRowIdentifier[0];
	}

}

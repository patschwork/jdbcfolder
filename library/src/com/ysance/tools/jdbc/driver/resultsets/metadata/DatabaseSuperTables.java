package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;
import com.ysance.tools.jdbc.driver.resultsets.metadata.DatabaseAttributes.DatabaseAttribute;

public class DatabaseSuperTables extends JdbcFolderAbstractResultSet {
	
	/**
	 *    1.  TABLE_CAT String => the type's catalog (may be null)
   2. TABLE_SCHEM String => type's schema (may be null)
   3. TABLE_NAME String => type name
   4. SUPERTABLE_NAME String => the direct super type's name 
	 * @author csebille
	 *
	 */
	class DatabaseSuperTable {

	}

	public DatabaseSuperTables() {
		super();
		this.tableauLignes = new java.util.ArrayList();	
	}
	
	

}

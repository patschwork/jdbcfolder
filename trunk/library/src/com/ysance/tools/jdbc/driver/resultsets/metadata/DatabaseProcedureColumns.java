package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;
/**
 * 
 * @author csebille
 *public ResultSet getProcedures(String catalog,
                               String schemaPattern,
                               String procedureNamePattern)
                        throws SQLException

    Retrieves a description of the stored procedures available in the given catalog.

    Only procedure descriptions matching the schema and procedure name criteria are returned. They are ordered by PROCEDURE_SCHEM and PROCEDURE_NAME.

    Each procedure description has the the following columns:

       1. PROCEDURE_CAT String => procedure catalog (may be null)
       2. PROCEDURE_SCHEM String => procedure schema (may be null)
       3. PROCEDURE_NAME String => procedure name
       4. reserved for future use
       5. reserved for future use
       6. reserved for future use
       7. REMARKS String => explanatory comment on the procedure
       8. PROCEDURE_TYPE short => kind of procedure:
              * procedureResultUnknown - May return a result
              * procedureNoResult - Does not return a result
              * procedureReturnsResult - Returns a result 

    Parameters:
        catalog - a catalog name; must match the catalog name as it is stored in the database; "" retrieves those without a catalog; null means that the catalog name should not be used to narrow the search
        schemaPattern - a schema name pattern; must match the schema name as it is stored in the database; "" retrieves those without a schema; null means that the schema name should not be used to narrow the search
        procedureNamePattern - a procedure name pattern; must match the procedure name as it is stored in the database 
    Returns:
        ResultSet - each row is a procedure description 
    Throws:
        SQLException - if a database access error occurs
    See Also:
        getSearchStringEscape()
 */


public class DatabaseProcedureColumns extends JdbcFolderAbstractResultSet {
	
	class ProcedureColumn {
		
	}

	/**
	 * 
	 */
	public DatabaseProcedureColumns() {
		super();
		this.tableauLignes = new java.util.ArrayList();
	}
	

}

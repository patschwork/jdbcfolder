package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;
/**
 * 
 * @author csebille
 *public ResultSet getProcedureColumns(String catalog,
                                     String schemaPattern,
                                     String procedureNamePattern,
                                     String columnNamePattern)
                              throws SQLException

    Retrieves a description of the given catalog's stored procedure parameter and result columns.

    Only descriptions matching the schema, procedure and parameter name criteria are returned. They are ordered by PROCEDURE_SCHEM and PROCEDURE_NAME. Within this, the return value, if any, is first. Next are the parameter descriptions in call order. The column descriptions follow in column number order.

    Each row in the ResultSet is a parameter description or column description with the following fields:

       1. PROCEDURE_CAT String => procedure catalog (may be null)
       2. PROCEDURE_SCHEM String => procedure schema (may be null)
       3. PROCEDURE_NAME String => procedure name
       4. COLUMN_NAME String => column/parameter name
       5. COLUMN_TYPE Short => kind of column/parameter:
              * procedureColumnUnknown - nobody knows
              * procedureColumnIn - IN parameter
              * procedureColumnInOut - INOUT parameter
              * procedureColumnOut - OUT parameter
              * procedureColumnReturn - procedure return value
              * procedureColumnResult - result column in ResultSet 
       6. DATA_TYPE int => SQL type from java.sql.Types
       7. TYPE_NAME String => SQL type name, for a UDT type the type name is fully qualified
       8. PRECISION int => precision
       9. LENGTH int => length in bytes of data
      10. SCALE short => scale
      11. RADIX short => radix
      12. NULLABLE short => can it contain NULL.
              * procedureNoNulls - does not allow NULL values
              * procedureNullable - allows NULL values
              * procedureNullableUnknown - nullability unknown 
      13. REMARKS String => comment describing parameter/column 

    Note: Some databases may not return the column descriptions for a procedure. Additional columns beyond REMARKS can be defined by the database.

    Parameters:
        catalog - a catalog name; must match the catalog name as it is stored in the database; "" retrieves those without a catalog; null means that the catalog name should not be used to narrow the search
        schemaPattern - a schema name pattern; must match the schema name as it is stored in the database; "" retrieves those without a schema; null means that the schema name should not be used to narrow the search
        procedureNamePattern - a procedure name pattern; must match the procedure name as it is stored in the database
        columnNamePattern - a column name pattern; must match the column name as it is stored in the database 
    Returns:
        ResultSet - each row describes a stored procedure parameter or column 
    Throws:
        SQLException - if a database access error occurs
    See Also:
        getSearchStringEscape()
 */


public class DatabaseProcedures extends JdbcFolderAbstractResultSet {
	
	class Procedure {
			
	}
	
	public DatabaseProcedures() {
		super();		
		this.tableauLignes = new java.util.ArrayList();
	}

}

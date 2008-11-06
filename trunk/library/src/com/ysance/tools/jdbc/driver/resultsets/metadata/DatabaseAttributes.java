package com.ysance.tools.jdbc.driver.resultsets.metadata;

import com.ysance.tools.jdbc.driver.resultsets.JdbcFolderAbstractResultSet;

public class DatabaseAttributes extends JdbcFolderAbstractResultSet {
	
	/**
	 * 
	 *    1.  TYPE_CAT String => type catalog (may be null)
   2. TYPE_SCHEM String => type schema (may be null)
   3. TYPE_NAME String => type name
   4. ATTR_NAME String => attribute name
   5. DATA_TYPE int => attribute type SQL type from java.sql.Types
   6. ATTR_TYPE_NAME String => Data source dependent type name. For a UDT, the type name is fully qualified. For a REF, the type name is fully qualified and represents the target type of the reference type.
   7. ATTR_SIZE int => column size. For char or date types this is the maximum number of characters; for numeric or decimal types this is precision.
   8. DECIMAL_DIGITS int => the number of fractional digits
   9. NUM_PREC_RADIX int => Radix (typically either 10 or 2)
  10. NULLABLE int => whether NULL is allowed
          * attributeNoNulls - might not allow NULL values
          * attributeNullable - definitely allows NULL values
          * attributeNullableUnknown - nullability unknown 
  11. REMARKS String => comment describing column (may be null)
  12. ATTR_DEF String => default value (may be null)
  13. SQL_DATA_TYPE int => unused
  14. SQL_DATETIME_SUB int => unused
  15. CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
  16. ORDINAL_POSITION int => index of column in table (starting at 1)
  17. IS_NULLABLE String => "NO" means column definitely does not allow NULL values; "YES" means the column might allow NULL values. An empty string means unknown.
  18. SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
  19. SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
  20. SCOPE_TABLE String => table name that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
  21. SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type,SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF) 
  @author csebille
	 *
	 */
	class DatabaseAttribute {
		
	}
	
	public DatabaseAttributes() {
		super();
		this.tableauLignes = new java.util.ArrayList();
	}

}

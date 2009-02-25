package com.ysance.tools.jdbc.driver.resultsets.metadata;

import java.util.HashMap;

/**
 * @author csebille
 *
 *  According to the following page http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html#1034737, the
 *  Class to SQL Type conversion is :
 *  
 *  Java Object Type		|		JDBC Type
 *  ------------------------+------------------------------------
 *  String                  |	CHAR, VARCHAR, or LONGVARCHAR
 *  java.math.BigDecimal    |	NUMERIC
 *  Boolean                 |	BIT
 *  Integer                 |	INTEGER
 *  Long                    |	BIGINT
 *  Float                   |	REAL
 *  Double                  |	DOUBLE
 *  byte[]                  |	BINARY, VARBINARY, or LONGVARBINARY
 *  java.sql.Date           |	DATE
 *  java.sql.Time           |	TIME
 *  java.sql.Timestamp      |	TIMESTAMP
 *  Clob                    |	CLOB
 *  Blob                    |	BLOB
 *  Array                   |	ARRAY
 *  Struct                  |	STRUCT
 *  Ref                     |	REF
 *  Java class              |	JAVA_OBJECT 
 *
 */

public class FieldMetadata {
	private static final HashMap mappingSQLTypes = new HashMap();
	
	static {
		 mappingSQLTypes.put("java.lang.String",new Integer(java.sql.Types.VARCHAR));
		 mappingSQLTypes.put("java.math.BigDecimal",new Integer(java.sql.Types.NUMERIC));
		 mappingSQLTypes.put("java.lang.Boolean",new Integer(java.sql.Types.BIT));
		 mappingSQLTypes.put("java.lang.Integer",new Integer(java.sql.Types.INTEGER));
		 mappingSQLTypes.put("java.lang.Long ",new Integer(java.sql.Types.BIGINT));
		 mappingSQLTypes.put("java.lang.Float",new Integer(java.sql.Types.REAL));
		 mappingSQLTypes.put("java.lang.Double",new Integer(java.sql.Types.DOUBLE));
		 mappingSQLTypes.put("java.sql.Date",new Integer(java.sql.Types.DATE));
		 mappingSQLTypes.put("java.sql.Time",new Integer(java.sql.Types.TIME));
		 mappingSQLTypes.put("java.sql.Timestamp",new Integer(java.sql.Types.TIMESTAMP));
		 mappingSQLTypes.put("Other",new Integer(java.sql.Types.JAVA_OBJECT ));	
	}
	
	public String  catalogName = "";
	public String  columnClassName = "";
	public int     columnDisplaySize = 0;
	public String  columnLabel = "";
	public String  columnName = "";
	public int     columnType = 0;
	public String  columnTypeName = "";
	public String  expression = "";
	public int     precision = 0;
	public int     scale = 0;
	public String  schemaName = "";
	public String  tableName = "";
	public boolean autoIncrement = false;
	public boolean caseSensitive = false;
	public boolean isCurrency = false;
	public boolean definitelyWritable = false;
	public int     nullable = 0;
	public boolean isReadOnly = false;
	public boolean isSearchable = false;
	public boolean isSigned = false;
	public boolean isWritable = false;
	
	public FieldMetadata(String  aCatalogName, String  aColumnClassName,
	int     aColumnDisplaySize,		String  aColumnLabel,		String  aColumnName,		String  aExpression,		int     aColumnType,
	String  aColumnTypeName,		int     aPrecision,		int     aScale,		String  aSchemaName,
	String  aTableName,		boolean aAutoIncrement,		boolean aCaseSensitive,		boolean aIsCurrency,
	boolean aDefinitelyWritable,		int     aNullable,		boolean aIsReadOnly,
	boolean aIsSearchable,		boolean aIsSigned,		boolean aIsWritable) {
		this.catalogName = aCatalogName;
		this.columnClassName = aColumnClassName;
		this.columnDisplaySize = aColumnDisplaySize;
		this.columnLabel = aColumnLabel;
		this.columnName = aColumnName;
		this.columnType = aColumnType;
		this.columnTypeName = aColumnTypeName;
		this.expression = aExpression;
		this.precision = aPrecision;
		this.scale = aScale;
		this.schemaName = aSchemaName;
		this.tableName = aTableName;
		this.autoIncrement = aAutoIncrement;
		this.caseSensitive = aCaseSensitive;
		this.isCurrency = aIsCurrency;
		this.definitelyWritable = aDefinitelyWritable;
		this.nullable = aNullable;
		this.isReadOnly = aIsReadOnly;
		this.isSearchable = aIsSearchable;
		this.isSigned = aIsSigned;
		this.isWritable = aIsWritable;
	}

	
	public Object clone() {
		return new FieldMetadata(	this.catalogName, 
										this.columnClassName,
										this.columnDisplaySize,
										this.columnLabel,
										this.columnName,
										this.expression,
										this.columnType,
										this.columnTypeName,
										this.precision,
										this.scale,
										this.schemaName,
										this.tableName,
										this.autoIncrement,
										this.caseSensitive,
										this.isCurrency,
										this.definitelyWritable,
										this.nullable,
										this.isReadOnly,
										this.isSearchable,	
										this.isSigned,	
										this.isWritable);
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}
	
	public void setColumnType(String aJavaClassName) {
		this.columnType = ((Integer)mappingSQLTypes.get(aJavaClassName)).intValue();
	}
}

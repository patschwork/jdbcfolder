package com.ysance.tools.jdbc.driver.resultsets.metadata;

public class FieldMetadata {
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
}

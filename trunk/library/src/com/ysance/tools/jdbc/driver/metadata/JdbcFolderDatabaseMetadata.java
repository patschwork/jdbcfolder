package com.ysance.tools.jdbc.driver.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
//JSE 6 import java.sql.RowIdLifetime;
import java.sql.SQLException;

import com.ysance.tools.jdbc.driver.JdbcFolderDriver;
import com.ysance.tools.jdbc.driver.resultsets.metadata.*;

public class JdbcFolderDatabaseMetadata implements DatabaseMetaData {
	
	private final String DATABASE_PRODUCT_NAME = "JdbcFolder";
	private final String CATALOG_SEPARATOR     = "/";
	private final String CATALOG_TERM          = "Folder";
	
	private java.sql.Connection connexionMere;
	
	public JdbcFolderDatabaseMetadata (java.sql.Connection aConnexionMere) {
		this.connexionMere = aConnexionMere;
	}

	public boolean allProceduresAreCallable() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.allProceduresAreCallable()");
		return false;
	}

	public boolean allTablesAreSelectable() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.allTablesAreSelectable()");
		return false;
	}

	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.autoCommitFailureClosesAllResultSets()");
		return false;
	}

	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.dataDefinitionCausesTransactionCommit()");
		return false;
	}

	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.dataDefinitionIgnoredInTransactions()");
		return false;
	}

	public boolean deletesAreDetected(int arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.deletesAreDetected()");
		return false;
	}

	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.doesMaxRowSizeIncludeBlobs()");
		return false;
	}

	public ResultSet getAttributes(String arg0, String arg1, String arg2,String arg3) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getAttributes()");
		return new DatabaseAttributes();
	}

	public ResultSet getBestRowIdentifier(String arg0, String arg1,	String arg2, int arg3, boolean arg4) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getBestRowIdentifier()");
		return new DatabaseBestRowIdentifier();
	}

	public String getCatalogSeparator() throws SQLException {
		return CATALOG_SEPARATOR;
	}

	public String getCatalogTerm() throws SQLException {
		return CATALOG_TERM;
	}

	public ResultSet getCatalogs() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getCatalogs()");
		return new DatabaseCatalogs();
	}

	public ResultSet getClientInfoProperties() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getClientInfoProperties()");
		return new DatabaseClientInfoProperties();
	}

	public ResultSet getColumnPrivileges(String arg0, String arg1, String arg2,
			String arg3) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getColumnPrivileges()");
		return new DatabaseColumnPrivileges();
	}

	public ResultSet getColumns(String arg0, String arg1, String arg2,String arg3) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getColumns()");
		return new DatabaseColumns();
	}

	public Connection getConnection() throws SQLException {
		return connexionMere;
	}

	public ResultSet getCrossReference(String arg0, String arg1, String arg2,String arg3, String arg4, String arg5) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getCrossReference()");
		return new DatabaseCrossReference();
	}

	public int getDatabaseMajorVersion() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getDatabaseMajorVersion()");
		return 0;
	}

	public int getDatabaseMinorVersion() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getDatabaseMinorVersion()");
		return 0;
	}

	public String getDatabaseProductName() throws SQLException {
		return DATABASE_PRODUCT_NAME;
	}

	public String getDatabaseProductVersion() throws SQLException {
		return this.getDriverVersion();
	}

	public int getDefaultTransactionIsolation() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getDefaultTransactionIsolation()");
		return 0;
	}

	public int getDriverMajorVersion() {
		return JdbcFolderDriver.DRIVER_MAJOR_VERSION;
	}

	public int getDriverMinorVersion() {
		return JdbcFolderDriver.DRIVER_MINOR_VERSION;
	}

	public String getDriverName() throws SQLException {
		return JdbcFolderDriver.DRIVER_NAME;
	}

	public String getDriverVersion() throws SQLException {
		return this.getDriverMajorVersion()+"."+this.getDriverMinorVersion();
	}

	public ResultSet getExportedKeys(String arg0, String arg1, String arg2)	throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getExportedKeys()");
		return new DatabaseExportedKeys() ;
	}

	public String getExtraNameCharacters() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getExtraNameCharacters()");
		return "getExtraNameCharacters";
	}

	public ResultSet getFunctionColumns(String arg0, String arg1, String arg2,String arg3) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getFunctionColumns()");
		return new DatabaseFunctionColumns() ;
	}

	public ResultSet getFunctions(String arg0, String arg1, String arg2)throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getFunctions()");
		return new DatabaseFunctions() ;
	}

	public String getIdentifierQuoteString() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getIdentifierQuoteString()");
		return "getIdentifierQuoteString";
	}

	public ResultSet getImportedKeys(String arg0, String arg1, String arg2)	throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getImportedKeys()");
		return new DatabaseImportedKeys() ;
	}

	public ResultSet getIndexInfo(String arg0, String arg1, String arg2,boolean arg3, boolean arg4) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getIndexInfo()");
		return new DatabaseIndexInfo() ;
	}

	public int getJDBCMajorVersion() throws SQLException {
		return 0;
	}

	public int getJDBCMinorVersion() throws SQLException {
		return 0;
	}

	public int getMaxBinaryLiteralLength() throws SQLException {
		return 0;
	}

	public int getMaxCatalogNameLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxCatalogNameLength()");
		return 0;
	}

	public int getMaxCharLiteralLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxCharLiteralLength()");
		return 0;
	}

	public int getMaxColumnNameLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxColumnNameLength()");
		return 0;
	}

	public int getMaxColumnsInGroupBy() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxColumnsInGroupBy()");
		return 0;
	}

	public int getMaxColumnsInIndex() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxColumnsInIndex()");
		return 0;
	}

	public int getMaxColumnsInOrderBy() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxColumnsInOrderBy()");
		return 0;
	}

	public int getMaxColumnsInSelect() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxColumnsInSelect()");
		return 0;
	}

	public int getMaxColumnsInTable() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxColumnsInTable()");
		return 0;
	}

	public int getMaxConnections() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxConnections()");
		return 0;
	}

	public int getMaxCursorNameLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxCursorNameLength()");
		return 0;
	}

	public int getMaxIndexLength() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxIndexLength()");
		return 0;
	}

	public int getMaxProcedureNameLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxProcedureNameLength()");
		return 0;
	}

	public int getMaxRowSize() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxRowSize()");
		return 0;
	}

	public int getMaxSchemaNameLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxSchemaNameLength()");
		return 0;
	}

	public int getMaxStatementLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxStatementLength()");
		return 0;
	}

	public int getMaxStatements() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxStatements()");
		return 0;
	}

	public int getMaxTableNameLength() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getMaxTableNameLength()");
		return 0;
	}

	public int getMaxTablesInSelect() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxTablesInSelect()");
		return 0;
	}

	public int getMaxUserNameLength() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getMaxUserNameLength()");
		return 0;
	}

	public String getNumericFunctions() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getNumericFunctions()");
		return "getNumericFunctions";
	}

	public ResultSet getPrimaryKeys(String arg0, String arg1, String arg2)
			throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getPrimaryKeys()");
		return new DatabasePrimaryKeys() ;
	}

	public ResultSet getProcedureColumns(String arg0, String arg1, String arg2,
			String arg3) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getProcedureColumns()");
		return new DatabaseProcedureColumns() ;
	}

	public String getProcedureTerm() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getProcedureTerm()");
		return "getProcedureTerm";
	}

	public ResultSet getProcedures(String arg0, String arg1, String arg2)
			throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getProcedures()");
		return new DatabaseProcedures() ;
	}

	public int getResultSetHoldability() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getResultSetHoldability()");
		return 0;
	}
//	JSE 6 
/*
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getRowIdLifetime()");
		return null ;
	}
*/
	public String getSQLKeywords() throws SQLException {
		return "SELECT";
	}

	public int getSQLStateType() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getSQLStateType()");
		return 0;
	}

	public String getSchemaTerm() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getSchemaTerm()");
		return "getSchemaTerm";
	}

	public ResultSet getSchemas() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getSchemas()");
		return new DatabaseSchemas() ;
	}

	public ResultSet getSchemas(String arg0, String arg1) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getSchemas()");
		return new DatabaseSchemas() ;
	}

	public String getSearchStringEscape() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.getSearchStringEscape()");
		return "getSearchStringEscape";
	}

	public String getStringFunctions() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getStringFunctions()");
		return "getStringFunctions";
	}

	public ResultSet getSuperTables(String arg0, String arg1, String arg2)
			throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getSuperTables()");
		return new DatabaseSuperTables() ;
	}

	public ResultSet getSuperTypes(String arg0, String arg1, String arg2)
			throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getSuperTypes()");
		return new DatabaseSuperTypes() ;
	}

	public String getSystemFunctions() throws SQLException {
		return "getSystemFunctions";
	}

	public ResultSet getTablePrivileges(String arg0, String arg1, String arg2)
			throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getTablePrivileges()");
		return new DatabaseTablePrivileges() ;
	}

	public ResultSet getTableTypes() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getTableTypes()");
		return new DatabaseTableTypes() ;
	}

	public ResultSet getTables(String arg0, String arg1, String arg2,
			String[] arg3) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getTables()");
		return new DatabaseTables() ;
	}

	public String getTimeDateFunctions() throws SQLException {
		return "getTimeDateFunctions";
	}

	public ResultSet getTypeInfo() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getTypeInfo()");
		return new DatabaseTypeInfo() ;
	}

	public ResultSet getUDTs(String arg0, String arg1, String arg2, int[] arg3)
			throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getUDTs()");
		return new DatabaseUDTs() ;
	}

	public String getURL() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getURL()");
		return "getURL";
	}

	public String getUserName() throws SQLException {
		return "getUserName";
	}

	public ResultSet getVersionColumns(String arg0, String arg1, String arg2)
			throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.getVersionColumns()");
		return new DatabaseVersionColumns() ;
	}

	public boolean insertsAreDetected(int arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.insertsAreDetected()");
		return false;
	}

	public boolean isCatalogAtStart() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.isCatalogAtStart()");
		return false;
	}

	public boolean isReadOnly() throws SQLException {
		return true;
	}

	public boolean locatorsUpdateCopy() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.locatorsUpdateCopy()");
		return false;
	}

	public boolean nullPlusNonNullIsNull() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.nullPlusNonNullIsNull()");
		return false;
	}

	public boolean nullsAreSortedAtEnd() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.nullsAreSortedAtEnd()");
		return false;
	}

	public boolean nullsAreSortedAtStart() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.nullsAreSortedAtStart()");
		return false;
	}

	public boolean nullsAreSortedHigh() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.nullsAreSortedHigh()");
		return false;
	}

	public boolean nullsAreSortedLow() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.nullsAreSortedLow()");
		return false;
	}

	public boolean othersDeletesAreVisible(int arg0) throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.othersDeletesAreVisible()");
		return false;
	}

	public boolean othersInsertsAreVisible(int arg0) throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.othersInsertsAreVisible()");
		return false;
	}

	public boolean othersUpdatesAreVisible(int arg0) throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.othersUpdatesAreVisible()");
		return false;
	}

	public boolean ownDeletesAreVisible(int arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.ownDeletesAreVisible()");
		return false;
	}

	public boolean ownInsertsAreVisible(int arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.ownInsertsAreVisible()");
		return false;
	}

	public boolean ownUpdatesAreVisible(int arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.ownUpdatesAreVisible()");
		return false;
	}

	public boolean storesLowerCaseIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.storesLowerCaseIdentifiers()");
		return false;
	}

	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.storesLowerCaseQuotedIdentifiers()");
		return false;
	}

	public boolean storesMixedCaseIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.storesMixedCaseIdentifiers()");
		return false;
	}

	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.storesMixedCaseQuotedIdentifiers()");
		return false;
	}

	public boolean storesUpperCaseIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.storesUpperCaseIdentifiers()");
		return false;
	}

	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.storesUpperCaseQuotedIdentifiers()");
		return false;
	}

	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsANSI92EntryLevelSQL()");
		return false;
	}

	public boolean supportsANSI92FullSQL() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsANSI92FullSQL()");
		return false;
	}

	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsANSI92IntermediateSQL()");
		return false;
	}

	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsAlterTableWithAddColumn()");
		return false;
	}

	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsAlterTableWithDropColumn()");
		return false;
	}

	public boolean supportsBatchUpdates() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsCatalogsInDataManipulation()");
		return false;
	}

	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsCatalogsInIndexDefinitions()");
		return false;
	}

	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsCatalogsInPrivilegeDefinitions()");
		return false;
	}

	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsCatalogsInProcedureCalls()");
		return false;
	}

	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsCatalogsInTableDefinitions()");
		return false;
	}

	public boolean supportsColumnAliasing() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsColumnAliasing()");
		return false;
	}

	public boolean supportsConvert() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsConvert()");
		return false;
	}

	public boolean supportsConvert(int arg0, int arg1) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsConvert()");
		return false;
	}

	public boolean supportsCoreSQLGrammar() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsCoreSQLGrammar()");
		return false;
	}

	public boolean supportsCorrelatedSubqueries() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsCorrelatedSubqueries()");
		return false;
	}

	public boolean supportsDataDefinitionAndDataManipulationTransactions()
			throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsDataDefinitionAndDataManipulationTransactions()");
		return false;
	}

	public boolean supportsDataManipulationTransactionsOnly()
			throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsDataManipulationTransactionsOnly()");
		return false;
	}

	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsDifferentTableCorrelationNames()");
		return false;
	}

	public boolean supportsExpressionsInOrderBy() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsExpressionsInOrderBy()");
		return false;
	}

	public boolean supportsExtendedSQLGrammar() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsExtendedSQLGrammar()");
		return false;
	}

	public boolean supportsFullOuterJoins() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsFullOuterJoins()");
		return false;
	}

	public boolean supportsGetGeneratedKeys() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsGetGeneratedKeys()");
		return false;
	}

	public boolean supportsGroupBy() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsGroupBy()");
		return false;
	}

	public boolean supportsGroupByBeyondSelect() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsGroupByBeyondSelect()");
		return false;
	}

	public boolean supportsGroupByUnrelated() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsGroupByUnrelated()");
		return false;
	}

	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsIntegrityEnhancementFacility()");
		return false;
	}

	public boolean supportsLikeEscapeClause() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsLikeEscapeClause()");
		return false;
	}

	public boolean supportsLimitedOuterJoins() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsLimitedOuterJoins()");
		return false;
	}

	public boolean supportsMinimumSQLGrammar() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsMinimumSQLGrammar()");
		return false;
	}

	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsMixedCaseIdentifiers()");
		return false;
	}

	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsMixedCaseQuotedIdentifiers()");
		return false;
	}

	public boolean supportsMultipleOpenResults() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsMultipleOpenResults()");
		return false;
	}

	public boolean supportsMultipleResultSets() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsMultipleResultSets()");
		return false;
	}

	public boolean supportsMultipleTransactions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsMultipleTransactions()");
		return false;
	}

	public boolean supportsNamedParameters() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsNamedParameters()");
		return false;
	}

	public boolean supportsNonNullableColumns() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsNonNullableColumns()");
		return false;
	}

	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsOpenCursorsAcrossCommit()");
		return false;
	}

	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsOpenCursorsAcrossRollback()");
		return false;
	}

	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsOpenStatementsAcrossCommit()");
		return false;
	}

	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsOpenStatementsAcrossRollback()");
		return false;
	}

	public boolean supportsOrderByUnrelated() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsOrderByUnrelated()");
		return false;
	}

	public boolean supportsOuterJoins() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsOuterJoins()");
		return false;
	}

	public boolean supportsPositionedDelete() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsPositionedDelete()");
		return false;
	}

	public boolean supportsPositionedUpdate() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsPositionedUpdate()");
		return false;
	}

	public boolean supportsResultSetConcurrency(int arg0, int arg1)
			throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsResultSetConcurrency()");
		return false;
	}

	public boolean supportsResultSetHoldability(int arg0) throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsResultSetHoldability()");
		return false;
	}

	public boolean supportsResultSetType(int arg0) throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsResultSetType()");
		return false;
	}

	public boolean supportsSavepoints() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsSavepoints()");
		return false;
	}

	public boolean supportsSchemasInDataManipulation() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSchemasInDataManipulation()");
		return false;
	}

	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSchemasInIndexDefinitions()");
		return false;
	}

	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSchemasInPrivilegeDefinitions()");
		return false;
	}

	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSchemasInProcedureCalls()");
		return false;
	}

	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSchemasInTableDefinitions()");
		return false;
	}

	public boolean supportsSelectForUpdate() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSelectForUpdate()");
		return false;
	}

	public boolean supportsStatementPooling() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsStatementPooling()");
		return false;
	}

	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsStoredFunctionsUsingCallSyntax()");
		return false;
	}

	public boolean supportsStoredProcedures() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsStoredProcedures()");
		return false;
	}

	public boolean supportsSubqueriesInComparisons() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSubqueriesInComparisons()");
		return false;
	}

	public boolean supportsSubqueriesInExists() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSubqueriesInExists()");
		return false;
	}

	public boolean supportsSubqueriesInIns() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSubqueriesInIns()");
		return false;
	}

	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsSubqueriesInQuantifieds()");
		return false;
	}

	public boolean supportsTableCorrelationNames() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsTableCorrelationNames()");
		return false;
	}

	public boolean supportsTransactionIsolationLevel(int arg0)
			throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.supportsTransactionIsolationLevel()");
		return false;
	}

	public boolean supportsTransactions() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsTransactions()");
		return false;
	}

	public boolean supportsUnion() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsUnion()");
		return false;
	}

	public boolean supportsUnionAll() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.supportsUnionAll()");
		return false;
	}

	public boolean updatesAreDetected(int arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.updatesAreDetected()");
		return false;
	}

	public boolean usesLocalFilePerTable() throws SQLException {
		
				// System.out.println("JdbcFolderDatabaseMetadata.usesLocalFilePerTable()");
		return false;
	}

	public boolean usesLocalFiles() throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.usesLocalFiles()");
		return false;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.isWrapperFor()");
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		// System.out.println("JdbcFolderDatabaseMetadata.unwrap()");
		return null ;
	}

}

package com.ysance.tools.jdbc.driver;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.ysance.tools.jdbc.driver.javascript.JavaScriptFilterFormatter;
import com.ysance.tools.jdbc.driver.preparedstatement.metadata.JdbcFolderStatementParameterMetaData;
import com.ysance.tools.jdbc.driver.resultsets.FolderResultSet;
import com.ysance.tools.jdbc.driver.resultsets.metadata.FieldMetadata;
import com.ysance.tools.jdbc.driver.resultsets.metadata.FolderResultSetMetaData;
import com.ysance.tools.jdbc.driver.sql.RequestCatalog;
import com.ysance.tools.jdbc.driver.sql.SQLValidator;

public class JdbcFolderStatement implements PreparedStatement {
	
	private boolean opened = false;
	private ResultSet resultat;
	private int maxRows = 10000000;
	
	private String preparedStatement;
	
	private FolderResultSet rsFinal;
	
	private String generatedScript;
	
	//public static final String DUMMY_REQUEST = "SELECT * FROM /temp ";
	public static final String DUMMY_REQUEST = "SELECT FILeNAME, FileNamE , FileName FROM /temp where size < 10000";

	public static final String FILTER_DEFAULT_VALUE = "true";
	
	public JdbcFolderStatement() {
		
	}

	public JdbcFolderStatement(String newSql) {
		preparedStatement = newSql;
	}

	public void addBatch(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.addBatch()");

	}

	public void cancel() throws SQLException {
		//  System.out.println("JdbcFolderStatement.cancel()");

	}

	public void clearBatch() throws SQLException {
		//  System.out.println("JdbcFolderStatement.clearBatch()");

	}

	public void clearWarnings() throws SQLException {
		//  System.out.println("JdbcFolderStatement.clearWarnings()");

	}

	public void close() throws SQLException {
		//  System.out.println("JdbcFolderStatement.close()");
		this.opened = false;
	}

	public boolean execute(String query) throws SQLException {
		//  System.out.println("JdbcFolderStatement.execute(String arg0) "+query);
		resultat = executeQuery(query);
		return  resultat != null;
	}

	public boolean execute(String arg0, int arg1) throws SQLException {
		//  System.out.println("JdbcFolderStatement.execute(String arg0, int arg1)");
		return false;
	}

	public boolean execute(String arg0, int[] arg1) throws SQLException {
		//  System.out.println("JdbcFolderStatement.execute(String arg0, int[] arg1)");
		return false;
	}

	public boolean execute(String arg0, String[] arg1) throws SQLException {
		//  System.out.println("JdbcFolderStatement.execute(String arg0, String[] arg1)");
		return false;
	}

	public int[] executeBatch() throws SQLException {
		//  System.out.println("JdbcFolderStatement.executeBatch()");
		return null;
	}
	
	private ResultSet getCatalogAsResultSet(RequestCatalog aCatalog) throws SQLException {
		if (aCatalog.getCatalogKind() == RequestCatalog.CATALOG_KIND_SINGLE) {
			return new FolderResultSet(aCatalog.toString());
		} else {
			JdbcFolderStatement statement =  new JdbcFolderStatement(aCatalog.toString());
			try {
				return	statement.executeQuery();			
			} finally {
				statement.close();
			}
			
		}
	}

	/**
	 * Ajoute les imports initiaux au script
	 */
	private void initInitialImports(StringBuffer currentScript, String indentation) {
		currentScript.append(indentation+"Row = Packages.com.ysance.tools.jdbc.driver.resultsets.row.Row;\n"); 
		currentScript.append(indentation+"\n\n"); 		
	}
	

	/**
	 * Méthode de l'interface Statement
	 * @param query : the query sent by user
	 */
	public ResultSet executeQuery(String query) throws SQLException {
	    //  System.out.println("JdbcFolderStatement.executeQuery()");
		SQLValidator validator = new SQLValidator(query);

        //FolderResultSetMetaData dataSetResultatMetaData = new FolderResultSetMetaData(validator.getFields());
        FolderResultSet dataSetResultat = null; //new FolderResultSet();//dataSetResultatMetaData);	          

		
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();

		try {		
			HashMap catalogues = catalogues = validator.getCatalogs();					
			Object[] clesCatalogues = catalogues.keySet().toArray();
			
            String filtre = FILTER_DEFAULT_VALUE;
            StringBuffer script = new StringBuffer();
            String indentation = "";
            
	        script.append(indentation+"//********* Debut script remplissage DataSet resultat \n"); 
	        initInitialImports(script, indentation);

	
			// Permet de déterminer si tous les catalogues sont vides
			boolean cataloguesVides = false;

			// Initialisation des metadata du Resultset de résultat
			FolderResultSetMetaData dataSetResultatMetaData = new FolderResultSetMetaData();
			
			// On echange les catalogues au format String par des catalogues au format DataSet
			for (int indexCleCatalogue = 0; indexCleCatalogue < clesCatalogues.length; indexCleCatalogue++) {						
				String aliasCatalogue = clesCatalogues[indexCleCatalogue].toString();			
				
				FolderResultSet catalogAsResultSet = (FolderResultSet)getCatalogAsResultSet((RequestCatalog)catalogues.get(clesCatalogues[indexCleCatalogue]));
				// Un catalogue est vide si on est avant sa première ligne et sur la dernière  
				cataloguesVides = cataloguesVides || ( catalogAsResultSet.isLast() && catalogAsResultSet.isBeforeFirst());			
				catalogues.put(aliasCatalogue, catalogAsResultSet);				
				
				// Ajout des colonnes du datasetsource comme colonnes possibles du dataset résultat 
				if (validator.hasAllFieldsWanted()) {
					dataSetResultatMetaData.addAllColumns(catalogAsResultSet.getMetaData());
				}
							
				// Ajout du dataset au scope javascript
		        Scriptable jsArgs = Context.toObject(catalogAsResultSet, scope);
		        scope.put(clesCatalogues[indexCleCatalogue].toString(), scope, jsArgs);
		        
		        script.append(indentation+aliasCatalogue+".first();\n");
		        script.append(indentation+"while ("+aliasCatalogue+".next()) { \n");
		        indentation = indentation + "  ";
		        if (clesCatalogues.length == 1) {
					for (int indexColonne = 1; indexColonne <= catalogAsResultSet.getMetaData().getColumnCount(); indexColonne++ ) {
						script.append(indentation+"var "+catalogAsResultSet.getMetaData().getColumnLabel(indexColonne)+" = "+aliasCatalogue+".get"+catalogAsResultSet.getMetaData().getColumnTypeName(indexColonne)+"('"+catalogAsResultSet.getMetaData().getColumnLabel(indexColonne)+"');\n");
					}					
				}		        	        
			}			
			
			if (!validator.hasAllFieldsWanted()) {
				dataSetResultatMetaData.addAllColumns(validator.getFields());
			}
	        dataSetResultat = new FolderResultSet(dataSetResultatMetaData);	 
	        
			// Application du WHERE
			filtre = validator.getWhereClause();	
		      
	    	filtre = JavaScriptFilterFormatter.format(filtre);
	    	
	    	filtre = filtre.trim().length() == 0 ? FILTER_DEFAULT_VALUE : filtre;
   
			script.append(indentation+"if ( " +filtre + " ) {\n");
			for (int indexTables = 0; indexTables < clesCatalogues.length; indexTables++) {						
				script.append(indentation+indentation+"row = new Row(dataSetResultatMetaData)\n");
				for (int indexChamp = 0; indexChamp < dataSetResultatMetaData.getColumnCount(); indexChamp++) {
					FieldMetadata champ = dataSetResultatMetaData.getColumnDefinitionByPosition(indexChamp);
					script.append(indentation+indentation+"row.setData('"+champ.columnLabel+"', "+champ.expression+");\n");
					script.append(indentation+indentation+"dataSetResultatMetaData.getColumnDefinitionByUniqueName('"+champ.columnLabel+"').setColumnType(row.getData('"+champ.columnLabel+"').getClass().getName());\n");
				}			    	      
				script.append(indentation+indentation+"dataSetResultat.addRow(row);\n");
			}
			
			script.append(indentation+"}\n");
			  
			// Fermeture des boucles sur les datasets source
			char[] tableauAccoladesFermantes = new char[clesCatalogues.length];
			java.util.Arrays.fill(tableauAccoladesFermantes, '}');
			script.append(new String(tableauAccoladesFermantes));
			indentation = "";
			script.append(indentation+"\n//********* Fin script remplissage DataSet resultat \n"); 
			
			// contenu du script
			this.generatedScript = script.toString();        
			            
			// Application des filtres de la clause where 
			java.util.ArrayList indexFichiers = new java.util.ArrayList();
			Scriptable jsArgsindexFichiers = Context.toObject(indexFichiers, scope);
			scope.put("indexFichiers", scope, jsArgsindexFichiers);           
			
			
			Scriptable jsArgsDataSetResultat = Context.toObject(dataSetResultat, scope);
			scope.put("dataSetResultat", scope, jsArgsDataSetResultat);
			  
			Scriptable jsArgsDataSetResultatMetaData = Context.toObject(dataSetResultatMetaData, scope);
			scope.put("dataSetResultatMetaData", scope, jsArgsDataSetResultatMetaData);
		  
			Object result = cx.evaluateString(scope, script.toString(), "<cmd>", 1, null);
			            
			// retour du script
			//System.err.println(cx.toString(result));
				    
		} catch (Exception ex) {
			ex.printStackTrace();    
		} finally {
			Context.exit();
		}  	
		    
 	    // Application du GROUP BY

	      
	    // Application du ORDER BY	
	      
	    rsFinal = dataSetResultat;
	  	
	  	
      return rsFinal;
	}

	public int executeUpdate(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.executeUpdate()");
		return 0;
	}

	public int executeUpdate(String arg0, int arg1) throws SQLException {
		//  System.out.println("JdbcFolderStatement.executeUpdate()");
		return 0;
	}

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		//  System.out.println("JdbcFolderStatement.executeUpdate()");
		return 0;
	}

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		//  System.out.println("JdbcFolderStatement.executeUpdate()");
		return 0;
	}

	public Connection getConnection() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getConnection()");
		return null;
	}

	public int getFetchDirection() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getFetchDirection()");
		return 0;
	}

	public int getFetchSize() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getFetchSize()");
		return 0;
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getGeneratedKeys()");
		return null;
	}

	public int getMaxFieldSize() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getMaxFieldSize()");
		return 0;
	}

	public int getMaxRows() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getMaxRows()");
		return maxRows;
	}

	public boolean getMoreResults() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getMoreResults()");
		return false;
	}

	public boolean getMoreResults(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.getMoreResults()");
		return false;
	}

	public int getQueryTimeout() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getQueryTimeout()");
		return 0;
	}

	public ResultSet getResultSet() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getResultSet()");
		return resultat;
	}

	public int getResultSetConcurrency() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getResultSetConcurrency()");
		return 0;
	}

	public int getResultSetHoldability() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getResultSetHoldability()");
		return 0;
	}

	public int getResultSetType() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getResultSetType()");
		return 0;
	}

	public int getUpdateCount() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getUpdateCount()");
		return 0;
	}

	public SQLWarning getWarnings() throws SQLException {
		//  System.out.println("JdbcFolderStatement.getWarnings()");
		return null;
	}

	public boolean isClosed() throws SQLException {
		//  System.out.println("JdbcFolderStatement.isClosed()");
		return !opened;
	}

	public boolean isPoolable() throws SQLException {
		//  System.out.println("JdbcFolderStatement.isPoolable()");
		return false;
	}

	public void setCursorName(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setCursorName()");

	}

	public void setEscapeProcessing(boolean arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setEscapeProcessing()");

	}

	public void setFetchDirection(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setFetchDirection()");

	}

	public void setFetchSize(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setFetchSize()");

	}

	public void setMaxFieldSize(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setMaxFieldSize()");

	}

	/**
	 * Affecte une nouvelle valeurs au nombre de lignes maximum retournées
	 * @param newMaxRows la nouvelle valeur pour maxRows
	 */
	public void setMaxRows(int newMaxRows) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setMaxRows()");
		this.maxRows = newMaxRows;
	}

	public void setPoolable(boolean arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setPoolable()");

	}

	public void setQueryTimeout(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.setQueryTimeout()");

	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.isWrapperFor()");
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		//  System.out.println("JdbcFolderStatement.unwrap()");
		return null;
	}

    /**
     * Executes the SQL query in this <code>PreparedStatement</code> object
     * and returns the <code>ResultSet</code> object generated by the query.
     *
     * @return a <code>ResultSet</code> object that contains the data produced by the
     *         query; never <code>null</code>
     * @exception SQLException if a database access error occurs or the SQL
     *            statement does not return a <code>ResultSet</code> object
     */	
    public ResultSet executeQuery() throws SQLException {
    	//  System.out.println("JdbcFolderStatement.executeQuery()");
    	return executeQuery(preparedStatement);	
    }

    /**
     * Executes the SQL statement in this <code>PreparedStatement</code> object,
     * which must be an SQL <code>INSERT</code>, <code>UPDATE</code> or
     * <code>DELETE</code> statement; or an SQL statement that returns nothing, 
     * such as a DDL statement.
     *
     * @return either (1) the row count for <code>INSERT</code>, <code>UPDATE</code>,
     *         or <code>DELETE</code> statements
     *         or (2) 0 for SQL statements that return nothing
     * @exception SQLException if a database access error occurs or the SQL
     *            statement returns a <code>ResultSet</code> object
     */
    public int executeUpdate() throws SQLException {
    	//  System.out.println("JdbcFolderStatement.executeUpdate()");
    	return 0;
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     *
     * <P><B>Note:</B> You must specify the parameter's SQL type.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param sqlType the SQL type code defined in <code>java.sql.Types</code>
     * @exception SQLException if a database access error occurs
     */
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setNull()");
    }

    /**
     * Sets the designated parameter to the given Java <code>boolean</code> value.
     * The driver converts this
     * to an SQL <code>BIT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setBoolean()");
    }

    /**
     * Sets the designated parameter to the given Java <code>byte</code> value.  
     * The driver converts this
     * to an SQL <code>TINYINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setByte(int parameterIndex, byte x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setByte()");
    }

    /**
     * Sets the designated parameter to the given Java <code>short</code> value. 
     * The driver converts this
     * to an SQL <code>SMALLINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setShort(int parameterIndex, short x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setShort()");
    }

    /**
     * Sets the designated parameter to the given Java <code>int</code> value.  
     * The driver converts this
     * to an SQL <code>INTEGER</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setInt(int parameterIndex, int x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setInt()");
    }

    /**
     * Sets the designated parameter to the given Java <code>long</code> value. 
     * The driver converts this
     * to an SQL <code>BIGINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setLong(int parameterIndex, long x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setLong()");
    }

    /**
     * Sets the designated parameter to the given Java <code>float</code> value. 
     * The driver converts this
     * to an SQL <code>FLOAT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setFloat(int parameterIndex, float x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setFloat()");
    }

    /**
     * Sets the designated parameter to the given Java <code>double</code> value.  
     * The driver converts this
     * to an SQL <code>DOUBLE</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setDouble(int parameterIndex, double x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setDouble()");
    }

    /**
     * Sets the designated parameter to the given <code>java.math.BigDecimal</code> value.  
     * The driver converts this to an SQL <code>NUMERIC</code> value when
     * it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setBigDecimal()");
    }

    /**
     * Sets the designated parameter to the given Java <code>String</code> value. 
     * The driver converts this
     * to an SQL <code>VARCHAR</code> or <code>LONGVARCHAR</code> value
     * (depending on the argument's
     * size relative to the driver's limits on <code>VARCHAR</code> values)
     * when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setString(int parameterIndex, String x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setString()");
    }

    /**
     * Sets the designated parameter to the given Java array of bytes.  The driver converts
     * this to an SQL <code>VARBINARY</code> or <code>LONGVARBINARY</code>
     * (depending on the argument's size relative to the driver's limits on
     * <code>VARBINARY</code> values) when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value 
     * @exception SQLException if a database access error occurs
     */
    public void setBytes(int parameterIndex, byte x[]) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setDate()");
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value.  
     * The driver converts this
     * to an SQL <code>DATE</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setDate(int parameterIndex, java.sql.Date x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setDate()");
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value.  
     * The driver converts this
     * to an SQL <code>TIME</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setTime(int parameterIndex, java.sql.Time x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setTime()");
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value.  
     * The driver
     * converts this to an SQL <code>TIMESTAMP</code> value when it sends it to the
     * database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value 
     * @exception SQLException if a database access error occurs
     */
    public void setTimestamp(int parameterIndex, java.sql.Timestamp x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setTimestamp()");
    }

    /**
     * Sets the designated parameter to the given input stream, which will have 
     * the specified number of bytes.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the Java input stream that contains the ASCII parameter value
     * @param length the number of bytes in the stream 
     * @exception SQLException if a database access error occurs
     */
    public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setAsciiStream()");
    }

    /**
     * Sets the designated parameter to the given input stream, which 
     * will have the specified number of bytes. A Unicode character has
     * two bytes, with the first byte being the high byte, and the second
     * being the low byte.
     *
     * When a very large Unicode value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the 
     * stream as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from Unicode to the database char format.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...  
     * @param x a <code>java.io.InputStream</code> object that contains the
     *        Unicode parameter value as two-byte Unicode characters
     * @param length the number of bytes in the stream 
     * @exception SQLException if a database access error occurs
     * @deprecated
     */
    public void setUnicodeStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setUnicodeStream()");
    }

    /**
     * Sets the designated parameter to the given input stream, which will have 
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the 
     * stream as needed until end-of-file is reached.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the java input stream which contains the binary parameter value
     * @param length the number of bytes in the stream 
     * @exception SQLException if a database access error occurs
     */
    public void setBinaryStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setBinaryStream()");    	
    }

    /**
     * Clears the current parameter values immediately.
     * <P>In general, parameter values remain in force for repeated use of a
     * statement. Setting a parameter value automatically clears its
     * previous value.  However, in some cases it is useful to immediately
     * release the resources used by the current parameter values; this can
     * be done by calling the method <code>clearParameters</code>.
     *
     * @exception SQLException if a database access error occurs
     */
    public void clearParameters() throws SQLException {
    	//  System.out.println("JdbcFolderStatement.clearParameters()");
    }

    //----------------------------------------------------------------------
    // Advanced features:

    /**
     * <p>Sets the value of the designated parameter with the given object. The second
     * argument must be an object type; for integral values, the
     * <code>java.lang</code> equivalent objects should be used.
     *
     * <p>The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     *
     * If the object has a custom mapping (is of a class implementing the 
     * interface <code>SQLData</code>),
     * the JDBC driver should call the method <code>SQLData.writeSQL</code> to 
     * write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>, <code>Struct</code>, 
     * or <code>Array</code>, the driver should pass it to the database as a 
     * value of the corresponding SQL type.
     *
     * <p>Note that this method may be used to pass database-specific
     * abstract data types. 
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be 
     * sent to the database. The scale argument may further qualify this type.
     * @param scale for java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     *          this is the number of digits after the decimal point.  For all other
     *          types, this value will be ignored.
     * @exception SQLException if a database access error occurs
     * @see Types 
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setObject()");    	
    }

   /**
    * Sets the value of the designated parameter with the given object.
    * This method is like the method <code>setObject</code>
    * above, except that it assumes a scale of zero.
    *
    * @param parameterIndex the first parameter is 1, the second is 2, ...
    * @param x the object containing the input parameter value
    * @param targetSqlType the SQL type (as defined in java.sql.Types) to be 
    *                      sent to the database
    * @exception SQLException if a database access error occurs
    */
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setObject()");
    }

    /**
     * <p>Sets the value of the designated parameter using the given object. 
     * The second parameter must be of type <code>Object</code>; therefore, the
     * <code>java.lang</code> equivalent objects should be used for built-in types.
     *
     * <p>The JDBC specification specifies a standard mapping from
     * Java <code>Object</code> types to SQL types.  The given argument 
     * will be converted to the corresponding SQL type before being
     * sent to the database.
     *
     * <p>Note that this method may be used to pass datatabase-
     * specific abstract data types, by using a driver-specific Java
     * type.
     *
     * If the object is of a class implementing the interface <code>SQLData</code>,
     * the JDBC driver should call the method <code>SQLData.writeSQL</code>
     * to write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>, <code>Struct</code>, 
     * or <code>Array</code>, the driver should pass it to the database as a 
     * value of the corresponding SQL type.
     * <P>
     * This method throws an exception if there is an ambiguity, for example, if the
     * object is of a class implementing more than one of the interfaces named above.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the object containing the input parameter value 
     * @exception SQLException if a database access error occurs or the type 
     *            of the given object is ambiguous
     */
    public void setObject(int parameterIndex, Object x) throws SQLException {
    	
    }

    /**
     * Executes the SQL statement in this <code>PreparedStatement</code> object,
     * which may be any kind of SQL statement.
     * Some prepared statements return multiple results; the <code>execute</code>
     * method handles these complex statements as well as the simpler
     * form of statements handled by the methods <code>executeQuery</code>
     * and <code>executeUpdate</code>.
     * <P>
     * The <code>execute</code> method returns a <code>boolean</code> to
     * indicate the form of the first result.  You must call either the method
     * <code>getResultSet</code> or <code>getUpdateCount</code>
     * to retrieve the result; you must call <code>getMoreResults</code> to
     * move to any subsequent result(s).
     *
     * @return <code>true</code> if the first result is a <code>ResultSet</code>
     *         object; <code>false</code> if the first result is an update
     *         count or there is no result
     * @exception SQLException if a database access error occurs or an argument
     *            is supplied to this method
     * @see Statement#execute
     * @see Statement#getResultSet
     * @see Statement#getUpdateCount
     * @see Statement#getMoreResults

     */
    public boolean execute() throws SQLException {
    	return executeQuery() != null;
    }

    //--------------------------JDBC 2.0-----------------------------

    /**
     * Adds a set of parameters to this <code>PreparedStatement</code>
     * object's batch of commands.
     * 
     * @exception SQLException if a database access error occurs
     * @see Statement#addBatch
     * @since 1.2
     */
    public void addBatch() throws SQLException {
    	
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param reader the <code>java.io.Reader</code> object that contains the 
     *        Unicode data
     * @param length the number of characters in the stream 
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setCharacterStream(int parameterIndex, java.io.Reader reader, int length) throws SQLException {
    	
    }

    /**
     * Sets the designated parameter to the given
     *  <code>REF(&lt;structured-type&gt;)</code> value.
     * The driver converts this to an SQL <code>REF</code> value when it
     * sends it to the database.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x an SQL <code>REF</code> value
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setRef (int i, Ref x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setRef()");
    }

    /**
     * Sets the designated parameter to the given <code>Blob</code> object.
     * The driver converts this to an SQL <code>BLOB</code> value when it
     * sends it to the database.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x a <code>Blob</code> object that maps an SQL <code>BLOB</code> value
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setBlob (int i, Blob x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setBlob()");
    }

    /**
     * Sets the designated parameter to the given <code>Clob</code> object.
     * The driver converts this to an SQL <code>CLOB</code> value when it
     * sends it to the database.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x a <code>Clob</code> object that maps an SQL <code>CLOB</code> value
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setClob (int i, Clob x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setClob()");
    }

    /**
     * Sets the designated parameter to the given <code>Array</code> object.
     * The driver converts this to an SQL <code>ARRAY</code> value when it
     * sends it to the database.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x an <code>Array</code> object that maps an SQL <code>ARRAY</code> value
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setArray (int i, Array x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setArray()");
    }

    /**
     * Retrieves a <code>ResultSetMetaData</code> object that contains
     * information about the columns of the <code>ResultSet</code> object
     * that will be returned when this <code>PreparedStatement</code> object 
     * is executed.
     * <P>
     * Because a <code>PreparedStatement</code> object is precompiled, it is
     * possible to know about the <code>ResultSet</code> object that it will
     * return without having to execute it.  Consequently, it is possible
     * to invoke the method <code>getMetaData</code> on a
     * <code>PreparedStatement</code> object rather than waiting to execute
     * it and then invoking the <code>ResultSet.getMetaData</code> method
     * on the <code>ResultSet</code> object that is returned.
     * <P>
     * <B>NOTE:</B> Using this method may be expensive for some drivers due
     * to the lack of underlying DBMS support.
     *
     * @return the description of a <code>ResultSet</code> object's columns or
     *         <code>null</code> if the driver cannot return a
     *         <code>ResultSetMetaData</code> object
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public ResultSetMetaData getMetaData() throws SQLException {
    	//  System.out.println("JdbcFolderStatement.getMetaData()");
    	return rsFinal.getMetaData();
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>DATE</code> value,
     * which the driver then sends to the database.  With 
     * a <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the date
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setDate(int parameterIndex, java.sql.Date x, Calendar cal) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setDate()");
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIME</code> value,
     * which the driver then sends to the database.  With 
     * a <code>Calendar</code> object, the driver can calculate the time
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the time
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setTime(int parameterIndex, java.sql.Time x, Calendar cal) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setTime()");
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIMESTAMP</code> value,
     * which the driver then sends to the database.  With a
     *  <code>Calendar</code> object, the driver can calculate the timestamp
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value 
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the timestamp
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setTimestamp(int parameterIndex, java.sql.Timestamp x, Calendar cal) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setTimestamp()");
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * This version of the method <code>setNull</code> should
     * be used for user-defined types and REF type parameters.  Examples
     * of user-defined types include: STRUCT, DISTINCT, JAVA_OBJECT, and 
     * named array types.
     *
     * <P><B>Note:</B> To be portable, applications must give the
     * SQL type code and the fully-qualified SQL type name when specifying
     * a NULL user-defined or REF parameter.  In the case of a user-defined type 
     * the name is the type name of the parameter itself.  For a REF 
     * parameter, the name is the type name of the referenced type.  If 
     * a JDBC driver does not need the type code or type name information, 
     * it may ignore it.     
     *
     * Although it is intended for user-defined and Ref parameters,
     * this method may be used to set a null parameter of any JDBC type.
     * If the parameter does not have a user-defined or REF type, the given
     * typeName is ignored.
     *
     *
     * @param paramIndex the first parameter is 1, the second is 2, ...
     * @param sqlType a value from <code>java.sql.Types</code>
     * @param typeName the fully-qualified name of an SQL user-defined type;
     *  ignored if the parameter is not a user-defined type or REF 
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public void setNull (int paramIndex, int sqlType, String typeName) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setNull()");
    }

    //------------------------- JDBC 3.0 -----------------------------------

    /**
     * Sets the designated parameter to the given <code>java.net.URL</code> value. 
     * The driver converts this to an SQL <code>DATALINK</code> value
     * when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the <code>java.net.URL</code> object to be set
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */ 
    public void setURL(int parameterIndex, java.net.URL x) throws SQLException {
    	//  System.out.println("JdbcFolderStatement.setURL()");
    }

    /**
     * Retrieves the number, types and properties of this 
     * <code>PreparedStatement</code> object's parameters.
     *
     * @return a <code>ParameterMetaData</code> object that contains information
     *         about the number, types and properties of this 
     *         <code>PreparedStatement</code> object's parameters
     * @exception SQLException if a database access error occurs
     * @see ParameterMetaData
     * @since 1.4
     */
    public ParameterMetaData getParameterMetaData() throws SQLException {
    	//  System.out.println("JdbcFolderStatement.getParameterMetaData()");
    	return new JdbcFolderStatementParameterMetaData();
    }
	
	public String toString() {
		return this.generatedScript;
	}
}

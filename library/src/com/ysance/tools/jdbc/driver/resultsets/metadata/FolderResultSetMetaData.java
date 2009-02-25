package com.ysance.tools.jdbc.driver.resultsets.metadata;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;
import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.NoDatasetFieldFoundAtPositionException;
import com.ysance.tools.jdbc.driver.sql.RequestFieldSelected;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * @author csebille
 *
 */
public class FolderResultSetMetaData implements ResultSetMetaData {

	public static final String EXTENSION_FIELD = "EXTENSION";		
	public static final String FILENAME_FIELD  = "FILENAME";
	public static final String SIZE_FIELD      = "SIZE";	
	
	
	private LinkedHashMap colonnes;
	private Hashtable positionColonne;
	private Hashtable positionColonneInverse;
	private Hashtable colonnesPossibles;
	private String catalogue = "";
	private String schemaName = "";
	
	public FolderResultSetMetaData() {
		colonnesPossibles = new Hashtable();
		colonnes= new LinkedHashMap();
		positionColonne = new Hashtable();
		positionColonneInverse = new Hashtable();
	}	
	
	public FolderResultSetMetaData(String aCatalogue) {
		this();
		this.catalogue = aCatalogue;
		this.schemaName = aCatalogue;
		colonnesPossibles.put(FILENAME_FIELD, new FieldMetadata(this.catalogue, String.class.getName(), 255, FILENAME_FIELD, FILENAME_FIELD, FILENAME_FIELD, java.sql.Types.VARCHAR, "String", 255, 255, this.catalogue, this.catalogue, false, true, false, false, 0, false, true, false, true));
		colonnesPossibles.put(SIZE_FIELD, new FieldMetadata(this.catalogue, Double.class.getName(), 255, SIZE_FIELD, SIZE_FIELD, SIZE_FIELD, java.sql.Types.BIGINT, "Double", 255, 255, this.catalogue, this.catalogue, false, true, false, false, 0, false, true, false, true));
		colonnesPossibles.put(EXTENSION_FIELD, new FieldMetadata(this.catalogue, String.class.getName(), 255, EXTENSION_FIELD, EXTENSION_FIELD, EXTENSION_FIELD, java.sql.Types.VARCHAR, "String", 255, 255, this.catalogue, this.catalogue, false, true, false, false, 0, false, true, false, true));
	}

	public FolderResultSetMetaData(ArrayList aFieldList) throws SQLException {
		this();
		
		for (int indexChamp = 0; indexChamp < aFieldList.size(); indexChamp++) {
			RequestFieldSelected champ = (RequestFieldSelected)aFieldList.get(indexChamp);
			
			String  columnLabel = champ.getAlias();
			String  columnName = champ.getExpression();

			String  catalogName = "";
			String  columnClassName = "";
			int     columnDisplaySize = 0;
			int     columnType = 0;
			String  columnTypeName = "";
			String  expression = champ.getExpression();
			int     precision = 0;
			int     scale = 0;
			String  schemaName = "";
			String  tableName = "";
			boolean autoIncrement = false;
			boolean caseSensitive = false;
			boolean isCurrency = false;
			boolean definitelyWritable = false;
			int     nullable = 0;
			boolean isReadOnly = false;
			boolean isSearchable = false;
			boolean isSigned = false;
			boolean isWritable = false;
			
			addColumn(new FieldMetadata(	catalogName, 
					columnClassName,
					columnDisplaySize,
					columnLabel,
					columnName,
					expression,
					columnType,
					columnTypeName,
					precision,
					scale,
					schemaName,
					tableName,
					autoIncrement,
					caseSensitive,
					isCurrency,
					definitelyWritable,
					nullable,
					isReadOnly,
					isSearchable,	
					isSigned,	
					isWritable));
		}				
	}
	
	/**
	 * Vérifie l'existence d'une colonne suivant son nom
	 * @param aField : nom d'une colonne
	 * @return true si la colonne existe
	 */
	public boolean recognizeField(String aField) {
		//  System.out.println("FolderResultSetMetaData.recognizeField()");
		return colonnesPossibles.containsKey(aField);
	}
	
	/**
	 * Ajoute une colonne au ResultSet et l'associe à une position
	 * @param aField : Label d'une colonne
	 * @return position de la colonne dans le ResultSet
	 */
	private int addColumn(String aField)  throws SQLException  {
		FieldMetadata uniqueFieldDefinition = (FieldMetadata)((FieldMetadata)colonnesPossibles.get(aField)).clone();
		uniqueFieldDefinition.setColumnLabel(aField);
		
		return addColumn(uniqueFieldDefinition);
		
		/*Integer positionEnCours = new Integer(colonnes.size());
		positionColonne.put(aField, positionEnCours);
		positionColonneInverse.put(positionEnCours, aField);
		colonnes.put(aField, uniqueFieldDefinition);		

		return positionEnCours.intValue();*/
	}

	/**
	 * Ajoute une colonne au ResultSet et l'associe à une position
	 * @param aField : nom d'une colonne
	 * @return position de la colonne dans le ResultSet
	 */
	int addColumn(FieldMetadata aFieldMetadata) throws SQLException {
		// Vérification que le nom de la colonne n'existe pas déjà
		if (colonnes.get(aFieldMetadata.columnLabel) != null) {
			throw new JdbcFolderExceptions.ColumnAliasAlreadyExistsException(aFieldMetadata.columnLabel);
		}
		
		Integer positionEnCours = new Integer(colonnes.size());
		positionColonne.put(aFieldMetadata.columnLabel, positionEnCours);
		positionColonneInverse.put(positionEnCours, aFieldMetadata.columnLabel);
		colonnes.put(aFieldMetadata.columnLabel, aFieldMetadata);		

		return positionEnCours.intValue();
	}
	
	
	/**
	 * Ajoute toutes les colonnes possibles au dataset 
	 */
	public void addAllPossibleColumns() throws SQLException {
		
		for (Iterator cles = colonnesPossibles.keySet().iterator(); cles.hasNext();) {
			addColumn(cles.next().toString());			
		}		
	}
	
	/**
	 * Retourne le nom unique d'une colonne à partir de sa position
	 * @param aPosition la position de la colonne souhaitée
	 * @return la nom unique de la colonne
	 */
	public String getColumnUniqueNameByPosition(int aPosition) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnUniqueNameByPosition()");
		Object foundColumn = positionColonneInverse.get(new Integer(aPosition));
		if (foundColumn == null) {
			throw new NoDatasetFieldFoundAtPositionException(aPosition + 1);
		}
		return foundColumn.toString();
	}
	
	/**
	 * Retourne les métadata d'une colonne à partir de son nom
	 * @param aUniqueFieldName le nom de la colonne souhaitée
	 * @return un objet FieldMetadata correspondant aux métadata d'une colonne
	 */
	public FieldMetadata getColumnDefinitionByUniqueName(String aUniqueFieldName ) {
		//  System.out.println("FolderResultSetMetaData.getColumnDefinitionByUniqueName()");
		return (FieldMetadata)colonnes.get(aUniqueFieldName);
	}
	
	/**
	 * Retourne les métadata d'une colonne à partir de sa position
	 * @param aUniqueFieldName le nom de la colonne souhaitée
	 * @return un objet FieldMetadata correspondant aux métadata d'une colonne
	 */
	public FieldMetadata getColumnDefinitionByPosition(int aPosition ) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnDefinitionByPosition()");
		return (FieldMetadata)colonnes.get(getColumnUniqueNameByPosition(aPosition));
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSetMetaData#getCatalogName(int)
	 */
	public String getCatalogName(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getCatalogName()"+" "+column);
		return this.catalogue;
	}

	public String getColumnClassName(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnClassName()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).columnClassName);
		return getColumnDefinitionByPosition(column - 1).columnClassName;
	}

	public int getColumnCount() throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnCount()");
		return colonnes.size();
	}

	public int getColumnDisplaySize(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnDisplaySize()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).columnDisplaySize);
		return getColumnDefinitionByPosition(column - 1).columnDisplaySize;
	}

	public String getColumnLabel(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnLabel()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).columnLabel);
		return getColumnDefinitionByPosition(column - 1).columnLabel;
	}

	public String getColumnName(int column) throws SQLException {		
		//  System.out.println("FolderResultSetMetaData.getColumnName()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).columnName);
		return getColumnDefinitionByPosition(column - 1).columnName;
	}

	public int getColumnType(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnType()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).columnType);
		return getColumnDefinitionByPosition(column - 1).columnType;
	}

	public String getColumnTypeName(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnTypeName()"+" "+column+ " "+getColumnDefinitionByPosition(column - 1).columnTypeName);
		return getColumnDefinitionByPosition(column - 1).columnTypeName;
	}

	public int getPrecision(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getPrecision()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).precision);
		return getColumnDefinitionByPosition(column - 1).precision;
	}

	public int getScale(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getScale()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).scale);
		return getColumnDefinitionByPosition(column - 1).scale;
	}

	public String getSchemaName(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getSchemaName()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).schemaName);
		return getColumnDefinitionByPosition(column - 1).schemaName;
	}

	public String getTableName(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getTableName()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).tableName);
		return getColumnDefinitionByPosition(column - 1).tableName;
	}

	public boolean isAutoIncrement(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isAutoIncrement()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).autoIncrement);
		return getColumnDefinitionByPosition(column - 1).autoIncrement;
	}

	public boolean isCaseSensitive(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isCaseSensitive()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).caseSensitive);
		return getColumnDefinitionByPosition(column - 1).caseSensitive;
	}

	public boolean isCurrency(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isCurrency()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).isCurrency);
		return getColumnDefinitionByPosition(column - 1).isCurrency;
	}

	public boolean isDefinitelyWritable(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isDefinitelyWritable()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).definitelyWritable);
		return getColumnDefinitionByPosition(column - 1).definitelyWritable;
	}

	public int isNullable(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isNullable()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).nullable);
		return getColumnDefinitionByPosition(column - 1).nullable;
	}

	public boolean isReadOnly(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isReadOnly()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).isReadOnly);
		return getColumnDefinitionByPosition(column - 1).isReadOnly;
	}

	public boolean isSearchable(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isSearchable()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).isSearchable);
		return getColumnDefinitionByPosition(column - 1).isSearchable;
	}

	public boolean isSigned(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isSigned()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).isSigned);
		return getColumnDefinitionByPosition(column - 1).isSigned;
	}

	public boolean isWritable(int column) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isWritable()"+" "+column+" "+getColumnDefinitionByPosition(column - 1).isWritable);
		return getColumnDefinitionByPosition(column - 1).isWritable;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.isWrapperFor()");
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.unwrap()");
		return null;
	}

}

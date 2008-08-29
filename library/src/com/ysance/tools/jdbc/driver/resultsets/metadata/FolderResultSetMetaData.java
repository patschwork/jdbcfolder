package com.ysance.tools.jdbc.driver.resultsets.metadata;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.NoDatasetFieldFoundAtPositionException;

/**
 * @author csebille
 *
 */
public class FolderResultSetMetaData implements ResultSetMetaData {

	public static final String EXTENSION_FIELD = "EXTENSION";		
	public static final String FILENAME_FIELD  = "FILENAME";
	public static final String SIZE_FIELD      = "SIZE";	
	
	public class FileFieldMetadata {
		public String  catalogName = "";
		public String  columnClassName = "";
		public int     columnDisplaySize = 0;
		public String  columnLabel = "";
		public String  columnName = "";
		public int     columnType = 0;
		public String  columnTypeName = "";
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
		
		public FileFieldMetadata(String  aCatalogName, String  aColumnClassName,
		int     aColumnDisplaySize,		String  aColumnLabel,		String  aColumnName,		int     aColumnType,
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
			return new FileFieldMetadata(	this.catalogName, 
											this.columnClassName,
											this.columnDisplaySize,
											this.columnLabel,
											this.columnName,
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
		//  System.out.println("FolderResultSetMetaData.FolderResultSetMetaData()");
		this.catalogue = aCatalogue;
		this.schemaName = aCatalogue;
		colonnesPossibles = new Hashtable();
		colonnesPossibles.put(FILENAME_FIELD, new FileFieldMetadata(this.catalogue, String.class.getName(), 255, FILENAME_FIELD, FILENAME_FIELD, java.sql.Types.VARCHAR, "String", 255, 255, this.catalogue, this.catalogue, false, true, false, false, 0, false, true, false, true));
		colonnesPossibles.put(SIZE_FIELD, new FileFieldMetadata(this.catalogue, Long.class.getName(), 255, SIZE_FIELD, SIZE_FIELD, java.sql.Types.BIGINT, "Long", 255, 255, this.catalogue, this.catalogue, false, true, false, false, 0, false, true, false, true));
		colonnesPossibles.put(EXTENSION_FIELD, new FileFieldMetadata(this.catalogue, String.class.getName(), 255, EXTENSION_FIELD, EXTENSION_FIELD, java.sql.Types.VARCHAR, "String", 255, 255, this.catalogue, this.catalogue, false, true, false, false, 0, false, true, false, true));
		
		colonnes= new LinkedHashMap();
		positionColonne = new Hashtable();
		positionColonneInverse = new Hashtable();
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
	 * Ajoute une colonne au ResultSet, change son nom éventuellement et l'associe à une position
	 * @param aField : nom d'une colonne
	 * @return position de la colonne dans le ResultSet
	 */
	public int addColumn(String aField) {
		//  System.out.println("FolderResultSetMetaData.addColumn() "+aField);
		String uniqueField = this.getFinalFieldName(aField);
		FileFieldMetadata uniqueFieldDefinition = (FileFieldMetadata)((FileFieldMetadata)colonnesPossibles.get(aField)).clone();
		uniqueFieldDefinition.setColumnLabel(uniqueField);
			
		Integer positionEnCours = new Integer(colonnes.size());
		positionColonne.put(uniqueField, positionEnCours);
		positionColonneInverse.put(positionEnCours, uniqueField);
		colonnes.put(uniqueField, uniqueFieldDefinition);			

		return positionEnCours.intValue();
	}
	
	/**
	 * Ajoute une colonne au ResultSet, change son nom éventuellement et l'associe à une position
	 * @param aField : nom d'une colonne
	 * @return position de la colonne dans le ResultSet
	 */
	public int addLazyStringColumn(String aField) {
		//  System.out.println("FolderResultSetMetaData.addColumn() "+aField);
		String uniqueField = this.getFinalFieldName(aField);
		FileFieldMetadata uniqueFieldDefinition = new FileFieldMetadata(this.catalogue, String.class.getName(), 255, FILENAME_FIELD, FILENAME_FIELD, java.sql.Types.VARCHAR, "String", 255, 255, this.catalogue, this.catalogue, false, true, false, false, 0, false, true, false, true);
		uniqueFieldDefinition.setColumnLabel(uniqueField);
			
		Integer positionEnCours = new Integer(colonnes.size());
		positionColonne.put(uniqueField, positionEnCours);
		positionColonneInverse.put(positionEnCours, uniqueField);
		colonnes.put(uniqueField, uniqueFieldDefinition);			

		return positionEnCours.intValue();
	}	
	
	/**
	 * Ajoute toutes les colonnes possibles au dataset 
	 */
	public void addAllPossibleColumns() {
		
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
	 * @return un objet FileFieldMetadata correspondant aux métadata d'une colonne
	 */
	public FileFieldMetadata getColumnDefinitionByUniqueName(String aUniqueFieldName ) {
		//  System.out.println("FolderResultSetMetaData.getColumnDefinitionByUniqueName()");
		return (FileFieldMetadata)colonnes.get(aUniqueFieldName);
	}
	
	/**
	 * Retourne les métadata d'une colonne à partir de sa position
	 * @param aUniqueFieldName le nom de la colonne souhaitée
	 * @return un objet FileFieldMetadata correspondant aux métadata d'une colonne
	 */
	public FileFieldMetadata getColumnDefinitionByPosition(int aPosition ) throws SQLException {
		//  System.out.println("FolderResultSetMetaData.getColumnDefinitionByPosition()");
		return (FileFieldMetadata)colonnes.get(getColumnUniqueNameByPosition(aPosition));
	}

	/**
	 * Calcule et retourne le nom unique du champ si celui-ci est utilisé plusieurs fois dansla requête
	 * @param aField : nom d'une colonne 
	 * @return renvoie le nom unique de champ, à cette position pour ce ResultSet
	 */
	private String getFinalFieldName(String aField) {
		//  System.out.println("FolderResultSetMetaData.getFinalFieldName()");
		String finalFieldName = aField;
		if (colonnes.containsKey(aField)) {
		  int i = 1;
		  finalFieldName = aField + "_"+i;		  
		  while (colonnes.containsKey(finalFieldName)) {
			  finalFieldName = aField + "_"+ ++i ;
		  }
		} 
		return finalFieldName;		
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

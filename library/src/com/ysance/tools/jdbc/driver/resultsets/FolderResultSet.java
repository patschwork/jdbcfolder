package com.ysance.tools.jdbc.driver.resultsets;


import java.io.File;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;
import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.DatasetFieldNotFoundException;
import com.ysance.tools.jdbc.driver.resultsets.metadata.FieldMetadata;
import com.ysance.tools.jdbc.driver.resultsets.metadata.FolderResultSetMetaData;
import com.ysance.tools.jdbc.driver.resultsets.row.Row;
import com.ysance.tools.jdbc.driver.resultsets.row.RowFile;


public class FolderResultSet extends JdbcFolderAbstractResultSet {

	private java.io.File   catalogue;
	
	FolderResultSetMetaData metaData;
	ArrayList listeFiltres = new ArrayList();
	
	public FolderResultSet() {
		this.tableauLignes = new ArrayList();
	}

	public FolderResultSet(FolderResultSetMetaData aMetaData) {
		this();
		this.metaData = aMetaData;	
	}
	
	public FolderResultSet(String aCatalog) throws SQLException {
		this();	
		// Vérification existence répertoire		
		this.catalogue = new File(aCatalog);
		if (!this.catalogue.exists()) throw new JdbcFolderExceptions.TableDoesntExistException(this.catalogue.getAbsolutePath()); 
		if (!this.catalogue.isDirectory()) throw new JdbcFolderExceptions.TableIsNotDirectoryException(this.catalogue.getAbsolutePath()) ;

	    
		FolderResultSetMetaData metadata = (FolderResultSetMetaData)this.getMetaData(); 
		metadata.addAllPossibleColumns();		
		
		populateData();
		
	}
	
	public ResultSetMetaData getMetaData() throws SQLException {
		//  System.out.println("JdbcFolderResultSet.getMetaData()");
		if (metaData == null) {
			metaData = new FolderResultSetMetaData(catalogue.getAbsolutePath());
		}
		return metaData;
	}

	public long getLong(int aPosition) throws SQLException {		
		//  System.out.println("FolderResultSet.getLong()");
		return ((Long)this.getObject(aPosition)).longValue();
	}

	public long getLong(String aChamp) throws SQLException {
		//  System.out.println("FolderResultSet.getLong(String aChamp)");		
		return ((Long)this.getObject(aChamp)).longValue();
	}
	

	public double getDouble(int aPosition) throws SQLException {		
		//  System.out.println("FolderResultSet.getLong()");
		return ((Double)this.getObject(aPosition)).longValue();
	}

	public double getDouble(String aChamp) throws SQLException {
		//  System.out.println("FolderResultSet.getLong(String aChamp)");		
		return ((Double)this.getObject(aChamp)).longValue();
	}
	
	public String getString(int aPosition) throws SQLException {		
		//  System.out.println("FolderResultSet.getString()");
		return this.getObject(aPosition).toString();
	}

	public String getString(String aChamp) throws SQLException {
		 String result = getObject(aChamp).toString() ;
		 //System.out.println("FolderResultSet.getString(String aChamp) result : "+result);			 
		return result;
	}
	
	public Object getObject(int aPosition) throws SQLException {
		//  System.out.println("FolderResultSet.getObject(int aPosition)");
		return getObject(metaData.getColumnUniqueNameByPosition(aPosition - 1));
	}

	public Object getObject(String aChamp) throws SQLException {
		aChamp = aChamp.toUpperCase();
		//System.out.println("FolderResultSet.getObject(String aChamp)"+aChamp);
		FieldMetadata definitionColonne = metaData.getColumnDefinitionByUniqueName(aChamp.toUpperCase());
		if (definitionColonne == null) {
			throw new DatasetFieldNotFoundException(aChamp);			
		}
				
	  return ((Row)(tableauLignes.get(positionCurseur))).getData(definitionColonne.columnLabel);	
	}
	
	/**
	 * renvoie l'objet ligne correspondant à la position demandée.
	 * Pour rester cohérent avec les manipulations de lignes des résultset, la première ligne se trouve à la position 1
	 * @param aPositionLigne : la position de la ligne voulue dans le dataset
	 * @return
	 */
	protected Row getRow(int aPositionLigne) {
		return (Row)tableauLignes.get(aPositionLigne + 1);
	}
	
	/**
	 * renvoie l'objet ligne correspondant à la position demandée.
	 * Pour rester cohérent avec les manipulations de lignes des résultset, la première ligne se trouve à la position 1
	 * @param aPositionLigne : la position de la ligne voulue dans le dataset
	 * @return
	 */
	public void addRow(Row aNewRow) {
		tableauLignes.add(aNewRow);
	}	
	
	public void populateData() throws SQLException {

	    // Récupération des fichiers du répertoire "table"
		java.io.File[] liste = catalogue.listFiles();            
	    
	    ArrayList fichiers = new ArrayList(liste.length); 
	    for (int indexListeFile =0; indexListeFile < liste.length; indexListeFile++) {
	      fichiers.add(new RowFile(liste[indexListeFile]));	    
	    }
	    
	    this.tableauLignes  = fichiers;

	}

}

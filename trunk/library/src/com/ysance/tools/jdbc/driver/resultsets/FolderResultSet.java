package com.ysance.tools.jdbc.driver.resultsets;


import java.io.File;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;
import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.DatasetFieldNotFoundException;
import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.NoClauseFoundException;
import com.ysance.tools.jdbc.driver.resultsets.metadata.FolderResultSetMetaData;
import com.ysance.tools.jdbc.driver.resultsets.row.RowFile;
import com.ysance.tools.jdbc.driver.sql.SQLFormatter;
import com.ysance.tools.jdbc.driver.sql.SQLValidator;

public class FolderResultSet extends JdbcFolderAbstractResultSet {

	private java.io.File   catalogue;
	
	FolderResultSetMetaData metaData;
	ArrayList listeFiltres = new ArrayList();	
	
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
	
	public String getString(int aPosition) throws SQLException {		
		//  System.out.println("FolderResultSet.getString()");
		return this.getObject(aPosition).toString();
	}

	public String getString(String aChamp) throws SQLException {
		//  System.out.println("FolderResultSet.getString(String aChamp)");		
		return getObject(aChamp).toString();
	}
	
	public Object getObject(int aPosition) throws SQLException {
		//  System.out.println("FolderResultSet.getObject(int aPosition)");
		return getObject(metaData.getColumnUniqueNameByPosition(aPosition - 1));
	}

	public Object getObject(String aChamp) throws SQLException {
		aChamp = aChamp.toUpperCase();
		//  System.out.println("FolderResultSet.getObject(String aChamp)");
		FolderResultSetMetaData.FileFieldMetadata definitionColonne = metaData.getColumnDefinitionByUniqueName(aChamp.toUpperCase());
		if (definitionColonne == null) {
			throw new DatasetFieldNotFoundException(aChamp);			
		}
				
	  return ((RowFile)(tableauLignes[positionCurseur])).getData(definitionColonne.columnName);	
	}	
	
	public void populateData(String aRequete) throws SQLException {
		/*aRequete = SQLFormatter.upperCaseSQLWordsAndFields(aRequete); 
		int positionSelect = aRequete.indexOf(SQLFormatter.SELECT_WORD);  
		int positionFrom   = aRequete.indexOf(SQLFormatter.FROM_WORD);
		int positionWhere   = aRequete.indexOf(SQLFormatter.WHERE_WORD);
		
		if ( positionSelect < 0 ) throw new JdbcFolderExceptions.NoSelectWordFoundException();
		if ( positionFrom < 0 )   throw new JdbcFolderExceptions.NoFromWordFoundException();

		if (positionWhere > 0) {			
		  clauseWhere =  aRequete.substring(positionWhere + SQLFormatter.WHERE_WORD.length());
				
		  if (clauseWhere.trim().length() == 0) throw new NoClauseFoundException();
		}*/	
		
		SQLValidator validator = new SQLValidator(aRequete);		
		
		//String cheminCatalogue = aRequete.substring(positionFrom + SQLFormatter.FROM_WORD.length(), positionWhere < 0 ? aRequete.length() : positionWhere).trim();
		
		String cheminCatalogue = validator.getCatalogPath();
		
		String clauseWhere = validator.getWhereClause();  
		
		// Vérification existence répertoire		
		File newCatalogue = new File(cheminCatalogue);
		if (!newCatalogue.exists()) throw new JdbcFolderExceptions.TableDoesntExistException(newCatalogue.getAbsolutePath()); 
		if (!newCatalogue.isDirectory()) throw new JdbcFolderExceptions.TableIsNotDirectoryException(newCatalogue.getAbsolutePath()) ;
					
		this.catalogue = newCatalogue;		
		
		StringTokenizer champs = validator.getFields();

		// Détermination des champs à retourner
		while (champs.hasMoreTokens()) {
			String champ = champs.nextToken().trim();
			FolderResultSetMetaData metadata = (FolderResultSetMetaData)this.getMetaData(); 
			if (SQLFormatter.JOKER_WORD.equals(champ)) {
				metadata.addAllPossibleColumns();
			} else {
				if (!metadata.recognizeField(champ)){
					throw new JdbcFolderExceptions.FieldNotFoundException(champ);				
				} else {
					metadata.addColumn(champ);
				}
			}
		}
		
	    // Récupération des fichiers du répertoire "table"
		java.io.File[] liste = catalogue.listFiles();            
	    
	    RowFile[] fichiers = new RowFile[liste.length]; 
	    for (int indexListeFile =0; indexListeFile < liste.length; indexListeFile++) {
	      fichiers[indexListeFile] = new RowFile(liste[indexListeFile]);
	    }
	    
	    
	    // Application des filtres de la clause where 
	    java.util.ArrayList indexFichiers = new java.util.ArrayList();
	    
	    if (fichiers.length > 0 && clauseWhere.trim().length() > 0 ) {
	      Context cx = Context.enter();
	
	      try {
	          Scriptable scope = cx.initStandardObjects();
	          	
	          String ligneFichier = "fichiers[i].";
	          String filtre = clauseWhere;
	          filtre = filtre.replaceAll(FolderResultSetMetaData.SIZE_FIELD, ligneFichier+RowFile.getMethodForField(FolderResultSetMetaData.SIZE_FIELD));
	          filtre = filtre.replaceAll(FolderResultSetMetaData.FILENAME_FIELD, ligneFichier+RowFile.getMethodForField(FolderResultSetMetaData.FILENAME_FIELD));
	          filtre = filtre.replaceAll(FolderResultSetMetaData.EXTENSION_FIELD, ligneFichier+RowFile.getMethodForField(FolderResultSetMetaData.EXTENSION_FIELD));            
	          
	          String requete = " for (i = 0; i < fichiers.length; i++ ) {if ( " +filtre + " ) indexFichiers.add(new java.lang.Integer(i))}";
	                      
	          System.out.println(requete);        
	          
	          Scriptable jsArgs = Context.toObject(fichiers, scope);
	          scope.put("fichiers", scope, jsArgs);
	
	          Scriptable jsArgsindexFichiers = Context.toObject(indexFichiers, scope);
	          scope.put("indexFichiers", scope, jsArgsindexFichiers);           
	          
	          String s = requete;
	
	          Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);
	                   
	          System.err.println(cx.toString(result));
	      } catch (Exception ex) {
	      	ex.printStackTrace();    
	      } finally {
	        Context.exit();
	      }  	
	    } else {
	      for (int i = 0; i < liste.length; i++ ) {
	      	indexFichiers.add(new java.lang.Integer(i));
	      }      
	    }
	    RowFile[] fichiersFiltres = new RowFile[indexFichiers.size()]; 
	    for (int indexFichier=0; indexFichier < indexFichiers.size(); indexFichier++) {
	      //  System.out.println("fichier ajouté : " +fichiers[((Integer)(indexFichiers.get(indexFichier))).intValue()]);
	      fichiersFiltres[indexFichier] = fichiers[((Integer)(indexFichiers.get(indexFichier))).intValue()];
	    }
    
		this.tableauLignes  = fichiersFiltres;  
	}

}

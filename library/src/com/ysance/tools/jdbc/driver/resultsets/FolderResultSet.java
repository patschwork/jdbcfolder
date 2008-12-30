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
	
	public String getString(int aPosition) throws SQLException {		
		//  System.out.println("FolderResultSet.getString()");
		return this.getObject(aPosition).toString();
	}

	public String getString(String aChamp) throws SQLException {
		 System.out.println("FolderResultSet.getString(String aChamp)"+aChamp);	
		 String result = getObject(aChamp).toString() ;
		 System.out.println("FolderResultSet.getString(String aChamp) result : "+result);			 
		return result;
	}
	
	public Object getObject(int aPosition) throws SQLException {
		//  System.out.println("FolderResultSet.getObject(int aPosition)");
		return getObject(metaData.getColumnUniqueNameByPosition(aPosition - 1));
	}

	public Object getObject(String aChamp) throws SQLException {
		aChamp = aChamp.toUpperCase();
		  System.out.println("FolderResultSet.getObject(String aChamp)"+aChamp);
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

		//String cheminCatalogue = aValidator.getCatalogPath();
		
							
		/*File newCatalogue = new File(cheminCatalogue);
		if (!newCatalogue.exists()) throw new JdbcFolderExceptions.TableDoesntExistException(newCatalogue.getAbsolutePath()); 
		if (!newCatalogue.isDirectory()) throw new JdbcFolderExceptions.TableIsNotDirectoryException(newCatalogue.getAbsolutePath()) ;*/

		//this.catalogue = newCatalogue;		

	    // Récupération des fichiers du répertoire "table"
		java.io.File[] liste = catalogue.listFiles();            
	    
	    ArrayList fichiers = new ArrayList(liste.length); 
	    for (int indexListeFile =0; indexListeFile < liste.length; indexListeFile++) {
	      fichiers.add(new RowFile(liste[indexListeFile]));	    
	    }
	    
		//FolderResultSetMetaData metadata = (FolderResultSetMetaData)this.getMetaData(); 
		//metadata.addAllPossibleColumns();	    
	    
	    this.tableauLignes  = fichiers;
	    
	    //((FolderResultSetMetaData)this.getMetaData()).addAllPossibleColumns();
		
		/*StringTokenizer champs = aValidator.getFields();

		// Détermination des champs à retourner
		while (champs.hasMoreTokens()) {
			String champ = champs.nextToken().trim();
			FolderResultSetMetaData metadata = (FolderResultSetMetaData)this.getMetaData(); 
			if (SQLGrammar.JOKER_WORD.equals(champ.trim())) {
				metadata.addAllPossibleColumns();
			} else {
				if (!metadata.recognizeField(champ)){
					throw new JdbcFolderExceptions.FieldNotFoundException(champ);				
				} else {
					metadata.addColumn(champ);
				}
			}
		}  
	    
	    
	    String clauseWhere = JavaScriptFilterFormatter.format(aValidator.getWhereClause());  
		
	    // Application des filtres de la clause where 
	    java.util.ArrayList indexFichiers = new java.util.ArrayList();
	    
	    // S'il y a une clause where à traiter, c'est fait ici
	    if (fichiers.length > 0 && clauseWhere.trim().length() > 0 ) {
	      Context cx = Context.enter();
	
	      try {
	          Scriptable scope = cx.initStandardObjects();
	          	
	          String ligneFichier = "fichiers[i].";
	          String filtre = clauseWhere;
	          filtre = filtre.replaceAll(FolderResultSetMetaData.SIZE_FIELD, ligneFichier+RowFile.getMethodForField(FolderResultSetMetaData.SIZE_FIELD));
	          filtre = filtre.replaceAll(FolderResultSetMetaData.FILENAME_FIELD, ligneFichier+RowFile.getMethodForField(FolderResultSetMetaData.FILENAME_FIELD));
	          filtre = filtre.replaceAll(FolderResultSetMetaData.EXTENSION_FIELD, ligneFichier+RowFile.getMethodForField(FolderResultSetMetaData.EXTENSION_FIELD));            
	          
	          String requete = " for (i = 0; i < fichiers.length; i++ ) { \n"+
	                           "   if ( " +filtre + " ) indexFichiers.add(new java.lang.Integer(i))\n"+
	                           " }";
	                      
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
  	      // S'il n'y a aucune clause where à traiter, on met toutes les lignes
	      for (int i = 0; i < liste.length; i++ ) {
	      	indexFichiers.add(new java.lang.Integer(i));
	      }      
	    }
	    RowFile[] fichiersFiltres = new RowFile[indexFichiers.size()]; 
	    for (int indexFichier=0; indexFichier < indexFichiers.size(); indexFichier++) {
	      //  System.out.println("fichier ajouté : " +fichiers[((Integer)(indexFichiers.get(indexFichier))).intValue()]);
	      fichiersFiltres[indexFichier] = fichiers[((Integer)(indexFichiers.get(indexFichier))).intValue()];
	    }

	    
		this.tableauLignes  = fichiersFiltres;  */
	}

}

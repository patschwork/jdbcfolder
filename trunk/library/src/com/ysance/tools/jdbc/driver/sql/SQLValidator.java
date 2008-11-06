package com.ysance.tools.jdbc.driver.sql;

import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;
import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.NoClauseFoundException;
import com.ysance.tools.jdbc.driver.resultsets.metadata.FolderResultSetMetaData;
import com.ysance.tools.jdbc.driver.resultsets.row.RowFile;

public class SQLValidator implements SQLGrammar, ParsingUtilities {
	
	ArrayList requete;
	int positionSelect   = -1;  
	int positionFrom     = -1;
	int positionWhere    = -1;
	int positionOrderBy  = -1;
	int positionGroupBy  = -1;
	
	public SQLValidator (String newRequete) throws SQLException {
		try {
			this.requete = SQLFormatter.upperCaseSQLWordsAndFields(newRequete);
		} catch (Exception ex) {
			throw new JdbcFolderExceptions.GenericBadRequestException();
		}
		positionSelect = this.requete.indexOf(new RequestWord(SQLFormatter.SELECT_WORD, RequestWord.KIND_SQL_WORD));  
		positionFrom   = this.requete.indexOf(new RequestWord(SQLFormatter.FROM_WORD, RequestWord.KIND_SQL_WORD));
		positionWhere  = this.requete.indexOf(new RequestWord(SQLFormatter.WHERE_WORD, RequestWord.KIND_SQL_WORD));
		
		if ( positionSelect < 0 ) throw new JdbcFolderExceptions.NoSelectWordFoundException();
		if ( positionSelect > 0 ) throw new JdbcFolderExceptions.RequestMustBeginBySelectException();
		if ( positionFrom < 0 )   throw new JdbcFolderExceptions.NoFromWordFoundException();
		
	}
	
	/**
	 * 
	 * @return a HashMap containing, for each expression in the SELECT clause, the alias as the key and a RequestFieldSelected as the value
	 */
	/*public StringTokenizer getFields() {
		StringBuffer fields = new StringBuffer();
		int finFields  = positionFrom;
		for ( int index = positionSelect + 1; index < finFields ; index++ ) {
			System.out.println(requete.get(index));
			fields.append(requete.get(index));
			fields.append(" ");
		}
		return new StringTokenizer(fields.toString(), ",");
		//return new StringTokenizer(this.requete.substring(positionSelect + SQLFormatter.SELECT_WORD.length(), positionFrom), ",");	
	}*/
	
	/**
	 * 
	 * @return a HashMap containing, for each expression in the SELECT clause, the alias as the key and a RequestFieldSelected as the value
	 */
	public ArrayList getFields() {
		int finFields  = positionFrom;
		
		
		ArrayList listeChamps = new ArrayList();
		HashMap listeAliasChamps = new HashMap();
		
		for ( int index = positionSelect + 1; index < finFields ; index++ ) {
			RequestFieldSelected aSelectedField = new RequestFieldSelected();
			
			// La règle :
			// EXPRESSION [separateur alias] [alias]
			// Tant qu'on arrive pas à un séparateur de groupe, on alimente l'expression de aSelectedField
			// C'est cette variable qui nous donnera ensuite les infos nécessaires
			RequestWord aRequestWord = null;
			
			boolean finDeGroupe = false;
			
			while (!finDeGroupe && index < finFields ) {
				aRequestWord = (RequestWord)requete.get(index);
				
				finDeGroupe = aSelectedField.addWord(aRequestWord);
				
				if (!finDeGroupe )
				  index++;				
			}
			
			// Si le champ n'est pas vide, on l'ajoute à la liste
			if (aSelectedField.getWordCount() > 0) {
				listeChamps.add(aSelectedField);
				
				String aliasOrigine = aSelectedField.getAlias();
				
				Integer numeroAlias = new Integer(1);
				// On affecte ici l'alias définitif
				if (listeAliasChamps.containsKey(aliasOrigine)) {
					numeroAlias = (Integer)listeAliasChamps.get(aliasOrigine);

					aSelectedField.setAlias(aliasOrigine+"_"+numeroAlias.toString());
					numeroAlias = new Integer(numeroAlias.intValue() + 1);
				}
				listeAliasChamps.put(aliasOrigine,numeroAlias);

				System.out.println(aSelectedField.toString());	            
			}
		}
				
		return listeChamps;
		//return new StringTokenizer(this.requete.substring(positionSelect + SQLFormatter.SELECT_WORD.length(), positionFrom), ",");	
	}	
	
	/**
	 * Return the WHERE clause as a String
	 * This form is used when several datasets are present in the request, therefore no one can be determined as default
	 */ 
	public String getWhereClause() {
		return this.getWhereClause(null);
	}

	/**
	 * Return the WHERE clause as a String
	 * @param aTableAlias : The default alias used to prefix fields, if null, no default dataset
	 * @return The request with fields prefixed with their dataset's alias and   
	 */ 
	public String getWhereClause(String aTableAlias) {
		if (aTableAlias == null ) {
			aTableAlias = "";
		} else {
			aTableAlias = aTableAlias+".";
		}
		StringBuffer whereClause = new StringBuffer();		
		if ( positionWhere >= 0 ) {
			int finWhere  = positionGroupBy;
			finWhere  = finWhere == -1 ? positionOrderBy : finWhere;
			finWhere  = finWhere == -1 ? requete.size()  : finWhere;
			for ( int index = positionWhere + 1; index < finWhere ; index++ ) {
				RequestWord aRequestWord = (RequestWord)requete.get(index);
				
				/*if ( aRequestWord.kind == RequestWord.KIND_UNKNOWN ) {
					aRequestWord.word = aRequestWord.word.replaceAll(FolderResultSetMetaData.SIZE_FIELD, aTableAlias+RowFile.getMethodForField(FolderResultSetMetaData.SIZE_FIELD));
					aRequestWord.word = aRequestWord.word.replaceAll(FolderResultSetMetaData.FILENAME_FIELD, aTableAlias+RowFile.getMethodForField(FolderResultSetMetaData.FILENAME_FIELD));
					aRequestWord.word = aRequestWord.word.replaceAll(FolderResultSetMetaData.EXTENSION_FIELD, aTableAlias+RowFile.getMethodForField(FolderResultSetMetaData.EXTENSION_FIELD));            
				}*/
				/*if ( aRequestWord.kind == RequestWord.KIND_UNKNOWN ) {
					aRequestWord.word = aRequestWord.word.replaceAll(FolderResultSetMetaData.SIZE_FIELD, FolderResultSetMetaData.SIZE_FIELD+"()");
					aRequestWord.word = aRequestWord.word.replaceAll(FolderResultSetMetaData.FILENAME_FIELD, FolderResultSetMetaData.FILENAME_FIELD+"()");
					aRequestWord.word = aRequestWord.word.replaceAll(FolderResultSetMetaData.EXTENSION_FIELD, FolderResultSetMetaData.EXTENSION_FIELD+"()");            
				}	*/			
				//System.out.println(aRequestWord.word);
				whereClause.append(aRequestWord.word);
				whereClause.append(" ");
			}
		}
		return whereClause.toString();
	}	
	/**
	 * Retourne les différentes sources de données
	 * 	La HashMap contient en clé l'alias du catalogue donné par l'utilisateur,
	 *   le timestamp sinon 
	 * @return
	 */
	public HashMap getCatalogs() throws SQLException{		
		int finFrom  = positionWhere;
		finFrom  = finFrom == -1 ? positionGroupBy : finFrom;
		finFrom  = finFrom == -1 ? positionOrderBy : finFrom;
		finFrom  = finFrom == -1 ? requete.size()  : finFrom;
				

		HashMap catalogues = new HashMap();		
		ArrayList listeCatalogues = new ArrayList();
		
		int groupe = AUCUN_GROUPE;
		int[] compteurGroupe = {0,0};
		boolean creerCatalogue = false;
		int catalogKind = RequestCatalog.CATALOG_KIND_SINGLE;
		StringBuffer catalogue = new StringBuffer();

		//System.out.println(listeCatalogues.toString());
		
		// On boucle sur chacune des portions de requete définissant la clause from
		for ( int index = positionFrom + 1; index < finFrom ; index++ ) {
			String portionRequete = requete.get(index).toString();
			// On travaille sur chaque lettre
			for ( int indexLettre = 0; indexLettre < portionRequete.length(); indexLettre++ ) {
				creerCatalogue = false;
				char caractereEnCours = portionRequete.charAt(indexLettre);
				int typeOuvertureGroupe = Arrays.binarySearch(ouvertureGroupeCatalogue,caractereEnCours);
				int typeFermetureGroupe = Arrays.binarySearch(fermetureGroupeCatalogue,caractereEnCours);
				
				// Si on est dans un groupe
				if (groupe != AUCUN_GROUPE) {
					// Si on a un caractère de fermeture correspondant au type de groupe, on clôt le groupe
					if ( groupe == typeFermetureGroupe ) {
						groupe = AUCUN_GROUPE;
					}
				} 
				// Si on est pas dans un groupe
				else {
					// Si on a un caractère d'ouverture de groupe
					if ((typeOuvertureGroupe > AUCUN_GROUPE)) {
						groupe = typeOuvertureGroupe;
						catalogKind = RequestCatalog.CATALOG_KIND_GROUP;
					} else {
						creerCatalogue = Arrays.binarySearch(separateursGroupe,caractereEnCours) >= 0 ;
					}
				}

				// Soit on ajoute un catalogue, soit on ajoute la caractère au catalogue en cours
				if (creerCatalogue) {
					listeCatalogues.add(new RequestCatalog(catalogue,catalogKind));
					catalogue = new StringBuffer();
					catalogKind = RequestCatalog.CATALOG_KIND_SINGLE;
				} else {
					catalogue.append(caractereEnCours);					
				}				
			}
		}
		if ( catalogue.toString().length() > 0 )  {
			listeCatalogues.add(new RequestCatalog(catalogue,catalogKind));
		}
		if (listeCatalogues.size() == 0 ) {
			throw new JdbcFolderExceptions.NoCatalogFoundException();
		}

		// On calcule les alias des différents catalogues
		for (int i = 0; i < listeCatalogues.size(); i++) {
			RequestCatalog catalogueEnCours = (RequestCatalog)listeCatalogues.get(i);
			
			// Pour l'alias du dataset, on prend le dernier mot. Sauf si on est dans le cadre d'un groupe, dans ce cas, 
			String[] motsCatalogue = catalogueEnCours.toString().trim().split(" ");

			String dataSet = "";
			
			String dernierMot = motsCatalogue[motsCatalogue.length - 1].trim();			
			boolean dernierMotGroupe = Arrays.binarySearch(fermetureGroupeCatalogue,dernierMot.charAt(dernierMot.length()-1)) >= 0;
				
			int nbMots = motsCatalogue.length;
			String aliasDataSet = "DATASET_"+i; 
			
			// Si on a plusieurs mots, mais que ce n'est pas un groupe, on prend le dernier mot
			// dans tous les autres cas, on génère un nom
			if (motsCatalogue.length > 1 && !dernierMotGroupe ) {
				nbMots = motsCatalogue.length - 1;
				aliasDataSet = dernierMot; 
			} 
			for (int j = 0; j < nbMots; j++) {
				dataSet = dataSet + " " + motsCatalogue[j];
			}
			
			// ici, on associe le catalogue et son alias
			// On recrée un nouvel objet requestCatalog
			catalogues.put(aliasDataSet, new RequestCatalog(new StringBuffer(dataSet.trim()),catalogueEnCours.getCatalogKind()));
		}
		
		return catalogues;
	}	

}

package com.ysance.tools.jdbc.driver.sql;

import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;
import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.NoClauseFoundException;

public class SQLValidator implements SQLGrammar {
	
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

		/*if (positionWhere > 0) {			
		  clauseWhere =  this.requete.substring(positionWhere + SQLFormatter.WHERE_WORD.length());
				
		  if (clauseWhere.trim().length() == 0) throw new NoClauseFoundException();
		}	*/
		
		/*StringBuffer requeteRetournee = new StringBuffer();
		for (int i = 0; i < requeteDecoupee.size(); i++) {
			RequestWord motRequete = (RequestWord)requeteDecoupee.get(i);
			// Si c'est une valeur, on la laisse telle quelle
			if (motRequete.kind == RequestWord.KIND_UNKNOWN) {
				requeteRetournee.append(motRequete.word);				
			} else {
				
				String motMajuscule = motRequete.word.toUpperCase();
				if (   (    (motMajuscule.trim().length() == 2)
						 && (motMajuscule.startsWith(AND_OPERATOR) || motMajuscule.startsWith(OR_OPERATOR)
					   )
					|| (    (motMajuscule.trim().length() > 2 )
						 && (    (motMajuscule.startsWith(AND_OPERATOR) && " ".equals(motMajuscule.substring(3,4)))
						      || (motMajuscule.startsWith(OR_OPERATOR) && " ".equals(motMajuscule.substring(2,3))))))) {
					if (motMajuscule.startsWith(AND_OPERATOR)) {
						motMajuscule = AND_JAVA_OPERATOR+ motMajuscule.substring(3);
					}
					if (motMajuscule.startsWith(OR_OPERATOR)) {
						motMajuscule = OR_JAVA_OPERATOR+ motMajuscule.substring(2);
					}
				}
				motMajuscule = motMajuscule.replaceAll("\\(",               " ( ");            				
				motMajuscule = motMajuscule.replaceAll("\\)",               " ) ");            								
				motMajuscule = motMajuscule.replaceAll(" "+AND_OPERATOR+" "," "+AND_JAVA_OPERATOR+" ");            
				motMajuscule = motMajuscule.replaceAll(" "+ OR_OPERATOR+" "," "+ OR_JAVA_OPERATOR+" ");            
				motMajuscule = motMajuscule.replaceAll("=",               "==");            
				requeteRetournee.append(motMajuscule);
			}
		}	*/	
	}
	
	public String getCatalogPath() {
		StringBuffer catalogue = new StringBuffer();
		int finFrom  = positionWhere;
		finFrom  = finFrom == -1 ? positionGroupBy : finFrom;
		finFrom  = finFrom == -1 ? positionOrderBy : finFrom;
		finFrom  = finFrom == -1 ? requete.size()  : finFrom;
		for ( int index = positionFrom + 1; index < finFrom ; index++ ) {
			catalogue.append(requete.get(index));
			catalogue.append(" ");
		}
		return catalogue.toString();

		//return this.requete.substring(positionFrom + SQLFormatter.FROM_WORD.length(), positionWhere < 0 ? this.requete.length() : positionWhere).trim();
	}
	
	public StringTokenizer getFields() {
		StringBuffer fields = new StringBuffer();
		int finFields  = positionFrom;
		for ( int index = positionSelect + 1; index < finFields ; index++ ) {
			fields.append(requete.get(index));
			fields.append(" ");
		}
		return new StringTokenizer(fields.toString(), ",");
		//return new StringTokenizer(this.requete.substring(positionSelect + SQLFormatter.SELECT_WORD.length(), positionFrom), ",");	
	}
	
	// retourne la clause WHERE sous forme de chaine
	public String getWhereClause() {
		StringBuffer whereClause = new StringBuffer();		
		if ( positionWhere >= 0 ) {
			int finWhere  = positionGroupBy;
			finWhere  = finWhere == -1 ? positionOrderBy : finWhere;
			finWhere  = finWhere == -1 ? requete.size()  : finWhere;
			for ( int index = positionWhere + 1; index < finWhere ; index++ ) {
				whereClause.append(requete.get(index));
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
	public HashMap getCatalogs() {		
		HashMap catalogues = new HashMap();
		
		ArrayList listeCatalogues = new ArrayList();

		int finFrom  = positionWhere;
		finFrom  = finFrom == -1 ? positionGroupBy : finFrom;
		finFrom  = finFrom == -1 ? positionOrderBy : finFrom;
		finFrom  = finFrom == -1 ? requete.size()  : finFrom;
		
		//String clauseFrom = requete.su;

		// Le caractère de fermeture correspondant à un caractère d'ouverture doit se trouver à la même position dans le tableau 
		char[] ouvertureGroupe = {'"','('};
		char[] fermetureGroupe = {'"',')'};
		
		char[] separateurs = {','};		
				
		final int AUCUN_GROUPE = -1;
		int groupe = AUCUN_GROUPE;
		int indexLettre = 0;
		int[] compteurGroupe = {0,0};
		boolean creerCatalogue = false;
		StringBuffer catalogue = new StringBuffer();

		//System.out.println(listeCatalogues.toString());
		
		// On boucle sur chacune des portions de requete définissant la clause from
		for ( int index = positionFrom + 1; index < finFrom ; index++ ) {
			String portionRequete = requete.get(index).toString();
			// On travaille sur chaque lettre
			for ( indexLettre = 0; indexLettre < portionRequete.length(); indexLettre++ ) {
				creerCatalogue = false;
				char caractereEnCours = portionRequete.charAt(indexLettre);
				int typeOuvertureGroupe = Arrays.binarySearch(ouvertureGroupe,caractereEnCours);
				int typeFermetureGroupe = Arrays.binarySearch(fermetureGroupe,caractereEnCours);
				
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
					} else {
						creerCatalogue = Arrays.binarySearch(separateurs,caractereEnCours) >= 0 ;
					}
				}

				// Soit on ajoute un catalogue, soit on ajoute la caractère au catalogue en cours
				if (creerCatalogue) {
					listeCatalogues.add(catalogue);
					catalogue = new StringBuffer();
				} else {
					catalogue.append(caractereEnCours);					
				}				
			}
		}		
		listeCatalogues.add(catalogue);

		for (int i = 0; i < listeCatalogues.size(); i++) {
			// Pour l'alias du dataset, on prend le dernier mot. Sauf si on est dans le cadre d'un groupe, dans ce cas, 
			String[] motsCatalogue = listeCatalogues.get(i).toString().trim().split(" ");

			String dataSet = "";
			
			String dernierMot = motsCatalogue[motsCatalogue.length - 1].trim();
			boolean dernierMotGroupe = Arrays.binarySearch(fermetureGroupe,dernierMot.charAt(dernierMot.length()-1)) >= 0;
				
			int nbMots = motsCatalogue.length;
			String aliasDataSet = "DATASET_"+i; 
			
			// Si on a plusieurs mots, mais que ce n'est pas un groupe, on prend le dernier mot
			// dans tous les autres cas, on génère un nom
			if (motsCatalogue.length > 1 && !dernierMotGroupe ) {
				nbMots = motsCatalogue.length - 1;
				aliasDataSet = dernierMot; 
			} 
			for (int j = 0; j < nbMots; j++) {
				dataSet = dataSet + motsCatalogue[j];
			}
			
			catalogues.put(aliasDataSet, dataSet);
		}
		
		return catalogues;
	}	

}

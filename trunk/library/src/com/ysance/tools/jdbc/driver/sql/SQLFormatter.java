package com.ysance.tools.jdbc.driver.sql;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

import com.ysance.tools.jdbc.driver.JdbcFolderGrammar;

public class SQLFormatter implements SQLGrammar {	
	
	/**
	 * Méthode devant retourner le nom des champs et les commandes SQL en majuscule mais pas les autres données
	 * Voir comment utiliser Antlr pour faire ceci
	 * @param aRequete
	 * @return
	 */
	public static ArrayList upperCaseSQLWordsAndFields(String aRequete) {
		
		char[] separateursMots = {' ',')','(','\n','\t'};
		
		Arrays.sort(separateursMots);
		
		//HashMap elements = new HashMap();
		
		// On protège le nom des "tables" en les entourant de doubles quotes
		/*String requeteMajuscules = aRequete.toUpperCase(); 
		int positionFrom   = requeteMajuscules.indexOf(FROM_WORD);		
		aRequete = aRequete.substring(0, positionFrom + FROM_WORD.length() )+" \" "+aRequete.substring(positionFrom + FROM_WORD.length() );
		
		requeteMajuscules = aRequete.toUpperCase(); 
		int positionWhere   = requeteMajuscules.indexOf(WHERE_WORD);
		if ( positionWhere > 0 ) {
			aRequete = aRequete.substring(0, positionWhere )+" \" "+aRequete.substring(positionWhere);
		} else {			
			aRequete = aRequete+" \" ";
		}*/
		
		final int AUCUNE_PROTECTION = 0; 
		final int SIMPLE_APOSTROPHE = 1; 
		final int DOUBLE_APOSTROPHE = 2; 		
		final int DANS_CLAUSE_FROM  = 3;
			
		final String SIMPLE_APOSTROPHE_STRING = "'"; 
		final String DEUX_APOSTROPHES_STRING = "''"; 		

		// Remplacement de 2 apostrophes par £doubleapos£
		//aRequete = aRequete.replaceAll("''", SQLGrammar.JDBCFOLDER_DOUBLE_QUOTES);
		
		int indexDerniereApostrophe = aRequete.indexOf(SIMPLE_APOSTROPHE_STRING);
		int indexDoubleApostrophe = 0;

		while ( aRequete.indexOf(DEUX_APOSTROPHES_STRING, indexDerniereApostrophe) > 0 ) {
			indexDerniereApostrophe = aRequete.indexOf(SIMPLE_APOSTROPHE_STRING,indexDerniereApostrophe + 1);
			indexDoubleApostrophe = aRequete.indexOf(DEUX_APOSTROPHES_STRING,indexDerniereApostrophe);
			
			if (indexDerniereApostrophe == indexDoubleApostrophe) {
				aRequete = aRequete.substring(0, indexDoubleApostrophe) + aRequete.substring(indexDoubleApostrophe).replaceFirst(DEUX_APOSTROPHES_STRING, JdbcFolderGrammar.JDBCFOLDER_DOUBLE_QUOTES);
			} else {
				indexDerniereApostrophe = aRequete.indexOf(SIMPLE_APOSTROPHE_STRING,indexDerniereApostrophe + 1);				
			}
		}		
		
		/**
		 * @see com.ysance.tools.jdbc.driver.test.TestDoublesQuotes
		 */
		
		
		// Découpage de la requete en protégeant les données entre simples et doubles quotes
		ArrayList requeteDecoupee = new ArrayList(); 
		char[] caracteresRequete = aRequete.toCharArray();
		int chaineProtegee = AUCUNE_PROTECTION;
		boolean premierCaractereNonEspaceTrouve = false;

		// Stockage des mots dans une ArrayList
		StringBuffer mot = new StringBuffer();
		for (int indexLettre = 0; indexLettre < caracteresRequete.length; indexLettre++) {
			char c = caracteresRequete[indexLettre];
			// On supprime les blancs en début de chaine
			if (!premierCaractereNonEspaceTrouve) {
				if ( Arrays.binarySearch(separateursMots,c) > -1 ) {
					continue;							
				} else {
					premierCaractereNonEspaceTrouve = true;
				}
			}
			
			// Si on est dans une chaine de caractères ou un lieu protégé
			if (chaineProtegee > 0) {
				// On ajoute la lettre au mot
				mot.append(c);	
				boolean fermerChaine = false;
				// Si on ferme la chaine ou qu'on arrive à la fin, on ajoute l'ajoute à la liste et on réinitialise le mot
				if ( chaineProtegee == SIMPLE_APOSTROPHE || chaineProtegee == DOUBLE_APOSTROPHE ) {
					if ((c == '\'' &&  chaineProtegee == SIMPLE_APOSTROPHE) ||
						(c == '\"' &&  chaineProtegee == DOUBLE_APOSTROPHE) || 
						 indexLettre == caracteresRequete.length - 1) {
						fermerChaine = true;
					}
				} else {
					// Déprotéger si where, order by, group by ou fin de chaine
					if ((Arrays.binarySearch(separateursMots,c) > -1) || indexLettre == caracteresRequete.length - 1) {
						if ( indexLettre < caracteresRequete.length - 1 ) {
							String prochainMot = aRequete.substring(indexLettre + 1, indexLettre + 1 + 5).toUpperCase(); 
							if ( WHERE_WORD.equals(prochainMot) || 
								 GROUP_BY_WORD_1_ON_2.equals(prochainMot) || 
								 ORDER_BY_WORD_1_ON_2.equals(prochainMot))  {
								fermerChaine = true;
							}
						} else {
							// Si on est en bout de requete
							fermerChaine = true;							
						}
					}					
				}
				if (fermerChaine) {
					chaineProtegee = AUCUNE_PROTECTION;
				    requeteDecoupee.add(new RequestWord(mot.toString(), RequestWord.KIND_PROTECTED)); 
				    mot = new StringBuffer();
				}

			} else {
			  mot.append(c);				
			  // Si on entre dans une chaine
			  if ((c == '\'') ||( c == '\"')) {
				  chaineProtegee = (c == '\'') ? SIMPLE_APOSTROPHE : DOUBLE_APOSTROPHE ;
			  } else {
				  // Si on trouve un séparateur de mots ou qu'on arrive à la dernière lettre de la requête, on ferme le mot courant
				  //if ( c == ' ' || indexLettre == caracteresRequete.length - 1) {
				  if ( Arrays.binarySearch(separateursMots,c) > -1 || indexLettre == caracteresRequete.length - 1) {				  
					  String motInsere = mot.toString().trim().toUpperCase();
					  int typeMot = RequestWord.KIND_UNKNOWN;
					  //requeteDecoupee.add(motInsere);
					  if (SELECT_WORD.equals( motInsere)) {
						  typeMot = RequestWord.KIND_SQL_WORD;
					  }
					  if (FROM_WORD.equals( motInsere)) {
						  chaineProtegee = DANS_CLAUSE_FROM;
						  typeMot = RequestWord.KIND_SQL_WORD;
					  }
					  // Si on trouve le where, on n'est plus dans la clause from 
					  if (WHERE_WORD.equals(motInsere)) {
						  chaineProtegee = AUCUNE_PROTECTION;
						  typeMot = RequestWord.KIND_SQL_WORD;
					  }					  
					  requeteDecoupee.add(new RequestWord(motInsere, typeMot));
					  mot = new StringBuffer();
				  }
			  }			
			}
		}		

		return requeteDecoupee;
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
		}
		
		return requeteRetournee.toString(); */
		//String result = requeteRetournee.toString(); 
		/*if ( positionWhere > 0 ) {
			result = result.replaceAll(" \" WHERE ", " WHERE " );
		} else {
			result = result.substring(0, result.length() - 2);
		}
		result = result.replaceAll(" FROM \" ",       " FROM ");*/

		//result = result.replaceAll(DOUBLE_QUOTES,    "''");

		
		//return result;
	}
}

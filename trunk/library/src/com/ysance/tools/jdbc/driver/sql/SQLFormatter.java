package com.ysance.tools.jdbc.driver.sql;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

import com.ysance.tools.jdbc.driver.JdbcFolderGrammar;

public class SQLFormatter implements SQLGrammar, ParsingUtilities {	
	
	/**
	 * Méthode devant retourner le nom des champs et les commandes SQL en majuscule mais pas les autres données
	 * Voir comment utiliser Antlr pour faire ceci
	 * @param aRequete
	 * @return
	 */
	public static ArrayList upperCaseSQLWordsAndFields(String aRequete) {
			
		//Arrays.sort(separateursMots);		

		// Remplacement de 2 apostrophes par £doubleapos£
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
				Arrays.sort(separateursMots);
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
				// Si on ferme la chaine ou qu'on arrive à la fin, on l'ajoute à la liste et on réinitialise le mot
				if ( chaineProtegee == SIMPLE_APOSTROPHE || chaineProtegee == DOUBLE_APOSTROPHE || chaineProtegee == DELIMITEUR_FONCTION) {
					if ((c == '\'' &&  chaineProtegee == SIMPLE_APOSTROPHE) ||
						(c == '\"' &&  chaineProtegee == DOUBLE_APOSTROPHE) || 
						(c == '('  &&  chaineProtegee == DELIMITEUR_FONCTION) ||
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
			  if ((c == '\'') ||( c == '\"') ||( c == '.') ) {
				  if ((c == '\'')) {
					  chaineProtegee = SIMPLE_APOSTROPHE;
				  } else if ( c == '\"') {
					  chaineProtegee = DOUBLE_APOSTROPHE ;
				  } else {
					  chaineProtegee = DELIMITEUR_FONCTION;
					  requeteDecoupee.add(new RequestWord(mot.toString().trim().toUpperCase(), RequestWord.KIND_UNKNOWN));
					  mot = new StringBuffer();					  
				  }
			  } else {
				  // Si on trouve un séparateur de mots ou qu'on arrive à la dernière lettre de la requête, on ferme le mot courant
				  //if ( c == ' ' || indexLettre == caracteresRequete.length - 1) {
				  if ( Arrays.binarySearch(separateursMots,c) > -1 || Arrays.binarySearch(separateursGroupe,c) > -1 || indexLettre == caracteresRequete.length - 1) {				  
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
	}
}

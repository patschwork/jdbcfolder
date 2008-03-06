package com.ysance.tools.jdbc.driver.sql;

import java.util.ArrayList;

public class SQLFormatter implements SQLGrammar {
	
	/**
	 * Méthode devant retourner le nom des champs et les commandes SQL en majuscule mais pas les autres données
	 * Voir comment utiliser Antlr pour faire ceci
	 * @param aRequete
	 * @return
	 */
	public static String upperCaseSQLWordsAndFields(String aRequete) {
		
		class RequestWord {
			public static final int KIND_UNKNOWN  = 0; 
			public static final int KIND_SQL_OR_FIELD_WORD = 1; 			
			
			public String word = "";
			public int kind = KIND_UNKNOWN;
			
			public RequestWord (String aWord, int aKind) {
				this.word = aWord;
				this.kind = aKind;
			}
		}
		
		
		// On protège le nom des "tables" en les entourant de doubles quotes
		String requeteMajuscules = aRequete.toUpperCase(); 
		int positionFrom   = requeteMajuscules.indexOf(FROM_WORD);		
		aRequete = aRequete.substring(0, positionFrom + FROM_WORD.length() )+" \" "+aRequete.substring(positionFrom + FROM_WORD.length() );
		
		requeteMajuscules = aRequete.toUpperCase(); 
		int positionWhere   = requeteMajuscules.indexOf(WHERE_WORD);
		if ( positionWhere > 0 ) {
			aRequete = aRequete.substring(0, positionWhere )+" \" "+aRequete.substring(positionWhere);
		} else {			
			aRequete = aRequete+" \" ";
		}
		
		final int AUCUNE_APOSTROPHE = 0; 
		final int SIMPLE_APOSTROPHE = 1; 
		final int DOUBLE_APOSTROPHE = 2; 		

		// Remplacement de 2 apostrophes par $£apos£$
		aRequete = aRequete.replaceAll("''", "£doubleapos£");
		
		// Découpage de la requete en protégeant les données entre simples et doubles quotes
		ArrayList requeteDecoupee = new ArrayList(); 
		char[] caracteresRequete = aRequete.toCharArray();
		int entreApostrophes = AUCUNE_APOSTROPHE;

		StringBuffer mot = new StringBuffer();
		for (int indexLettre = 0; indexLettre < caracteresRequete.length; indexLettre++) {
			char c = caracteresRequete[indexLettre];
			
			if (entreApostrophes > 0) {
				mot.append(c);				
				if ((c == '\'' &&  entreApostrophes == SIMPLE_APOSTROPHE) ||
					(c == '\"' &&  entreApostrophes == DOUBLE_APOSTROPHE)) {
					entreApostrophes = AUCUNE_APOSTROPHE;
				    requeteDecoupee.add(new RequestWord(mot.toString(), RequestWord.KIND_UNKNOWN)); 
				    mot = new StringBuffer();
				}
			} else {
			  if ((c == '\'') ||( c == '\"')) {
				  mot.append(c);				
				  entreApostrophes = (c == '\'') ? SIMPLE_APOSTROPHE : DOUBLE_APOSTROPHE ;
			  } else {
				  mot.append(c);
				  if ( c == ' ') {
					  requeteDecoupee.add(new RequestWord(mot.toString(), RequestWord.KIND_SQL_OR_FIELD_WORD)); 
					  mot = new StringBuffer();
				  } else {
					if (indexLettre == caracteresRequete.length - 1) {
						requeteDecoupee.add(new RequestWord(mot.toString(), RequestWord.KIND_SQL_OR_FIELD_WORD));
					}					  
				  }
			  }			
			}
		}		

		StringBuffer requeteRetournee = new StringBuffer();
		for (int i = 0; i < requeteDecoupee.size(); i++) {
			RequestWord motRequete = (RequestWord)requeteDecoupee.get(i);
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
		
		String result = requeteRetournee.toString(); 
		if ( positionWhere > 0 ) {
			result = result.replaceAll(" \" WHERE ", " WHERE " );
		} else {
			result = result.substring(0, result.length() - 2);
		}
		result = result.replaceAll(" FROM \" ",       " FROM ");

		result = result.replaceAll("£doubleapos£",    "''");

		
		return result;
	}
}

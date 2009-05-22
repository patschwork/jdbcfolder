package com.ysance.tools.jdbc.driver.sql;

import java.util.ArrayList;
import java.util.Arrays;

public class RequestFieldSelected implements SQLGrammar, ParsingUtilities {
	
	ArrayList mots = new ArrayList();
	
	int positionSeparateurAlias = -1;
	int positionAlias = -1;
	
	boolean bIsSQLJoker = false;
	
	String alias;
	String expression;
	String wholeString;

	public String getAlias() {
		computeField();
		return alias;
	}

	public String getExpression() {
		computeField();
		return expression;
	}

	/**
	 * 
	 * @param aRequestWord
	 * @return if word has finishes by a group separator
	 */
	public boolean addWord(RequestWord aRequestWord) {
		boolean dernierMot = false;
		// On ne prend pas les mots vides
		
		if (aRequestWord.toString().trim().length() > 0) {
			// On supprime la virgule de fin de champ si elle existe, déterminant du même coup la fin du groupe
			if ( Arrays.binarySearch(ParsingUtilities.separateursGroupe, aRequestWord.word.charAt(aRequestWord.word.length() - 1)) > -1) {
				aRequestWord.word = aRequestWord.word.substring(0, aRequestWord.word.length() - 1);
				dernierMot = true;
			}				
			
			// On reteste au cas où la virgule de séparation de groupe était seule
			if (aRequestWord.toString().trim().length() > 0) {
				this.mots.add(aRequestWord);
				// Si ce n'est pas une chaine protégée, on fait une batterie de tests
				if (aRequestWord.getKind() != RequestWord.KIND_PROTECTED) {
					// Si c'est le séparateur d'alias
					if (positionSeparateurAlias == -1 ) {
						if (SQLGrammar.FIELD_ALIAS_WORD.equals(aRequestWord.toString().trim())) {
							positionSeparateurAlias =  this.mots.size() - 1;			
						}  
					} else {
						// Si est après le séparateur d'alias, on prend le dernier mot comme alias
						positionAlias =  this.mots.size() - 1;			
					}
				}
			}	
			
		} 
		return dernierMot;
	}
	
	public int getWordCount() {
		return this.mots.size();
	}
	
	public String toString() {
		computeField();
		return wholeString;
	}
	
	private void computeField() {
		// initialisation des propriétés si ça n'a pas été déjà fait
		if (alias == null && expression == null) {
			alias = "";
			expression = "";
			wholeString = "";
			
			// On travaille sur les différents mots de l'expression
			for (int i = 0; i < this.mots.size(); i++) {
				RequestWord mot = (RequestWord)this.mots.get(i);
				wholeString = wholeString +" "+ mot.toString();
				// S'il y a un séparateur d'alias, on ne le compte pas dans l'expression ni tout ce qui suit
				if ((positionSeparateurAlias == -1) || ( positionSeparateurAlias > -1 ) && (i < positionSeparateurAlias )) {
					expression = expression +" "+  this.mots.get(i).toString(); 					
				}								
			}
			
			if ( positionAlias > -1 ) {
				alias = this.mots.get(positionAlias).toString(); 					
			} else {
				alias = this.mots.get(this.mots.size() - 1).toString();
			}
			// On "nettoie" l'alias
			alias = alias.toUpperCase().replace("'", "");

			bIsSQLJoker = SQLGrammar.JOKER_WORD.equals(expression.trim());

		}
		
		//System.out.println("expression" + expression);
		//System.out.println("alias " + alias);
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * 
	 * @return true if the expression is the SQL Joker (*)
	 */
	public boolean isSQLJoker() {
		return this.bIsSQLJoker;
	}
}

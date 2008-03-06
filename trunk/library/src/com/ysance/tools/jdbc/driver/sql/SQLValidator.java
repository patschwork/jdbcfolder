package com.ysance.tools.jdbc.driver.sql;

import java.sql.SQLException;
import java.util.StringTokenizer;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;
import com.ysance.tools.jdbc.driver.JdbcFolderExceptions.NoClauseFoundException;

public class SQLValidator implements SQLGrammar {
	
	String requete;
	String cheminCatalogue;
	String clauseWhere = "";
	int positionSelect = -1;  
	int positionFrom   = -1;
	int positionWhere  = -1;
	
	public SQLValidator (String newRequete) throws SQLException {
		
		this.requete = SQLFormatter.upperCaseSQLWordsAndFields(newRequete); 
		positionSelect = this.requete.indexOf(SQLFormatter.SELECT_WORD);  
		positionFrom   = this.requete.indexOf(SQLFormatter.FROM_WORD);
		positionWhere  = this.requete.indexOf(SQLFormatter.WHERE_WORD);
		
		if ( positionSelect < 0 ) throw new JdbcFolderExceptions.NoSelectWordFoundException();
		if ( positionFrom < 0 )   throw new JdbcFolderExceptions.NoFromWordFoundException();

		if (positionWhere > 0) {			
		  clauseWhere =  this.requete.substring(positionWhere + SQLFormatter.WHERE_WORD.length());
				
		  if (clauseWhere.trim().length() == 0) throw new NoClauseFoundException();
		}			
	}
	
	public String getCatalogPath() {
		return this.requete.substring(positionFrom + SQLFormatter.FROM_WORD.length(), positionWhere < 0 ? this.requete.length() : positionWhere).trim();
	}
	
	public StringTokenizer getFields() {
		return new StringTokenizer(this.requete.substring(positionSelect + SQLFormatter.SELECT_WORD.length(), positionFrom), ",");	
	}
	
	public String getWhereClause() {
		return this.clauseWhere;
	}

}

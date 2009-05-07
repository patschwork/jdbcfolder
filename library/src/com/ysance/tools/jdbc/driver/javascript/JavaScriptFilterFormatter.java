package com.ysance.tools.jdbc.driver.javascript;

import com.ysance.tools.jdbc.driver.sql.SQLGrammar;

public class JavaScriptFilterFormatter implements JavaScriptGrammar {
	
	public static String format(String aWhereClause) {
		return aWhereClause.replaceAll("=",JavaScriptGrammar.JAVASCRIPT_EQUALITY_OPERATOR)
		                   .replaceAll(JavaScriptGrammar.JDBCFOLDER_DOUBLE_QUOTES, "''")
		                   .replaceAll("<>", JavaScriptGrammar.JAVASCRIPT_DIFFERENCE_OPERATOR)		                   
		                   .replaceAll(" "+SQLGrammar.SQL_AND_OPERATOR+" "," "+JAVASCRIPT_AND_OPERATOR+" ")          
		   				   .replaceAll(" "+SQLGrammar.SQL_OR_OPERATOR+" "," "+ JAVASCRIPT_OR_OPERATOR+" ");
	}

}

package com.ysance.tools.jdbc.driver.sql;

public interface SQLGrammar extends com.ysance.tools.jdbc.driver.JdbcFolderGrammar {

	// SQL WORDS
	public static String FROM_WORD            = "FROM";	
	public static String JOKER_WORD           = "*";
	public static String ORDER_BY_WORD_1_ON_2 = "ORDER";	
	public static String GROUP_BY_WORD_1_ON_2 = "GROUP";	
	public static String BY_WORD			  = "BY";	
	public static String SELECT_WORD          = "SELECT";	
	public static String WHERE_WORD           = "WHERE";	

	
	// SQL OPERATORS
	public static String SQL_AND_OPERATOR      = "AND";	
	public static String SQL_OR_OPERATOR       = "OR";	
	
}

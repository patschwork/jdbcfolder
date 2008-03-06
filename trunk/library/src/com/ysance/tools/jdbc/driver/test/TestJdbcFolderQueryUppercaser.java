package com.ysance.tools.jdbc.driver.test;

public class TestJdbcFolderQueryUppercaser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String requete = "select filename from e:/temp where filename = 'l''excuse mogador, c''est fermé !' and toto >= toto and titi <= tutu ";

		String requete = "SELECT * FROM /temp where filename = 'The Beauty and the beast or someonelse ' and filename = '1+2=3 or something like that' or size < 10000 ";		
		System.out.println(com.ysance.tools.jdbc.driver.sql.SQLFormatter.upperCaseSQLWordsAndFields(requete));
				
		requete = "SELECT FILeNAME, FileNamE , FileName FROM /temp where filename = 'The Beauty AND the beast OR someone else ' and filename = '1+2=3 or somethhing like that' or size < 10000 ";		
		System.out.println(com.ysance.tools.jdbc.driver.sql.SQLFormatter.upperCaseSQLWordsAndFields(requete));
		
		requete = "SELECT ORphée FROM /temp where meteor = meteor or filename = 3232or size < 10000 ";		
		System.out.println(com.ysance.tools.jdbc.driver.sql.SQLFormatter.upperCaseSQLWordsAndFields(requete));

		requete = "SELECT ORphée FROM /temp where (meteor = meteor)or(filename = 3232)or size < 10000 ";		
		System.out.println(com.ysance.tools.jdbc.driver.sql.SQLFormatter.upperCaseSQLWordsAndFields(requete));		
	}

}

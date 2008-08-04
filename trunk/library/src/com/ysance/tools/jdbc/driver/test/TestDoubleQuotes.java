package com.ysance.tools.jdbc.driver.test;

import com.ysance.tools.jdbc.driver.*;
import java.util.*;

public class TestDoubleQuotes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// com.ysance.tools.jdbc.driver.JdbcFolderGrammar;		
		//String requete = "2''''''0'000'1'''7''''''''''''''2'''''4''''''''''";
		String requete = "2'' ''''0'000'1'' '7'''''' ''''''''2'''''4''''''''''";
		//String requete = "2'' '''' where toto='' and toto='' or toto='' ";
		
		
		final int SIMPLE_APOSTROPHE = 1;		
		final int DOUBLE_APOSTROPHE = 2; 		
		int chaineProtegee = SIMPLE_APOSTROPHE;
		
		int indexDerniereApostrophe = requete.indexOf("'");
		int indexDoubleApostrophe = 0;

		while ( requete.indexOf("''", indexDerniereApostrophe) > 0 ) {
			indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);
			indexDoubleApostrophe = requete.indexOf("''",indexDerniereApostrophe);
			
			if (indexDerniereApostrophe == indexDoubleApostrophe) {
				requete = requete.substring(0, indexDoubleApostrophe) + requete.substring(indexDoubleApostrophe).replaceFirst("''", JdbcFolderGrammar.JDBCFOLDER_DOUBLE_QUOTES);
			} else {
				indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);				
			}
		}
		
		/*indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);
		indexDoubleApostrophe = requete.indexOf("''",indexDerniereApostrophe);
		
		if (indexDerniereApostrophe == indexDoubleApostrophe) {
			requete = requete.substring(0, indexDoubleApostrophe) + requete.substring(indexDoubleApostrophe).replaceFirst("''", JdbcFolderGrammar.JDBCFOLDER_DOUBLE_QUOTES);
		} else {
			if (chaineProtegee == SIMPLE_APOSTROPHE) {
				chaineProtegee = 0;			
			} 
			indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);				

		}

		indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);
		indexDoubleApostrophe = requete.indexOf("''",indexDerniereApostrophe);
		
		if (indexDerniereApostrophe == indexDoubleApostrophe) {
			requete = requete.substring(0, indexDoubleApostrophe) + requete.substring(indexDoubleApostrophe).replaceFirst("''", JdbcFolderGrammar.JDBCFOLDER_DOUBLE_QUOTES);
		} else {
			if (chaineProtegee == SIMPLE_APOSTROPHE) {
				chaineProtegee = 0;			
			} 
			indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);				
		}
		
		indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);
		indexDoubleApostrophe = requete.indexOf("''",indexDerniereApostrophe);
		
		if (indexDerniereApostrophe == indexDoubleApostrophe) {
			requete = requete.substring(0, indexDoubleApostrophe) + requete.substring(indexDoubleApostrophe).replaceFirst("''", JdbcFolderGrammar.JDBCFOLDER_DOUBLE_QUOTES);
		} else {
			if (chaineProtegee == SIMPLE_APOSTROPHE) {
				chaineProtegee = 0;			
			} 
			indexDerniereApostrophe = requete.indexOf("'",indexDerniereApostrophe + 1);				
		}	*/	
/*		while (requete.indexOf("''",indexDerniereApostrophe + 1) > 0) {
			int indexDoubleApostrophe = requete.indexOf("''",indexDerniereApostrophe + 1);
			System.out.println(" indexDoubleApostrophe : "+indexDoubleApostrophe +"/ indexApostrophe : " + indexDerniereApostrophe  );
			if (indexDoubleApostrophe == indexDerniereApostrophe ) {
				System.out.println(requete.replaceFirst("''", JdbcFolderGrammar.JDBCFOLDER_DOUBLE_QUOTES));
				requete = requete.replaceFirst("''", JdbcFolderGrammar.JDBCFOLDER_DOUBLE_QUOTES);
			}
						
		}*/
		
		System.out.print(requete);
	}

}

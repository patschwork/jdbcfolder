package com.ysance.tools.jdbc.driver.test;

import java.util.HashMap;
import java.util.Iterator;

import com.ysance.tools.jdbc.driver.sql.SQLValidator;

public class TestJdbcFolderQueryValidator {

	public static void main(String[] args) {
		
		testValidator();
		HashMap catalogues = getCatalogs();
		
		Object[] cles = catalogues.keySet().toArray();
		
		for (int indexCle = 0; indexCle < cles.length; indexCle++) {
			System.out.println(cles[indexCle].toString());
		}
		
		
	}
	
	static public HashMap getCatalogs() {		
		HashMap catalogues = new HashMap();
		
		String listeCatalogues = " /temp ,/temp2 2";		
		// Une fois la clause FROM reconstituée, on travaille dessus
		final int AUCUN_GROUPE    = 0;
		final int GROUPE_PROTEGE  = 1;
		final int GROUPE_SELECT   = 2;
		int groupe = AUCUN_GROUPE;

		int indexLettre = 0;
		
		for ( indexLettre = 0; indexLettre <= listeCatalogues.length(); indexLettre++ ) {
		
		}
		
		
		return catalogues;
	}	

	static public void testValidator() {
		try {
			String query = "select * from titi";
			System.out.println(" ================  "+ query);
			SQLValidator validator = new SQLValidator(query);
			System.out.println("validator.getFields() "+ validator.getFields());
			System.out.println("validator.getCatalogPath() "+ validator.getCatalogPath());
			System.out.println("validator.getWhereClause() "+ validator.getWhereClause());

			HashMap catalogues = validator.getCatalogs();			
			Object[] cles = catalogues.keySet().toArray();			
			for (int indexCle = 0; indexCle < cles.length; indexCle++) {
				System.out.println("validator.getCatalogs() "+ cles[indexCle].toString()+ "   " + catalogues.get(cles[indexCle]));
			}			

			query = "select * from titi where toto = '''blablabla blublublu''' ";
			System.out.println(" ================  "+ query);
			validator = new SQLValidator(query);
			System.out.println("validator.getFields() "+ validator.getFields());
			System.out.println("validator.getCatalogPath() "+ validator.getCatalogPath());
			System.out.println("validator.getWhereClause() "+ validator.getWhereClause());

			catalogues = validator.getCatalogs();			
			cles = catalogues.keySet().toArray();			
			for (int indexCle = 0; indexCle < cles.length; indexCle++) {
				System.out.println("validator.getCatalogs() "+ cles[indexCle].toString()+ "   " + catalogues.get(cles[indexCle]));
			}
			
			query = "select * from titi, toto B where toto = '''blablabla blublublu''' ";
			System.out.println(" ================  "+ query);
			validator = new SQLValidator(query);
			System.out.println("validator.getFields() "+ validator.getFields());
			System.out.println("validator.getCatalogPath() "+ validator.getCatalogPath());
			System.out.println("validator.getWhereClause() "+ validator.getWhereClause());

			catalogues = validator.getCatalogs();			
			cles = catalogues.keySet().toArray();			
			for (int indexCle = 0; indexCle < cles.length; indexCle++) {
				System.out.println("validator.getCatalogs() "+ cles[indexCle].toString()+ "   " + catalogues.get(cles[indexCle]));
			}				
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}

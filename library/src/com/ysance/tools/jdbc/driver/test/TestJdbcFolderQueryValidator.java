package com.ysance.tools.jdbc.driver.test;

import java.util.HashMap;
import java.util.Iterator;

import com.ysance.tools.jdbc.driver.sql.SQLValidator;

public class TestJdbcFolderQueryValidator {

	public static void main(String[] args) {
		
		testValidator();	
		
	}

	static public void testValidator() {
		try {
			String query = "select titi, toto, tata + tutu + 'bloubloublou'  from titi";
			System.out.println(" ================  "+ query);
			SQLValidator validator = new SQLValidator(query);
			System.out.println("validator.getFields() "+ validator.getFields());
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
			System.out.println("validator.getWhereClause() "+ validator.getWhereClause());

			catalogues = validator.getCatalogs();			
			cles = catalogues.keySet().toArray();			
			for (int indexCle = 0; indexCle < cles.length; indexCle++) {
				System.out.println("validator.getCatalogs() "+ cles[indexCle].toString()+ "   " + catalogues.get(cles[indexCle]));
			}
			
			query = "select * from titi, (select * from TOTO) B where toto = '''blablabla blublublu''' ";
			System.out.println(" ================  "+ query);
			validator = new SQLValidator(query);
			System.out.println("validator.getFields() "+ validator.getFields());
			System.out.println("validator.getWhereClause() "+ validator.getWhereClause());

			catalogues = validator.getCatalogs();			
			cles = catalogues.keySet().toArray();			
			for (int indexCle = 0; indexCle < cles.length; indexCle++) {
				System.out.println("validator.getCatalogs() "+ cles[indexCle].toString()+ "   " + catalogues.get(cles[indexCle]));
			}
			
			query = "select * from ";
			System.out.println(" ================  "+ query);
			validator = new SQLValidator(query);
			System.out.println("validator.getFields() "+ validator.getFields());
			System.out.println("validator.getWhereClause() "+ validator.getWhereClause());

			try {
				catalogues = validator.getCatalogs();			
				cles = catalogues.keySet().toArray();			
				for (int indexCle = 0; indexCle < cles.length; indexCle++) {
					System.out.println("validator.getCatalogs() "+ cles[indexCle].toString()+ "   " + catalogues.get(cles[indexCle]));
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Erreur de test levée en succès");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}

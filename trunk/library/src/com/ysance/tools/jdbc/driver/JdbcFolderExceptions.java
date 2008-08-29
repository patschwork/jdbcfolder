package com.ysance.tools.jdbc.driver;

import java.sql.SQLException;

public class JdbcFolderExceptions {
	
	/**
	 * ERREURS UTILISATEUR
	 */
	
	/** Erreur E001 */
	public static class NoSelectWordFoundException extends SQLException{
		public NoSelectWordFoundException() {
			super("E001 - No select statement found");		
		}
	}
	
	/** Erreur E002 */
	public static class NoFromWordFoundException extends SQLException{
		public NoFromWordFoundException() {
			super("E002 - No from found");		
		}
	}
	
	/** Erreur E003 */
	public static class FieldNotFoundException extends SQLException{
		public FieldNotFoundException(String aMissingField) {
			super("E003 - field "+aMissingField+" not found");		
		}
	}
	
	/** Erreur E004 */
	public static class TableDoesntExistException extends SQLException{
		public TableDoesntExistException(String aTableName) {
			super("E004 - Table "+aTableName+"doesn't exist");		
		}
	}
	
	/** Erreur E005 */
	public static class TableIsNotDirectoryException extends SQLException{
		public TableIsNotDirectoryException(String aTableName) {
			super("E005 - Table "+aTableName+" is not a folder");		
		}
	}	
	
	/** Erreur E006 */
	public static class NoClauseFoundException extends SQLException{
		public NoClauseFoundException() {
			super("E006 - No clause found while WHERE word found");		
		}
	}
	
	/** Erreur E007 */
	public static class RequestMustBeginBySelectException extends SQLException{
		public RequestMustBeginBySelectException() {
			super("E007 - Request MUST begin by a SELECT ");		
		}
	}
	
	/** Erreur E008 */
	public static class NoCatalogFoundException extends SQLException{
		public NoCatalogFoundException() {
			super("E008 - No catalog found in request");		
		}
	}	
	
	/** Erreur E100 */
	public static class GetFieldCastException extends SQLException{
		public GetFieldCastException(String aFieldName, String aFieldType) {
			super("E100 - Field "+aFieldName+" is not instanceof "+aFieldType);		
		}
	}	

	/** Erreur E101 */
	public static class DatasetFieldNotFoundException extends SQLException{
		public DatasetFieldNotFoundException(String aFieldName) {
			super("E101 - Field "+aFieldName+" doesn't belong to fields returned by query" );		
		}
	}	
	
	/** Erreur E102 */
	public static class NoDatasetFieldFoundAtPositionException extends SQLException{
		public NoDatasetFieldFoundAtPositionException(int aPosition) {
			super("E102 - No field returned by query at position "+aPosition);		
		}
	}
	
	/** Erreur E999 */
	public static class GenericBadRequestException extends SQLException{
		public GenericBadRequestException() {
			super("E999 - Invalid SQL request");		
		}
	}
	
	/**
	 * ERREURS TECHNIQUES
	 */
	
	/** Erreur T001 */
	public static class CatalogKindUnknownException extends SQLException{
		public CatalogKindUnknownException(int aCatalogKind) {
			super("T001 - Catalog kind unknown "+aCatalogKind);		
		}
	}
	
	
	
}

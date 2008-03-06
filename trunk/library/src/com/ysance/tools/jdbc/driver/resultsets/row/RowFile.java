package com.ysance.tools.jdbc.driver.resultsets.row;

import com.ysance.tools.jdbc.driver.resultsets.metadata.FolderResultSetMetaData;

public class RowFile {
	java.io.File fichierMappe;
	
	public RowFile(java.io.File aFichierMappe) {
		fichierMappe = aFichierMappe;
	}
	
	public String getFileName() {
		return this.fichierMappe.getName();
	}
	
	public long getSize() {
		return this.fichierMappe.length();
	}
	
	public String getExtension() {
		String extension = "";
		int positionPoint = this.fichierMappe.getName().lastIndexOf(".");
		
		if ( positionPoint >= 0 && !this.fichierMappe.isDirectory() ) {
			extension = this.fichierMappe.getName().substring(positionPoint + 1, this.fichierMappe.getName().length());
		}
		return extension;
	}	  	
	
	public Object getData(String aFieldName) {
		if (FolderResultSetMetaData.FILENAME_FIELD.equals(aFieldName)) {
			return this.getFileName();
		} else {
	  		if (FolderResultSetMetaData.SIZE_FIELD.equals(aFieldName)) {
	  			return new Long(this.getSize());
	  		} else {
		  		if (FolderResultSetMetaData.EXTENSION_FIELD.equals(aFieldName)) {
		  			return this.getExtension();
		  		}		  			
	  		}	  			
		}
		return "";
	}	
		  	
	public static String getMethodForField(String aFieldName) {
		if (FolderResultSetMetaData.FILENAME_FIELD.equals(aFieldName)) {
			return "getFileName()";
		} else {
  		if (FolderResultSetMetaData.SIZE_FIELD.equals(aFieldName)) {
  			return "getSize()";
  		} else {
	  		if (FolderResultSetMetaData.EXTENSION_FIELD.equals(aFieldName)) {
	  			return "getExtension()";
	  		}		  			
  		}	  			
		}
		return "";
	}
	
	public String toString()  {
		return fichierMappe.toString();
	}
}

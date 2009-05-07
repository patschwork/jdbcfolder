package com.ysance.tools.jdbc.driver.resultsets.row;

import java.sql.SQLException;

import com.ysance.tools.jdbc.driver.resultsets.metadata.FolderResultSetMetaData;

public class RowFile extends Row {
	java.io.File fichierMappe;
	
	
	public RowFile(java.io.File aFichierMappe) {
		super();
		fichierMappe = aFichierMappe;
		this.fieldsList.add(FolderResultSetMetaData.FILENAME_FIELD);
		this.fieldsList.add(FolderResultSetMetaData.SIZE_FIELD);
		this.fieldsList.add(FolderResultSetMetaData.EXTENSION_FIELD);

		this.fieldsData.put(FolderResultSetMetaData.FILENAME_FIELD, this.fichierMappe.getName());
		this.fieldsData.put(FolderResultSetMetaData.SIZE_FIELD, new Double(new Long(this.fichierMappe.length()).doubleValue()));
		//System.out.println(new Double(new Long(this.fichierMappe.length()).doubleValue()).toString());
		this.fieldsData.put(FolderResultSetMetaData.EXTENSION_FIELD, getExtension());
	}
	
	/*public String getFileName() {
		return this.fichierMappe.getName();
	}
	
	public long getSize() {
		return this.fichierMappe.length();
	}*/
	
	private String getExtension() {
		String extension = "";
		int positionPoint = this.fichierMappe.getName().lastIndexOf(".");
		
		if ( positionPoint >= 0 && !this.fichierMappe.isDirectory() ) {
			extension = this.fichierMappe.getName().substring(positionPoint + 1, this.fichierMappe.getName().length());
		}
		return extension;
	}  	
	
	public String toString()  {
		return fichierMappe.toString();
	}

}

package com.ysance.tools.jdbc.driver.sql;

import java.sql.SQLException;

import com.ysance.tools.jdbc.driver.JdbcFolderExceptions;

public class RequestCatalog {
	
	public static final int CATALOG_KIND_SINGLE = 0;
	public static final int CATALOG_KIND_GROUP  = 1;
	
	private int catalogKind = CATALOG_KIND_SINGLE;
	private StringBuffer catalog;
	
	public RequestCatalog(StringBuffer aCatalog, int aCatalogKind) throws SQLException {
		if (aCatalogKind < CATALOG_KIND_SINGLE && aCatalogKind > CATALOG_KIND_GROUP) {
			throw new JdbcFolderExceptions.CatalogKindUnknownException(aCatalogKind);
		}
		this.catalog = aCatalog;
		this.catalogKind = aCatalogKind;
	}

	public String toString() {
		return this.catalog.toString();
	}
	
	public int getCatalogKind(){
		return this.catalogKind;
	}
}

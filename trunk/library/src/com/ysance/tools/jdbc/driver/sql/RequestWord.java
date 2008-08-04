package com.ysance.tools.jdbc.driver.sql;

public class RequestWord implements Comparable {
	public static final int KIND_UNKNOWN  = 0; 
	public static final int KIND_SQL_WORD = 1; 			
	public static final int KIND_PROTECTED  = 2; 
	
	public String word = "";
	public int kind = KIND_UNKNOWN;
	
	public RequestWord (String aWord, int aKind) {
		this.word = aWord;
		this.kind = aKind;
	}
	
	public int compareTo(RequestWord aRequestWord) {
    	int thisVal = this.kind;
    	int anotherVal = aRequestWord.kind;
    	int comparaisonType = (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
    	if ( comparaisonType == 0 ) {
    		return this.word.compareTo(aRequestWord.word);
    	} 
    	return comparaisonType;
	}

	public int compareTo(Object o) {
		return compareTo((RequestWord)o); 
	}
	
    public boolean equals(Object obj) {
    	if (obj instanceof RequestWord) {
    	    return this.word.equals(((RequestWord)obj).word) && this.kind == ((RequestWord)obj).kind ;		    	
    	}
    	return false;
    }			
	
	public String toString() {
		return this.word;
	}
}
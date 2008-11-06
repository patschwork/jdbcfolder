package com.ysance.tools.jdbc.driver.resultsets;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
//JSE 6 import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
//JSE 6 import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
//JSE 6 import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.ArrayList;
import com.ysance.tools.jdbc.driver.resultsets.row.*;

public abstract class JdbcFolderAbstractResultSet implements ResultSet {
	
	static final int POSITION_BEFORE_FIRST_ROW = -1;
	
	/**
	 * Attention !!!
	 * positionCurseur est la position dans le tableau de lignes. Il va de 0 à tableauLignes.length - 1
	 * tandis que les appels aux fonctions de positionnement des lignes vont de 1 à  tableauLignes.length
	 */
	protected int positionCurseur = POSITION_BEFORE_FIRST_ROW;
	 
	protected ArrayList tableauLignes;

	public boolean absolute(int aPosition) throws SQLException {
		// Pour être raccord avec les indices de tableau, on décrémente la position
		aPosition--;
		boolean inTheResultSetSet = (aPosition > POSITION_BEFORE_FIRST_ROW && aPosition < tableauLignes.size() );
		if (inTheResultSetSet) {
			this.positionCurseur = aPosition;
		} else {
			if (aPosition <= POSITION_BEFORE_FIRST_ROW) {
				this.positionCurseur = POSITION_BEFORE_FIRST_ROW;
			} else {
				this.positionCurseur = tableauLignes.size();
			}
		}
		return inTheResultSetSet;
	}

	public void afterLast() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.afterLast()");

	}

	public void beforeFirst() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.beforeFirst()");

	}

	public void cancelRowUpdates() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.cancelRowUpdates()");

	}

	public void clearWarnings() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.clearWarnings()");

	}

	public void close() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.close()");

	}

	public void deleteRow() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.deleteRow()");

	}

	public int findColumn(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.findColumn()");
		return 0;
	}

	public boolean first() throws SQLException {
		this.absolute(1);
		return this.tableauLignes.size() > 0;
	}

	public Array getArray(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getArray()");
		return null;
	}

	public Array getArray(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getArray()");
		return null;
	}

	public InputStream getAsciiStream(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getAsciiStream()");
		return null;
	}

	public InputStream getAsciiStream(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getAsciiStream()");
		return null;
	}

	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBigDecimal()");
		return null;
	}

	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBigDecimal()");
		return null;
	}

	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBigDecimal()");
		return null;
	}

	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBigDecimal()");
		return null;
	}

	public InputStream getBinaryStream(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBinaryStream()");
		return null;
	}

	public InputStream getBinaryStream(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBinaryStream()");
		return null;
	}

	public Blob getBlob(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBlob()");
		return null;
	}

	public Blob getBlob(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBlob()");
		return null;
	}

	public boolean getBoolean(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBoolean()");
		return false;
	}

	public boolean getBoolean(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBoolean()");
		return false;
	}

	public byte getByte(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getByte()");
		return 0;
	}

	public byte getByte(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getByte()");
		return 0;
	}

	public byte[] getBytes(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBytes()");
		return null;
	}

	public byte[] getBytes(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getBytes()");
		return null;
	}

	public Reader getCharacterStream(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getCharacterStream()");
		return null;
	}

	public Reader getCharacterStream(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getCharacterStream()");
		return null;
	}

	public Clob getClob(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getClob()");
		return null;
	}

	public Clob getClob(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getClob()");
		return null;
	}

	public String getColumnLabel(int column) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getColumnLabel()");
		return null;
	}
	
	public int getConcurrency() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getConcurrency()");
		return 0;
	}

	public String getCursorName() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getCursorName()");
		return null;
	}

	public Date getDate(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getDate()");
		return null;
	}

	public Date getDate(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getDate()");
		return null;
	}

	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getDate()");
		return null;
	}

	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getDate()");
		return null;
	}

	public double getDouble(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getDouble()");
		return 0;
	}

	public double getDouble(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getDouble()");
		return 0;
	}

	public int getFetchDirection() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getFetchDirection()");
		return 0;
	}

	public int getFetchSize() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getFetchSize()");
		return 0;
	}

	public float getFloat(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getFloat()");
		return 0;
	}

	public float getFloat(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getFloat()");
		return 0;
	}

	public int getHoldability() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getHoldability()");
		return 0;
	}

	public int getInt(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getInt()");
		return 0;
	}

	public int getInt(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getInt()");
		return 0;
	}

	public long getLong(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getLong()");
		return 0;
	}

	public long getLong(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getLong()");
		return 0;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getMetaData()");
		return null;
	}

	public Reader getNCharacterStream(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getNCharacterStream()");
		return null;
	}

	public Reader getNCharacterStream(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getNCharacterStream()");
		return null;
	}

//	JSE 6 
	/*
	public NClob getNClob(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getNClob()");
		return null;
	}

	public NClob getNClob(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getNClob()");
		return null;
	}
*/
	public String getNString(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getNString()");
		return null;
	}

	public String getNString(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getNString()");
		return null;
	}

	public Object getObject(int aPosition) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getObject(int arg0)");
		return null;
	}

	public Object getObject(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getObject(String arg0)");
		return null;
	}

	public Object getObject(int arg0, Map arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getObject(int arg0, Map arg1)");
		return null;
	}

	public Object getObject(String arg0, Map arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getObject(String arg0, Map arg1)");
		return null;
	}

	public Ref getRef(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getRef()");
		return null;
	}

	public Ref getRef(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getRef()");
		return null;
	}

	public int getRow() throws SQLException {
		// Pour être raccord avec la plage de valeur attendue, on retourne la position incrémentée
		return positionCurseur + 1;
	}

//	JSE 6
/*
	public RowId getRowId(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getRowId()");
		return null;
	}

	public RowId getRowId(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getRowId()");
		return null;
	}

	public SQLXML getSQLXML(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getSQLXML()");
		return null;
	}

	public SQLXML getSQLXML(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getSQLXML()");
		return null;
	}
*/
	public short getShort(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getShort()");
		return 0;
	}

	public short getShort(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getShort()");
		return 0;
	}

	public Statement getStatement() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getStatement()");
		return null;
	}

	public String getString(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getString()");
		return null;
	}

	public String getString(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getString()");
		return null;
	}

	public Time getTime(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTime()");
		return null;
	}

	public Time getTime(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTime()");
		return null;
	}

	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTime()");
		return null;
	}

	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTime()");
		return null;
	}

	public Timestamp getTimestamp(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTimestamp()");
		return null;
	}

	public Timestamp getTimestamp(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTimestamp()");
		return null;
	}

	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTimestamp()");
		return null;
	}

	public Timestamp getTimestamp(String arg0, Calendar arg1)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getTimestamp()");
		return null;
	}

	public int getType() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getType()");
		return 0;
	}

	public URL getURL(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getURL()");
		return null;
	}

	public URL getURL(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getURL()");
		return null;
	}

	public InputStream getUnicodeStream(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getUnicodeStream()");
		return null;
	}

	public InputStream getUnicodeStream(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getUnicodeStream()");
		return null;
	}

	public SQLWarning getWarnings() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.getWarnings()");
		return null;
	}

	public void insertRow() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.insertRow()");

	}

	public boolean isAfterLast() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.isAfterLast()");
		return positionCurseur > (tableauLignes.size() - 1);
	}

	public boolean isBeforeFirst() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.isBeforeFirst()");
		return positionCurseur == POSITION_BEFORE_FIRST_ROW;
	}

	public boolean isClosed() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.isClosed()");
		return false;
	}

	public boolean isFirst() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.isFirst()");		
		return positionCurseur == 0;
	}

	public boolean isLast() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.isLast()");
		return positionCurseur == (tableauLignes.size() - 1);
	}

	/**
	 * @todo implémenter type <code>TYPE_FORWARD_ONLY</code>
	 */
	public boolean last() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.last()");
		this.absolute(-1);
		return this.tableauLignes.size() > 0;
	}

	public void moveToCurrentRow() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.moveToCurrentRow()");

	}

	public void moveToInsertRow() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.moveToInsertRow()");

	}

	public boolean next() throws SQLException {
		////  System.out.println("JdbcFolderResultSet.next() positionCuseur avant inc = "+positionCurseur+" longueur dataset  = "+this.listeFichiers.length);
		positionCurseur++;
		return ( this.tableauLignes.size() - 1 ) >= positionCurseur;
	}

	public boolean previous() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.previous()");
		return false;
	}

	public void refreshRow() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.refreshRow()");

	}

	public boolean relative(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.relative()");
		return false;
	}

	public boolean rowDeleted() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.rowDeleted()");
		return false;
	}

	public boolean rowInserted() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.rowInserted()");
		return false;
	}

	public boolean rowUpdated() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.rowUpdated()");
		return false;
	}

	public void setFetchDirection(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.setFetchDirection()");

	}

	public void setFetchSize(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.setFetchSize()");

	}

	public void updateArray(int arg0, Array arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateArray()");

	}

	public void updateArray(String arg0, Array arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateArray()");

	}

	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateAsciiStream()");

	}

	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateAsciiStream()");

	}

	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateAsciiStream()");

	}

	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateAsciiStream()");

	}

	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateAsciiStream()");

	}

	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateAsciiStream()");

	}

	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBigDecimal()");

	}

	public void updateBigDecimal(String arg0, BigDecimal arg1)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBigDecimal()");

	}

	public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBinaryStream()");

	}

	public void updateBinaryStream(String arg0, InputStream arg1)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBinaryStream()");

	}

	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBinaryStream()");

	}

	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)	throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBinaryStream()");

	}

	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBinaryStream()");

	}

	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBinaryStream()");

	}

	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBlob()");

	}

	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBlob()");

	}

	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBlob()");

	}

	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBlob()");

	}

	public void updateBlob(int arg0, InputStream arg1, long arg2)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBlob()");

	}

	public void updateBlob(String arg0, InputStream arg1, long arg2)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBlob()");

	}

	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBoolean()");

	}

	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBoolean()");

	}

	public void updateByte(int arg0, byte arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateByte()");

	}

	public void updateByte(String arg0, byte arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateByte()");

	}

	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBytes()");

	}

	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateBytes()");

	}

	public void updateCharacterStream(int arg0, Reader arg1)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateCharacterStream()");

	}

	public void updateCharacterStream(String arg0, Reader arg1)	throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateCharacterStream()");

	}

	public void updateCharacterStream(int arg0, Reader arg1, int arg2)	throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateCharacterStream()");

	}

	public void updateCharacterStream(String arg0, Reader arg1, int arg2)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateCharacterStream()");

	}

	public void updateCharacterStream(int arg0, Reader arg1, long arg2)	throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateCharacterStream()");

	}

	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateCharacterStream()");

	}

	public void updateClob(int arg0, Clob arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateClob()");

	}

	public void updateClob(String arg0, Clob arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateClob()");

	}

	public void updateClob(int arg0, Reader arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateClob()");

	}

	public void updateClob(String arg0, Reader arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateClob()");

	}

	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateClob()");

	}

	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateClob()");

	}

	public void updateDate(int arg0, Date arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateDate()");

	}

	public void updateDate(String arg0, Date arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateDate()");

	}

	public void updateDouble(int arg0, double arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateDouble()");

	}

	public void updateDouble(String arg0, double arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateDouble()");

	}

	public void updateFloat(int arg0, float arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateFloat()");

	}

	public void updateFloat(String arg0, float arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateFloat()");

	}

	public void updateInt(int arg0, int arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateInt()");

	}

	public void updateInt(String arg0, int arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateInt()");

	}

	public void updateLong(int arg0, long arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateLong()");

	}

	public void updateLong(String arg0, long arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateLong()");

	}

	public void updateNCharacterStream(int arg0, Reader arg1)	throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNCharacterStream()");

	}

	public void updateNCharacterStream(String arg0, Reader arg1)	throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNCharacterStream()");

	}

	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNCharacterStream()");

	}

	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)	throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNCharacterStream()");

	}

//	JSE 6
	/*
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNClob()");

	}

	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNClob()");

	}

	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNClob()");

	}

	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNClob()");

	}

	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNClob()");

	}

	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNClob()");

	}

	public void updateNString(int arg0, String arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNString()");

	}

	public void updateNString(String arg0, String arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNString()");

	}
*/
	public void updateNull(int arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNull()");

	}

	public void updateNull(String arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateNull()");

	}

	public void updateObject(int arg0, Object arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateObject()");

	}

	public void updateObject(String arg0, Object arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateObject()");

	}

	public void updateObject(int arg0, Object arg1, int arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateObject()");

	}

	public void updateObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateObject()");

	}

	public void updateRef(int arg0, Ref arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateRef()");

	}

	public void updateRef(String arg0, Ref arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateRef()");

	}

	public void updateRow() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateRow()");

	}

//	JSE 6
	/*	
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateRowId()");

	}

	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateRowId()");

	}

	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateSQLXML()");

	}

	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateSQLXML()");

	}
*/
	public void updateShort(int arg0, short arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateShort()");

	}

	public void updateShort(String arg0, short arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateShort()");

	}

	public void updateString(int arg0, String arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateString()");

	}

	public void updateString(String arg0, String arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateString()");

	}

	public void updateTime(int arg0, Time arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateTime()");

	}

	public void updateTime(String arg0, Time arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateTime()");

	}

	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateTimestamp()");

	}

	public void updateTimestamp(String arg0, Timestamp arg1)
			throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.updateTimestamp()");

	}

	public boolean wasNull() throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.wasNull()");
		return false;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.isWrapperFor()");
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		//  System.out.println("JdbcFolderAbstractResultSet.unwrap()");
		return null;
	}

}

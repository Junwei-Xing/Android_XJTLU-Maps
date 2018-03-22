package com.example.mymap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * a SQLite database realizes some functions.
 *
 */
public class myDatabase extends SQLiteOpenHelper implements databaseApi{
	
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "GPS'INFORMATION";
	private String TABLE_CONTACTS = "points"; 
	private static final String KEY_ID = "id"; 
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_LATITUDE = "latitude"; 
	private static final String KEY_REVEAL = "reveal";
	private static final String KEY_LINK = "link";
	private static final String KEY_DESCRIBE = "describe";
	
	pointInfo piece;
	int count = 0;
	/**
	 * a constructed function is from SQLiteOpenHelper.
	 * @param context
	 */
	public myDatabase (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * create a table with these attributes.
	 */
	@Override
	public void onCreate(SQLiteDatabase myDB) {
		// TODO Auto-generated method stub
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_LONGITUDE + " TEXT,"
				+ KEY_LATITUDE + " TEXT,"
				+ KEY_REVEAL + " TEXT,"
				+ KEY_LINK + " TEXT,"
				+ KEY_DESCRIBE + " TEXT"
				+ ")";
				myDB.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * add a new tuple in this table.
	 */
	public void addData (pointInfo info) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues myValues = new ContentValues();

		myValues.put(KEY_LONGITUDE, info.getLongitude()); 
		myValues.put(KEY_LATITUDE , info.getLatitude()); 
		myValues.put(KEY_REVEAL, info.getReveal()); 
		myValues.put(KEY_LINK, info.getLink()); 
		myValues.put(KEY_DESCRIBE, info.getDescribe()); 
		
		db.insert(TABLE_CONTACTS, null, myValues);
		db.close(); 
		count = count + 1;
		}
	
	/**
	 * by ID to traverse the SQLite database.
	 */
	public pointInfo getData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_LONGITUDE, KEY_LATITUDE, KEY_REVEAL, KEY_LINK, KEY_DESCRIBE }, KEY_ID + "=?",
		new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		cursor.moveToFirst();
		
		piece = new pointInfo(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
				cursor.getString(3), cursor.getString(4) ,cursor.getString(5));
		return piece;
		}
	
	/**
	 * by the attribute "DESCRIBE" to index the data.
	 */
	
	public Cursor getIndex(String getDescribe){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor myCursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_LONGITUDE, KEY_LATITUDE, KEY_REVEAL, KEY_LINK, KEY_DESCRIBE }, KEY_DESCRIBE + "=?",
				new String[] {getDescribe},null,null,null,null);
		
		return myCursor;
	}
	
	public int getCount (){
		return count;
	}
	
}

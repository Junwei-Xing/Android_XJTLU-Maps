package com.example.mymap;

import android.database.Cursor;

/**
 * this target interface is different from databaseApi
 * based on the methods from database, and implements some extra operations.
 *
 */
public interface parseInputApi {

	public Cursor parseInput(String input,double myLongi,double myLati);
	
	public void constructDB ();
	
	public Cursor getIndex(String getDescribe);
	
	public pointInfo getData(int id);
}

package com.example.mymap;

import android.database.Cursor;

/**
 * this interface already exists, but the functions are not satisfied with present requirements.
 * adaptee interface.
 *
 */
public interface databaseApi {

	public void addData (pointInfo info);
	
	public pointInfo getData(int id) ;
	
	public Cursor getIndex(String getDescribe);
	
	public int getCount ();
}

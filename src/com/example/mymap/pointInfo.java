package com.example.mymap;

/**
 * This class is for the data construction for the SQLite database.
 * @author junwei.xing11
 *
 */
public class pointInfo {
	
	String longitude,latitude,reveal,link,describe;
	private int  _id;
	
	public pointInfo(int  _id, String longitude, String latitude, String reveal, String link, String describe){
		this._id = _id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.reveal = reveal;
		this.link = link;
		this.describe = describe;
	}

	public int getId(){
		return _id;
	}
	
	public String getLongitude(){
		return longitude;
	}
	
	public String getLatitude(){
		return latitude;
	}
	
	public String getReveal(){
		return reveal;
	}
	
	public String getLink(){
		return link;
	}
	
	public String getDescribe(){
		return describe;
	}
	
	public void setId(int _id){
		this._id = _id;
	}
	
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	
	public void setReveal(String reveal){
		this.reveal = reveal;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public void setDescribe(String describe){
		this.describe = describe;
	}
	
}

package com.example.mymap;

import android.graphics.PointF;
import android.util.Log;

/**
 * this class is a logical module to transform GPS data to the coordinate system with X value and Y value. 
 * @author junwei.xing11
 *
 */
public class myTrans {

	double nowLongi = 0; // hold the value of current position
	double nowLati = 0;
	int transW = 0; //hold the results of coordinate after calculating.
	int transH = 0;
	
    PointF p1 = new PointF (0f,0f); // the coordinate of the up left corner of screen
    PointF p2 = new PointF (0f,725f); // the coordinate of the bottom left corner of screen
    PointF p3 = new PointF (1025f,725f);// the coordinate of the bottom right corner of screen
    PointF p4 = new PointF (0f,725f); // the coordinate of the up right corner of screen

	 double fixLongi1 =120.73144455049008; // gps data of the up left corner of map
	 double fixLati1 = 31.27759898214914; 
	 double fixLongi2 = 120.7316831132832; // gps data the bottom left corner of map
	 double fixLati2 = 31.273418001173557;
	 double fixLongi3 = 120.7422018433988;// gps data the bottom right corner of map
	 double fixLati3 = 31.2738074697868; 
	 double fixLongi4 = 120.74122940503344;// gps data the up right corner of map
	 double fixLati4 = 31.27807651457051;
	
/**
 * in order to obtain gps data as input.
 * @param myLongi is the data of longitude of current position. 
 * @param myLati is the data of latitude of current position. 
 */
		
	public myTrans(double myLongi, double myLati){
		nowLongi = myLongi;
		nowLati = myLati;
	}
	
	public double getNowLongi(){
		return nowLongi;
	}
	
	public double getNowLati(){
		return nowLati;
	}
	
	public int gettransW(){
		return transW;
	}
	
	public int gettransH(){
		return transH;
	}

	/**
	 * reset the four vertexes if user invoke the operations of zooming out or in, moving.
	 * @param for1
	 * @param for2
	 * @param for3
	 * @param for4
	 */
	public void setScale(PointF for1,PointF for2,PointF for3,PointF for4){
		p1 = for1;
		p2 = for2;
		p3 = for3;
		p4 = for4;
	}
	
	/**
	 * this method is the main logical module to proceed the translation.
	 */
	public void arithmetic(){

		double value2 = fixLongi3 - fixLongi2; // get the real distance of width of the map.
		double value4 = fixLati4 - fixLati3; // get the real distance of height of the map.
		
		double result1 = (p3.x-p1.x)/value2; // calculate one degree of longitude equals to how much pixels.
		double result2 = (p3.y-p1.y)/value4; // calculate one degree of latitude equals to how much pixels.
		
		double distance1 = nowLongi - fixLongi1; //calculate the longitude difference between up left corner of map.
		double distance2 = fixLati1 - nowLati; //calculate the latitude difference between up left corner of map.
		
		// mapping the X value and Y value on coordinate system according to longitude and latitude, and then turn back.
		transW =(int)p1.x + Integer.parseInt(new java.text.DecimalFormat("0").format(distance1 * result1));
		transH =(int)p1.y + Integer.parseInt(new java.text.DecimalFormat("0").format(distance2 * result2));
	}

}

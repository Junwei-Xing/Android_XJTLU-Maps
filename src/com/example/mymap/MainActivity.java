package com.example.mymap;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * this class means client, in order to communicate with SingleTouchDrawView.class
 * particularly, deploy widgets for input data, receive the gps data from gps sensing.
 * @author junwei.xing11
 *
 */
public class MainActivity extends Activity {

	 // hold the GPS data of last known location.
	double OLastLatitude = 0;
	double OLastLongitude = 0;
	double OLastAltitude = 0;
	
	// hold the GPS data of current location.
	double NLastLatitude = 0; 
	double NLastLongitude = 0;
	double NLastAltitude = 0;
	
    SingleTouchDrawView myView; // connect to the SingleTouchDrawView.class
    Button search;
    EditText myInput;
    String getInput; // obtain what users input
    
    // for Database.
    pointInfo info;
    
    
    //create the adapted object.
    databaseApi myBase = new myDatabase(this);
    //convert to suitable interface.
    parseInputApi base = new Adapter(myBase);
    
    
    
    Cursor myCur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Compel to LANDSCAPE model.
        requestWindowFeature(Window.FEATURE_NO_TITLE);//hide the title.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		  WindowManager.LayoutParams.FLAG_FULLSCREEN);//Set full screen.
        
        setContentView(R.layout.activity_main);
        myInput = (EditText) findViewById(R.id.editText1);
        myView = (SingleTouchDrawView) findViewById(R.id.singleTouchDrawView1);

        myView.setDatabase(base); // pass a database object to SingleTouchDrawView.class



        displayOldLocation(); //invoke if GPS can NOT be received.
        displayNewLocaion(); //invoke if GPS can be received.
    }
    
    Button.OnClickListener listener = new Button.OnClickListener(){//listen the operation.
       public void onClick(View v){    
           getInput = myInput.getText().toString();
           
           //call the method, pass user input and present location.
           myCur = base.parseInput(getInput, OLastLongitude, OLastLatitude);
           
           int a = myCur.getCount();
           if(a > 0){
        	   myView.setSearch(myCur);   
           }else{
        	   // if users' input do NOT comply with the database, a reminder will be triggered.
        	   Toast.makeText(getApplicationContext(), "Wrong Information.", Toast.LENGTH_LONG).show();
           }
       }    
    };

    /**
     * if gps sensing do NOT receive any gps data, this method will revoke the last known data from stack.
     */
    
    public void displayOldLocation(){
    	LocationManager locationManager = (LocationManager)
    	this.getSystemService(Context.LOCATION_SERVICE);
    	Location lastLoca = locationManager.
    	getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	
    	TextView tv= (TextView) findViewById(R.id.text_menu1);

    	
    	if(lastLoca != null){
    	OLastLatitude = lastLoca.getLatitude();
    	OLastLongitude = lastLoca.getLongitude();
    	OLastAltitude = lastLoca.getAltitude();
    	lastLoca.getTime();
    	lastLoca.getAccuracy();
    	
    	//display this data on the screen for testing.
    	tv.setText("OLastLatitude=" + OLastLatitude + "; OLastLongitude=" + OLastLongitude + ": OLastAltitude=" + OLastAltitude + "\n");
    	}
    	else{
    		tv.setText("Can NOT acquire your GPS."); //If in the stack without last known data, a reminder will be displayed.
    	}
    	
    	myView.setLocation(OLastLongitude, OLastLatitude);

        
        search = (Button) findViewById(R.id.button1);
        search.setOnClickListener(listener);
        
    }
    
    /**
     * when a new data comes from gps sensing, it will be a coverage on that. 
     */
    
    public void displayNewLocaion(){
    	LocationManager locationManager = (LocationManager)
    	this.getSystemService(Context.LOCATION_SERVICE);
    	LocationListener locationListener = new LocationListener() {
    	public void onLocationChanged(Location location) {
    	NLastLatitude = location.getLatitude();
    	NLastLongitude = location.getLongitude();
    	NLastAltitude = location.getAltitude();
    	location.getTime();
    	location.getAccuracy();
    	
    	myView.setLocationMove(NLastLongitude , NLastLatitude);

    	TextView tv= (TextView) findViewById(R.id.text_menu1);
    	tv.setText("NLastLatitude=" + NLastLatitude + "; NLastLongitude=" + NLastLongitude + "; NLastAltitude=" + NLastAltitude + "\n");
    	}
    	public void onStatusChanged(String provider, int status, Bundle extras) {}
    	public void onProviderEnabled(String provider) {}
    	public void onProviderDisabled(String provider) {}
    	};
    	// Register the listener with the Location Manager to receive location updates
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    	
        
        search = (Button) findViewById(R.id.button1);
        search.setOnClickListener(listener);
    	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onResume (){
    	
    	//set landscape.	
    	if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
    		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    		 }
    		 super.onResume();
    		 
    }
    

    
}

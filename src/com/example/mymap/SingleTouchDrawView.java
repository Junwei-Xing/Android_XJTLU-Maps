package com.example.mymap;



import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * this class means a canvas to arrange the map and settle all pivots
 * this class is a transfer station between MainActivity.class and myTrans.class
 * @author junwei.xing11
 *
 */
public class SingleTouchDrawView extends View{

// test of the GPS's longitude and altitude.
double test1 = 120.73550441958264;
double test2 = 31.274513301108144;
final int markScope = 25; //the size of displaying images for augmented reality.
String myTarget; // obtain the DERCRIBE value of the termination of a navigation event.
double myLongi = 0, myLati = 0; // hold the GPS data from the MainActicity.class
//paint objects
private Paint mPaint = new Paint(); 
private Paint textPaint = new Paint();
private Paint transparent = new Paint();
//declaring myTrans
myTrans myCal,myCal2;
Bitmap photoView,resizeFB; // for the resizing the images in augmented reality.
float holdp2 = 0;
// tracing the X value and Y value of single touch.
int X = 0;
int Y = 0;
float scale = 0f; //what is the new size users set now.
float forscale = 0f; // as a judgment.
float distanceX = 0f,distanceY = 0f; //recording the effect of moving.
float fordistanceX = 0f,fordistanceY = 0f; // as a judgment.
parseInputApi getSet;
pointInfo info,info2,info3,info4,info5;
pointInfo[] display = new pointInfo[20]; //store every pivot for a navigation event
int displayCount = 0; //default as 42.
int tpValue = 0; // the value of transparency.
int count = 0;
String forSpare[] = new String[10]; // store the names of every pivot for a navigation event.
Cursor myIndex; //hold a cursor object.
String getResult; //hold the name of the destination of a navigation event.
double compare,hold,hold2;
int conveal;
//judge the touch event.
boolean twoF = false;
boolean once = true;
// record the width and height of screen.
int widthScreen = 1280;
int heightScreen = 750;

float x_down = 0;
float y_down = 0;
float oldDist = 1f;
float oldRotation = 0;
Matrix forMatrix = new Matrix();
Matrix matrix1 = new Matrix();
Matrix savedMatrix = new Matrix();

// record the touch event
private static final int NONE = 0;
private static final int DRAG = 1;
private static final int ZOOM = 2;
int mode = NONE;

boolean matrixCheck = false; //broach the function of resizing.
Bitmap resizeMap; // my map
myTrans forMark ; // myTrans object

//tracing four vertexes
PointF p1 = new PointF(0f,0f);
PointF p2 = new PointF(0f,855f);
PointF p3 = new PointF(1792f,855f);
PointF p4 = new PointF(1792f,0f);
PointF gP1 = new PointF(0f,0f);
PointF gP2 = new PointF(0f,0f);
PointF gP3 = new PointF(0f,0f);
PointF gP4 = new PointF(0f,0f);

boolean track = false;
Cursor forTrack;

public SingleTouchDrawView(Context context, AttributeSet attrs) {
super(context, attrs);
}

// during execution, this method will be abidingly invoked
protected void onDraw(Canvas canvas) { // Override the onDraw()
super.onDraw(canvas);


Bitmap bm = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.maps)); //for the image of red mark 
Bitmap map = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.section2)); // for the image of map.
Bitmap mark = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.mark)); // for the image of thumbtack.
Bitmap arrow = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.arrow1)); // for the image of arrow

//by transforming, calculate the coordinate.
myCal = new myTrans(myLongi,myLati);
if(twoF){
myCal.setScale(p1, p2, p3, p4);
}
myCal.arithmetic();
//minimize the guide icon.
Matrix matrix = new Matrix(); 
matrix.postScale(0.1f,0.1f); 

//resize the images to match the screen.
Matrix mapMatrix = new Matrix();
Bitmap resizeBmp = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);
Bitmap resizeMark = Bitmap.createBitmap(mark,0,0,mark.getWidth(),mark.getHeight(),matrix,true);
Bitmap resizeArrow = Bitmap.createBitmap(arrow,0,0,arrow.getWidth(),arrow.getHeight(),matrix,true);
float a = (float)1025/map.getWidth();
float b = (float)725/map.getHeight();

if(once){
mapMatrix.setScale(a,b);
}

resizeMap = Bitmap.createBitmap(map,0,0,map.getWidth(),map.getHeight(),mapMatrix,true);
canvas.drawBitmap(resizeMap,forMatrix,mPaint);

canvas.drawBitmap(resizeBmp,myCal.gettransW(),myCal.gettransH(),mPaint); 

canvas.drawText("Total pointers: " +myCal.gettransW()+"/"+myCal.gettransH() +"----------"+ X + "/" + Y 
		,10, 40 , textPaint);


myTrans mark1 = new myTrans(120.73846189873356,31.274726572265375); //EB tennis
myTrans mark2 = new myTrans(120.73843666540209,31.27514484981412); //BB near pavilion
myTrans mark3 = new myTrans(120.73805982982938,31.275555759384204); //BA near Global
myTrans mark4 = new myTrans(120.7371343284022,31.275603938252656); //between BA and PB
myTrans mark5 = new myTrans(120.73730678004814,31.275046394275762); //BB main entrance
myTrans mark6 = new myTrans(120.73737694960361,31.274724186068433); //EB main entrance and near while pavilion
myTrans mark7 = new myTrans(120.7372229158994,31.274210894204803); //between EB and EE
myTrans mark8 = new myTrans(120.7361263682994,31.274242580794812); //between B2 and EE
myTrans mark9 = new myTrans(120.73608309465376,31.275562888091113); //between B2 and PB
myTrans mark10 = new myTrans(120.73498975320143,31.275567858957892); //B2 SA
myTrans mark11 = new myTrans(120.73502740243406,31.275148927029633); //B2 SB
myTrans mark12 = new myTrans(120.73499648775017,31.274644880821256); //B2 SC
myTrans mark13 = new myTrans(120.73505871026285,31.27420174377303); //B2 SD
myTrans mark14 = new myTrans(120.73438377674132,31.275059825920526); //library main entrance
myTrans mark15 = new myTrans(120.73381249241497,31.274657762208774); //library near road
myTrans mark16 = new myTrans(120.73381799873127,31.275439479029583); //library near FB
myTrans mark17 = new myTrans(120.73379280945358,31.276310126894163); //FB back entrance
myTrans mark18 = new myTrans(120.7344928907257,31.276654868664032); //FB side entrance
myTrans mark19 = new myTrans(120.73375984621226,31.276902119408618); //FB main entrance

if(twoF){
	mark1.setScale(p1, p2, p3, p4);
	mark2.setScale(p1, p2, p3, p4);
	mark3.setScale(p1, p2, p3, p4);
	mark4.setScale(p1, p2, p3, p4);
	mark5.setScale(p1, p2, p3, p4);
	mark6.setScale(p1, p2, p3, p4);
	mark7.setScale(p1, p2, p3, p4);
	mark8.setScale(p1, p2, p3, p4);
	mark9.setScale(p1, p2, p3, p4);
	mark10.setScale(p1, p2, p3, p4);
	mark11.setScale(p1, p2, p3, p4);
	mark12.setScale(p1, p2, p3, p4);
	mark13.setScale(p1, p2, p3, p4);
	mark14.setScale(p1, p2, p3, p4);
	mark15.setScale(p1, p2, p3, p4);
	mark16.setScale(p1, p2, p3, p4);
	mark17.setScale(p1, p2, p3, p4);
	mark18.setScale(p1, p2, p3, p4);
	mark19.setScale(p1, p2, p3, p4);
}

mark1.arithmetic();
mark2.arithmetic();
mark3.arithmetic();
mark4.arithmetic();
mark5.arithmetic();
mark6.arithmetic();
mark7.arithmetic();
mark8.arithmetic();
mark9.arithmetic();
mark10.arithmetic();
mark11.arithmetic();
mark12.arithmetic();
mark13.arithmetic();
mark14.arithmetic();
mark15.arithmetic();
mark16.arithmetic();
mark17.arithmetic();
mark18.arithmetic();
mark19.arithmetic();



transparent.setAlpha(tpValue);
if (tpValue < 250){
	tpValue = tpValue + 45;
}else{
	tpValue = 0;
}

Matrix forShow = new Matrix(); 
forShow.postScale(0.4f,0.3f); 

for(int i = 1; i <= 42; i++){
	info2 = getSet.getData(i);
	
	if(info2.reveal.equals("N") && i != conveal){
		 forMark = new myTrans(Double.parseDouble(info2.getLongitude()),Double.parseDouble(info2.getLatitude()));
		 if(twoF){
		 forMark.setScale(p1, p2, p3, p4);
		 }
		forMark.arithmetic();
		canvas.drawBitmap(resizeMark,forMark.gettransW(),forMark.gettransH(),mPaint); 
		
		
	}
	
	if(i == conveal){
		 forMark = new myTrans(Double.parseDouble(info2.getLongitude()),Double.parseDouble(info2.getLatitude()));
		 if(twoF){
			 forMark.setScale(p1, p2, p3, p4);
			 }
		forMark.arithmetic();
		canvas.drawBitmap(resizeMark,forMark.gettransW(),forMark.gettransH(),transparent); 
		
	}
}


for(int k = 1; k < displayCount; k ++){
	
int getAngle = angle(Double.parseDouble(display[k].getLongitude()),Double.parseDouble(display[k].getLatitude()),Double.parseDouble(display[k-1].getLongitude()),Double.parseDouble(display[k-1].getLatitude()));
	Matrix matrix2 = new Matrix();      
	 matrix2.reset();  
	 matrix2.setRotate(getAngle);
	 matrix2.postScale(0.1f,0.1f);
	 Bitmap resizeArrow2 = Bitmap.createBitmap(arrow,0,0,mark.getWidth(),mark.getHeight(),matrix2,true);
	
	myTrans hiden = new myTrans(Double.parseDouble(display[k].getLongitude()),Double.parseDouble(display[k].getLatitude()));
	if(twoF){
		hiden.setScale(p1, p2, p3, p4);
	}
	hiden.arithmetic();		
	canvas.drawBitmap(resizeArrow2,hiden.gettransW(),hiden.gettransH(),transparent);
}

if(mark1.gettransW() - markScope< X && X < mark1.gettransW() + markScope && mark1.gettransH() - markScope< Y && Y < mark1.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.ebtennis));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark2.gettransW() - markScope< X && X < mark2.gettransW() + markScope && mark2.gettransH() - markScope< Y && Y < mark2.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.bbnearpavilion));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark3.gettransW() - markScope< X && X < mark3.gettransW() + markScope && mark3.gettransH() - markScope< Y && Y < mark3.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.bbglobal));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark4.gettransW() - markScope< X && X < mark4.gettransW() + markScope && mark4.gettransH() - markScope< Y && Y < mark4.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.bapb));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark5.gettransW() - markScope< X && X < mark5.gettransW() + markScope && mark5.gettransH() - markScope< Y && Y < mark5.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.ibss));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark6.gettransW() - markScope< X && X < mark6.gettransW() + markScope && mark6.gettransH() - markScope< Y && Y < mark6.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.ebmain));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark7.gettransW() - markScope< X && X < mark7.gettransW() + markScope && mark7.gettransH() - markScope< Y && Y < mark7.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.ebee));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark8.gettransW() - markScope< X && X < mark8.gettransW() + markScope && mark8.gettransH() - markScope< Y && Y < mark8.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.eeb2));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark9.gettransW() - markScope< X && X < mark9.gettransW() + markScope && mark9.gettransH() - markScope< Y && Y < mark9.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.pbb2));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark10.gettransW() - markScope< X && X < mark10.gettransW() + markScope && mark10.gettransH() - markScope< Y && Y < mark10.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.sa));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark11.gettransW() - markScope< X && X < mark11.gettransW() + markScope && mark11.gettransH() - markScope< Y && Y < mark11.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.sb));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark12.gettransW() - markScope< X && X < mark12.gettransW() + markScope && mark12.gettransH() - markScope< Y && Y < mark12.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.sc));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark13.gettransW() - markScope< X && X < mark13.gettransW() + markScope && mark13.gettransH() - markScope< Y && Y < mark13.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.sd));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark14.gettransW() - markScope< X && X < mark14.gettransW() + markScope && mark14.gettransH() - markScope< Y && Y < mark14.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.librarymain));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark15.gettransW() - markScope< X && X < mark15.gettransW() + markScope && mark15.gettransH() - markScope< Y && Y < mark15.gettransH() + markScope){
	Bitmap photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.library));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark16.gettransW() - markScope< X && X < mark16.gettransW() + markScope && mark16.gettransH() - markScope< Y && Y < mark16.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.libraryfb));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark17.gettransW() - markScope< X && X < mark17.gettransW() + markScope && mark17.gettransH() - markScope< Y && Y < mark17.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.fbback));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark18.gettransW() - markScope< X && X < mark18.gettransW() + markScope && mark18.gettransH() - markScope< Y && Y < mark18.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.fbside));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}

if(mark19.gettransW() - markScope< X && X < mark19.gettransW() + markScope && mark19.gettransH() - markScope< Y && Y < mark19.gettransH() + markScope){
	photoView = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.fbmain));
	resizeFB = Bitmap.createBitmap(photoView,0,0,photoView.getWidth(),photoView.getHeight(),forShow,true);
	canvas.drawBitmap(resizeFB,X,Y,mPaint); 
}


 
if(track){
	setSearch(forTrack);
}



}

/**
 * this method is a main arithmetic to find out the relevant pivots (arrow and nails) as the output of a navigation event.
 * @param forTarget
 */
public void setSearch(Cursor forTarget){
	track = true;
	forTrack = forTarget;
	display = new pointInfo[20];
	displayCount = 0;
	
	 forTarget.moveToFirst();
	 
	info = new pointInfo(Integer.parseInt(forTarget.getString(0)),forTarget.getString(1),forTarget.getString(2),
			forTarget.getString(3),forTarget.getString(4),forTarget.getString(5)); 
	conveal = info.getId();
	display[displayCount] = info;	
	displayCount = displayCount + 1;
	info5 = info;
	myTarget = info.getLongitude()+ "/" + info.getLatitude() + "/" + info.getReveal()+ "/" + info.getLink()+ "/" + info.getDescribe();
	forSpare = info.getLink().split("-");
	myCal2 = new myTrans(Double.parseDouble(info.getLongitude()),Double.parseDouble(info.getLatitude()));
	 if(twoF){
	 myCal2.setScale(p1, p2, p3, p4);
	 }
	myCal2.arithmetic();
	 compare = 1500;
	Boolean judge = true;
	
	while(judge){
	for(int y = 0;y < forSpare.length;y++){
		myIndex = getSet.getIndex(forSpare[y]);
		myIndex.moveToFirst();
		info4 = new pointInfo(Integer.parseInt(myIndex.getString(0)),myIndex.getString(1),myIndex.getString(2),
				myIndex.getString(3),myIndex.getString(4),myIndex.getString(5)); 
		myCal2 = new myTrans(Double.parseDouble(info4.getLongitude()),Double.parseDouble(info4.getLatitude()));
		 if(twoF){
			 myCal2.setScale(p1, p2, p3, p4);
			 }
		myCal2.arithmetic();
		hold = Math.sqrt(Math.pow((myCal.gettransH()-myCal2.gettransH()), 2) + Math.pow((myCal.gettransW()-myCal2.gettransW()), 2));
		
		// Log.d("1", info4.getDescribe() + "/" + hold + "/" + myCal.gettransH()+ "/" +myCal.gettransW()+"/"+myCal2.gettransH()+"/"+ myCal2.gettransW());
		
		if(hold<compare){
			compare = hold;
			getResult = info4.getDescribe();
		}
		
        
		
	}
	
	myCal2 = new myTrans(Double.parseDouble(info5.getLongitude()),Double.parseDouble(info5.getLatitude()));
	 if(twoF){
	 myCal2.setScale(p1, p2, p3, p4);
	 }
	myCal2.arithmetic();
	hold = Math.sqrt(Math.pow((myCal.gettransH()-myCal2.gettransH()), 2) + Math.pow((myCal.gettransW()-myCal2.gettransW()), 2));
	if(hold==compare){
		judge = false;
		break;	
	}
	
	myIndex = getSet.getIndex(getResult);
	myIndex.moveToFirst();
	info4 = new pointInfo(Integer.parseInt(myIndex.getString(0)),myIndex.getString(1),myIndex.getString(2),
			myIndex.getString(3),myIndex.getString(4),myIndex.getString(5));
	info5 = info4;
	display[displayCount] = info4;
	displayCount = displayCount + 1;
	forSpare = info4.getLink().split("-");


	}

	
	
	}

// for a database object as the input from MainActivity.class.
public void setDatabase(parseInputApi set){

	getSet = set;
}

//for the GPS data of last known position as the inputs from MainActivity.class.
public void setLocation (double myLongitude, double myLatitude){
	myLongi = myLongitude;
	myLati = myLatitude;
}

//for the GPS data of current position as the inputs from MainActivity.class.
public void setLocationMove (double myLongitude, double myLatitude){
	myLongi = myLongitude;
	myLati = myLatitude;
	invalidate();
}

/**
 * listen the state of touch event.
 */
public boolean onTouchEvent(MotionEvent event) {
	switch (event.getAction() & MotionEvent.ACTION_MASK) {
	case MotionEvent.ACTION_DOWN:
		mode = DRAG;
		x_down = event.getX();
		y_down = event.getY();
		X = (int) x_down;
		Y = (int) y_down;
		savedMatrix.set(forMatrix);
		invalidate();
		break;
	case MotionEvent.ACTION_POINTER_DOWN:
		mode = ZOOM;
		oldDist = spacing(event);
		savedMatrix.set(forMatrix);
		invalidate();
		break;
	case MotionEvent.ACTION_MOVE:
		if (mode == ZOOM) {
			once = false;
			twoF = true;
			matrix1.set(savedMatrix);
			float newDist = spacing(event);
			scale = newDist / oldDist;
			matrix1.postScale(scale, scale);
			matrixCheck = matrixCheck();
			if (matrixCheck == false) {
				forMatrix.set(matrix1);
				invalidate();
			}
		} else if (mode == DRAG) {
			X = (int) event.getX();
			Y = (int) event.getY();
			matrix1.set(savedMatrix);
			distanceX = event.getX() - x_down;
			distanceY =  event.getY() - y_down;
			matrix1.postTranslate(distanceX,distanceY);
			matrixCheck = matrixCheck();
			matrixCheck = matrixCheck();
			if (matrixCheck == false) {
				fordistanceX = distanceX;
				fordistanceY = distanceY;
				forMatrix.set(matrix1);
				invalidate();
			}
			
		}
		break;
	case MotionEvent.ACTION_UP:{
		if(twoF){
		follow();
		fordistanceX = 0;
		fordistanceY = 0;
		}
	}
	case MotionEvent.ACTION_POINTER_UP:{
		if(forscale!=scale && twoF){
			forscale = scale;
			followScale();
			}
	}
		mode = NONE;
		break;
	}
	return true;
}

/**
 * calculate the rough angle from a point to red mark.
 * @param longitude1
 * @param latitude1
 * @param longitude2
 * @param latitude2
 * @return
 */
public int angle(double longitude1, double latitude1, double longitude2, double latitude2){
    int transAngle ;
    
    double a = Math.abs(longitude1 - longitude2 );
    double b = Math.abs(latitude1 - latitude2 );
    
    if(a >= b){
    	if(longitude1 >= longitude2){
    		transAngle = 270;
    	}else{
    		transAngle = 90;
    	}
    	
    }else{
    	
    	if(latitude1 >= latitude2){
    		transAngle = 180;
    	}else{
    		transAngle = 0;
    	}
    }

    return transAngle;
	
}

/**
 * limit the operations are not over the boundary.
 * @return
 */
private boolean matrixCheck() {
	float[] f = new float[9];
	matrix1.getValues(f);
	// four vertexes of map.
	float x1 = f[0] * 0 + f[1] * 0 + f[2];
	float y1 = f[3] * 0 + f[4] * 0 + f[5];
	float x2 = f[0] * resizeMap.getWidth() + f[1] * 0 + f[2];
	float y2 = f[3] * resizeMap.getWidth() + f[4] * 0 + f[5];
	float x3 = f[0] * 0 + f[1] * resizeMap.getHeight() + f[2];
	float y3 = f[3] * 0 + f[4] * resizeMap.getHeight() + f[5];
	float x4 = f[0] * resizeMap.getWidth() + f[1] * resizeMap.getHeight() + f[2];
	float y4 = f[3] * resizeMap.getWidth() + f[4] * resizeMap.getHeight() + f[5];
	// current width of map.
	double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	// the scale of zooming in and out.
	if (width < widthScreen || width > widthScreen * 3) {
		return true;
	}
	// judge whether the map is over the boundary.
	if ((x1 < widthScreen / 3 && x2 < widthScreen / 3
			&& x3 < widthScreen / 3 && x4 < widthScreen / 3)
			|| (x1 > widthScreen * 2 / 3 && x2 > widthScreen * 2 / 3
					&& x3 > widthScreen * 2 / 3 && x4 > widthScreen * 2 / 3)
			|| (y1 < heightScreen / 3 && y2 < heightScreen / 3
					&& y3 < heightScreen / 3 && y4 < heightScreen / 3)
			|| (y1 > heightScreen * 2 / 3 && y2 > heightScreen * 2 / 3
					&& y3 > heightScreen * 2 / 3 && y4 > heightScreen * 2 / 3)) {
		return true;
	}
	return false;
}

/**
 * calculate the distance between 2 points in coordinate system.
 * @param event
 * @return
 */
private float spacing(MotionEvent event) {
	float x = event.getX(0) - event.getX(1);
	float y = event.getY(0) - event.getY(1);
	return FloatMath.sqrt(x * x + y * y);
}

/**
 * this method is for alter the four vertexes with the change of moving
 */

private void follow(){
	p1.x = p1.x + fordistanceX;
	p1.y = p1.y + fordistanceY;
	p2.x = p2.x + fordistanceX;
	p2.y = p2.y + fordistanceY;
	p3.x = p3.x + fordistanceX;
	p3.y = p3.y + fordistanceY;
	p4.x = p4.x + fordistanceX;
	p4.y = p4.y + fordistanceY;
	Log.d("forMypoints",Float.toString(forscale)+"/"+Float.toString(p1.x)+"/"+Float.toString(p1.y)+"/"+Float.toString(p2.x)+"/"+Float.toString(p2.y)+"/"+Float.toString(p3.x)+"/"+Float.toString(p3.y)+"/"+Float.toString(p4.x)+"/"+Float.toString(p4.y));

	
}

/**
 * this method is for alter the four vertexes with the change of resizing
 */
private void followScale(){
	Log.d("-----------",Float.toString(scale)+"/"+Float.toString(p1.x)+"/"+Float.toString(p1.y)+"/"+Float.toString(p2.x)+"/"+Float.toString(p2.y)+"/"+Float.toString(p3.x)+"/"+Float.toString(p3.y)+"/"+Float.toString(p4.x)+"/"+Float.toString(p4.y));
	p1.x =  p1.x*forscale;
	p1.y =  p1.y*forscale;
	p2.x =  p2.x*forscale;
	p2.y =  p2.y*forscale;
	p3.x =  p3.x*forscale;
	p3.y =  p3.y*forscale;
	p4.x =  p4.x*forscale;
	p4.y =  p4.y*forscale;
	Log.d("+++++++++++",Float.toString(forscale)+"/"+Float.toString(p1.x)+"/"+Float.toString(p1.y)+"/"+Float.toString(p2.x)+"/"+Float.toString(p2.y)+"/"+Float.toString(p3.x)+"/"+Float.toString(p3.y)+"/"+Float.toString(p4.x)+"/"+Float.toString(p4.y));
}

}	     
	    
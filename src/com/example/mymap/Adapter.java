package com.example.mymap;

import android.database.Cursor;
import android.util.Log;

/**
 * adapter
 *
 */
public class Adapter implements parseInputApi{
	
	private databaseApi adaptee;//hold the interface object.
	
	private pointInfo myInfo;
	private myTrans myTrans1,myTrans2;
	private String upperInput, upper,result = "ERROR";
	private double shorter = 1500, hold = 0;
    private static final int FOR_PROMARY_KEY = 1;
    private Cursor forCur;
    
	/**
	 * obtain the object that the interface should be adapted. 
	 * @param adaptee
	 */
	public Adapter(databaseApi adaptee){
		this.adaptee = adaptee;
	}

	/**
	 * according to the user input and present location, to determine the shortest distance. 
	 * invoke the methods (getData and getIndex) from myDatabase.
	 */
	@Override
	public Cursor parseInput(String input,double myLongi, double myLati) {
		// TODO Auto-generated method stub		
		upper = input.toUpperCase();
		if(upper.length() >= 2){
		upperInput = upper.substring(0, 2);
		}

		myTrans1 = new myTrans(myLongi,myLati);
		myTrans1.arithmetic();
		

		
		for(int i = 1; i <= 42; i++){
			myInfo = adaptee.getData(i);
			
			if(upperInput.equals("FB")){
				if(myInfo.getDescribe().equals("FB1")){
                
					calculate();			
				}else if(myInfo.getDescribe().equals("FB2")){

					calculate();
				}else if(myInfo.getDescribe().equals("FB3")){

					calculate();
				}
				
			}else if(upperInput.equals("SB")){
				if(myInfo.getDescribe().equals("SA")){
					calculate();	
				}else if(myInfo.getDescribe().equals("SB")){
					calculate();
				}else if(myInfo.getDescribe().equals("SC")){
					calculate();
				}else if(myInfo.getDescribe().equals("SD")){
					calculate();
				}else if(myInfo.getDescribe().equals("SAPB")){
					calculate();
				}else if(myInfo.getDescribe().equals("SDEE")){
					calculate();
				}
				
			}else if(upperInput.equals("CB")){
			if(myInfo.getDescribe().equals("LB1")){
					calculate();
				}else if(myInfo.getDescribe().equals("LB2")){
					calculate();
				}else if(myInfo.getDescribe().equals("LB3")){
					calculate();
				}
				
			}else if(upperInput.equals("EB")){
				if(myInfo.getDescribe().equals("EB1")){
					calculate();		
				}else if(myInfo.getDescribe().equals("EB2")){
					calculate();
				}else if(myInfo.getDescribe().equals("EBEE")){
					calculate();
				}
				
				
				
			}else if(upperInput.equals("BB")){
		        if(myInfo.getDescribe().equals("BB1")){
		        	calculate();
				}else if(myInfo.getDescribe().equals("BB2")){
					calculate();
				}
				
			}else if(upperInput.equals("EE")){
		        if(myInfo.getDescribe().equals("SDEE")){
		        	calculate();
				}else if(myInfo.getDescribe().equals("EBEE")){
					calculate();
				}
				
				
			}else if(upperInput.equals("PB")){
		        if(myInfo.getDescribe().equals("SAPB")){
		        	calculate();
				}else if(myInfo.getDescribe().equals("BAPB")){
					calculate();
				}
				
			}else if(upperInput.equals("BA")){
		        if(myInfo.getDescribe().equals("BAPB")){
		        	calculate();
				}else if(myInfo.getDescribe().equals("BA")){
					calculate();
				}
		}
		
		}
		
		forCur = adaptee.getIndex(result);
		result = "ERROR";
		shorter = 1500;
		return forCur;

		

}
	private void calculate(){
		myTrans2 = new myTrans(Double.parseDouble(myInfo.getLongitude()),Double.parseDouble(myInfo.getLatitude()));
		myTrans2.arithmetic();
		hold = Math.sqrt(Math.pow((myTrans1.gettransH()-myTrans2.gettransH()), 2) + Math.pow((myTrans1.gettransW()-myTrans2.gettransW()), 2));
		if(hold<shorter){
			shorter = hold;
			result = myInfo.getDescribe();
	
		}
	}

	/**
	 * use getIndex to judge whether the database is null
	 * and invoke addData to construct database.
	 */
	@Override
	public void constructDB() {
		// TODO Auto-generated method stub
		
		Cursor judge = adaptee.getIndex("1");
    	int b = judge.getCount();
    	if(b ==  0){
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73614860806711","31.273976454264886","Y", "2-23","1"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73478660731529","31.273926305466906","Y", "3-1","2"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73478077542022","31.274488389539584","Y", "2-4-7-15","3"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73339003098127","31.27449631231079","Y", "3-5","4"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73294139607741","31.27579743844745","Y", "6-11","5"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73380627204627","31.27581019445275","Y", "5-7","6"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73475177119037","31.27581472017837","Y", "3-6-8-14","7"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73491995180959","31.276642746371564","Y", "7-9-13","8"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73487439964643","31.277316803502295","Y", "8-10-12","9"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73380125232615","31.277386505398432","Y", "9-11","10"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73233212841629","31.277364261260367","Y", "5-10","11"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.7360872741393","31.277368540913497","Y", "9-13-20","12"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73611202432858","31.27661347796577","Y", "8-12-14-19","13"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73609551174061","31.275785228698908","Y", "7-13-15-17","14"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73616285617572","31.27469791389626","Y", "1-3-14-16","15"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73714536934874","31.27484693496662","Y", "15-17-21-23","16"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.7372153470179","31.275857553882808","Y", "14-16-18","17"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73787200028794","31.27588399538768","Y", "17-19-21","18"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73740726563493","31.276512697266675","Y", "13-18-20","19"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.7380229709922","31.277373155113178","Y", "12-19","20"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73878263905421","31.274965982509044","Y", "1-18-22","21"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73925597954468","31.274075695852793","Y", "21","22"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73730672041237","31.273973352744013","Y", "1-16-22","23"));
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73846189873356","31.274726572265375","N", "16-21-22","EB1"));//EB tennis
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73843666540209","31.27514484981412","N", "16-18-22","BB1"));//BB near pavilion
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73805982982938","31.275555759384204","N", "18-21","BA"));//BA near Global
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.7371343284022","31.275603938252656","N", "16-17","BAPB"));//between BA and PB
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73730678004814","31.275046394275762","N", "16-17-21","BB2"));//BB main entrance
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73737694960361","31.274724186068433","N", "16-23","EB2"));//EB main entrance and near while pavilion
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.7372229158994","31.274210894204803","N", "16-23","EBEE"));//between EB and EE
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.7361263682994","31.274242580794812","N", "1-3-15","SDEE"));//between B2 and EE
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73608309465376","31.275562888091113","N", "14-15","SAPB"));//between B2 and PB
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73498975320143","31.275567858957892","N", "3-7","SA"));//B2 SA
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73502740243406","31.275148927029633","N", "3-7","SB"));//B2 SB
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73499648775017","31.274644880821256","N", "3-7","SC"));//B2 SC
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73505871026285","31.27420174377303","N", "2-3","SD"));//B2 SD
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73438377674132","31.275059825920526","N", "3-7","LB1"));//library main entrance
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73381249241497","31.274657762208774","N", "3-4","LB2"));//library near road
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73381799873127","31.275439479029583","N", "6","LB3"));//library near FB
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73379280945358","31.276310126894163","N", "6-8","FB1"));//FB back entrance
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.7344928907257","31.276654868664032","N", "8","FB2"));//FB side entrance
    		adaptee.addData(new pointInfo(FOR_PROMARY_KEY , "120.73375984621226","31.276902119408618","N", "10","FB3"));//FB main entrance
    	}
   	
	}

	@Override
	public Cursor getIndex(String getDescribe) {
		// TODO Auto-generated method stub
		Cursor myCursor = adaptee.getIndex(getDescribe);
		return myCursor;
	}

	@Override
	public pointInfo getData(int id) {
		// TODO Auto-generated method stub
		pointInfo forInfo = adaptee.getData(id);
		return forInfo;
	}
	
	
}

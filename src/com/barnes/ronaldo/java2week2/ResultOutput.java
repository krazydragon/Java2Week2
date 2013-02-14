/*
 * project	Java2Week2
 * 
 * package	com.barnes.ronaldo.java2week2
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Feb 12, 2013
 */
package com.barnes.ronaldo.java2week2;

import java.util.HashMap;


import com.barnes.ronaldo.java2week2.R;
import com.rbarnes.lib.FileInterface;
import com.rbarnes.lib.WebInterface;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.ImageView;
import android.widget.TextView;


public class ResultOutput extends Activity implements OnClickListener{

	
	Context _context;
	HashMap<String, String> _resultValues;
	String _titleStr;
	String _addressStr;
	String _cityStr;
	String _stateStr;
	String _phoneStr;
	String _mapCoord;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.result_fragment);
		_context = this;
		
		Button callButton = (Button)findViewById(R.id.callButton);
		Button mapButton = (Button)findViewById(R.id.mapButton);
		Boolean connected = WebInterface.getConnectionStatus(_context);
		_resultValues = getOldLocation();
		if (!connected && _resultValues == null){
			//No Internet or saved files found
		}else{
			//Display results
			displayResults();
		}
		
		
		
		
		
		
		//Detect Button CLick
		callButton.setOnClickListener(this);
		mapButton.setOnClickListener(this);
		
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.callButton:
			try {
		        Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse(_phoneStr));
		        startActivity(callIntent);
		        } catch (ActivityNotFoundException e) {
		        Log.e("CALL", "Call failed", e);
		    }
		break;
		case R.id.mapButton:
			try {
				
				Intent mapIntent = new Intent(this, MapOutput.class);
				startActivity(mapIntent);
		        } catch (ActivityNotFoundException e) {
		        Log.e("MAP", "Map did not launch", e);
		    }
		break;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getOldLocation(){
		Object stored = null;
		stored = FileInterface.readObjectFile(_context, "oldLocation", false);
		HashMap<String, String> oldLocation;
		if(stored == null){
			Log.i("OLD LOCATION", "NO OLD LOCATION FILE FOUND");
			oldLocation = null;
		}else{
			oldLocation = (HashMap<String, String>) stored;
		}
		return oldLocation;
	}
	
	private void displayResults(){
			//ImageView resultImageView = (ImageView)findViewById(R.id.resultImageView);
			TextView tempTitle = (TextView)findViewById(R.id.titleValue);
			TextView tempAddress = (TextView)findViewById(R.id.addressValue);
			TextView tempCity = (TextView)findViewById(R.id.cityValue);
			TextView tempState = (TextView)findViewById(R.id.stateValue);
			//Bundle resultIntent = getIntent().getExtras();
			//String imageText = resultIntent.getString("Picture");
			
			
			
			tempTitle.setText(_resultValues.get("Title").toString());
			tempAddress.setText(_resultValues.get("Address").toString());
			tempCity.setText(_resultValues.get("City").toString());
			tempState.setText(_resultValues.get("State").toString());
			_phoneStr = "tel:" + (_resultValues.get("Phone").toString());
			_mapCoord = "geo:" + (_resultValues.get("Coords").toString() + "?z=50");
			
			
			
			/*if(imageText == "cookies"){
				resultImageView.setImageResource(R.drawable.cookies);	
			}else if(imageText == "candy"){
				resultImageView.setImageResource(R.drawable.candy);
			}else if(imageText == "pies"){
				resultImageView.setImageResource(R.drawable.pies);	
			}else if(imageText == "cakes"){
				resultImageView.setImageResource(R.drawable.cakes);
			}*/
		}
	
}



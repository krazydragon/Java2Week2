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
import com.barnes.ronaldo.java2week2.ResultFragment.ResultListener;
import com.rbarnes.lib.FileInterface;
import com.rbarnes.lib.WebInterface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

//import android.widget.ImageView;
import android.widget.TextView;


public class ResultOutput extends Activity implements ResultListener{

	
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
		
		_resultValues = getOldLocation();
		//Display results
		displayResults();
		
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

	@Override
	public void showResults() {
	
			
		
		
	}
}



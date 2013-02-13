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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONException;
import org.json.JSONObject;





import com.barnes.ronaldo.java2week2.InputFragment.InputListener;
import com.barnes.ronaldo.java2week2.ResultOutput;
import com.rbarnes.lib.FileInterface;
import com.rbarnes.lib.WebInterface;
import com.rbarnes.other.Dessert;





import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainInputForm extends Activity implements InputListener{

	Context _context;
	ArrayList<Dessert> _desserts;
	HashMap<String, String> _oldLocation;
	EditText _inputField;
	Toast _toast;
	JSONObject _tempLocation;
	Button _inputButton;
	int _buttonId;
	Intent _resulutIntent;
	String _picture;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.input_fragment);
		_context = this;
		_resulutIntent = new Intent(this, ResultOutput.class);
		_oldLocation = new HashMap<String, String>();
		Boolean connected = WebInterface.getConnectionStatus(_context);
		
		if (!connected){
			displayResults();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	//Display results
	private void displayResults(){
		
		startActivity(_resulutIntent);
	}
	//Submit search
	private void getLocations(String dessert, String zipCode){
		String baseUrl = "http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=qJIjRlbV34GJZfg2AwqSWVV03eeg8SpTQKy5PZqSfjlRrItt5hS2n3PIysdPU_CCIQlCGXIGjoTDESp3l42Ueic3O1EaYXU-&query="+dessert+"&zip="+zipCode+"&results=1&output=json";
		URL finalURL;
		try{
			finalURL = new URL(baseUrl);
			LocationRequest lr = new LocationRequest();
			lr.execute(finalURL);
			Log.i("URL ", baseUrl);
			
		}catch(MalformedURLException e){
			Log.e("BAD URL","MALFORMED URL");
			finalURL = null;
		}
	}
	
	//get results
	private class LocationRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			
			for(URL url: urls){
				
				response = WebInterface.getUrlStringResponse(url);
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
			try{
				JSONObject json = new JSONObject(result);
				JSONObject locations = json.getJSONObject("ResultSet");
				if(locations.getString("totalResultsAvailable").compareTo("0")==0){
					_toast = Toast.makeText(_context, "No Results" , Toast.LENGTH_SHORT);
					_toast.show();
				}else{
					JSONObject location = locations.getJSONObject("Result");
					if(location != null){
						_toast = Toast.makeText(_context, "Saving File.", Toast.LENGTH_SHORT);
						_toast.show();
						_oldLocation.put("Title",  location.getString("Title"));
						_oldLocation.put("Address", location.getString("Address"));
						_oldLocation.put("City", location.getString("City"));
						_oldLocation.put("State", location.getString("State"));
						_oldLocation.put("Phone", location.getString("Phone"));
						_oldLocation.put("Coords", location.getString("Latitude")+","+location.getString("Longitude"));
						
						
						
						//Save File
						FileInterface.storeObjectFile(_context, "oldLocation", _oldLocation, false);
						//Show data
						//Add Location Display
						displayResults();
					}else{
						_toast = Toast.makeText(_context, "Something went wrong" , Toast.LENGTH_SHORT);
						_toast.show();
					}
				}
				
			}catch(JSONException e){
				Log.e("JSON", "JSON OBJECT EXCEPTION");
			}
			
		}
	}

	@Override
	public void onDessertSearch(String zipCode, String category) {
		
		getLocations(zipCode,category);
	}


}

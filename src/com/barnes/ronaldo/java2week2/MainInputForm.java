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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainInputForm extends Activity implements OnClickListener {

	Context _context;
	ArrayList<Dessert> _desserts;
	HashMap<String, String> _oldLocation;
	EditText _inputField;
	Toast _toast;
	JSONObject _tempLocation;
	LinearLayout _inputLayout;
	Button _inputButton;
	int _buttonId;
	Intent _resulutIntent;
	String _picture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_input_form);
		 _inputLayout = (LinearLayout)findViewById(R.id.InputFormLayout);
		_context = this;
		_resulutIntent = new Intent(this, ResultOutput.class);
		_oldLocation = new HashMap<String, String>();
		_inputButton = (Button)findViewById(R.id.inputButton);
		//Detect form elements
		_inputButton = (Button)findViewById(R.id.inputButton);
		Button cookieButton = (Button)findViewById(R.id.cookieButton);
		Button pieButton = (Button)findViewById(R.id.pieButton);
		Button cakeButton = (Button)findViewById(R.id.cakeButton);
		Button candyButton = (Button)findViewById(R.id.candyButton);
		//Detect button click
		cookieButton.setOnClickListener(this);
		pieButton.setOnClickListener(this);
		cakeButton.setOnClickListener(this);
		candyButton.setOnClickListener(this);
		_inputButton.setOnClickListener(this);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		RadioGroup inputGroup = (RadioGroup)findViewById(R.id.inputRadioGroup);
		ImageView dessertView = (ImageView)findViewById(R.id.dessert_view);
		switch(v.getId()){
		//Submit Form
		case R.id.inputButton:
			//show results
			int selectedButtonId = inputGroup.getCheckedRadioButtonId();
			RadioButton selectedButton = (RadioButton) findViewById(selectedButtonId);
			Spinner inputSpinner = (Spinner) findViewById(R.id.inputSpinner);
			String buttonText = (String) selectedButton.getText();
			String spinnerText = String.valueOf(inputSpinner.getSelectedItem());
			getLocations(buttonText,spinnerText);

		//Change image when button is pressed	
		case R.id.cookieButton:
			dessertView.setImageResource(R.drawable.cookies);
			_resulutIntent.putExtra("Picture", "cakes");
			break;
		case R.id.pieButton:
			dessertView.setImageResource(R.drawable.pies);
			_resulutIntent.putExtra("Picture", "pies");
			break;
		case R.id.cakeButton:
			dessertView.setImageResource(R.drawable.cakes);
			_resulutIntent.putExtra("Picture", "cakes");
			break;
		case R.id.candyButton:
			dessertView.setImageResource(R.drawable.candy);
			_resulutIntent.putExtra("Picture", "candy");
			break;
			
		}
	}

}

package com.barnes.ronaldo.java2week2;

import java.util.HashMap;



import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;


public class ResultFragment extends Fragment implements OnClickListener{
	
	
	HashMap<String, String> _resultValues;
	String _titleStr;
	String _addressStr;
	String _cityStr;
	String _stateStr;
	String _phoneStr;
	String _mapCoord;
	
	private ResultListener listener;
	
	public interface ResultListener{
		public void showResults();
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.result, container, false);
		
		Button callButton = (Button)view.findViewById(R.id.callButton);
		
		//Detect Button CLick
		callButton.setOnClickListener(this);
		return view;
		//Display results
		

		
		
		
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
		
		}
	}


	
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			listener = (ResultListener)activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString() + "did not Implemnt Result Listener!");
		}
	}




}

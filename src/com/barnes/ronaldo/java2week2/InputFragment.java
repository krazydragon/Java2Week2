package com.barnes.ronaldo.java2week2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class InputFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.activity_main_input_form, container, false);
		
		return view;
	}

}

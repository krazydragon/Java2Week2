package com.barnes.ronaldo.java2week2;



import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class InputFragment extends Fragment implements OnClickListener  {
	
	private InputListener listener;
	
	public interface InputListener{
		public void onDessertSearch(String zipCode, String category);
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		super.onCreateView(inflater, container, savedInstanceState);
		
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.activity_main_input_form, container, false);
		
		//Detect form elements
		Button inputButton = (Button)view.findViewById(R.id.inputButton);
		Button cookieButton = (Button)view.findViewById(R.id.cookieButton);
		Button pieButton = (Button)view.findViewById(R.id.pieButton);
		Button cakeButton = (Button)view.findViewById(R.id.cakeButton);
		Button candyButton = (Button)view.findViewById(R.id.candyButton);
		//Detect button click
		cookieButton.setOnClickListener(this);
		pieButton.setOnClickListener(this);
		cakeButton.setOnClickListener(this);
		candyButton.setOnClickListener(this);
		inputButton.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		RadioGroup inputGroup = (RadioGroup)getActivity().findViewById(R.id.inputRadioGroup);
		ImageView dessertView = (ImageView)getActivity().findViewById(R.id.dessert_view);
		switch(v.getId()){
		//Submit Form
		case R.id.inputButton:
			//show results
			int selectedButtonId = inputGroup.getCheckedRadioButtonId();
			RadioButton selectedButton = (RadioButton) getActivity().findViewById(selectedButtonId);
			Spinner inputSpinner = (Spinner) getActivity().findViewById(R.id.inputSpinner);
			String buttonText = (String) selectedButton.getText();
			String spinnerText = String.valueOf(inputSpinner.getSelectedItem());
			listener.onDessertSearch(buttonText,spinnerText);

		//Change image when button is pressed	
		case R.id.cookieButton:
			dessertView.setImageResource(R.drawable.cookies);
			break;
		case R.id.pieButton:
			dessertView.setImageResource(R.drawable.pies);
			break;
		case R.id.cakeButton:
			dessertView.setImageResource(R.drawable.cakes);
			break;
		case R.id.candyButton:
			dessertView.setImageResource(R.drawable.candy);
			break;
		
		}
	}
		@Override
		public void onAttach(Activity activity){
			super.onAttach(activity);
			try{
				listener = (InputListener)activity;
			}catch(ClassCastException e){
				throw new ClassCastException(activity.toString() + "did not Implemnt Input Listener!");
			}
		}
}

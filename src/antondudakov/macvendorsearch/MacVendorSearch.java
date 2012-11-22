package antondudakov.macvendorsearch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MacVendorSearch extends Activity {
	int CurrentPointer = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mac_vendor_search);
		SetTouchable(R.id.button0);
		SetTouchable(R.id.button1);
		SetTouchable(R.id.button2);
		SetTouchable(R.id.button3);
		SetTouchable(R.id.button4);
		SetTouchable(R.id.button5);
		SetTouchable(R.id.button6);
		SetTouchable(R.id.button7);
		SetTouchable(R.id.button8);
		SetTouchable(R.id.button9);
		SetTouchable(R.id.buttona);
		SetTouchable(R.id.buttonb);
		SetTouchable(R.id.buttonc);
		SetTouchable(R.id.buttond);
		SetTouchable(R.id.buttone);
		SetTouchable(R.id.buttonf);
		
		final ImageButton buttonLeft = (ImageButton)findViewById(R.id.buttonleft);
		buttonLeft.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (CurrentPointer > 0) CurrentPointer--;
				TextView textView = (TextView)findViewById(R.id.address);
				char[] addr = textView.getText().toString().toCharArray();
				if ( addr[CurrentPointer] == (":".charAt(0))) CurrentPointer--;
				setActiveCurrentPosition(addr[CurrentPointer]);
			}
			
		});
		final ImageButton buttonRight = (ImageButton)findViewById(R.id.buttonright);
		buttonRight.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {				
				if (CurrentPointer < 16) CurrentPointer++;
				TextView textView = (TextView)findViewById(R.id.address);
				char[] addr = textView.getText().toString().toCharArray();
				if ( addr[CurrentPointer] == (":".charAt(0))) CurrentPointer++;
				setActiveCurrentPosition(addr[CurrentPointer]);
			}
			
		});
		
		Button searchButton = (Button)findViewById(R.id.searchbutton);
		searchButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TextView textView = (TextView)findViewById(R.id.address);
				Intent intent = new Intent(v.getContext(), Result.class);		
				intent.putExtra("MAC", textView.getText().toString());
				startActivity(intent);

			}
			
		});
		if (savedInstanceState != null){
			this.CurrentPointer = savedInstanceState.getInt("CurrentPointer", 0);
			TextView textView = (TextView)findViewById(R.id.address);
			textView.setText(savedInstanceState.getString("Value"));
			setActiveCurrentPosition(textView.getText().charAt(this.CurrentPointer));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mac_vendor_search, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.aboutmenu:
            	onAboutMenuClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }	

    public void onAboutMenuClick(){   	
    	final Dialog dialog = new Dialog(this);
    	dialog.setContentView(R.layout.about);
    	dialog.setTitle(getString(R.string.about));
    	    	    	
    	Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);    	
    	
    	dialogButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
				dialog.dismiss();
				}
			});
    	dialog.show();    	

    	TextView EmailLink = (TextView)dialog.findViewById(R.id.emailtext);
    	
    	if ( EmailLink == null ) {Log.d("Null: EmailLink", "Why");} 
    	else Linkify.addLinks(EmailLink, Linkify.EMAIL_ADDRESSES);    	
    	
    }

    
	private void SetTouchable(int ID){
		final ImageButton imageButton = (ImageButton) findViewById(ID);
		imageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TextView textView = (TextView)findViewById(R.id.address);
				char[] addr = textView.getText().toString().toCharArray();
				if ( addr[CurrentPointer] == (":".charAt(0))) CurrentPointer++;
				setActiveCurrentPosition( imageButton.getTag().toString().charAt(0) );
				if (CurrentPointer<16) {
					CurrentPointer++;
				}
				if ( addr[CurrentPointer] == (":".charAt(0))) CurrentPointer++;
				setActiveCurrentPosition( textView.getText().toString().charAt(CurrentPointer) );
			}
			
		});
	}
	private void setActiveCurrentPosition(char CharValue){
		TextView textView = (TextView)findViewById(R.id.address);
		String addr = textView.getText().toString();
		char[] addrArray = addr.toCharArray();				
		char[] newaddrArray = new char[addr.length()+15];
		char[] currentValue = ("<u><b>"+CharValue+"</u></b>").trim().toCharArray();
		
		//if ( addrArray[CurrentPointer] == (":".charAt(0))) CurrentPointer++;

		for (int i=0; i< CurrentPointer; i++) {
			newaddrArray[i] = addrArray[i];
		}
		for (int j=0;j<currentValue.length;j++){
			newaddrArray[CurrentPointer+j]=currentValue[j];
		}
		for (int i=CurrentPointer+1; i<addrArray.length; i++){
			newaddrArray[i+14]=addrArray[i];
		}
		
		textView.setText( Html.fromHtml( String.copyValueOf(newaddrArray) ) );
	}

	protected void onSaveInstanceState(Bundle bundle) {
		  super.onSaveInstanceState(bundle);
		  bundle.putString("Value", ((TextView)findViewById(R.id.address)).getText().toString());
		  bundle.putInt("CurrentPointer", CurrentPointer);
		}
	
}

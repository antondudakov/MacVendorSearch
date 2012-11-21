package antondudakov.macvendorsearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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
		
		Button buttonLeft = (Button)findViewById(R.id.buttonleft);
		buttonLeft.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (CurrentPointer > 0) CurrentPointer--;
			}
			
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_mac_vendor_search, menu);
//		return true;
//	}

	private void SetTouchable(int ID){
		final ImageButton imageButton = (ImageButton) findViewById(ID);
		imageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TextView textView = (TextView)findViewById(R.id.address);
				String addr = textView.getText().toString();
				char[] addrArray = addr.toCharArray();
				if ( addrArray[CurrentPointer] == (":".charAt(0))) CurrentPointer++;
				addrArray[CurrentPointer] = imageButton.getTag().toString().toCharArray()[0];
				textView.setText("<u>" + String.copyValueOf(addrArray) + "</u>");
				if (CurrentPointer<16) {
					CurrentPointer++;
				}
			}
			
		});
	}
	private void setActiveCurrentPosition(){
		TextView textView = (TextView)findViewById(R.id.address);
		String addr = textView.getText().toString();
		addr.replaceAll("<.?u>", null);
	}
}

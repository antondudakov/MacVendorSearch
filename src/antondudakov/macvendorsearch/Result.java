package antondudakov.macvendorsearch;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class Result extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Intent intent = getIntent();
        String Mac = intent.getStringExtra("MAC");
        
        Handler ResultHandler;
        final TextView textView = (TextView)findViewById(R.id.result);
        ResultHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				String text = (String) msg.obj;
				textView.setText( (String)msg.obj);
			}
        };


        (new SetResult(ResultHandler)).execute(Mac);
        

	}

	public class SetResult extends AsyncTask<String, Void, Void> {
		private final Handler handler;
		
		public SetResult(Handler handler){
			this.handler = handler;
		}
		
		@Override
		protected Void doInBackground(String... macs) { 
			// TODO Auto-generated method stub
			try{
				Log.d("Дебаж", getString(R.string.urlstring) +  macs[0]);
				String jsonResult = GetUrl.result( getString(R.string.urlstring) +  macs[0]);
				Log.d("Дебаж", jsonResult);
				String result = "no information";
				if (jsonResult.trim().compareToIgnoreCase("none".trim()) != 0 ){
					JSONObject json = new JSONArray(jsonResult).getJSONObject(0);
					result = "company: " + json.getString("company") + "\n" +
								"department: " + json.getString("department") + "\n" +
								"address1: " + json.getString("address1") + "\n" +
								"address2: " + json.getString("address2") + "\n" +
								"country: " + json.getString("country") + "\n";
				}
				
				Message msg = new Message();
				msg.obj = result;
				handler.sendMessage(msg);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

	}	
	
}

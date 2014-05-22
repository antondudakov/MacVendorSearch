package antondudakov.macvendorsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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

    TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Intent intent = getIntent();
        String Mac = intent.getStringExtra("MAC");
        
        textView = (TextView)findViewById(R.id.result);

        (new SetResult()).execute(Mac);

	}

	public class SetResult extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }

        @Override
		protected String doInBackground(String... macs) {
            String result = "no information";
			try{
//				Log.d("�����", getString(R.string.urlstring) +  macs[0]);
				String jsonResult = GetUrl.result( getString(R.string.urlstring) +  macs[0]);
//				Log.d("�����", jsonResult);
				if (jsonResult.trim().compareToIgnoreCase("none".trim()) != 0 ){
					JSONObject json = new JSONArray(jsonResult).getJSONObject(0);

                    Iterator<String> keys = json.keys();
                    ArrayList<String> keysArray = new ArrayList<String>();
                    while (keys.hasNext()) {
                        keysArray.add((String) keys.next());
                    }

                    Collections.sort(keysArray, new Comparator<String>() {
                        @Override
                        public int compare(String lhs, String rhs) {
                            return lhs.compareToIgnoreCase(rhs);
                        }
                    });
                    result = "";
                    for (String key:keysArray) {
                        result = result + key + ": " + json.getString(key) + "\n";
                    }

//					result = "company: " + json.getString("company") + "\n" +
//								"department: " + json.getString("department") + "\n" +
//								"address1: " + json.getString("address1") + "\n" +
//								"address2: " + json.getString("address2") + "\n" +
//								"country: " + json.getString("country") + "\n";
				}
				

			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}

	}	
	
}

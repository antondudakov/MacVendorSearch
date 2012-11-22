package antondudakov.macvendorsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUrl {
	public static String result(String urlString)  throws IOException{
		String resultString = "";
		
		try {
			  URL url = new URL(urlString);
			  HttpURLConnection con = (HttpURLConnection) url
			    .openConnection();
			  resultString = readStream(con.getInputStream());
			  } catch (Exception e) {
			  e.printStackTrace();
			}
		
		return resultString;
	}
	
	private static String readStream(InputStream in) {
		  BufferedReader reader = null;
		  String output = "";
		  try {
		    reader = new BufferedReader(new InputStreamReader(in));
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		      output = output + line + "\n";
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    if (reader != null) {
		      try {
		        reader.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		        }
		    }
		  }
		  return output;
		} 


}

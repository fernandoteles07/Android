package com.zup.getmovieinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;

public class GetJsonTask extends AsyncTask<String, String, String> {
	
	TextView mText;
	String getJson;
	
	public GetJsonTask(TextView mText) {
		this.mText = mText;
	}

	@Override
	protected String doInBackground(String... params) {
		
		HttpURLConnection connection = null;
		BufferedReader bReader = null;

		try {
			URL url = new URL (params[0]);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			InputStream inStream = connection.getInputStream();
			bReader = new BufferedReader(new InputStreamReader(inStream));
			StringBuffer sBuffer = new StringBuffer();
			String line = "";

			while ((line = bReader.readLine()) != null ) {
				sBuffer.append(line);
			}
		
			return sBuffer.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			try {
				if (bReader != null) {
					bReader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		try {
			mText.setText(parseJson("Title", result));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String parseJson (String detail, String getJson) throws JSONException {
		
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(getJson);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj.getString(detail);

	}
}

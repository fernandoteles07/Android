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

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GetJsonTask extends AsyncTask<String, String, String> {
	
	Context context;
	TextView mText;
	ImageView mImg;
	String getJson;
	
	public GetJsonTask(Context context, TextView mText, ImageView mImg) {
		this.context = context;
		this.mText = mText;
		this.mImg = mImg;
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
			
			String b =  sBuffer.toString();
		
			return b;

		

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
			Picasso.with(context).load(parseJson("Poster", result)).into(mImg);
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

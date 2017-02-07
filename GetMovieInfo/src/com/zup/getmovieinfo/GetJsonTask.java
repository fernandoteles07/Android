package com.zup.getmovieinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

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

		// Perform URL request
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

			String requestResponse =  sBuffer.toString();

			return requestResponse;

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

		JsonParser jp = new JsonParser();

		// Set movie title and movie poster in Main Activity
		try {
			mText.setText(jp.parseJson("Title", result));
			// External Lib for set online image easier
			Picasso.with(context).load(jp.parseJson("Poster",result)).into(mImg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

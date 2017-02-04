package com.zup.getmovieinfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailActivity extends Activity {

	TextView dTitle;
	TextView dPlot;
	TextView dYear;
	TextView dGenre;
	TextView dTime;
	TextView dDirector;
	ImageView dPoster;
	String getName;
	Button btBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);

		dTitle = (TextView) findViewById(R.id.tvTitle);
		dPlot = (TextView) findViewById(R.id.tvPlot);
		dYear = (TextView) findViewById(R.id.tvYear);
		dGenre = (TextView) findViewById(R.id.tvGenre);
		dTime = (TextView) findViewById(R.id.tvTime);
		dDirector = (TextView) findViewById(R.id.tvDirector);
		dPoster = (ImageView) findViewById(R.id.ivPoster);
		btBack = (Button) findViewById(R.id.btBack);

		Intent intent = getIntent();
		Bundle bd = intent.getExtras();       
		if(bd != null)
		{
			getName = (String) bd.get("pjFile");            
		}
		try {
			dTitle.setText(parseJson("Title"));
			dPlot.setText(parseJson("Plot"));
			dYear.setText(parseJson("Year"));
			dGenre.setText(parseJson("Genre"));
			dTime.setText(parseJson("Runtime"));
			dDirector.setText(parseJson("Director"));
			Picasso.with(this).load(parseJson("Poster")).into(dPoster);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
	}

	public String parseJson (String detail) throws JSONException {

		JSONObject jObj = null;
		try {
			jObj = new JSONObject(getName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj.getString(detail);

	}
}

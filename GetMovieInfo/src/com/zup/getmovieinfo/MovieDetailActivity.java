package com.zup.getmovieinfo;

import org.json.JSONException;

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

	private final String JSON_KEY = "jKey";
	String getJson = "";

	TextView dTitle;
	TextView dPlot;
	TextView dYear;
	TextView dGenre;
	TextView dTime;
	TextView dDirector;
	TextView dActors;
	TextView dCountry;
	TextView dLanguage;
	ImageView dPoster;
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
		dActors = (TextView) findViewById(R.id.tvActors);
		dCountry = (TextView) findViewById(R.id.tvCountry);
		dLanguage = (TextView) findViewById(R.id.tvLanguage);
		dPoster = (ImageView) findViewById(R.id.ivPoster);
		btBack = (Button) findViewById(R.id.btDBack);

		Intent intent = getIntent();
		Bundle bd = intent.getExtras();       

		if(bd != null) {
			getJson = (String) bd.get(JSON_KEY);            
		}

		JsonParser jp = new JsonParser();

		try {

			// Set activity name
			this.setTitle("Movie: " + jp.parseJson("Title",getJson));

			// Set Views
			dTitle.setText(jp.parseJson("Title",getJson));
			dPlot.setText(jp.parseJson("Plot",getJson));
			dYear.setText(jp.parseJson("Year",getJson));
			dGenre.setText(jp.parseJson("Genre",getJson));
			dTime.setText(jp.parseJson("Runtime",getJson));
			dDirector.setText(jp.parseJson("Director",getJson));
			dActors.setText(jp.parseJson("Actors",getJson));
			dCountry.setText(jp.parseJson("Country",getJson));
			dLanguage.setText(jp.parseJson("Language",getJson));
			// External Lib for set online image easier
			Picasso.with(this).load(jp.parseJson("Poster",getJson)).into(dPoster);

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
}

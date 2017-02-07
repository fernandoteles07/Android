package com.zup.getmovieinfo;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String JSON_KEY = "jKey";
	private final String SHARED_GLOBAL_KEY = "globalKey";
	private final String MAIN_URL = "http://www.omdbapi.com/?t=";
	private final String FILTER_URL = "&y=&plot=short&r=json";
	private String jFile = "";

	Context context = this;
	EditText mEdit;
	Button mSearchButton;
	Button mListButton;
	TextView mText;
	ImageView mImg;
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mEdit = (EditText) findViewById(R.id.etSearch);
		mSearchButton = (Button) findViewById(R.id.btOk);
		mListButton = (Button) findViewById(R.id.btList);
		mText = (TextView) findViewById(R.id.tvShowTitle);
		mImg = (ImageView) findViewById(R.id.imPoster);



		// Click on OK button for database request
		mSearchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Hide Keyboard 
				hideKeyboard();

				// Get user input
				String getEdit = mEdit.getText().toString();

				// Check if user typed some space
				if (getEdit.contains(" ")) {
					getEdit = mEdit.getText().toString().replace(" ", "+");
				} 

				// Get AsyncTask return 
				try {
					jFile = new GetJsonTask(context, mText, mImg).execute(MAIN_URL + getEdit + FILTER_URL).get();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});

		// If user click on movie image go to MovieDetailActivity
		mImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent iMovieDetail = new Intent(context, MovieDetailActivity.class);
				iMovieDetail.putExtra(JSON_KEY, jFile);
				startActivity(iMovieDetail);
			}
		});

		// If user long click on movie image add movie to List
		mImg.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				try {
					putJsontoList(context, jFile);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true; 
			}
		});

		// Go to MyListActivity
		mListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent iMyList = new Intent(context, MyListActivity.class);
				startActivity(iMyList);
			}
		});
	}

	// Save JsonObject in SharedPreferences and put it on My List
	public void putJsontoList(Context context, String jsonObject) throws JSONException {

		SharedPreferences pref;
		Editor editor;
		pref = context.getSharedPreferences(SHARED_GLOBAL_KEY, Context.MODE_PRIVATE);
		editor = pref.edit();

		JsonParser jp = new JsonParser();
		String sharedKey = jp.parseJson("Title", jsonObject);

		Toast.makeText(MainActivity.this, sharedKey +" was added to your list" ,Toast.LENGTH_SHORT).show();

		editor.putString(sharedKey, jsonObject);
		editor.commit();
	}

	public void hideKeyboard() {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}
}

package com.zup.getmovieinfo;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText mEdit;
	Button mButton;
	TextView mText;
	ImageView mImg;
	String jFile = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mEdit = (EditText) findViewById(R.id.etSearch);
		mButton = (Button) findViewById(R.id.btOk);
		mText = (TextView) findViewById(R.id.tvShowTitle);
		mImg = (ImageView) findViewById(R.id.imPoster);
		
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String getEdit = mEdit.getText().toString();
				
				if (getEdit.contains(" ")) {
					getEdit = mEdit.getText().toString().replace(" ", "+");
				} 
				
				try {
					jFile = new GetJsonTask(MainActivity.this,mText,mImg).execute("https://api.myjson.com/bins/rz1c1").get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		mImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(MainActivity.this, MovieDetailActivity.class);
				i.putExtra("pjFile", jFile);
				startActivity(i);
			}
		});
		
		mImg.setOnLongClickListener(new OnLongClickListener() {
		    public boolean onLongClick(View arg0) {
		        Toast.makeText(getApplicationContext(), "The movie was added to your List :)",Toast.LENGTH_SHORT).show();

		        return true;    // <- set to true
		    }
		});
	}
}

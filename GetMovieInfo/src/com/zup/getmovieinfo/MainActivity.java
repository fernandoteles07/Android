package com.zup.getmovieinfo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	EditText mEdit;
	Button mButton;
	TextView mText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mEdit = (EditText) findViewById(R.id.etSearch);
		mButton = (Button) findViewById(R.id.btOk);
		mText = (TextView) findViewById(R.id.tvShowTitle);
		
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String getEdit = mEdit.getText().toString();
				
				if (getEdit.contains(" ")) {
					getEdit = mEdit.getText().toString().replace(" ", "+");
				} 
				
				new GetJsonTask(mText).execute("https://api.myjson.com/bins/tseq9");
			}
		});
	}
}

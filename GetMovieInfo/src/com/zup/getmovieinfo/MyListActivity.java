package com.zup.getmovieinfo;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MyListActivity extends Activity {

	private final String SHARED_GLOBAL_KEY = "globalKey";
	private final String JSON_KEY = "jKey";

	SharedPreferences pref;
	ArrayList<String> arr = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	Button btBack;
	Button btClearAll;
	ListView lv;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_list);

		lv = (ListView) findViewById(R.id.lv);
		btBack = (Button) findViewById(R.id.btLBack);
		btClearAll = (Button) findViewById(R.id.btClearAll);


		// Get all app shared preferences
		pref = this.getSharedPreferences(SHARED_GLOBAL_KEY, Context.MODE_PRIVATE);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		lv.setAdapter(adapter);

		loadPreferences();

		// If user click on movie title item go to MovieDetailActivity
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent i = new Intent(context, MovieDetailActivity.class);
				i.putExtra(JSON_KEY, arr.get(position));
				startActivity(i);
			}
		});

		// If user long click on movie title item clear it from List
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(R.string.alert_dialog_title);
				builder.setMessage(R.string.alert_dialog_clear_item);
				builder.setPositiveButton(R.string.yes_option,
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Remove deleted item from app shared preferences 
						pref.edit().remove(parent.getAdapter().getItem(position).toString()).commit();
						// Remove deleted item from adapter
						adapter.remove(parent.getAdapter().getItem(position).toString());
						adapter.notifyDataSetChanged();
						Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
					}
				});

				builder.setNegativeButton(R.string.no_option, null);
				builder.show();
				return true;
			}

		});

		btBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// Clear all List
		btClearAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(R.string.alert_dialog_title);
				builder.setMessage(R.string.alert_dialog_clear_all);
				builder.setPositiveButton(R.string.yes_option,
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						pref.edit().clear().commit();
						adapter.clear();
						adapter.notifyDataSetChanged();
						Toast.makeText(context, "All items was deleted", Toast.LENGTH_SHORT).show();
					}
				});

				builder.setNegativeButton(R.string.no_option, null);
				builder.show();
				
			}
		});
	}

	// Load all app shared preferences and put them into array
	public void loadPreferences() {

		Map<String,?> keys = pref.getAll();

		for (Map.Entry<String,?> entry : keys.entrySet()) {
			String dataSet = pref.getString(entry.getKey(), null);
			arr.add(dataSet);
			adapter.add(entry.getKey());
			adapter.notifyDataSetChanged();
		}
	} 
}


package com.example.birdnote;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.example.birdnote.db.BirdsDataSource;
import com.example.birdnote.list.CustomBaseAdapter;
import com.example.birdnote.model.Bird;
import com.example.birdnote.xml.BirdsPullParser;

public class ReferenceGuide extends ListActivity {

	private List<Bird> birds;
	boolean isBirdsSeen;
	
	// create reference to database
	BirdsDataSource datasource;

	public final static String LOGTAG = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reference_guide);

		// open connection to db
		datasource = new BirdsDataSource(this);
		datasource.open();
		
		// get list of birds from db
		birds = datasource.findAll();
		if (birds.size() == 0) {
			createData();
			birds = datasource.findAll();
		}
		
		isBirdsSeen = false;
		
		ArrayAdapter<Bird> adapter = new CustomBaseAdapter(this, birds);
		setListAdapter(adapter);
	}

	protected void onResume() {
		super.onResume();
		datasource.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}

	private void createData() {

		BirdsPullParser parser = new BirdsPullParser();
		List<Bird> birds = parser.parseXML(this);
		
		for (Bird bird : birds) {
			datasource.create(bird);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Bird bird = birds.get(position);

		Intent intent = new Intent(this, Profile.class);
		intent.putExtra("com.example.birdnote.model.Bird", bird);
		intent.putExtra("isBirdsSeen", isBirdsSeen);
		
		startActivity(intent);
	}
}
package com.example.birdnote;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.example.birdnote.db.BirdsDataSource;
import com.example.birdnote.list.CustomBaseAdapter;
import com.example.birdnote.model.Bird;

public class ReferenceGuide extends ListActivity {

	private List<Bird> birds;
	
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
        List<Bird> birds = datasource.findAll();  
        if(birds.size() == 0) {
        	try {
				createData();
			} catch (JSONException e) {
				e.printStackTrace();
			}
        	birds = datasource.findAll();
        }
        
        // set view adapter
        ArrayAdapter<Bird> adapter = new CustomBaseAdapter(this, birds);
        setListAdapter(adapter);
    }
    
    protected void onResume(){
    	super.onResume();
    	datasource.open();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	datasource.close();
    }
    
    private void createData() throws JSONException {
    	
    	/*Bird bird = new Bird();
    	bird.setName("Arctic Tern");
    	bird.setLatinName("Sterna paradisaea");
    	bird.setDescription("Usually seen over the sea. Slender seabird with narrow, pointed wings, long forked tail and long, pointed bill. Grey above and white below, dark cap to head. Flight light and buoyant, can hover briefly over the sea before diving in. Very similar to Common Tern (with which it breeds) and told apart by plumage and structure. Arctic Tern is smaller, with a smaller head, neck and bill and slightly narrower wings, which look forwardly placed on the body. Very short legs. Adults have a blood red bill, usually with no dark tip. The underparts are greyer than Common Tern and there some contrast with the cheek. The wing pattern is useful in separation, Arctic terns shows no dark wedge in the primaries but shows a distinct trailing edge. Arctic terns have longer tail steamers, extending beyond the wing tips. Adult winter plumage, like all terns is different from breeding plumage, but is only seen in the wintering range. Also has distinctive juvenile plumage, with some brown in the mantle, a dark carpel bar and white secondaries. Shows a distinct trailing bar to the primaries, bill darkens rapidly.");
    	bird.setHabitat("Summer visitor from March to September to all Irish coasts. Winters off south Africa and as far south as Antarctica.");
    	bird.setDiet("Marine fish, crustaceans and insects.");
    	bird.setBreeding("Mainly a coastal breeding bird, but in Ireland the species also breeds inland on the fresh water lakes of Lough Corrib (Co. Galway) and Lough Conn (Co. Mayo). More colonies are found on the west coast with Co. Wexford, Co. Kerry, Co. Mayo and Co. Donegal having the largest number of birds. ', 'Considered to have the longest migration of all birds, utilizing the summer of both hemispheres.");
    	bird.setWinteringHabits("Considered to have the longest migration of all birds, utilizing the summer of both hemispheres.");
    	bird.setWhereToSee("Lady''s Island Lake, near Rosslare, in County Wexford has up to 300 pairs. As well as other tern species.");
    	bird.setConservation("Amber-listed in Ireland due to its localised breeding population. The European population is regarded as Secure.");
    	bird.setPrimaryColour(9);
    	bird.setSecondaryColour(4);
    	bird.setCrownColour(1);
    	bird.setBillLength(2);
    	bird.setBillColour(5);
    	bird.setTailShape(3);
    	bird.setCreatedAt("now");
    	bird.setUpdatedAt("now");
    	
    	bird = datasource.create(bird);
    	Log.i(LOGTAG, "Bird created with id " + bird.getId() );*/
    }
    
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Bird bird = birds.get(position);
		
		Intent intent = new Intent(this, Profile.class);
		intent.putExtra(".model.Bird", bird);
		startActivity(intent);
	}
    
    
    
    // attempt to add JSON below
    
/*public void readFile() throws IOException, JSONException {
		
		FileInputStream fis = openFileInput("birds");
		BufferedInputStream bis = new BufferedInputStream(fis);
		StringBuffer b = new StringBuffer();
		while (bis.available() != 0) {
			char c = (char) bis.read();
			b.append(c);
		}
		bis.close();
		fis.close();
		

    	JSONArray data = new JSONArray(b.toString());

    	StringBuffer birdsBuffer = new StringBuffer();
    	for (int i = 0; i < data.length(); i++) {
			String bird = data.getJSONObject(i).getString("name");
			birdsBuffer.append(bird + "\n");
		}
    	
		displayText(this, R.id.testtextView, birdsBuffer.toString());
	}

	public static void displayText(Activity activity, int id, String text) {
		TextView tv = (TextView) activity.findViewById(id);
		tv.setText(text);
	}*/
}
















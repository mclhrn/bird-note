package com.example.birdnote;


import com.example.birdnote.R;
import com.example.birdnote.model.Bird;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends Activity{
	
	Bird bird;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
    
		Bundle b = getIntent().getExtras();
		bird = b.getParcelable(".model.bird");
		
		refreshDisplay();
		
	}

	private void refreshDisplay() {
		
		TextView tv = (TextView) findViewById(R.id.name);
		tv.setText(bird.getName());
		
		tv = (TextView) findViewById(R.id.latin);
		tv.setText(bird.getLatinName());
		
		tv = (TextView) findViewById(R.id.description);
		tv.setText(bird.getDescription());
		
		/*ImageView iv = (ImageView) findViewById(R.id.main_profile_image);
        int imageResource = getResources().getIdentifier(
        		bird.getImage(), "drawable", getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }*/
		
	}
}

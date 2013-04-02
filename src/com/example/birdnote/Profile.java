package com.example.birdnote;

import com.example.birdnote.R;
import com.example.birdnote.db.BirdsDataSource;
import com.example.birdnote.model.Bird;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {

	Bird bird;
	BirdsDataSource datasource;
	boolean isBirdsSeen;
	boolean isWishlist;

	private static final String LOGTAG = "Birds";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
    
		Bundle b = getIntent().getExtras();
		bird = b.getParcelable("com.example.birdnote.model.Bird");
		
		isBirdsSeen = b.getBoolean("isBirdsSeen");
		isWishlist = b.getBoolean("isWishlist");
		
		refreshDisplay();
		
		datasource = new BirdsDataSource(this);
		datasource.open();
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

	private void refreshDisplay() {
		
		TextView tv = (TextView) findViewById(R.id.name);
		tv.setText(bird.getName());
		
		TextView tv2 = (TextView) findViewById(R.id.latin);
		tv2.setText(bird.getLatinName());
		
		TextView tv3 = (TextView) findViewById(R.id.status);
		tv3.setText(bird.getStatus());
		
		TextView tv4 = (TextView) findViewById(R.id.identification);
		tv4.setText(bird.getIdentification());
		
		TextView tv5 = (TextView) findViewById(R.id.diet);
		tv5.setText(bird.getDiet());
		
		TextView tv6 = (TextView) findViewById(R.id.breeding);
		tv6.setText(bird.getBreeding());
		
		TextView tv7 = (TextView) findViewById(R.id.wintering_habits);
		tv7.setText(bird.getWinteringHabits());
		
		TextView tv8 = (TextView) findViewById(R.id.where_to_see);
		tv8.setText(bird.getWhereToSee());
		
		TextView tv9 = (TextView) findViewById(R.id.conservation);
		tv9.setText(bird.getConservation());
		
		ImageView iv = (ImageView) findViewById(R.id.main_profile_image);
        int imageResource = getResources().getIdentifier(
        		bird.getImageLarge(), "drawable", getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }
	}
	
//	@Override
//	public void onLocationResultAvailable(Location location) {
//		lat = location.getLatitude();
//		lng = location.getLongitude();
//	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile_menu, menu);
		
		// Show delete menu item if we came from Birds Seen or Wishlist
		menu.findItem(R.id.delete).setVisible(isBirdsSeen || isWishlist);
		
		// Show add menu item if we didn't come from Birds Seen or Wishlist
		menu.findItem(R.id.add_to_seen).setVisible(!isBirdsSeen && !isWishlist);
		menu.findItem(R.id.add_to_wishlist).setVisible(!isBirdsSeen && !isWishlist);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_to_seen:
			System.out.println(MainActivity.LAT);
	    	System.out.println(MainActivity.LNG);
			if (datasource.addToBirdsSeen(bird, MainActivity.LAT, MainActivity.LNG)) {
				Log.i(LOGTAG, "Bird added");
				Toast toast = Toast.makeText(this, bird.getName()
						+ " added to Birds Seen List", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL
						| Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
			} else {
				Log.i(LOGTAG, "Bird not added");
			}
			break;
		case R.id.add_to_wishlist:
			if (datasource.addToWishList(bird)) {
				Log.i(LOGTAG, "Bird added");
				Toast toast = Toast.makeText(this, bird.getName()
						+ " added to Wishlist", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL
						| Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
			} else {
				Log.i(LOGTAG, "Bird not added");
			}
			break;
		case R.id.delete:
			if (isBirdsSeen) {
				if (datasource.removeFromBirdsSeen(bird)) {
					setResult(-1);
					Toast toast = Toast.makeText(this, bird.getName()
							+ " removed from List", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_VERTICAL
							| Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					finish();
				}
			} else if (isWishlist) {
				if (datasource.removeFromWishList(bird)) {
					setResult(-1);
					Toast toast = Toast.makeText(this, bird.getName()
							+ " removed from List", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_VERTICAL
							| Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					finish();
				}
			}
			break;			
		}	
		return super.onOptionsItemSelected(item);
	}
}

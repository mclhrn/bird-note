package com.example.birdnote;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.birdnote.db.BirdsDataSource;
import com.example.birdnote.model.Bird;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
	String url;
	
	private static final String LOGTAG = "Birds";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		Bundle b = getIntent().getExtras();
		bird = b.getParcelable("com.example.birdnote.model.Bird");

		isBirdsSeen = b.getBoolean("isBirdsSeen");
		isWishlist = b.getBoolean("isWishlist");

		datasource = new BirdsDataSource(this);
		datasource.open();
		
		url = "www.birdnote.heroku.com/";

		refreshDisplay();
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
		int imageResource = getResources().getIdentifier(bird.getImageLarge(),
				"drawable", getPackageName());
		if (imageResource != 0) {
			iv.setImageResource(imageResource);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile_menu, menu);

		// Show delete menu item if we came from Birds Seen or Wishlist
		menu.findItem(R.id.delete).setVisible(isBirdsSeen || isWishlist);

		// Show add menu item if we came from main reference guide
		menu.findItem(R.id.add_to_seen).setVisible(!isBirdsSeen && !isWishlist);
		menu.findItem(R.id.add_to_wishlist).setVisible(
				!isBirdsSeen && !isWishlist);
		menu.findItem(R.id.share_sighting).setVisible(
				!isBirdsSeen && !isWishlist);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_to_seen:
			System.out.println(MainActivity.LAT);
			System.out.println(MainActivity.LNG);
			if (datasource.addToBirdsSeen(bird, MainActivity.LAT,
					MainActivity.LNG)) {
				Log.i(LOGTAG, "Bird added");
				Toast toast = Toast.makeText(this, bird.getName()
						+ " added to Birds Seen List", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL
						| Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
				// HTTP Post list item
				new UpdateRemoteList().execute();
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
		case R.id.share_sighting:
			shareSighting();
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

					// async http delete here

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

	private void shareSighting() {
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		String shareBody = "I just saw a " + bird.getName()
				+ " using BirdNote. ";
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"BirdNote Sighting");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	
	// async inner class here
	private class UpdateRemoteList extends AsyncTask<String, Void, Void> {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		private ProgressDialog Dialog = new ProgressDialog(Profile.this);
		
		protected void onPreExecute() {
			Dialog.setMessage("Sending value..");
			Dialog.show();

			nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString((MainActivity.LNG))));
			nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString((MainActivity.LAT))));
			nameValuePairs.add(new BasicNameValuePair("bird_id", Integer.toString((int) bird.getId())));
		}

		protected Void doInBackground(String...urls) {
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httpclient.execute(httppost);
			}
			catch(Exception e){
				Log.e("log_tag", "Error in http connection " + e.toString());
			}
		return null;
		}

		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			Toast.makeText(getApplicationContext(), "Value updated", Toast.LENGTH_SHORT).show();
		}
	}
}

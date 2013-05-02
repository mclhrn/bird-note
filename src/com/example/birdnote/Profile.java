package com.example.birdnote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.birdnote.connections.ConnectionDetector;
import com.example.birdnote.db.BirdsDataSource;
import com.example.birdnote.model.Bird;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	
	// flag for Internet connection status
    Boolean isInternetPresent = false;
     
    // Connection detector class
    ConnectionDetector cd;
	
    String myurl = 	"http://birdnote.herokuapp.com/sightings";

	private static final String LOGTAG = "Birds";

	@SuppressLint("UseValueOf")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		Bundle b = getIntent().getExtras();
		bird = b.getParcelable("com.example.birdnote.model.Bird");

		isBirdsSeen = b.getBoolean("isBirdsSeen");
		isWishlist = b.getBoolean("isWishlist");

		datasource = new BirdsDataSource(this);
		datasource.open();

		cd = new ConnectionDetector(getApplicationContext());
		
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


//				 // get Internet status
//                isInternetPresent = cd.isConnectingToInternet();
// 
//                // check for Internet status
//                if (isInternetPresent) {
//                    // Internet Connection is Present
//                    // make HTTP requests
//                	
//                } else {
//                    // Internet connection is not present
//                    // Ask user to connect to Internet
//                    showAlertDialog(Profile.this, "No Internet Connection",
//                            "You don't have internet connection.", false);
//                }
				
				
				
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
	
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
         
        //Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }

	// async inner class here
	private class UpdateRemoteList extends AsyncTask<String, Void, Void> {

		//private ProgressDialog Dialog = new ProgressDialog(Profile.this);

		protected void onPreExecute() {
			Log.i(LOGTAG, "Starting Background Task!!!!!!!!!!!!!!!!");

		}

		protected Void doInBackground(String...urls) {
			try {
		        
				URL url = new URL(myurl);
				
				JSONObject json = new JSONObject();
		        json.put("longitude", new Double(MainActivity.LNG));
		        json.put("latitude", new Double(MainActivity.LAT));
		        json.put("user_id", 1);
		        json.put("bird_id", new Integer((int) bird.getId()));
		        
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpParams myParams = new BasicHttpParams();
		        HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		        HttpConnectionParams.setSoTimeout(myParams, 10000);
		        
		        try {
		        	HttpPost httppost = new HttpPost(url.toString());
		            httppost.setHeader("Accept", "application/json");		            
		            httppost.setHeader("Content-type", "application/json");

		            StringEntity se = new StringEntity(json.toString()); 
		            httppost.setEntity(se); 

		            HttpResponse response = httpclient.execute(httppost);
		            String temp = EntityUtils.toString(response.getEntity());
		            Log.i("tag", temp);
		        } 
		        catch (ClientProtocolException e) {

		        } 
		        catch (IOException e) {
		        
		        }
		    } 
			catch (JSONException e) {
				e.printStackTrace();
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			}
			finally {
				
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			Log.i(LOGTAG, "Leaving Background Task!!!!!!!!!!!!!!!!");
		}
	}
}

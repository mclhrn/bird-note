package com.example.birdnote.login;

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

import com.example.birdnote.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	private final static String REGISTER_ENDPOINT_URL = "http://birdnote.heroku.com/users";
	private SharedPreferences mPreferences;
	private String mRealName;
	private String mUserEmail;
	private String mUserName;
	private String mUserPassword;
	private String mUserPasswordConfirmation;
	String myurl = "http://birdnote.herokuapp.com/users";
	Boolean value;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
	}

	public void registerNewAccount(View button) {
		EditText realNameField = (EditText) findViewById(R.id.realName);
		mRealName = realNameField.getText().toString();
		EditText userNameField = (EditText) findViewById(R.id.userName);
		mUserName = userNameField.getText().toString();
		EditText userEmailField = (EditText) findViewById(R.id.userEmail);
		mUserEmail = userEmailField.getText().toString();
		EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
		mUserPassword = userPasswordField.getText().toString();
		EditText userPasswordConfirmationField = (EditText) findViewById(R.id.userPasswordConfirmation);
		mUserPasswordConfirmation = userPasswordConfirmationField.getText()
				.toString();

		if (mRealName.length() == 0 || mUserEmail.length() == 0
				|| mUserName.length() == 0 || mUserPassword.length() == 0
				|| mUserPasswordConfirmation.length() == 0) {
			// input fields are empty
			Toast.makeText(this, "Please complete all the fields",
					Toast.LENGTH_LONG).show();
			return;
		} else {
			if (!mUserPassword.equals(mUserPasswordConfirmation)) {
				// password doesn't match confirmation
				Toast.makeText(
						this,
						"Your password doesn't match confirmation, check again",
						Toast.LENGTH_LONG).show();
				return;
			} else {
				// everything is ok!
				RegisterTask registerTask = new RegisterTask();
				// registerTask.setMessageLoading("Registering new account...");
				registerTask.execute();
			}
		}
	}

	// async inner class here
	private class RegisterTask extends AsyncTask<String, Void, Boolean> {

		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
		    super.onPreExecute();

		     dialog = ProgressDialog.show(Register.this, "", 
		            "Creating account...", true);
		     dialog.show();
		}

		@Override
		protected Boolean doInBackground(String... urls) {
			try {

				URL url = new URL(myurl);

				JSONObject json = new JSONObject();
				json.put("name", mRealName);
				json.put("email", mUserEmail);
				json.put("nickname", mUserName);
				json.put("password", mUserPassword);
				json.put("password_confirmation", mUserPasswordConfirmation);

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
					value = true;
				} catch (ClientProtocolException e) {

				} catch (IOException e) {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} finally {

			}
			return value;
		}
		
		@Override
		protected void onPostExecute(Boolean value) {
			// launch the Login and close this one
			Toast.makeText(
					Register.this,
					"Account Created",
					Toast.LENGTH_LONG).show();
			if (value) {
				Intent intent = new Intent(getApplicationContext(),
						Login.class);
				startActivity(intent);
				finish();
			}
			else 
				showAlertDialog(Register.this, "No Internet Connection",
                      "You don't have internet connection.", false);
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
	}

	// private class RegisterTask extends AsyncTask<Void, Void, Object> {
	//
	// @SuppressWarnings("unused")
	// protected JSONObject doInBackground(Object... params) {
	// DefaultHttpClient client = new DefaultHttpClient();
	// HttpPost post = new HttpPost(REGISTER_ENDPOINT_URL);
	// JSONObject holder = new JSONObject();
	// JSONObject userObj = new JSONObject();
	// String response = null;
	// JSONObject json = new JSONObject();
	//
	// try {
	// try {
	// // setup the returned values in case
	// // something goes wrong
	// json.put("success", false);
	// json.put("info", "Something went wrong. Retry!");
	//
	// // add the users's info to the post params
	// userObj.put("name", mRealName);
	// userObj.put("email", mUserEmail);
	// userObj.put("nickname", mUserName);
	// userObj.put("password", mUserPassword);
	// userObj.put("password_confirmation", mUserPasswordConfirmation);
	// holder.put("user", userObj);
	// StringEntity se = new StringEntity(holder.toString());
	// post.setEntity(se);
	//
	// // setup the request headers
	// post.setHeader("Accept", "application/json");
	// post.setHeader("Content-Type", "application/json");
	//
	// ResponseHandler<String> responseHandler = new BasicResponseHandler();
	// response = client.execute(post, responseHandler);
	// json = new JSONObject(response);
	//
	// } catch (HttpResponseException e) {
	// e.printStackTrace();
	// Log.e("ClientProtocol", "" + e);
	// } catch (IOException e) {
	// e.printStackTrace();
	// Log.e("IO", "" + e);
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// Log.e("JSON", "" + e);
	// }
	//
	// return json;
	// }
	//
	// @SuppressWarnings("unused")
	// protected void onPostExecute(JSONObject json) {
	// try {
	// if (json.getBoolean("success")) {
	// // everything is ok
	// SharedPreferences.Editor editor = mPreferences.edit();
	// // save the returned auth_token into
	// // the SharedPreferences
	// editor.putString("AuthToken",
	// json.getJSONObject("data").getString("auth_token"));
	// editor.commit();
	//
	// // launch the HomeActivity and close this one
	// Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	// startActivity(intent);
	// finish();
	// }
	// Toast.makeText(Register.this, json.getString("info"),
	// Toast.LENGTH_LONG).show();
	// } catch (Exception e) {
	// // something went wrong: show a Toast
	// // with the exception message
	// Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
	// } finally {
	// super.onPostExecute(json);
	// }
	// }
	//
	// @Override
	// protected Object doInBackground(Void... params) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	// }
}

package com.example.birdnote.login;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.birdnote.MainActivity;
import com.example.birdnote.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private final static String LOGIN_ENDPOINT_URL = "http://birdnote.heroku.com/sessions";
	private SharedPreferences mPreferences;
	private String mUserEmail;
	private String mUserPassword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login);

	    mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
	}
	
	public void login(View button) {
	    EditText userEmailField = (EditText) findViewById(R.id.userEmail);
	    mUserEmail = userEmailField.getText().toString();
	    EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
	    mUserPassword = userPasswordField.getText().toString();

	    if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
	        // input fields are empty
	        Toast.makeText(this, "Please complete all the fields",
	            Toast.LENGTH_LONG).show();
	        return;
	    } else {
	        LoginTask loginTask = new LoginTask();
	        //loginTask.setMessageLoading("Logging in...");
	        loginTask.execute();
	    }
	}
	
	private class LoginTask extends AsyncTask<Void, Void, Object> {

		@SuppressWarnings("unused")
		protected JSONObject doInBackground(Object... params) {
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(LOGIN_ENDPOINT_URL.toString());
	        JSONObject holder = new JSONObject();
	        JSONObject userObj = new JSONObject();
	        String response = null;
	        JSONObject json = new JSONObject();

	        try {
	            try {
	                // setup the returned values in case
	                // something goes wrong
	                json.put("success", false);
	                json.put("info", "Something went wrong. Retry!");
	                // add the user email and password to
	                // the params
	                userObj.put("email", mUserEmail);
	                userObj.put("password", mUserPassword);
	                holder.put("user", userObj);
	                StringEntity se = new StringEntity(holder.toString());
	                post.setEntity(se);

	                // setup the request headers
	                post.setHeader("Accept", "application/json");
	                post.setHeader("Content-Type", "application/json");

	                ResponseHandler<String> responseHandler = new BasicResponseHandler();
	                response = client.execute(post, responseHandler);
	                json = new JSONObject(response);

	            } catch (HttpResponseException e) {
	                e.printStackTrace();
	                Log.e("ClientProtocol", "" + e);
	                json.put("info", "Email and/or password are invalid. Retry!");
	            } catch (IOException e) {
	                e.printStackTrace();
	                Log.e("IO", "" + e);
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	            Log.e("JSON", "" + e);
	        }

	        return json;
	    }
		
		@SuppressWarnings({ "unused" })
		protected void onPostExecute(JSONObject json) {
	        try {
	            if (json.getBoolean("success")) {
	                // everything is ok
	                SharedPreferences.Editor editor = mPreferences.edit();
	                // save the returned auth_token into
	                // the SharedPreferences
	                editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
	                editor.commit();

	                // launch the HomeActivity and close this one
	                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	                startActivity(intent);
	                finish();
	            }
	            Toast.makeText(Login.this, json.getString("info"), Toast.LENGTH_LONG).show();
	        } catch (Exception e) {
	            // something went wrong: show a Toast
	            // with the exception message
	            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
	        } finally {
	            super.onPostExecute(json);
	        }
	    }

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}

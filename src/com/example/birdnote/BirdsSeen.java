package com.example.birdnote;

import com.example.birdnote.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class BirdsSeen extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birds_seen);

		showToast();
	}

	private void showToast() {

		Toast toast = Toast.makeText(this,
				"You have not added any\nbirds to this list yet.",
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,
				0, 0);
		toast.show();
	}
}
package com.example.goodsamaritan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SamaritanProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_samaritan_profile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.samaritan_profile, menu);
		return true;
	}

}

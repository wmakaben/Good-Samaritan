/*
 * HomeActivity - the home page of the app
 */

package com.example.goodsamaritan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends Activity {
	
	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		sharedPref = getSharedPreferences(PREFS_NAME, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	protected void onResume(){
		checkLoginStatus();
		super.onResume();
	}
	
	/** Checks if the user is logged in or if it is the first time the app is accessed on a device */
	public void checkLoginStatus(){
		// TODO: Check shared preferences for the value of the "email" and "password" key. If empty then call login activity
		
		// Checks if this is the first time a device accesses the app
		if(sharedPref.getBoolean("my_first_time", true)){
			
			Intent loginIntent = new Intent(this, SignInActivity.class);
			//Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivity(loginIntent);
			sharedPref.edit().putBoolean("my_first_time", false).commit();
			finish();
		}
		
		
		
	}
	
	/**
	 * Starts the SettingsActivity
	 * @param view
	 */
	public void showSettings(View view){
		
		// This line is only for testing the login/registration activity. Remove it eventually
		sharedPref.edit().putBoolean("my_first_time", true).commit();
		
		// TODO: Create settings activity
	}
	
	/**
	 * Starts the ProfileActivity
	 * @param view
	 */
	public void showProfile(View view){
		Intent profileIntent = new Intent(this, SamaritanProfileActivity.class);
		startActivity(profileIntent);
	}
	
	/**
	 * Starts the HelpRequestActivity
	 * @param view
	 */
	public void requestHelp(View view){
		Intent intent = new Intent(this, RequestHelpActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Starts the HelperActivity
	 * @param view
	 */
	public void lendAHand(View view){
		// TODO: create helping activity process
	}
	
	/**
	 * Starts the PendingRequestActivity
	 * @param view
	 */
	public void showPendingRequests(View view){
		// TODO: Display the pending request activity
	}

}

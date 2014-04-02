package com.example.goodsamaritan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends Activity {
	
	final String PREFS_NAME = "MyPrefsFile";			// Name for shared preferences file
	
	private SharedPreferences sharedPreferences;		// Shared Preferences

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	protected void onResume(){
		super.onResume();
		checkLoginStatus();
	}
	
	/** Checks if the user is logged in or if it is the first time the app is accessed on a device */
	public void checkLoginStatus(){
		// TODO: Check if the user is not logged in
		
		// Checks if this is the first time a device accesses the app
		if(sharedPreferences.getBoolean("my_first_time", true)){
			// TODO: Create sign in activity for first time users
			
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivity(loginIntent);
			sharedPreferences.edit().putBoolean("my_first_time", false);
		}
	}
	
	/**
	 * Starts the SettingsActivity
	 * @param view
	 */
	public void showSettings(View view){
		
	}
	
	/**
	 * Starts the ProfileActivity
	 * @param view
	 */
	public void showProfile(View view){
		
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
		
	}
	
	/**
	 * Starts the PendingRequestActivity
	 * @param view
	 */
	public void showPendingRequests(View view){
		
	}

}

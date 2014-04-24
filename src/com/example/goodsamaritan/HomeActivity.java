/*
 * HomeActivity - the home page of the app
 */

package com.example.goodsamaritan;

import java.util.ArrayList;

import model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends Activity {

	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences

	// TODO: move location finding services to service
	private LocationManager lm;
	private MyLocationListener ll;
	public Location loc;
	public double lat;
	public double longi;
	private ArrayList<NameValuePair> params;
	private JSONParser jsonparser;
	private static String update_loc_url = "http://153.104.185.129:81/goodsamaritan/update_location.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		sharedPref = getSharedPreferences(PREFS_NAME, 0);	

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new MyLocationListener();
		jsonparser = new JSONParser();
		Log.i("Provider enabled? ", "" + lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
		if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){   //if provider is enabled
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 1, ll);
			Log.i("Getting geoloc", "yo");
			//new UpdateLocation().execute();
			//updateDatabaseLocation();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		// Handle presses on the action bar items
		switch(item.getItemId()){
		case R.id.action_settings:
			showSettings();
			return true;

		case R.id.action_profile:
			showProfile();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
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
		if(sharedPref.getBoolean("first_time", true)){
			Intent loginIntent = new Intent(this, SignInActivity.class);
			startActivity(loginIntent);
			sharedPref.edit().putBoolean("first_time", false).commit();
		}
		else if(sharedPref.getString("email", "").equals("") || sharedPref.getString("password", "").equals("")){
			Intent loginIntent = new Intent(this, SignInActivity.class);
			startActivity(loginIntent);
			sharedPref.edit().putBoolean("first_time", true).commit();
		} 
	}

	/**
	 * Starts the SettingsActivity
	 * @param view
	 */
	public void showSettings(){
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}

	/**
	 * Starts the ProfileActivity
	 * @param view
	 */
	public void showProfile(){
		//sharedPref.edit().putBoolean("first_time", true).commit();	// TODO: remove. only for testing purposes
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
		Intent intent = new Intent(this, RequestListActivity.class);
		startActivity(intent);
	}

	/**
	 * Starts the PendingRequestActivity
	 * @param view
	 */
	public void showPendingRequests(View view){
		Intent intent = new Intent(this, PendingRequestActivity.class);
		startActivity(intent);
	}

	public void updateDatabaseLocation(){
		params = new ArrayList<NameValuePair>();
		//Database automatically converts strings to doubles so we're good here
		params.add(new BasicNameValuePair("latitude", "" + lat));
		params.add(new BasicNameValuePair("longitude", "" + longi));
		params.add(new BasicNameValuePair("email", sharedPref.getString("email", " ")));
		Log.i("Location", ""+lat);

		new UpdateLocation().execute();
	}

	private class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			//get location object
			if(lm.getProvider(LocationManager.NETWORK_PROVIDER) != null){
				loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//LocationManager.NETWORK_PROVIDER);
				//get latitude and longitude, and then send them to the server
				if(loc != null){
					lat = loc.getLatitude();
					longi = loc.getLongitude();
					updateDatabaseLocation();
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider) {	}
		@Override
		public void onProviderEnabled(String provider) {	}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {	}

	};

	class UpdateLocation extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();	
		}

		@Override
		protected String doInBackground(String... args) {
			JSONObject json = jsonparser.makeHttpRequest(update_loc_url, "POST", params);

			Log.i("JSON: ", json.toString());
			System.out.println("Location: " + String.valueOf(lat));

			try{
				int success = json.getInt("success");

				if(success == 1){
				}
				else{
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String file_url){	}
	}
}

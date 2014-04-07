/*
 * RecipientSelectionActivity
 * Provides a list of groups/organizations that the user can choose to send the request to
 */

package com.example.goodsamaritan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class RecipientSelectionActivity extends Activity {
	
	// TODO: check the database for organizations/groups that the samaritan is a member of and create checkboxes for each
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipient_selection);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/** Set up the {@link android.app.ActionBar}. */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipient_selection, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Submits the request to the database to be sent out to the selected recipients
	 * @param view
	 */
	public void sendRequest(View view){
		// TODO: send request to the database
		
		// TODO: implement a better notification system
		// Temporary "request sent" notification
		Context context = getApplicationContext();
		CharSequence text = "Request Sent";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}
	
	// TODO: get a list of groups that a request can be sent to
	// TODO: check if connected to Facebook and if so, display the facebook friends option
	// TODO: check to see if a group has been selected before submitting request

}

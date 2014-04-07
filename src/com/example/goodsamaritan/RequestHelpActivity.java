/*
 * RequestHelpActivity
 * Activity that allows the user to input info on the request and sends that info to the request database table
 */

package com.example.goodsamaritan;

import model.HelpRequest;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class RequestHelpActivity extends Activity {
	
	// UI References
	private EditText titleView;
	private EditText descriptionView;
	private RadioGroup urgencyView;
	// TODO: create a variable for the image
	
	// Request variable that stores user input as a request
	private HelpRequest request;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_help);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Sets up UI references
		titleView = (EditText) findViewById(R.id.title_text);
		descriptionView = (EditText) findViewById(R.id.description_text);
		urgencyView = (RadioGroup) findViewById(R.id.urgency_buttons);
		
		
	}

	/** Set up the {@link android.app.ActionBar}. */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_help, menu);
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
	
	@Override
	protected void onResume(){
		super.onResume();
		
		// TODO: saves and displays user input if user clicks back on recipient selection activity
		if(request != null){
			titleView.setText(request.getTitle());
			descriptionView.setText(request.getDescription());
			// TODO: set urgency button
		}
	}
	
	/**
	 * Starts the RecipientSelectionActivity
	 * @param view
	 */
	public void selectRecipients(View view){
		Intent selectionIntent = new Intent(this, RecipientSelectionActivity.class);
		
		// TODO: Either pass request details to next activity or submit it to database here
		
		startActivity(selectionIntent);
	}
	
	/**
	 * Checks the user input and sets errors if any required information is missing
	 * Stores the information in the request object if it looks good
	 * @return
	 */
	public boolean checkInfo(){
		String requestTitle = titleView.getText().toString();
		String description = descriptionView.getText().toString();
		int buttonID = urgencyView.getCheckedRadioButtonId();
		RadioButton urgencyButton = (RadioButton) findViewById(buttonID);
		String urgency = urgencyButton.getText().toString();
		
		titleView.setError(null);
		
		// Checks if there is a missing field
		if(requestTitle == "" || urgency == ""){
			// TODO: set error flags
			if(requestTitle == "")
				titleView.setError(getString(R.string.missing_field_error));
			//if(urgency=="")
				//TODO: set error flag for urgency
			return false;
		}
		// Else - information checks out, so save it to a request
		else{
			request = new HelpRequest(requestTitle, 0);	// TODO: pass in actual sender ID from profile
			request.setDescription(description);
			request.setUrgency(urgency);
			return true;
		}
	}
	
	// TODO: Check if title and urgency are empty
	// TODO: Connect picture button to camera

}

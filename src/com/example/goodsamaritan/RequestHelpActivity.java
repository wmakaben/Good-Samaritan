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
import android.support.v4.app.NavUtils;

public class RequestHelpActivity extends Activity {
	
	// UI References
	private EditText titleView;
	private EditText descriptionView;
	private RadioGroup urgencyView;
	private RadioButton lowButton;
	private RadioButton medButton;
	private RadioButton highButton;
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
		lowButton = (RadioButton) findViewById(R.id.urgency_low);
		medButton = (RadioButton) findViewById(R.id.urgency_medium);
		highButton = (RadioButton) findViewById(R.id.urgency_high);
		// Check off low button by default
		lowButton.setChecked(true);
		medButton.setChecked(false);
		highButton.setChecked(false);
		
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
			// This ID represents the Home or Up button. In the case of this activity, the Up button is shown. 
			// Use NavUtils to allow users to navigate up one level in the application structure. For
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
		
		// TODO: save and display user input if user clicks back on recipient selection activity
	}
	
	/** Starts the RecipientSelectionActivity and passes a request object to the next activity */
	public void selectRecipients(View view){
		if (checkInfo()){
			Intent selectionIntent = new Intent(this, RecipientSelectionActivity.class);
			selectionIntent.putExtra("help_request", request);		
			startActivity(selectionIntent);
		}
	}
	
	/** Checks for missing inputs and stores info in the request object if it is fine */
	public boolean checkInfo(){
		String requestTitle = titleView.getText().toString();
		String description = descriptionView.getText().toString();
		int buttonID = urgencyView.getCheckedRadioButtonId();
		RadioButton urgencyButton = (RadioButton) findViewById(buttonID);
		String urgency = urgencyButton.getText().toString();
		
		// Clear all previous errors
		titleView.setError(null);
		
		// Checks if there is a missing field
		if(requestTitle.trim().equals("")){
			titleView.setError(getString(R.string.missing_field_error));
			return false;
		}
		// Else - information checks out, save it to a request
		else{
			request = new HelpRequest(requestTitle);
			request.setDescription(description);
			request.setUrgency(urgency);
			return true;
		}
	}
	
	// TODO: Connect picture button to camera
	public void takePicture(){
		
	}
	
}

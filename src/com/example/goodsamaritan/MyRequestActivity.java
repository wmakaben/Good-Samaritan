package com.example.goodsamaritan;

import model.HelpRequest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyRequestActivity extends Activity {

	private static final String HELP_REQUEST_TAG = "help_request";
	static final String ACCEPTED_TAG = "Accepted";
	private static final String HELPER_PHONE_TAG = "Phone";
	
	private TextView titleView;
	private TextView descriptionView;
	private TextView urgencyView;
	private TextView samaritanView;
	private Button callButton;
	
	private HelpRequest myRequest;
	private String accepted;
	private String phoneNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_request);
		// Show the Up button in the action bar.
		setupActionBar();
		
		titleView = (TextView) findViewById(R.id.title);
		descriptionView = (TextView) findViewById(R.id.description);
		urgencyView = (TextView) findViewById(R.id.urgency);
		samaritanView = (TextView) findViewById(R.id.samaritan_helper);
		callButton = (Button) findViewById(R.id.call_button);
		
		Bundle b = getIntent().getExtras();
		myRequest = b.getParcelable(HELP_REQUEST_TAG);
		
		String samaritanName = myRequest.gethelperID();	// TODO: change from id to name
		
		accepted = getIntent().getStringExtra(ACCEPTED_TAG);
		if(!accepted.trim().equals("1")){
			callButton.setVisibility(View.GONE);
			samaritanName = "None";
		}
		
		titleView.setText(myRequest.getTitle());
		descriptionView.setText("Description:\n" + myRequest.getDescription());
		urgencyView.setText("Urgency: " + myRequest.getUrgency());
		samaritanView.setText("Samaritan Helper: " + samaritanName);
		
		phoneNumber = getIntent().getStringExtra(HELPER_PHONE_TAG);
	}

	/** Set up the {@link android.app.ActionBar}. */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_request, menu);
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

	public void editRequest(View view){
		// TODO: edit request
	}
	
	public void withdrawRequest(View view){
		// TODO: withdraw request
	}
	
	public void requestResolved(View view){
		// TODO: request finished
	}
	
	public void callSamaritan(View view){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:"+phoneNumber));
		startActivity(callIntent);
	}
	
}

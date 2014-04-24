package com.example.goodsamaritan;

import model.HelpRequest;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class CurrentlyHelpingActivity extends Activity {
	
	private static final String SENDER_PHONE_TAG = "SenderPhone";
	private static final String HELP_REQUEST_TAG = "help_request";
	
	private HelpRequest request;
	private String phoneNumber;
	
	private TextView nameView;
	private TextView titleView;
	private TextView descriptionView;
	private TextView urgencyView;
	private Button callButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currently_helping);
		// Show the Up button in the action bar.
		setupActionBar();
		
		titleView = (TextView)findViewById(R.id.title);
		nameView = (TextView)findViewById(R.id.sender_name);
		descriptionView = (TextView)findViewById(R.id.description);
		urgencyView = (TextView)findViewById(R.id.urgency);
		callButton = (Button)findViewById(R.id.call_button);
		
		Bundle b = getIntent().getExtras();
		request = b.getParcelable(HELP_REQUEST_TAG);
		phoneNumber = getIntent().getStringExtra(SENDER_PHONE_TAG);
		
		titleView.setText(request.getTitle());
		nameView.setText(request.getSenderID());
		descriptionView.setText("Description:\n" + request.getDescription());
		urgencyView.setText("Urgency: " + request.getUrgency());
		callButton.setText("Call " + request.getSenderID());
	}

	/** Set up the {@link android.app.ActionBar}. */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.currently_helping, menu);
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
	
	public void startCall(View view){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:"+phoneNumber));
		startActivity(callIntent);
	}
	
}

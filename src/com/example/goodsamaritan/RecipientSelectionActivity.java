/*
 * RecipientSelectionActivity
 * Provides a list of groups/organizations that the user can choose to send the request to
 */

package com.example.goodsamaritan;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import model.HelpRequest;
import model.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RecipientSelectionActivity extends Activity { 
	
	// UI References
	LinearLayout layout;		// Layout that holds the dynamic check boxes
	CheckBox checkbox;			// CheckBox - used to dynamically create check boxes
	
	private HelpRequest request;				// Request class that holds all the information of a request
	private ArrayList<String> availableGroups;	// List that holds a list of all available groups to select from
	private ArrayList<String> selectedGroups;	// List that holds a list of groups selected from the list of available groups
	// SharedPreferences variables
	final String PREFS_NAME = "MyPrefsFile";
	private SharedPreferences sharedPref;
	
	private JSONParser jsonParser;	// Parses JSON
	private ProgressDialog pDialog;	// Progress dialog for registering
	private static String url_register = "http://153.104.19.82:81/GoodSamaritan/new_request.php";
    private static final String TAG_SUCCESS = "success";
    // TODO: get url for both getting groups and sending the request
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipient_selection);
		// Show the Up button in the action bar.
		setupActionBar();
		
		sharedPref = getSharedPreferences(PREFS_NAME, 0);
		jsonParser = new JSONParser();
		layout = (LinearLayout)findViewById(R.id.checkBoxLayout);
		
		selectedGroups = new ArrayList<String>();
		availableGroups = new ArrayList<String>();
		availableGroups.add("Nearby Samaritans");	// Add the default nearby samaritans option
		availableGroups.add("Facebook Friends");	// TODO: check if connected to facebook then add the Facebook option
		
		// Gets request information from the RequestHelpActivity
		Bundle b = getIntent().getExtras();
		request = b.getParcelable("help_request");
		
		// TODO: Get list of groups from database. Add groups to availableGroups list
		
		// Dynamically add check boxes for each option in the availableGroups list
		for(int i=0; i<availableGroups.size(); i++){
			CheckBox cb = new CheckBox(this);
			cb.setText(availableGroups.get(i));
			cb.setOnClickListener(getOnClickListener(cb));
			layout.addView(cb);
		}
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
	
	/** returns a listener for the checkboxes */
	public View.OnClickListener getOnClickListener(final CheckBox box){
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// If the box is checked, add the name of the group to the selectedGroups list
				if (box.isChecked())
					selectedGroups.add(box.getText().toString());
				// If the box is unchecked, remove the name of the group from the selectedGroup list
				else
					selectedGroups.remove(box.getText().toString());
			}
		};
	}
	
	/** Submits the request to the database to be sent out to the selected recipients */
	public void sendRequest(View view){
		
		// Sets up initial toast required variables
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		
		// Checks if at least one group has been selected
		if(!selectedGroups.isEmpty()){
			new SendRequest().execute();	// Sends request info to the database
			
			// Temporary "request sent" notification
			CharSequence text = "Request Sent";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			// Return to the main activity
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}
		// Else - warn user that there needs to be one group selected
		else{
			CharSequence text = "Select at least 1 group";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
	
	/**	Class that connects to the database and stores the info is successful */
	class SendRequest extends AsyncTask<String, String, String>{
		
		List <NameValuePair> params;
		
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject json = jsonParser.makeHttpRequest(url_register, "POST", params);
			try{
				int success = json.getInt(TAG_SUCCESS);
				if(success == 1){
					// TODO: If successful connection then do something
				}
				else{
					// TODO: Unsuccessful connection, do something else 
				}
			}catch (JSONException e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(RecipientSelectionActivity.this);
			pDialog.setMessage("Requesting Help...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("title", request.getTitle()));
			params.add(new BasicNameValuePair("description", request.getDescription()));
			params.add(new BasicNameValuePair("urgency", request.getUrgency()));
			params.add(new BasicNameValuePair("email", sharedPref.getString("email", "")));
		}
		
		@Override
		protected void onPostExecute(String file_url){
			pDialog.dismiss();
		}
	}
}

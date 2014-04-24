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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HelperRequestViewActivity extends Activity {

	private static final String HELP_REQUEST_TAG = "help_request";
	private static final String NAME_TAG = "Sender_name";
	private static final String PHONE_TAG = "Phone";
	
	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences
	
	private HelpRequest request;
	private String senderName;
	private String phoneNumber;
	
	private TextView nameTitleView;
	private TextView titleView;
	private TextView descriptionView;
	private TextView urgencyView;
	private Button callButton;
	
	private JSONObject json;
	private JSONParser jsonParser;
	private final String accepted_url = "http://153.104.185.129:81/goodsamaritan/accept_request.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper_request_view);
		// Show the Up button in the action bar.
		setupActionBar();
		
		nameTitleView = (TextView) findViewById(R.id.sender_name);
		titleView = (TextView) findViewById(R.id.title);
		descriptionView = (TextView) findViewById(R.id.description);
		urgencyView = (TextView) findViewById(R.id.urgency);
		
		Bundle b = getIntent().getExtras();
		request = b.getParcelable(HELP_REQUEST_TAG);
		senderName = getIntent().getStringExtra(NAME_TAG);
		phoneNumber = getIntent().getStringExtra(PHONE_TAG);
		
		titleView.setText(request.getTitle());
		nameTitleView.setText("Sender: " + senderName);
		descriptionView.setText("Description:\n" + request.getDescription());
		urgencyView.setText(request.getUrgency());
		
		callButton = (Button) findViewById(R.id.call_button);
		String firstName = senderName.substring(0, senderName.indexOf(" "));
		callButton.setText("Call " + firstName);
		
		sharedPref = getSharedPreferences(PREFS_NAME, 0);	
		jsonParser = new JSONParser();
	}

	/** Set up the {@link android.app.ActionBar}. */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.helper_request_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this activity, the Up 
			// button is shown. Use NavUtils to allow users to navigate up one level in the 
			// application structure. For more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void acceptRequest(View view){
		new Accepted().execute();
		
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		CharSequence text = "Now Helping " + senderName;
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public void startCall(View view){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:"+phoneNumber));
		startActivity(callIntent);
	}
	
	class Accepted extends AsyncTask<String, String, String>{
		List<NameValuePair> params; 
		
		@Override
		protected void onPreExecute(){
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", sharedPref.getString("email", "null")));
			params.add(new BasicNameValuePair("ID", request.getRequestID()));
		}
	
		@Override
		protected String doInBackground(String... arg0) {			
			JSONObject json = jsonParser.makeHttpRequest(accepted_url, "POST", params);
			
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
		
	}
}

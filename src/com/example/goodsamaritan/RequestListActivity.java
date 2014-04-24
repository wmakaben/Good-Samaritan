package com.example.goodsamaritan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.HelpRequest;
import model.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class RequestListActivity extends Activity {
	
	// tag references
	private static final String FIRST_TAG = "First";
	private static final String LAST_TAG = "Last";
	private static final String NAME_TAG = "Sender_name";
	private static final String SENDER_ID_TAG = "SenderID";
	private static final String PHONE_TAG = "Phone";
	private static final String REQUEST_ID_TAG = "ID";
	private static final String TITLE_TAG = "Title";
	private static final String DESCRIPTION_TAG = "Description";
	private static final String URGENCY_TAG = "Urgency";
	private static final String DIST_TAG = "Distance";
	private static final String HELP_REQUEST_TAG = "help_request";
	
	private ListView listView;	// UI Reference
	
	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences
	
	private JSONParser jsonParser;	// Parses JSON
	//private static String url_register = "http://153.104.90.74:81/GoodSamaritan/get_request_join_tables.php";
	private static String url_register = "http://153.104.185.129:81/GoodSamaritan/get_nearby_requests.php";
    private ProgressDialog pDialog;	// Progress dialog
    private ArrayList<HashMap<String, String>> requestList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		listView = (ListView) findViewById(R.id.list);
		sharedPref = getSharedPreferences(PREFS_NAME, 0);
		jsonParser = new JSONParser();
		requestList = new ArrayList<HashMap<String, String>>();
		
		// Gets a list of requests from the database
		new GetRequests().execute();
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
				System.out.println("Setting up intent");
				
				HelpRequest helpRequest = new HelpRequest(((TextView) view.findViewById(R.id.request_title)).getText().toString());
				helpRequest.setDescription(((TextView) view.findViewById(R.id.description)).getText().toString());
				helpRequest.setSenderID(((TextView) view.findViewById(R.id.sender_id)).getText().toString());
				helpRequest.setRequestID(((TextView) view.findViewById(R.id.request_id)).getText().toString());
				helpRequest.setUrgency(((TextView) view.findViewById(R.id.urgency)).getText().toString());
				
				System.out.println(helpRequest.getTitle() + ", " + helpRequest.getDescription() + ", " + helpRequest.getSenderID() + ", "+ helpRequest.getUrgency() + ", " + helpRequest.getRequestID());
				
				String name = ((TextView) view.findViewById(R.id.sender_name)).getText().toString();
				String phoneNumber = ((TextView) view.findViewById(R.id.phone_number)).getText().toString();
				
				Intent i = new Intent(getApplicationContext(), HelperRequestViewActivity.class);
				i.putExtra(HELP_REQUEST_TAG, helpRequest);
				i.putExtra(NAME_TAG, name);
				i.putExtra(PHONE_TAG, phoneNumber);
				startActivity(i);				
			}
			
		});
		
	}

	/** Set up the {@link android.app.ActionBar}. */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_list, menu);
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

	/**	Class that connects to the database and stores the info is successful */
	class GetRequests extends AsyncTask<String, String, String>{
		List <NameValuePair> params;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(RequestListActivity.this);
			pDialog.setMessage("Getting Requests...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", sharedPref.getString("email", "")));
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			System.out.println("Test");
			try{
				JSONObject json = jsonParser.makeHttpRequest(url_register, "POST", params);
				JSONArray requests = json.getJSONArray("requests");
				
				System.out.println("Getting requests");
				for(int i=0; i<requests.length(); i++){
					JSONObject request = requests.getJSONObject(i);
					
					String firstName = request.getString(FIRST_TAG);
					String lastName = request.getString(LAST_TAG);
					String senderName = firstName + " " + lastName;
					String senderID = request.getString(SENDER_ID_TAG);
					String phoneNumber = request.getString(PHONE_TAG);
					String requestID = request.getString(REQUEST_ID_TAG);
					String title = request.getString(TITLE_TAG);
					String description = request.getString(DESCRIPTION_TAG);
					String urgency = "Urgency: " + request.getString(URGENCY_TAG);
					String distance = "Distance(km): " + String.valueOf(request.getInt(DIST_TAG));
										
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(FIRST_TAG, firstName);
					map.put(LAST_TAG, lastName);
					map.put(NAME_TAG, senderName);
					map.put(SENDER_ID_TAG, senderID);
					map.put(PHONE_TAG, phoneNumber);
					map.put(REQUEST_ID_TAG, requestID);
					map.put(TITLE_TAG, title);
					map.put(DESCRIPTION_TAG, description);
					map.put(URGENCY_TAG, urgency);
					map.put(DIST_TAG, distance);
					requestList.add(map);
					
				}
				System.out.println("Added requests to list");
			
			}catch (JSONException e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String file_url){
			pDialog.dismiss();
			
			ListAdapter adapter = new SimpleAdapter(RequestListActivity.this, 
													requestList, 
													R.layout.list_item, 
													new String[]{TITLE_TAG, NAME_TAG, URGENCY_TAG, DIST_TAG, SENDER_ID_TAG, PHONE_TAG, REQUEST_ID_TAG, DESCRIPTION_TAG}, 
													new int[]{R.id.request_title, R.id.sender_name, R.id.urgency, R.id.distance, R.id.sender_id, R.id.phone_number, R.id.request_id, R.id.description} );
			listView.setAdapter(adapter);
		}
	}	
}

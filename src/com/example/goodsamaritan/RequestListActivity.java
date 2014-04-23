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
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class RequestListActivity extends Activity {
	
	// HashMap tag references
	private static final String TITLE_TAG = "title";
	private static final String NAME_TAG = "sender_name";
	private static final String DIST_TAG = "distance";
	
	private ListView listView;	// UI Reference
	
	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences
	
	private JSONParser jsonParser;	// Parses JSON
	private static String url_register = "http://153.104.37.89:81/GoodSamaritan/get_nearby_requests.php";
    private static final String TAG_SUCCESS = "success";
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
				TextView clickedView = (TextView) view;
				
				// TODO: Get info from text view and pass it through an intent
				
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
			
			try{
				JSONObject json = jsonParser.makeHttpRequest(url_register, "POST", params);
				JSONArray requests = json.getJSONArray("requests");
				
				for(int i=0; i<requests.length(); i++){
					JSONObject request = requests.getJSONObject(i);
					
					String title = request.getString("Title");
					String senderName = request.getString("First") + " " + request.getString("Last");
					int distance = request.getInt("Distance");
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TITLE_TAG, title);
					map.put(NAME_TAG, senderName);
					map.put(DIST_TAG, String.valueOf(distance));
					requestList.add(map);
				}
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
													new String[]{TITLE_TAG, NAME_TAG, DIST_TAG}, 
													new int[]{R.id.request_title, R.id.sender_name, R.id.distance} );
			listView.setAdapter(adapter);
		}
	}	
}

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HelperFragment extends Fragment {
	
	private static final String TITLE_TAG = "Title";
	private static final String REQUEST_ID_TAG = "ID";
	private static final String DESCRIPTION_TAG = "Description";
	private static final String URGENCY_TAG = "Urgency";
	private static final String SENDER_FIRST_TAG = "SenderFirst";
	private static final String SENDER_LAST_TAG = "SenderLast";
	private static final String SENDER_PHONE_TAG = "SenderPhone";
	private static final String HELP_REQUEST_TAG = "help_request";
	private static final String SENDER_NAME_TAG = "sender_name";
	
	private ListView listView;
	
	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences
	
	private JSONParser jsonParser;	// Parses JSON
	private static String url_get_requests = "http://153.104.185.129:81/GoodSamaritan/get_helping.php";
    private ArrayList<HashMap<String, String>> requestList;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper, container, false);
        
        listView = (ListView) rootView.findViewById(R.id.request_helper_list);
		sharedPref = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
		jsonParser = new JSONParser();
		requestList = new ArrayList<HashMap<String, String>>();
        
        new GetHelping().execute();
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
				System.out.println("Setting up intent");
				
				HelpRequest helpRequest = new HelpRequest(((TextView) view.findViewById(R.id.title)).getText().toString());
				helpRequest.setDescription(((TextView) view.findViewById(R.id.description)).getText().toString());
				helpRequest.setUrgency(((TextView) view.findViewById(R.id.urgency)).getText().toString());
				helpRequest.setRequestID(((TextView) view.findViewById(R.id.request_ID)).getText().toString());
				helpRequest.setSenderID(((TextView) view.findViewById(R.id.sender_name)).getText().toString()); // TODO: Create a field for the helper name. It's a name not an id
				String senderPhoneNumber = (((TextView) view.findViewById(R.id.sender_phone)).getText().toString());
				
				Intent i = new Intent(getActivity(), CurrentlyHelpingActivity.class);
				i.putExtra(HELP_REQUEST_TAG, helpRequest);
				i.putExtra(SENDER_PHONE_TAG, senderPhoneNumber);
				startActivity(i);	
			}
		});
        
        return rootView;
    }
	
	class GetHelping extends AsyncTask<String, String, String>{
		List <NameValuePair> params;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", sharedPref.getString("email", "")));
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			
			try {
				JSONObject json = jsonParser.makeHttpRequest(url_get_requests, "POST", params);
				JSONArray requests = json.getJSONArray("requests");
				
				for(int i=0; i<requests.length(); i++){
					JSONObject request = requests.getJSONObject(i);
					
					String title = request.getString(TITLE_TAG);
					String description = request.getString(DESCRIPTION_TAG);
					String urgency = request.getString(URGENCY_TAG);
					String requestID = request.getString(REQUEST_ID_TAG);
					String senderFirst = request.getString(SENDER_FIRST_TAG);
					String senderLast = request.getString(SENDER_LAST_TAG);
					String senderPhone = request.getString(SENDER_PHONE_TAG);
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TITLE_TAG, title);
					map.put(DESCRIPTION_TAG, description);
					map.put(URGENCY_TAG, urgency);
					map.put(REQUEST_ID_TAG, requestID);
					map.put(SENDER_NAME_TAG, senderFirst + " " + senderLast);
					map.put(SENDER_PHONE_TAG, senderPhone);
					requestList.add(map);
				}
				System.out.println("Connected to request list");
			} catch (JSONException e) {	
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String file_url){			
			ListAdapter adapter = new SimpleAdapter(getActivity(), 
													requestList, 
													R.layout.requests_helping_list_item, 
													new String[]{TITLE_TAG, DESCRIPTION_TAG, URGENCY_TAG, REQUEST_ID_TAG, SENDER_NAME_TAG, SENDER_PHONE_TAG}, 
													new int[]{R.id.title, R.id.description, R.id.urgency, R.id.request_ID, R.id.sender_name, R.id.sender_phone} );
			listView.setAdapter(adapter);
		}
	}
}
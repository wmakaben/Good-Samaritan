/*
 * SignInActivity
 * Login activity - requires email and password
 */

package com.example.goodsamaritan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.JSONParser;
import model.LoginValidation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity {
	
	// UI references
	private EditText emailText;			// email EditText
	private EditText passwordText;		// password EditText
	private TextView registerLink;		// TextView link to register activity
	
	private String dbPassword;			// User password from database
	private String inputPassword;		// Password from password field
	
	LoginValidation validator = new LoginValidation();	// LoginValidator - checks if email/password is valid
	
	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences
	
	private JSONParser jsonParser;	// Parses JSON
	private ProgressDialog pDialog;	// Progress dialog for registering
    private static String url_login = "http://153.104.113.109:81/GoodSamaritan/login.php";		// TODO: get a better way of finding ip
    private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		// Set up UI references
		emailText = (EditText) findViewById(R.id.email);
		passwordText = (EditText) findViewById(R.id.password);
		registerLink = (TextView) findViewById(R.id.register_link);
		
		sharedPref = getSharedPreferences(PREFS_NAME, 0);
		
		jsonParser = new JSONParser();
		
		// listener for TextView that switches to the register activity
		registerLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(registerIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}
	
	/**
	 * When button is clicked, attempt to sign in
	 * @param view
	 */
	public void loginAttempt(View view){
		
		if(isValidCredentials()){
			try {
				new Login().execute().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Password: " + dbPassword);
			
			inputPassword = passwordText.getText().toString();			
			//inputPassword = "passeord";
			//dbPassword = "passeord";
			// Check if database password matches the user input password
			if(dbPassword != null && dbPassword.equals(inputPassword)){
				
				// Saves the login credentials to shared preferences
				sharedPref.edit().putString("email", emailText.getText().toString()).commit();
				sharedPref.edit().putString("password", inputPassword);
				
				finish();
			}
			
			else{
				// TODO: Wrong password notification
				Context context = getApplicationContext();
				CharSequence text = "Wrong Password";
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}
	}
	
	/**
	 * Checks if the email and password follow the correct format or if it exists in the database
	 * @return	true if input is correct, false otherwise
	 */
	public boolean isValidCredentials(){
		boolean isValid = true;
		
		if(!validator.isValidEmail(emailText.getText().toString())){
			isValid = false;
			emailText.setError("Invalid Email Address");
		}
		
		return isValid;
	}
	
	class Login extends AsyncTask<String, String, String>{
		
		List<NameValuePair> params;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(SignInActivity.this);
			pDialog.setMessage("Logging In...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", emailText.getText().toString()));			
		}
		
		@Override
		protected String doInBackground(String... args) {
			JSONObject json = jsonParser.makeHttpRequest(url_login, "POST", params);

			Log.i("JSON: ", json.toString());
			
			try{
				int success = json.getInt(TAG_SUCCESS);
								
				if(success == 1){
					// Get the password for the email
					Log.i("Password from JSON", json.getString("Password"));
					dbPassword = json.getString("Password");

					Log.i("PASSWORD VALUE", dbPassword);
				}
				else{
					dbPassword = "";	// TODO: change this later
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String file_url){
			pDialog.dismiss();
		}
		
	}
	
	
	// TODO: Add option for forgotten passwords
}

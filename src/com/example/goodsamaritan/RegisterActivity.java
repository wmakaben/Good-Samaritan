/*
 * RegisterActivity
 * Registration screen for first time users. Has a field to input name, email, and password
 */

package com.example.goodsamaritan;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import model.JSONParser;
import model.LoginValidation;
import model.Samaritan;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	// UI references
	private EditText firstName;			// EditText for first name input
	private EditText lastName;			// EditText for last name input
	private EditText email;				// EditText for email input
	private EditText password;			// EditText for password input
	private EditText confirm_password;	// EditText for password confirmation input
	private TextView loginLink;			// TextView link to login activity
	
	private Samaritan newUser;		// Stores the input values as a new Samaritan
	
	private LoginValidation validator;	// Checks if email/password is valid
	
	private JSONParser jsonParser;	// Parses JSON
	private ProgressDialog pDialog;	// Progress dialog for registering
    private static String url_register = "http://153.104.19.82:81/GoodSamaritan/newuser.php";		// TODO: get a better way of finding ip
    private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		// Set up UI references
		firstName = (EditText) findViewById(R.id.first_name);
		lastName = (EditText) findViewById(R.id.last_name);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		confirm_password = (EditText) findViewById(R.id.confirm_password);
		
		validator = new LoginValidation();
		jsonParser = new JSONParser();
		
		// Creates the listener to switch back to the login activity
		loginLink = (TextView) findViewById(R.id.login_link);
		loginLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent loginIntent = new Intent(getApplicationContext(), SignInActivity.class);
				startActivity(loginIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	@Override
    public void onPause(){
		super.onPause();
		if(pDialog != null)
			pDialog.dismiss();
    }
	
	/**
	 * Checks the user input for valid information
	 * @param view
	 */
	public void registerAttempt(View view){
		
		if(isValidInput()){
			// Samaritan object that holds the input values
			newUser = new Samaritan(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
			newUser.setPassword(password.getText().toString());
			
			// TODO: Add samaritan data to the database as an unverified new user, verification is done by email
			// TODO: add samaritan data by using the Samaritan class getter methods
			new Register().execute();
			
			// TODO: change notifications
			// Registration notification
			Context context = getApplicationContext();
			CharSequence text = "Check your email to verify your account";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			
			finish();
		}
		else{
			password.setText("");
			confirm_password.setText("");
		}
	}
	
	/**
	 * Checks for valid input by checking if any fields are left blank, 
	 * if the email/password format is correct, and if the password matches the confirmation
	 * @return	true if all fields are filled and have the correct format, false otherwise
	 */
	public boolean isValidInput(){
		boolean isValid = true;
		
		// Check if the first name field is empty
		if(firstName.getText().toString().trim().equals("")){
			firstName.setError("This field is required");
			isValid = false;
		}
		// Check if the last name field is empty
		if(lastName.getText().toString().trim().equals("")){
			lastName.setError("This field is required");
			isValid = false;
		}
		// Check if email does not match the proper format
		if(!validator.isValidEmail(email.getText().toString())){
			email.setError("Improper email format");
			isValid = false;
		}
		// Check if password follows proper format
		if(!validator.isValidPassword(password.getText().toString())){
			password.setError("Password must have at least 1 number, 1 lower case letter, and a length of 6-20 characters");
			isValid = false;
		}
		// Checks if the confirmation matches the password
		if(!password.getText().toString().equals(confirm_password.getText().toString())){
			confirm_password.setError("Password does not match");
			password.setError("Password does not match");
			isValid = false;
		}		
		return isValid;
	}
	
	class Register extends AsyncTask<String, String, String>{

		List<NameValuePair> params;
		
		@Override
		protected String doInBackground(String... args) {
			JSONObject json = jsonParser.makeHttpRequest(url_register, "POST", params);
			
			try{
				int success = json.getInt(TAG_SUCCESS);
				if(success == 1){
					//start the home activity if successful
					Intent i  = new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(i);
					//closing screen
					//pDialog.dismiss();
					finish();
				}
				else{
					//failed to register
					finish();
				}
			}catch (JSONException e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setMessage("Registering...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			
			/*
			first = firstName.getText().toString();
			last = lastName.getText().toString();
			latitude = "0";
			longitude = "0";
			emailinsert = email.getText().toString();
			passwordinsert = password.getText().toString();
            */ 
               
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("first", newUser.getFirstName()));
			params.add(new BasicNameValuePair("last", newUser.getLastName()));
			params.add(new BasicNameValuePair("latitude", String.valueOf(newUser.getLatitude())));
			params.add(new BasicNameValuePair("longitude", String.valueOf(newUser.getLongitude())));
			params.add(new BasicNameValuePair("email", newUser.getEmail()));
			params.add(new BasicNameValuePair("password", newUser.getPassword()));
		}
		
		protected void onPostExecute(String file_url){
			//dismiss the progress dialogue
			pDialog.dismiss();
		}
		
	}
	
	
	// TODO: Send verification email to given email, set user profile to unverified until email link is clicked
	// Unverified accounts cannot be logged on to and disappear after a certain time limit
}

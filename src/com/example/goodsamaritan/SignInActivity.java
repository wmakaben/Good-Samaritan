/*
 * SignInActivity
 * Login activity - requires email and password
 */

package com.example.goodsamaritan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.LoginValidation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends Activity {
	
	// UI references
	private EditText emailText;			// email EditText
	private EditText passwordText;		// password EditText
	private TextView registerLink;		// TextView link to register activity
	
	LoginValidation validator = new LoginValidation();	// LoginValidator - checks if email/password is valid
	
	final String PREFS_NAME = "MyPrefsFile";	// Name for shared preferences file
	private SharedPreferences sharedPref;		// Shared Preferences
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		// Set up UI references
		emailText = (EditText) findViewById(R.id.email);
		passwordText = (EditText) findViewById(R.id.password);
		registerLink = (TextView) findViewById(R.id.register_link);
		
		sharedPref = getSharedPreferences(PREFS_NAME, 0);
		
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
			// Saves the login credentials to shared preferences
			sharedPref.edit().putString("email", emailText.getText().toString()).commit();
			sharedPref.edit().putString("password", passwordText.getText().toString()).commit();
			
			// Starts the Home Activity after login
			Intent i = new Intent(this, HomeActivity.class);
			startActivity(i);
			finish();
		}
	}
	
	/**
	 * Checks if the email and password follow the correct format or if it exists in the database
	 * @return
	 */
	public boolean isValidCredentials(){
		boolean isValid = true;
		
		if(!validator.isValidEmail(emailText.getText().toString())){
			isValid = false;
			emailText.setError("Invalid Email Address");
		}
		
		// TODO: Check if email is in database, set error
		// TODO: Check if the password in the database matches the password input, set error
		
		return isValid;
	}
	
	// TODO: Option for forgotten passwords
	// TODO: save email/password to sharedpreferences
}

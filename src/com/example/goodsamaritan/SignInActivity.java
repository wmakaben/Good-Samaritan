package com.example.goodsamaritan;

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
	private EditText emailText;
	private EditText passwordText;
	private TextView registerLink;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		// Set up UI references
		emailText = (EditText) findViewById(R.id.email);
		passwordText = (EditText) findViewById(R.id.password);
		registerLink = (TextView) findViewById(R.id.register_link);
		
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
		// TODO: check validity of email/password and set error flags when necessary
		
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
		finish();
	}

}

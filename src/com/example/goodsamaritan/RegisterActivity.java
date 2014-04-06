package com.example.goodsamaritan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	
	private EditText firstName;
	private EditText lastName;
	private EditText email;
	private EditText password;
	private EditText confirm_password;
	
	private TextView loginLink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		firstName = (EditText) findViewById(R.id.first_name);
		lastName = (EditText) findViewById(R.id.last_name);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		confirm_password = (EditText) findViewById(R.id.confirm_password);
		
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
	
	public void registerAttempt(View view){
		// TODO: check info validity and set error flags when necessary
		
		finish();
	}

}

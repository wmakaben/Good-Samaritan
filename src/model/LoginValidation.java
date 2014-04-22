package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidation {
	
	// Email Regex Pattern
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	// Password Regex Pattern - 1 number, 1 lower case character, length 6-20
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z]).{6,20})";
	private static final String PHONE_PATTERN = "^\\d{10}$";
	private Pattern pattern;
	private Matcher matcher;
	
	public LoginValidation(){
	}
	
	/**
	 * Checks if the email input is valid by comparing it to regular expression
	 * @param email	user email for logging in
	 * @return	true if valid email format, and false if not valid
	 */
	public boolean isValidEmail(CharSequence email){
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * Checks if the password input is valid by comparing it to a regular expression.
	 * The password must have 1 number, 1 lower case character, and a length of 6-20 characters
	 * @param password	login password
	 * @return	true if valid, and false if not valid
	 */
	public boolean isValidPassword(CharSequence password){
		pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
	
	public boolean isValidPhoneNumber(CharSequence pNum){
		pattern = Pattern.compile(PHONE_PATTERN);
		matcher = pattern.matcher(pNum);
		return matcher.matches();
	}
}

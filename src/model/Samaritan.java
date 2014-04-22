package model;

public class Samaritan {
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private int id;
	private int requestsCompleted;
	private int requestsAccepted;
	private double rating;
	private String password;
	private long latitude;
	private long longitude;
	
	public Samaritan(String first, String last, String email){
		this.firstName = first;
		this.lastName = last;
		this.email = email;
	}

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; } 

	public void setEmail(String email) { this.email = email; }

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public int getRequestsCompleted() {	return requestsCompleted; }

	public void setRequestsCompleted(int requestsCompleted) { this.requestsCompleted = requestsCompleted; }

	public int getRequestsAccepted() { return requestsAccepted; }

	public void setRequestsAccepted(int requestsAccepted) {	this.requestsAccepted = requestsAccepted; }

	public double getRating() {	return rating; }

	public void setRating(double rating) { this.rating = rating; }

	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public long getLatitude() { return latitude; }

	public void setLatitude(long latitude) { this.latitude = latitude; }

	public long getLongitude() { return longitude; }

	public void setLongitude(long longitude) { this.longitude = longitude; }
	
	public String getPhoneNumber(){ return phoneNumber; }
	
	public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }
}

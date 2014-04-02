/*
 * HelpRequest.java
 * Represents a help request and holds all the variables associated with a request
 */

package model;

public class HelpRequest {
	
	 private int requestID;
	 private int samaritanID;
	 private int senderID;
	 private String title;
	 private String description;
	 private String urgency;
	 
	 //TODO: figure out how to save the request image
	 //TODO: date variable
	 //TODO: category?
	
	 public HelpRequest(String title, int senderID){
		 this.title = title;
		 this.senderID = senderID;
	 }

	public int getRequestID() {return requestID;}

	public void setRequestID(int requestID) {this.requestID = requestID;}

	public int getSamaritanID() {return samaritanID;}

	public void setSamaritanID(int samaritanID) {this.samaritanID = samaritanID;}

	public int getSenderID() {return senderID;}

	public void setSenderID(int senderID) {this.senderID = senderID;}

	public String getTitle() {return title;}

	public void setTitle(String title) {this.title = title;}

	public String getDescription() {return description;}

	public void setDescription(String description) {this.description = description;}

	public String getUrgency() {return urgency;}

	public void setUrgency(String urgency) {this.urgency = urgency;	}
	
}

/*
 * HelpRequest.java
 * Represents a help request and holds all the variables associated with a request
 */

package model;

import android.os.Parcel;
import android.os.Parcelable;

public class HelpRequest implements Parcelable {

	private String requestID;
	private String helperID;
	private String senderID;
	private String title;
	private String description;
	private String urgency;

	//TODO: figure out how to save the request image
	//TODO: date variable
	//TODO: category?

	public HelpRequest(String title){
		this.title = title;
	}
	
	/** Constructor - takes in a parcelable object and transforms it into a HelpRequest object */
	public HelpRequest(Parcel in){
		String [] data = new String[6];
		
		in.readStringArray(data);
		this.requestID = data[0];
		this.helperID = data[1];
		this.senderID = data[2];
		this.title = data[3];
		this.description = data[4];
		this.urgency = data[5];
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[]{	this.requestID, 
											this.helperID, 
											this.senderID, 
											this.title, 
											this.description, 
											this.urgency});
	}

	public static final Parcelable.Creator<HelpRequest> CREATOR = new Parcelable.Creator<HelpRequest>() {
		@Override
		public HelpRequest createFromParcel(Parcel in) {
			return new HelpRequest(in);
		}

		@Override
		public HelpRequest[] newArray(int size) {
			return new HelpRequest[size];
		}
	};
	
	// Setters and Getters
	public String getRequestID() {return requestID;}
	public void setRequestID(String requestID) {this.requestID = requestID;}
	public String gethelperID() {return helperID;}
	public void sethelperID(String helperID) {this.helperID = helperID;}
	public String getSenderID() {return senderID;}
	public void setSenderID(String senderID) {this.senderID = senderID;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	public String getUrgency() {return urgency;}
	public void setUrgency(String urgency) {this.urgency = urgency;	}

}

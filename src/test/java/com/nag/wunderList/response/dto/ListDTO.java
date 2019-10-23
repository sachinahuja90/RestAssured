package com.nag.wunderList.response.dto;


public class ListDTO {
	long id;
	String created_at;
	String title;
	String list_type;
	String type;
	int revision;
	
    String owner_type;
    long owner_id;
    
	public long getId() {
		return id;
	}
	
	public long getOwnerId() {
		return owner_id;
	}
	
	public String getOwner_Type() {
		return owner_type;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getList_type() {
		return list_type;
	}
	
	public String getType() {
		return type;
	}
	
	public int getRevision() {
		return revision;
	}
}

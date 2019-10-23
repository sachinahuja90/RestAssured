package com.nag.wunderList.response.dto;


public class NotesDTO {
	
    
    long id;
	String type;
	String content;
	String task_id;
	int revision;
	
    String owner_type;
    long owner_id;
    
	public long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getType() {
		return type;
	}
	
	public String getTaskId() {
		return task_id;
	}
	
	public int getRevision() {
		return revision;
	}
}

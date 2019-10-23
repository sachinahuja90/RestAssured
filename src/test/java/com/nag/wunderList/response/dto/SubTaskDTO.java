package com.nag.wunderList.response.dto;


public class SubTaskDTO {
	long id;
    long task_id;
    String created_at;
    long created_by_id;
    int revision;
    String title;
    String type;
    
	public long getId() {
		return id;
	}
	public long getTask_id() {
		return task_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public long getCreated_by_id() {
		return created_by_id;
	}
	public int getRevision() {
		return revision;
	}
	public String getTitle() {
		return title;
	}
	public String getType() {
		return type;
	}
	
	
}

package com.nag.wunderList.response.dto;


public class TaskDTO {
	
		
	long id;
    long assignee_id;
    String created_at;
    long created_by_id;
    String due_date;
    long list_id;
    int revision;
    boolean starred;
    String title;
    boolean completed;
    String completed_at;
    String completed_by_id;
    String type;
    String list_type;
    
    
	public boolean isCompleted() {
		return completed;
	}
	public String getCompleted_at() {
		return completed_at;
	}
	public String getCompleted_by_id() {
		return completed_by_id;
	}
	public String getType() {
		return type;
	}
	public long getId() {
		return id;
	}
	public long getAssignee_id() {
		return assignee_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public long getCreated_by_id() {
		return created_by_id;
	}
	public String getDue_date() {
		return due_date;
	}
	public long getList_id() {
		return list_id;
	}
	public int getRevision() {
		return revision;
	}
	public boolean isStarred() {
		return starred;
	}
	public String getTitle() {
		return title;
	}
	
	public String getList_Type() {
		return list_type;
	}
	
	
}

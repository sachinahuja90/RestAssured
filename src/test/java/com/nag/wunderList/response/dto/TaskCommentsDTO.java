package com.nag.wunderList.response.dto;


public class TaskCommentsDTO {
	
	long id;
    long task_id;
    String local_created_at;
    String created_at;
    int revision;
    AuthorDTO author;
    String text;
    String type;
	public long getId() {
		return id;
	}
	public long getTask_id() {
		return task_id;
	}
	public String getLocal_created_at() {
		return local_created_at;
	}
	public String getCreated_at() {
		return created_at;
	}
	public int getRevision() {
		return revision;
	}
	public AuthorDTO getAuthor() {
		return author;
	}
	public String getText() {
		return text;
	}
	public String getType() {
		return type;
	}
}

package com.study.springmvc.case03.entity;

public class ExamSubject {

	private String name;
	private String id;
	
	
	public ExamSubject(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public ExamSubject() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ExamSubject [name=" + name + ", id=" + id + "]";
	}
	
	
}

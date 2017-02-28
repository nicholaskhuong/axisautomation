package com.abb.ventyx.utilities.report;

import java.util.ArrayList;

public class TestSuiteResultAdapter {
	private String id = "st0ts0";
	private String suiteName = "";
	private ArrayList<TestClassResultAdapter> classes;
	public TestSuiteResultAdapter() {
		this.classes = new ArrayList<>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	public ArrayList<TestClassResultAdapter> getClasses() {
		return classes;
	}
	public void setClasses(ArrayList<TestClassResultAdapter> classes) {
		this.classes = classes;
	}
	
	public void addClass(TestClassResultAdapter classs){
		this.classes.add(classs);
	}
	
	
	
}

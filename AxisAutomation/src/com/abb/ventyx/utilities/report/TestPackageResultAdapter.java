package com.abb.ventyx.utilities.report;

import java.util.ArrayList;

public class TestPackageResultAdapter {
	private String id = "st0";
	private String packageName = "";
	private ArrayList<TestSuiteResultAdapter> suites;
	public TestPackageResultAdapter() {
		this.suites = new ArrayList<>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public ArrayList<TestSuiteResultAdapter> getSuites() {
		return suites;
	}
	public void setSuites(ArrayList<TestSuiteResultAdapter> suites) {
		this.suites = suites;
	}
	
	public void addSuite(TestSuiteResultAdapter suite){
		this.suites.add(suite);
	}
}

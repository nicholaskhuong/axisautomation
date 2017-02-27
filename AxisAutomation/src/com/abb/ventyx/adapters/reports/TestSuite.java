package com.abb.ventyx.adapters.reports;

import java.util.ArrayList;

public class TestSuite {
	private String id, text, steps, total, pass, fail, time, status, timestamp;
	private ArrayList<TestCase> testcase;
	
	public TestSuite() {
		this.id = "temp";
		this.text = "temp";
		this.total = "0";
		this.pass = "0";
		this.fail = "0";
		this.time = "24.05";
		this.status = "0";
		this.timestamp = "2016-02-01T19:50:12";
		this.testcase = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public ArrayList<TestCase> getTestcase() {
		return testcase;
	}

	public void setTestcase(ArrayList<TestCase> testcase) {
		this.testcase = testcase;
	}
	
	
	

}

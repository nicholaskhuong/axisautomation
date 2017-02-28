package com.abb.ventyx.utilities;

import java.util.ArrayList;

import com.abb.ventyx.utilities.report.TestSuite;

public class Package {
	private String id, text, total, pass, fail, gtime, time, status, timestamp;
	private ArrayList<TestSuite> testsuite;
	public Package() {
		this.id = "st0";
		this.text = "N/A";
		this.total = this.pass = this.fail = this.status = "0";
		this.gtime = this.time = "3.33";
		this.timestamp = "2016-02-01T19:50:12";
		this.testsuite = new ArrayList<>();
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
	public String getGtime() {
		return gtime;
	}
	public void setGtime(String gtime) {
		this.gtime = gtime;
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
	public ArrayList<TestSuite> getTestsuite() {
		return testsuite;
	}
	public void setTestsuite(ArrayList<TestSuite> testsuite) {
		this.testsuite = testsuite;
	}
	
	
	
	
	
}

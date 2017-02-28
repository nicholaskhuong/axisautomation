package com.abb.ventyx.utilities;

import java.util.ArrayList;

public class TestCase {
	private String id, text, status, timestamp, time, total, pass, fail, rpt;
	private Error error = new Error();
	private String output;
	private ArrayList<TestStep> teststep;
	
	public TestCase() {
		this.id = "ID";
		this.text = "test";
		this.status = "1";
		this.timestamp = "2016-02-01T19:50:12";
		this.time = "30.33";
		this.total = "0";
		this.pass = "0";
		this.fail = "0";
		this.rpt = "1";
		this.output = "";
		teststep = new ArrayList<>();
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getRpt() {
		return rpt;
	}

	public void setRpt(String rpt) {
		this.rpt = rpt;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public ArrayList<TestStep> getTeststep() {
		return teststep;
	}

	public void setTeststep(ArrayList<TestStep> teststep) {
		this.teststep = teststep;
	}
	
	
	
}

package com.abb.ventyx.adapters.reports;

import org.testng.ITestResult;

import com.abb.ventyx.adapters.TestResultAdapter;

public class TestStep {
	private String id, text, label, value, actualvalue, action;
	private String status = "0";
	private String screenshot = null;
	public TestStep(String id, String text, String action, String label, String value, String actualvalue, String status,
			String screenShot) {
		this.id = id;
		this.text = text;
		this.action = action;
		this.label = label;
		this.value = value;
		this.actualvalue = actualvalue;
		this.status = status;
		this.screenshot = screenShot;
	}
	
	
	public TestStep() {
		
	}
	
	public TestStep(TestResultAdapter adapter){
		this.id = adapter.getInstanceName() + "." + adapter.getName();
		this.text = adapter.getName();
		this.action = "N/A";
		this.status = adapter.getStatus() + "";
		if(adapter.getStatus()==ITestResult.FAILURE){
			this.screenshot = adapter.getInstanceName() + "_" + adapter.getName() + "_" + adapter.getEndmillis() + ".png";
			this.status = "0";
		}
		else{
			this.screenshot = "";
		}
		this.label = "";
		this.actualvalue = "";
		this.value = "";
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getActualvalue() {
		return actualvalue;
	}
	public void setActualvalue(String acualvalue) {
		this.actualvalue = acualvalue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScreenShot() {
		return screenshot;
	}
	public void setScreenShot(String screenShot) {
		this.screenshot = screenShot;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}
	
	
	
}

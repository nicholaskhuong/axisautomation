package com.abb.ventyx.utilities.report;

import org.testng.ITestResult;

public class TestResultAdapter {
	private long startMillis, endmillis;
	private int status;
	private String host, instanceName, name, testName;
	private Object parameters;
	private String ID;
	
	public TestResultAdapter(ITestResult result){
		this.startMillis = result.getStartMillis();
		this.endmillis = result.getEndMillis();
		this.status = result.getStatus();
		this.host = result.getHost();
		this.instanceName = result.getInstanceName();
		this.name = result.getName();
		this.testName = result.getTestName();
		this.parameters = result.getParameters();
		this.ID = result.getMethod().getId();
	}

	public long getStartMillis() {
		return startMillis;
	}

	public void setStartMillis(long startMillis) {
		this.startMillis = startMillis;
	}

	public long getEndmillis() {
		return endmillis;
	}

	public void setEndmillis(long endmillis) {
		this.endmillis = endmillis;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Object getParameters() {
		return parameters;
	}

	public void setParameters(Object parameters) {
		this.parameters = parameters;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	
}

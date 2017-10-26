package com.abb.ventyx.utilities.report;

import java.io.File;
import java.io.Serializable;
import java.util.regex.Pattern;

import org.testng.ITestResult;

import com.abb.ventyx.utilities.BaseTestCase;

public class TestMethodResultAdapter implements Serializable {
	private String id, text, label, value, actualvalue, action;
	private String status = "0";
	private String screenshot = null;
	private String testClass;
	private String methodName;
	private String testName;
	private String testSuite;
	private String xmlFile;
	private long startTime, endTime;
	private Object[] parameters;
	private String hostName;
	private String almID;

	public TestMethodResultAdapter(String id, String text, String action, String label, String value, String actualvalue, String status,
			String screenShot, String almID) {
		this.id = id;
		this.text = text;
		this.action = action;
		this.label = label;
		this.value = value;
		this.actualvalue = actualvalue;
		this.status = status;
		this.screenshot = screenShot;
		this.almID = almID;
	}

	public TestMethodResultAdapter() {

	}

	public TestMethodResultAdapter(ITestResult testResult, String screenshot, String almID) {
		this.testClass = testResult.getInstanceName();
		this.methodName = testResult.getName();
		this.testName = testResult.getTestContext().getName();
		this.testSuite = testResult.getTestContext().getSuite().getName();
		this.screenshot = testResult.getStatus() == ITestResult.FAILURE ? screenshot : "";
		this.status = testResult.getStatus() == ITestResult.FAILURE ? "0" : testResult.getStatus() + "";
		this.action = "";
		this.id = this.text = this.label = this.action = "";
		this.actualvalue = "";
		this.value = "";
		// this.action = "";
		this.startTime = testResult.getStartMillis();
		this.endTime = testResult.getEndMillis();
		this.parameters = testResult.getParameters();
		this.hostName = testResult.getHost();
		this.almID = almID;
		if (testResult.getStatus() == ITestResult.FAILURE) {
			this.actualvalue = testResult.getThrowable().getMessage();
		}
	}

	public TestMethodResultAdapter(ITestResult testResult, String screenshot, String xmlFile, String almID) {
		this.testClass = testResult.getInstanceName();
		this.methodName = testResult.getName();
		this.testName = testResult.getTestContext().getName();
		this.testSuite = testResult.getTestContext().getSuite().getName();
		// this.screenshot = testResult.getStatus()==ITestResult.FAILURE ?
		// screenshot : "";
		this.screenshot = screenshot.length() == 0
				|| (testResult.getStatus() == ITestResult.SUCCESS && !Boolean
						.valueOf(BaseTestCase.getProperties().getProperty("test.developer.mode"))) ? "" : screenshot; // Need
																														// to
																														// be
																														// fixed.
		this.status = testResult.getStatus() == ITestResult.FAILURE ? "0" : testResult.getStatus() + "";
		this.action = "";
		this.label = this.id = this.text = this.value = this.action = this.actualvalue = "";
		if  (null != testResult.getMethod().getDescription())
		{
				this.label = testResult.getMethod().getDescription();
		}
		this.startTime = testResult.getStartMillis();
		this.endTime = testResult.getEndMillis();
		this.parameters = testResult.getParameters();
		this.hostName = testResult.getHost();
		this.xmlFile = xmlFile;
		this.almID = almID;
		if (testResult.getStatus() == ITestResult.FAILURE) {
			this.actualvalue = testResult.getThrowable().getMessage();
			this.actualvalue = String.format("%s<br/><br/><br/><br/>%s", this.actualvalue, testResult.getThrowable().getStackTrace());
		}
	}

	public String getId() {
		return id;
	}

	public String getXMLFile() {
		return xmlFile;
	}

	public void setXMLFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getPackageName() {
		String[] parts = this.xmlFile.split(Pattern.quote(File.separator));
		return parts[parts.length - 2];
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

	public void setActualvalue(String actualvalue) {
		this.actualvalue = actualvalue;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public String getTestClass() {
		return testClass;
	}

	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(String testSuite) {
		this.testSuite = testSuite;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setALMID(String almID) {
		this.almID = almID;
	}

	public String getALMID() {
		return almID;
	}
}

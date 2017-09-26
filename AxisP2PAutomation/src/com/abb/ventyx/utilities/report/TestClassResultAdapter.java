package com.abb.ventyx.utilities.report;

import java.util.ArrayList;

public class TestClassResultAdapter {
	private String id = "st0ts0tc0";
	private String testClassName = "";
	private String testOriginnalClassName = "";
	private String almID;
	private ArrayList<TestMethodResultAdapter> methods;

	public TestClassResultAdapter() {
		this.methods = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestOriginnalClassName() {
		return testOriginnalClassName;
	}

	public void setTestOriginnalClassName(String testOriginnalClassName) {
		this.testOriginnalClassName = testOriginnalClassName;
	}

	public String getTestClassName() {
		return testClassName;
	}

	public void setTestClassName(String testClassName) {
		this.testClassName = testClassName;
	}

	public ArrayList<TestMethodResultAdapter> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<TestMethodResultAdapter> methods) {
		this.methods = methods;
	}

	public void addMethod(TestMethodResultAdapter method) {
		this.methods.add(method);
	}

	public void setALMID(String almID) {
		this.almID = almID;
	}

	public String getALMID() {
		return almID;
	}

}

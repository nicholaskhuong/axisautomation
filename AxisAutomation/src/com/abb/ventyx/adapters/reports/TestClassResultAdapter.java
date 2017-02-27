package com.abb.ventyx.adapters.reports;

import java.util.ArrayList;

public class TestClassResultAdapter {
	private String id = "st0ts0tc0";
	private String testClassName = "";
	private ArrayList<TestMethodResultAdapter> methods;
	
	public TestClassResultAdapter(){
		this.methods = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	
	public void addMethod(TestMethodResultAdapter method){
		this.methods.add(method);
	}
	
	
}

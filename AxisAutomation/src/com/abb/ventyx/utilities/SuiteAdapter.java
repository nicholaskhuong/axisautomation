package com.abb.ventyx.utilities;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IInvokedMethod;
import org.testng.IObjectFactory;
import org.testng.IObjectFactory2;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestNGListener;
import org.testng.ITestNGMethod;
import org.testng.SuiteRunState;
import org.testng.internal.annotations.IAnnotationFinder;
import org.testng.xml.XmlSuite;

import com.google.gson.Gson;
import com.google.inject.Injector;

public class SuiteAdapter implements ISuite{
	public String toJson() {
	       Gson gson = new Gson();
	       return gson.toJson(this);
	   }
	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ISuiteResult> getResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObjectFactory getObjectFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObjectFactory2 getObjectFactory2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutputDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParallel() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getParentModule() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getGuiceStage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String parameterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Collection<ITestNGMethod>> getMethodsByGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ITestNGMethod> getInvokedMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IInvokedMethod> getAllInvokedMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ITestNGMethod> getExcludedMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuiteRunState getSuiteState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAnnotationFinder getAnnotationFinder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XmlSuite getXmlSuite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(ITestNGListener listener) {
		// TODO Auto-generated method stub
		
	}

	public Injector getParentInjector() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParentInjector(Injector injector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ITestNGMethod> getAllMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

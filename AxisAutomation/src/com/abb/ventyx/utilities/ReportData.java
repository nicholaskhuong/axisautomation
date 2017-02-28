package com.abb.ventyx.utilities;

import java.util.ArrayList;

public class ReportData {
	private String mode, release, version, timestamp, createddate, builddate, host_name;
	private String total, pass, fail, time, gtime, status, rcNodeList, jenkinsURL;
	private String seleniumHubURL, automation_url;
	private ArrayList<Package> package_xyz;
	public ReportData() {
		this.mode = "showstep";
	    this.release = "";
	    this.version = "";
	    this.timestamp = "2016-02-01T19:50:12";
	    this.createddate = "2016-02-02T09:55:10+07:00";
	    this.builddate = "";
	    this.host_name = "";
	    this.total = "1";
	    this.pass = "1";
	    this.fail = "0";        
	    this.time = "398.109";
	    this.gtime = "398.109";
	    this.status = "1";
	    this.rcNodeList = "";
	    this.jenkinsURL = "http://awsjenkins.techops.ventyx.abb.com:8080/job/ellipse_selenium-regression/12/";
	    this.seleniumHubURL = "172.31.180.244";        
	    this.automation_url = "http://ellipseonlineb0-atrel8-epsprd1-eps-prod.techops.ventyx.abb.com:8080/ria/ui.html";
	    this.package_xyz = new ArrayList<>();
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getBuilddate() {
		return builddate;
	}
	public void setBuilddate(String builddate) {
		this.builddate = builddate;
	}
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
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
	public String getGtime() {
		return gtime;
	}
	public void setGtime(String gtime) {
		this.gtime = gtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRcNodeList() {
		return rcNodeList;
	}
	public void setRcNodeList(String rcNodeList) {
		this.rcNodeList = rcNodeList;
	}
	public String getJenkinsURL() {
		return jenkinsURL;
	}
	public void setJenkinsURL(String jenkinsURL) {
		this.jenkinsURL = jenkinsURL;
	}
	public String getSeleniumHubURL() {
		return seleniumHubURL;
	}
	public void setSeleniumHubURL(String seleniumHubURL) {
		this.seleniumHubURL = seleniumHubURL;
	}
	public String getAutomation_url() {
		return automation_url;
	}
	public void setAutomation_url(String automation_url) {
		this.automation_url = automation_url;
	}
	public ArrayList<Package> getPackage_xyz() {
		return package_xyz;
	}
	public void setPackage_xyz(ArrayList<Package> package_xyz) {
		this.package_xyz = package_xyz;
	}
	
	
}

package com.abb.ventyx.axis;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pages.HomePage;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Constants;
import com.abb.ventyx.utilities.DriverCreator;

import com.abb.ventyx.utilities.BaseTestCase;

@Test public class CreateLeaveRequest extends BaseTestCase {

	public void CreateLeaveRequest() throws InterruptedException {
	    
		WebDriver driver= super.driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys("u@example.com");
	    driver.findElement(By.id("loginButton")).click();
	    driver.findElement(By.id("vCode")).clear();
	    driver.findElement(By.id("vCode")).sendKeys("u");
	    driver.findElement(By.id("verifyButton")).click();
	    driver.findElement(By.linkText("LEAVEREQUEST EL8ST (8.8)")).click();
//	    WebElement element = driver.findElement(By.id("LeaveRequest_EL8ST__8_8_"));
//	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//	    Thread.sleep(500); 
//	    element.click();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("ausaenc");
	    Thread.sleep(500); 
	    driver.findElement(By.id("scope")).clear();
	    driver.findElement(By.id("scope")).sendKeys("r100");
	    Thread.sleep(500); 
	    driver.findElement(By.id("position")).clear();
	    driver.findElement(By.id("position")).sendKeys("hrman");
	    Thread.sleep(500); 
	    driver.findElement(By.id("saveBtn")).click();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//	    driver.findElement(By.linkText("New Leave Request")).click();
	    driver.findElement(By.cssSelector("#searchLeaveRequest > span.v-button-wrap > span.v-button-caption")).click();
	    WebElement newLeaveRequest = driver.findElement(By.id("New Leave Request"));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newLeaveRequest);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
//	    newLeaveRequest.click();
	    new Select(driver.findElement(By.cssSelector("select.v-select-select"))).selectByVisibleText("S Sick Leave");
	    new Select(driver.findElement(By.xpath("//div[@id='bookedLeaveCode']/select"))).selectByVisibleText("S Sick Leave");
	    driver.findElement(By.id("modifyAction")).click();
	}
}

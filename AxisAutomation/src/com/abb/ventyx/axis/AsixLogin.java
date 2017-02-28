package com.abb.ventyx.axis;

import org.openqa.selenium.*;

import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Constants;

import org.testng.annotations.Test;

public class AsixLogin extends BaseTestCase {

  @Test
  public void Login() throws Exception {
//	  	WebDriver driver= super.driver;
	  	driver.get(Constants.HOME_URL + "/SupplierPortal/#!listSupplier");
	    driver.findElement(By.id("gwt-uid-7")).clear();
	    driver.findElement(By.id("gwt-uid-7")).sendKeys("1094");
	    driver.findElement(By.id("gwt-uid-9")).clear();
	    driver.findElement(By.id("gwt-uid-9")).sendKeys("Testuser1");
	    driver.findElement(By.id("gwt-uid-22")).clear();
	    driver.findElement(By.xpath("//span[@text='Login']")).click();
	    
	    driver.findElement(By.id("gwt-uid-22")).sendKeys("Test 1");
  }
}

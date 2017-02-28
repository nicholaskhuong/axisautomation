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
//	    driver.findElement(By.xpath("//span[@text='v-button-caption']")).click();
	    WebElement loginButon = driver.findElement(By.cssSelector("div.v-horizontallayout > div.v-expand > div.v-slot > div.v-button-primary > span.v-button-wrap > span.v-button-caption"));
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].click();", loginButon);
	    
	    WebElement continueButton = driver.findElement(By.cssSelector("div.v-horizontallayout > div.v-expand > div.v-align-right > div.v-widget > span.v-button-wrap > span.v-button-caption"));
	    js.executeScript("arguments[0].click();", continueButton);
	    
	    driver.findElement(By.id("gwt-uid-22")).clear();
	    driver.findElement(By.id("gwt-uid-22")).sendKeys("Test 1");
  }
}

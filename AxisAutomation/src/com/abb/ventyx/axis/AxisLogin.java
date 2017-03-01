package com.abb.ventyx.axis;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Constants;

import org.testng.annotations.Test;

public class AxisLogin extends BaseTestCase {

  @Test
  public void Login() throws Exception {
//	  	WebDriver driver= super.driver;
	  	driver.get(Constants.HOME_URL + "/SupplierPortal/#!listSupplier");
	  	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.findElement(By.id("userSeqNo")).clear();
	    driver.findElement(By.id("userSeqNo")).sendKeys("1094");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Testuser1");
	    driver.findElement(By.xpath("//div[@id='signin']")).click();
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    WebElement continueButton = driver.findElement(By.cssSelector("div.v-horizontallayout > div.v-expand > div.v-align-right > div.v-widget > span.v-button-wrap > span.v-button-caption"));
	    
	    js.executeScript("arguments[0].click();", continueButton);
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//	    driver.findElement(By.id("gwt-uid-22")).clear();
//	    driver.findElement(By.id("gwt-uid-22")).sendKeys("Test 1");
  }
}

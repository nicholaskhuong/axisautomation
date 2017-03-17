package com.abb.ventyx.axis.dashboard;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Constants;
import com.abb.ventyx.utilities.Credentials;

import org.testng.annotations.Test;
@ALM(id = "1") 
@Credentials(user = "1094", password = "Testuser1")
public class AxisLogin extends BaseTestCase {

  @Test
  public void Login() throws Exception {
	  	driver.navigate().to(Constants.HOME_URL + "SupplierPortal/#!listSupplier");
//	  	WebElement userSeqNo = (new WebDriverWait(driver, 10))
//	  			.until(ExpectedConditions.presenceOfElementLocated(By.id("userSeqNo")));
//	  	userSeqNo.clear();
//	  	userSeqNo.sendKeys("1094");
//	    driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Testuser1");
//	    driver.findElement(By.xpath("//div[@id='signin']")).click();
//	    JavascriptExecutor js = (JavascriptExecutor)driver;
//	    WebElement continueButton = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("continue")));
//	    js.executeScript("arguments[0].click();", continueButton);
  }
  @Test(dependsOnMethods = "Login")
  public void CreateSupplier() throws Exception {
//	    driver.get(Constants.HOME_URL + "/SupplierPortal/#!CustomerAdminDashboard");
	    WebElement customerConfiguration = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.v-horizontallayout > span.v-menu-item-caption")));
	    customerConfiguration.click();
	    driver.findElement(By.cssSelector("#dashboard-menu-tab-child-layout > div:nth-child(1) > div > span > span")).click();
	    driver.findElement(By.cssSelector("#common-header-right-layout > div > div > span:nth-child(1) > span > span")).click();
	    driver.findElement(By.id("gwt-uid-14")).clear();
	    driver.findElement(By.id("gwt-uid-14")).sendKeys("AT12");
	    driver.findElement(By.id("gwt-uid-22")).clear();
	    driver.findElement(By.id("gwt-uid-22")).sendKeys("Auto Test01");
	    driver.findElement(By.id("gwt-uid-16")).clear();
	    driver.findElement(By.id("gwt-uid-16")).sendKeys("ENC02");
	    driver.findElement(By.id("gwt-uid-24")).clear();
	    driver.findElement(By.id("gwt-uid-24")).sendKeys("auto@email.cm");
	    driver.findElement(By.id("gwt-uid-18")).clear();
	    driver.findElement(By.id("gwt-uid-18")).sendKeys("AUTOTax1");
	    driver.findElement(By.id("gwt-uid-12")).clear();
	    driver.findElement(By.id("gwt-uid-12")).sendKeys("AUTO1234");
	    driver.findElement(By.id("gwt-uid-20")).clear();
	    driver.findElement(By.id("gwt-uid-20")).sendKeys("AUTO1234");
	    driver.findElement(By.cssSelector("#createsupplierwindow > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-mainform-verticallayout > div > div.v-slot.v-slot-v-bottombar-button-layout > div > div > div > div > div:nth-child(3) > div > span > span")).click();
	    
//	    driver.findElement(By.id("gwt-uid-22")).clear();
//	    driver.findElement(By.id("gwt-uid-22")).sendKeys("Test 1");
  }
}

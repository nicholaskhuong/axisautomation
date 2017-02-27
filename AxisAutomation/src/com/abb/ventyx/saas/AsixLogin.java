package com.abb.ventyx.saas;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.abb.ventyx.utilities.BaseTestCase;

public class AsixLogin extends BaseTestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\chromedriver.exe");
	  driver = new ChromeDriver();
    
    baseUrl = "http://172.31.181.109:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSearchTC() throws Exception {
	  driver.get(baseUrl + "/SupplierPortal/#!listSupplier");
	    driver.findElement(By.id("gwt-uid-7")).clear();
	    driver.findElement(By.id("gwt-uid-7")).sendKeys("1094");
	    driver.findElement(By.id("gwt-uid-9")).clear();
	    driver.findElement(By.id("gwt-uid-9")).sendKeys("Testuser1");
	    driver.findElement(By.id("gwt-uid-22")).clear();
	    driver.findElement(By.xpath("//span[@text='Login']")).click();
	    
	    driver.findElement(By.id("gwt-uid-22")).sendKeys("Test 1");
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

package com.abb.ventyx.axis.dashboard;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Constants;
import com.abb.ventyx.utilities.Credentials;

import org.testng.annotations.Test;
@ALM(id = "1") 
@Credentials(user = "1094", password = "Testuser1")
public class Create_Supplier extends BaseTestCase {

  @Test
  public void Login() throws Exception {
	  	driver.navigate().to(getServerURL() + "/SupplierPortal/#!CustomerAdminDashboard");
  }
  @Test(dependsOnMethods = "Login")
  public void CreateSupplier() throws Exception {
	  	JavascriptExecutor js = (JavascriptExecutor)driver;
	    WebElement customerConfiguration = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#custConfigParentButton > span > span.valo-menu-item-caption > div > span.v-menu-item-caption")));
	    customerConfiguration.click();
	    driver.findElement(By.cssSelector("#supplierMenu > span > span")).click();
	    WebElement addSupplier = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#HeaderMenuBar > span:nth-child(1) > span > span")));
	    js.executeScript("arguments[0].click();", addSupplier);
	    driver.findElement(By.id("spTpId")).clear();
	    driver.findElement(By.id("spTpId")).sendKeys("AT12");
	    driver.findElement(By.id("psuName")).clear();
	    driver.findElement(By.id("psuName")).sendKeys("Auto Test01");
	    driver.findElement(By.id("psuCoRegnNo")).clear();
	    driver.findElement(By.id("psuCoRegnNo")).sendKeys("ENC02");
	    driver.findElement(By.id("emailAddress")).clear();
	    driver.findElement(By.id("emailAddress")).sendKeys("auto@email.cm");
	    driver.findElement(By.id("psuTaxRegnNo")).clear();
	    driver.findElement(By.id("psuTaxRegnNo")).sendKeys("AUTOTax1");
	    driver.findElement(By.id("lngSeqId")).clear();
	    driver.findElement(By.id("lngSeqId")).sendKeys("AUTO1234");
	    WebElement createEditBtn = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("createEditBtn")));
	    js.executeScript("arguments[0].click();", createEditBtn);
	    
  }
}

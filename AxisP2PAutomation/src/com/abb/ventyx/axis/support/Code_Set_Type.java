package com.abb.ventyx.axis.support;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static org.junit.Assert.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import com.abb.ventyx.axis.objects.pages.HomePage;
import com.abb.ventyx.axis.objects.pages.UserApplicationPage;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Constants;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.MaintainCodeSetType;
import com.ventyx.testng.TestDataKey;

public class Code_Set_Type extends BaseTestCase {
	@TestDataKey private final String CODE_TYPE_A = "A_CODE_TYPE";
	@TestDataKey private final String CODE_TYPE_B = "AA_CODE_TYPE";
	@TestDataKey private final String CODE_DESC = "A_CODE_DESC";
	@Test
	  public void login() throws Exception {
		  	driver.get(Constants.HOME_URL + "/SupplierPortal/#!listSupplier");
		  	WebElement userSeqNo = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id("userSeqNo")));
		  	userSeqNo.clear();
		  	userSeqNo.sendKeys("5");
		    driver.findElement(By.id("password")).click();
		    driver.findElement(By.cssSelector("input[type='password']")).sendKeys("testuser");
		    driver.findElement(By.xpath("//div[@id='signInBtn']")).click();
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    WebElement continueButton = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#SupplierPortal-1227837064 > div > div.v-verticallayout.v-layout.v-vertical.v-widget.v-has-width.v-has-height > div > div > div > div:nth-child(3) > div > div:nth-child(3) > div > div > div > div")));
		    js.executeScript("arguments[0].click();", continueButton);
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		   		    
		    driver.findElement(By.cssSelector(AxisConfigMenu.CODE_SET_TYPE)).click();
		    driver.findElement(By.cssSelector(MaintainCodeSetType.ADD)).click();
		    //CODE TYPE field ID is dynamic, failed.
		    driver.findElement(By.id(MaintainCodeSetType.CODE_TYPE_FIELD)).click();
		    driver.findElement(By.id(MaintainCodeSetType.CODE_TYPE_FIELD)).sendKeys(CODE_TYPE_A);
		    driver.findElement(By.id(MaintainCodeSetType.CODE_DESC_FIELD)).clear();
		    driver.findElement(By.id(MaintainCodeSetType.CODE_DESC_FIELD)).sendKeys(CODE_DESC);
		    driver.findElement(By.id(MaintainCodeSetType.SAVE));
		    assertEquals(driver.findElement(By.cssSelector(MaintainCodeSetType.CODE_TYPE_i)).getText(),CODE_TYPE_A);
		   
		    
		    driver.findElement(By.cssSelector(MaintainCodeSetType.ADD)).click();
		    driver.findElement(By.id(MaintainCodeSetType.CODE_TYPE_FIELD)).click();
		    driver.findElement(By.id(MaintainCodeSetType.CODE_TYPE_FIELD)).sendKeys(CODE_TYPE_B);
		    driver.findElement(By.cssSelector(MaintainCodeSetType.CANCEL)).click();
		    assertEquals(driver.findElement(By.cssSelector(MaintainCodeSetType.COMFIRMATION_WINDOW)).getText(),Messages.UNSAVED_CHANGE);
		    driver.findElement(By.id(MaintainCodeSetType.YES));
		    assertEquals(driver.findElement(By.cssSelector(MaintainCodeSetType.CODE_TYPE_i)).getText(),CODE_TYPE_A);
		    
		   }	    
}




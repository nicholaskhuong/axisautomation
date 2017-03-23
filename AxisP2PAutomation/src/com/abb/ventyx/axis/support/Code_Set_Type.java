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
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.MaintainCodeSetType;

public class Code_Set_Type extends BaseTestCase {
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
		    driver.findElement(By.cssSelector(MaintainCodeSetType.ADD_NEW)).click();
		    driver.findElement(By.id(MaintainCodeSetType.CODE_TYPE_FIELD)).sendKeys("TAXCODE2017");
		    
		    
		    
		    driver.findElement(By.cssSelector("#resourceTypeCB > div")).click();
		    driver.findElement(By.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table > tbody > tr:nth-child(2) > td > span")).click();
		    driver.findElement(By.id("backBtn")).click();
		    assertEquals(driver.findElement(By.cssSelector("#confirmation-window > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-confirmation-window-text-and-button-layout > div > div:nth-child(1) > div")).getText(), Messages.UNSAVED_CHANGE);
		    driver.findElement(By.cssSelector("#confirmation-window > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-confirmation-window-text-and-button-layout > div > div.v-slot.v-slot-v-bottombar-button-layout.v-align-right.v-align-middle > div > div:nth-child(3) > div")).click();
		    
//		    WebElement scroll = driver.findElement(By.cssSelector("#content-component > div > div.v-panel-content.v-panel-content-borderless.v-panel-content-v-common-page-panel.v-scrollable > div > div > div.v-slot.v-slot-v-common-page-content-layout > div > div > div > div > div > div:nth-child(1) > div > div > div > div > div.v-grid-horizontal-scrollbar-deco"));
//		    JavascriptExecutor js2 = (JavascriptExecutor)driver; 
		  //  js2.executeScript(driver.execute_script("arguments[0].scrollIntoView()", scroll));
		    WebElement scroll = driver.findElement(By.cssSelector("#content-component > div > div.v-panel-content.v-panel-content-borderless.v-panel-content-v-common-page-panel.v-scrollable > div > div > div.v-slot.v-slot-v-common-page-content-layout > div > div > div > div > div > div:nth-child(1) > div > div > div > div > div.v-grid-scroller.v-grid-scroller-horizontal"));
		    JavascriptExecutor js2 = (JavascriptExecutor)driver; 
		    js2.executeScript("arguments[0].scrollIntoView()", scroll);
		    
		    		    
		    driver.findElement(By.id("deleteItemBtn0")).click();
		    assertThat(driver.findElement(By.cssSelector("#confirmation-window > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-confirmation-window-text-and-button-layout > div > div:nth-child(1) > div")).getText(), containsString(Messages.FOP_Resrc_Delete_Confirm));
		     }
		    
		    
		/*    
		    
		    
		    
		    //------------
		    driver.findElement(By.cssSelector("#uploader > div > div")).sendKeys("AT12");
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
		    */
	
}




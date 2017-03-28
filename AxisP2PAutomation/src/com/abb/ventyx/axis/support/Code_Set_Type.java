package com.abb.ventyx.axis.support;

import static org.testng.AssertJUnit.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.MaintainCodeSetType;

public class Code_Set_Type extends BaseTestCase {
	private final String CODE_TYPE_A = "A_CODE_TYPE";
	private final String CODE_TYPE_B = "AA_CODE_TYPE";
	private final String CODE_DESC = "A_CODE_DESC";
	
	@Test  
	public void login() {
			driver.navigate().to(getServerURL() + "/SupplierPortal/#!CustomerAdminDashboard");
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




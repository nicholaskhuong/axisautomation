package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.ventyx.testng.TestDataKey;

@ALM(id = "2") 
@Credentials(user = "5", password = "testuser")
public class Permissions_Updating extends BaseTestCase {
	
	@TestDataKey private final String PERMISSION_NAME_A = "Export PO ABB";
	@TestDataKey private final String PERMISSION_NAME_B = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String DOCUMENT_TYPE_A = "POAck";
	@TestDataKey private final String DOCUMENT_TYPE_B = "SYSTEM";
	@TestDataKey private final String USER_TYPE_A = "CSA";
	@TestDataKey private final String USER_TYPE_B = "A";
	
	@Test
	  public void login() throws Exception {
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		 // Choose one Permission to be updated, assert all the given details of Permission. 		    
		    driver.findElement(By.cssSelector(AxisConfigMenu.PERMISSIONS)).click();
		    driver.findElement(By.xpath(Permissions.PERMISSION_NAME_FILTER)).clear();
		    driver.findElement(By.xpath(Permissions.PERMISSION_NAME_FILTER)).sendKeys(PERMISSION_NAME_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(),PERMISSION_NAME_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(),USER_TYPE_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(),DOCUMENT_TYPE_A);
		 
		 //Update the Permission with Document Type and User Type.
		    driver.findElement(By.id(Permissions.ROW0)).click();
		    driver.findElement(By.id(Permissions.DOCUMENT_TYPE)).click();
		    driver.findElement(By.cssSelector(Permissions.SYSTEM)).click();
		    driver.findElement(By.cssSelector(Permissions. CUSTOMER)).click();
		    driver.findElement(By.cssSelector(Permissions. SUPPLIER)).click();
		    driver.findElement(By.id(Permissions.SAVE)).click();
		 
		 //Determine the updated details on the table.
		    driver.findElement(By.xpath(Permissions.PERMISSION_NAME_FILTER)).clear();
		    driver.findElement(By.xpath(Permissions.PERMISSION_NAME_FILTER)).sendKeys(PERMISSION_NAME_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(),PERMISSION_NAME_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(),USER_TYPE_B);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(),DOCUMENT_TYPE_B);
		 
		 //Return the updated details of Permission to its original value for the next time of test.
		    driver.findElement(By.id(Permissions.ROW0)).click();
		    driver.findElement(By.id(Permissions.DOCUMENT_TYPE)).click();
		    driver.findElement(By.cssSelector(Permissions.PURCHASE_ORDERS)).click();
		    driver.findElement(By.cssSelector(Permissions. CUSTOMER)).click();
		    driver.findElement(By.cssSelector(Permissions. SUPPLIER)).click();
		    driver.findElement(By.id(Permissions.SAVE)).click();
		    
	}	    
}




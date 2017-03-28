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

@ALM(id = "1") 
@Credentials(user = "5", password = "testuser")
public class Permissions_Creating extends BaseTestCase {
	@TestDataKey private final String PERMISSION_NAME_A = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String PERMISSION_NAME_B = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String DOCUMENT_TYPE_A = "POAck";
	@TestDataKey private final String USER_TYPE_A = "A";
	@Test
	  public void login() throws Exception {
		   // Create Permission 
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		   		    
		    driver.findElement(By.cssSelector(AxisConfigMenu.PERMISSIONS)).click();
		    driver.findElement(By.cssSelector(Permissions.ADD)).click();
		    
		    driver.findElement(By.id(Permissions.PERMISSION_NAME)).click();
		    driver.findElement(By.id(Permissions.PERMISSION_NAME)).sendKeys(PERMISSION_NAME_A);
		    driver.findElement(By.id(Permissions.DOCUMENT_TYPE)).click();
		    driver.findElement(By.cssSelector(Permissions.PURCHASE_ORDER_ACK)).click();
		    driver.findElement(By.cssSelector(Permissions. AXIS_ADMIN)).click();
		    driver.findElement(By.id(Permissions.SAVE)).click();
		    driver.findElement(By.xpath(Permissions.PERMISSION_NAME_FILTER)).clear();
		    driver.findElement(By.xpath(Permissions.PERMISSION_NAME_FILTER)).sendKeys(PERMISSION_NAME_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(),PERMISSION_NAME_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(),USER_TYPE_A);
		    assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(),DOCUMENT_TYPE_A);
		   }	    
}




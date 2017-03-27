package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.ventyx.testng.TestDataKey;

@ALM(id = "3") 
@Credentials(user = "5", password = "testuser")
public class Permissions_Deleting extends BaseTestCase {
	@TestDataKey private final String PERMISSION_NAME_A = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String PERMISSION_NAME_B = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String DOCUMENT_TYPE_A = "POAck";
	@TestDataKey private final String USER_TYPE_A = "A";
	@Test
	  public void login() throws Exception {
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		    driver.findElement(By.cssSelector(AxisConfigMenu.PERMISSIONS)).click();
		    final String PERMISION_ID_A = driver.findElement(By.id(Permissions.ROW1)).getText();
		    driver.findElement(By.id(Permissions.DELETE1)).click();
		    driver.findElement(By.cssSelector(Permissions.DELETE_YES)).click();
		    final String PERMISION_ID_B = driver.findElement(By.id(Permissions.ROW1)).getText();
		    assertThat(PERMISION_ID_B, is(not(PERMISION_ID_A)));	
		  	    
  }
}

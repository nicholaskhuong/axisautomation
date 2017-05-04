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
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;


@ALM(id = "156") 
@Credentials(user = "5", password = "testuser")
public class Permissions_Deleting extends BaseTestCase {

	@Test
	  public void login() throws Exception {
		
		  // Delete Permission on the 2nd row.
			WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		    driver.findElement(By.cssSelector(AxisConfigMenu.PERMISSIONS)).click();
		    final String PERMISION_ID_A = driver.findElement(By.id(Permissions.ROW1)).getText();
		    driver.findElement(By.id(Permissions.DELETE1)).click();
		  
		  //Make sure this is a Confirmation of deleting process
		    assertThat(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(),containsString(Messages.DELETE_CONFIRM));
		    driver.findElement(By.cssSelector(Permissions.DELETE_YES)).click();
		    final String PERMISION_ID_B = driver.findElement(By.id(Permissions.ROW1)).getText();
		   
	      //Indicate 1st row's value is now different from the original one.
		    assertThat(PERMISION_ID_B, is(not(PERMISION_ID_A)));	
		  	    
  }
}

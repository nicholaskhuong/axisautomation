package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.ventyx.testng.TestDataKey;

@ALM(id = "1") 
@Credentials(user = "5", password = "testuser")
public class Document_Type_Deleting extends BaseTestCase {
	@TestDataKey private final String DOCTYPE_B = "ABB";
	@TestDataKey private final String DESC_B = "AAA_MAINTAIN_DOCTYPES";
	@Test
	  public void login() throws Exception {
		 
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		  
		   //Delete first instance on grid.
		    driver.findElement(By.cssSelector(AxisConfigMenu.DOC_TYPE)).click();
		    final String DOCTYPE_A = driver.findElement(By.id(DocType.DTROW1)).getText();
		    final String DESC_A = driver.findElement(By.cssSelector(DocType.DROW1)).getText();
		    driver.findElement(By.id(DocType.DELETE_ROW1)).click();
		    assertThat(driver.findElement(By.cssSelector(DocType.CONFIRMATION_OF_DELETION)).getText(),containsString(Messages.Delete_Confirm));
		    driver.findElement(By.cssSelector(DocType.DELETE_YES)).click();
		  
		   //Indicate 1st row's value is now different from the original one as the original one already deleted.
		    assertThat(driver.findElement(By.id(DocType.DTROW1)).getText(), is(not(DOCTYPE_A)));
		    assertThat(driver.findElement(By.cssSelector(DocType.DROW1)).getText(), is(not(DESC_A)));
		  
   }	    
}

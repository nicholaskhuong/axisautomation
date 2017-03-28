package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.ventyx.testng.TestDataKey;

@ALM(id = "159") 
@Credentials(user = "5", password = "testuser")
public class Document_Type_Editing extends BaseTestCase {
	@TestDataKey private final String DOCTYPE_B = "ABB";
	@TestDataKey private final String DESC_B = "AAA_MAINTAIN_DOCTYPES";
	@Test
	  public void login() throws Exception {
		
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		  
		    // Update first instance on grid.
		    driver.findElement(By.cssSelector(AxisConfigMenu.DOC_TYPE)).click();
		    final String DOCTYPE_A = driver.findElement(By.id(DocType.DTROW1)).getText();
		    final String DESC_A = driver.findElement(By.cssSelector(DocType.DROW1)).getText();
		    driver.findElement(By.id(DocType.DTROW1)).click();
		    driver.findElement(By.id(DocType.DESC)).clear();
		    driver.findElement(By.id(DocType.DESC)).sendKeys(DESC_B);
		    driver.findElement(By.id(DocType.SAVE)).click();
		    final String DOCTYPE_C = driver.findElement(By.id(DocType.DTROW1)).getText();
		    final String DESC_C = driver.findElement(By.cssSelector(DocType.DROW1)).getText();
		 
		   //Compare Values before and after updating.
		    assertEquals(DOCTYPE_C, DOCTYPE_A);
		    assertThat(DESC_C, is(not(DESC_A)));
		    assertEquals(DESC_C, DESC_B);
		   			    
		   //Return Document type to its original value.
		    driver.findElement(By.id(DocType.DTROW1)).click();
		    driver.findElement(By.id(DocType.DESC)).clear();
		    driver.findElement(By.id(DocType.DESC)).sendKeys(DESC_A);
		    driver.findElement(By.id(DocType.SAVE)).click();
		    
   }	    
}

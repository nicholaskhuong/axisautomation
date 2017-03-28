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

@ALM(id = "1") 
@Credentials(user = "5", password = "testuser")
public class Document_Type_Creating extends BaseTestCase {
	@TestDataKey private final String DOCTYPE_B = "ABB";
	@TestDataKey private final String DESC_B = "AA_MAINTAIN_DOCTYPES";
	@Test
	  public void login() throws Exception {
		   // Create Permission 
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		    
		  //Document Types is unique.
		    driver.findElement(By.cssSelector(AxisConfigMenu.DOC_TYPE)).click();
		    final String DOCTYPE_A = driver.findElement(By.id(DocType.DTROW1)).getText();
		    final String DESC_A = driver.findElement(By.cssSelector(DocType.DROW1)).getText();
		    driver.findElement(By.cssSelector(DocType.ADD)).click();
		    driver.findElement(By.id(DocType.DOCTYPES)).click();
		    driver.findElement(By.id(DocType.DOCTYPES)).sendKeys(DOCTYPE_B);
		    driver.findElement(By.id(DocType.DESC)).click();
		    driver.findElement(By.id(DocType.DESC)).sendKeys(DESC_A);
		    driver.findElement(By.id(DocType.SAVE)).click();
		    final String DOCTYPE_C = driver.findElement(By.id(DocType.DTROW1)).getText();
		    final String DESC_C = driver.findElement(By.id(DocType.DROW1)).getText();
		    assertThat(DOCTYPE_C, is(not(DOCTYPE_A)));
		    assertThat(DESC_C, is(not(DESC_A)));
		   			    
   }	    
}





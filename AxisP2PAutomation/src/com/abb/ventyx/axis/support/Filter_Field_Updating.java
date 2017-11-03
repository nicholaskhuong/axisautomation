package com.abb.ventyx.axis.support;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.FilterField;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.ventyx.testng.TestDataKey;
@ALM(id = "158") 
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Filter_Field_Updating extends BaseTestCase {
	@TestDataKey private final String FIELD_NAME = "Test 2017";
	@TestDataKey private final String FIELD_NAME_UPDATE = "Update";
	String DOCTYPE_A;
	String DESC_A;
	BaseDropDownList list;
	int row;
	BaseGrid grid;
	@Test
	public void clickFilterField(){
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
	    axisConfigParentButton.click();
	    WebElement axisFilterConfig = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.FILTER_CONFIG)));
	    axisFilterConfig.click();
	    WebElement axisFilterField = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.FILTER_FIELD)));
	    axisFilterField.click();
	}
 @Test(dependsOnMethods = "clickFilterField")
	public void updateFilterField (){   
		    // Step 1 Update the record from Add New step.
		    grid = new BaseGrid(driver, FilterField.GRID);
		    row = grid.findItemByColumnName("Field Name", FIELD_NAME);
		    Assert.assertNotEquals(row, -1, "Record not found");
		    driver.findElement(By.id("docTypeBtn" + (row-1))).click();
		    
		    WebElement desc = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.FIELD_NAME)));
		    desc.clear();
		    desc.sendKeys(FIELD_NAME_UPDATE);
		    WebElement save = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.ADD_SAVE)));
		    save.click();
 }
@Test(dependsOnMethods = "updateFilterField")
public void catchSuccessMessage (){  

		    WebElement flashMessage = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE)));
		    Assert.assertEquals(driver.findElement(By.cssSelector(DocType.POPUP_SUCCESS)).getCssValue("background-color"), "rgba(33, 190, 137, 1)");
		    Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
		    Assert.assertEquals(flashMessage.getCssValue("display"), "inline-block");
		    Assert.assertEquals(flashMessage.getText(), Messages.DOCUMENT_FILTER_FIELD_SUCCESSFULLY_UPDATED);
	}
@Test(dependsOnMethods = "catchSuccessMessage")
			public void returnPermissionToOriginalValue (){
		    grid = new BaseGrid(driver, Permissions.GRID); 
		    row = grid.findItemByColumnName("Field Name", FIELD_NAME_UPDATE);
		    driver.findElement(By.id("docTypeBtn" + (row-1))).click();
		    WebElement fieldName = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.FIELD_NAME)));
		    fieldName.clear();
		    fieldName.sendKeys(FIELD_NAME);
		    driver.findElement(By.id(FilterField.ADD_SAVE)).click();
	}	    
}




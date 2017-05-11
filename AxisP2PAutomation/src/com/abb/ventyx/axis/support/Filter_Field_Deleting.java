package com.abb.ventyx.axis.support;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import com.ventyx.testng.TestDataKey;
@ALM(id = "166") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Filter_Field_Deleting extends BaseTestCase {
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
	    
	    
	    // Step 1 Update the record from Add New step.
	    grid = new BaseGrid(driver, FilterField.GRID);
	    WebElement wait = (new WebDriverWait(driver, 80))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.FILTER_CONFIG)));
	    row = grid.findItemByColumnName("Field Name", FIELD_NAME);
	    Assert.assertNotEquals(row, -1, "Record not found");
		
	    WebElement horizontal_scroll = driver.findElement(By.xpath("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div[2]"));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", horizontal_scroll);
	    driver.findElement(By.id("deleteItemBtn" + (row-1))).click();
	}    
	 @Test(dependsOnMethods = "clickFilterField")
	 public void catchSuccessMessage (){  

		 WebElement delete_Confirm = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.CONFIRMATION)));
		 assertThat(delete_Confirm.getText(),containsString(Messages.DELETE_CONFIRM_FILTER_FIELD));
		 WebElement delete_Yes = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(Messages.YES_BTN_ID)));
		 delete_Yes.click();
		 WebElement flashMessage = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.SUCCESS_MESSAGE)));
		 WebElement popup =driver.findElement(By.cssSelector(DocType.POPUP_SUCCESS));
		 Assert.assertEquals(popup.getCssValue("background-color"), "rgba(33, 190, 137, 1)");
		 Assert.assertEquals(popup.getCssValue("display"), "block");
		 Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
		 Assert.assertEquals(flashMessage.getCssValue("display"), "inline-block");
		 Assert.assertEquals(flashMessage.getText(), Messages.DEL_FILTER_FIELD_SUCCESS);
		 Assert.assertNotEquals(row, -1, "Record not deleted yet");
	 }
	    
	
}

package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.ventyx.testng.TestDataKey;

@ALM(id = "160") 
@Credentials(user = "5", password = "testuser")
public class Document_Type_Deleting extends BaseTestCase {
	@TestDataKey private final String DOCTYPE_B = "Abb";
	@TestDataKey private final String DESC_B = "AAA_MAINTAIN_DOCTYPES";
	int row;
	BaseGrid grid;
	@Test
	  public void Delete_Document_Type_From_Grid() throws Exception {
		 
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		    WebElement axisDocType = (new WebDriverWait(driver, 20))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.DOC_TYPE)));
		    axisDocType.click();
		    
		   //Step 1 Delete the record from Add New Doc Type Step.
		    grid = new BaseGrid(driver, DocType.GRID);
		    row = grid.findItemByColumnName("Document Types", DOCTYPE_B);
		    Assert.assertNotEquals(row, -1, "Record to be deleted not found");
		    driver.findElement(By.id("deleteItemBtn"+ (row-1))).click();
		    WebElement delete_Confirm = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.CONFIRMATION)));
		    assertThat(delete_Confirm.getText(),containsString(Messages.DEL_CONFIRM));
		    WebElement delete_Yes = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.DELETE_YES)));
		    delete_Yes.click();
		    WebElement flashMessage = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE)));
		    WebElement popup =driver.findElement(By.cssSelector(DocType.POPUP_SUCCESS));
		    Assert.assertEquals(popup.getCssValue("background-color"), "rgba(33, 190, 137, 1)");
		    Assert.assertEquals(popup.getCssValue("display"), "block");
		    Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
		    Assert.assertEquals(flashMessage.getCssValue("display"), "inline-block");
		    Assert.assertEquals(flashMessage.getText(), Messages.DOCUMENT_DELETE_SUCCESSFULLY);
		    Assert.assertNotEquals(row, -1, "Record not deleted yet");
		    
   }	    
}

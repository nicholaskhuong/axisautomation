package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
	String DOCTYPE_A ;
	String DESC_A;
	int row;
	BaseGrid grid;
	@Test
	  public void Delete_Document_Type_From_Grid() throws Exception {
		 
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		  
		   //Delete first instance on grid.
		    driver.findElement(By.cssSelector(AxisConfigMenu.DOC_TYPE)).click();
		    grid = new BaseGrid(driver, DocType.GRID);
		    row = grid.findItemByColumnName("Document Types", DOCTYPE_B);
		    DOCTYPE_A =  grid.getGridCellByColumnName("Document Types",row);
		    DESC_A =   grid.getGridCellByColumnName("Description",row);
		    driver.findElement(By.id("deleteItemBtn"+ (row-1))).click();
		    assertThat(driver.findElement(By.cssSelector(DocType.CONFIRMATION_OF_DELETION)).getText(),containsString(Messages.Delete_Confirm));
		    WebElement delete_Yes = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.DELETE_YES)));
		    delete_Yes.click();
		    WebElement flashMessage = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE)));
		    Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
		    Assert.assertEquals(flashMessage.getCssValue("display"), "inline-block");
		    Assert.assertEquals(flashMessage.getCssValue("color"), "rgba(255, 255, 255, 1)");
		    Assert.assertEquals(flashMessage.getText(), Messages.DOCUMENT_DELETE_SUCCESSFULLY);
	}
	@Test(dependsOnMethods ="Delete_Document_Type_From_Grid" )
	  public void Check_Successful_Message() throws Exception {
		   //Indicate 1st row's value is now different from the original one as the original one already deleted.
			grid = new BaseGrid(driver, DocType.GRID);
		    assertThat(grid.getGridCellByColumnName("Document Types",row), is(not(DOCTYPE_A)));
		    assertThat(grid.getGridCellByColumnName("Description",row), is(not(DESC_A)));
		  
   }	    
}

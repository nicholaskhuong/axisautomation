package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DialogBtns;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "159")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Document_Type_Editing extends BaseTestCase {

	String DESC_B = "UPDATED SECOND TIME";
	String DESC_A = "AA_MAINTAIN_DOCTYPES";
	BaseGrid grid;
	int row;
	TableFunction table;
	ScreenAction action;

	// Step 1 click 1st document Abb
	@Test
	public void clickDocumentType() {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();

		WebElement axisDocType = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(AxisConfigMenu.DOC_TYPE)));
		axisDocType.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		table.clickDocType(DocType.GRID, Document_Type_Creating.DOCTYPE_B);
		action.pause(1000);
	}

	// Step 2 Update description of document type
	@Test(dependsOnMethods = "clickDocumentType")
	public void updateDescription() {
		Assert.assertEquals(driver.findElement(By.id(DocType.DOCTYPES)).isEnabled(), false);
		action.pause(500);
		WebElement desc = driver.findElement(By.id(DocType.DESC));
		desc.clear();
		action.pause(500);
		desc.sendKeys(DESC_B);
		action.pause(500);
		WebElement save = driver.findElement(By.id(DocType.SAVE));
		save.click();
		action.pause(2000);
		WebElement flashMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(DocType.SUCCESS_MESSAGE)));
		Assert.assertEquals(flashMessage.getText(), Messages.DOCUMENT_UPDATED_SUCCESSFULLY);

		row = table.findRowByString(1, Document_Type_Creating.DOCTYPE_B);

		grid = new BaseGrid(driver, DocType.GRID);
		final String DOCTYPE_C = grid.getGridCellByColumnName("Document Types", row);
		final String DESC_C = grid.getGridCellByColumnName("Description", row);

		Assert.assertEquals(DOCTYPE_C, Document_Type_Creating.DOCTYPE_B);
		Assert.assertEquals(DESC_C, DESC_B);
	}

	// Step 3,4,5,6 Click 1st document Abb
	@Test(dependsOnMethods = "updateDescription")
	public void clickDocumentType2nd() {
		// Step 3 Return Document type to its original value.
		// driver.findElement(By.id("docTypeBtn" + (row - 1))).click();
		table.clickDocType(DocType.GRID, Document_Type_Creating.DOCTYPE_B);
		action.pause(3000);
		//action.assertTitleScreen("Edit Document Types");


		WebElement desc2 = driver.findElement(By.id(DocType.DESC));
		desc2.clear();
		action.pause(300);
		desc2.sendKeys(DESC_A);
		driver.findElement(By.id(DocType.CANCEL)).click();
		action.pause(300);
		action.waitObjVisible(By.cssSelector(DocType.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(DocType.CONFIRMATION)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(DialogBtns.NO)).click();
		action.pause(500);

		driver.findElement(By.id(DocType.SAVE)).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisible(By.cssSelector(DocType.SUCCESS_MESSAGE));
		action.pause(2000);
		
		assertEquals(driver.findElement(By.cssSelector(DocType.DOCUMENTTYPE_HEADER)).getText(), "Maintain Document Types");

		WebElement flashMessage2 = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE))); 
		Assert.assertEquals(driver.findElement(By.cssSelector(DocType.POPUP_SUCCESS)).getCssValue("background-color"), "rgba(33, 190, 137, 1)");

		Assert.assertEquals(flashMessage2.getCssValue("visibility"), "visible"); Assert.assertEquals(flashMessage2.getCssValue("display"),
										"inline-block"); Assert.assertEquals(flashMessage2.getText(),
												Messages.DOCUMENT_UPDATED_SUCCESSFULLY);

	}
}

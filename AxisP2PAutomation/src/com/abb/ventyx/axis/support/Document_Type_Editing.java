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
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.TableFunction;
import com.ventyx.testng.TestDataKey;

@ALM(id = "159")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Document_Type_Editing extends BaseTestCase {
	@TestDataKey
	private final String DOCTYPE_B = "Abb";
	@TestDataKey
	private final String DESC_B = "UPDATED SECOND TIME";
	@TestDataKey
	private final String DESC_A = "AA_MAINTAIN_DOCTYPES";
	BaseGrid grid;
	int row = 1;

	// Step 1 click 1st document Abb
	@Test
	public void clickDocumentType() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		TableFunction tablefunction = new TableFunction(driver);

		WebElement axisConfigParentButton = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();

		WebElement axisDocType = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(AxisConfigMenu.DOC_TYPE)));
		axisDocType.click();
		Thread.sleep(1000);

		tablefunction.clickDocType(DocType.GRID, DOCTYPE_B);
		Thread.sleep(1000);
	}

	// Step 2 Update description of document type
	@Test(dependsOnMethods = "clickDocumentType")
	public void updateDescription() throws Exception {
		Assert.assertEquals(driver.findElement(By.id(DocType.DOCTYPES)).isEnabled(), false);
		Thread.sleep(200);
		WebElement desc = driver.findElement(By.id(DocType.DESC));
		desc.clear();
		Thread.sleep(300);
		desc.sendKeys(DESC_B);
		Thread.sleep(400);
		WebElement save = driver.findElement(By.id(DocType.SAVE));
		save.click();
		Thread.sleep(2000);
		WebElement flashMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(DocType.SUCCESS_MESSAGE)));
		Assert.assertEquals(flashMessage.getText(), Messages.DOCUMENT_UPDATED_SUCCESSFULLY);

		grid = new BaseGrid(driver, DocType.GRID);
		final String DOCTYPE_C = grid.getGridCellByColumnName("Document Types", row);
		final String DESC_C = grid.getGridCellByColumnName("Description", row);

		Assert.assertEquals(DOCTYPE_C, DOCTYPE_B);
		Assert.assertEquals(DESC_C, DESC_B);
	}

	// Step 3,4,5,6 Click 1st document Abb
	@Test(dependsOnMethods = "updateDescription")
	public void clickDocumentType1() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		// Step 3 Return Document type to its original value.
		// driver.findElement(By.id("docTypeBtn" + (row - 1))).click();
		TableFunction tablefunction = new TableFunction(driver);
		tablefunction.clickDocType(DocType.GRID, DOCTYPE_B);
		/*
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .cssSelector(DocType.DOCUMENTTYPE_HEADER))); Thread.sleep(2000);
		 * assertEquals
		 * (driver.findElement(By.cssSelector(DocType.DOCUMENTTYPE_HEADER
		 * )).getText(), "Edit Document Types");
		 */
		Thread.sleep(3000);
		WebElement desc2 = driver.findElement(By.id(DocType.DESC));
		desc2.clear();
		Thread.sleep(300);
		desc2.sendKeys(DESC_A);
		driver.findElement(By.id(DocType.CANCEL)).click();
		Thread.sleep(500);
		assertEquals(driver.findElement(By.cssSelector(DocType.CONFIRMATION)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(DialogBtns.NO)).click();
		Thread.sleep(500);
		// assertEquals(driver.findElement(By.cssSelector(DocType.DOCUMENTTYPE_HEADER)).getText(),
		// "Edit Document Types");
		driver.findElement(By.id(DocType.SAVE)).click();
		Thread.sleep(500);
		assertEquals(driver.findElement(By.cssSelector(DocType.DOCUMENTTYPE_HEADER)).getText(), "Maintain Document Types");
		/*
		 * WebElement flashMessage2 = (new WebDriverWait(driver, 10))
		 * .until(ExpectedConditions.presenceOfElementLocated(By
		 * .cssSelector(DocType.SUCCESS_MESSAGE))); Assert.assertEquals(
		 * driver.findElement(By.cssSelector(DocType.POPUP_SUCCESS))
		 * .getCssValue("background-color"), "rgba(33, 190, 137, 1)");
		 * Assert.assertEquals(flashMessage2.getCssValue("visibility"),
		 * "visible"); Assert.assertEquals(flashMessage2.getCssValue("display"),
		 * "inline-block"); Assert.assertEquals(flashMessage2.getText(),
		 * Messages.DOCUMENT_UPDATED_SUCCESSFULLY);
		 */

	}
}

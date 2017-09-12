package com.abb.ventyx.axis.support;

import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.hamcrest.CoreMatchers;
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
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;

@ALM(id = "158")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Document_Type_Creating extends BaseTestCase {
	private final String DOCTYPE_B = "Abb";
	private final String DESC_B = "AA_MAINTAIN_DOCTYPES";
	BaseGrid grid;

	// Step 1 Add new doc type and success message
	@Test
	public void createDocumentType() {
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();
		
		WebElement axisDocType = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisConfigMenu.DOC_TYPE)));
		axisDocType.click();
		WebElement addDocType = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(DocType.ADD)));
		addDocType.click();
		WebElement clickDocTpe = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DocType.DOCTYPES)));
		clickDocTpe.click();
		driver.findElement(By.id(DocType.DOCTYPES)).sendKeys(DOCTYPE_B);
		driver.findElement(By.id(DocType.DESC)).click();
		driver.findElement(By.id(DocType.DESC)).sendKeys(DESC_B);
		driver.findElement(By.id(DocType.SAVE)).click();
		WebElement flashMessage1 = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(DocType.SUCCESS_MESSAGE)));
		Assert.assertEquals(flashMessage1.getText(),
				Messages.DOCUMENT_CREATE_SUCCESSFULLY);
		grid = new BaseGrid(driver, DocType.GRID);
		Assert.assertNotEquals(
				grid.findItemByColumnName("Document Types", DOCTYPE_B), -1,
				"Data not created");

	}

	// Step 2 Check if Existing Doc type could be created one more time
	@Test(dependsOnMethods = "createDocumentType")
	public void createExistingDocType() {
		WebElement addDocType = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(DocType.ADD)));
		addDocType.click();
		WebElement clickDocTpe = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DocType.DOCTYPES)));
		clickDocTpe.click();
		driver.findElement(By.id(DocType.DOCTYPES)).sendKeys(DOCTYPE_B);
		driver.findElement(By.id(DocType.DESC)).click();
		driver.findElement(By.id(DocType.DESC)).sendKeys(DESC_B);
		driver.findElement(By.id(DocType.SAVE)).click();
		WebElement flashMessage2 = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(DocType.DOC_TYPES_EXIST)));
		assertThat(flashMessage2.getText(),
				CoreMatchers.containsString(Messages.DOC_TYPES_EXIST));
		WebElement cancelBtn = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DocType.CANCEL)));
		cancelBtn.click();

	}

	// Step 3 Check error message of Input Mandatory data to mandatory fields.
	@Test(dependsOnMethods = "createExistingDocType")
	public void leaveRequiredInputFieldsEmpty() throws InterruptedException {

		WebElement addDocType = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(DocType.ADD)));
		addDocType.click();
		Thread.sleep(300);
		WebElement clickDesc = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DocType.DESC)));
		clickDesc.click();
		driver.findElement(By.id(DocType.DESC)).sendKeys(DESC_B);
		driver.findElement(By.id(DocType.SAVE)).click();
		Thread.sleep(300);
		WebElement flashMessage3 = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(DocType.ENTER_MANDATORY_FIELDS)));
		Assert.assertEquals(flashMessage3.getText(),
				Messages.ENTER_MANDATORY_FIELDS);
		Thread.sleep(1000);

	}

	// Step 4 Check unsaved change message.
	@Test(dependsOnMethods = "leaveRequiredInputFieldsEmpty")
	public void checkUnsavedChange() throws InterruptedException {
		ScreenAction action = new ScreenAction(driver);
		WebElement cancelBtn = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DocType.CANCEL)));
		cancelBtn.click();
		Thread.sleep(500);
		WebElement message = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(DocType.CONFIRMATION)));
		message.getText();
		Assert.assertEquals(message.getText(), Messages.UNSAVED_CHANGE);
		Thread.sleep(500);
		driver.findElement(By.id(DialogBtns.NO)).click();
		Thread.sleep(1000);
		WebElement cancelBtn2 = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DocType.CANCEL)));
		cancelBtn2.click();
		Thread.sleep(500);
		WebElement yesBtn = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DialogBtns.YES)));
		yesBtn.click();
		Thread.sleep(500);
		assertEquals(action.isElementPresent(By.id(DocType.DOCTYPES)), false);

	}
}

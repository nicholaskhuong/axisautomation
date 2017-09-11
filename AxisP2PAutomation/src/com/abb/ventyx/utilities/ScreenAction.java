package com.abb.ventyx.utilities;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObject;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;

public class ScreenAction {
	WebDriver driver;

	public ScreenAction(WebDriver driver) {
		this.driver = driver;
	}

	public void waitObjInvisible(By obj) {
		(new WebDriverWait(driver, 60)).until(ExpectedConditions
				.invisibilityOfElementLocated(obj));
	}

	public void waitObjVisible(By obj) {
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(obj));
	}

	public void waitObjVisibleAndClick(By obj) {
		WebElement element = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(obj));
		element.click();

	}

	public void assertTitleScreen(String titleScreen) {
		WebElement screenTitle = driver.findElement(By
				.id(ScreenObject.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), titleScreen, "Title is wrong");
	}

	public void clickBtn(By obj) {
		WebElement btn = driver.findElement(obj);
		btn.click();
	}

	public void inputTextField(String obj, String value) {
		WebElement txtField = driver.findElement(By.id(obj));
		txtField.clear();
		txtField.sendKeys(value);
	}

	public void clickCheckBoxN(int n) {
		List<WebElement> listCheckbox = driver.findElements(By
				.xpath("//input[@type='checkbox']"));
		listCheckbox.get(n).click();
	}

/*	public int findRowByString(String tableCSS, String value, int columnindex) {
		int row = 0;
		WebElement baseTable = driver.findElement(By.cssSelector(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i < sumRow; i++) {
			WebElement columnValue = driver.findElement(By.cssSelector(tableCSS
					+ "> table > tbody > tr:nth-child(" + i
					+ ") > td:nth-child(" + columnindex + ")"));
			if (columnValue.getText().equals(value)) {
				row = i;
				break;
			}

		}
		return row;
	}*/

	public void checkAddSuccess(String msg) {

		WebElement flashMessage1 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.SUCCESS_MESSAGE)));
		Assert.assertEquals(flashMessage1.getText(), msg);
	}

	public void assertMessgeError(String msgCSS, String msg) {
		WebElement error = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(msgCSS)));
		Assert.assertEquals(error.getText(), msg);
	}

	public void checkValidationTextField(String obj, String value)
			throws InterruptedException {

		waitObjInvisible(By.cssSelector(ScreenObject.SUCCESS_MESSAGE));
		clickBtn(By.cssSelector(ScreenObject.ADD_BTN_CSS));
		waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));

		// Don't input data in text Field
		clickBtn(By.id(ScreenObject.SAVE_ID));
		assertMessgeError(ScreenObject.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);
		waitObjInvisible(By.cssSelector(ScreenObject.ERROR_WITHOUT_ICON_CSS));

		// Text Field is only space
		inputTextField(obj, "  ");
		clickBtn(By.id(ScreenObject.SAVE_ID));
		assertMessgeError(ScreenObject.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);
		waitObjInvisible(By.cssSelector(ScreenObject.ERROR_WITHOUT_ICON_CSS));

		// Text Field contain existing data
		inputTextField(obj, value);
		clickBtn(By.id(ScreenObject.SAVE_ID));
		assertMessgeError(ScreenObject.ERROR_CSS, Messages.USERGROUP_EXISTING);
	}

	public void cancelClickYes(By obj, String titleScreen) {

		driver.findElement(By.id(ScreenObject.CANCEL_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		driver.findElement(By.id(ScreenObject.YES_BTN_ID)).click();
		waitObjVisible(obj);
		assertTitleScreen(titleScreen);
	}

	public void cancelClickNo(String titleScreen) {

		driver.findElement(By.id(ScreenObject.CANCEL_ID)).click();
		WebElement msgDialog = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObject.NO_BTN_ID)).click();
		assertTitleScreen(titleScreen);

	}

	public void cancelWithoutdata(By obj, String titleScreen)
			throws InterruptedException {

		driver.findElement(By.id(ScreenObject.CANCEL_ID)).click();
		waitObjVisible(obj);
		assertTitleScreen(titleScreen);
	}

	/*
	 * public void inputFilterField(By filer, String value) {
	 * clickBtn(By.cssSelector(Messages.)); driver.findElement(filter).click();
	 * }
	 */
}

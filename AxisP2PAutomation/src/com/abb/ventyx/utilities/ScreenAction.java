package com.abb.ventyx.utilities;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;

public class ScreenAction {
	WebDriver driver;

	public ScreenAction(WebDriver driver) {
		this.driver = driver;
	}

	public void waitObjInvisible(By obj) {
		(new WebDriverWait(driver, 120)).until(ExpectedConditions
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
				.id(ScreenObjects.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), titleScreen, "Title is wrong");
	}

	public void clickBtn(By obj) {
		WebElement btn = driver.findElement(obj);
		btn.click();
	}

	public void inputTextField(String obj, String value) {
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(obj)));
		WebElement txtField = driver.findElement(By.id(obj));
		txtField.clear();
		txtField.sendKeys(value);
	}

	public void assertFieldReadOnly(By by) {
		WebElement field = driver.findElement(by);
		String readonly = field.getAttribute("readonly");
		assertNotNull(readonly);
	}

	public void assertTextEqual(By by, String text) {
		WebElement screenTitle = driver.findElement(by);
		assertEquals(screenTitle.getText(), text, "Title is wrong");
	}

	public String getAttribute(By by) {
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(by));
		return driver.findElement(by).getAttribute("value");
	}
	public void clickCheckBoxN(int n) {
		List<WebElement> listCheckbox = driver.findElements(By
				.xpath("//input[@type='checkbox']"));
		listCheckbox.get(n).click();
	}

	public void checkObjSelected(int start, int end) {
		List<WebElement> listCheckbox = driver.findElements(By
				.xpath("//input[@type='checkbox']"));
		for (int i = start; i < end; i++) {
			listCheckbox.get(i).isSelected();
		}
	}

	public boolean checkObjSelected(int index) {
		boolean isSelected = false;
		List<WebElement> listCheckbox = driver.findElements(By
				.xpath("//input[@type='checkbox']"));
		isSelected = listCheckbox.get(index).isSelected();
		return isSelected;
	}

	public void clickCheckBoxNInTable(String tableCSS, int n) {
		WebElement table = driver.findElement(By.cssSelector(tableCSS));
		List<WebElement> listCheckbox = table.findElements(By
				.className("v-grid-selection-checkbox"));
		listCheckbox.get(n).click();
	}

	public void clickYesUpdatePasswordRadio(){
		WebElement radioButton = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)));
		radioButton.findElement(By.tagName("label")).click();
	}

	public void clickNoUpdatePasswordRadio(){
		WebElement radioButton = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(CustomerUsers.NOUPDATEPASSWORD_RADIOBUTTON)));
		radioButton.findElement(By.tagName("label")).click();
	}

	public void clickExpandButton(int n) {
		List<WebElement> listExpand = driver.findElements(By
				.className(ScreenObjects.EXPAND_CLASS));
		listExpand.get(n).click();
	}

	public void checkAddSuccess(String msg) {

		WebElement flashMessage1 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		Assert.assertEquals(flashMessage1.getText(), msg);
	}

	public void selectComboValue(By by, String value){
		final Select selectBox = new Select(driver.findElement(by));
		selectBox.selectByValue(value);
	}
		
	public void selectStatus(String tableCSS, String value) {
		WebElement baseTable = driver.findElement(By.cssSelector(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			WebElement columnValue = driver
					.findElement(By
							.xpath("//div[@id='VAADIN_COMBOBOX_OPTIONLIST']//div//div[2]//table//tbody//tr["
									+ i + "]//td//span"));
			System.out.println("Status "+columnValue.getText());
			if (columnValue.getText().equals(value)) {
				columnValue.click();
				break;
			}
		}

	}
	
	public void assertMessgeError(String msgCSS, String msg) {
		WebElement error = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(msgCSS)));
		Assert.assertEquals(error.getText(), msg);
	}

	public void checkValidationTextField(String obj, String value, String msg,
			String msgCSS) throws InterruptedException {

		// Don't input data in text Field
		inputTextField(obj, "");
		clickBtn(By.id(ScreenObjects.SAVE_ID));
		assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);
		clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));

		// Text Field is only space
		inputTextField(obj, "  ");
		clickBtn(By.id(ScreenObjects.SAVE_ID));
		assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);

		// Text Field contain existing data
		inputTextField(obj, value);
		clickBtn(By.id(ScreenObjects.SAVE_ID));
		assertMessgeError(msgCSS, msg);
	}

	public void checkValidationTextField(String obj)
			throws InterruptedException {

		// Don't input data in text Field
		inputTextField(obj, "");
		clickBtn(By.id(ScreenObjects.SAVE_ID));
		assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);
		clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));

		// Text Field is only space
		inputTextField(obj, "  ");
		clickBtn(By.id(ScreenObjects.SAVE_ID));
		assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);
		clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));

	}

	public void cancelClickYes(By obj, String titleScreen) {

		driver.findElement(By.id(ScreenObjects.CANCEL_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		waitObjVisible(obj);
		assertTitleScreen(titleScreen);
	}

	public void cancelClickNo(String titleScreen) {

		driver.findElement(By.id(ScreenObjects.CANCEL_ID)).click();
		WebElement msgDialog = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		assertTitleScreen(titleScreen);

	}

	public void cancelWithoutdata(By obj, String titleScreen)
			throws InterruptedException {

		driver.findElement(By.id(ScreenObjects.CANCEL_ID)).click();
		waitObjVisible(obj);
		assertTitleScreen(titleScreen);
	}

	public void cancelByMenuClickYes(By obj, String titleScreen, By newObj) {

		driver.findElement(obj).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		waitObjVisible(newObj);
		assertTitleScreen(titleScreen);
	}

	public void cancelByMenuClickNo(By obj, String titleScreen) {

		driver.findElement(obj).click();
		WebElement msgDialog = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		assertTitleScreen(titleScreen);

	}

	public void cancelByMenuWithoutdata(By obj, String titleScreen, By newObj)
			throws InterruptedException {

		driver.findElement(obj).click();
		waitObjVisible(newObj);
		assertTitleScreen(titleScreen);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Test(dependsOnMethods = "checkScreen")
	public void deleteClickNo(String msgConfirm) throws Exception {

		// Click No on dialog
		WebElement deleteConfirm = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		assertThat(deleteConfirm.getText(), containsString(msgConfirm));
		WebElement deleteNoBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.DELETE_NO)));
		deleteNoBtn.click();
		Thread.sleep(2000);

	}

	@Test(dependsOnMethods = "deleteUserGroupClickNo")
	public void deleteClickYes(String msgDelete) throws Exception {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		WebElement deleteYesBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.DELETE_YES)));
		deleteYesBtn.click();
		WebElement flashMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		assertEquals(flashMessage.getText(), msgDelete);

	}

	public void clickHorizontalScrollBar() {
		WebElement horizontal_scroll = driver.findElement(By
				.className(ScreenObjects.HORIZONTAL_SCROLLBAR_CLASS));
		int width = horizontal_scroll.getSize().getWidth();
		Actions move = new Actions(driver);
		move.dragAndDropBy(horizontal_scroll, ((width * 25) / 100), 0).build()
		.perform();
	}

	public String getTextField(String obj) {
		try {
			WebElement textField = driver.findElement(By.id(obj));
			return textField.getText();
		} catch (NoSuchElementException e) {
			return "";
		}

	}
}
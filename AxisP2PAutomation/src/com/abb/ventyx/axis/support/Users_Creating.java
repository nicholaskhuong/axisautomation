package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportCustomerUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;

@ALM(id = "203")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Users_Creating extends BaseTestCase {
	public static String userID = "DathyUser6296";
	public static String email = "email970@dathy.com";
	ScreenAction action;

	@Test
	public void checkScreen() {
		action = new ScreenAction(driver);
		WebElement axisAdminMenu = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID)));
		axisAdminMenu.click();
		WebElement usersMenu = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.USERS_SUBMENU_ID)));
		usersMenu.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID
						+ "0")));
		WebElement screenTitle = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.SCREEN_TITLE_ID)));
		assertEquals(screenTitle.getText(),
				AxisAdministratorUsers.SCREEN_TITLE, "Title is wrong");
	}

	@Test(dependsOnMethods = "checkScreen")
	public void addWithoutData() {
		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH))
				.click();
		WebElement saveBtn = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.SAVE_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisAdministratorUsers.SCREEN_CREATE_TITLE, "Title is wrong");
		saveBtn.click();
		// Don't input all field
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Don't input User
		WebElement userId = driver.findElement(By
				.id(AxisAdministratorUsers.USER_ID));
		userId.clear();
		WebElement email = driver.findElement(By
				.id(AxisAdministratorUsers.EMAIL_ID));
		email.sendKeys("email@dathy.com");
		WebElement passWord = driver.findElement(By
				.id(AxisAdministratorUsers.PASSWORD_ID));
		passWord.sendKeys("TestUser1");
		WebElement confirmPassWord = driver.findElement(By
				.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPassWord.sendKeys("TestUser1");
		saveBtn.click();
		WebElement warningMessage1 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage1.getText(), Messages.ENTER_MANDATORY_FIELDS);

		// Don't input PWD
		email.clear();
		userId.clear();
		userId.sendKeys(userID);
		passWord.clear();
		email.sendKeys("email@dathy.com");
		confirmPassWord.clear();
		confirmPassWord.sendKeys("TestUser1");
		saveBtn.click();
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Don't input email
		userId.clear();
		userId.sendKeys(userID);
		passWord.clear();
		passWord.sendKeys("TestUser1");
		confirmPassWord.clear();
		confirmPassWord.sendKeys("TestUser1");
		email.clear();
		saveBtn.click();
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Don't input confirm password

		userId.clear();
		userId.sendKeys(userID);
		email.clear();
		email.sendKeys("email@dathy.com");
		passWord.clear();
		passWord.sendKeys("TestUser1");
		confirmPassWord.clear();
		saveBtn.click();
		WebElement warningMessage4 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage4.getText(), Messages.ENTER_MANDATORY_FIELDS);

	}

	// Click Cancel do it again
	@Test(dependsOnMethods = "addWithoutData")
	public void cancelWithoutData() {

		WebElement userId = driver.findElement(By
				.id(AxisAdministratorUsers.USER_ID));
		userId.clear();
		WebElement email = driver.findElement(By
				.id(AxisAdministratorUsers.EMAIL_ID));
		email.clear();
		WebElement passWord = driver.findElement(By
				.id(AxisAdministratorUsers.PASSWORD_ID));
		passWord.clear();
		WebElement confirmPassWord = driver.findElement(By
				.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPassWord.clear();
		WebElement cancelBtn = driver.findElement(By
				.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID
						+ "0")));
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "cancelWithoutData")
	public void addWithoutUserGroup() {

		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH))
				.click();
		WebElement saveBtn = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.SAVE_ID)));

		// Don't select User Group
		int n = new Random().nextInt(1000);
		userID = String.format("DathyUser%s" + n);
		email = String.format("email%s@dathy.com",n);
		WebElement userId = driver.findElement(By
				.id(AxisAdministratorUsers.USER_ID));
		userId.sendKeys(userID);
		WebElement email = driver.findElement(By
				.id(AxisAdministratorUsers.EMAIL_ID));
		email.sendKeys("email" + n + "@dathy.com");
		WebElement passWord = driver.findElement(By
				.id(AxisAdministratorUsers.PASSWORD_ID));
		passWord.sendKeys("TestUser1");
		WebElement confirmPassWord = driver.findElement(By
				.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPassWord.sendKeys("TestUser1");
		saveBtn.click();
		WebElement warningMessage5 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage5.getText(), Messages.USER_SELECT_USERGROUP);
	}

	@Test(dependsOnMethods = "addWithoutUserGroup")
	public void addWithValidData() throws InterruptedException {
		List<WebElement> list = driver.findElements(By
				.className(AxisAdministratorUsers.USERGROUP_CHECKBOX_CLASS));
		list.get(1).click();
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement sucessMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		assertEquals(sucessMessage.getText(), Messages.USER_CREATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "addWithValidData")
	public void cancelWithYes() throws InterruptedException {
		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH))
				.click();
		WebElement userId = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.USER_ID)));
		userId.sendKeys(userID);
		WebElement cancelBtn = driver.findElement(By
				.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();
		// Click Yes
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID
						+ "0")));
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_TITLE);

	}

	@Test(dependsOnMethods = "cancelWithYes")
	public void cancelClickNo() {

		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH))
				.click();
		WebElement userId = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.USER_ID)));
		userId.sendKeys(userID);
		WebElement cancelBtn = driver.findElement(By
				.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();

		WebElement msgDialog = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisAdministratorUsers.SCREEN_CREATE_TITLE);
	}

}

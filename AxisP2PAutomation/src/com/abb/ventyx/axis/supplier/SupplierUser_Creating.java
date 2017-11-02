package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "634")
@Credentials(user = "salem1@abb.com", password = "Testuser1")
public class SupplierUser_Creating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String user_id = "Salem Nguyen";
	String password = "Testuser2";
	String confirm_password = "Testuser2";
	String email = "salem@abb.com";
	String user_id_exist = "salem 3";
	String email_exist = "salem3@abb.com";
	String user_id_is_not_exist = "salem 102";
	String email_is_not_exist = "salem102@abb.com";
	int milliseconds = 3000;
	String password_less_than_6_character = "test";
	String password_wrong_format = "test1234567";
	String confirmPasswordNotMap = "Testuser6000";
	String userIDvalid = "Salem 200";
	String passwordValid = "Testuser1";
	String confirmPasswordValid = "Testuser1";
	String userEmailAddressValid = "salem200@abb.com";

	@Test
	public void openScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void createUser() {

		// Step 2
		action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.inputTextField(Users.USER_ID, user_id);
		action.pause(milliseconds);
		action.waitObjVisible(By.id(ScreenObjects.SAVE_ID));
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		// Step 3
		action.inputTextField(Users.PASSWORD_ID, password);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirm_password);
		action.inputTextField(Users.EMAIL_ID, email);
		action.waitObjVisible(By.id(ScreenObjects.SAVE_ID));
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "createUser")
	public void userIdExist() {
		// Step 4
		action.clickCheckBoxN(1);
		action.pause(milliseconds);
		action.inputTextField(Users.USER_ID, user_id_exist);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.USERS_EXISTING);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "userIdExist")
	public void emailExist() {
		// Step 4
		action.pause(milliseconds);
		action.inputTextField(Users.EMAIL_ID, email_exist);
		action.inputTextField(Users.EMAIL_ID, email_exist);
		action.inputTextField(Users.USER_ID, user_id_is_not_exist);
		action.inputTextField(Users.USER_ID, user_id_is_not_exist);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SAME_EMAIL);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "emailExist")
	public void passwordLessThan6Character() {
		// Step 4
		action.inputTextField(Users.PASSWORD_ID, password_less_than_6_character);
		action.inputTextField(Users.PASSWORD_ID, password_less_than_6_character);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_PWD);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "passwordLessThan6Character")
	public void passwordWrongFormat() {
		// Step 5
		action.pause(milliseconds);
		// action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputTextField(Users.PASSWORD_ID, password_wrong_format);
		action.inputTextField(Users.PASSWORD_ID, password_wrong_format);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_PWD2);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "passwordWrongFormat")
	public void passwordIsNotMap() {
		// Step 6
		action.pause(milliseconds);
		// action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.pause(milliseconds);
		action.inputTextField(Users.PASSWORD_ID, password);
		action.inputTextField(Users.PASSWORD_ID, confirmPasswordNotMap);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.MESSAGE_ERROR_PASSWORD);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "passwordIsNotMap")
	public void userCreateSuccessfully() {
		// step 7
		action.inputEmailField(Users.USER_ID, userIDvalid);
		action.inputTextField(Users.EMAIL_ID, userEmailAddressValid);
		action.inputTextField(Users.PASSWORD_ID, passwordValid);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirmPasswordValid);
		action.clickCheckBoxN(2);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.checkAddSuccess(Messages.USER_CREATE_SUCCESSFULLY);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "userCreateSuccessfully")
	public void noSaveChange() {
		action.pause(milliseconds);
		action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.inputTextField(Users.USER_ID, user_id);
		action.pause(milliseconds);
		// step 9
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(),
				Messages.UNSAVED_CHANGE);
		// Step 10
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		action.pause(milliseconds);
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(),
				Messages.UNSAVED_CHANGE);
		// Step 11
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();

	}

}

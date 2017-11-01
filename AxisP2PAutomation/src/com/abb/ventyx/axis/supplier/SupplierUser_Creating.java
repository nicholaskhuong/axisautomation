package com.abb.ventyx.axis.supplier;

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
	int milliseconds = 800;
	String password_less_than_6_character = "test";
	String password_wrong_format = "test1234567";

	/*
	 * String USER_ID = "BOSS"; String PASSWORD = "Testuser1"; String
	 * CONFIRMPASSWORD = "Testuser2"; String EMAIL = "boss@abb.com"; ScreenAction
	 * action; TableFunction table; String USERGROUP = "Salem"; int row; int
	 * sumRowBefore; int sumRowAfter;
	 */
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
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "createUser")
	public void userIdExist() {
		// Step 4
		action.clickCheckBoxN(1);
		action.inputTextField(Users.USER_ID, user_id_exist);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.USERS_EXISTING);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "userIdExist")
	public void emailExist() {
		// Step 4

		action.inputTextField(Users.EMAIL_ID, email_exist);
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
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_PWD);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "passwordLessThan6Character")
	public void passwordWrongFormat() {
		// Step 4
		action.inputTextField(Users.PASSWORD_ID, password_wrong_format);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		// action.pause(milliseconds);
		// action.assertMessgeError(ScreenObjects.ERROR_ICON_CSS,
		// Messages.INVALID_PWD2);
		// ((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	/*
	 * * action.clickCheckBoxN(1); action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.checkAddSuccess(Messages.USER_CREATE_SUCCESSFULLY);
	 */
	/*
	 * @Test(dependsOnMethods = "addUsers") public void checkAddSuccessfully() {
	 * action.clickBtn(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	 * sumRowAfter = table.countRow(Users.SUPPLIER_USERS_TABLE_CSS);
	 * assertEquals(sumRowAfter, sumRowBefore + 1); table.assertValueRow(2,
	 * sumRowBefore, USER_ID); table.assertValueRow(5, sumRowBefore, "Created"); }
	 * 
	 * @Test(dependsOnMethods = "checkAddSuccessfully") public void cancelClickYes()
	 * { action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	 * action.waitObjVisible(By.id(Users.USER_ID));
	 * action.inputTextField(Users.USER_ID, "ABC");
	 * action.cancelClickYes(By.cssSelector(ScreenObjects.ADD_BTN_CSS),
	 * Users.TITLE); }
	 */

	/*
	 * @Test(dependsOnMethods = "cancelClickYes") public void cancelClickNo() {
	 * action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	 * action.waitObjVisible(By.id(Users.USER_ID));
	 * action.inputTextField(Users.USER_ID, USER_ID);
	 * action.cancelClickNo(Users.TITLE_CREATE); }
	 * 
	 * @Test(dependsOnMethods = "cancelClickNo") public void cancelWithoutdata()
	 * throws InterruptedException {
	 * 
	 * action.inputTextField(Users.USER_ID, "");
	 * action.cancelWithoutdata(By.cssSelector(ScreenObjects.ADD_BTN_CSS),
	 * Users.TITLE); }
	 * 
	 * @Test(dependsOnMethods = "cancelWithoutdata") public void
	 * addValidationUserID() {
	 * action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	 * action.waitObjVisible(By.id(Users.USER_ID));
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.ENTER_MANDATORY_FIELDS);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ; // input other data action.inputTextField(Users.PASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.EMAIL_ID, EMAIL); action.clickCheckBoxN(1); //
	 * don't input UserID action.checkValidationTextField(Users.USER_ID, USER_ID,
	 * Messages.USERS_EXISTING, ScreenObjects.ERROR_CSS); }
	 * 
	 * @Test(dependsOnMethods = "addValidationUserID") public void
	 * addValidationEmail() throws InterruptedException { // input other data
	 * action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.PASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD); // don't input
	 * UserID // action.checkValidationTextField(Users.EMAIL_ID);
	 * action.inputTextField(Users.EMAIL_ID, ""); Thread.sleep(2000);
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.ENTER_MANDATORY_FIELDS);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * driver.findElement(By.id(Users.EMAIL_ID)).click();
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ;
	 * 
	 * // Text Field is only space action.inputTextField(Users.EMAIL_ID, "  ");
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.ENTER_MANDATORY_FIELDS);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * driver.findElement(By.id(Users.EMAIL_ID)).click();
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ;
	 * 
	 * action.inputTextField(Users.EMAIL_ID, "mail232@abb.com");
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SAME_EMAIL);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_ICON_CSS));
	 * action.inputTextField(Users.EMAIL_ID, "Boss");
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.INVALID_EMAIL); }
	 * 
	 * @Test(dependsOnMethods = "addValidationEmail") public void
	 * addValidationPassword() {
	 * action.clickBtn(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ; // input other data action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.EMAIL_ID, EMAIL);
	 * 
	 * // don't input UserID action.inputTextField(Users.PASSWORD_ID, "");
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.ENTER_MANDATORY_FIELDS);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ; action.inputTextField(Users.PASSWORD_ID, CONFIRMPASSWORD);
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.INVALID_CONFIRM_PWD);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ; action.inputTextField(Users.PASSWORD_ID, "ABC");
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.INVALID_PWD); }
	 * 
	 * @Test(dependsOnMethods = "addValidationPassword") public void
	 * addValidationConfirmPassword() { // input other data
	 * action.clickBtn(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ; action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.EMAIL_ID, EMAIL);
	 * action.inputTextField(Users.PASSWORD_ID, PASSWORD); // don't input UserID
	 * action.checkValidationTextField(Users.CONFIMRPASSWORD_ID); }
	 * 
	 * @Test(dependsOnMethods = "addValidationConfirmPassword") public void
	 * unselectUserGroup() {
	 * 
	 * action.clickBtn(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
	 * ;
	 * 
	 * action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.EMAIL_ID, "boss1@abb.com");
	 * action.inputTextField(Users.PASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.clickCheckBoxN(1); action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.USER_SELECT_USERGROUP); }
	 */
}

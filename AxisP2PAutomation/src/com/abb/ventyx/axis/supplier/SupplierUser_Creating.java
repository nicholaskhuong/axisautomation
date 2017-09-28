package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
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

@ALM(id = "601")
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierUser_Creating extends BaseTestCase {
	String USER_ID = "BOSS";
	String PASSWORD = "Testuser1";
	String CONFIRMPASSWORD = "Testuser2";
	String EMAIL = "boss@abb.com";
	ScreenAction action;
	TableFunction table;
	String USERGROUP = "Datherine";
	int row;
	int sumRowBefore;
	int sumRowAfter;

	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(Users.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void addUsers() {
		table = new TableFunction(driver);
		sumRowBefore = table.countRow(Users.SUPPLIER_USERS_TABLE_CSS);
		action.clickBtn(By.cssSelector(Users.ADD_BTN_CSS));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.assertTitleScreen(Users.TITLE_CREATE);
		action.inputTextField(Users.USER_ID, USER_ID);
		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		action.inputTextField(Users.EMAIL_ID, EMAIL);
		// Click PO permission
		action.clickCheckBoxN(1);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.USER_CREATE_SUCCESSFULLY);

	}

	@Test(dependsOnMethods = "addUsers")
	public void checkAddSuccessfully() throws InterruptedException {
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		sumRowAfter = table.countRow(Users.SUPPLIER_USERS_TABLE_CSS);
		assertEquals(sumRowAfter, sumRowBefore + 1);
		table.assertValueRow(2, sumRowBefore, USER_ID);

	}

	@Test(dependsOnMethods = "checkAddSuccessfully")
	public void cancelClickYes() {
		action.clickBtn(By.cssSelector(Users.ADD_BTN_CSS));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, "ABC");
		action.cancelClickYes(By.cssSelector(Users.ADD_BTN_CSS), Users.TITLE);
	}

	@Test(dependsOnMethods = "cancelClickYes")
	public void cancelClickNo() {

		action.clickBtn(By.cssSelector(Users.ADD_BTN_CSS));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, USER_ID);
		action.cancelClickNo(Users.TITLE_CREATE);

	}

	@Test(dependsOnMethods = "cancelClickNo")
	public void cancelWithoutdata() throws InterruptedException {

		action.inputTextField(Users.USER_ID, "");
		action.cancelWithoutdata(By.cssSelector(Users.ADD_BTN_CSS), Users.TITLE);
	}

	@Test(dependsOnMethods = "cancelWithoutdata")
	public void addValidationUserID() throws InterruptedException {
		action.clickBtn(By.cssSelector(Users.ADD_BTN_CSS));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By
				.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// input other data
		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		action.inputTextField(Users.EMAIL_ID, EMAIL);
		action.clickCheckBoxN(1);
		// don't input UserID
		action.checkValidationTextField(Users.USER_ID, USER_ID,
				Messages.USERS_EXISTING);
		action.clickCheckBoxN(1);
		// don't select User group
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.USER_SELECT_USERGROUP);
	}

	@Test(dependsOnMethods = "addValidationUserID")
	public void addValidationEmail() throws InterruptedException {
		// input other data
		action.inputTextField(Users.USER_ID, "BOSS_1");
		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		action.clickCheckBoxN(1);
		// don't input UserID
		action.checkValidationTextField(Users.EMAIL_ID);

	}

	@Test(dependsOnMethods = "addValidationEmail")
	public void addValidationPassword() throws InterruptedException {
		// input other data
		action.inputTextField(Users.USER_ID, "BOSS_1");
		action.inputTextField(Users.EMAIL_ID, EMAIL);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		// don't input UserID
		action.checkValidationTextField(Users.PASSWORD_ID);
		action.inputTextField(Users.PASSWORD_ID, CONFIRMPASSWORD);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.INVALID_CONFIRM_PWD);
		action.inputTextField(Users.PASSWORD_ID, "ABC");
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.INVALID_PWD);
	}

	@Test(dependsOnMethods = "addValidationPassword")
	public void addValidationConfirmPassword() throws InterruptedException {
		// input other data
		action.inputTextField(Users.USER_ID, "BOSS_1");
		action.inputTextField(Users.EMAIL_ID, EMAIL);
		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		// action.clickCheckBoxN(1);
		// don't input UserID
		action.checkValidationTextField(Users.CONFIMRPASSWORD_ID);

	}
}

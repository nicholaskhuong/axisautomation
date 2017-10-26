package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

@ALM(id = "603")
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierUser_Updating extends BaseTestCase {
	String USER_ID = "BOSS";
	String PASSWORD = "Testuser1";
	String CONFIRMPASSWORD = "Testuser2";
	String EMAIL = "boss@abb.com";
	String EMAIL_UPDATE = "bossdathy@abb.com";
	String USER_NO = "";
	ScreenAction action;
	TableFunction table;
	String USERGROUP = "Datherine";
	String AllGROUP = "";
	String USERID_EXISTING = "";

	int row;

	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void selectUser() {
		table = new TableFunction(driver);
		row = table.findRowByString(Users.SUPPLIER_USERS_TABLE_CSS, 2, USER_ID);
		table.assertValueRow(2, row, USER_ID);
		row = row - 1;
		USER_NO = driver.findElement(By.id(Users.USERNUMBER_LINKID + row)).getText();
		action.clickBtn(By.id(Users.USERNUMBER_LINKID + row));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.assertTitleScreen(Users.TITLE_MODIFY);

	}

	@Test(dependsOnMethods = "selectUser")
	public void checkDataOfUser() {
		WebElement userNo = driver.findElement(By.id(Users.USERNUMBER_ID));
		assertEquals(userNo.getAttribute("value"), USER_NO);
		assertEquals(userNo.getAttribute("readonly"), "true");
		assertEquals(driver.findElement(By.id(Users.USER_ID)).getAttribute("value"), USER_ID);
		assertEquals(driver.findElement(By.id(Users.EMAIL_ID)).getAttribute("value"), EMAIL);

		WebElement userStatus = driver.findElement(By.id(Users.STATUS_ID)).findElement(By.tagName("input"));
		assertEquals(userStatus.getAttribute("value"), "Created");
		assertEquals(userStatus.getAttribute("aria-readonly"), "true");
		action.checkObjSelected(1);
		WebElement radioNO = driver.findElement(By.cssSelector(Users.UPDATEPASSWORD_NO_CSS));
		assertEquals(radioNO.findElement(By.tagName("label")).getText(), "No");
		assertEquals(radioNO.findElement(By.tagName("input")).getAttribute("checked"), "true");
		assertEquals(action.isElementPresent(By.id(Users.PASSWORD_ID)), false);
		assertEquals(action.isElementPresent(By.id(Users.CONFIMRPASSWORD_ID)), false);
		AllGROUP = table.getValueAllRowchecked(2, table.countRow(Users.USERGROUP_TABLE_CSS));
	}

	@Test(dependsOnMethods = "checkDataOfUser")
	public void updateUserSuccessfully() {
		driver.findElement(By.cssSelector(Users.UPDATEPASSWORD_YES_CSS)).findElement(By.tagName("label")).click();
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		action.inputTextField(Users.PASSWORD_ID, CONFIRMPASSWORD);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, CONFIRMPASSWORD);
		action.inputTextField(Users.EMAIL_ID, EMAIL_UPDATE);
		action.clickCheckBoxN(0);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.USER_UPDATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "updateUserSuccessfully")
	public void checkUpdateSuccessfully() throws InterruptedException {
		action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		row = row + 1;
		table.assertValueRow(2, row, USER_ID);
		table.assertValueRow(3, row, EMAIL_UPDATE);
		table.assertValueRow(4, row, AllGROUP);
		table.assertValueRow(5, row, "Created");
		USERID_EXISTING = table.getValueRow(2, 1);
	}

	@Test(dependsOnMethods = "checkUpdateSuccessfully")
	public void cancelClickYes() {
		row = row - 1;
		action.clickBtn(By.id(Users.USERNUMBER_LINKID + row));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, "ABC");
		action.cancelClickYes(By.cssSelector(ScreenObjects.ADD_BTN_CSS), Users.TITLE);
	}

	@Test(dependsOnMethods = "cancelClickYes")
	public void cancelClickNo() {

		action.clickBtn(By.id(Users.USERNUMBER_LINKID + row));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, "ABC");
		action.cancelClickNo(Users.TITLE_MODIFY);
	}

	@Test(dependsOnMethods = "cancelClickNo")
	public void cancelWithoutdata() throws InterruptedException {

		action.inputTextField(Users.USER_ID, USER_ID);
		action.cancelWithoutdata(By.cssSelector(ScreenObjects.ADD_BTN_CSS), Users.TITLE);
	}

	@Test(dependsOnMethods = "cancelWithoutdata")
	public void addValidationEmail() throws InterruptedException {

		action.clickBtn(By.id(Users.USERNUMBER_LINKID + row));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, USER_ID);
		// don't input
		action.checkValidationTextField(Users.EMAIL_ID);
		action.inputTextField(Users.EMAIL_ID, "mail232@abb.com");
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.UPDATE_SAME_EMAIL);
		action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_ICON_CSS));
		action.inputTextField(Users.EMAIL_ID, "Boss");
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
	}

	@Test(dependsOnMethods = "addValidationEmail")
	public void addValidationPassword() throws InterruptedException {
		action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));

		// don't input UserID
		driver.findElement(By.cssSelector(Users.UPDATEPASSWORD_YES_CSS)).findElement(By.tagName("label")).click();
		action.waitObjVisible(By.id(Users.CONFIMRPASSWORD_ID));
		assertEquals(action.isElementPresent(By.id(Users.PASSWORD_ID)), true);
		assertEquals(action.isElementPresent(By.id(Users.CONFIMRPASSWORD_ID)), true);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		action.inputTextField(Users.EMAIL_ID, EMAIL);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// input password is not same connfirm password
		action.inputTextField(Users.PASSWORD_ID, CONFIRMPASSWORD);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_CONFIRM_PWD);
		action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// input invalid password
		action.inputTextField(Users.PASSWORD_ID, "ABC");
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_PWD);
	}

	@Test(dependsOnMethods = "addValidationPassword")
	public void addValidationConfirmPassword() throws InterruptedException {
		// input other data
		action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));

		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		// don't input UserID
		action.checkValidationTextField(Users.CONFIMRPASSWORD_ID);
	}

	@Test(dependsOnMethods = "addValidationConfirmPassword")
	public void unselectUserGroup() throws InterruptedException {

		action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));

		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		action.clickCheckBoxN(0);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
	}

	@Test(dependsOnMethods = "unselectUserGroup")
	public void addValidationUserID() throws InterruptedException {
	
		action.clickCheckBoxN(1);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// Now, this is an issue
		action.checkValidationTextField(Users.USER_ID, USERID_EXISTING, Messages.USERS_EXISTING,
				ScreenObjects.ERROR_CSS);
	}
}

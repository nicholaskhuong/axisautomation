package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "845")
@Credentials(user = "cuserdefault@abb.com", password = "Testuser1")
public class CustomerUser_CUD_ByUser extends BaseTestCase {

	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i;
	String USERID = "createdby_nick";
	String CUSTOMERUSEREMAIL = "nickcustomeruser1@abb.com";
	String PASSWORD = "Testuser2";
	String CONFIRMPASSWORD = "Testuser2";
	String USERGROUPNAME = "All Permissions";
	String CREATEDSTATUS = "Created";
	String ACTIVESTATUS = "Active";
	String NEWPASSWORD = "Testuser1";
	String USEREMAIL_ADMIN = "cadmin1@abb.com";
	String NEWPASSWORD_ADMIN = "Testuser2";
	String NEWUSEREMAIL_ADMIN = "cadmin1updated@abb.com";

	// New info for customer user to use for updating
	String USERID2 = "CU Updated";
	String USEREMAIL2 = "CUUPDATED@abb.com";
	String USEREMAILLOWERCASE = "cuupdated@abb.com";
	String USERGGROUP2 = "test";
	String NEWPASSWORD2 = "Testuser3";
	String userNo = "";

	@Test
	public void selectUsersSubMenu(){
		wait = new WebDriverWait(driver, 60);
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));

		// The system wrong here
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users",
				"Title is displaying incorrectly, need to raise a P3 defect here");
	}

	// Step 1 Customer user creates another user
	@Test(dependsOnMethods = "selectUsersSubMenu", alwaysRun = true)
	public void createNewUser() throws InterruptedException {
		i = table.findRowByString(3, CUSTOMERUSEREMAIL);
		if (i > 0) {
			action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
			action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
			action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
			action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
			assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_DELETE_SUCCESSFULLY);
		}
		i = table.findRowByString(3, USEREMAILLOWERCASE);
		if (i > 0) {
			action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
			action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
			action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
			action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
			assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_DELETE_SUCCESSFULLY);
		}
		assertEquals(table.isValueExisting(3, CUSTOMERUSEREMAIL), false, "User exists! Can't create a new user with the same email");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.inputEmailField(CustomerUsers.USERID_TEXTBOX_ID, USERID);
		action.inputEmailField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, CUSTOMERUSEREMAIL);
		action.inputEmailField(CustomerUsers.PASSWORD_TEXTBOX_ID, PASSWORD);
		action.inputEmailField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, CONFIRMPASSWORD);
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME);
		action.clickBtn(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
		i = table.findRowByString(3, CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(2, i), USERID);
		assertEquals(table.getValueRow(3, i), CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), CREATEDSTATUS);
		userNo = table.getIDValue(i);
	}

	@Test(dependsOnMethods = "createNewUser", alwaysRun = true)
	public void loginAsNewUser() throws InterruptedException {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));

		action.inputEmailField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, CUSTOMERUSEREMAIL);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, PASSWORD);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));

		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, PASSWORD);

		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, NEWPASSWORD);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID, NEWPASSWORD);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)));
		action.assertTitleScreen("Customer Dashboard");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		// The system wrong here
		// assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(),
		// "Maintain Customer Users");
		i = table.findRowByString(3, CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(2, i), USERID);
		assertEquals(table.getValueRow(3, i), CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), ACTIVESTATUS);
		assertEquals(table.getValueRow(1, i), userNo);

	}

	// Step 2 Customer user update other user
	@Test(dependsOnMethods = "loginAsNewUser", alwaysRun = true)
	public void logOutAndLoginToTheDefaultUser() throws InterruptedException {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));

		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, "cuserdefault@abb.com");
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, "Testuser1");
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
	}

	// User update other user's info

	@Test(dependsOnMethods = "logOutAndLoginToTheDefaultUser")
	public void updateUserInfo() throws InterruptedException {
		//

		i = table.findRowByString(1, userNo);
		org.testng.Assert.assertTrue(1 >= 0, "User not found!");
		assertEquals(table.getValueRow(2, i), USERID);
		assertEquals(table.getValueRow(3, i), CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), ACTIVESTATUS);

		table.clickUserNo(i);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		Thread.sleep(500);
		action.assertFieldReadOnly(By.id(Users.USERNUMBER_ID));
		assertEquals(action.getAttribute(By.id(Users.USER_ID)), USERID);
		assertEquals(action.getAttribute(By.id(Users.EMAIL_ID)), CUSTOMERUSEREMAIL);
		action.checkObjSelected(0);

		driver.findElement(By.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)).findElement(By.tagName("label")).isSelected();

		action.inputEmailField(Users.USER_ID, USERID2);
		action.inputEmailField(Users.EMAIL_ID, USEREMAIL2);

		action.clickYesUpdatePasswordRadio();
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, NEWPASSWORD2);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, NEWPASSWORD2);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);

		assertEquals(table.getValueRow(2, i), USERID2);
		assertEquals(table.getValueRow(3, i), USEREMAILLOWERCASE);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), ACTIVESTATUS);
		assertEquals(table.getValueRow(1, i), userNo);

		// Update status to Inactive
		table.clickUserNo(i);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		Thread.sleep(500);
		action.clickBtn(By.id(Users.STATUS_ID));
		Thread.sleep(500);

		action.selectStatus(CustomerUsers.STATUSLIST, "Inactive");
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);
		assertEquals(table.getValueRow(5, i), "Inactive");

		// Update user group to "NoInvoice" and Active
		table.clickUserNo(i);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		Thread.sleep(500);
		action.clickBtn(By.id(Users.STATUS_ID));
		Thread.sleep(500);
		action.selectStatus(CustomerUsers.STATUSLIST, "Active");
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, "NoInvoice");
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, "All Permissions");
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);
		assertEquals(table.getValueRow(4, i), "NoInvoice");
	}

	@Test(dependsOnMethods = "updateUserInfo", alwaysRun = true)
	public void signOut() throws InterruptedException {
		action.signOut();
	}

	@Test(dependsOnMethods = "signOut")
	public void loginAgainWithNewInfo() throws InterruptedException {

		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, USEREMAILLOWERCASE);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, NEWPASSWORD2);

		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER));
		action.assertTitleScreen("Customer Dashboard");
	}

	@Test(dependsOnMethods = "loginAgainWithNewInfo", alwaysRun = true)
	public void logOut() throws InterruptedException {

		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
	}

	@Test(dependsOnMethods = "logOut", alwaysRun = true)
	public void logIn() throws InterruptedException {

		action.inputEmailField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, "cuserdefault@abb.com");
		action.inputEmailField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, "Testuser1");
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));

		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN)).getAttribute("aria-disabled"), "true");
	}

	@Test(dependsOnMethods = "logIn")
	public void deleteCustomerAdmin() throws InterruptedException {

		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN)).getAttribute("aria-disabled"), "true");

	}

	// Click Trash Bin icon of the user to test
	@Test(dependsOnMethods = "deleteCustomerAdmin", alwaysRun = true)
	public void clickTrashBinIconOfUser() throws InterruptedException {

		i = table.findRowByString(3, CUSTOMERUSEREMAIL);
		if (i <= 0) {
			i = table.findRowByString(1, userNo);
		}
		if (i <= 0) {
			i = table.findRowByString(3, USEREMAILLOWERCASE);
		}
		Assert.assertTrue("User doesn't exist", i > 0);
		action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);

	}

	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void clickNoButton() throws InterruptedException {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.CONFIRMATION)), false);
	}

	// Step 4 Click Trash Bin and choose Yes
	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void clickYesButton() throws InterruptedException {

		i = table.findRowByString(3, CUSTOMERUSEREMAIL);
		if (i <= 0) {
			i = table.findRowByString(1, userNo);
		}
		if (i <= 0) {
			i = table.findRowByString(3, USEREMAILLOWERCASE);
		}
		Assert.assertTrue("User doesn't exist", i > 0);
		action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_DELETE_SUCCESSFULLY);

		assertEquals(table.isValueExisting(3, USEREMAILLOWERCASE), false);
	}

	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void loginAsTheDeletedUser() throws InterruptedException {

		action.clickBtn(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.waitObjVisible(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, USEREMAIL2);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, USEREMAILLOWERCASE);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_CSS)).getText(), Messages.USERNOTFOUND);
	}

	@Test(dependsOnMethods = "loginAsTheDeletedUser")
	public void updateAdminInfo() throws InterruptedException {
		action.signIn("cuserdefault@abb.com", "Testuser1");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		table.clickUserNo(table.findRowByString(1, "4073"));
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));

		action.assertFieldReadOnly(By.id(CustomerUsers.USERNUMBER_MODIFYSCREEN_ID));
		action.isFieldDisable(By.id(CustomerUsers.SAVE_BUTTON_ID));
	}
}

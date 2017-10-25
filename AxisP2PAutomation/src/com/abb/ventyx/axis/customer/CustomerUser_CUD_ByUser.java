package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

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
	String CUSTOMERUSEREMAIL = "nickcusercreatedbyuser@abb.com";
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

	@Test
	public void selectUsersSubMenu() throws InterruptedException {
		wait = new WebDriverWait(driver, 60);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		Thread.sleep(1000);
		// The system wrong here
		// assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(),
		// "Maintain Customer Users");
	}

	// Step 1 Customer user creates another user
	@Test(dependsOnMethods = "selectUsersSubMenu")
	public void createNewUser() throws InterruptedException {
		table = new TableFunction(driver);
		wait = new WebDriverWait(driver, 60);
		assertEquals(table.isValueExisting(3, CUSTOMERUSEREMAIL), false, "User exists! Can't create a new user with the same email");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.inputTextField(CustomerUsers.USERID_TEXTBOX_ID, USERID);
		action.inputTextField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, CUSTOMERUSEREMAIL);
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, PASSWORD);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, CONFIRMPASSWORD);
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME);
		action.clickBtn(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(CustomerUsers.SUCCESS, Messages.USER_CREATE_SUCCESSFULLY);
		i = table.findRowByString1(3, CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(2, i), USERID);
		assertEquals(table.getValueRow(3, i), CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), CREATEDSTATUS);
	}

	@Test(dependsOnMethods = "createNewUser")
	public void loginAsNewUser() throws InterruptedException {
		table = new TableFunction(driver);
		wait = new WebDriverWait(driver, 60);
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));

		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, PASSWORD);
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, CUSTOMERUSEREMAIL);
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
		// The system wrong here
		// assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(),
		// "Maintain Customer Users");
		i = table.findRowByString1(3, CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(2, i), USERID);
		assertEquals(table.getValueRow(3, i), CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), ACTIVESTATUS);

	}

	// Step 2 Customer user update other user
	@Test(dependsOnMethods = "loginAsNewUser")
	public void logOutAndLoginToTheDefaultUser() throws InterruptedException {

		wait = new WebDriverWait(driver, 60);
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
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		// wait = new WebDriverWait(driver, 60);
		System.out.print("Print index " + i);
		assertEquals(table.getValueRow(2, i), USERID);
		assertEquals(table.getValueRow(3, i), CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), ACTIVESTATUS);

		table.clickUserNumber(CUSTOMERUSEREMAIL);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		Thread.sleep(500);
		action.assertFieldReadOnly(By.id(Users.USERNUMBER_ID));
		assertEquals(action.getAttribute(By.id(Users.USER_ID)), USERID);
		assertEquals(action.getAttribute(By.id(Users.EMAIL_ID)), CUSTOMERUSEREMAIL);
		action.checkObjSelected(0);

		driver.findElement(By.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)).findElement(By.tagName("label")).isSelected();

		action.inputTextField(Users.USER_ID, USERID2);
		action.inputTextField(Users.EMAIL_ID, USEREMAIL2);

		action.clickYesUpdatePasswordRadio();
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, NEWPASSWORD2);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, NEWPASSWORD2);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(CustomerUsers.SUCCESS));
		action.assertMessgeError(CustomerUsers.SUCCESS, Messages.USER_UPDATE_SUCCESSFULLY);

		assertEquals(table.getValueRow(2, i), USERID2);
		assertEquals(table.getValueRow(3, i), USEREMAILLOWERCASE);
		assertEquals(table.getValueRow(4, i), USERGROUPNAME);
		assertEquals(table.getValueRow(5, i), ACTIVESTATUS);

		// Update status to Inactive
		table.clickUserNumber(USEREMAILLOWERCASE);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		Thread.sleep(500);
		action.clickBtn(By.id(Users.STATUS_ID));
		Thread.sleep(500);

		action.selectStatus(CustomerUsers.STATUSLIST, "Inactive");
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.assertMessgeError(CustomerUsers.SUCCESS, Messages.USER_UPDATE_SUCCESSFULLY);
		assertEquals(table.getValueRow(5, i), "Inactive");

		// Update user group to "NoInvoice" and Active
		table.clickUserNumber(USEREMAILLOWERCASE);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		Thread.sleep(500);
		action.clickBtn(By.id(Users.STATUS_ID));
		Thread.sleep(500);
		action.selectStatus(CustomerUsers.STATUSLIST, "Active");
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, "NoInvoice");
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, "All Permissions");
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.assertMessgeError(CustomerUsers.SUCCESS, Messages.USER_UPDATE_SUCCESSFULLY);
		assertEquals(table.getValueRow(4, i), "NoInvoice");
		assertEquals(table.getValueRow(5, i), "Active");

		// Login as the new updated user.
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));

		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, USEREMAILLOWERCASE);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, NEWPASSWORD2);

		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER));
		action.assertTitleScreen("Customer Dashboard");
	}

	@Test(dependsOnMethods = "updateUserInfo")
	public void logOutAndLogIn() throws InterruptedException {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		wait = new WebDriverWait(driver, 60);

		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, "cuserdefault@abb.com");
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, "Testuser1");
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));

		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN)).getAttribute("aria-disabled"), "true");
	}

	@Test(dependsOnMethods = "logOutAndLogIn")
	public void deleteCustomerAdmin() throws InterruptedException {

		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN)).getAttribute("aria-disabled"), "true");

	}

	// Click Trash Bin icon of the user to test
	@Test(dependsOnMethods = "deleteCustomerAdmin", alwaysRun = true)
	public void clickTrashBinIconOfUser() throws InterruptedException {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		int j = i - 1;
		action.clickBtn(By.id("deleteItemBtn" + j));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);

	}

	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void clickNoButton() throws InterruptedException {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.CONFIRMATION)), false);
	}

	// Step 4 Click Trash Bin and choose Yes
	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void clickYesButton() throws InterruptedException {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		int j = i - 1;
		action.clickBtn(By.id("deleteItemBtn" + j));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_DELETE_SUCCESSFULLY);

		assertEquals(table.isValueExisting(3, USEREMAILLOWERCASE), false);
	}

	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void loginAsTheDeletedUser() throws InterruptedException {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.clickBtn(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.waitObjVisible(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, USEREMAIL2);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, USEREMAILLOWERCASE);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_CSS)).getText(), Messages.USERNOTFOUND);
	}

	// User can't update customer user as we got the error message: id value
	// forbidden for non admin user type, so I put this method at the end of the
	// class
	@Test(dependsOnMethods = "loginAsTheDeletedUser")
	public void updateAdminInfo() throws InterruptedException {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		wait = new WebDriverWait(driver, 60);

		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, "cuserdefault@abb.com");
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, "Testuser1");
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));

		assertEquals(table.getValueRow(2, 1), "Administrator");
		assertEquals(table.getValueRow(3, 1), USEREMAIL_ADMIN);
		assertEquals(table.getValueRow(4, 1), "CUST_ADMIN");
		assertEquals(table.getValueRow(5, 1), ACTIVESTATUS);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.ADMINUSERNUMBER_ID));
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));

		action.assertFieldReadOnly(By.id(Users.USERNUMBER_ID));
		action.assertFieldReadOnly(By.id(Users.USER_ID));

		System.out.print(action.getAttribute(By.id(Users.USER_ID)) + "Admin User Id Value");
		assertEquals(action.getAttribute(By.id(Users.USER_ID)), "Administrator");
		System.out.print(action.getAttribute(By.id(Users.EMAIL_ID)) + "Admin Email Value");
		assertEquals(action.getAttribute(By.id(Users.EMAIL_ID)), "cadmin1@abb.com");
		assertEquals(action.isElementPresent(By.cssSelector(CustomerUsers.USERGROUP_GRID)), false);

		action.inputTextField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, NEWUSEREMAIL_ADMIN);

		driver.findElement(By.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)).findElement(By.tagName("label")).click();

		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, NEWPASSWORD_ADMIN);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, NEWPASSWORD_ADMIN);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.SUCCESS)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.SUCCESS)).getText(), Messages.USER_UPDATE_SUCCESSFULLY);

		assertEquals(table.getValueRow(2, 1), "Administrator");
		assertEquals(table.getValueRow(3, 1), USEREMAIL_ADMIN);
		assertEquals(table.getValueRow(4, 1), "CUST_ADMIN");
		assertEquals(table.getValueRow(5, 1), ACTIVESTATUS);
		action.assertTitleScreen("Maintain Customer Users");
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, NEWUSEREMAIL_ADMIN);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, NEWPASSWORD_ADMIN);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER));
		action.assertTitleScreen("Customer Dashboard");
	}
}

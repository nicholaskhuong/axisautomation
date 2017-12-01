package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.junit.Assert;
import org.openqa.selenium.By;
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
	int i;
	String defaultUser = "cuserdefault@abb.com";
	String defaultPass = "Testuser1";
	
	String userID = "createdby_nick";
	String customerUserEmail = "nickcustomeruser@abb.com";
	String password = "Testuser2";
	String confirmPassword = "Testuser2";
	String userGroupName = "All Permissions";
	String newUserGroupName = "NoInvoice";
	String createdStatus = "Created";
	String activeStatus = "Active";
	String newPassword = "Testuser1";
	//String userEmailAdmin = "cadmin1@abb.com";

	// New info for customer user to use for updating
	String userID2 = "CU Updated";
	String userEmail2 = "cuupdated@abb.com";
	String newPassword2 = "Testuser3";
	String newPassword3 = "Testuser4";
	String userNo = "";

	@Test
	public void openUsersScreen(){
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
	@Test(dependsOnMethods = "openUsersScreen", alwaysRun = true)
	public void createNewUser() {
		i = table.findRowByString(1, customerUserEmail);
		if (i > 0) {
			action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
			action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
			action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
			action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
			assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_DELETE_SUCCESSFULLY);
		}
		i = table.findRowByString(1, userEmail2);
		if (i > 0) {
			action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
			action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
			action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
			action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
			assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_DELETE_SUCCESSFULLY);
		}
		assertEquals(table.isValueExisting(3, customerUserEmail), false, "User exists! Can't create a new user with the same email");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.inputEmailField(CustomerUsers.USERID_TEXTBOX_ID, userID);
		action.inputEmailField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, customerUserEmail);
		action.inputEmailField(CustomerUsers.PASSWORD_TEXTBOX_ID, password);
		action.inputEmailField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, confirmPassword);
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, userGroupName);
		action.clickBtn(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
		i = table.findRowByString(1, customerUserEmail);
		assertEquals(table.getValueRow(2, i), userID);
		assertEquals(table.getValueRow(3, i), userGroupName);
		assertEquals(table.getValueRow(4, i), createdStatus);
	}

	@Test(dependsOnMethods = "createNewUser", alwaysRun = true)
	public void loginAsNewUserAndCheckInfo() {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));

		action.inputEmailField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, customerUserEmail);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));

		
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, newPassword);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID, newPassword);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		
		
		action.waitObjVisible(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER));
		action.assertTitleScreen("Customer Dashboard");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));

		
		i = table.findRowByString(1, customerUserEmail);
		assertEquals(table.getValueRow(2, i), userID);
		assertEquals(table.getValueRow(3, i), userGroupName);
		assertEquals(table.getValueRow(4, i), activeStatus);

	}

	@Test(dependsOnMethods = "loginAsNewUserAndCheckInfo", alwaysRun = true)
	public void logOutNewUser() {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));

	}
	
	// Step 2 Customer user update other user
	@Test(dependsOnMethods = "logOutNewUser", alwaysRun = true)
	public void loginToDefaultUser() {
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, defaultUser);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, defaultPass);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		
	}
	
	// User update other user's info
	@Test(dependsOnMethods = "loginToDefaultUser")
	public void openUsersScreenAndAssertUserInfoInGrid() {
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		
		
		i = table.findRowByString(1, customerUserEmail);
		org.testng.Assert.assertTrue(1 >= 0, "User not found!");
		assertEquals(table.getValueRow(2, i), userID);
		assertEquals(table.getValueRow(3, i), userGroupName);
		assertEquals(table.getValueRow(4, i), activeStatus);

	}
	@Test(dependsOnMethods = "openUsersScreenAndAssertUserInfoInGrid", alwaysRun = true)
	public void updateUserStatus() {

		// Update status to Inactive
		table.clickUserNo(i);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.pause(500);
		
		action.assertFieldReadOnly(By.id(Users.USERNUMBER_ID));
		assertEquals(action.getAttribute(By.id(Users.USER_ID)), userID);
		assertEquals(action.getAttribute(By.id(Users.EMAIL_ID)), customerUserEmail);
		
		action.clickBtn(By.id(Users.STATUS_ID));
		action.pause(1000);
		
		action.selectStatus(CustomerUsers.STATUSLIST, "Inactive");
		action.pause(500);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.pause(1000);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);
		
		assertEquals(table.getValueRow(4, i), "Inactive");
		
	}
	@Test(dependsOnMethods = "updateUserStatus", alwaysRun = true)
	public void loginToInactiveUser() {
		
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));

		action.inputEmailField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, customerUserEmail);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_CSS)).getText(), "User inactive");
	}
	
	@Test(dependsOnMethods = "loginToInactiveUser", alwaysRun = true)
	public void loginToDefaultUser3nd() {

		action.inputEmailField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, defaultUser);
		action.inputEmailField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, defaultPass);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
	}
	
	
	@Test(dependsOnMethods = "loginToDefaultUser3nd", alwaysRun = true)
	public void updateUserGroup() {
		action.pause(1000);
		// Update user group to "NoInvoice" and Active
		table.clickUserNo(i);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.pause(500);
		
		// Check All Permissions group is selected
		action.checkObjSelected(0);
		
		action.clickBtn(By.id(Users.STATUS_ID));
		action.pause(500);
		action.selectStatus(CustomerUsers.STATUSLIST, activeStatus);
		// Unselect All Permissions group
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, userGroupName);
		action.pause(500);
		
		// Select NoInvoice group
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, newUserGroupName);
		
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);
		action.pause(1000);
		assertEquals(table.getValueRow(3, i), newUserGroupName);
		
		
	}

	@Test(dependsOnMethods = "updateUserGroup", alwaysRun = true)
	public void updateUserIDAndUserEmail() {
		
		table.clickUserNo(i);
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.pause(500);
		
		driver.findElement(By.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)).findElement(By.tagName("label")).isSelected();

		action.inputTextField(Users.USER_ID, userID2);
		action.inputEmailField(Users.EMAIL_ID, userEmail2);

		action.clickYesUpdatePasswordRadio();
		
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, newPassword2);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, newPassword2);
		
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);

		assertEquals(table.getValueRow(2, i), userID2);
		assertEquals(table.getValueRow(1, i), userEmail2);
		// Status is "Created" after updating password
		assertEquals(table.getValueRow(4, i), createdStatus);
	}
	
	@Test(dependsOnMethods = "updateUserIDAndUserEmail", alwaysRun = true)
	public void signOut() {
		action.signOut();
	}

	// Checking password expired appears here
	@Test(dependsOnMethods = "signOut")
	public void loginAgainWithNewInfo() {

		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, userEmail2);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, newPassword2);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, newPassword2);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, newPassword3);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID, newPassword3);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));

		action.waitObjVisible(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER));
		action.assertTitleScreen("Customer Dashboard");
	}

	@Test(dependsOnMethods = "loginAgainWithNewInfo", alwaysRun = true)
	public void signOut2nd() {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
	}

	@Test(dependsOnMethods = "signOut2nd", alwaysRun = true)
	public void loginToDefaultUser2nd() {

		action.inputEmailField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, defaultUser);
		action.inputEmailField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, defaultPass);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
	}

	
	@Test(dependsOnMethods = "loginToDefaultUser2nd")
	public void deleteCustomerAdmin(){

		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN)).getAttribute("aria-disabled"), "true");

	}

	// Click Trash Bin icon of the user to test
	@Test(dependsOnMethods = "deleteCustomerAdmin", alwaysRun = true)
	public void clickTrashBinIconToDeleteUser() {

		i = table.findRowByString(1, customerUserEmail);
		if (i <= 0) {
			i = table.findRowByString(1, userEmail2);
		}
		Assert.assertTrue("User doesn't exist", i > 0);
		action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);

	}

	@Test(dependsOnMethods = "clickTrashBinIconToDeleteUser")
	public void clickNoButton() {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.CONFIRMATION)), false);
	}

	// Step 4 Click Trash Bin and choose Yes
	@Test(dependsOnMethods = "clickTrashBinIconToDeleteUser")
	public void clickYesButton() {

		i = table.findRowByString(1, customerUserEmail);
		if (i <= 0) {
			i = table.findRowByString(1, userEmail2);
		}
		Assert.assertTrue("User doesn't exist", i > 0);
		action.clickBtn(By.id("deleteItemBtn" + (i - 1)));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_DELETE_SUCCESSFULLY);

		assertEquals(table.isValueExisting(3, userEmail2), false);
	}

	@Test(dependsOnMethods = "clickYesButton")
	public void loginAsDeletedUser() {

		action.clickBtn(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.waitObjVisible(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, userEmail2);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, newPassword3);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_CSS)).getText(), Messages.USERNOTFOUND);
	}

	@Test(dependsOnMethods = "loginAsDeletedUser")
	public void updateAdminInfo() {
		action.signIn(defaultUser, defaultPass);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.clickBtn(By.id(CustomerUsers.ADMINUSERNUMBER_ID));
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));

		action.assertFieldReadOnly(By.id(CustomerUsers.USERNUMBER_MODIFYSCREEN_ID));
		assertEquals(action.isFieldDisable(By.id(CustomerUsers.SAVE_BUTTON_ID)), true);
		action.assertFieldReadOnly(By.id(Users.USER_ID));
		action.assertFieldReadOnly(By.id(Users.EMAIL_ID));
}
}

package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerList;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ResetUserPassword;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "423")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Customer_Creating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;
	Random rand = new Random();
	int drand = rand.nextInt(10000);
	String customerName = String.format("customer %s", drand);
	String emailAddress = String.format("customer%s@abb.com", drand);
	String newPassword = "Testuser1";
	String password;
	String passwordUser = "Testuser2";
	String userID = String.format("customer_user %s", drand);
	String emailUser = String.format("customer_user%s@abb.com", drand);
	String confirmPassword = "Testuser2";

	@Test
	public void openCustomerScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_CUSTOMERS_PAGE);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		assertEquals(table.getValueTableHeader(1), "ID");
		assertEquals(table.getValueTableHeader(2), "Name");
		assertEquals(table.getValueTableHeader(3), "Status");
		assertEquals(table.getValueTableHeader(4), "Email");
	}

	// Step 2
	@Test(dependsOnMethods = "openCustomerScreen", alwaysRun = true)
	public void clickAddButtonAndInputLackMandatoryFields() {
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ADD_BUTTON));
		// Don't input email address
		action.inputTextField(CustomerList.CUSTOMER_NAME, customerName);
		action.waitObjVisibleAndClick(By.id(CustomerList.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// Don't select Authorised Document Types
		action.inputTextField(CustomerList.EMAIL_ADDRESS, emailAddress);
		action.waitObjVisibleAndClick(By.id(CustomerList.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.CUSTOMER_SELECT_USERGROUP);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// Upload LOGO
		action.pause(waitTime);
		action.clickCheckBoxN(0);
		// Can't automation.
		// Only manual test
		// action.inputTextField(Users.HOME_URL_FIELS, "apple.JPG");
		// action.pause(waitTime);
		// action.waitObjVisibleAndClick(By.id(CustomerAdmin.SAVE_BUTTON_ID));
		// action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
		// Messages.ENTER_MANDATORY_FIELDS);
	}

	// Step 3 and Step 4 Can't automation

	// Step 5
	@Test(dependsOnMethods = "clickAddButtonAndInputLackMandatoryFields", alwaysRun = true)
	public void inputDataOnCustomerListScreen() {
		action.waitObjVisibleAndClick(By.id(CustomerList.SAVE_BUTTON_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.CREATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "inputDataOnCustomerListScreen", alwaysRun = true)
	public void openResetUserPasswordScreen() {
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.RESET_USER_PASSWORD));
	}

	@Test(dependsOnMethods = "openResetUserPasswordScreen", alwaysRun = true)
	public void enterEmaidId() {
		action.waitObjVisibleAndClick(By.id(ResetUserPassword.EMAIL_ID_CHECK));
		action.pause(waitTime);
		action.inputTextField(ResetUserPassword.EMAIL_ID, emailAddress);
		action.waitObjVisibleAndClick(By.id(ResetUserPassword.RESET));
	}

	@Test(dependsOnMethods = "enterEmaidId", alwaysRun = true)
	public void getPassworkOfNewCustomerList() {
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		String message = driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText();
		System.out.println("Message: " + message);
		password = action.getPassword(message);
	}

	@Test(dependsOnMethods = "getPassworkOfNewCustomerList", alwaysRun = true)
	public void logoutAndLoginWithNewCustomerList() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(emailAddress, password);
	}

	@Test(dependsOnMethods = "logoutAndLoginWithNewCustomerList", alwaysRun = true)
	public void changePasswordOnAxisSupplierPortal() {
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, newPassword);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID, newPassword);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
	}

	@Test(dependsOnMethods = "changePasswordOnAxisSupplierPortal", alwaysRun = true)
	public void openUserGroupsWithAccountCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USER_GROUP_CUSTOMER));
	}

	@Test(dependsOnMethods = "openUserGroupsWithAccountCustomer", alwaysRun = true)
	public void createUserGroup() {
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ADD_BUTTON));
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, "Invoicing");
		action.clickCheckBoxN(2);
		action.waitObjVisibleAndClick(By.id(CustomerList.SAVE_BUTTON_ID));
	}

	@Test(dependsOnMethods = "createUserGroup", alwaysRun = true)
	public void openUsersScreenWithAccountCustomer() {
		action.waitObjVisible(By.cssSelector(AxisConfigMenu.USER_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USER_CUSTOMER));
	}

	@Test(dependsOnMethods = "openUsersScreenWithAccountCustomer", alwaysRun = true)
	public void clickAddButtonOnMaintainCustomerAdmin() {
		action.pause(waitTime);
		action.waitObjVisible(By.cssSelector(CustomerList.ADD_BUTTON));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ADD_BUTTON));
		action.inputTextField(Users.USER_ID, userID);
		action.inputTextField(Users.PASSWORD_ID, passwordUser);
		action.inputTextField(Users.EMAIL_ID, emailUser);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirmPassword);
		action.clickCheckBoxN(0);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
	}
}

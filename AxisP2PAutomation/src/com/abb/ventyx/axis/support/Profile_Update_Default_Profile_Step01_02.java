package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerList;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ResetUserPassword;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "789")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Profile_Update_Default_Profile_Step01_02 extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	public static String customerName = "Honda %s";
	public static String emailAddress = "Honda%s@abb.com";
	String password;
	String newPassword = "Testuser1";
	String profileName = "All Document Types";
	WebElement index;
	String mailAdminLogin = "axis_support@abb.com";
	String PasswordAdmin = "Testuser1";
	int i;

	// Step 1
	@Test
	public void openCustomersScreen() {
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

	@Test(dependsOnMethods = "openCustomersScreen", alwaysRun = true)
	public void clickAddButtonOnMaintainCustomerScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisible(By.id(CustomerList.CUSTOMER_NAME));
		action.waitObjVisible(By.id(CustomerList.EMAIL_ADDRESS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.CREATE_NEW_CUSTOMERS_PAGE);
	}

	@Test(dependsOnMethods = "clickAddButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void inputDataOnCustomerListScreen() {
		Random rand = new Random();
		int drand = rand.nextInt(10000);
		customerName = String.format("Honda %s", drand);
		emailAddress = String.format("Honda%s@abb.com", drand);

		action.inputTextField(CustomerList.CUSTOMER_NAME, customerName);
		action.inputEmailField(CustomerList.EMAIL_ADDRESS, emailAddress);
		action.clickCheckBoxN(0);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.CREATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "inputDataOnCustomerListScreen", alwaysRun = true)
	public void checkDataAgainAfterCreated() {
		table.clickFilterAndInputWithColumn(customerName, CustomerList.NAME_FILTER, true);
		action.pause(waitTime);
		Assert.assertTrue(i >= 0, String.format("Name: %s not found!", customerName));
	}

	@Test(dependsOnMethods = "checkDataAgainAfterCreated", alwaysRun = true)
	public void openResetUserPasswordScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.RESET_USER_PASSWORD));
		action.waitObjVisible(By.id(ScreenObjects.RESET_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), ResetUserPassword.TITLE_RESET_USER_PASSWORD);
	}

	@Test(dependsOnMethods = "openResetUserPasswordScreen", alwaysRun = true)
	public void enterEmaidId() {
		action.waitObjVisibleAndClick(By.id(ResetUserPassword.EMAIL_ID_CHECK));
		action.pause(waitTime);
		action.inputEmailField(ResetUserPassword.EMAIL_ID, emailAddress);
		action.waitObjVisibleAndClick(By.id(ResetUserPassword.RESET));
	}

	@Test(dependsOnMethods = "enterEmaidId", alwaysRun = true)
	public void getPassworkOfNewCustomerList() {
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		String message = driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText();
		password = action.getPassword(message);
	}

	// Step 2
	@Test(dependsOnMethods = "getPassworkOfNewCustomerList", alwaysRun = true)
	public void signOutCurrentUser() {
		action.signOut();
	}

	@Test(dependsOnMethods = "signOutCurrentUser", alwaysRun = true)
	public void logoutAndLoginWithNewCustomerList() {
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
	public void openMaintainCustomerScreenWithAccountCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Profiles.TITLE_PROFILES);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
	}

	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer", alwaysRun = true)
	public void verifyDocumentTypesOnMaintainCustomerScreen() {
		action.pause(waitTime);
		assertEquals(table.getValueRow(1, 1), "All Document Types");
	}

	@Test(dependsOnMethods = "verifyDocumentTypesOnMaintainCustomerScreen", alwaysRun = true)
	public void logoutWithCustomerList() {
		action.signOut();
	}
}

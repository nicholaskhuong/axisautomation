package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "700")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Customer_Creating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;
	public static String user = "customer_userab";
	public static String email = "customer_userab@abb.com";
	String password = "Testuser1";
	String emailInvalid = "111";
	String emailAlready = "thuy15@abb.com";
	String confirmPassword = "Testuser2";
	String invalidPassword1 = "111";
	String invalidPassword2 = "testuser";

	// Step 1
	@Test
	public void openCustomerScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Users.TITLE_MAINTENANCE_USERS);
		assertEquals(table.getValueTableHeader(1), "ID");
		assertEquals(table.getValueTableHeader(2), "Name");
		assertEquals(table.getValueTableHeader(3), "Status");
	}

	// Step 2
	@Test(dependsOnMethods = "openCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnCustomerScreen() {
		table.clikFilterAndInputWithColumn("435", Users.USER_NUMBER_FILTER, true);
		action.pause(waitTime);
		// assertEquals(table.getValueRow(2, 1), "QATest");
	}

	// Step3
	@Test(dependsOnMethods = "clickFiterButtonOnCustomerScreen", alwaysRun = true)
	public void clickCustomerIDOnCustomerScreen() {
		index = table.getCellObject(
				"//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div/div/div/div[3]/table/tbody", 1, 1);
		action.pause(2000);
		index.click();
		action.pause(2000);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_MAINTAIN_CSS)).getText(), Users.TITLE_MAINTENANCE_CUSTOMER_USERS);
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
	}

	// Step 4
	@Test(dependsOnMethods = "clickCustomerIDOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerUsersScreen() {
		action.pause(2000);
		table.clikFilterAndInputWithColumn("axisuser1", Users.USER_ID_FILTER, true);
		assertEquals(table.getValueRow(2, 1), "axisuser1");
	}

	// Step 5
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerUsersScreen", alwaysRun = true)
	public void clickAddButtonAndInputLackMandatoryFields() {
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 100000000L);
		user = String.format("customer_usera%s", drand);
		email = String.format("customer_usera%s@abb.com", drand);
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_IN_TITLE_CSS)).getText(), Users.CREATE_USER);
		action.inputTextField(Users.USER_ID, user);
		action.inputTextField(Users.PASSWORD_ID, password);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 6
	@Test(dependsOnMethods = "clickAddButtonAndInputLackMandatoryFields", alwaysRun = true)
	public void inputMandatoryFields() {
		action.inputEmailField(Users.EMAIL_ID, email);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, password);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 7
	@Test(dependsOnMethods = "inputMandatoryFields", alwaysRun = true)
	public void inputInvalidEmail() {
		action.inputEmailField(Users.EMAIL_ID, emailInvalid);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, password);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputEmailField(Users.EMAIL_ID, emailAlready);
		action.clickCheckBoxN(6);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SAME_EMAIL);
	}

	// Step 8
	@Test(dependsOnMethods = "inputInvalidEmail", alwaysRun = true)
	public void inputInvalidPassword() {
		action.inputTextField(Users.PASSWORD_ID, invalidPassword1);
		action.inputEmailField(Users.EMAIL_ID, email);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, invalidPassword1);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD1);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputTextField(Users.PASSWORD_ID, invalidPassword2);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, invalidPassword2);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD2);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 9
	@Test(dependsOnMethods = "inputInvalidPassword", alwaysRun = true)
	public void inputPasswordAndConfirmPasswordAreNotTheSame() {
		action.inputTextField(Users.PASSWORD_ID, password);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirmPassword);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.UNMATCHED_CONFIRM_PWD);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 10
	@Test(dependsOnMethods = "inputPasswordAndConfirmPasswordAreNotTheSame", alwaysRun = true)
	public void inputAllMandatoryFields() {
		action.inputTextField(Users.CONFIMRPASSWORD_ID, password);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 11
	@Test(dependsOnMethods = "inputAllMandatoryFields", alwaysRun = true)
	public void clickAddButtonAndNoInputData() {
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
	}

	// Step 12_13_14
	@Test(dependsOnMethods = "clickAddButtonAndNoInputData", alwaysRun = true)
	public void clickAddButtonAndInputDataName() {
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.inputTextField(Users.USER_ID, user);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
	}
}

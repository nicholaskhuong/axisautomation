package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "602")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_Created_ByAdmin extends BaseTestCase{
	public static String USERID = "Auto740";
	public static String USEREMAILADDRESS ="cuser740@abb.com";
	String PASSWORD ="Testuser2";
	String CONFIRMPASSWORD ="Testuser3";
	String INVALIDEMAIL = "<HTML>";
	String INVALIDPASSWORD = "12345";
	String USERGROUPNAME1 = "All Permissions";
	TableFunction table;
	ScreenAction action;

	// Step 1 Open Users sub menu
	@Test
	public void selectUsersSubMenu() {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 2 Check error message in case inputting lack of mandatory field.
	@Test(dependsOnMethods="selectUsersSubMenu")
	public void addNewUserWithoutMandatoryField() {
			
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.waitObjVisible(By.id(CustomerUsers.USERID_TEXTBOX_ID));
		action.assertTitleScreen("Create User");
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000L);
		USERID = String.format("Auto%s", drand);
		USEREMAILADDRESS = String.format("cuser%s@abb.com", drand);
		// Case 1: User ID is empty
		action.inputTextField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, USEREMAILADDRESS);

		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, PASSWORD);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, PASSWORD);
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// Case 2: User Email Address ID is empty
		driver.findElement(By.id(CustomerUsers.USERID_TEXTBOX_ID)).sendKeys(USERID);
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// Case 3: Password is empty
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(USEREMAILADDRESS);
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		// Case 4: Confirm Password is empty
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));

	}

	// Step 3 Input mandatory field, don't select user group
	@Test(dependsOnMethods="addNewUserWithoutMandatoryField")
	public void addNewUserWithoutUserGroup() {
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 4 Input invalid e-mail
	@Test(dependsOnMethods="addNewUserWithoutUserGroup")
	public void addNewUserWithInvalidEmail() {
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);

		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).clear();

		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(INVALIDEMAIL);

		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();

		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 5 Input invalid password
	@Test(dependsOnMethods="addNewUserWithInvalidEmail")
	public void addNewUserWithInvalidPassword(){
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(USEREMAILADDRESS);
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();

		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_PWD);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 6 Password and confirm password does not match
	@Test(dependsOnMethods="addNewUserWithInvalidPassword")
	public void addNewUserWithUnmatchedPassword() {
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, PASSWORD);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, CONFIRMPASSWORD);
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.UNMATCHED_CONFIRM_PWD);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 7 Create a new customer user successfully
	@Test(dependsOnMethods="addNewUserWithUnmatchedPassword")
	public void addNewUserWithValidValue() {
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON));
		action.pause(2000);
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 8 Check cancel button on unsaved changes Dialog
	@Test(dependsOnMethods="addNewUserWithUnmatchedPassword")
	public void cancelCreateNewUserWithoutInput() {
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		action.waitObjVisible(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID));
		action.assertTitleScreen("Create User");
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 9, 10, 11, 12 Check cancel button on unsaved changes Dialog
	@Test(dependsOnMethods="cancelCreateNewUserWithoutInput")
	public void cancelCreateNewUserWithInput() {
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		action.waitObjVisible(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID));
		action.assertTitleScreen("Create User");
		action.inputEmailField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, USEREMAILADDRESS);
		
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(1000);
		action.waitObjVisible(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID));
		action.assertTitleScreen("Create User");
		assertEquals(action.isElementPresent(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)), true);
		
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();

		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 14 

}

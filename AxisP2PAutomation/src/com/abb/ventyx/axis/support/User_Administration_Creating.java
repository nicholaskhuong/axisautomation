package com.abb.ventyx.axis.support;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "203")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Administration_Creating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1500;
	WebElement index;
	String user = "Taumato";
	String user1 = "Tauuu";
	String password = "Testuser1";
	String email = "Taumato@abb.com";
	String emailAlready = "thuy15@abb.com";
	String confirmPassword = "Testuser2";
	String passwordReset;
	String newPassword = "Testuser2";
	//Step 1
	@Test
	public void openUserScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID));
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
	}
	
	//Step 2
	@Test(dependsOnMethods = "openUserScreen", alwaysRun = true)
	public void clickAddButtonAndInputLackMandatoryFields(){
		action.waitObjVisibleAndClick(By.cssSelector(Users.ADD_USERS));
		action.inputTextField(Users.USER_ID, user);
		action.inputTextField(Users.PASSWORD_ID, password);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	
	//Step 3
	@Test(dependsOnMethods = "clickAddButtonAndInputLackMandatoryFields", alwaysRun = true)
	public void inputMandatoryFields(){
		action.waitObjVisible(By.id(Users.EMAIL_ID));
		action.inputEmailField(Users.EMAIL_ID, email);
		action.waitObjVisible(By.id(Users.CONFIMRPASSWORD_ID));
		action.pause(waitTime);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, password);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	
	//Step 4
	@Test(dependsOnMethods = "inputMandatoryFields", alwaysRun = true)
	public void inputAllMandatoryFields(){
		action.clickCheckBoxN(0);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}
	
	//Step 5
	@Test(dependsOnMethods = "inputAllMandatoryFields", alwaysRun = true)
	public void inputInvalidEmail(){
		action.waitObjVisibleAndClick(By.cssSelector(Users.ADD_USERS));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, user1);
		action.waitObjVisible(By.id(Users.EMAIL_ID));
		action.inputEmailField(Users.EMAIL_ID, emailAlready);
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		action.inputTextField(Users.PASSWORD_ID, password);
		action.waitObjVisible(By.id(Users.CONFIMRPASSWORD_ID));
		action.inputTextField(Users.CONFIMRPASSWORD_ID, password);
		action.clickCheckBoxN(6);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SAME_EMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}
	
	//Steps 06_08
	@Test(dependsOnMethods = "inputInvalidEmail", alwaysRun = true)
	public void clickAddButtonAndNoInputData(){
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
	}
	
	//Step 9_10
	@Test(dependsOnMethods = "clickAddButtonAndNoInputData", alwaysRun = true)
	public void clickAddButtonAndInputDataName(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, user);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
	}
	
	//Step 11
	@Test(dependsOnMethods = "clickAddButtonAndInputDataName", alwaysRun = true)
	public void logoutAndLoginWithNewUser() {
		action.pause(waitTime);
		action.signOut();
		action.pause(waitTime);
		action.signIn(email, password);
	}
	
	@Test(dependsOnMethods = "logoutAndLoginWithNewUser", alwaysRun = true)
	public void changePasswordOnAxisSupplierPortal() {
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, newPassword);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID,newPassword);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
	}
	
	@Test(dependsOnMethods = "changePasswordOnAxisSupplierPortal", alwaysRun = true)
	public void openMaintainCustomerScreenWithNewUser(){
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USER_CUSTOMER_ADMIN));
	}
}


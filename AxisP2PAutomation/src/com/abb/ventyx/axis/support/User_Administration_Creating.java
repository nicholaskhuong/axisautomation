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
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Administration_Creating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 3000;
	WebElement index;
	String USER = "Taumato";
	String USER1 = "Tauuu";
	String PASSWORD = "Testuser1";
	String EMAIL = "Taumato@abb.com";
	String EMAIL_ALREADY = "thuy15@abb.com";
	String CONFIRM_PASSWORD = "Testuser2";
	String passwordReset;
	String NEWPASSWORD = "Testuser2";
	//Step 1
	@Test
	public void openUserScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID));
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
		action.pause(waitTime);
	}
	
	//Step 2
	@Test(dependsOnMethods = "openUserScreen", alwaysRun = true)
	public void clickAddButtonAndInputLackMandatoryFields(){
		action.waitObjVisibleAndClick(By.cssSelector(Users.ADD_USERS));
		action.inputTextField(Users.USER_ID, USER);
		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
	}
	
	//Step 3
	@Test(dependsOnMethods = "clickAddButtonAndInputLackMandatoryFields", alwaysRun = true)
	public void inputMandatoryFields(){
		action.pause(waitTime);
		WebElement id = driver.findElement(By.id(Users.USER_ID));
		id.clear();
		action.pause(waitTime);
		id.sendKeys(USER);
		action.pause(6000);
		WebElement password = driver.findElement(By.id(Users.PASSWORD_ID));
		password.clear();
		action.pause(waitTime);
		password.sendKeys(PASSWORD);
		action.pause(waitTime);
		action.inputEmailField(Users.EMAIL_ID, EMAIL);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
	}
	
	//Step 4
	@Test(dependsOnMethods = "inputMandatoryFields", alwaysRun = true)
	public void inputAllMandatoryFields(){
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(PASSWORD);
		action.clickCheckBoxN(0);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
	}
	
	//Step 5
	@Test(dependsOnMethods = "inputAllMandatoryFields", alwaysRun = true)
	public void inputInvalidEmail(){
		action.waitObjVisibleAndClick(By.cssSelector(Users.ADD_USERS));
		action.inputTextField(Users.USER_ID, USER1);
		action.inputEmailField(Users.EMAIL_ID, EMAIL_ALREADY);
		WebElement password = driver.findElement(By.id(Users.PASSWORD_ID));
		password.clear();
		action.pause(waitTime);
		password.sendKeys(PASSWORD);
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(PASSWORD);
		action.clickCheckBoxN(6);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SAME_EMAIL);
		action.pause(waitTime);
	}
	
	//Steps 06_08
	@Test(dependsOnMethods = "inputInvalidEmail", alwaysRun = true)
	public void clickAddButtonAndNoInputData(){
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.pause(waitTime);
		action.clickBtn(By.id(Users.CANCEL_BTN_ID));
	}
	
	//Step 9_10
	@Test(dependsOnMethods = "clickAddButtonAndNoInputData", alwaysRun = true)
	public void clickAddButtonAndInputDataName(){
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.pause(waitTime);
		action.inputTextField(Users.USER_ID, USER);
		action.clickBtn(By.id(Users.CANCEL_BTN_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(Users.CANCEL_BTN_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(waitTime);
	}
	
	//Step 11
	@Test(dependsOnMethods = "clickAddButtonAndInputDataName", alwaysRun = true)
	public void logoutAndLoginWithNewUser() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(EMAIL, PASSWORD);
	}
	
	@Test(dependsOnMethods = "logoutAndLoginWithNewUser", alwaysRun = true)
	public void changePasswordOnAxisSupplierPortal() {
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, PASSWORD);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, NEWPASSWORD);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID,NEWPASSWORD);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
	}
	
	@Test(dependsOnMethods = "changePasswordOnAxisSupplierPortal", alwaysRun = true)
	public void openMaintainCustomerScreenWithNewUser(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USER_CUSTOMER_ADMIN));
		action.pause(waitTime);
	}
	}


package com.abb.ventyx.axis.support;


import static org.testng.Assert.assertEquals;

import java.util.List;

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

@ALM(id = "204")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Administration_Updating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;
	String user = "Taumato";
	String user1 = "Tauuu";
	String password = "Testuser1";
	String email = "Taumato@abb.com";
	String emailAlready = "thuy15@abb.com";
	String confirmPassword = "Testuser2";
	String passwordReset;
	String newPassword = "Testuser2";
	String emailInvalid = "111";
	String userUpdate = "Taumato1";
	String emailUpdate = "Taumato1@abb.com";
	String invalidPassword1 = "111";
	String invalidPassword2 = "testuser";
	//Step 1
	@Test
	public void openUserScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID));
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
	}
	
	//Step 2
	@Test(dependsOnMethods = "openUserScreen", alwaysRun = true)
	public void clickFiterButtonOnUserScreen() {
		table = new TableFunction(driver);
		table.inputFilter(user, Users.USER_ID_FILTER , true);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH,1, 1);
		index.click();
	}
	
	//Step 3
	@Test(dependsOnMethods = "clickFiterButtonOnUserScreen", alwaysRun = true)
	public void inputInvalidEmail(){
		action.waitObjVisible(By.id(Users.EMAIL_ID));
		action.inputEmailField(Users.EMAIL_ID, emailInvalid);
		action.clickCheckBoxN(6);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.waitObjVisible(By.id(Users.EMAIL_ID));
		action.inputEmailField(Users.EMAIL_ID, emailAlready);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.UPDATE_SAME_EMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}
	
	//Step 4
	@Test(dependsOnMethods = "inputInvalidEmail", alwaysRun = true)
	public void clickYesForUpdatePassword(){
		List<WebElement> onBtn = driver.findElements(By.id(AxisAdministratorUsers.UPDATED_PASSWORD_ID));
		onBtn.get(0).click();
	}
	
	@Test(dependsOnMethods = "clickYesForUpdatePassword", alwaysRun = true)
	public void inputInvalidPassword(){
		action.waitObjVisible(By.id(Users.EMAIL_ID));
		action.inputEmailField(Users.EMAIL_ID, emailUpdate);
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		action.inputTextField(Users.PASSWORD_ID, invalidPassword1);
		action.pause(waitTime);
		action.waitObjVisible(By.id(Users.CONFIMRPASSWORD_ID));
		action.inputTextField(Users.CONFIMRPASSWORD_ID, invalidPassword1);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD1);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		action.inputTextField(Users.PASSWORD_ID, invalidPassword2);
		action.waitObjVisible(By.id(Users.CONFIMRPASSWORD_ID));
		action.inputTextField(Users.CONFIMRPASSWORD_ID, invalidPassword2);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD2);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	//Step 5
	@Test(dependsOnMethods = "inputInvalidPassword", alwaysRun = true)
	public void inputPasswordAndConfirmPasswordAreNotTheSame(){
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		action.inputTextField(Users.PASSWORD_ID, password);
		action.pause(3000);
		action.waitObjVisible(By.id(Users.CONFIMRPASSWORD_ID));
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirmPassword);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.UNMATCHED_CONFIRM_PWD);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	
	//Step 6
	@Test(dependsOnMethods = "inputPasswordAndConfirmPasswordAreNotTheSame", alwaysRun = true)
	public void inputAllMandatoryFields(){
		action.pause(waitTime);
		action.waitObjVisible(By.id(Users.USER_ID));
		action.inputTextField(Users.USER_ID, userUpdate);
		action.waitObjVisible(By.id(Users.CONFIMRPASSWORD_ID));
		action.inputTextField(Users.CONFIMRPASSWORD_ID, password);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}
	
	//Step 7
	@Test(dependsOnMethods = "inputAllMandatoryFields", alwaysRun = true)
	public void ClickAnExistingRecord () {
		action.pause(waitTime);
		table.inputFilterAtIndex("Tau1", Users.USER_ID_FILTER , true);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH,1, 1);
		index.click();
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
	}
	
	//Steps 08_11
	@Test(dependsOnMethods = "ClickAnExistingRecord", alwaysRun = true)
	public void clickAddButtonAndInputDataName(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.inputTextField(Users.USER_ID, userUpdate);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
	}
	
	//Step 12
	@Test(dependsOnMethods = "clickAddButtonAndInputDataName", alwaysRun = true)
	public void logoutAndLoginWithNewUser() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(emailUpdate, password);
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

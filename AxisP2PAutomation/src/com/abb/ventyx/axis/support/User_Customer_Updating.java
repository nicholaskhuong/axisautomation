package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
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

@ALM(id = "711")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Customer_Updating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index, index1;
	String user = "customer_userab";
	String userUpdate = "customer_user1";
	String password = "Testuser1";
	String email = "customer_userab@abb.com";
	String emailUpdate = "customer_user1@abb.com";
	String emailInvalid = "111";
	String emailAlready = "thuy15@abb.com";
	String confirmPassword = "Testuser2";
	String invalidPassword1 = "111";
	String invalidPassword2 = "testuser";
	
	//Step 1
	@Test
	public void openCustomerScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERS));
	}
	
	//Step 2
	@Test(dependsOnMethods = "openCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnCustomerScreen() {
		table = new TableFunction(driver);
		table.inputFilter("435", Users.USER_NUMBER_FILTER, true);
		action.pause(waitTime);
		assertEquals(table.getValueRow(2, 1), "QATest");
	}
	
	//Step3
	@Test(dependsOnMethods = "clickFiterButtonOnCustomerScreen", alwaysRun = true)
	public void clickCustomerIDOnCustomerScreen() {
		index = table.getCellObject("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div/div/div/div[3]/table/tbody",1, 1);
		action.pause(waitTime);
		index.click();
	}
		
	//Step 4
	@Test(dependsOnMethods = "clickCustomerIDOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerUsersScreen() {
		action.pause(waitTime);
		table.inputFilter("customer_userab", Users.USER_ID_FILTER, true);
		action.pause(waitTime);
		assertEquals(table.getValueRow(2, 1), "customer_userab");
	}
		
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerUsersScreen", alwaysRun = true)
	public void clickUserNumberOnMaintainCustomerUsersScreen() {
		index1 = table.getCellObject("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div[3]/table/tbody",1, 1);
		index1.click();
	}
	
	@Test(dependsOnMethods = "clickUserNumberOnMaintainCustomerUsersScreen", alwaysRun = true)
	public void updateLackMandatoryField(){
		action.inputTextField(Users.EMAIL_ID, "");
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	
	//Step 4
	@Test(dependsOnMethods = "updateLackMandatoryField", alwaysRun = true)
	public void inputMandatoryFields(){
		action.inputTextField(Users.EMAIL_ID, emailUpdate);
		action.clickCheckBoxN(6);
		action.waitObjVisible(By.id(Users.SAVE_BTN_ID));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.USER_SELECT_USERGROUP);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	
	//Step 5
	@Test(dependsOnMethods = "inputMandatoryFields", alwaysRun = true)
	public void inputInvalidEmail(){
		action.inputEmailField(Users.EMAIL_ID, emailInvalid);
		action.clickCheckBoxN(6);
		action.waitObjVisible(By.id(Users.SAVE_BTN_ID));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputEmailField(Users.EMAIL_ID, emailAlready);
		action.waitObjVisible(By.id(Users.SAVE_BTN_ID));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.UPDATE_SAME_EMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}
	
	//Step 6
	@Test(dependsOnMethods = "inputInvalidEmail", alwaysRun = true)
	public void clickYesForUpdatePassword(){
		List<WebElement> onBtn = driver.findElements(By
				.id(AxisAdministratorUsers.UPDATED_PASSWORD_ID));
		onBtn.get(0).click();
	}
	@Test(dependsOnMethods = "clickYesForUpdatePassword", alwaysRun = true)
	public void inputInvalidPassword(){
		action.inputEmailField(Users.EMAIL_ID, emailUpdate);
		action.inputEmailField(Users.PASSWORD_ID, invalidPassword1);
		action.inputEmailField(Users.CONFIMRPASSWORD_ID, invalidPassword1);
		action.waitObjVisible(By.id(Users.SAVE_BTN_ID));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD1);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputTextField(Users.PASSWORD_ID, invalidPassword2);
		action.inputEmailField(Users.CONFIMRPASSWORD_ID, invalidPassword2);
		action.waitObjVisible(By.id(Users.SAVE_BTN_ID));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD2);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	
	//Step 7
	@Test(dependsOnMethods = "inputInvalidPassword", alwaysRun = true)
	public void inputPasswordAndConfirmPasswordAreNotTheSame(){
		action.inputEmailField(Users.PASSWORD_ID, password);
		action.inputEmailField(Users.CONFIMRPASSWORD_ID, confirmPassword);
		action.waitObjVisible(By.id(Users.SAVE_BTN_ID));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.UNMATCHED_CONFIRM_PWD);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}
	
	//Step 8
	@Test(dependsOnMethods = "inputPasswordAndConfirmPasswordAreNotTheSame", alwaysRun = true)
	public void inputAllMandatoryFields(){
		action.inputTextField(Users.USER_ID, userUpdate);
		action.inputEmailField(Users.CONFIMRPASSWORD_ID, password);
		action.waitObjVisible(By.id(Users.SAVE_BTN_ID));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}
	
	//Step 9
	@Test(dependsOnMethods = "inputAllMandatoryFields", alwaysRun = true)
	public void clickAddButtonAndNoInputData(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
	}
	
	//Step 10-13
	@Test(dependsOnMethods = "clickAddButtonAndNoInputData", alwaysRun = true)
	public void clickAddButtonAndInputDataName(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.inputTextField(Users.USER_ID, userUpdate);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.CANCEL_BTN_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
	}

}


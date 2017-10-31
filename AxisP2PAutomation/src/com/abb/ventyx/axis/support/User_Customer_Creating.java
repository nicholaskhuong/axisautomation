package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Customer_Creating extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 3000;
	WebElement index;
	String USER = "lead";
	String PASSWORD = "Testuser1";
	String EMAIL = "lead@abb.com";
	String EMAIL_INVALID = "111";
	String EMAIL_ALREADY = "thuy15@abb.com";
	String CONFIRM_PASSWORD = "Testuser2";
	String INVALID_PASSWORD1 = "111";
	String INVALID_PASSWORD2 = "testuser";
	
	//Step 1
	@Test
	public void openCustomerScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERS));
		action.pause(waitTime);
	}
	
	//Step 2
	@Test(dependsOnMethods = "openCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnCustomerScreen() {
		table = new TableFunction(driver);
		action.pause(2000);
		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "435");
		action.pause(waitTime);
		assertEquals(table.getValueRow(2, 1), "QATest");
	}
	
	//Step3
	@Test(dependsOnMethods = "clickFiterButtonOnCustomerScreen", alwaysRun = true)
	public void clickCustomerIDOnCustomerScreen() {
		action.pause(waitTime);
		index = table.getCellObject("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div/div/div/div[3]/table/tbody",1, 1);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", index);
	}
	
	//Step 4
	@Test(dependsOnMethods = "clickCustomerIDOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerUsersScreen() {
	    table = new TableFunction(driver);
		action.pause(5000);
		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "4016");
		action.pause(waitTime);
		assertEquals(table.getValueRow(2, 1), "abc");
	}
	
	//Step 5
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerUsersScreen", alwaysRun = true)
	public void clickAddButtonAndInputLackMandatoryFields(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.inputTextField(Users.USER_ID, USER);
		action.inputTextField(Users.PASSWORD_ID, PASSWORD);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
	}
	
	//Step 6
	@Test(dependsOnMethods = "clickAddButtonAndInputLackMandatoryFields", alwaysRun = true)
	public void inputMandatoryFields(){
		action.pause(waitTime);
		WebElement id = driver.findElement(By.id(Users.USER_ID));
		id.clear();
		action.pause(waitTime);
		id.sendKeys("lead");
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
	
	//Step 7
	@Test(dependsOnMethods = "inputMandatoryFields", alwaysRun = true)
	public void inputInvalidEmail(){
		action.inputEmailField(Users.EMAIL_ID, EMAIL_INVALID);
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(PASSWORD);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.inputEmailField(Users.EMAIL_ID, EMAIL_ALREADY);
		action.clickCheckBoxN(6);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SAME_EMAIL);
		action.pause(waitTime);
	}
	
	//Step 8
	@Test(dependsOnMethods = "inputInvalidEmail", alwaysRun = true)
	public void inputInvalidPassword(){
		WebElement password = driver.findElement(By.id(Users.PASSWORD_ID));
		password.clear();
		action.pause(waitTime);
		password.sendKeys(INVALID_PASSWORD1);
		action.inputEmailField(Users.EMAIL_ID, EMAIL);
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(INVALID_PASSWORD1);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD1);
		action.pause(waitTime);
		action.inputTextField(Users.PASSWORD_ID, INVALID_PASSWORD2);
		action.inputEmailField(Users.EMAIL_ID, EMAIL);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, INVALID_PASSWORD2);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD2);
	}
	
	//Step 9
	@Test(dependsOnMethods = "inputInvalidPassword", alwaysRun = true)
	public void inputPasswordAndConfirmPasswordAreNotTheSame(){
		WebElement password = driver.findElement(By.id(Users.PASSWORD_ID));
		password.clear();
		action.pause(waitTime);
		password.sendKeys(PASSWORD);
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(CONFIRM_PASSWORD);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.UNMATCHED_CONFIRM_PWD);
	}
	
	//Step 10
	@Test(dependsOnMethods = "inputPasswordAndConfirmPasswordAreNotTheSame", alwaysRun = true)
	public void inputAllMandatoryFields(){
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(PASSWORD);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
	}
	
	//Step 11
	@Test(dependsOnMethods = "inputAllMandatoryFields", alwaysRun = true)
	public void clickAddButtonAndNoInputData(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.pause(waitTime);
		action.clickBtn(By.id(Users.CANCEL_BTN_ID));
	}
	
	//Step 12_13_14
	@Test(dependsOnMethods = "clickAddButtonAndNoInputData", alwaysRun = true)
	public void clickAddButtonAndInputDataName(){
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
		}
	}


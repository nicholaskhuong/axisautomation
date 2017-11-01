package com.abb.ventyx.axis.support;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

@ALM(id = "204")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Administration_Updating extends BaseTestCase {
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
	String EMAIL_INVALID = "111";
	String USER_UPDATE = "Taumato1";
	String EMAIL_UPDATE = "Taumato1@abb.com";
	String INVALID_PASSWORD1 = "111";
	String INVALID_PASSWORD2 = "testuser";
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
	public void clickFiterButtonOnUserScreen() {
		table = new TableFunction(driver);
		action.pause(5000);
		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		WebElement filterPermissionName = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Users.USER_ID_FILTER)));
		filterPermissionName.sendKeys(USER);
		action.pause(5000);
		index = table.getCellObject("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div[3]/table/tbody",1, 1);
		action.pause(waitTime);      
		index.click();
		action.pause(waitTime);
	}
	
	//Step 3
	@Test(dependsOnMethods = "clickFiterButtonOnUserScreen", alwaysRun = true)
	public void inputInvalidEmail(){
		action.inputEmailField(Users.EMAIL_ID, EMAIL_INVALID);
		action.clickCheckBoxN(6);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(4000);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.inputEmailField(Users.EMAIL_ID, EMAIL_ALREADY);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.pause(4000);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.UPDATE_SAME_EMAIL);
	}
	
	//Step 4
	@Test(dependsOnMethods = "inputInvalidEmail", alwaysRun = true)
	public void clickYesForUpdatePassword(){
		action.pause(waitTime);
		List<WebElement> onBtn = driver.findElements(By.id(AxisAdministratorUsers.UPDATED_PASSWORD_ID));
		onBtn.get(0).click();
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "clickYesForUpdatePassword", alwaysRun = true)
	public void inputInvalidPassword(){
		action.inputEmailField(Users.EMAIL_ID, EMAIL_UPDATE);
		action.pause(waitTime);
		WebElement password = driver.findElement(By.id(Users.PASSWORD_ID));
		action.pause(waitTime);
		password.sendKeys(INVALID_PASSWORD1);
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(INVALID_PASSWORD1);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD1);
		action.pause(waitTime);
		action.inputTextField(Users.PASSWORD_ID, INVALID_PASSWORD2);
		action.pause(waitTime);
		WebElement comfirmPassword1 = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword1.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(INVALID_PASSWORD2);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INPUT_INVALID_PASSWORD2);
	}
	//Step 5
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
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.UNMATCHED_CONFIRM_PWD);
	}
	
	//Step 6
	@Test(dependsOnMethods = "inputPasswordAndConfirmPasswordAreNotTheSame", alwaysRun = true)
	public void inputAllMandatoryFields(){
		WebElement user = driver.findElement(By.id(Users.USER_ID));
		user.clear();
		action.pause(waitTime);
		user.sendKeys(USER_UPDATE);
		WebElement comfirmPassword = driver.findElement(By.id(Users.CONFIMRPASSWORD_ID));
		comfirmPassword.clear();
		action.pause(waitTime);
		comfirmPassword.sendKeys(PASSWORD);
		action.waitObjVisibleAndClick(By.id(Users.SAVE_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_UPDATE_SUCCESSFULLY);
	}
	
	//Step 7
	@Test(dependsOnMethods = "inputAllMandatoryFields", alwaysRun = true)
	public void ClickAnExistingRecord () {
		table = new TableFunction(driver);
		action.pause(5000);
		ScreenAction action = new ScreenAction(driver);
		WebElement filterPermissionName = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Users.USER_ID_FILTER)));
		filterPermissionName.clear();
		filterPermissionName.sendKeys("Tau1");
		action.pause(waitTime);
		index = table.getCellObject("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div[3]/table/tbody",1, 1);
		action.pause(waitTime);      
		index.click();
		action.pause(waitTime);
		action.clickBtn(By.id(Users.CANCEL_BTN_ID));
	}
	
	//Steps 08_11
	@Test(dependsOnMethods = "ClickAnExistingRecord", alwaysRun = true)
	public void clickAddButtonAndInputDataName(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
		action.pause(waitTime);
		action.inputTextField(Users.USER_ID, USER_UPDATE);
		action.clickBtn(By.id(Users.CANCEL_BTN_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(Users.CANCEL_BTN_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(waitTime);
	}
	
	//Step 12
	@Test(dependsOnMethods = "clickAddButtonAndInputDataName", alwaysRun = true)
	public void logoutAndLoginWithNewUser() {
		action.pause(waitTime);
		action.signOut();
		action.pause(waitTime);
		action.signIn(EMAIL_UPDATE, PASSWORD);
	}
	
	@Test(dependsOnMethods = "logoutAndLoginWithNewUser", alwaysRun = true)
	public void openMaintainCustomerScreenWithNewUser(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USER_CUSTOMER_ADMIN));
		action.pause(waitTime);
	}
}

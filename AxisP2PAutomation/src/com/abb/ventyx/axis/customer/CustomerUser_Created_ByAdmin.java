package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	String USERID ="Automator 1";
	String USEREMAILADDRESS ="cuser1@abb.com";
	String PASSWORD ="Testuser2";
	String CONFIRMPASSWORD ="Testuser3";
	String INVALIDEMAIL = "<HTML>";
	String INVALIDPASSWORD = "12345";
	String USERGROUPNAME1 = "All Permissions";
	TableFunction table;
	ScreenAction action;
	// Step 1 Open Users sub menu
	@Test
	public void selectUsersSubMenu() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement customerMaintenanceMenu = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU)));
		customerMaintenanceMenu.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(CustomerMenu.USERS_SUBMENU)));
		driver.findElement(By.cssSelector(CustomerMenu.USERS_SUBMENU)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		Thread.sleep(2000);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 2 Check error message in case inputting lack of mandatory field.
	@Test(dependsOnMethods="selectUsersSubMenu")
	public void addNewUserWithoutMandatoryField() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		Thread.sleep(1500);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");

		// Case 1: User ID is empty
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(USEREMAILADDRESS);
		Thread.sleep(100);
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);
		Thread.sleep(100);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);
		Thread.sleep(100);
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();	

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));

		// Case 2: User Email Address ID is empty
		driver.findElement(By.id(CustomerUsers.USERID_TEXTBOX_ID)).sendKeys(USERID);
		Thread.sleep(100);
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();	

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));

		// Case 3: Password is empty
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(USEREMAILADDRESS);
		Thread.sleep(100);
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));

		// Case 4: Confirm Password is empty
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);
		Thread.sleep(100);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));

	}

	// Step 3 Input mandatory field, don't select user group
	@Test(dependsOnMethods="addNewUserWithoutMandatoryField")
	public void addNewUserWithoutUserGroup() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		TableFunction action = new TableFunction(driver);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);
		action.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		Thread.sleep(500);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(), Messages.USER_SELECT_USERGROUP);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
	}

	// Step 4 Input invalid e-mail
	@Test(dependsOnMethods="addNewUserWithoutUserGroup")
	public void addNewUserWithInvalidEmail() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		TableFunction action = new TableFunction(driver);
		action.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		Thread.sleep(300);
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).clear();
		Thread.sleep(200);
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(INVALIDEMAIL);

		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(), Messages.INVALID_EMAIL);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
	}

	// Step 5 Input invalid password
	@Test(dependsOnMethods="addNewUserWithInvalidEmail")
	public void addNewUserWithInvalidPassword(){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(USEREMAILADDRESS);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(), Messages.INVALID_PWD);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
	}

	// Step 6 Password and confirm password does not match
	@Test(dependsOnMethods="addNewUserWithInvalidPassword")
	public void addNewUserWithUnmatchedPassword() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);

		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(CONFIRMPASSWORD);

		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();

		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.UNMATCHED_CONFIRM_PWD);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
	}

	// Step 7 Create a new customer user successfully
	@Test(dependsOnMethods="addNewUserWithUnmatchedPassword")
	public void addNewUserWithValidValue() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(PASSWORD);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.SAVE_BUTTON));
		action.pause(1000);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_CREATE_SUCCESSFULLY);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 8 Check cancel button on unsaved changes Dialog
	@Test(dependsOnMethods="addNewUserWithUnmatchedPassword")
	public void cancelCreateNewUserWithoutInput() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 9, 10, 11, 12 Check cancel button on unsaved changes Dialog
	@Test(dependsOnMethods="cancelCreateNewUserWithoutInput")
	public void cancelCreateNewUserWithInput() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		ScreenAction action = new ScreenAction(driver);
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");
		
		driver.findElement(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)).sendKeys(USEREMAILADDRESS);
		action.pause(100);
		
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		action.pause(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");
		assertEquals(action.isElementPresent(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)), true);
		
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 14 

}

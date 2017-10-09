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
import com.ventyx.testng.TestDataKey;

@ALM(id = "602")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_Created_ByAdmin extends BaseTestCase{
	@TestDataKey private final String USERID ="Automator 1";
	@TestDataKey private final String USEREMAILADDRESS ="cuser1@abb.com";
	@TestDataKey private final String PASSWORD ="Testuser2";
	@TestDataKey private final String CONFIRMPASSWORD ="Testuser3";
	@TestDataKey private final String INVALIDEMAIL = "<HTML>";
	@TestDataKey private final String INVALIDPASSWORD = "12345";
	@TestDataKey private final String USERGROUPNAME1 = "All Permissions";

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
		TableFunction action = new TableFunction(driver);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		Thread.sleep(1500);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");

		// Case 1: User ID is empty
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(USEREMAILADDRESS);
		Thread.sleep(100);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(PASSWORD);
		Thread.sleep(100);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(PASSWORD);
		Thread.sleep(100);
		action.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();	
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.ENTER_MANDATORY_FIELDS);		
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));

		// Case 2: User Email Address ID is empty
		driver.findElement(By.cssSelector(CustomerUsers.USERID_TEXTBOX)).sendKeys(USERID);
		Thread.sleep(100);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();	
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.ENTER_MANDATORY_FIELDS);		
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));

		// Case 3: Password is empty
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(USEREMAILADDRESS);
		Thread.sleep(100);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.ENTER_MANDATORY_FIELDS);		
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));

		// Case 4: Confirm Password is empty
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(PASSWORD);
		Thread.sleep(100);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.ENTER_MANDATORY_FIELDS);		
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));

	}

	// Step 3 Input mandatory field, don't select user group
	@Test(dependsOnMethods="addNewUserWithoutMandatoryField")
	public void addNewUserWithoutUserGroup() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		TableFunction action = new TableFunction(driver);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(PASSWORD);
		action.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		Thread.sleep(500);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.USER_SELECT_USERGROUP);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
	}

	// Step 4 Input invalid e-mail
	@Test(dependsOnMethods="addNewUserWithoutUserGroup")
	public void addNewUserWithInvalidEmail() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		TableFunction action = new TableFunction(driver);
		action.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME1);
		Thread.sleep(300);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).clear();
		Thread.sleep(200);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(INVALIDEMAIL);

		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.INVALID_EMAIL);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
	}

	// Step 5 Input invalid password
	@Test(dependsOnMethods="addNewUserWithInvalidEmail")
	public void addNewUserWithInvalidPassword(){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(USEREMAILADDRESS);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.INVALID_PWD);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
	}

	// Step 6 Password and confirm password does not match
	@Test(dependsOnMethods="addNewUserWithInvalidPassword")
	public void addNewUserWithUnmatchedPassword() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(PASSWORD);

		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(CONFIRMPASSWORD);

		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.UNMATCHED_CONFIRM_PWD);
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
	}

	// Step 7 Create a new customer user successfully
	@Test(dependsOnMethods="addNewUserWithUnmatchedPassword")
	public void addNewUserWithValidValue() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(PASSWORD);

		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.SUCCESS)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.SUCCESS)).getText(), Messages.USER_CREATE_SUCCESSFULLY);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 8 Check cancel button on unsaved changes Dialog
	@Test(dependsOnMethods="addNewUserWithUnmatchedPassword")
	public void cancelCreateNewUserWithoutInput() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)));
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
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");
		
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(USEREMAILADDRESS);
		Thread.sleep(100);
		
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");
		assertEquals(action.isElementPresent(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)), true);
		
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 14 

}

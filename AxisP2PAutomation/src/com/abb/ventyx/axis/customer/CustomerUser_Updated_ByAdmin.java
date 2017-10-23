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

@ALM(id = "612")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_Updated_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	String CUSTOMERUSEREMAIL = "cuser1@abb.com";
	String INVALIDCUSTOMERUSEREMAIL = "cuser1abb.com";
	String INVALIDPASSWORD = "<html>";
	String PASSWORD ="Testuser2";
	String CONFIRMPASSWORD ="Testuser3";
	String NEWPASSWORD ="Testuser4";
	String NEWUSERID ="Automator 1 Upda";
	String USERID ="Automator 1";
	String ACTIONSTATUS ="Active";
	String CREATEDSTATUS ="Created";
	int i;
	// Step 1 Select Users Sub Menu
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

	// Step 2 Click on User Number of an existing customer user
	@Test(dependsOnMethods="selectUsersSubMenu")
	public void openModifyUserScreen() throws InterruptedException{
		table = new TableFunction(driver);
		i = table.findRowByString1(3,CUSTOMERUSEREMAIL);
		Thread.sleep(1000);
		assertEquals(table.getValueRow(2,i), USERID);
		assertEquals(table.getValueRow(4,i), "All Permissions");
		assertEquals(table.getValueRow(5,i), CREATEDSTATUS);
		table.clickUserNumber(CUSTOMERUSEREMAIL);
	}

	// Step 3 Update with Invalid Email
	@Test(dependsOnMethods="openModifyUserScreen")
	public void updateWithInvalidEmail() throws InterruptedException{
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)));
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).clear();
		Thread.sleep(500);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(INVALIDCUSTOMERUSEREMAIL);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(), "Invalid email address");
	}

	// Step 4 Update with Invalid Password.
	@Test(dependsOnMethods="updateWithInvalidEmail")
	public void updateWithInvalidPassword() throws InterruptedException{

		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).clear();
		Thread.sleep(500);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(CUSTOMERUSEREMAIL);
		Thread.sleep(500);
		driver.findElement(By.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)).findElement(By.tagName("label")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)));	
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(),Messages.INVALID_PWD1);
	}
	// Step 5 Update with different confirm password and password
	@Test(dependsOnMethods="updateWithInvalidPassword")
	public void updateWithUnmachtedPassword() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)));	
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).clear();
		Thread.sleep(500);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(PASSWORD);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).clear();
		Thread.sleep(500);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(CONFIRMPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(),Messages.UNMATCHED_CONFIRM_PWD);
	}

	// Step 6 Update with valid data
	@Test(dependsOnMethods="updateWithUnmachtedPassword")
	public void updateWithValidData() throws InterruptedException{
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).clear();
		Thread.sleep(200);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(NEWPASSWORD);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).clear();
		Thread.sleep(200);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(NEWPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.USERID_TEXTBOX)).clear();
		Thread.sleep(200);
		driver.findElement(By.cssSelector(CustomerUsers.USERID_TEXTBOX)).sendKeys(NEWUSERID);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.SUCCESS)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.SUCCESS)).getText(), Messages.USER_UPDATE_SUCCESSFULLY);
		assertEquals(table.getValueRow(2,i), NEWUSERID);
		assertEquals(table.getValueRow(4,i), "All Permissions");
	}
	// Step 7 Check Cancel button without input
	@Test(dependsOnMethods="updateWithValidData")
	public void checkCancelButtonWithoutInput() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		table.clickUserNumber(CUSTOMERUSEREMAIL);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.CANCEL_BUTTON)));
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}
	
	// Step 8 Check No button on Unsaved Changes dialog 
	@Test(dependsOnMethods="checkCancelButtonWithoutInput")
	public void checkNoButtonOnUnsavedChangesDialog() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		table.clickUserNumber(CUSTOMERUSEREMAIL);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.USERID_TEXTBOX)));
		driver.findElement(By.cssSelector(CustomerUsers.USERID_TEXTBOX)).clear();
		Thread.sleep(200);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(USERID);
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		Thread.sleep(200);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), true);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Modify User");
	}

	// Step 9 Check Cancel button on Unsaved Changes dialog
	@Test(dependsOnMethods="checkNoButtonOnUnsavedChangesDialog")
	public void checkYesButtonOnUnsavedChangesDialog() throws InterruptedException{
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");

	}

}

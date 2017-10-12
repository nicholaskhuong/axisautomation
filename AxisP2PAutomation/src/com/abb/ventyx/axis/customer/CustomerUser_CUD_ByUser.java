package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
import com.ventyx.testng.TestDataKey;

@ALM(id = "845")
@Credentials(user = "cuserdefault@abb.com", password = "Testuser1")
public class CustomerUser_CUD_ByUser extends BaseTestCase {

	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	public static int i;
	@TestDataKey private final String USERID = "createdbyuser 01";
	@TestDataKey private final String CUSTOMERUSEREMAIL = "cusercreatedbyuser@abb.com";
	@TestDataKey private final String PASSWORD = "Testuser2";
	@TestDataKey private final String CONFIRMPASSWORD = "Testuser2";
	@TestDataKey private final String USERGROUPNAME = "All Permissions";
	@TestDataKey private final String CREATEDSTATUS = "Created";
	@TestDataKey private final String ACTIVESTATUS = "Active";
	@TestDataKey private final String NEWPASSWORD = "Testuser1";
	
	@TestDataKey private final String USEREMAIL_ADMIN = "cadmin1@abb.com";
	
	@TestDataKey private final String NEWPASSWORD_ADMIN = "Testuser2";
	@TestDataKey private final String NEWUSEREMAIL_ADMIN = "cadmin1updated@abb.com";

	@Test
	public void selectUsersSubMenu() throws InterruptedException{
		wait = new WebDriverWait(driver, 60);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		Thread.sleep(1000);
		// The system wrong here
		//assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}
	// Step 1 Customer user creates another user
	@Test(dependsOnMethods="selectUsersSubMenu")
	public void createNewUser() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		wait = new WebDriverWait(driver, 60);
		assertEquals(table.isValueExisting(3, CUSTOMERUSEREMAIL), false);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.inputTextField(CustomerUsers.USERID_TEXTBOX_ID, USERID);
		action.inputTextField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, CUSTOMERUSEREMAIL);
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, PASSWORD);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, CONFIRMPASSWORD);
		table.selectUserGroup(CustomerUsers.USERGROUP_GRID, USERGROUPNAME);
		action.clickBtn(By.id(CustomerUsers.SAVE_BUTTON_ID));
		System.out.print("Test Test");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_CREATE_SUCCESSFULLY);
		i = table.findRowByString1(3, CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(2, i),USERID);
		assertEquals(table.getValueRow(3, i),CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i),USERGROUPNAME);
		assertEquals(table.getValueRow(5, i),CREATEDSTATUS);
	}
	
	@Test(dependsOnMethods="createNewUser")
	public void loginAsNewUser() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		wait = new WebDriverWait(driver, 60);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, CUSTOMERUSEREMAIL);		
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, PASSWORD);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, PASSWORD);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID,NEWPASSWORD);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID,NEWPASSWORD);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Customer Dashboard");
		
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		// The system wrong here
		//assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
		i = table.findRowByString1(3, CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(2, i),USERID);
		assertEquals(table.getValueRow(3, i),CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i),USERGROUPNAME);
		assertEquals(table.getValueRow(5, i),ACTIVESTATUS);
		
	}
	//Step 2 Customer user update other user
	@Test(dependsOnMethods="loginAsNewUser")
	public void logOutAndLoginToTheDefaultUser() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		wait = new WebDriverWait(driver, 60);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, "cuserdefault@abb.com");		
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, "Testuser1");
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));	
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
	}
	@Test(dependsOnMethods="logOutAndLoginToTheDefaultUser")
	public void updateAdminInfo() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		wait = new WebDriverWait(driver, 60);		
		assertEquals(table.getValueRow(2, 1),"Administrator");
		assertEquals(table.getValueRow(3, 1), USEREMAIL_ADMIN);
		assertEquals(table.getValueRow(4, 1),"CUST_ADMIN");
		assertEquals(table.getValueRow(5, 1),ACTIVESTATUS);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.ADMINUSERNUMBER_ID));
		action.waitObjVisible(By.id(CustomerUsers.SAVE_BUTTON_ID));

		action.assertFieldReadOnly(By.id(Users.USERNUMBER_ID));
		action.assertFieldReadOnly(By.id(Users.USER_ID));
		System.out.print(action.getAttribute(By.id(Users.USER_ID))+"Admin User Id Value");
		assertEquals(action.getAttribute(By.id(Users.USER_ID)), "Administrator");
		System.out.print(action.getAttribute(By.id(Users.EMAIL_ID))+"Admin Email Value");
		assertEquals(action.getAttribute(By.id(Users.EMAIL_ID)), "cadmin1@abb.com");
		assertEquals(action.isElementPresent(By.cssSelector(CustomerUsers.USERGROUP_GRID)), false);
		
		action.inputTextField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, NEWUSEREMAIL_ADMIN);
		
		driver.findElement(By.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)).findElement(By.tagName("label")).click();
		
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, NEWPASSWORD_ADMIN);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, NEWPASSWORD_ADMIN);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON_ID));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.SUCCESS)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.SUCCESS)).getText(), Messages.USER_UPDATE_SUCCESSFULLY);
		
		assertEquals(table.getValueRow(2, 1), "Administrator");
		assertEquals(table.getValueRow(3, 1), USEREMAIL_ADMIN);
		assertEquals(table.getValueRow(4, 1), "CUST_ADMIN");
		assertEquals(table.getValueRow(5, 1), ACTIVESTATUS);
		
		action.waitObjVisibleAndClick(By.id(ScreenObjects.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, NEWUSEREMAIL_ADMIN);		
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, NEWPASSWORD_ADMIN);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Customer Dashboard");
	}
}

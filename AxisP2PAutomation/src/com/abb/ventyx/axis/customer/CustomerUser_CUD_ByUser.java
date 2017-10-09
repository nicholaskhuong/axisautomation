package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
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
	@TestDataKey private final String PASSWORD = "TestUser2";
	@TestDataKey private final String CONFIRMPASSWORD = "TestUser2";
	@TestDataKey private final String USERGROUPNAME = "All Permissions";
	@TestDataKey private final String CREATEDSTATUS = "Created";
	@TestDataKey private final String ACTIVESTATUS = "Active";
	@TestDataKey private final String NEWPASSWORD = "Testuser1";

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
		Thread.sleep(1000);
		// The system wrong here
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

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
		action.waitObjVisibleAndClick(By.id(CustomerUsers.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SINGOUT_BUTTON));
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
		
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
		i = table.findRowByString1(3, CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(2, i),USERID);
		assertEquals(table.getValueRow(3, i),CUSTOMERUSEREMAIL);
		assertEquals(table.getValueRow(4, i),USERGROUPNAME);
		assertEquals(table.getValueRow(5, i),ACTIVESTATUS);
		
	}


}

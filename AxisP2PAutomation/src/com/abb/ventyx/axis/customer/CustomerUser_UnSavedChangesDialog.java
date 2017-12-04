package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "995")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_UnSavedChangesDialog extends BaseTestCase{
	public static String USERID = "Auto740";
	public static String USEREMAILADDRESS ="cuser740@abb.com";
	String PASSWORD ="Testuser2";
	String CONFIRMPASSWORD ="Testuser3";
	String INVALIDEMAIL = "<HTML>";
	String INVALIDPASSWORD = "12345";
	String USERGROUPNAME1 = "All Permissions";
	TableFunction table;
	ScreenAction action;

	// Step 1 Open Users sub menu
	@Test
	public void selectUsersSubMenu() {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 2 
	@Test(dependsOnMethods="selectUsersSubMenu")
	public void cancelCreateNewUserWithoutInput() {
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		action.waitObjVisible(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID));
		action.assertTitleScreen("Create User");
		driver.findElement(By.cssSelector(CustomerUsers.CANCEL_BUTTON)).click();
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 3, 4, 5 
	@Test(dependsOnMethods="cancelCreateNewUserWithoutInput")
	public void cancelCreateNewUserWithInput() {
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		action.waitObjVisible(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID));
		action.assertTitleScreen("Create User");
		action.inputEmailField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, USEREMAILADDRESS);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(1000);
		action.waitObjVisible(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID));
		action.assertTitleScreen("Create User");
		assertEquals(action.isElementPresent(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID)), true);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();

		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 6
	@Test(dependsOnMethods="cancelCreateNewUserWithInput")
	public void cancelModifyUserWithoutInput() {
		driver.findElement(By.id("usrSequenceIdStrBtn1")).click();
		action.pause(1000);
		action.waitObjVisible(By.id(CustomerUsers.USERID_TEXTBOX_ID));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.CANCEL_BUTTON));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		action.pause(2000);
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");

	}

	// Step 7
	@Test(dependsOnMethods="cancelModifyUserWithoutInput")
	public void checkNoButtonOnUnsavedChanges() {
		action.waitObjVisibleAndClick(By.id("usrSequenceIdStrBtn1"));
		action.waitObjVisible(By.id(CustomerUsers.USERID_TEXTBOX_ID));
		action.inputTextField(CustomerUsers.USERID_TEXTBOX_ID, "test");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		action.assertTitleScreen("Modify User");
	}

	// Step 8
	@Test(dependsOnMethods = "checkNoButtonOnUnsavedChanges")
	public void checkYesButtonOnUnsavedChanges() {
		action.waitObjVisible(By.id(CustomerUsers.USERID_TEXTBOX_ID));
		action.inputTextField(CustomerUsers.USERID_TEXTBOX_ID, "test12");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");

	}

	// Step 9
	@Test(dependsOnMethods = "checkYesButtonOnUnsavedChanges")
	public void updateStatusOnlyAndCheckUnsavedChanges() {
		assertEquals(action.getTextField(By.xpath("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div[3]/table/tbody/tr[2]/td[4]/div/div")),"Active");
		action.waitObjVisibleAndClick(By.id("usrSequenceIdStrBtn1"));
		action.waitObjVisible(By.id(CustomerUsers.USERID_TEXTBOX_ID));
		action.clickBtn(By.id(Users.STATUS_ID));
		action.pause(1000);
		action.selectStatus(CustomerUsers.STATUSLIST, "Inactive");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerUsers.CANCEL_BUTTON));
		action.pause(1000);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), true, "Raised as the defect D-134288");
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
	}

}

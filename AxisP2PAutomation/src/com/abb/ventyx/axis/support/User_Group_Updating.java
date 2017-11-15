package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "541")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Group_Updating extends BaseTestCase {
	String systemGroupName = "CUST_ADMIN";
	String customerName = "Tanya Customer 11";
	public static String newGroupName = "Cryy Group";
	int row = 0;
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;

	// Step 1
	@Test
	public void openUserGroupScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMERMAINTAINCE_MENU_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERGROUP_SUBMENU_CSS));
		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), UserGroup.CUSTOMER_USERGROUP_TITLE);
	}

	@Test(dependsOnMethods = "openUserGroupScreen", alwaysRun = true)
	public void selectTabAndClickAddButton() {
		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));
		String system_row0 = driver.findElement(By.id(UserGroup.ROW_ID + "0")).getText();
		assertEquals(system_row0, systemGroupName);
		driver.findElement(By.id(UserGroup.USER_TAB_ID)).click();
	}

	// Step 2
	@Test(dependsOnMethods = "selectTabAndClickAddButton", alwaysRun = true)
	public void selectCustomerAndClickOneRowInGrid() {
		action.pause(waitTime);
		WebElement customer = driver.findElement(By.className(UserGroup.CUSTOMER_CLASS));
		customer.sendKeys(customerName);
		action.pause(waitTime);
		action.selectStatus(ScreenObjects.DROPDOWNLIST_CSS, customerName);
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, User_Group_Creating.userGroupName);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.GROUPNAME_LINKID + row));
	}

	// Step 3
	@Test(dependsOnMethods = "selectCustomerAndClickOneRowInGrid", alwaysRun = true)
	public void updateWithNewName() {
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_IN_TITLE_CSS)).getText(), UserGroup.MODIFY_USERGROUP_TITLE);
		action.pause(2000);
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000L);
		newGroupName = String.format("Cryy Group %s", drand);
		WebElement newGroupUser1 = driver.findElement(By.id(UserGroup.USERGROUP_NAME_ID));
		newGroupUser1.clear();
		newGroupUser1.sendKeys(newGroupName);
		action.waitObjVisibleAndClick(By.id(UserGroup.SAVE_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USERGROUP_UPDATE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 4
	@Test(dependsOnMethods = "updateWithNewName", alwaysRun = true)
	public void selectCustomerAndClickOneRowInGrid2() {
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, newGroupName);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.GROUPNAME_LINKID + row));

	}

	@Test(dependsOnMethods = "selectCustomerAndClickOneRowInGrid2", alwaysRun = true)
	public void updateWithNewPermissions() {
		action.clickCheckBoxN(3);
		action.waitObjVisible(By.id(UserGroup.SAVE_ID));
		action.waitObjVisibleAndClick(By.id(UserGroup.SAVE_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USERGROUP_UPDATE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 5
	@Test(dependsOnMethods = "updateWithNewPermissions", alwaysRun = true)
	public void clickCancelWithoutData() {
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, newGroupName);
		action.waitObjVisibleAndClick(By.id(Users.GROUPNAME_LINKID + row));
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
	}

	// Steps 6_7
	@Test(dependsOnMethods = "clickCancelWithoutData", alwaysRun = true)
	public void clickCancelClickN0() {
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, newGroupName);
		action.waitObjVisibleAndClick(By.id(Users.GROUPNAME_LINKID + row));
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, User_Group_Creating.userGroupName);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
	}

	// Steps 8_9
	@Test(dependsOnMethods = "clickCancelClickN0", alwaysRun = true)
	public void clickCancelClickYes() {
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
	}
}
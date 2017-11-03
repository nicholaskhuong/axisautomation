package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "163")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Group_Updating extends BaseTestCase {

	String systemGroupName = "CUST_ADMIN";
	String customerName = "Tanya Customer 11";
	String userGroupName = "Cry Group";
	String newGroupName = "Cryy Group";
	BaseDropDownList list;
	BaseGrid userGroupList;
	int row = 0;
	ScreenAction action;
	TableFunction table;
	int waitTime = 2000;
	WebElement index;

	//Step 1
	@Test
	public void openUserGroupScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMERMAINTAINCE_MENU_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERGROUP_SUBMENU_CSS));
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "openUserGroupScreen", alwaysRun = true)
	public void selectTabAndClickAddButton() {
		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));
		String system_row0 = driver.findElement(By.id(UserGroup.ROW_ID + "0")).getText();
		assertEquals(system_row0, systemGroupName);
		driver.findElement(By.id(UserGroup.USER_TAB_ID)).click();
	}
	
	//Step 2
	@Test(dependsOnMethods = "selectTabAndClickAddButton", alwaysRun = true)
	public void selectCustomerAndClickOneRowInGrid() {
		WebElement customer = driver.findElement(By.className(UserGroup.CUSTOMER_CLASS));
		customer.sendKeys(customerName);
		action.selectStatus(ScreenObjects.DROPDOWNLIST_CSS, customerName);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, userGroupName);
		action.pause(waitTime);
		action.clickBtn(By.id(Users.GROUPNAME_LINKID + row));
	}
	
	//Step 3
	@Test(dependsOnMethods = "selectCustomerAndClickOneRowInGrid", alwaysRun = true)
	public void updateWithNewName() {
		action.pause(waitTime);
		WebElement nameUserGroup = driver.findElement(By.id(UserGroup.USERGROUP_NAME_ID));
		nameUserGroup.clear();
		action.pause(waitTime);
		nameUserGroup.sendKeys(newGroupName);
		action.pause(waitTime);
		driver.findElement(By.id(UserGroup.SAVE_ID)).click();
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE,Messages.USERGROUP_UPDATE_SUCCESSFULLY);
	}
	
	//Step 4
	@Test(dependsOnMethods = "updateWithNewName", alwaysRun = true)
	public void selectCustomerAndClickOneRowInGrid2() {
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, newGroupName);
		action.pause(waitTime);
		action.clickBtn(By.id(Users.GROUPNAME_LINKID + row));
	}
	
	@Test(dependsOnMethods = "selectCustomerAndClickOneRowInGrid2", alwaysRun = true)
	public void updateWithNewPermissions() {
		action.pause(waitTime);
		action.clickCheckBoxN(3);
		action.pause(waitTime);
		driver.findElement(By.id(UserGroup.SAVE_ID)).click();
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE,Messages.USERGROUP_UPDATE_SUCCESSFULLY);
	}

	//Step 5 
	@Test(dependsOnMethods = "updateWithNewPermissions", alwaysRun = true)
	public void clickCancelWithoutdata() {
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, newGroupName);
		action.pause(waitTime);
		action.clickBtn(By.id(Users.GROUPNAME_LINKID + row));
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
	}
	
	//Steps 6_7
	@Test(dependsOnMethods = "clickCancelWithoutdata", alwaysRun = true)
	public void clickCancelClickN0() {
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, newGroupName);
		action.pause(waitTime);
		action.clickBtn(By.id(Users.GROUPNAME_LINKID + row));
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, userGroupName);
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.NO_BTN_ID));
	}
	
	//Steps 8_9
	@Test(dependsOnMethods = "clickCancelClickN0", alwaysRun = true)
	public void clickCancelClickYes() {
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
	}
}
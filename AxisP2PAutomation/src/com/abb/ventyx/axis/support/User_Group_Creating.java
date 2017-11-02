package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "162")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Group_Creating extends BaseTestCase {
	String systemGroupName = "CUST_ADMIN";
	String customerName = "Tanya Customer 11";
	String userGroupName = "Cry Group";
	BaseDropDownList list;
	int row;
	ScreenAction action;
	TableFunction table;
	int waitTime = 2000;

	//Step 1
	@Test
	public void openUserGroupScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMERMAINTAINCE_MENU_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERGROUP_SUBMENU_CSS));
		action.pause(waitTime);
	}
	
	//Step 2
	@Test(dependsOnMethods = "openUserGroupScreen", alwaysRun = true)
	public void selectTabAndClickAddButton() {
		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));
		String system_row0 = driver.findElement(By.id(UserGroup.ROW_ID + "0")).getText();
		assertEquals(system_row0, systemGroupName);
		driver.findElement(By.id(UserGroup.USER_TAB_ID)).click();
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.xpath(UserGroup.ADD_XPATH));
		action.assertMessgeError(ScreenObjects.WARNING_MESSAGE_CSS, Messages.USERGROUP_SELECT_CUSTOMER);
	}
	
	//Step 3
	@Test(dependsOnMethods = "selectTabAndClickAddButton", alwaysRun = true)
	public void selectCustomerAndClickAddButton() {
		WebElement customer = driver.findElement(By.className(UserGroup.CUSTOMER_CLASS));
		customer.sendKeys(customerName);
		action.selectStatus(ScreenObjects.DROPDOWNLIST_CSS, customerName);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.xpath(UserGroup.ADD_XPATH));
		action.pause(waitTime);
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, userGroupName);
		action.pause(waitTime);
		driver.findElement(By.id(UserGroup.SAVE_ID)).click();
		action.assertMessgeError(ScreenObjects.WARNING_MESSAGE_CSS, Messages.EMPTY_PERMISSION);
	}
	
	// Step 4
	@Test(dependsOnMethods = "selectCustomerAndClickAddButton", alwaysRun = true)
	public void addWithSelectedCustomerHasUserGroupName() {
		action.pause(waitTime);
		action.clickCheckBoxN(2);
		driver.findElement(By.id(UserGroup.SAVE_ID)).click();
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE,Messages.USERGROUP_CREATE_SUCCESSFULLY);
	}
	
	//Step 5
	@Test(dependsOnMethods = "addWithSelectedCustomerHasUserGroupName", alwaysRun = true)
	public void cancelWithoutdata(){
		action.waitObjVisibleAndClick(By.xpath(UserGroup.ADD_XPATH));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
	}
	
	//Steps 6_7
	@Test(dependsOnMethods = "cancelWithoutdata", alwaysRun = true)
	public void cancelClickNo() {
		action.waitObjVisibleAndClick(By.xpath(UserGroup.ADD_XPATH));
		action.pause(waitTime);
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, userGroupName);
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.NO_BTN_ID));
	}
	
	//Steps 8_9
	@Test(dependsOnMethods = "cancelClickNo", alwaysRun = true)
	public void cancelClickYes() {
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(UserGroup.CANCEL_ID));
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
	}
}

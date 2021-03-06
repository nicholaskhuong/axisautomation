package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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

@ALM(id = "553")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Group_Deleting extends BaseTestCase {
	String systemGroupName = "CUST_ADMIN";
	String customerName = "Tanya Customer 11";
	BaseDropDownList list;
	BaseGrid userGroupList;
	int row = 0;
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;

	// Step 1
	@Test
	public void openUserGroupScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
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
		action.selectStatus(ScreenObjects.DROPDOWNLIST_CSS, customerName);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.xpath(UserGroup.FILTER_XPATH));
		action.inputTextField(UserGroup.NAME_FILTER, User_Group_Updating.newGroupName);
		action.pause(2000);
		WebElement cell = table.getCellObject(1, 1);
		Assert.assertEquals(cell.getText(), User_Group_Updating.newGroupName);
		row = table.findRealIndexByCell(1, 2, Users.DELETE_BUTTON);
		action.waitObjVisibleAndClick(By.id(Users.DELETE_BUTTON + row));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Users.DELETE_BUTTON + row));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USERGROUP_DELETE_SUCCESSFULLY);
	}

}

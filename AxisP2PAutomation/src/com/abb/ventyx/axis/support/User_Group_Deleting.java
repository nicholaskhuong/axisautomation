package com.abb.ventyx.axis.support;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportCustomerUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "164")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Group_Deleting extends BaseTestCase {

	String SYSTEM_GROUP_NAME = "CUST_ADMIN";
	String CUSTOMER_NAME = "Tanya Customer 11";
	String USER_GROUP_NAME = "Manager Group";
	BaseDropDownList list;
	BaseGrid userGroupList;
	int row;
	ScreenAction action;
	TableFunction table;

	@Test
	public void selectuserTab() throws Exception {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisSupportCustomerUserGroup.CUSTOMERMAINTAINCE_MENU_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(AxisSupportCustomerUserGroup.USERGROUP_SUBMENU_CSS));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.SYSTEM_TAB_ID)));
		WebElement screenTitle = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID)));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_TITLE, "Title is wrong");
		String system_row0 = driver.findElement(
				By.id(AxisSupportCustomerUserGroup.ROW_ID + "0")).getText();
		assertEquals(system_row0, SYSTEM_GROUP_NAME);
		driver.findElement(By.id(AxisSupportCustomerUserGroup.USER_TAB_ID))
				.click();

	}

	@Test(dependsOnMethods = "selectuserTab")
	public void deleteUserGroupClickNo() throws Exception {

		// select Customer Name
		WebElement customer = driver.findElement(By
				.className(AxisSupportCustomerUserGroup.CUSTOMER_CLASS));
		customer.sendKeys(CUSTOMER_NAME);
		list = new BaseDropDownList(driver,
				AxisSupportCustomerUserGroup.LIST_CSS);
		row = list.findItemInDropDownList(CUSTOMER_NAME);
		WebElement rowClick = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisSupportCustomerUserGroup.LIST_CSS
								+ "> tbody > tr:nth-child(" + (row - 1)
								+ ") > td")));
		rowClick.click();
		// Click on row with a user group name
		(new WebDriverWait(driver, 15)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.ROW_ID + "0")));
		userGroupList = new BaseGrid(driver,
				AxisSupportCustomerUserGroup.USERGROUP_TABLE_CSS);
		int rowSelected = userGroupList.findItemByColumnName(
				AxisSupportCustomerUserGroup.NAME_COLUMN, USER_GROUP_NAME);
		driver.findElement(
				By.id(AxisSupportCustomerUserGroup.DELETE_ID
						+ (rowSelected - 1))).click();
		// Click No on dialog
		WebElement deleteConfirm = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		assertThat(deleteConfirm.getText(),
				containsString(Messages.DELETE_USERGROUP_CONFIRM));
		WebElement deleteNoBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.DELETE_NO)));
		deleteNoBtn.click();

		WebElement rowDelete = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.ROW_ID
								+ (rowSelected - 1))));
		assertEquals(rowDelete.getText(), USER_GROUP_NAME);

	}

	@Test(dependsOnMethods = "deleteUserGroupClickNo")
	public void deleteUserGroupClickYes() throws Exception {

		// Click Yes on dialog
		int rowSelected = userGroupList.findItemByColumnName(
				AxisSupportCustomerUserGroup.NAME_COLUMN, USER_GROUP_NAME);
		driver.findElement(
				By.id(AxisSupportCustomerUserGroup.DELETE_ID
						+ (rowSelected - 1))).click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		WebElement deleteYesBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.DELETE_YES)));
		deleteYesBtn.click();
		WebElement flashMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		assertEquals(flashMessage.getText(),
				Messages.USERGROUP_DELETE_SUCCESSFULLY);
	}

}

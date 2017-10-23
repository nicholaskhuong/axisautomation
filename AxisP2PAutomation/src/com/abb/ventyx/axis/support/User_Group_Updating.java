package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;

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

@ALM(id = "163")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Group_Updating extends BaseTestCase {

	String SYSTEM_GROUP_NAME = "CUST_ADMIN";
	String CUSTOMER_NAME = "Tanya Customer 11";
	String USER_GROUP_NAME = "Manager Group";
	String NEW_GROUP_NAME = "Admin Group";
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

	// Step 2
	@Test(dependsOnMethods = "selectuserTab")
	public void selectRowForUpdate() throws Exception {
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
		(new WebDriverWait(driver, 15)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.ROW_ID + "0")));
		userGroupList = new BaseGrid(driver,
				AxisSupportCustomerUserGroup.USERGROUP_TABLE_CSS);
		int rowSelected = userGroupList.findItemByColumnName(
				AxisSupportCustomerUserGroup.NAME_COLUMN, USER_GROUP_NAME);
		driver.findElement(
				By.id(AxisSupportCustomerUserGroup.ROW_ID + (rowSelected - 1)))
				.click();
	}

	// Step 3
	@Test(dependsOnMethods = "selectRowForUpdate")
	public void updateWithNewName() {

		WebElement userGroupName = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_UPDATE_TITLE);
		// assertEquals(userGroupName.getText(), USER_GROUP_NAME);
		// List<WebElement> list=
		// driver.findElements(By.className(AxisSupportCustomerUserGroup.PERMISSION_CLASS));
		// for(WebElement permission:list)
		// {
		// assertTrue(permission.isSelected());
		// }
		userGroupName.clear();
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement errorMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(errorMessage.getText(), Messages.ENTER_MANDATORY_FIELDS);
		userGroupName.sendKeys(NEW_GROUP_NAME);
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement sucessMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		assertEquals(sucessMessage.getText(),
				Messages.USERGROUP_UPDATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "updateWithNewName")
	public void updatePermission() {

		(new WebDriverWait(driver, 15)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.ROW_ID + "0")));
		userGroupList = new BaseGrid(driver,
				AxisSupportCustomerUserGroup.USERGROUP_TABLE_CSS);
		row = userGroupList.findItemByColumnName(
				AxisSupportCustomerUserGroup.NAME_COLUMN, NEW_GROUP_NAME);
		driver.findElement(
				By.id(AxisSupportCustomerUserGroup.ROW_ID + (row - 1))).click();
		WebElement userGroupName = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		// assertEquals(userGroupName.getText(), NEW_GROUP_NAME);
		userGroupName.clear();
		userGroupName.sendKeys(CUSTOMER_NAME);
		List<WebElement> list = driver.findElements(By
				.className(AxisSupportCustomerUserGroup.PERMISSION_CLASS));
		list.get(0).click();
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement sucessMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		assertEquals(sucessMessage.getText(),
				Messages.USERGROUP_UPDATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "updatePermission")
	public void clickCancelWithoutdata() throws InterruptedException {
		WebElement rowLink = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.ROW_ID + "1"));
		rowLink.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		driver.findElement(By.id(AxisSupportCustomerUserGroup.CANCEL_ID))
				.click();

		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USER_CUSTOMER_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "clickCancelWithoutdata")
	public void clickCancelClickYes() {

		WebElement rowLink = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.ROW_ID + "1"));
		rowLink.click();
		WebElement userGroupName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		userGroupName.sendKeys(USER_GROUP_NAME);
		driver.findElement(By.id(AxisSupportCustomerUserGroup.CANCEL_ID))
				.click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USER_CUSTOMER_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "clickCancelClickYes")
	public void clickCancelClickN0() {

		WebElement rowLink = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.ROW_ID + "1"));
		rowLink.click();
		WebElement userGroupName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		userGroupName.sendKeys(USER_GROUP_NAME);
		driver.findElement(By.id(AxisSupportCustomerUserGroup.CANCEL_ID))
				.click();
		WebElement msgDialog = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		;
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_UPDATE_TITLE);

	}

}

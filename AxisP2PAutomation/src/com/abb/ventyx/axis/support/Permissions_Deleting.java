package com.abb.ventyx.axis.support;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.PermissionsAction;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "549")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Permissions_Deleting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	PermissionsAction permissionsAction;
	String supplierAdminUserGroupName = "SUPP_ADMIN";
	String POTypeDescription = "Purchase Orders";
	@Test
	public void openMaintainPermissionScreen() throws Exception {
		// Step 1	
		permissionsAction = new PermissionsAction(driver);
		action = new ScreenAction(driver);
		table = new TableFunction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

	}
	// Step 2
	@Test(dependsOnMethods = "openMaintainPermissionScreen" , alwaysRun = true)
	public void clickTrashBinIcon() {

		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS)).click();
		action.pause(1000);
		action.waitObjVisible(By.xpath(Permissions.PERMISSION_NAME_FILTER));
		driver.findElement(By.xpath(Permissions.PERMISSION_NAME_FILTER)).sendKeys(Permissions_Creating.permissionName);		
		action.pause(3000);
		
		if(!driver.findElement(By.xpath(Permissions.FIRST_PERMISSION_NAME_XPATH)).getText().equals(Permissions_Creating.permissionName))
		{
			action.pause(3000);
			
		}
		action.assertTextEqual(By.xpath(Permissions.FIRST_PERMISSION_NAME_XPATH), Permissions_Creating.permissionName);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 5));
		action.waitObjVisible(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION));
		// Make sure this is a Confirmation of deleting process
		assertThat(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(), containsString(Messages.DELETE_CONFIRM));


	}
	// Step 3
	@Test(dependsOnMethods = "clickTrashBinIcon", alwaysRun = true)
	public void clickYes() {
		WebElement yesButton = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(Permissions.DELETE_YES)));
		yesButton.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(),
				Messages.PERMISSION_DELETED_SUCCESSFULLY);
	}

	// Step 4
	@Test(dependsOnMethods = "clickYes", alwaysRun = true)
	public void checkNewPermissionUnavailableInSupplierUserGroup() {
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.AXIS_ADMIN_ID));
		// Supplier Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.SUPPLIER_USERGROUP_ID));

		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), supplierAdminUserGroupName);

		action.pause(2000);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, POTypeDescription, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), POTypeDescription);

		table.clickArrowDownToShowPermission(row, 2);

		action.pause(2000);
		assertEquals(table.isPermissionExisting(Permissions_Creating.permissionName, row - 1), false);

	}
	// Step 5
	@Test(dependsOnMethods = "checkNewPermissionUnavailableInSupplierUserGroup", alwaysRun = true)
	public void clickTrashBinIconFistPermisson() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 5));
		action.waitObjVisible(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION));
		// Make sure this is a Confirmation of deleting process
		assertThat(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(), containsString(Messages.DELETE_CONFIRM));

	}
	// Step 6
	@Test(dependsOnMethods = "clickTrashBinIconFistPermisson", alwaysRun = true)
	public void clickNo() {
		WebElement NoButton = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(Permissions.DELETE_NO)));
		NoButton.click();
		action.waitObjInvisible(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION));
		action.pause(2000);
		action.assertTitleScreen("Maintain Permissions");
	}
	// Step 7
	@Test(dependsOnMethods = "clickNo", alwaysRun = true)
	public void deletePermissionAssigningToUserGroup() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 5));
		action.waitObjVisible(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION));
		WebElement yesButton = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(Permissions.DELETE_YES)));
		yesButton.click();
		action.pause(1000);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DELELE_PERMISSION_IN_USE);
	}
}

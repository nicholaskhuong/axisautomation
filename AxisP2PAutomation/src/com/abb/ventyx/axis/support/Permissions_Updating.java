package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
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

@ALM(id = "544")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Permissions_Updating extends BaseTestCase {

	String invoiceTypeName = "Invoicing";
	public static String invoiceTypeDescription = "Invoice";
	ScreenAction action;
	PermissionsAction permissionsAction;
	String axisAdminUserGroupName = "AXIS_ADMIN";
	TableFunction table;
	String supplierAdminUserGroupName = "SUPP_ADMIN";
	// Step 1
	@Test
	public void openMaintainPermissionScreen() {
		// Update Permission
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		permissionsAction = new PermissionsAction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.inputTextField(By.xpath(Permissions.PERMISSION_NAME_FILTER), Permissions_Creating.permissionName);
		action.pause(2000);

		if(!driver.findElement(By.cssSelector(Permissions.PNROW1)).getText().equals(Permissions_Creating.permissionName))
		{
			action.pause(3000);

		}
		// Get Document Type value
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(), Permissions_Creating.permissionName);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(), Permissions_Creating.userTypeCSA);
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(), Permissions_Creating.purchaseorderTypeName);

		// Click on Permission ID
		action.waitObjVisibleAndClick(By.xpath(Permissions.GRID_PERMISSIONIDCELL));
		action.waitObjVisible(By.id(Permissions.PERMISSION_NAME));
		action.pause(2000);
	}

	// Step 2
	@Test(dependsOnMethods = "openMaintainPermissionScreen", alwaysRun = true)
	public void updatePermissionWithValidValue() {

		// Step 2 update
		permissionsAction.selectDocTypebyText(invoiceTypeName);
		// Unselect Customer 
		permissionsAction.selectUserType(Permissions.CUSTOMER_CHECKBOX);
		// Unselect Supplier
		permissionsAction.selectUserType(Permissions.SUPPLIER_CHECKBOX);

		action.waitObjVisibleAndClick(By.id(Permissions.SAVE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertTextEqual(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE), Messages.PERMISSION_UPADTED_SUCCESSFULLY);

		action.inputTextField(By.xpath(Permissions.PERMISSION_NAME_FILTER), Permissions_Creating.permissionName);
		action.pause(2000);

		if(!driver.findElement(By.cssSelector(Permissions.PNROW1)).getText().equals(Permissions_Creating.permissionName))
		{
			action.pause(3000);

		}
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(), Permissions_Creating.permissionName);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(), "A");
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(), invoiceTypeDescription);

	}
	// Step 3
	@Test(dependsOnMethods = "updatePermissionWithValidValue", alwaysRun = true)
	public void checkNewPermissionAvailableInAxisUserGroup() {
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.AXIS_ADMIN_ID));
		// Axis Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.AXIS_USERGROUP_ID));
		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), axisAdminUserGroupName);

		action.pause(2000);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, invoiceTypeName, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), invoiceTypeName);

		table.clickArrowDownToShowPermission(row, 2);

		action.pause(2000);

		assertEquals(table.isPermissionExisting(Permissions_Creating.permissionName, row - 1), true);

	}
	@Test(dependsOnMethods = "checkNewPermissionAvailableInAxisUserGroup", alwaysRun = true)
	public void checkNewPermissionAvailableInSupplierUserGroup() {

		// Supplier Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.SUPPLIER_USERGROUP_ID));

		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), supplierAdminUserGroupName);

		action.pause(3000);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, invoiceTypeName, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), invoiceTypeName);

		table.clickArrowDownToShowPermission(row, 2);

		action.pause(2000);

		assertEquals(table.isPermissionExisting(Permissions_Creating.permissionName, row - 1), false);
	}

	// Step 4, 5, 6, 7
	@Test(dependsOnMethods = "checkNewPermissionAvailableInSupplierUserGroup")
	public void checkUnsavedChangesDialog() throws InterruptedException {
		action.pause(1000);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		
		// Step 4 Click 1st instance ID
		action.waitObjVisibleAndClick(By.xpath(Permissions.GRID_PERMISSIONIDCELL));
		action.waitObjVisible(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER));

		action.inputTextField(Permissions.PERMISSION_NAME, "ECHO2");
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.assertTextEqual(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION), Messages.UNSAVED_CHANGE);

		// Step 5 Click No

		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.id(ScreenObjects.NO_BTN_ID));
		
		// Step 6
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));

		// Step 7
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(2000);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)), false);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)), false);

	}

}

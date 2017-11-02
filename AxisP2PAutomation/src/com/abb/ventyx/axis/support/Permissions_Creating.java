package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

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

@ALM(id = "437")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Creating extends BaseTestCase {

	public static String permissionName = "Permision 61093109";
	public static String purchaseorderTypeName = "PurchaseOrder";
	public static String userTypeCSA = "CSA";
	String addPermissionHeader = "Add Permission";
	String maintainPermissionHeader = "Maintain Permissions";

	PermissionsAction permissionsAction;
	ScreenAction action;
	TableFunction table;
	String supplierAdminUserGroupName = "SUPP_ADMIN";
	String systemType = "SYSTEM";
	String axisAdminUserGroupName = "AXIS_ADMIN";
	String custAdminUserGroupName = "CUST_ADMIN";
	String POTypeDescription = "Purchase Orders";

	// Step 1
	@Test
	public void openMaintainPermissionScreen() throws InterruptedException {
		permissionsAction = new PermissionsAction(driver);
		action = new ScreenAction(driver);
		table = new TableFunction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		assertEquals(table.getValueTableHeader(1), "Permission ID");
		assertEquals(table.getValueTableHeader(2), "Document Type");
		assertEquals(table.getValueTableHeader(3), "Permission Name");
		assertEquals(table.getValueTableHeader(4), "User Type");
	}

	// Step 2, 3
	@Test(dependsOnMethods = "openMaintainPermissionScreen", alwaysRun = true)
	public void createPermissionwithValidValue() {

		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 100000000L);
		permissionName = String.format("Permision %s", drand);

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.inputTextField(Permissions.PERMISSION_NAME, permissionName);
		action.pause(500);
		permissionsAction.selectDocTypebyText(POTypeDescription);
		// action.pause(2000);
		permissionsAction.selectUserType(Permissions.AXIS_ADMIN);
		permissionsAction.selectUserType(Permissions.CUSTOMER);
		permissionsAction.selectUserType(Permissions.SUPPLIER);
		action.pause(4000);
		action.clickBtn(By.id(Permissions.SAVE));
	
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.PERMISSION_CREATED_SUCCESSFULLY);

		// Filter
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		permissionsAction.enterValueTofilterPermission(permissionName);
		action.pause(2000);
		permissionsAction.filterPermissionbyDocumentType("PurchaseOrder");
		action.pause(1000);
		action.waitObjVisible(By.cssSelector(Permissions.PNROW1));
		action.waitObjVisible(By.cssSelector(Permissions.UTROW1));
		action.waitObjVisible(By.cssSelector(Permissions.DTROW1));
		assertEquals(action.getTextField(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[1]//td[2]")),
				purchaseorderTypeName);

		assertEquals(action.getTextField(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[1]//td[3]")),
				permissionName);

		assertEquals(action.getTextField(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[1]//td[4]")),
				userTypeCSA);

	}

	// Step 4
	@Test(dependsOnMethods = "createPermissionwithValidValue", alwaysRun = true)
	public void checkNewPermissionAvailableInSupplierUserGroup() {

		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.AXIS_ADMIN_ID));
		// Supplier Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.SUPPLIER_USERGROUP_ID));

		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), supplierAdminUserGroupName);
		System.out.println("Perla Perla 1");
		action.pause(2000);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, POTypeDescription, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), POTypeDescription);

		table.clickArrowDownToShowPermission(row, 2);
		System.out.println("Row: " + row);
		action.pause(2000);

		table.isPermissionExisting(permissionName, row - 1);
	}

	@Test(dependsOnMethods = "checkNewPermissionAvailableInSupplierUserGroup", alwaysRun = true)
	public void checkNewPermissionAvailableInAxisUserGroup() {

		// Supplier Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.AXIS_USERGROUP_ID));
		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), axisAdminUserGroupName);
		System.out.println("Perla Perla 1");
		action.pause(2000);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, POTypeDescription, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), POTypeDescription);

		table.clickArrowDownToShowPermission(row, 2);
		System.out.println("Row: " + row);
		action.pause(2000);

		table.isPermissionExisting(permissionName, row - 1);

	}

	@Test(dependsOnMethods = "checkNewPermissionAvailableInAxisUserGroup", alwaysRun = true)
	public void checkNewPermissionAvailableInCustomerUserGroup() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.CUSTOMER_MAINTENANCE_ID));
		// Customer Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.CUSTOMER_USERGROUP_ID));

		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.pause(1000);
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), custAdminUserGroupName);
		System.out.println("Perla Perla 1");
		action.pause(2000);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, POTypeDescription, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), POTypeDescription);

		table.clickArrowDownToShowPermission(row, 2);
		System.out.println("Row: " + row);
		action.pause(2000);
		table.isPermissionExisting(permissionName, row - 1);
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.CUSTOMER_MAINTENANCE_ID));

	}

	// Step 5, 6, 7
	@Test(dependsOnMethods = "checkNewPermissionAvailableInCustomerUserGroup", alwaysRun = true)
	public void addPermissonWithoutMandatoryField() throws InterruptedException {

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		// Step 6
		action.waitObjVisibleAndClick(By.id(Permissions.SAVE));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.EMPTYPERMISSIONNAME);

		// Step 7
		action.inputTextField(Permissions.PERMISSION_NAME, "ECHO 1");
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(Permissions.SAVE));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.EMPTYUSERTYPE);
	}

	// Step 8, 9, 10
	@Test(dependsOnMethods = "addPermissonWithoutMandatoryField", alwaysRun = true)
	public void checkUnsavedChangesDialog() throws InterruptedException {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.assertTextEqual(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION), Messages.UNSAVED_CHANGE);

		// Step 9

		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(1000);
		action.assertTextEqual(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER), addPermissionHeader);

		// Step 10
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));

		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));

		action.pause(2000);
		action.assertTextEqual(By.cssSelector(Permissions.PERMISSIONHEADER), maintainPermissionHeader);
		// Step 11
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.pause(2000);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)), false);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)), false);
		action.assertTextEqual(By.cssSelector(Permissions.PERMISSIONHEADER), maintainPermissionHeader);
	}
}

package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Permissions_Creating extends BaseTestCase {

	public static String permissionName = "Permision 9562202";
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
	int waitTime = 1000;

	// Step 1
	@Test
	public void openMaintainPermissionScreen() throws InterruptedException {
		permissionsAction = new PermissionsAction(driver);
		action = new ScreenAction(driver);
		table = new TableFunction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Permissions.MAINTAIN_PERMISSIONS);
		assertEquals(table.getValueTableHeader(1), "Permission ID");
		assertEquals(table.getValueTableHeader(2), "Document Type");
		assertEquals(table.getValueTableHeader(3), "Permission Name");
		assertEquals(table.getValueTableHeader(4), "User Type");
	}

	// Step 2
	@Test(dependsOnMethods = "openMaintainPermissionScreen", alwaysRun = true)
	public void createPermissionwithValidValue() {

		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 100000000L);
		permissionName = String.format("Permision %s", drand);

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisible(By.id(Permissions.PERMISSION_NAME));
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSION_CSS)).getText(), Permissions.ADD_PERMISSIONS);

		action.inputTextField(Permissions.PERMISSION_NAME, permissionName);
		action.pause(500);
		permissionsAction.selectDocTypebyText(POTypeDescription);
		permissionsAction.selectUserType(Permissions.AXIS_ADMIN);
		permissionsAction.selectUserType(Permissions.CUSTOMER);
		permissionsAction.selectUserType(Permissions.SUPPLIER);
	}

	// Step 3
	@Test(dependsOnMethods = "createPermissionwithValidValue", alwaysRun = true)
	public void clicButtonSaveAndCheckSuccessfulMessage() {
		action.pause(waitTime);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id(Permissions.SAVE)));

		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.PERMISSION_CREATED_SUCCESSFULLY);
	}

	// Step 3
	@Test(dependsOnMethods = "clicButtonSaveAndCheckSuccessfulMessage", alwaysRun = true)
	public void checkNewlyCreatedPermissionDisplayingOnTheGrid() {

		// Filter
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.inputTextField(By.xpath(Permissions.PERMISSION_NAME_FILTER), Permissions_Creating.permissionName);
		action.pause(waitTime);
		// action.inputTextField(By.xpath(Permissions.DOC_TYPE_FILTER),
		// "PurchaseOrder");
		// action.pause(waitTime);
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
	@Test(dependsOnMethods = "checkNewlyCreatedPermissionDisplayingOnTheGrid", alwaysRun = true)
	public void checkNewPermissionAvailableInSupplierUserGroup() {

		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.AXIS_ADMIN_ID));
		// Supplier Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.SUPPLIER_USERGROUP_ID));

		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), supplierAdminUserGroupName);

		action.pause(waitTime);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, POTypeDescription, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), POTypeDescription);

		table.clickArrowDownToShowPermission(row, 2);

		action.pause(waitTime);

		assertEquals(table.isPermissionExisting(permissionName, row - 1), true);
	}

	@Test(dependsOnMethods = "checkNewPermissionAvailableInSupplierUserGroup", alwaysRun = true)
	public void checkNewPermissionAvailableInAxisUserGroup() {

		// Supplier Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.AXIS_USERGROUP_ID));
		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), axisAdminUserGroupName);

		action.pause(waitTime);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, POTypeDescription, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), POTypeDescription);

		table.clickArrowDownToShowPermission(row, 2);

		action.pause(waitTime);

		assertEquals(table.isPermissionExisting(permissionName, row - 1), true);

	}

	@Test(dependsOnMethods = "checkNewPermissionAvailableInAxisUserGroup", alwaysRun = true)
	public void checkNewPermissionAvailableInCustomerUserGroup() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.CUSTOMER_MAINTENANCE_ID));
		// Customer Usergroup sub-menu
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.CUSTOMER_USERGROUP_ID));

		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		action.waitObjVisibleAndClick(By.id(UserGroup.ADMINUSERGROUP_ID));
		action.pause(waitTime);
		action.waitObjVisible(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS));

		action.assertTextEqual(By.cssSelector(UserGroup.ADMIN_USERGROUPNAME_CSS), custAdminUserGroupName);

		action.pause(waitTime);
		int row = table.findRowByString(UserGroup.USERGROUP_GRID_XPATH, 3, POTypeDescription, true);

		assertEquals(table.getCellObjectUserGroup(UserGroup.USERGROUP_GRID_XPATH, row, 3).getText(), POTypeDescription);

		table.clickArrowDownToShowPermission(row, 2);

		action.pause(waitTime);
		assertEquals(table.isPermissionExisting(permissionName, row - 1), true);
		action.waitObjVisibleAndClick(By.id(AxisConfigMenu.CUSTOMER_MAINTENANCE_ID));

	}

	// Step 5, 6
	@Test(dependsOnMethods = "checkNewPermissionAvailableInCustomerUserGroup", alwaysRun = true)
	public void addPermissonWithoutMandatoryField() throws InterruptedException {

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		// Step 5
		action.waitObjVisibleAndClick(By.id(Permissions.SAVE));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.EMPTYPERMISSIONNAME);

		// Step 6
		action.inputTextField(Permissions.PERMISSION_NAME, "ECHO 1");
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(Permissions.SAVE));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.EMPTYUSERTYPE);
	}

	// Step 7, 8, 9, 10
	@Test(dependsOnMethods = "addPermissonWithoutMandatoryField", alwaysRun = true)
	public void checkUnsavedChangesDialog() throws InterruptedException {
		// Step 7
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.assertTextEqual(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION), Messages.UNSAVED_CHANGE);

		// Step 8

		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		action.assertTextEqual(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER), addPermissionHeader);

		// Step 9
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));

		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));

		action.pause(waitTime);
		action.assertTextEqual(By.cssSelector(Permissions.PERMISSIONHEADER), maintainPermissionHeader);
		// Step 10
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.pause(waitTime);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)), false);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)), false);
		action.assertTextEqual(By.cssSelector(Permissions.PERMISSIONHEADER), maintainPermissionHeader);

	}

}

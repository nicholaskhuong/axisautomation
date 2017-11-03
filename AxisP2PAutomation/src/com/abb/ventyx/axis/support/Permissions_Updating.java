package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.PermissionsAction;
import com.abb.ventyx.utilities.ScreenAction;

@ALM(id = "544")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Permissions_Updating extends BaseTestCase {

	String invoiceTypeName = "Invoicing";
	public static String invoiceTypeDescription = "Invoice";
	ScreenAction action;
	PermissionsAction permissionsAction;
	// Step 1
	@Test
	public void openMaintainPermissionScreen() {
		// Update Permission
		action = new ScreenAction(driver);
		permissionsAction = new PermissionsAction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PERMISSIONS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		permissionsAction.enterValueTofilterPermission(Permissions_Creating.permissionName);

		action.pause(2000);
		// Get Document Type value
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(), Permissions_Creating.permissionName);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(), Permissions_Creating.userTypeCSA);
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(), Permissions_Creating.purchaseorderTypeName);

		// Click on Permission ID
		action.waitObjVisibleAndClick(By.xpath(Permissions.GRID_PERMISSIONIDCELL));
		action.waitObjVisible(By.id(Permissions.PERMISSION_NAME));
		action.pause(2000);
		/*assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSION_EDIT_TITLE)).getText(), "Edit Permission",
				"Selenium can't get title here");*/
		/*
		 * assertEquals(driver.findElement(By.id(Permissions.PERMISSION_NAME)).
		 * getText(), PERMISSION_NAME_A);
		 * assertEquals(driver.findElement(By.id(Permissions
		 * .DOCUMENT_TYPE)).getText(), "PurchaseOrder");
		 */

		/*
		 * assertTrue(driver.findElement(By.cssSelector(Permissions.AXIS_ADMIN)).
		 * isSelected());
		 * assertTrue(driver.findElement(By.cssSelector(Permissions
		 * .CUSTOMER)).isSelected());
		 * assertTrue(driver.findElement(By.cssSelector
		 * (Permissions.SUPPLIER)).isSelected());
		 */
	}

	// Step 2
	@Test(dependsOnMethods = "openMaintainPermissionScreen", alwaysRun = true)
	public void updatePermissionWithValidValue() {

		// Step 2 update
		permissionsAction.selectDocTypebyText(invoiceTypeName);
		// Unselect Customer and Supplier
		permissionsAction.selectUserType(Permissions.CUSTOMER_CHECKBOX);
		permissionsAction.selectUserType(Permissions.SUPPLIER_CHECKBOX);
//		USER_TYPE
		action.waitObjVisibleAndClick(By.id(Permissions.SAVE));
		action.assertTextEqual(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE), Messages.PERMISSION_UPADTED_SUCCESSFULLY);
		permissionsAction.enterValueTofilterPermission(Permissions_Creating.permissionName);
		action.pause(2000);
		// permissionsAction.filterPermissionbyDocumentType(invoiceTypeDescription);
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(), Permissions_Creating.permissionName);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(), "A");
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(), invoiceTypeDescription);

	}

	// Step 3
	@Test(dependsOnMethods = "updatePermissionWithValidValue", alwaysRun = true)
	public void checkUnsavedChangesDialog() throws InterruptedException {

		// Step 3 Click 1st instance ID
		action.waitObjVisibleAndClick(By.xpath(Permissions.GRID_PERMISSIONIDCELL));
		action.waitObjVisible(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER));

		action.inputTextField(Permissions.PERMISSION_NAME, "ECHO2");
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.assertTextEqual(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION), Messages.UNSAVED_CHANGE);

		// Step 4 Click No

		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));

		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(2000);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)), false);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)), false);

	}

}

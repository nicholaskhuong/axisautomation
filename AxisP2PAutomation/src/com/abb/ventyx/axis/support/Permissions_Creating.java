package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

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
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "437")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Creating extends BaseTestCase {

	public static String permissionName = "";
	String purchaseorderTypeName = "PurchaseOrder";
	String purchaseorderTypeDescription = "Purchase Orders";
	String userTypeCSA = "CSA";
	String addPermissionHeader = "Add Permission";
	String maintainPermissionHeader = "Maintain Permissions";

	PermissionsAction permissionsAction;
	ScreenAction action;
	TableFunction table;

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

	// Step 2, 3, 4
	@Test(dependsOnMethods = "openMaintainPermissionScreen")
	public void createPermissionwithValidValue() {

		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 100000000L);
		permissionName = String.format("Permision %s", drand);

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.inputTextField(Permissions.PERMISSION_NAME, permissionName);
		permissionsAction.selectDocTypebyText("Purchase Orders");
		permissionsAction.selectUserType(Permissions.AXIS_ADMIN);
		permissionsAction.selectUserType(Permissions.CUSTOMER);
		permissionsAction.selectUserType(Permissions.SUPPLIER);

		action.clickBtn(By.id(Permissions.SAVE));

		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(),
				Messages.PERMISSION_CREATED_SUCCESSFULLY);

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

	// Step 5, 6, 7
	@Test(dependsOnMethods = "createPermissionwithValidValue")
	public void addPermissonWithoutMandatoryField() throws InterruptedException {

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
	@Test(dependsOnMethods = "addPermissonWithoutMandatoryField")
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

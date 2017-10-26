package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.PermissionsAction;
import com.abb.ventyx.utilities.ScreenAction;

@ALM(id = "155")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Updating extends BaseTestCase {
	String PERMISSION_NAME_A = "AUTOMATION_PERMISSION_AA";
	String PERMISSION_NAME_B = "AUTOMATION_PERMISSION_BB";
	String DOCUMENT_TYPE_A = "PurchaseOrder";
	String USER_TYPE_A = "CSA";
	WebDriverWait wait;
	ScreenAction action;
	PermissionsAction permissionsAction;
	// Step 1
	@Test
	public void openMaintainPermissionScreen() throws Exception {
		// Update Permission
		wait = new WebDriverWait(driver, 30);
		action = new ScreenAction(driver);
		permissionsAction = new PermissionsAction(driver);
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		permissionsAction.clickSystemConfigurationMenu();
		permissionsAction.clickPermissionsSubMenu();

		permissionsAction.filterPermissionbyPermissionName(PERMISSION_NAME_A);
		permissionsAction.filterPermissionbyDocumentType("PurchaseOrder");
		Thread.sleep(1000);

		// Get Document Type value
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(), PERMISSION_NAME_A);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(), "CSA");
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(), "PurchaseOrder");

		// Click on Permission ID
		WebElement gridCell = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(Permissions.GRID_PERMISSIONIDCELL)));
		gridCell.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)));
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)).getText(), "Edit Permission");

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
	@Test(dependsOnMethods = "openMaintainPermissionScreen")
	public void updatePermissionWithValidValue() throws InterruptedException {

		// Step 2 update
		permissionsAction.selectDocTypebyText(DOCUMENT_TYPE_A);
		// Unselect Customer and Supplier
		permissionsAction.selectUserType(Permissions.CUSTOMER_CHECKBOX);
		permissionsAction.selectUserType(Permissions.SUPPLIER_CHECKBOX);
//		USER_TYPE
		permissionsAction.clickSaveButtonOnAddPermisisonPopUp();
		action.waitObjInvisible(By.id(Permissions.SAVE));
		action.assertTextEqual(By.cssSelector(Messages.PERMISSION_CREATED_SUCCESSFULLY_CSS), Messages.PERMISSION_UPADTED_SUCCESSFULLY);
		permissionsAction.enterValueTofilterPermission(PERMISSION_NAME_A);
		permissionsAction.filterPermissionbyDocumentType(DOCUMENT_TYPE_A);
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(), PERMISSION_NAME_A);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(), "A");
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(), DOCUMENT_TYPE_A);

	}

	// Step 3
	@Test(dependsOnMethods = "updatePermissionWithValidValue")
	public void checkUnsavedChangesDialog() throws InterruptedException {

		// Step 3 Click 1st instance ID
		WebElement gridCell1 = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(Permissions.GRID_PERMISSIONIDCELL)));
		gridCell1.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)));
		permissionsAction.enterPermissionName("ECHO2");
		permissionsAction.clickCancelButtonOnAddPermisisonPopUp();
		assertEquals(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(), Messages.UNSAVED_CHANGE);

		// Step 4 Click No
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)).getText(), "Edit Permission");

		permissionsAction.clickCancelButtonOnAddPermisisonPopUp();
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		Thread.sleep(1000);

		assertEquals(action.isElementPresent(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)), false);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)), false);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PERMISSIONHEADER)));

	}

}

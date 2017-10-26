package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
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

@ALM(id = "106")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Creating extends BaseTestCase {
	int numberOfRowsBeforeAdding;
	public static int numberOfRowsAfterAdding;
	String PERMISSION_NAME_A = "AUTOMATION_PERMISSION_AA";
	String PurchaseorderType = "Purchase Orders";
	String USER_TYPE_A = "CSA";
	String ADDPERMISSIONHEADER = "Add Permission";
	String MAINTAINPERMISSIONHEADER = "Maintain Permissions";

	// Step 1
	@Test
	public void openMaintainPermissionScreen() throws InterruptedException {
		PermissionsAction permissionsAction = new PermissionsAction(driver);

		permissionsAction.clickSystemConfigurationMenu();
		permissionsAction.clickPermissionsSubMenu();

		// Check there is any permission AA_MAINTAIN_PERMISSION existing
		permissionsAction.filterPermissionbyPermissionName(PERMISSION_NAME_A);
		permissionsAction.filterPermissionbyDocumentType("Purchase Orders");
		numberOfRowsBeforeAdding = permissionsAction.countRow(Permissions.TABLEBODY);
		System.out.print(numberOfRowsBeforeAdding + "numberOfRowsBeforeAdding");
	}

	// Step 2, 3, 4
	@Test(dependsOnMethods = "openMaintainPermissionScreen")
	public void createPermissionwithValidValue() throws InterruptedException {
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		permissionsAction.clickAddButton();
		permissionsAction.enterPermissionName(PERMISSION_NAME_A);
		permissionsAction.selectDocTypebyText("Purchase Orders");
		Thread.sleep(200);
		permissionsAction.selectUserType(Permissions.AXIS_ADMIN);
		permissionsAction.selectUserType(Permissions.CUSTOMER);
		permissionsAction.selectUserType(Permissions.SUPPLIER);

		permissionsAction.clickSaveButtonOnAddPermisisonPopUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Messages.PERMISSION_CREATED_SUCCESSFULLY_CSS)));
		assertEquals(driver.findElement(By.cssSelector(Messages.PERMISSION_CREATED_SUCCESSFULLY_CSS)).getText(),
				Messages.PERMISSION_CREATED_SUCCESSFULLY);
		// Filter
		permissionsAction.enterValueTofilterPermission(PERMISSION_NAME_A);
		permissionsAction.filterPermissionbyDocumentType("Purchase Orders");
		Thread.sleep(2000);
		numberOfRowsAfterAdding = permissionsAction.countRow(Permissions.TABLEBODY);

		assertEquals(numberOfRowsBeforeAdding + 1, numberOfRowsAfterAdding);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PNROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.UTROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.DTROW1)));

		assertEquals(
				driver.findElement(
						By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + numberOfRowsAfterAdding
								+ "]//td[2]")).getText(), PurchaseorderType);
		assertEquals(
				driver.findElement(
						By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + numberOfRowsAfterAdding
								+ "]//td[3]")).getText(), PERMISSION_NAME_A);
		assertEquals(
				driver.findElement(
						By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + numberOfRowsAfterAdding
								+ "]//td[4]")).getText(), USER_TYPE_A);
		Thread.sleep(1000);
	}

	// Step 5, 6, 7
	@Test(dependsOnMethods = "createPermissionwithValidValue")
	public void addPermissonWithoutMandatoryField() throws InterruptedException {
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		permissionsAction.clickAddButton();

		// Step 6
		permissionsAction.clickSaveButtonOnAddPermisisonPopUp();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(), Messages.EMPTYPERMISSIONNAME);

		// Step 7
		permissionsAction.enterPermissionName("ECHO 1");
		permissionsAction.clickSaveButtonOnAddPermisisonPopUp();
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(), Messages.EMPTYUSERTYPE);
		Thread.sleep(2000);
	}

	// Step 8, 9, 10
	@Test(dependsOnMethods = "addPermissonWithoutMandatoryField")
	public void checkUnsavedChangesDialog() throws InterruptedException {
		// Step 8
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		ScreenAction action = new ScreenAction(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		permissionsAction.clickCancelButtonOnAddPermisisonPopUp();
		assertEquals(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(), Messages.UNSAVED_CHANGE);

		// Step 9
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)).getText(), ADDPERMISSIONHEADER);

		// Step 10
		permissionsAction.clickCancelButtonOnAddPermisisonPopUp();
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSIONHEADER)).getText(), MAINTAINPERMISSIONHEADER);

		// Step 11
		permissionsAction.clickAddButton();
		permissionsAction.clickCancelButtonOnAddPermisisonPopUp();

		assertEquals(action.isElementPresent(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)), false);
		assertEquals(action.isElementPresent(By.cssSelector(Permissions.PERMISSIONWINDOWHEADER)), false);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PERMISSIONHEADER)));
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSIONHEADER)).getText(), MAINTAINPERMISSIONHEADER);
	}
}

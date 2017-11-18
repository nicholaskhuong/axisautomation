package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
@ALM(id = "972")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_Unsaved_Changes_Dialog extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;

	// Step 1
	@Test
	public void openSupplierListScreen(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		wait = new WebDriverWait(driver, 20);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	}
	// Create
	// Step 2
	@Test(dependsOnMethods = "openSupplierListScreen", alwaysRun = true)
	public void checkCancelButtonWithoutInput() {
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.pause(1000);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)), true);
		// action.assertTitleScreen("Check Suppliers");
	}

	// Step 3
	@Test(dependsOnMethods = "checkCancelButtonWithoutInput", alwaysRun = true)
	public void checkNoButtonOnUnsavedChangesDialog() {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, "test");
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		// action.assertTitleScreen("Create New Supplier");
	}

	// Step 4, 5
	@Test(dependsOnMethods = "checkNoButtonOnUnsavedChangesDialog", alwaysRun = true)
	public void checkYesButtonOnUnsavedChangesDialog() {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		// action.assertTitleScreen("Check Suppliers");
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)), true);
	}
	// Edit
	
	// Step 7
	@Test(dependsOnMethods = "checkYesButtonOnUnsavedChangesDialog", alwaysRun = true)
	public void clickBackButtonOnCheckSuppliersScreen() {
		action.waitObjVisibleAndClick(By.id(ScreenObjects.BACK_ID));
		action.pause(500);
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	}
	// Step 7
	@Test(dependsOnMethods="clickBackButtonOnCheckSuppliersScreen")
	public void checkCancelWithoutInput() {
		action.pause(500);
		action.waitObjVisibleAndClick(By.id("spIdBtn0"));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)),false);
		action.pause(1000);
		action.assertTitleScreen("Maintain Suppliers");
	}

	// Step 8, 9 Check No button on Unsaved Changes dialog 
	@Test(dependsOnMethods="checkCancelWithoutInput")
	public void checkNoButtonOnUnsavedChangesDialog2nd() {

		action.waitObjVisibleAndClick(By.id("spIdBtn0"));
		action.pause(500);
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, "test");
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(500);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_ID)),true);
	}

	// Step 10, 11 Check Yes button on Unsaved Changes dialog
	@Test(dependsOnMethods="checkNoButtonOnUnsavedChangesDialog2nd")
	public void checkYesButtonOnUnsavedChangesDialog2nd() {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_ID)), false);
		action.pause(500);
		action.assertTitleScreen("Maintain Suppliers");
	}
}

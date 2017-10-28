package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
@ALM(id = "614")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_UpdateSupplier_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i;
	int j;
	public static String supplierName = "Name 297587967";
	public static String supplierEmail = "297587967@abb.com";
	public static String companyRegistrationNo = "NO297587967";
	public static String taxRegistrationNo = "Tax297587967";
	String activeStatus = "Active";
	String INVALIDEMAIL = "<HTML>";
	String PASSWORDORIGINALLY = "Testuser2";
	String NEWPASSWORD = "Testuser1";

	String newSupplierName = "NewName";
	String profileUpdated = "ASNOFF";

	// Step 1
	@Test
	public void openSupplierListScreen(){
		table = new TableFunction(driver);
		action = new ScreenAction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
	}

	// Step 2 Update with null data
	@Test(dependsOnMethods="openSupplierListScreen")
	public void updateSupplierWithBlankMandatoryField(){

		table.filter(SupplierList.SUPPLIER_EMAIL_FILTER_XPATH, SupplierList_CreateNewSupplier_ByAdmin.supplierEmail);
		i = table.findRowByString(6, SupplierList_CreateNewSupplier_ByAdmin.supplierEmail);
		System.out.println("print i: "+i);
		assertEquals(table.getValueRow(2, i), SupplierList_CreateNewSupplier_ByAdmin.companyRegistrationNo);
		assertEquals(table.getValueRow(3, i), SupplierList_CreateNewSupplier_ByAdmin.taxRegistrationNo);
		assertEquals(table.getValueRow(4, i), activeStatus);
		assertEquals(table.getValueRow(5, i), SupplierList_CreateNewSupplier_ByAdmin.supplierName);
		assertEquals(table.getValueRow(7, i), SupplierList_CreateNewSupplier_ByAdmin.profile);

		assertEquals(action.isRemoteIconDisable(i), false);

		table.getCellObject(i, 1).click();

		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.assertTextBoxDisable(By.id(SupplierList.COMPANYREGISTRATIONNO_ID));
		action.assertTextBoxDisable(By.id(SupplierList.TAXREGRISTRATIONNO_ID));
		action.assertTextBoxDisable(By.id(SupplierList.SUPPLIEREMAIL_ID));
		//action.assertFieldReadOnly(By.id(SupplierList.SUPPLIERADMINUSERID_ID));
		// Empty Supplier Name
		driver.findElement(By.id(SupplierList.SUPPLIERNAME_ID)).clear();
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
	}

	// Step 3 
	@Test(dependsOnMethods="updateSupplierWithBlankMandatoryField")
	public void updateSupplierWithValidValue() {
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000000000L);
		newSupplierName = String.format("Nam%s", drand);
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, newSupplierName);
		action.clickBtn(By.cssSelector(SupplierList.PROFILE_CSS));
		action.pause(500);
		action.selectStatus(SupplierList.COMBOBOX_CSS, profileUpdated);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.SUPPLIER_UPDATED_SUCCESSFULLY);
		System.out.println("2 print i: "+i);
		assertEquals(table.getValueRow(5, i), newSupplierName);
		assertEquals(table.getValueRow(7, i), profileUpdated);
	}

	// Step 4
	@Test(dependsOnMethods="updateSupplierWithValidValue")
	public void checkCancelWithoutInput() {
		action.pause(500);
		// table.clickSupplierIDInSupplierListGrid(SupplierList_CreateNewSupplier_ByAdmin.taxRegistrationNo);
		table.getCellObject(i, 1).click();
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)),false);
		action.pause(1000);
		action.assertTitleScreen("Maintain Suppliers");
	}

	// Step 5,6 Check No button on Unsaved Changes dialog 
	@Test(dependsOnMethods="checkCancelWithoutInput")
	public void checkNoButtonOnUnsavedChangesDialog() {

		// table.clickSupplierIDInSupplierListGrid(SupplierList_CreateNewSupplier_ByAdmin.taxRegistrationNo);
		table.getCellObject(i, 1).click();
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

	// Step 7,8 Check Yes button on Unsaved Changes dialog
	@Test(dependsOnMethods="checkNoButtonOnUnsavedChangesDialog")
	public void checkYesButtonOnUnsavedChangesDialog() {

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

	@Test(dependsOnMethods="checkYesButtonOnUnsavedChangesDialog")
	public void accessSupplierFromCustomerAndCheckASNOff() {

		action.clickRemoteIcon(i);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(AddressContact.COMPANY_NAME));
		// action.assertTextEqual(By.id(AddressContact.COMPANY_NAME),
		// newSupplierName);
		System.out.println(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value")+" Supplier Name");
		assertEquals(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value"), newSupplierName);
	
	}
	
	@Test(dependsOnMethods="accessSupplierFromCustomerAndCheckASNOff")
	public void loginAsTheUpdatedSupplierAndCheckASNOff() {

		action.signOut();
		action.pause(3000);
		action.signOut();
		action.pause(1000);
		action.signIn(SupplierList_CreateNewSupplier_ByAdmin.supplierEmail, NEWPASSWORD);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		assertEquals(action.isElementPresent(By.id("menuItemAsn")),false);
		action.waitObjVisible(By.id(AddressContact.COMPANY_NAME));
		// action.assertTextEqual(By.id(AddressContact.COMPANY_NAME),
		// newSupplierName);
		assertEquals(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value"), newSupplierName);
	}
}

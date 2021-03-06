package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressAndContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.DocumentsInError;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "835")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_RemoteIcon_Step1_11 extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i;

	String inactiveStatus = "Inactive";
	String activeStatus = "Active";
	String password1 = "Testuser1";
	String password2 = "Testuser2";
	String userID = "Adminstrator1";
	public static String profile = "All Document Types";
	String customerUser = "cuserdefault@abb.com";
	String ASNType = "AdvanceShippingNotice";
	String invoiceType = "Invoice";
	String POAckType = "PurchaseOrderAcknowledgement";
	String supplierEmail = "yamaha7@abb.com";
	String supplierID = "100000041246";

	// Step 1
	@Test
	public void openSupplierListScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Suppliers");
	}

	@Test(dependsOnMethods = "openSupplierListScreen", alwaysRun = true)
	public void assertRemoteIconDisableForTestSupplier() {
		// table.filter(SupplierList.SUPPLIER_EMAIL_FILTER_XPATH,
		// supplierEmail);
		table.clickFilterAndInput(SupplierList.SUPPLIER_ID_FILTER_XPATH, supplierID);
		i = table.findRowByString(1, supplierID);
		// supplierID = table.getCellObject(ScreenObjects.TABLE_BODY_XPATH, i,
		// 1).getText();
		System.out.print("Supplier ID: " + supplierID);
		assertEquals(action.isRemoteIconDisable(i), false);
	}

	// Step 2 already scripted in test case 644 and 630

	// Step 3
	@Test(dependsOnMethods = "assertRemoteIconDisableForTestSupplier", alwaysRun = true)
	public void changeCustomerProfileDetails() {

		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(UserPreferences.EDITPROFILE_ID));
		action.waitObjVisibleAndClick(By
				.id(UserPreferences.CONTACTDETAILSTAB_ID));
		action.waitObjVisible(By.id(UserPreferences.USER_ID));
		action.inputTextField(UserPreferences.USER_ID, userID);
		action.waitObjVisibleAndClick(By
				.cssSelector(UserPreferences.SAVEBUTTON_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE,
				Messages.USERPREFERENCES_UPDATED_SUCCESSFULLY);
		assertEquals(table.getValueRow(7, i), profile);

	}

	@Test(dependsOnMethods = "changeCustomerProfileDetails", alwaysRun = true)
	public void remoteToSupplierAndAssertDocumentAvailable() {
		action.clickRemoteIcon(i);
		action.waitObjVisible(By.id(SupplierMenu.PURCHASE_ORDERS_ID));
		action.assertTitleScreen("Supplier Dashboard");
		assertEquals(action.isElementPresent(By.id(SupplierMenu.PURCHASE_ORDERS_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.SHIPPING_NOTICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.INVOICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.ADMINISTRATION_ID)), true);

	}
	
	// Step 4
	@Test(dependsOnMethods = "remoteToSupplierAndAssertDocumentAvailable", alwaysRun = true)
	public void assertPenIconInvisible() {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisible(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.pause(2000);
		assertEquals(action.isElementPresent(By.id(UserPreferences.EDITPROFILE_ID)),false);
	
	}

	// Step 5
	@Test(dependsOnMethods = "assertPenIconInvisible", alwaysRun = true)
	public void signOutAndAssertBackToCustomerView() {
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.pause(2000);
		action.waitObjVisible(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.assertTitleScreen("Customer Dashboard");
	}

	// Step 6
	@Test(dependsOnMethods = "signOutAndAssertBackToCustomerView", alwaysRun = true)
	public void openSupplierListAndClickRemoteIcon() {
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		table.clickFilterAndInput(SupplierList.SUPPLIER_ID_FILTER_XPATH, supplierID);
		i = table.findRowByString(1, supplierID);
		Assert.assertTrue(i >= 0, String.format("Supplier %s not found", supplierID));
		action.clickRemoteIcon(i);
		action.waitObjVisible(By.id(SupplierMenu.PURCHASE_ORDERS_ID));
		action.assertTitleScreen("Supplier Dashboard");
		assertEquals(action.isElementPresent(By.id(SupplierMenu.PURCHASE_ORDERS_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.SHIPPING_NOTICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.INVOICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.ADMINISTRATION_ID)), true);
	}

	// Step 7, 8, 9 will script after done creating, modifying, deleting PO Ack,
	// Invoice, ASN

	// Step 10
	@Test(dependsOnMethods = "openSupplierListAndClickRemoteIcon", alwaysRun = true)
	public void checkAddressAndContact() {
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));

		action.waitObjVisible(By.id(AddressAndContact.SUPLLIERNAME_ID));

		action.assertTextBoxDisable(By.id(AddressAndContact.COMPANYREGISNO_ID));
		action.assertTextBoxDisable(By.id(AddressAndContact.TAXREGISNO_ID));
		action.assertTextBoxDisable(By.id(AddressAndContact.SUPPLIEREMAIL_ID));

	}

	// Step 11 documents in error not available

	@Test(dependsOnMethods = "checkAddressAndContact", alwaysRun = true)
	public void checkDocumentsInError() {

		action.waitObjVisibleAndClick(By.id(SupplierMenu.DOCUMENT_IN_ERROR_ID));
		action.waitObjVisible(By.id("componentId0"));

		// Check ASN in Error
		table.clickFilterAndInput(DocumentsInError.DOC_TYPE_FILTER_XPATH, ASNType);
		table.getCellObject(1, 2).click();
		action.pause(1000);
		if (action.isElementPresent(By.cssSelector(ScreenObjects.ERROR_CSS))) {
			action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.ASN_NOT_FOUND);
			action.pause(1000);
		} else {
			action.pause(2000);
			action.assertDocumentTitle("Shipping Notice");

			action.waitObjVisibleAndClick(By.id(ScreenObjects.BACK_ID));
		}
		action.waitObjVisible(By.id("componentId0"));
		action.assertTitleScreen("Documents in Error");
	}
	@Test(dependsOnMethods = "checkDocumentsInError", alwaysRun = true)
	public void filterDocumentInvoice() {
		// Check Invoice in Error
		table.clickFilterAndInput(DocumentsInError.DOC_TYPE_FILTER_XPATH, invoiceType);
		i = table.findRowByString(3, invoiceType);
		Assert.assertTrue(i >= 0);
		table.getCellObject(i, 2).click();
		action.pause(1000);
		if (action.isElementPresent(By.cssSelector(ScreenObjects.ERROR_CSS))) {
			action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.INVOICE_NOT_FOUND);
			action.pause(1000);
		} else {
			action.pause(2000);
			action.assertDocumentTitle("Invoice");
			action.waitObjVisibleAndClick(By.id(ScreenObjects.BACK_ID));
		}
		action.waitObjVisible(By.id("componentId0"));
		action.assertTitleScreen("Documents in Error");
	}

	@Test(dependsOnMethods = "filterDocumentInvoice", alwaysRun = true)
	public void filterDocumentPOAckType() {
		// Check PO Ack in Error
		//table.clickFilterAndInput(DocumentsInError.DOC_TYPE_FILTER_XPATH, POAckType);
		table.inputFilterAtIndex(POAckType, DocumentsInError.DOC_TYPE_FILTER_XPATH, true);
		table.getCellObject(1, 2).click();
		action.pause(1000);
		if (action.isElementPresent(By.cssSelector(ScreenObjects.ERROR_CSS))) {
			action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.POACK_NOT_FOUND);
			action.pause(1000);
		} else {
			action.pause(2000);
			action.waitObjVisibleAndClick(By.id(ScreenObjects.BACK_ID));
		}
		action.waitObjVisible(By.id("componentId0"));
		action.assertTitleScreen("Documents in Error");

	}

	// // Step 12
	// @Test(dependsOnMethods = "filterDocumentPOAckType", alwaysRun = true)
	// public void signOutAgain() {
	// action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
	// action.pause(5000);
	// WebElement btn = (new WebDriverWait(driver,
	// 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(ScreenObjects.SIGNOUT_BUTTON)));
	// ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	// btn);
	// }

}

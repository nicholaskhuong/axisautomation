package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressAndContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.DocumentsInError;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "835")
@Credentials(user = "cuserdefault@abb.com", password = "Testuser1")
public class SupplierList_RemoteIcon_Step12_P1 extends BaseTestCase {
	ScreenAction action;
	TableFunction table;

	String activeStatus = "Active";
	String invoiceType = "Invoice";
	String POAckType = "PurchaseOrderAcknowledgement";
	String supplierID = "100000041246";


	// Step 12
	@Test
	public void loginAsCustomerUserAndCheckRemoteIconAvailable() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Suppliers");

		table.filter(SupplierList.SUPPLIER_ID_FILTER_XPATH, supplierID);
		assertEquals(action.isRemoteIconDisable(1), false);
		assertEquals(table.getValueRow(4, 1), activeStatus);
	}


	// Step 13
	@Test(dependsOnMethods = "loginAsCustomerUserAndCheckRemoteIconAvailable", alwaysRun = true)
	public void accessSupplier() {
		action.clickRemoteIcon(1);
		action.waitObjVisible(By.id(SupplierMenu.PURCHASE_ORDERS_ID));
		action.assertTitleScreen("Supplier Dashboard");

		assertEquals(action.isElementPresent(By.id(SupplierMenu.PURCHASE_ORDERS_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.SHIPPING_NOTICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.INVOICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.ADMINISTRATION_ID)), true);

		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));

		action.waitObjVisible(By.id(AddressAndContact.SUPLLIERNAME_ID));

		action.assertTextBoxDisable(By.id(AddressAndContact.COMPANYREGISNO_ID));
		action.assertTextBoxDisable(By.id(AddressAndContact.TAXREGISNO_ID));
		action.assertTextBoxDisable(By.id(AddressAndContact.SUPPLIEREMAIL_ID));

		action.waitObjVisibleAndClick(By.id(SupplierMenu.DOCUMENT_IN_ERROR_ID));
		action.waitObjVisible(By.id("componentId0"));

		// Check Invoice in Error
		table.filter(DocumentsInError.DOC_TYPE_FILTER_XPATH, invoiceType);
		table.getCellObject(1, 2).click();
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

		// Check PO Ack in Error
		table.filter(DocumentsInError.DOC_TYPE_FILTER_XPATH, POAckType);
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
}

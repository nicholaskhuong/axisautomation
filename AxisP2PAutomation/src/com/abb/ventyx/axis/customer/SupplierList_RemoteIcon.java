package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressAndContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "835")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_RemoteIcon extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i;
	int j;
	String supplierName = "Yamaha7";
	String supplierEmail = "yamaha7@abb.com";
	// String userSupplierEmailCreated = "cuseryamaha8@abb.com";
	// String userSupplierEmailActive = "cuseractive@abb.com";
	String inactiveStatus = "Inactive";
	String activeStatus = "Active";
	String password1 = "Testuser1";
	String password2 = "Testuser2";
	String userID = "Adminstrator1";
	String profile = "All Document Types";
	String customerUser = "cuserdefault@abb.com";

	// Step 1
	@Test
	public void openSupplierListScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Suppliers");
		i = table.findRowByString1(6, supplierEmail);
		assertEquals(table.getValueRow(4, i), activeStatus);
		j = i - 1;
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn" + j)),
				false);
	}

	// Step 2 already scripted in test case 644 and 630

	// Step 3
	@Test(dependsOnMethods = "openSupplierListScreen")
	public void changeCustomerProfileAndClickRemoteIcon() {

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
		action.waitObjVisibleAndClick(By.id("accessSupplierBtn" + j));
		action.assertTitleScreen("Supplier Dashboard");
		assertEquals(
				action.isElementPresent(By.id(SupplierMenu.PURCHASE_ORDERS_ID)),
				true);
		assertEquals(action.isElementPresent(By
				.id(SupplierMenu.SHIPPING_NOTICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.INVOICES_ID)),
				true);
		assertEquals(
				action.isElementPresent(By.id(SupplierMenu.ADMINISTRATION_ID)),
				true);

	}

	// Step 4
	@Test(dependsOnMethods = "changeCustomerProfileAndClickRemoteIcon")
	public void openProfilePanel() {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisible(By.id(ScreenObjects.SIGNOUT_BUTTON));
		assertEquals(
				action.isElementPresent(By.id(UserPreferences.EDITPROFILE_ID)),
				true);
	}

	// Step 5
	@Test(dependsOnMethods = "openProfilePanel")
	public void signOutAtSupplierView() {
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.waitObjVisible(By
				.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.assertTitleScreen("Customer Dashboard");
	}

	// Step 6
	@Test(dependsOnMethods = "changeCustomerProfileAndClickRemoteIcon")
	public void openSupplierListAndClickRemoteIcon() {
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));

		action.waitObjVisibleAndClick(By.id("accessSupplierBtn" + j));
		action.assertTitleScreen("Supplier Dashboard");
		assertEquals(
				action.isElementPresent(By.id(SupplierMenu.PURCHASE_ORDERS_ID)),
				true);
		assertEquals(action.isElementPresent(By
				.id(SupplierMenu.SHIPPING_NOTICES_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.INVOICES_ID)),
				true);
		assertEquals(
				action.isElementPresent(By.id(SupplierMenu.ADMINISTRATION_ID)),
				true);
	}

	// Step 7, 8, 9 will script after done creating, modifying, deleting PO Ack,
	// Invoice, ASN

	// Step 10
	@Test(dependsOnMethods = "openSupplierListAndClickRemoteIcon")
	public void checkAddressAndContact() {
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));

		action.waitObjVisible(By.id(AddressAndContact.SUPLLIERNAME_ID));

		action.assertTextBoxDisable(By.id(AddressAndContact.COMPANYREGISNO_ID));
		action.assertTextBoxDisable(By.id(AddressAndContact.TAXREGISNO_ID));
		action.assertTextBoxDisable(By.id(AddressAndContact.SUPPLIEREMAIL_ID));

	}

	// Step 11 documents in error not available

	// Step 12
	@Test(dependsOnMethods = "checkAddressAndContact")
	public void loginAsCustomerUserAndCheckRemoteIconAvailable() {

		action.signOut();
		action.waitObjVisible(By
				.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.assertTitleScreen("Customer Dashboard");
		action.signOut();
		action.signIn(customerUser, password1);
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Suppliers");
		assertEquals(table.getValueRow(4, i), activeStatus);
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn" + j)),
				false);
	}

	// Step 13
	@Test(dependsOnMethods = "loginAsCustomerUserAndCheckRemoteIconAvailable")
	public void loginAsCustomerUser() {

		action.signOut();
		action.waitObjVisible(By
				.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.assertTitleScreen("Customer Dashboard");
		action.signOut();
		action.signIn(customerUser, password1);
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By
				.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Suppliers");
		assertEquals(table.getValueRow(4, i), activeStatus);
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn" + j)),
				false);
	}

}

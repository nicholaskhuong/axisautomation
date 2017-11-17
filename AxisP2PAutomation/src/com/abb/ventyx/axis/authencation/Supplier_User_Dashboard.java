package com.abb.ventyx.axis.authencation;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "976")
@Credentials(user = "supplier_user_14@abb.com", password = "Testuser1")
public class Supplier_User_Dashboard extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String supplierDashborad = "Supplier Dashboard";
	int milliseconds = 3000;
	String userName = "User of Supplier";
	String userID = "supplier_user_14@abb.com";
	String purchaseOrders = "Purchase Orders";
	String shippingNotices = "Shipping Notices";
	String invoices = "Invoices";
	String administration = "Administration";
	String documentation = "Documentation";
	String userGroups = "User Groups";
	String users = "Users";
	String addressContact = "Address & Contact";
	String auditLog = "Audit Log";
	String businessCodeSets = "Business Code Sets";
	String documentInError = "Documents in Error";
	String maintainSupplierUserGroups = "Maintain Supplier User Groups";
	String maintainAddressContact = "Maintain Address & Contact";
	String supplierCodeSets = "Supplier Code Sets";
	String userGuide = "User Guide";
	String about = "About";
	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.assertTitleScreen(supplierDashborad);
	}
	@Test(dependsOnMethods = "openScreen")
	public void checkTopRibbon() {
		// step 2,10
		assertEquals(driver.findElement(By.id(AxisConfigMenu.LANGUAGE)).getText(), "EN");
		assertEquals(driver.findElement(By.id(UserPreferences.PROFILE_PANEL)).getText(), userName);
		action.clickBtn(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisible(By.id(ScreenObjects.SIGNOUT_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_LOGIN)).getText(), userName);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_ID)).getText(), userID);
		action.clickBtn(By.id(UserPreferences.PROFILE_PANEL));
	}

	@Test(dependsOnMethods = "checkTopRibbon")
	public void checkMenuOptions() {
		// Step 3
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.SUPPLIER_DASHBOARD_WITHOUT_ICON)).getText(), supplierDashborad);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.PURCHASE_ORDERS_WITHOUT_ICON)).getText(), purchaseOrders);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.SHIPPING_NOTICES_WITHOUT_ICON)).getText(), shippingNotices);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.INVOICES_WITHOUT_ICON)).getText(), invoices);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.ADMINISTRATION_WITHOUT_ICON)).getText(), administration);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.DOCUMENTATION_WITHOUT_ICON)).getText(), documentation);
	}

	@Test(dependsOnMethods = "checkMenuOptions")
	public void CheckPageOfSupplierDashBoard() {
		// Step 4,5,6
		action.clickBtn(By.id(SupplierMenu.PURCHASE_ORDERS_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), purchaseOrders);
		action.clickBtn(By.id(SupplierMenu.SHIPPING_NOTICES_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), shippingNotices);
		action.clickBtn(By.id(SupplierMenu.INVOICES_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), invoices);
	}

	@Test(dependsOnMethods = "CheckPageOfSupplierDashBoard")
	public void checkSubmenuOfAdministration() {
		// Step 7
		action.clickBtn(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisible(By.id(SupplierMenu.USER_GROUPS_ID));
		assertEquals(driver.findElement(By.id(SupplierMenu.USER_GROUPS_ID)).getText(), userGroups);
		assertEquals(driver.findElement(By.id(SupplierMenu.USERS_ID)).getText(), users);
		assertEquals(driver.findElement(By.id(SupplierMenu.ADDRESS_CONTACT_ID)).getText(), addressContact);
		assertEquals(driver.findElement(By.id(SupplierMenu.AUDIT_LOG_ID)).getText(), auditLog);
		assertEquals(driver.findElement(By.id(SupplierMenu.BUSINESS_CODE_SETS_ID)).getText(), businessCodeSets);
		assertEquals(driver.findElement(By.id(SupplierMenu.DOCUMENT_IN_ERROR_ID)).getText(), documentInError);
	}

	@Test(dependsOnMethods = "checkSubmenuOfAdministration")
	public void checkPageOfAdministration() {
		// Step 8
		action.clickBtn(By.id(SupplierMenu.USER_GROUPS_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), maintainSupplierUserGroups);
		action.clickBtn(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), users);
		action.clickBtn(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), maintainAddressContact);
		action.clickBtn(By.id(SupplierMenu.AUDIT_LOG_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), auditLog);
		action.clickBtn(By.id(SupplierMenu.BUSINESS_CODE_SETS_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), supplierCodeSets);
		action.clickBtn(By.id(SupplierMenu.DOCUMENT_IN_ERROR_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), documentInError);
	}

	@Test(dependsOnMethods = "checkPageOfAdministration")
	public void checkSubmenuDocumentation() {
		// step 9
		action.clickBtn(By.id(SupplierMenu.DOCUMENTATION_ID));
		action.waitObjVisible(By.id(SupplierMenu.USER_GUIDE_ID));
		assertEquals(driver.findElement(By.id(SupplierMenu.USER_GUIDE_ID)).getText(), userGuide);
		assertEquals(driver.findElement(By.id(SupplierMenu.ABOUT_ID)).getText(), about);
	}

	@Test(dependsOnMethods = "checkSubmenuDocumentation")
	public void signOut() {
		// step 11
		action.signOut();

	}
}

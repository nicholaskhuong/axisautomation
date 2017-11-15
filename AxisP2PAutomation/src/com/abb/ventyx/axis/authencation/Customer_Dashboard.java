package com.abb.ventyx.axis.authencation;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "493")
@Credentials(user = "customer_user_1@abb.com", password = "Testuser1")
public class Customer_Dashboard extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int milliseconds = 3000;
	String customerDashboard = "Customer Dashboard";
	String axisSupportPortal = "Axis Supplier Portal";
	String userName = "Customer User 1";
	String userID = "customer_user_1@abb.com";
	String customerMaintenance = "Customer Maintenance";
	String notifications = "Notifications";
	String documentation = "Documentation";
	String supplierList = "Supplier List";
	String userGroups = "User Groups";
	String users = "Users";
	String profiles = "Profiles";
	String documentFilters = "Document Filters";
	String busniessCodeSets = "Business Code Sets";
	String addressContact = "Address & Contact";
	String customiseLiterals = "Customise Literals";
	String documentInError = "Documents in Error";
	String auditLog = "Audit Log";
	String configureDocument = "Configure Documentation";
	String userGuide = "User Guide";
	String about = "About";
	String maintainSuppliers = "Maintain Suppliers";
	String maitainUserGroups = "Maintain User Groups";
	String maintainCustomerUsers = "Maintain Customer Users";
	String maintainCustomerDefinedProfiles = "Maintain Customer Defined Profiles";
	String maintainBusinessCodeSets = "Maintain Business Code Sets";
	String customerDefinedFilters = "Customer Defined Filters";
	String maintainAddressContact = "Maintain Address & Contact";
	String maintainLiteralsForACustomer = "Maintain Literals for a Customer";
	String documentsInError = "Documents in Error";
	String configureDocumentation = "Configure Documentation";
	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.assertTitleScreen(customerDashboard);
	}
	@Test(dependsOnMethods = "openScreen")
	public void checkTopRibbon() {
		// step 2,4
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AXIS_SUPPORT_PORTAL)).getText(), axisSupportPortal);
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
		// step 3
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.CUSTOMER_DASHBOARD_WITHOUT_ICON)).getText(), customerDashboard);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.CUSTOMER_MAINTENANCE_WITHOUT_ICON)).getText(), customerMaintenance);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.NOTIFICATIONS_WITHOUT_ICON)).getText(), notifications);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.DOCUMENTATION_WITHOUT_ICON)).getText(), documentation);
	}

	@Test(dependsOnMethods = "checkMenuOptions")
	public void checkCustomerMaintenance() {
		// step 5,6
		action.clickBtn(By.id(CustomerMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisible(By.cssSelector(CustomerMenu.SUPPLIER_LIST));
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.SUPPLIER_LIST)).getText(), supplierList);
		assertEquals(driver.findElement(By.id(CustomerMenu.USER_GROUPS)).getText(), userGroups);
		assertEquals(driver.findElement(By.id(CustomerMenu.USERS)).getText(), users);
		assertEquals(driver.findElement(By.id(CustomerMenu.PROFILES)).getText(), profiles);
		assertEquals(driver.findElement(By.id(CustomerMenu.DOCUMENT_FILTERS)).getText(), documentFilters);
		assertEquals(driver.findElement(By.id(CustomerMenu.BUSINESS_CODE_SET)).getText(), busniessCodeSets);
		assertEquals(driver.findElement(By.id(CustomerMenu.ADDRESS_CONTACT)).getText(), addressContact);
		assertEquals(driver.findElement(By.id(CustomerMenu.CUSTOMISE_LITERALS)).getText(), customiseLiterals);
		assertEquals(driver.findElement(By.id(CustomerMenu.DOCUMENT_IN_ERROR)).getText(), documentInError);
		assertEquals(driver.findElement(By.id(CustomerMenu.AUDIT_LOG)).getText(), auditLog);
	}

	@Test(dependsOnMethods = "checkCustomerMaintenance")
	public void checkOpenNotificationsPage() {
		// step 7
		action.clickBtn(By.id(CustomerMenu.NOTIFICATIONS));
		action.waitObjVisible(By.cssSelector(CustomerMenu.DELETE_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), notifications);
	}

	@Test(dependsOnMethods = "checkOpenNotificationsPage")
	public void checkDocumentMenu() {
		// step 8
		action.clickBtn(By.id(CustomerMenu.DOCUMENTATION));
		action.waitObjVisible(By.id(CustomerMenu.CONFIGURE_DOCUMENT));
		assertEquals(driver.findElement(By.id(CustomerMenu.CONFIGURE_DOCUMENT)).getText(), configureDocument);
		assertEquals(driver.findElement(By.id(CustomerMenu.USER_GUIDE)).getText(), userGuide);
		assertEquals(driver.findElement(By.id(CustomerMenu.ABOUT)).getText(), about);
	}
	@Test(dependsOnMethods = "checkDocumentMenu")
	public void checkPageOfCustomerMaintainance() {
		// Step 9
		action.clickBtn(By.id(CustomerMenu.DOCUMENTATION));
		action.clickBtn(By.cssSelector(CustomerMenu.SUPPLIER_LIST));
		action.waitObjVisible(By.cssSelector(CustomerMenu.DEACTIVE_ICON));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), maintainSuppliers);
		action.clickBtn(By.id(CustomerMenu.USER_GROUPS));
		action.waitObjVisible(By.cssSelector(CustomerMenu.DOCUMENT_FILTER_HEADER));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.DOCUMENT_FILTER_HEADER)).getText(), maitainUserGroups);
		action.clickBtn(By.id(CustomerMenu.USERS));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), maintainCustomerUsers);
		action.clickBtn(By.id(CustomerMenu.PROFILES));
		action.waitObjVisible(By.cssSelector(CustomerMenu.MENU_ICON));
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), maintainCustomerDefinedProfiles);
		action.clickBtn(By.id(CustomerMenu.DOCUMENT_FILTERS));
		action.waitObjVisible(By.cssSelector(CustomerMenu.DOCUMENT_FILTER_HEADER));
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), customerDefinedFilters);
		action.clickBtn(By.id(CustomerMenu.BUSINESS_CODE_SET));
		action.waitObjVisible(By.cssSelector(CustomerMenu.BUSINESS_CODE_SETS_HEADER));
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), maintainBusinessCodeSets);
		action.clickBtn(By.cssSelector(CustomerMenu.ADDRESS_CONTACT_MENU));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), maintainAddressContact);
		action.clickBtn(By.id(CustomerMenu.CUSTOMISE_LITERALS));
		action.waitObjVisible(By.cssSelector(CustomerMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), maintainLiteralsForACustomer);
		action.clickBtn(By.id(CustomerMenu.DOCUMENT_IN_ERROR));
		action.waitObjVisible(By.cssSelector(CustomerMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), documentsInError);
		action.clickBtn(By.id(CustomerMenu.AUDIT_LOG));
		action.waitObjVisible(By.cssSelector(CustomerMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), auditLog);
	}

	@Test(dependsOnMethods = "checkPageOfCustomerMaintainance")
	public void checkPageOfDocumentation() {
		// step 10
		action.clickBtn(By.id(CustomerMenu.DOCUMENTATION));
		action.waitObjVisible(By.id(CustomerMenu.CONFIGURE_DOCUMENT));
		action.clickBtn(By.id(CustomerMenu.CONFIGURE_DOCUMENT));
		action.waitObjVisible(By.cssSelector(CustomerMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(CustomerMenu.HEADER_OF_PAGE)).getText(), configureDocumentation);
		// Step 11
		action.signOut();
	}


}

package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerList;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "850")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Access_Customer_And_Supplier extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 2000;
	WebElement index;
	String nameCustomerList = "Development Cust";
	int i;
	String companyRegistrationNo = "REG";

	// Step 01
	@Test
	public void openCustomerListScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_CUSTOMERS_PAGE);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		assertEquals(table.getValueTableHeader(2), "Name");
		assertEquals(table.getValueTableHeader(3), "Status");
		assertEquals(table.getValueTableHeader(4), "Email");
		assertEquals(table.getValueTableHeader(5), "Homepage");
		assertEquals(table.getValueTableHeader(1), "ID");
	}

	// Step 2
	@Test(dependsOnMethods = "openCustomerListScreen", alwaysRun = true)
	public void clickDeactivateAnCustomer() {
		table.clickFilterAndInputWithColumn(nameCustomerList, CustomerList.NAME_FILTER, true);
		action.pause(3000);
		WebElement row = driver.findElement(By.xpath(ScreenObjects.TABLE_ROW_XPATH_CUSTOMER));
		row.click();
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_DEACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEACTIVATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		index = table.getCellObject(1, 6);
		index.isDisplayed();
	}

	@Test(dependsOnMethods = "clickDeactivateAnCustomer", alwaysRun = true)
	public void clickActivateTheCustomer() {
		action.pause(3000);
		WebElement row = driver.findElement(By.xpath(ScreenObjects.TABLE_ROW_XPATH_CUSTOMER));
		row.click();
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ACTIVE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_ACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.ACTIVATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		index = table.getCellObject(1, 6);
		index.isEnabled();
	}

	// Step 03
	@Test(dependsOnMethods = "clickActivateTheCustomer", alwaysRun = true)
	public void clickToAccessaActivateCustomer() {
		index = table.getCellObject(1, 6);
		index.click();
		action.waitObjVisible(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_CUSTOMER_DASHBOARD_PAGE);
	}

	// Step 04
	@Test(dependsOnMethods = "clickToAccessaActivateCustomer", alwaysRun = true)
	public void checkAllFunctionInCustomer() {
		assertEquals(action.isElementPresent(By.id(UserPreferences.EDITPROFILE_ID)), false);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.SUPPLIER_LIST));
		action.waitObjVisible(By.cssSelector(CustomerList.ADD_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_SUPPLIER_LIST);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USER_GROUP_CUSTOMER));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_USER_GROUPS);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USER_CUSTOMER));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_USERS);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_PROFILES);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.DOCUMENT_FILTERS));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_DOCUMENT_FILTERS);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.BUSINESS_CODE_SETS));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_BUSINESS_CODE_SETS);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.ADDRESS_AND_CONTACT));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_ADDRESS_CONTACT);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMISE_LITERALS));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_CUSTOMISE_LITERALS);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.DOCUMENT_IN_ERROR_CSS));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_DOCUMENTS_IN_ERROR);

		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AUDIT_LOG));
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_AUDITLOG);
	}

	@Test(dependsOnMethods = "checkAllFunctionInCustomer", alwaysRun = true)
	public void clickSignOutButton() {
		action.signOut();
		action.pause(1000);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_AXIS_SUPPORT_DASHBOARD);
	}

	// Step 04
	@Test(dependsOnMethods = "clickSignOutButton", alwaysRun = true)
	public void accessActiveCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
		table.clickFilterAndInputWithColumn(nameCustomerList, CustomerList.NAME_FILTER, true);
		action.pause(waitTime);
		index = table.getCellObject(1, 6);
		index.click();
	}

	@Test(dependsOnMethods = "accessActiveCustomer", alwaysRun = true)
	public void viewSupplierListOfCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.SUPPLIER_LIST));
		action.waitObjVisible(By.cssSelector(CustomerList.ADD_BUTTON));
		assertEquals(table.getValueTableHeader(1), "Supplier ID");
		assertEquals(table.getValueTableHeader(2), "Company Registration No");
		assertEquals(table.getValueTableHeader(3), "Tax Registration No");
		assertEquals(table.getValueTableHeader(4), "Supplier Status");
		assertEquals(table.getValueTableHeader(5), "Supplier Name");
		assertEquals(table.getValueTableHeader(6), "Supplier Notification Email");
		assertEquals(table.getValueTableHeader(7), "Profile Name");
	}

	@Test(dependsOnMethods = "viewSupplierListOfCustomer", alwaysRun = true)
	public void editAvaliableDocuments() {
		action.pause(waitTime);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 1);
		index.click();
		action.pause(waitTime);
		assertEquals(driver.findElement(By.cssSelector(CustomerList.EDIT_SUPPLIER_CSS)).getText(), CustomerList.TITLE_EDIT_SUPPLIER);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
	}

	// @Test(dependsOnMethods = "editAvaliableDocuments", alwaysRun = true)
	// public void accessSupplier() {
	// table.clikFilterAndInputWithColumn(companyRegistrationNo,
	// CustomerList.NAME_FILTER, true);
	// action.clickHorizontalScrollBar();
	// index = table.getCellObject(1, 8);
	// index.click();
	// action.pause(waitTime);
	// assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(),
	// CustomerList.TITLE_SUPPLIER_DASHBOARD);
	// }
	//
	// @Test(dependsOnMethods = "accessSupplier", alwaysRun = true)
	// public void accessAddressAndContact() {
	// action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.ADMINISTRATION));
	// action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.ADDRESS_AND_CONTACT));
	//
	// }

}

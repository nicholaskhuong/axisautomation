package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
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
	public void clickSupplierID(){

		table.clickFilterAndInput(SupplierList.SUPPLIER_EMAIL_FILTER_XPATH, SupplierList_CreateActiveSupplier_ByAdmin.supplierEmail);
		i = table.findRowByString(6, SupplierList_CreateActiveSupplier_ByAdmin.supplierEmail);
		Assert.assertTrue(1 >= 0, "Item doesn't exist!");
		WebElement accessSupplier = table.getCellObject(i, 8);
		action.clickHorizontalScrollBar(true);
		assertEquals(table.getValueRow(2, i), SupplierList_CreateActiveSupplier_ByAdmin.companyRegistrationNo);
		assertEquals(table.getValueRow(3, i), SupplierList_CreateActiveSupplier_ByAdmin.taxRegistrationNo);
		assertEquals(table.getValueRow(4, i), activeStatus);
		action.clickHorizontalScrollBarToElement(accessSupplier);
		assertEquals(table.getValueRow(5, i), SupplierList_CreateActiveSupplier_ByAdmin.supplierName);
		assertEquals(table.getValueRow(7, i), SupplierList_CreateActiveSupplier_ByAdmin.profile);

		assertEquals(action.isRemoteIconDisable(i), false);
		
		table.getCellObject(i, 1).click();
		action.isFieldDisable(accessSupplier);
		
	}

	// Step 3
	@Test(dependsOnMethods = "clickSupplierID", alwaysRun = true)
	public void clearDataAndSave() {
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

	// Step 4
	@Test(dependsOnMethods = "clearDataAndSave", alwaysRun = true)
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
	// Step 5
	@Test(dependsOnMethods="updateSupplierWithValidValue")
	public void accessSupplierFromCustomerAndCheckASNOff() {

		action.clickRemoteIcon(i);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(AddressContact.COMPANY_NAME));
		System.out.println(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value")+" Supplier Name");
		assertEquals(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value"), newSupplierName);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.PURCHASE_ORDERS_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.SHIPPING_NOTICES_ID)), false);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.INVOICES_ID)), true);
	}
	
	// Step 6, 7
	@Test(dependsOnMethods = "accessSupplierFromCustomerAndCheckASNOff", alwaysRun = true)
	public void signOutDefaultUser() {
		action.waitObjVisibleAndClick(By.id(UserPreferences.PROFILE_PANEL));
		action.pause(5000);
		WebElement btn = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(ScreenObjects.SIGNOUT_BUTTON)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		action.signOut();
	}
	@Test(dependsOnMethods = "signOutDefaultUser", alwaysRun = true)
	public void loginAsTheUpdatedSupplierAndCheckASNOff() {
		action.signIn(SupplierList_CreateActiveSupplier_ByAdmin.supplierEmail, SupplierList_CreateActiveSupplier_ByAdmin.password);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(AddressContact.COMPANY_NAME));
		assertEquals(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value"), newSupplierName);
		
		assertEquals(action.isElementPresent(By.id(SupplierMenu.PURCHASE_ORDERS_ID)), true);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.SHIPPING_NOTICES_ID)), false);
		assertEquals(action.isElementPresent(By.id(SupplierMenu.INVOICES_ID)), true);
	}
}

package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

	String SUPPLIERNAME = "Yamaha10";
	String SUPPLIEREMAIL = "yamaha10@abb.com";
	String COMPANYREGIRATIONNO = "COMYAMAHA10";
	String TAXREGIRATIONNO = "TAXYAMAHA10";
	String PENDINGSTATUS = "Pending";
	String ACTIVESTATUS = "Active";
	String PROFILE = "All Document Types";
	String INVALIDEMAIL = "<HTML>";
	String PASSWORDORIGINALLY = "Testuser2";
	String NEWPASSWORD = "Testuser1";

	String DUPLICATEDNAME = "ENCLAVE";
	String DUPLICATEDCOMPANYREGISTRATIONNO = "ENCLAVE";
	String DUPLICATEDTAXREGISTRATIONNO = "ENCLAVE";
	String DUPLICATEDSUPPLIEREMAIL = "perla@enclave.vn";
	String AXISSUPPORTEMAIL = "mail5@abb.com";
	String AXISSUPPORTPWD = "testuser";

	String NEWSUPPLIERNAME = "Yamaha9UPDATED";
	String PROFILEUPDATED = "ASNOFF";

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
		table = new TableFunction(driver);
		action = new ScreenAction(driver);

		i = table.findRowByString1(6, SUPPLIEREMAIL);
		System.out.println("print i: "+i);
		assertEquals(table.getValueRow(2, i), COMPANYREGIRATIONNO);
		assertEquals(table.getValueRow(3, i), TAXREGIRATIONNO);
		assertEquals(table.getValueRow(4, i), ACTIVESTATUS);
		assertEquals(table.getValueRow(5, i), NEWSUPPLIERNAME);
		assertEquals(table.getValueRow(6, i), SUPPLIEREMAIL);
		assertEquals(table.getValueRow(7, i), PROFILEUPDATED);
		j=i-1;
		System.out.println("Print J: "+ i);
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),false);

		table.clickSupplierIDInSupplierListGrid(TAXREGIRATIONNO);
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
	public void updateSupplierWithValidValue() throws InterruptedException{
		action = new ScreenAction(driver);

		action.inputTextField(SupplierList.SUPPLIERNAME_ID, NEWSUPPLIERNAME);
		action.clickBtn(By.cssSelector(SupplierList.PROFILE_CSS));
		Thread.sleep(1000);
		action.selectStatus(SupplierList.COMBOBOX_CSS, PROFILEUPDATED);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.SUPPLIER_UPDATED_SUCCESSFULLY);
		System.out.println("2 print i: "+i);
		Thread.sleep(1000);
		assertEquals(table.getValueRow(5, i), NEWSUPPLIERNAME);
		assertEquals(table.getValueRow(7, i), PROFILEUPDATED);
	}

	// Step 4
	@Test(dependsOnMethods="updateSupplierWithValidValue")
	public void checkCancelWithoutInput() throws InterruptedException{
		action = new ScreenAction(driver);
		table.clickSupplierIDInSupplierListGrid(TAXREGIRATIONNO);
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)),false);
		Thread.sleep(1000);
		action.assertTitleScreen("Maintain Suppliers");
	}

	// Step 5,6 Check No button on Unsaved Changes dialog 
	@Test(dependsOnMethods="checkCancelWithoutInput")
	public void checkNoButtonOnUnsavedChangesDialog() throws InterruptedException{
		action = new ScreenAction(driver);	
		table.clickSupplierIDInSupplierListGrid(TAXREGIRATIONNO);
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, "test");
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		Thread.sleep(500);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_ID)),true);
	}

	// Step 7,8 Check Yes button on Unsaved Changes dialog
	@Test(dependsOnMethods="checkNoButtonOnUnsavedChangesDialog")
	public void checkYesButtonOnUnsavedChangesDialog() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_ID)), false);
		Thread.sleep(1000);
		action.assertTitleScreen("Maintain Suppliers");
	}

	@Test(dependsOnMethods="checkYesButtonOnUnsavedChangesDialog")
	public void accessSupplierFromCustomerAndCheckASNOff() throws InterruptedException{
		action = new ScreenAction(driver);
		action.clickBtn(By.id("accessSupplierBtn"+j));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id(AddressContact.COMPANY_NAME)));
		//action.assertTextEqual(By.id(AddressContact.COMPANY_NAME), NEWSUPPLIERNAME);
		System.out.println(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value")+" Supplier Name");
		assertEquals(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value"), NEWSUPPLIERNAME);
	
	}
	
	@Test(dependsOnMethods="accessSupplierFromCustomerAndCheckASNOff")
	public void loginAsTheUpdatedSupplierAndCheckASNOff() throws InterruptedException {
		action = new ScreenAction(driver);
		action.signOut();
		Thread.sleep(1000);
		action.signOut();
		action.signIn(SUPPLIEREMAIL, NEWPASSWORD);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		assertEquals(action.isElementPresent(By.id("menuItemAsn")),false);
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id(AddressContact.COMPANY_NAME)));
		//action.assertTextEqual(By.id(AddressContact.COMPANY_NAME), NEWSUPPLIERNAME);
		assertEquals(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value"), NEWSUPPLIERNAME);
	}
}

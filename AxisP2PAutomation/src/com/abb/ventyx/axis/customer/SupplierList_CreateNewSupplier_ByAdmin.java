package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
@ALM(id = "607")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_CreateNewSupplier_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i;

	public static String supplierName = "Name 297587967";
	public static String supplierEmail = "297587967@abb.com";
	public static String companyRegistrationNo = "NO297587967";
	public static String taxRegistrationNo = "Tax297587967";
	String pendingStatus = "Pending";
	public static String profile = "All Document Types";
	String invalidEmail = "<HTML>";
	String passwordOriginally = "Testuser2";
	String newPassword = "Testuser1";

	String dupplicatedName = "ENCLAVE";
	String duplicatedCompanyRegistrationNo = "ENCLAVE";
	String duplicatedTaxRegistrationNo = "ENCLAVE";
	String duplicatedSupplierEmail = "perla@enclave.vn";
	String axisSupportEmail = "mail5@abb.com";
	String axisSupportPWD = "testuser";
	String password;

	// Step 1
	@Test
	public void openSupplierListScreen(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		wait = new WebDriverWait(driver, 20);
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 100000000L);
		supplierName = String.format("Name %s", drand);
		supplierEmail = String.format("%s@abb.com", drand);
		companyRegistrationNo = String.format("NO%s", drand);
		taxRegistrationNo = String.format("Tax%s", drand);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		assertEquals(table.getValueTableHeader(1), "Supplier ID");
		assertEquals(table.getValueTableHeader(2), "Company Registration No");
		assertEquals(table.getValueTableHeader(3), "Tax Registration No");
		assertEquals(table.getValueTableHeader(4), "Supplier Status");
		assertEquals(table.getValueTableHeader(5), "Supplier Name");
		assertEquals(table.getValueTableHeader(6), "Supplier Notification Email");
		assertEquals(table.getValueTableHeader(7), "Profile Name");
	}

	// Step 2
	@Test(dependsOnMethods = "openSupplierListScreen", alwaysRun = true)
	public void createSupplierWithBlankMandatoryField() {

		action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));

		// Empty Supplier Name
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, supplierEmail);
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, companyRegistrationNo);
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, taxRegistrationNo);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Empty Company Registration No
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, supplierName);
		driver.findElement(By.id(SupplierList.COMPANYREGISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Empty Tax Registration No
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, companyRegistrationNo);
		driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Empty Supplier Email
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, taxRegistrationNo);
		driver.findElement(By.id(SupplierList.SUPPLIEREMAIL_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
	}

	// Step 3
	@Test(dependsOnMethods = "createSupplierWithBlankMandatoryField")
	public void createSupplierWithInvalidEmail() {

		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, invalidEmail);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.clickBtn(By.id(SupplierList.SUPPLIEREMAIL_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 4
	@Test(dependsOnMethods = "createSupplierWithInvalidEmail")
	public void createSupplierWithDuplicatedValue() {

		// Duplicated email
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, duplicatedSupplierEmail);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATEDEMAIL);

		// Duplicated Comp. Registration Number
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, supplierEmail);
		WebElement txtField = driver.findElement(By.id(SupplierList.COMPANYREGISTRATIONNO_ID));
		txtField.clear();
		action.pause(1000);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		txtField.sendKeys(duplicatedCompanyRegistrationNo);

		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATECOMPANYREGISTRATIONNO);

		// Duplicated Tax Registration Number
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, duplicatedTaxRegistrationNo);

		WebElement txtField1 = driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID));
		txtField1.clear();
		action.pause(500);
		txtField1.sendKeys(duplicatedTaxRegistrationNo);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, companyRegistrationNo);

		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATEDTAXREGISTRATIONNO);

	}

	// Step 5
	@Test(dependsOnMethods = "createSupplierWithInvalidEmail")
	public void createSupplierWithValidValue() {

		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, taxRegistrationNo);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));

		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.SUPPLIER_CREATED_SUCCESSFULLY);
		table.filter(SupplierList.SUPPLIER_EMAIL_FILTER_XPATH, supplierEmail);
		i = table.findRowByString(6, supplierEmail);
		Assert.assertTrue(i >= 0, "Supplier doesn't exist");
		assertEquals(table.getValueRow(2, i), companyRegistrationNo);
		assertEquals(table.getValueRow(3, i), taxRegistrationNo);
		assertEquals(table.getValueRow(4, i), pendingStatus);
		assertEquals(table.getValueRow(5, i), supplierName);
		assertEquals(table.getValueRow(6, i), supplierEmail);
		assertEquals(table.getValueRow(7, i), profile);
		WebElement accessCell = table.getCellObject(i, 8);
		action.isFieldDisable(accessCell);
		// String supplierID = table.getIDValue(i);
	}

	// Step 6,7 can't auto
	// Step 8
	@Test(dependsOnMethods = "createSupplierWithValidValue")
	public void checkCancelButtonWithoutInput() {

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.pause(1000);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)), true);
		// action.assertTitleScreen("Check Suppliers");
	}

	// Step 9,10 Check No button on Unsaved Changes dialog
	@Test(dependsOnMethods = "checkCancelButtonWithoutInput")
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

	// Step 11,12 Check Cancel button on Unsaved Changes dialog
	@Test(dependsOnMethods = "checkNoButtonOnUnsavedChangesDialog")
	public void checkYesButtonOnUnsavedChangesDialog() {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		// action.assertTitleScreen("Check Suppliers");
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)), true);
	}

	// Step 14
	@Test(dependsOnMethods = "checkYesButtonOnUnsavedChangesDialog")
	public void loginAsTheCreatedSupplier() {

		action.signOut();
		action.signIn(supplierEmail, passwordOriginally);
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.ERROR_CSS), Messages.USERNOTFOUND);
	}
	// Step 15
	@Test(dependsOnMethods = "loginAsTheCreatedSupplier")
	public void getPasswordForTheNewSupplier() {

		action.signIn(axisSupportEmail, axisSupportPWD);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.RESET_USER_PASSWORD));
		action.pause(1000);
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.EMAILCHECKBOX_RESETUSERPASSWORDSCREEN_ID));
		action.pause(1000);
		action.inputTextField(ScreenObjects.USER_LOGIN, supplierEmail);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.RESET_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));

		String message = driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText();
		password = action.getPassword(message);
	}

	// Step 16
	@Test(dependsOnMethods = "getPasswordForTheNewSupplier")
	public void signOut() {
		action.signOut();
	}

	// Step 16
	@Test(dependsOnMethods = "signOut")
	public void changePassword() {
		assertEquals(action.isElementPresent(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID)), true);
		action.signIn(supplierEmail, password);

		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, newPassword);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID, newPassword);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
	}
	// Step 16
	@Test(dependsOnMethods = "changePassword")
	public void acceptTradingRelationshipRequest() {


		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.ACCEPT_BUTTON_CSS)));
		action.clickBtn(By.cssSelector(ScreenObjects.ACCEPT_BUTTON_CSS));

	}

	@Test(dependsOnMethods = "acceptTradingRelationshipRequest", alwaysRun = true)
	public void checkAddressAndContact() {

		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));

		WebElement companyName = wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id(AddressContact.COMPANY_NAME)));
		Assert.assertEquals(companyName.getAttribute("value"), supplierName);
		action.assertTextEqual(By.id(AddressContact.COMPANY_REGISTRATION_NO), companyRegistrationNo);
		action.assertTextEqual(By.id(AddressContact.TAX_REGISTRATION_NO), taxRegistrationNo);
		action.assertTextEqual(By.id(AddressContact.SUPPLIER_EMAIL), supplierEmail);
	}
	
	@Test(dependsOnMethods = "checkAddressAndContact", alwaysRun = true)
	public void checkStatusAndRemoteIcon() {

		action.signOut();
		action.signIn("cadmin1@abb.com", "Testuser1");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		// table.filter(SupplierList.SUPPLIER_EMAIL_FILTER_XPATH,
		// supplierEmail);
		i = table.findRowByString(6, supplierEmail);
		WebElement supplierIDCell = table.getCellObject(i, 1);
		int indexOfSupplier = table.findRealIndexByCell(supplierIDCell, "spIdBtn");
		Assert.assertTrue(i >= 0, String.format("Supplier %s not found!", supplierEmail));
		assertEquals(table.getValueRow(4, i), "Active");
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn" + indexOfSupplier)), false);
	
	}


}

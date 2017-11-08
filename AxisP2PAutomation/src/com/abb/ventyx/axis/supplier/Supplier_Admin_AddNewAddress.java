package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.MaintainSuppliers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "604")
@Credentials(user = "supplier_user_2@abb.com", password = "Testuser1")
public class Supplier_Admin_AddNewAddress extends BaseTestCase {

	String supplierEmail = "supplier_user_test@abb.com";
	String supplierMultiEmail = "supplier_user_test1@abb.com,supplier_user_test2@abb.com,supplier_user_test3@abb.com";
	String supplierInvalidEmail = "invalid";
	String companyRegistrationNo = "$%#%%%(&";
	String taxRegistrationNo = "$%#%%%(&";
	String supplierNameValid = "Supplier User Test";
	String companyRegistrationNoValid = "123456789";
	String taxRegistrationNoValid = "123456";
	String userLogin = "customer_user_1@abb.com";
	String passwordLogin = "Testuser1";
	String supplierNameInpopup = "Supplier Name Test";
	String addressPopup = "Address 1";
	ScreenAction action;
	TableFunction table;
	int i, j;
	int milliseconds = 800;
	String city = "KL central";
	String state = "Sanlagor";
	String zipCode = "23656844";

	@Test
	public void openScreen() {
		action = new ScreenAction(driver);
		// Step 1:
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.SUPPLIER_NAME));
		action.assertTitleScreen(AddressContact.TITLE_ADDRESS_CONTACT);

	}

	// Step 2
	@Test(dependsOnMethods = "openScreen")
	public void updateSupplierEmail() {
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, supplierEmail);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 4
	@Test(dependsOnMethods = "updateSupplierEmail")
	public void invalidEmail() {
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, supplierInvalidEmail);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALIED_EMAIL_MESSAGE);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 3
	@Test(dependsOnMethods = "invalidEmail")
	public void updateMulSupplierEmail() {
		// action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, supplierMultiEmail);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 5,6
	@Test(dependsOnMethods = "updateMulSupplierEmail")
	public void checkSpecialCharacter() {
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, companyRegistrationNo);
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, companyRegistrationNo);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.NO_SPECIAL_CHARACTER_ALLOWED);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.inputTextField(AddressContact.TAX_REGISTRATION_NO, taxRegistrationNo);
		action.inputTextField(AddressContact.TAX_REGISTRATION_NO, taxRegistrationNo);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.NO_SPECIAL_CHARACTER_ALLOWED);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.pause(milliseconds);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputTextField(AddressContact.COMPANY_NAME, "");
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, "");
		action.inputTextField(AddressContact.TAX_REGISTRATION_NO, "");
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, "");
		action.pause(milliseconds);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 7
	@Test(dependsOnMethods = "checkSpecialCharacter")
	public void saveSuccessfully() {
		action.inputTextField(AddressContact.COMPANY_NAME, supplierNameValid);
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, companyRegistrationNoValid);
		action.inputTextField(AddressContact.TAX_REGISTRATION_NO, taxRegistrationNoValid);
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, supplierEmail);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 8
	@Test(dependsOnMethods = "saveSuccessfully")
	public void checkSupplierList() {
		action.signOut();
		action.signIn(userLogin, passwordLogin);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIER_LIST));
		action.waitObjVisible(By.id("spIdBtn0"));
		action.clickBtn(By.id("spIdBtn0"));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME_INPOPUP));
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME_INPOPUP, supplierNameInpopup);
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME_INPOPUP, supplierNameInpopup);
		action.clickBtn(By.id(MaintainSuppliers.SAVE_EDIT));
		action.checkAddSuccess(Messages.SUPPLIER_UPDATED_SUCCESSFULLY);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 9
	@Test(dependsOnMethods = "checkSupplierList")
	public void updateAddress() {
		action.clickBtn(By.cssSelector(CustomerMenu.ADDRESS_CONTACT_MENU));
		action.waitObjVisible(By.id("addrId0"));
		table = new TableFunction(driver);
		i = table.findRowByString(2, "Default Address");
		j = i - 1;
		WebElement elemement = driver.findElement(By.id("addrId" + j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, addressPopup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, addressPopup);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_SUCCESSFULLY_UPDATED);
	}

	// Step 10
	@Test(dependsOnMethods = "updateAddress")
	public void addNewDefaultAddressExist() {
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, addressPopup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP_2, addressPopup);
		action.inputTextField(MaintainSuppliers.CITY_POPUP, city);
		action.inputTextField(MaintainSuppliers.STATE_POPUP, state);
		action.inputTextField(MaintainSuppliers.ZIPCODE, zipCode);
		action.pause(milliseconds);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.DEFAULT_ADDRESS_EXIST);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.YES_BTN_BACKUP));

	}

	// step 12
	@Test(dependsOnMethods = "addNewDefaultAddressExist")
	public void addAddressSuccessfully() {
		action.waitObjVisible(By.id("addrId0"));
		table = new TableFunction(driver);
		i = table.findRowByString(2, "Invoice");
		j = i - 1;
		WebElement elemement = driver.findElement(By.id("addrId" + j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, addressPopup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, addressPopup);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_SUCCESSFULLY_UPDATED);
	}

	// Step 13
	@Test(dependsOnMethods = "addAddressSuccessfully")
	public void noDialogConfirm() {
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
	}
}

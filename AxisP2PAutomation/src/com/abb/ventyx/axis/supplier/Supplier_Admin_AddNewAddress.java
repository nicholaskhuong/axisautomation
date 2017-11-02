package com.abb.ventyx.axis.supplier;

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
@Credentials(user = "donna900@abb.com", password = "Testuser2")
public class Supplier_Admin_AddNewAddress extends BaseTestCase {

	String supplierEmail = "duc800@abb.com";
	String supplierMultiEmail = "duc800@abb.com,donna@abb.com,hieunguyen@abb.com";
	String supplierInvalidEmail = "invalid";
	String companyRegistrationNo = "$%#%%%(&";
	String taxRegistrationNo = "$%#%%%(&";
	String supplierNameValid = "Donna Nguyen Thi";
	String companyRegistrationNoValid = "123456789";
	String taxRegistrationNoValid = "123456";
	String userLogin = "mail222@abb.com";
	String passwordLogin = "Testuser2";
	String supplierNameInpopup = "Donna NGUYEN Thi DONNA";
	String addressPopup = "Address 1";
	ScreenAction action;
	TableFunction table;
	int i;
	int j;
	int milliseconds = 800;

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
	public void updateSupplierEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, supplierEmail);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 4
	@Test(dependsOnMethods = "updateSupplierEmail")
	public void invalidEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, supplierInvalidEmail);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALIED_EMAIL_MESSAGE);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 3
	@Test(dependsOnMethods = "invalidEmail")
	public void updateMulSupplierEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, supplierMultiEmail);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 5,6
	@Test(dependsOnMethods = "updateMulSupplierEmail")
	public void checkSpecialCharacter() throws InterruptedException {
		action = new ScreenAction(driver);
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, companyRegistrationNo);
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, companyRegistrationNo);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));

		// action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.NO_SPECIAL_CHARACTER_ALLOWED);
		((JavascriptExecutor) driver).executeScript("window.focus();");

		// action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
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
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.EMPTY_DATE, Messages.ENTER_MANDATORY_FIELDS);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 7
	@Test(dependsOnMethods = "checkSpecialCharacter")
	public void saveSuccessfully() throws InterruptedException {
		action = new ScreenAction(driver);
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
	public void checkSupplierList() throws InterruptedException {
		action.signOut();
		action.signIn(userLogin, passwordLogin);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIER_LIST));
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.SELECT_RECORD));
		action.clickBtn(By.cssSelector(MaintainSuppliers.SELECT_RECORD));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME_INPOPUP));
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME_INPOPUP, supplierNameInpopup);
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME_INPOPUP, supplierNameInpopup);
		action.clickBtn(By.id(MaintainSuppliers.SAVE_EDIT));
		action.checkAddSuccess(Messages.SUPPLIER_UPDATED_SUCCESSFULLY);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	// Step 8
	@Test(dependsOnMethods = "checkSupplierList")
	public void updateAddress() throws InterruptedException {
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
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		// action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
	}
}

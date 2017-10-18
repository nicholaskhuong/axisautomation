package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
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

	String Supplier_Email = "duc800@abb.com";
	String Supplier_Multi_Email = "duc800@abb.com,donna@abb.com,hieunguyen@abb.com";
	String Supplier_Invalid_Email = "invalid";
	String Company_Registration_No = "$%#%%%(&";
	String Tax_Registration_No = "$%#%%%(&";
	String supplier_name_Valid = "Donna Nguyen Thi";
	String Company_Registration_No_Valid = "123456789";
	String Tax_Registration_No_Valid = "123456";
	String User_Login = "mail222@abb.com";
	String Password_Login = "Testuser2";
	String Supplier_Name_Inpopup = "Donna NGUYEN";
	String Address_Popup = "Address 1";
	ScreenAction action;
	TableFunction table;
	public static int i;
	public static int j;


	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		// Step 1:
		// click on Administration button
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		// Click on Address & Contact button
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.SUPPLIER_NAME));
		action.assertTitleScreen(AddressContact.TITLE_ADDRESS_CONTACT);

	}
	// Step 2
	@Test(dependsOnMethods = "openScreen")
	public void updateSupplierEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		// input supplier ID
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		// update the email address
		WebElement supplier_name = driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_name.clear();
		Thread.sleep(300);
		supplier_name.sendKeys(Supplier_Email);
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
	}
	// Step 4
	@Test(dependsOnMethods = "updateSupplierEmail")
	public void invalidEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		// input supplier ID
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		// update the email address
		WebElement supplier_name = driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_name.clear();
		Thread.sleep(300);
		supplier_name.sendKeys(Supplier_Invalid_Email);
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.INVALIED_EMAIL_MESSAGE);
	}
	// Step 3
	@Test(dependsOnMethods = "invalidEmail")
	public void updateMulSupplierEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		// input supplier ID
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		// update the email address
		WebElement supplier_name = driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_name.clear();
		Thread.sleep(300);
		supplier_name.sendKeys(Supplier_Multi_Email);
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
	}
	// Step 5,6
	@Test(dependsOnMethods = "updateMulSupplierEmail")
	public void checkSpecialCharacter() throws InterruptedException {
		action = new ScreenAction(driver);
		// update the COMPANY_REGISTRATION_NO 
		WebElement comapny_registration_no = driver.findElement(By.id(AddressContact.COMPANY_REGISTRATION_NO));
		comapny_registration_no.clear();
		Thread.sleep(300);
		comapny_registration_no.sendKeys(Company_Registration_No);
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.NO_SPECIAL_CHARACTER_ALLOWED);

		WebElement tax_registration_no =  driver.findElement(By.id(AddressContact.TAX_REGISTRATION_NO));
		tax_registration_no.clear();
		Thread.sleep(300);
		tax_registration_no.sendKeys(Tax_Registration_No);
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.NO_SPECIAL_CHARACTER_ALLOWED);	
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		WebElement supplier_name= driver.findElement(By.id(AddressContact.COMPANY_NAME));
		supplier_name.clear();
		Thread.sleep(300);
		supplier_name.sendKeys("");
		Thread.sleep(300);
		comapny_registration_no.clear();
		Thread.sleep(300);
		comapny_registration_no.sendKeys("");
		Thread.sleep(300);
		tax_registration_no.clear();
		Thread.sleep(300);
		tax_registration_no.sendKeys("");
		WebElement supplier_email= driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_email.clear();
		Thread.sleep(300);
		supplier_email.sendKeys("");
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.EMPTY_DATE,
				Messages.NO_INPUT_DATA);
	}
	// Step 7
	@Test(dependsOnMethods = "checkSpecialCharacter")
	public void saveSuccessfully() throws InterruptedException {
		action = new ScreenAction(driver);
		WebElement supplier_name= driver.findElement(By.id(AddressContact.COMPANY_NAME));
		supplier_name.clear();
		Thread.sleep(300);
		supplier_name.sendKeys(supplier_name_Valid);
		Thread.sleep(300);
		WebElement comapny_registration_no = driver.findElement(By.id(AddressContact.COMPANY_REGISTRATION_NO));
		comapny_registration_no.clear();
		Thread.sleep(300);
		comapny_registration_no.sendKeys(Company_Registration_No_Valid);
		Thread.sleep(300);
		WebElement tax_registration_no =  driver.findElement(By.id(AddressContact.TAX_REGISTRATION_NO));
		tax_registration_no.clear();
		Thread.sleep(300);
		tax_registration_no.sendKeys(Tax_Registration_No_Valid);
		Thread.sleep(300);
		WebElement supplier_email= driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_email.clear();
		Thread.sleep(300);
		supplier_email.sendKeys(Supplier_Email);
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);

	}

	// Step 8
	@Test(dependsOnMethods = "saveSuccessfully")
	public void checkSupplierList() throws InterruptedException {
		// Logout and login with Customer admin
		action.waitObjVisibleAndClick(By
				.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.clickBtn(By.id(ScreenObjects.PROFILE_PANEL));
		action.waitObjVisible(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.clickBtn(By.id(ScreenObjects.SIGNOUT_BUTTON));
		// Login with Customer admin
		action.waitObjInvisible(By.id(ScreenObjects.LOGIN_BUTTON));
		Thread.sleep(900);
		action.inputTextField(ScreenObjects.USER_LOGIN, User_Login);
		
		action.inputTextField(ScreenObjects.PASSWORD_LOGIN, Password_Login);
		action.clickBtn(By.id(ScreenObjects.LOGIN_BUTTON));
		// Click on Customer Maintenance ,Supplier list
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIER_LIST));
		// Select a record with multiple email and update them
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.SELECT_RECORD));
		action.clickBtn(By.cssSelector(MaintainSuppliers.SELECT_RECORD));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME_INPOPUP));
		WebElement supplier_name_popup= driver.findElement(By.id(MaintainSuppliers.SUPPLIER_NAME_INPOPUP));
		supplier_name_popup.clear();
		Thread.sleep(300);
		supplier_name_popup.sendKeys(Supplier_Name_Inpopup);
		Thread.sleep(300);
		action.clickBtn(By.id(MaintainSuppliers.SAVE_EDIT));
		action.checkAddSuccess(Messages.SUPPLIER_UPDATED_SUCCESSFULLY);
	}

	// Step 8
	@Test(dependsOnMethods = "checkSupplierList")
	public void updateAddress() throws InterruptedException {
		// Click on Address&Contact menu
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.ADDRESS_CONTACT_MENU));
		action.waitObjVisible(By.id("addrId0"));
		//Click a record with Address type = Default Address
		table = new TableFunction(driver);
		i = table.findRowByString1(2, "Default Address");
		j=i-1;
		WebElement elemement= driver.findElement(By.id("addrId"+j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		WebElement address_popup= driver.findElement(By.id(MaintainSuppliers.ADDRESS_POPUP));
		address_popup.clear();
		Thread.sleep(300);
		address_popup.sendKeys(Address_Popup);
		Thread.sleep(300);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_SUCCESSFULLY_UPDATED);
		// Click x icon at the top right of popup
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
	}
}

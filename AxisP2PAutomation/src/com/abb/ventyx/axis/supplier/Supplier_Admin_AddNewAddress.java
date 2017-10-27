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
	String Supplier_Name_Inpopup = "Donna NGUYEN Thi DONNA";
	String Address_Popup = "Address 1";
	ScreenAction action;
	TableFunction table;
	int i;
	int j;


	@Test
	public void openScreen() {
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
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, Supplier_Email);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		//action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		//action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
	}
	// Step 4
	@Test(dependsOnMethods = "updateSupplierEmail")
	public void invalidEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		// input supplier ID
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		// update the email address
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, Supplier_Invalid_Email);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		//action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				//Messages.INVALIED_EMAIL_MESSAGE);
	}
	// Step 3
	@Test(dependsOnMethods = "invalidEmail")
	public void updateMulSupplierEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		// input supplier ID
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		// update the email address
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, Supplier_Multi_Email);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		//action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
	}
	// Step 5,6
	@Test(dependsOnMethods = "updateMulSupplierEmail")
	public void checkSpecialCharacter() throws InterruptedException {
		action = new ScreenAction(driver);
		// update the COMPANY_REGISTRATION_NO 
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, Company_Registration_No);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
/*		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.NO_SPECIAL_CHARACTER_ALLOWED);*/
		//action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputTextField(AddressContact.TAX_REGISTRATION_NO, Tax_Registration_No);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		//action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
			//	Messages.NO_SPECIAL_CHARACTER_ALLOWED);	
		//action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputTextField(AddressContact.COMPANY_NAME, "");
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, "");
		action.inputTextField(AddressContact.TAX_REGISTRATION_NO, "");

		action.inputTextField(AddressContact.SUPPLIER_EMAIL, "");
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		//action.assertMessgeError(ScreenObjects.EMPTY_DATE,
		//		Messages.ENTER_MANDATORY_FIELDS);
	}
	// Step 7
	@Test(dependsOnMethods = "checkSpecialCharacter")
	public void saveSuccessfully() throws InterruptedException {
		action = new ScreenAction(driver);
		action.inputTextField(AddressContact.COMPANY_NAME, supplier_name_Valid);
		action.inputTextField(AddressContact.COMPANY_REGISTRATION_NO, Company_Registration_No_Valid);
		action.inputTextField(AddressContact.TAX_REGISTRATION_NO, Tax_Registration_No_Valid);
		action.inputTextField(AddressContact.SUPPLIER_EMAIL, Supplier_Email);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
	}

	// Step 8
	@Test(dependsOnMethods = "saveSuccessfully")
	public void checkSupplierList() throws InterruptedException {
		action.signOut();
		action.signIn(User_Login, Password_Login);
		// Click on Customer Maintenance ,Supplier list
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIER_LIST));
		// Select a record with multiple email and update them
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.SELECT_RECORD));
		action.clickBtn(By.cssSelector(MaintainSuppliers.SELECT_RECORD));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME_INPOPUP));	
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME_INPOPUP, Supplier_Name_Inpopup);
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME_INPOPUP, Supplier_Name_Inpopup);
		action.clickBtn(By.id(MaintainSuppliers.SAVE_EDIT));
		action.checkAddSuccess(Messages.SUPPLIER_UPDATED_SUCCESSFULLY);
	}

	// Step 8
	@Test(dependsOnMethods = "checkSupplierList")
	public void updateAddress() throws InterruptedException {
		// Click on Address&Contact menu
		action.clickBtn(By.cssSelector(CustomerMenu.ADDRESS_CONTACT_MENU));
		action.waitObjVisible(By.id("addrId0"));
		//Click a record with Address type = Default Address
		table = new TableFunction(driver);
		i = table.findRowByString(2, "Default Address");
		j=i-1;
		WebElement elemement= driver.findElement(By.id("addrId"+j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, Address_Popup);
		//action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, Address_Popup);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		
		action.checkAddSuccess(Messages.ADDRESS_SUCCESSFULLY_UPDATED);
		// Click x icon at the top right of popup
		//action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		//action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
	}
}

package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.MaintainSuppliers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "638")
@Credentials(user = "donna201@abb.com", password = "Testuser1")
public class Supplier_Admin_AddContact extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String supplierName = "Donna 1";
	String expectedAddNewContact = "Add New Contact";
	String contactId = "Donna 123";
	String contactName = "Donna Nguyen";
	String invalidEmail = "Donna";
	String validEmail = "Donna@enclave.vn";
	String roleField = "Manager";

	String phoneFiled = "0973600146";
	String extensionField = "Extension";
	String faxNumber = "+84973600146";
	String mobileNumber = "0905842718";
	String titleHeader = "Maintain Address & Contact";
	int milliseconds = 800;

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
	}

	@Test(dependsOnMethods = "openScreen")
	public void addNewContact() throws InterruptedException {
		// step 2
		action.clickBtn(By.cssSelector(MaintainSuppliers.CONTACT_TAB));
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME, "");
		action.pause(milliseconds);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Step 3
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME, supplierName);
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
	}

	@Test(dependsOnMethods = "addNewContact")
	public void verifyPopup() throws InterruptedException {
		// Step 4,5: Input lack mandatory fields and try cto click OK
		action.inputTextField(MaintainSuppliers.CONTACT_ID_FILED, contactId);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contactName);
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, invalidEmail);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		/*
		 * assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(
		 * ), Messages.INVALID_EMAIL_2);
		 */
	}

	@Test(dependsOnMethods = "verifyPopup")
	public void addContactSuccessfully() throws InterruptedException {
		// Step 6
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, validEmail);
		action.inputTextField(MaintainSuppliers.CONTACT_ROLE_FILED, roleField);
		action.inputTextField(MaintainSuppliers.CONTACT_PHONE_FILED, phoneFiled);
		action.inputTextField(MaintainSuppliers.CONTACT_EXTENSION_FILED, extensionField);
		action.inputTextField(MaintainSuppliers.CONTACT_FAX_FILED, faxNumber);
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobileNumber);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		// Step 7
		action.pause(milliseconds);
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.id(ScreenObjects.CANCEL_ID));
		action.pause(milliseconds);
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		// verify back to Maintain Address & Contact
		// assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_ID)).getText(),
		// title_header);
	}

	@Test(dependsOnMethods = "addContactSuccessfully")
	public void addNoSaving() throws InterruptedException {
		// Step 8,9 add but dont save
		action.pause(milliseconds);
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.id(MaintainSuppliers.CONTACT_ID_FILED));
		action.inputTextField(MaintainSuppliers.CONTACT_ID_FILED, contactId);
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contactName);
		action.inputTextField(MaintainSuppliers.CONTACT_ROLE_FILED, roleField);
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, validEmail);
		action.inputTextField(MaintainSuppliers.CONTACT_PHONE_FILED, phoneFiled);
		action.inputTextField(MaintainSuppliers.CONTACT_EXTENSION_FILED, extensionField);
		action.inputTextField(MaintainSuppliers.CONTACT_FAX_FILED, faxNumber);
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobileNumber);
		action.waitObjVisible(By.id(ScreenObjects.CANCEL_ID));
		/*
		 * action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		 * assertEquals(driver.findElement(By.cssSelector(ScreenObjects.
		 * UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		 * driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		 */

	}

}

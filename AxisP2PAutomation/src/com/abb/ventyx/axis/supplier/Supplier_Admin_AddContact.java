package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
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
	String supplierName= "Donna 1";
	String expected_AddNewContact = "Add New Contact";
	String contact_Id= "Donna 123";
	String contact_name = "Donna Nguyen";
	String invalid_Email = "Donna";
	String valid_Email = "Donna@enclave.vn";
	String role_field = "Manager";
	String phone_filed = "0973600146";
	String extension_field = "Extension";
	String fax_number = "+84973600146";
	String mobile_number = "0905842718";
	String title_header = "Maintain Address & Contact";
	@Test
	public void openScreen() throws InterruptedException {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
	}
	@Test(dependsOnMethods = "openScreen")
	public void addNewContact() throws InterruptedException {
		// step 2
		// click on Contact tab
		action.clickBtn(By.id(MaintainSuppliers.CONTACT_TAB));
		// Delete Supplier Name
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME, "");
		// try to save
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		// Verify message display
		action.waitObjVisible(By.cssSelector(CustomerUsers.ERROR));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.ENTER_MANDATORY_FIELDS);
		// Step 3
		// Input Supplier again
		action.inputTextField(MaintainSuppliers.SUPPLIER_NAME, supplierName);
		// Click on Add button
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
	}
	@Test(dependsOnMethods = "addNewContact")
	public void verifyPopup() throws InterruptedException {
		//Step 4,5: Input lack mandatory fields and try cto click OK 
		// Input Only Contact ID
		action.waitObjVisible(By.id(MaintainSuppliers.CONTACT_ID_FILED));
		action.inputTextField(MaintainSuppliers.CONTACT_ID_FILED, contact_Id);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ERROR));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(CustomerUsers.ERROR));
		// Input Name
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contact_name);
		// Input Invalid email
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, invalid_Email);
		// Click on OK button
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		// Verify that message display
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ERROR));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.INVALID_EMAIL_2);
	}
	@Test(dependsOnMethods = "verifyPopup")
	public void addContactSuccessfully() throws InterruptedException {
		// Step 6
		action.waitObjInvisible(By.cssSelector(CustomerUsers.ERROR));
		// Input Email valid
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, valid_Email);
		// Input Role
		action.inputTextField(MaintainSuppliers.CONTACT_ROLE_FILED, role_field);
		// Input Phone Number
		action.inputTextField(MaintainSuppliers.CONTACT_PHONE_FILED, phone_filed);
		// Input Extension
		action.inputTextField(MaintainSuppliers.CONTACT_EXTENSION_FILED, extension_field);
		// Input Fax Number
		action.inputTextField(MaintainSuppliers.CONTACT_FAX_FILED, fax_number);
		// Input Mobile Number
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobile_number);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		// Step 7
		// Click Add button, don't change anything and click Cancel at the popup.
		Thread.sleep(900);
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.id(ScreenObjects.CANCEL_ID));
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		// verify back to Maintain Address & Contact
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_ID)).getText(), title_header);
	}
	@Test(dependsOnMethods = "addContactSuccessfully")
	public void addNoSaving() throws InterruptedException {
		// Step 8,9 add but dont save
		Thread.sleep(900);
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.id(MaintainSuppliers.CONTACT_ID_FILED));
		action.inputTextField(MaintainSuppliers.CONTACT_ID_FILED, contact_Id);
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contact_name);
		action.inputTextField(MaintainSuppliers.CONTACT_ROLE_FILED, role_field);
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, valid_Email);
		action.inputTextField(MaintainSuppliers.CONTACT_PHONE_FILED, phone_filed);
		action.inputTextField(MaintainSuppliers.CONTACT_EXTENSION_FILED, extension_field);
		action.inputTextField(MaintainSuppliers.CONTACT_FAX_FILED, fax_number);
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobile_number);
		action.waitObjVisible(By.id(ScreenObjects.CANCEL_ID));
		Thread.sleep(900);
		/*action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();*/

	}
		
}

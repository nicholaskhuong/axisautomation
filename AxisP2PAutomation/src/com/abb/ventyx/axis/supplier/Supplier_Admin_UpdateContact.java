package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

@ALM(id = "642")
@Credentials(user = "donna601@abb.com", password = "Testuser1")
public class Supplier_Admin_UpdateContact extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String title_header = "Update Contact";
	String contact_Id = "Donna 123";
	String contact_name = "Donna Nguyen";
	String invalid_Email = "Donna";
	String valid_Email = "Donna@enclave.vn";
	String role_field = "Manager";
	String phone_filed = "0973600146";
	String extension_field = "Extension";
	String fax_number = "+84973600146";
	String mobile_number = "0905842718";
	String title_header_Maintain = "Maintain Address & Contact";
	int milliseconds = 800;

	@Test
	public void openScreen() throws InterruptedException {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		Random rand = new Random();
		long drand = (long)(rand.nextDouble()*10000000000L);
		contact_Id = "ID" + drand;
		contact_name = "Name" + drand;
		valid_Email =drand + "@enclave.vn";
		phone_filed = String.valueOf(drand);
		drand = (long) (rand.nextDouble() * 10000000000L);
		fax_number =String.valueOf(drand);
		drand = (long) (rand.nextDouble() * 10000000000L);
		mobile_number = String.valueOf(drand);
		
	}

	@Test(dependsOnMethods = "openScreen")
	public void addNewContact() throws InterruptedException {
		// step 2
		// click on Contact tab
		action.clickBtn(By.cssSelector(MaintainSuppliers.CONTACT_TAB));
		// Click on a row in summary
		// Thread.sleep(900);
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		WebElement btn = (new WebDriverWait(driver, 30)).until(ExpectedConditions
.presenceOfElementLocated(By.id(MaintainSuppliers.SELECT_ROW)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.TITLE_POPUP)).getText(), title_header);
		
		// Step 3
		// Input lack data mandatory in the form, clear data of Contact ID
		driver.findElement(By.id(MaintainSuppliers.CONTACT_ID_FILED)).clear();
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// Step 4
		// Fill all data in the Update Contact popup
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.waitObjVisible(By.id(MaintainSuppliers.CONTACT_ID_FILED));
		action.inputTextField(MaintainSuppliers.CONTACT_ID_FILED, contact_Id);
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contact_name);
		action.inputTextField(MaintainSuppliers.CONTACT_ROLE_FILED, role_field);
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, valid_Email);
		action.inputTextField(MaintainSuppliers.CONTACT_PHONE_FILED, phone_filed);
		action.inputTextField(MaintainSuppliers.CONTACT_EXTENSION_FILED, extension_field);
		action.inputTextField(MaintainSuppliers.CONTACT_FAX_FILED, fax_number);
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobile_number);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(),
				title_header_Maintain);

	}

	@Test(dependsOnMethods = "addNewContact")
	public void verifyMessage() throws InterruptedException {
		// step 5
		// Click 1 row, dont change anything
		action.waitObjVisible(By.id(MaintainSuppliers.SELECT_ROW));
		action.pause(milliseconds);
		WebElement btn = driver.findElement(By.id(MaintainSuppliers.SELECT_ROW));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		// Click on cancel button
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		// verify back to Maintain Address & Contact
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(),
				title_header_Maintain);
		// step 6
		// Click a row, change any data and click on cancel
		action.waitObjVisible(By.id(MaintainSuppliers.SELECT_ROW));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		// Change any data
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contact_name + "New");
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobile_number + "1");
		// Click on cancel
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		// Verify the message
		action.assertMessgeError(ScreenObjects.UNSAVED_CHANGE_CSS, Messages.UNSAVED_CHANGE);

	}

}

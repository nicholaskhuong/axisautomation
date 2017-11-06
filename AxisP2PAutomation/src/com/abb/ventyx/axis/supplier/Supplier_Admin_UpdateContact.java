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
@Credentials(user = "supplier_user_5@abb.com", password = "Testuser1")
public class Supplier_Admin_UpdateContact extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String titleHeader = "Update Contact";
	String contactId = "Donna 123";
	String contactName = "Donna Nguyen";
	String invalidEmail = "Donna";
	String validEmail = "Donna@enclave.vn";
	String roleField = "Manager";
	String phoneFiled = "0973600146";
	String extensionField = "Extension";
	String faxNumber = "+84973600146";
	String mobileNumber = "0905842718";
	String titleHeaderMaintain = "Maintain Address & Contact";
	int milliseconds = 800;

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000000000L);
		contactId = "ID" + drand;
		contactName = "Name" + drand;
		validEmail = drand + "@enclave.vn";
		phoneFiled = String.valueOf(drand);
		drand = (long) (rand.nextDouble() * 10000000000L);
		faxNumber = String.valueOf(drand);
		drand = (long) (rand.nextDouble() * 10000000000L);
		mobileNumber = String.valueOf(drand);

	}

	@Test(dependsOnMethods = "openScreen")
	public void addNewContact() throws InterruptedException {
		// step 2
		action.clickBtn(By.cssSelector(MaintainSuppliers.CONTACT_TAB));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		WebElement btn = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id(MaintainSuppliers.SELECT_ROW)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.TITLE_POPUP)).getText(), titleHeader);
		// Step 3
		driver.findElement(By.id(MaintainSuppliers.CONTACT_ID_FILED)).clear();
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		// Step 4
		action.inputTextField(MaintainSuppliers.CONTACT_ID_FILED, contactId);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.waitObjVisible(By.id(MaintainSuppliers.CONTACT_ID_FILED));
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contactName);
		action.inputTextField(MaintainSuppliers.CONTACT_ROLE_FILED, roleField);
		action.inputTextField(MaintainSuppliers.CONTACT_EMAIl_FILED, validEmail);
		action.inputTextField(MaintainSuppliers.CONTACT_PHONE_FILED, phoneFiled);
		action.inputTextField(MaintainSuppliers.CONTACT_EXTENSION_FILED, extensionField);
		action.inputTextField(MaintainSuppliers.CONTACT_FAX_FILED, faxNumber);
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobileNumber);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), titleHeaderMaintain);

	}

	@Test(dependsOnMethods = "addNewContact")
	public void verifyMessage() throws InterruptedException {
		// step 5
		action.waitObjVisible(By.id(MaintainSuppliers.SELECT_ROW));
		action.pause(milliseconds);
		WebElement btn = driver.findElement(By.id(MaintainSuppliers.SELECT_ROW));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), titleHeaderMaintain);
		// step 6
		action.waitObjVisible(By.id(MaintainSuppliers.SELECT_ROW));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.TITLE_POPUP));
		action.pause(milliseconds);
		action.inputTextField(MaintainSuppliers.CONTACT_NAME_FILED, contactName + "New");
		action.inputTextField(MaintainSuppliers.CONTACT_MOBILE_FILED, mobileNumber + "1");
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.assertMessgeError(ScreenObjects.UNSAVED_CHANGE_CSS, Messages.UNSAVED_CHANGE);

	}

}

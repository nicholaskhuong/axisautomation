package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

@ALM(id = "617")
@Credentials(user = "supplier_user_5@abb.com", password = "Testuser1")
public class Supplier_Admin_UpdateNewAddress extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String address_Popup = "Address 10";
	String address_Popup_2 = "Address 20";
	String city_Popup = "American";
	String state_Popup = "Los Angeles";
	String zip_Code = "123456788";
	String address_Type = "Invoice";
	String country = "ARGENTINAsd";
	int milliseconds = 800;
	WebDriverWait wait;
	String expected = "Maintain Address & Contact";
	int i, j;

	@Test
	public void openScreen() {
		// step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
	}

	@Test(dependsOnMethods = "openScreen")
	public void selectRecordDefault() {
		// step 2
		action.waitObjVisible(By.id("addrId0"));
		table = new TableFunction(driver);
		i = table.findRowByString(2, "Default Address");
		j = i - 1;
		WebElement elemement = driver.findElement(By.id("addrId" + j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
	}

	@Test(dependsOnMethods = "selectRecordDefault")
	public void updateLackdataPopup() {
		// Step 3
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, "");
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP_2, "");
		action.inputTextField(MaintainSuppliers.CITY_POPUP, "");
		action.inputTextField(MaintainSuppliers.STATE_POPUP, "");
		action.inputTextField(MaintainSuppliers.ZIPCODE, "");
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, address_Popup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP_2, address_Popup_2);
		action.inputTextField(MaintainSuppliers.CITY_POPUP, city_Popup);
		action.inputTextField(MaintainSuppliers.STATE_POPUP, state_Popup);
		action.inputTextField(MaintainSuppliers.ZIPCODE, zip_Code);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
	}

	@Test(dependsOnMethods = "updateLackdataPopup")
	public void createDefaultExist() {
		// Step 4
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, address_Popup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP_2, address_Popup_2);
		action.inputTextField(MaintainSuppliers.CITY_POPUP, city_Popup);
		action.inputTextField(MaintainSuppliers.STATE_POPUP, state_Popup);
		action.inputTextField(MaintainSuppliers.ZIPCODE, zip_Code);
		action.inputTextField(MaintainSuppliers.ZIPCODE, zip_Code);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.DEFAULT_ADDRESS_EXIST);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		// click cancel to exist popup
		action.clickBtn(By.cssSelector(MaintainSuppliers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.YES_BTN_BACKUP));
	}

	@Test(dependsOnMethods = "createDefaultExist")
	public void updateAddressWithoutDefaultAddress() {
		// Step 5
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.waitObjVisible(By.id("addrId0"));
		table = new TableFunction(driver);
		i = table.findRowByString(2, "Invoice");
		j = i - 1;
		WebElement elemement = driver.findElement(By.id("addrId" + j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, address_Popup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP_2, address_Popup_2);
		action.inputTextField(MaintainSuppliers.CITY_POPUP, city_Popup);
		action.inputTextField(MaintainSuppliers.STATE_POPUP, state_Popup);
		action.inputTextField(MaintainSuppliers.ZIPCODE, zip_Code);
		action.inputTextField(MaintainSuppliers.ZIPCODE, zip_Code);
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);
	}

	@Test(dependsOnMethods = "updateAddressWithoutDefaultAddress")
	public void selectRowDontChangeAnythingClose() {
		// step 6
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.waitObjVisible(By.id("addrId0"));
		table = new TableFunction(driver);
		i = table.findRowByString(2, "Order");
		j = i - 1;
		WebElement elemement = driver.findElement(By.id("addrId" + j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		action.clickBtn(By.cssSelector(MaintainSuppliers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);
	}

	@Test(dependsOnMethods = "selectRowDontChangeAnythingClose")
	public void selectRowChangeAnythingClose() {
		// step 7
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.waitObjVisible(By.id("addrId0"));
		table = new TableFunction(driver);
		i = table.findRowByString(2, "Shipping");
		j = i - 1;
		WebElement elemement = driver.findElement(By.id("addrId" + j));
		elemement.click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, address_Popup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP_2, address_Popup_2);
		action.clickBtn(By.cssSelector(MaintainSuppliers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		// step 8
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.DELETE_NO));
		action.pause(milliseconds);
		// step 9
		action.clickBtn(By.cssSelector(MaintainSuppliers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		// Step 10
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.DELETE_YES));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);

	}

}

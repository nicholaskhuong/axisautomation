package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
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
@Credentials(user = "donna700@abb.com", password = "Testuser1")
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

	@Test
	public void openScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
	}

	@Test(dependsOnMethods = "openScreen")
	public void checkAndDeletExistingAddress() throws InterruptedException {
		List<WebElement> listCheckbox = driver.findElements(By.xpath("//input[@type='checkbox']"));
		if (listCheckbox.size() > 0) {
			if (listCheckbox.get(1).isDisplayed()) {
				action.clickCheckBoxN(1);
				action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));
				action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
				assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.DELETE_ADDRESS_MESSAGE);
				driver.findElement(By.cssSelector(MaintainSuppliers.YES_BTN)).click();
				action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
				assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);
			}
		}
	}

	@Test(dependsOnMethods = "checkAndDeletExistingAddress")
	public void addNewAddress() {
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		try {
			commonMethod();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.pause(milliseconds);
		action.waitObjVisible(By.id(ScreenObjects.YES_BTN_ID));
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);

	}

	@Test(dependsOnMethods = "addNewAddress")
	public void verifyElement() {
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		try {
			commonMethod();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		action.clickBtn(By.cssSelector(MaintainSuppliers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();

	}

	@Test(dependsOnMethods = "verifyElement")
	public void selectAddressType() throws InterruptedException {
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		action.selectStatus(MaintainSuppliers.ADDRESS_TYPE, address_Type);
	}

	public void commonMethod() throws InterruptedException {
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP, address_Popup);
		action.inputTextField(MaintainSuppliers.ADDRESS_POPUP_2, address_Popup_2);
		action.inputTextField(MaintainSuppliers.CITY_POPUP, city_Popup);
		action.inputTextField(MaintainSuppliers.STATE_POPUP, state_Popup);
		action.inputTextField(MaintainSuppliers.ZIPCODE, zip_Code);

	}

}

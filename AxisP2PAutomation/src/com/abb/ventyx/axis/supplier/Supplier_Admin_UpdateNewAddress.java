package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.MaintainSuppliers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
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
	String address_Popup = "Address 1";
	String address_Popup_2 = "Address 2";
	String city_Popup = "American";
	String state_Popup = "Los Angeles";
	String zip_Code = "123456789";
	String address_Type = "Invoice";
	String country = "ARGENTINAsd";
	WebDriverWait wait;
	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
	}
	@Test(dependsOnMethods = "openScreen")
	public void addNewAddress() throws InterruptedException {
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		commonMethod();
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		Thread.sleep(200);
		// try to Saving 
		//action.waitObjVisible(By.id(ScreenObjects.YES_BTN_ID));
		//action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		//action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);

	}

	@Test(dependsOnMethods = "addNewAddress")
	public void verifyElement() throws InterruptedException {
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		action.clickBtn(By.cssSelector(MaintainSuppliers.OK_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		commonMethod();
		// Do not save. Try click on Cancel button
		action.clickBtn(By.cssSelector(MaintainSuppliers.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		//action.clickBtn(By.cssSelector(MaintainSuppliers.CANCEL_BUTTON));

	}

	@Test(dependsOnMethods = "verifyElement")
	public void selectAddressType() throws InterruptedException {
		action.waitObjVisible(By.id(MaintainSuppliers.ADDRESS_POPUP));
		action.selectStatus(MaintainSuppliers.ADDRESS_TYPE, address_Type);
	}

	public void commonMethod() throws InterruptedException {
		WebElement address_popup= driver.findElement(By.id(MaintainSuppliers.ADDRESS_POPUP));
		address_popup.clear();
		address_popup.sendKeys(address_Popup);
		Thread.sleep(400);
		WebElement address_more= driver.findElement(By.id(MaintainSuppliers.ADDRESS_POPUP_2));
		address_more.clear();
		address_more.sendKeys(address_Popup_2);
		Thread.sleep(400);
		WebElement city_popup= driver.findElement(By.id(MaintainSuppliers.CITY_POPUP));
		city_popup.clear();
		city_popup.sendKeys(city_Popup);
		Thread.sleep(400);
		WebElement state_popup = driver.findElement(By.id(MaintainSuppliers.STATE_POPUP));
		state_popup.clear();
		state_popup.sendKeys(state_Popup);
		Thread.sleep(400);
		WebElement zip_code= driver.findElement(By.id(MaintainSuppliers.ZIPCODE));
		zip_code.clear();
		zip_code.sendKeys(zip_Code);
		Thread.sleep(400);
		
	}


}

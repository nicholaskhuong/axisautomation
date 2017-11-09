package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.BusinessCodeSets;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "953")
@Credentials(user = "mail231@abb.com", password = "Testuser2")
public class Supplier_Admin_BusinessCodeSets_TaxCode extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String supplierName = "Supplier Name";
	String deliveryCodeLessThan15 = "001";
	String deliveryCodeMoreThan15 = "Delivery Code example 123";
	int milliseconds = 1000;
	String codeSetDescription = "Code set";
	String expected = "Supplier Code Sets";
	String titleUpdatePopup = "Update Supplier Code Set";
	String taxType = "Tax";
	String taxtRate = "10";
	String taxTypeEdit = "Tax edit";
	String taxtRateEdit = "20";

	@Test
	public void openScreen() {
		// Step 1
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.cssSelector(BusinessCodeSets.BUSINESS_CODE_SETS));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.SUPPLIER_CODE_SETS_HEADER));
	}

	@Test(dependsOnMethods = "openScreen")
	public void expandTaxCode() {
		// step 2
		action.clickBtn(By.cssSelector(BusinessCodeSets.TAX_CODE_ICON));
		action.clickBtn(By.cssSelector(BusinessCodeSets.ADD_BUTTON1));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.ADD_NEW_SUPPLIER_CODE_SETS_POPUP));
	}

	@Test(dependsOnMethods = "expandTaxCode")
	public void mandatoryField() {
		// step 3
		action.waitObjVisible(By.id(ScreenObjects.SAVE_ID));
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "mandatoryField")
	// Step 4
	public void addTaxCodeSuccessfully() {
		action.inputTextField(By.id(BusinessCodeSets.TAXTYPE_ID), taxType);
		action.inputTextField(By.id(BusinessCodeSets.TAXTYPE_ID), taxType);
		action.inputTextField(By.id(BusinessCodeSets.TAXRATE_ID), taxtRate);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.DELIVERY_CODE_EQUAL_15CHARACTER);
	}

	@Test(dependsOnMethods = "addTaxCodeSuccessfully")
	public void updateTaxCodeSuccessfully() {
		// Step 5,6
		table.getCellObjectSupplierCodeSetTaxCode(1, 1).click();
		action.inputTextField(By.id(BusinessCodeSets.TAXTYPE_ID), taxType);
		action.inputTextField(By.id(BusinessCodeSets.TAXTYPE_ID), taxType);
		action.inputTextField(By.id(BusinessCodeSets.TAXRATE_ID), taxtRate);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.DELIVERY_CODE_SET_UPDATED_SUCCESS);
	}

	@Test(dependsOnMethods = "addTaxCodeSuccessfully")
	public void updateTaxCodeWithoutSavingNo() {
		// Step 7
		table.getCellObjectSupplierCodeSetTaxCode(2, 1).click();
		// Step 8
		action.inputTextField(By.id(BusinessCodeSets.TAXTYPE_ID), taxTypeEdit);
		action.inputTextField(By.id(BusinessCodeSets.TAXRATE_ID), taxtRateEdit);
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		// Step 9
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.DELETE_NO));
		action.waitObjVisible(By.id(BusinessCodeSets.TAXTYPE_ID));
	}

	@Test(dependsOnMethods = "updateTaxCodeWithoutSavingNo")
	public void updateTaxCodeWithoutSavingYes() {
		// Step 10
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		// step 11
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.YES_BTN_BACKUP));
	}

	@Test(dependsOnMethods = "updateTaxCodeWithoutSavingYes")
	public void deleteConfirmNo() {
		// step 12
		table.getCellObjectSupplierCodeSetTaxCode(1, 3).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.MESSAGE_DELETE_DILIVERY_CODE);
		// Step 13
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.DELETE_NO));
	}

	@Test(dependsOnMethods = "updateTaxCodeWithoutSavingYes")
	public void deleteConfirmYes() {
		table.getCellObjectSupplierCodeSetTaxCode(1, 3).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.MESSAGE_DELETE_DILIVERY_CODE);
		// Step 13
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.YES_BTN_BACKUP));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.DELIVERY_CODE_SET_DELETED_SUCCESS);
	}

}

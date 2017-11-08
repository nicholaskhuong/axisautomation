package com.abb.ventyx.axis.supplier;

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

@ALM(id = "111")
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
	String title_Popup = "Add New Supplier Code Set";
	String taxType = "Tax";
	String taxtRate = "10";

	@Test
	public void openScreen() {
		// Step 1
		// 1. Login as Supplier User (mail231@abb.com)
		// 2. Go to the Business Code Sets submenu under Administration menu --> Open
		// Supplier Code Sets page
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.cssSelector(BusinessCodeSets.BUSINESS_CODE_SETS));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.SUPPLIER_CODE_SETS_HEADER));
	}

	@Test(dependsOnMethods = "openScreen")
	public void expandTaxCode() {
		// Click on TaxCode icon
		action.clickBtn(By.cssSelector(BusinessCodeSets.TAX_CODE_ICON));
		// click on Add button --> Open Add New Supplier Code Set popup
		action.clickBtn(By.cssSelector(BusinessCodeSets.ADD_BUTTON1));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.ADD_NEW_SUPPLIER_CODE_SETS_POPUP));
	}

	@Test(dependsOnMethods = "expandTaxCode")
	public void mandatoryField() {
		// Click on Save --> A message "One or more fields are in error. Please
		// correct." will be displayed
		action.waitObjVisible(By.id(ScreenObjects.SAVE_ID));
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	@Test(dependsOnMethods = "mandatoryField")
	public void addTaxCodeSuccessfully() {
		// Input Tax Type
		action.inputTextField(By.id(BusinessCodeSets.TAXTYPE_ID), taxType);
		// Input Tax Rate
		action.inputTextField(By.id(BusinessCodeSets.TAXRATE_ID), taxtRate);
		// Click on Save button --> A message "Code Set Successfully Added" will be
		// displayed
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.DELIVERY_CODE_EQUAL_15CHARACTER);

	}

}

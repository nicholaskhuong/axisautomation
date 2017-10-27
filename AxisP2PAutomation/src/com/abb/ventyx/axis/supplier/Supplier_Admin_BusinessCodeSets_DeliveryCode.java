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

@ALM(id = "613")
@Credentials(user = "donna202@abb.com", password = "Testuser1")
public class Supplier_Admin_BusinessCodeSets_DeliveryCode extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String supplierName = "Donna 1";
	String deliveryCodeLessThan15 = "001";
	String deliveryCodeMoreThan15 = "DONNA NGUYEN THI NGUYEN";
	int milliseconds = 800;

	@Test
	public void openScreen() throws InterruptedException {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.cssSelector(BusinessCodeSets.BUSINESS_CODE_SETS));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.SUPPLIER_CODE_SETS_HEADER));

	}

	@Test(dependsOnMethods = "openScreen")
	public void addNewContact() throws InterruptedException {
		// step 2
		action.clickBtn(By.cssSelector(BusinessCodeSets.DELIVERY_CODE_ICON));
		// step 3
		action.waitObjVisibleAndClick(By.cssSelector(BusinessCodeSets.ADD_BUTTON));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.ADD_NEW_SUPPLIER_CODE_SETS_POPUP));
		action.pause(milliseconds);
		// step 4
		action.clickBtn(By.cssSelector(BusinessCodeSets.SAVE_BUTTON));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		// step 5
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.inputTextField(BusinessCodeSets.TAXTYPE_ID, deliveryCodeLessThan15);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.inputTextField(BusinessCodeSets.TAXTYPE_ID, deliveryCodeLessThan15);
		action.clickBtn(By.cssSelector(BusinessCodeSets.SAVE_BUTTON));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.ERROR_DELIVERYCODE));
		assertEquals(driver.findElement(By.cssSelector(BusinessCodeSets.ERROR_DELIVERYCODE)).getText(),
				Messages.DELIVERY_CODE_LESS_15CHARACTER);

	}

	@Test(dependsOnMethods = "addNewContact")
	public void addDeliverySuccessfully() throws InterruptedException {
		// step 6
		action.pause(milliseconds);
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.ERROR_DELIVERYCODE));
		action.inputTextField(BusinessCodeSets.TAXTYPE_ID, deliveryCodeMoreThan15);
		action.clickBtn(By.cssSelector(BusinessCodeSets.SAVE_BUTTON));
		action.waitObjVisible(By.cssSelector(BusinessCodeSets.SUCCESS_DELIVERYCODE));

		assertEquals(driver.findElement(By.cssSelector(BusinessCodeSets.SUCCESS_DELIVERYCODE)).getText().replaceAll("?", ""),
				Messages.DELIVERY_CODE_EQUAL_15CHARACTER);
	}

}

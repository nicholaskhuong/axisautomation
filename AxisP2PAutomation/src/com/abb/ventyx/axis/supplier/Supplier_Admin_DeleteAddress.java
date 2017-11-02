package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

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

@ALM(id = "627")
@Credentials(user = "donna201@abb.com", password = "Testuser1")
public class Supplier_Admin_DeleteAddress extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String expected = "Maintain Address & Contact";
	public int i;
	public int j;

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
	}

	@Test(dependsOnMethods = "openScreen")
	public void deleteOneorMoreNO() throws InterruptedException {
		// Step 2,3
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(),
				Messages.DELETE_ADDRESS_MESSAGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);
	}

	@Test(dependsOnMethods = "deleteOneorMoreNO")
	public void deleteOneorMoreYES() throws InterruptedException {
		// Step 4
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(),
				Messages.DELETE_ADDRESS_MESSAGE);
		driver.findElement(By.cssSelector(MaintainSuppliers.YES_BTN)).click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);
	}

}

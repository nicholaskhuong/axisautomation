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

@ALM(id = "648")
@Credentials(user = "donna202@abb.com", password = "Testuser1")
public class Supplier_Admin_DeleteContact extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String expected = "Maintain Address & Contact";
	int milliseconds = 800;

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
	}

	@Test(dependsOnMethods = "openScreen")
	public void deleteContact() throws InterruptedException {
		// step 2,3
		action.clickBtn(By.cssSelector(MaintainSuppliers.CONTACT_TAB));
		action.pause(milliseconds);
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.pause(milliseconds);
		action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));

		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(),
				Messages.DELETE_CONTACT_MESSAGE);
		action.pause(milliseconds);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);
		// step 4
		action.pause(milliseconds);
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(),
				Messages.DELETE_CONTACT_MESSAGE);
		driver.findElement(By.cssSelector(MaintainSuppliers.YES_BTN)).click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(), expected);
	}

}

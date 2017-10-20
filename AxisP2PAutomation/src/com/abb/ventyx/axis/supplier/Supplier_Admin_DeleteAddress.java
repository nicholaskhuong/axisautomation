package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
	String message_cant_delete = "The default address cannot be deleted";
	public static int i;
	public static int j;
	@Test
	public void openScreen() throws InterruptedException {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
	}
	@Test(dependsOnMethods = "openScreen")
	public void deleteOneorMoreNO() throws InterruptedException {
		//Step 2,3
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNDELETE_MESSAGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(),expected);
	}
	@Test(dependsOnMethods = "deleteOneorMoreNO")
	public void deleteOneorMoreYES() throws InterruptedException {
		//Step 4
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickCheckBoxN(1);
		action.clickCheckBoxN(2);
		action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNDELETE_MESSAGE);
		driver.findElement(By.cssSelector(MaintainSuppliers.YES_BTN)).click();
		action.waitObjVisible(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP));
		assertEquals(driver.findElement(By.cssSelector(MaintainSuppliers.EDIT_SUPPLIER_POPUP)).getText(),expected);
	}
	@Test(dependsOnMethods = "deleteOneorMoreYES")
	public void deleteDefaultType() throws InterruptedException {
		//Step 5
		action.waitObjVisible(By.id("addrId1"));
		//Click a record with Address type = Default Address
		table = new TableFunction(driver);
		i = table.findRowByString1(2, "Default Address");
		j=i-1;
		WebElement elemement= driver.findElement(By.id("addrId"+j));
		elemement.click();
		action.clickCheckBoxN(2);
		//action.clickBtn(By.cssSelector(MaintainSuppliers.DELETE_ICON));
		//action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		//assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNDELETE_MESSAGE);
		//driver.findElement(By.cssSelector(Maintain]=[Suppliers.YES_BTN)).click();
		
	}

	
}

package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
@ALM(id = "614")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_UpdateSupplier_ByAdmin_Step9 extends BaseTestCase {
	ScreenAction action;

	@Test
	public void loginAsTheUpdatedSupplierAndCheckASNOff() {
		action = new ScreenAction(driver);
		action.signOut();
		action.signIn(SupplierList_CreateActiveSupplier_ByAdmin.supplierEmail, SupplierList_CreateActiveSupplier_ByAdmin.password);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(AddressContact.COMPANY_NAME));
		assertEquals(driver.findElement(By.id(AddressContact.COMPANY_NAME)).getAttribute("value"),
				SupplierList_CreateActiveSupplier_ByAdmin.supplierName);
	}
}

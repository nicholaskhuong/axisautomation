package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
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
@Credentials(user = "donna900@abb.com", password = "Testuser2")
public class Supplier_Admin_UpdateNewAddress extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		// Step 1:
		// click on Administration button
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		// Click on Address & Contact button
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(MaintainSuppliers.SUPPLIER_NAME));
		// Click on Add icon to create a new Address
		action.clickBtn(By.cssSelector(MaintainSuppliers.ADDICON));

		System.out.println("Donna Nguyen");


	}
	/*// Step 2
	@Test(dependsOnMethods = "openScreen")
	public void updateSupplierEmail() throws InterruptedException {

	}*/

}

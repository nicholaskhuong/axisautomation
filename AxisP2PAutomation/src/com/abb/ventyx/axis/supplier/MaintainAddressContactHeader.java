package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressAndContact;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "601")
@Credentials(user = "mail601@enclave.vn", password = "Testuser2")
public class MaintainAddressContactHeader extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String supplierName;

	@Test
	public void openScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(AddressAndContact.SUPLLIERNAME_ID));
		action.assertTitleScreen(AddressAndContact.MAINTAIN_TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void UpdateAddressContact() {
		action = new ScreenAction(driver);
		supplierName = action.getTextField(AddressAndContact.SUPLLIERNAME_ID)
				+ "Auto Updated";
		action.inputTextField(AddressAndContact.SUPPLIEREMAIL_ID,
				"mail232@abb.com,thuydatherine@enclave.vn");
		action.inputTextField(AddressAndContact.SUPLLIERNAME_ID, supplierName);
		action.inputTextField(AddressAndContact.COMPANYREGISNO_ID, "22111984");
		action.inputTextField(AddressAndContact.TAXREGISNO_ID, "19102013");
		action.inputTextField(AddressAndContact.SUPPLIEREMAIL_ID,
				"mail601@enclave.vn,thuy601@enclave.vn");
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESSCONTACTUPDATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "UpdateAddressContact")
	public void cancelClickYes() {
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.inputTextField(AddressAndContact.COMPANYREGISNO_ID, "124");
		action.cancelByMenuClickYes(By.id(SupplierMenu.USERS_ID), Users.TITLE,
				By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	}

	@Test(dependsOnMethods = "cancelClickYes")
	public void cancelClickNo() {

		action.clickBtn(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisible(By.id(AddressAndContact.SUPLLIERNAME_ID));
		action.inputTextField(AddressAndContact.COMPANYREGISNO_ID, "124");
		action.cancelByMenuClickNo(By.id(SupplierMenu.USERS_ID),
				AddressAndContact.MAINTAIN_TITLE);
	}

	@Test(dependsOnMethods = "cancelClickNo")
	public void cancelWithoutdata() throws InterruptedException {

		action.inputTextField(AddressAndContact.SUPLLIERNAME_ID, supplierName);
		action.cancelByMenuWithoutdata(By.id(SupplierMenu.USERS_ID),
				Users.TITLE, By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	}
}

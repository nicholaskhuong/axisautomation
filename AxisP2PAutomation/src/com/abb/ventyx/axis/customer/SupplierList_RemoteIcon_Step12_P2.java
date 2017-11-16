package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "835")
@Credentials(user = "cuserdefault@abb.com", password = "Testuser1")
public class SupplierList_RemoteIcon_Step12_P2 extends BaseTestCase {
	ScreenAction action;
	TableFunction table;

	@Test
	public void CheckRemoteIconForPendingSupplier() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisible(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.assertTitleScreen("Customer Dashboard");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Suppliers");

		table.clickFilterAndInput(SupplierList.SUPPLIER_STATUS_FILTER_XPATH, "Pending");
		action.pause(2000);
		assertEquals(action.isRemoteIconDisable(1), true);
	}
}

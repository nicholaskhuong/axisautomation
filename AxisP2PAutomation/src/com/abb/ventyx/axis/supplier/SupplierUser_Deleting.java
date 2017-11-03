package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "618")
@Credentials(user = "salem85@abb.com", password = "Testuser1")
public class SupplierUser_Deleting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int row;

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void deleteConfirmNO() {
		// Step 2,3
		table = new TableFunction(driver);
		row = table.findRowByString(Users.SUPPLIER_USERS_TABLE_CSS, 2, "User ID 1");
		Assert.assertTrue(row >= 0, String.format("Supplier user %s not found!", "User ID 1"));
		table.assertValueRow(2, row, "User ID 1");
		row = row - 1;
		action.clickBtn(By.id(ScreenObjects.DELETE_BTN_ID + row));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
	}

	@Test(dependsOnMethods = "deleteConfirmNO")
	public void deleteConfirmYes() {
		action.clickBtn(By.id(ScreenObjects.DELETE_BTN_ID + row));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);
		// driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		// action.checkAddSuccess(Messages.USER_DELETE_SUCCESSFULLY);

	}
}

package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
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
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierUser_Deleting extends BaseTestCase {
	String USER_ID = "BOSS";
	ScreenAction action;
	TableFunction table;

	int row;

	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void selectRowDeleting() {
		table = new TableFunction(driver);
		row = table.findRowByString(Users.SUPPLIER_USERS_TABLE_CSS, USER_ID, 2);
		table.assertValueRow(2, row, USER_ID);
		row = row - 1;
		action.clickBtn(By.id(ScreenObjects.DELETE_BTN_ID + row));
	}

	@Test(dependsOnMethods = "selectRowDeleting")
	public void deleteUserClickNo() throws Exception {
		// Click No on dialog
		action.deleteClickNo(Messages.DELETE_USER_CONFIRM);
		table.assertValueRow(2, row + 1, USER_ID);

	}

	@Test(dependsOnMethods = "deleteUserClickNo")
	public void deleteUserClickYes() throws Exception {
		int sumRowBefore = table.countRow(Users.SUPPLIER_USERS_TABLE_CSS);
		// Click Yes on dialog
		action.clickBtn(By.id(ScreenObjects.DELETE_BTN_ID + row));
		action.deleteClickYes(Messages.USER_DELETE_SUCCESSFULLY);
		int sumRowAfter = table.countRow(Users.SUPPLIER_USERS_TABLE_CSS);
		assertEquals(sumRowAfter, sumRowBefore - 1);
	}

}

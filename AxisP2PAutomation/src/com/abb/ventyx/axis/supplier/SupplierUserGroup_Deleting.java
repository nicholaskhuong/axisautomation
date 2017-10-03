package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "625")
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierUserGroup_Deleting extends BaseTestCase {
	String USER_GROUP_NAME = "POGroup";
	ScreenAction action;
	TableFunction table;

	int row;

	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USER_GROUPS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(UserGroup.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void selectRowDeleting() {
		table = new TableFunction(driver);
		row = table.findRowByString(UserGroup.SUPPLIER_GROUP_TABLE_CSS,
				USER_GROUP_NAME, 1);
		table.assertRowEqual(UserGroup.ROW_ID, USER_GROUP_NAME, row - 1);
		row = row - 1;
		action.clickBtn(By.id(ScreenObjects.DELETE_BTN_ID + row));

	}

	@Test(dependsOnMethods = "selectRowDeleting")
	public void deleteUserGroupClickNo() throws Exception {

		// Click No on dialog
		action.deleteClickNo(Messages.DELETE_USERGROUP_CONFIRM);
		table.assertRowEqual(UserGroup.ROW_ID, USER_GROUP_NAME, row);

	}

	@Test(dependsOnMethods = "deleteUserGroupClickNo")
	public void deleteUserGroupClickYes() throws Exception {
		int sumRowBefore = table.countRow(UserGroup.SUPPLIER_GROUP_TABLE_CSS);
		// Click Yes on dialog
		action.clickBtn(By.id(ScreenObjects.DELETE_BTN_ID + row));
		action.deleteClickYes(Messages.USERGROUP_DELETE_SUCCESSFULLY);
		int sumRowAfter = table.countRow(UserGroup.SUPPLIER_GROUP_TABLE_CSS);
		assertEquals(sumRowAfter, sumRowBefore - 1);
	}

}

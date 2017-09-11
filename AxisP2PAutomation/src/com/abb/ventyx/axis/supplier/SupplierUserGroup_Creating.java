package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObject;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "601")
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierUserGroup_Creating extends BaseTestCase {
	String USER_GROUP_NAME = "POGroup";
	ScreenAction action;

	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USER_GROUPS_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADD_BTN_CSS));
		action.assertTitleScreen(UserGroup.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void addUserGroup() {

		action.clickBtn(By.cssSelector(UserGroup.ADD_BTN_CSS));
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		action.assertTitleScreen(UserGroup.TITLE_CREATE);
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, USER_GROUP_NAME);

		// Click PO permission
		TableFunction table = new TableFunction(driver);
		int row = table.findRowByString(UserGroup.PERMISSION_TABLE_CSS,
				"PurchaseOrder", 3);
		action.clickCheckBoxN(row);
		action.clickBtn(By.id(ScreenObject.SAVE_ID));
		action.checkAddSuccess(Messages.USERGROUP_CREATE_SUCCESSFULLY);

	}

	@Test(dependsOnMethods = "addUserGroup")
	public void addValidation() throws InterruptedException {
		action.checkValidationTextField(UserGroup.USERGROUP_NAME_ID,
				USER_GROUP_NAME);
	}

	@Test(dependsOnMethods = "addValidation")
	public void cancelClickYes() {

		action.waitObjInvisible(By.cssSelector(ScreenObject.ERROR_CSS));
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, "ABC");
		action.cancelClickYes(By.cssSelector(UserGroup.ADD_BTN_CSS),
				UserGroup.TITLE);
	}

	@Test(dependsOnMethods = "cancelClickYes")
	public void cancelClickNo() {

		action.clickBtn(By.cssSelector(UserGroup.ADD_BTN_CSS));
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, USER_GROUP_NAME);
		action.cancelClickNo(UserGroup.TITLE_CREATE);

	}

	@Test(dependsOnMethods = "cancelClickNo")
	public void cancelWithoutdata() throws InterruptedException {

		action.inputTextField(UserGroup.USERGROUP_NAME_ID, "");
		action.cancelWithoutdata(By.cssSelector(UserGroup.ADD_BTN_CSS),
				UserGroup.TITLE);
	}
}

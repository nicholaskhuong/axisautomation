package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

@ALM(id = "620")
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierUserGroup_Updating extends BaseTestCase {
	String USER_GROUP_NAME = "POGroup";
	ScreenAction action;
	TableFunction table;
	String PERMISSION = "Purchase Orders";
	String PERMISSION_CHILD = "Export Purchase Order";
	int row;

	@Test
	public void openScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USER_GROUPS_ID));
		action.waitObjVisible(By.cssSelector(UserGroup.ADD_BTN_CSS));
		action.assertTitleScreen(UserGroup.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void selectRowUpdating() {
		table = new TableFunction(driver);
		row = table.findRowByString(UserGroup.SUPPLIER_GROUP_TABLE_CSS, 1, USER_GROUP_NAME);
		row -= 1;
		table.assertRowEqual(UserGroup.ROW_ID, USER_GROUP_NAME, row);

		action.clickBtn(By.id(UserGroup.ROW_ID + row));
	}

	@Test(dependsOnMethods = "selectRowUpdating")
	public void selectRowUpdatingDetail() {
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		action.assertTitleScreen(UserGroup.TITLE_MODIFY);
		row = table.findRowByString(UserGroup.USER_PERMISSION_TABLE_XPATH, 3, PERMISSION, true);
		row -= 1;
		// click expand row
		action.clickBtn(By.id(UserGroup.EXPAND_ID + row));

		String permissionChildCSS = UserGroup.PERMISSION_CHILD_ID + row + UserGroup.PERMISSION_CHILD_TABLE_CSS;
		action.waitObjVisible(By.cssSelector(permissionChildCSS));
		// Un check a permission child
		int rowChild = table.findRowByString(permissionChildCSS, 2, PERMISSION_CHILD);
		int end = table.countRow(permissionChildCSS);
		action.checkObjSelected(row, end);
		int newRow = row + rowChild + 1;
		action.clickCheckBoxN(newRow);
		boolean select = false;
		select = action.checkObjSelected(newRow);
		assertEquals(select, false);
	}

	@Test(dependsOnMethods = "selectRowUpdatingDetail")
	public void selectRowUpdatingClickSave() {
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.USERGROUP_UPDATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "selectRowUpdatingClickSave")
	public void addValidation() throws InterruptedException {
		(new WebDriverWait(driver, 20)).until((ExpectedConditions.elementToBeClickable(By.id(UserGroup.ROW_ID + row))));
		action.clickBtn(By.id(UserGroup.ROW_ID + row));
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		action.checkValidationTextField(UserGroup.USERGROUP_NAME_ID, "Administrator", Messages.USERGROUP_EXISTING, ScreenObjects.ERROR_CSS);
	}

	@Test(dependsOnMethods = "addValidation")
	public void cancelClickYes() {

		// action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, "ABC");
		action.cancelClickYes(By.cssSelector(UserGroup.ADD_BTN_CSS), UserGroup.TITLE);
	}

	@Test(dependsOnMethods = "cancelClickYes")
	public void cancelClickNo() {

		action.clickBtn(By.id(UserGroup.ROW_ID + row));
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, "ABC");
		action.cancelClickNo(UserGroup.TITLE_MODIFY);

	}

	@Test(dependsOnMethods = "cancelClickNo")
	public void cancelWithoutdata() throws InterruptedException {

		action.inputTextField(UserGroup.USERGROUP_NAME_ID, USER_GROUP_NAME);
		driver.findElement(By.id(ScreenObjects.CANCEL_ID)).click();
		action.waitObjInvisible(By.id(ScreenObjects.CANCEL_ID));
		action.assertTitleScreen(UserGroup.TITLE);

	}

}

package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "640")
@Credentials(user = "supplier_user_2@abb.com", password = "Testuser1")
public class SupplierUserGroups_Filter extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int row;
	WebElement index;
	String userGroupNumber = "Salem 11";

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USER_GROUPS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE_HEADER);
	}

	@Test(dependsOnMethods = "openScreen")
	public void filterFunction() {
		table = new TableFunction(driver);
		// step 2,3,4
		table.inputFilter(userGroupNumber);
		action.clickBtn(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
	}

}

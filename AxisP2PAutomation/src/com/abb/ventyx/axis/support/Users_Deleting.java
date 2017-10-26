package com.abb.ventyx.axis.support;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertNotEquals;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "205")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Users_Deleting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int row;
	String userID = "DathyUser1";
	String email = "email@dathy.com";
	String suserID;
	@Test
	public void checkScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID));
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
		action.waitObjVisible(By.id(AxisAdministratorUsers.ROW_ID + "0"));
		action.assertTextEqual(By.cssSelector(AxisAdministratorUsers.SCREEN_TITLE_CSS), AxisAdministratorUsers.SCREEN_TITLE);
		Assert.assertFalse("Field is ediable", action.isFieldDisable(By.id(AxisAdministratorUsers.DELETE_ID + "0")));
	}

	@Test(dependsOnMethods = "checkScreen")
	public void deleteUserGroupClickNo() throws Exception {
		row = table.findRowByString(ScreenObjects.TABLE_BODY_USER_XPATH, 2, userID, true);
		Assert.assertTrue("User not found!", row > 0);
		table.assertValueRow(ScreenObjects.TABLE_BODY_USER_XPATH, 3, row, email);
		driver.findElement(By.id(AxisAdministratorUsers.DELETE_ID + row))
				.click();
		suserID = table.getIDValue(ScreenObjects.TABLE_BODY_USER_XPATH, 1, row);
		// Click No on dialog
		WebElement deleteConfirm = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObjects.CONFIRMATION)));
		assertThat(deleteConfirm.getText(),
				containsString(Messages.DELETE_USER_CONFIRM));
		WebElement deleteNoBtn = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.DELETE_NO)));
		deleteNoBtn.click();
		Thread.sleep(2000);
		table.assertValueRow(1, row, suserID);

	}

	@Test(dependsOnMethods = "deleteUserGroupClickNo")
	public void deleteUserGroupClickYes() throws Exception {
		row = table.findRowByString(ScreenObjects.TABLE_BODY_USER_XPATH, 2, userID, true);
		Assert.assertTrue("User not found!", row > 0);
		table.assertValueRow(ScreenObjects.TABLE_BODY_USER_XPATH, 3, row, email);
		// Click Yes on dialog
		driver.findElement(By.id(AxisAdministratorUsers.DELETE_ID + row)).click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.CONFIRMATION)));
		WebElement deleteYesBtn = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.DELETE_YES)));
		deleteYesBtn.click();
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_DELETE_SUCCESSFULLY);
		assertNotEquals(driver.findElement(By.id(AxisAdministratorUsers.ROW_ID + row)).getText(), suserID);
	}

}

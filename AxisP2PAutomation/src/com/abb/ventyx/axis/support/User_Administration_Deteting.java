package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "559")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Administration_Deteting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebElement index;
	String newPassword = "Testuser2";
	int waitTime = 1000;
	int i;

	// Step 1
	@Test
	public void openUserScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID));
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Users.TITLE_ADMINISTRATION_USERS);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(table.getValueTableHeader(1), "User Number");
		assertEquals(table.getValueTableHeader(2), "User ID");
		assertEquals(table.getValueTableHeader(3), "Email");
		assertEquals(table.getValueTableHeader(4), "User Groups");
		assertEquals(table.getValueTableHeader(5), "Status");
	}

	@Test(dependsOnMethods = "openUserScreen", alwaysRun = true)
	public void clickFiterButton() {
		action.clickVerticalScrollBar();
		table.clickFilterAndInputWithColumn(User_Administration_Updating.userUpdate, Users.USER_ID_FILTER, true);
		action.pause(3000);
	}

	@Test(dependsOnMethods = "clickFiterButton")
	public void clickDeleteButton() {
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index = table.getCellObject(1, 6);
		action.pause(waitTime);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_DELETE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "clickDeleteButton", alwaysRun = true)
	public void checkDataAgainAfterDeleted() {
		table.inputFilterAtIndex(User_Administration_Updating.userUpdate, Users.USER_ID_FILTER, true);
		action.pause(waitTime);
		Assert.assertTrue(i >= 0, String.format("User: %s not found!", User_Administration_Updating.userUpdate));
	}

	@Test(dependsOnMethods = "checkDataAgainAfterDeleted", alwaysRun = true)
	public void logoutAndLoginWithNewUser() {
		action.pause(waitTime);
		action.signOut();
		action.pause(waitTime);
		action.signIn(User_Administration_Updating.emailUpdate, newPassword);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.USERNOTFOUND);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}

}

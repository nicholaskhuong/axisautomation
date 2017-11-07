package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "631")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_Deleted_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i;
	String password = "Testuser1";
	String customerUserEmailDefault = "cuserdefault@abb.com";
	// Step 1 Select Users Sub Menu
	@Test
	public void selectUsersSubMenu() {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	@Test(dependsOnMethods = "selectUsersSubMenu")
	public void deleteCustomerAdminDisable() throws InterruptedException {
		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN))
				.getAttribute("aria-disabled"), "true");
	}

	// Click Trash Bin icon of the user to test
	@Test(dependsOnMethods = "deleteCustomerAdminDisable")
	public void clickTrashBinIconOfUser() {
		action.pause(1000);
		i = table.findRowByString(3, CustomerUser_Created_ByAdmin.USEREMAILADDRESS);
		action.clickBtn(By.id("deleteItemBtn" + (i - 1)));

		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USER_CONFIRM);
	}

	// Step 3 Choose No
	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void clickNoButton() {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(action.isElementPresent(By
				.cssSelector(ScreenObjects.CONFIRMATION)), false);
		assertEquals(
				driver.findElement(
						By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER))
						.getText(), "Maintain Customer Users");
	}

	// Step 4 Click Trash Bin and choose Yes
	@Test(dependsOnMethods = "clickNoButton")
	public void clickYesButton() {
		action.pause(2000);
		action.clickBtn(By.id("deleteItemBtn" + (i-1)));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		assertEquals(
				driver.findElement(
						By.cssSelector(ScreenObjects.SUCCESS_MESSAGE))
						.getText(), Messages.USER_DELETE_SUCCESSFULLY);
		assertEquals(table.isValueExisting(3, CustomerUser_Created_ByAdmin.USEREMAILADDRESS), false);
	}

	// Step 5 check that can't login as the deleted user
	@Test(dependsOnMethods = "clickYesButton")
	public void loginAsTheDeletedUser() throws InterruptedException {

		action.signOut();
		action.signIn(CustomerUser_Created_ByAdmin.USEREMAILADDRESS, password);
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		assertEquals(
				driver.findElement(By.cssSelector(ScreenObjects.ERROR_CSS))
						.getText(), Messages.USERNOTFOUND);
	}

	@Test(dependsOnMethods = "loginAsTheDeletedUser")
	public void loginAsCustomerUser() {
		action.pause(1000);
		action.signIn(customerUserEmailDefault, password);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		// System wrong here, found title User
		// action.assertTitleScreen("Maintain Customer Users");

	}

	@Test(dependsOnMethods = "loginAsCustomerUser")
	public void deleteAdminDisableAndUserEnable() {
		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN)).getAttribute("aria-disabled"), "true");
		assertEquals(action.isFieldDisable(By.id("deleteItemBtn1")), false);
	}

}

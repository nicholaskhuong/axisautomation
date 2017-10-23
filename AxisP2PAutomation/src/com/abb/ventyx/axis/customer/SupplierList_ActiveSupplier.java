package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
import com.ventyx.testng.TestDataKey;

@ALM(id = "644")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_ActiveSupplier extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	public static int i;
	public static int j;
	@TestDataKey private final String SUPPLIERNAME = "Yamaha9";
	@TestDataKey private final String SUPPLIEREMAIL = "yamaha9@abb.com";
	@TestDataKey private final String INACTIVESTATUS = "Inactive";
	@TestDataKey private final String ACTIVESTATUS = "Active";
	@TestDataKey private final String NEWPASSWORD = "Testuser1";

	// Step 1
	@Test
	public void openSupplierListScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Suppliers");
	}

	// Step 2
	@Test(dependsOnMethods="openSupplierListScreen")
	public void clickDeactiveWithoutSelectedSupplier(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.ACTIVATE_CSS));
		action.assertMessgeError(ScreenObjects.WARNING_MESSAGE_CSS, Messages.ACTIVATE_DEACTIVE_WITHOUTSUPPLIER);
	}

	// Step 3
	@Test(dependsOnMethods="clickDeactiveWithoutSelectedSupplier")
	public void activeAnActiveSupplier() throws InterruptedException{
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		i = table.findRowByString1(6, SUPPLIEREMAIL);
		table.selectRow(i);
		Thread.sleep(2000);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.ACTIVATE_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS), Messages.SUPPLIER_ALREADY_ACTIVE);
	}

	// Step 4
	@Test(dependsOnMethods="activeAnActiveSupplier")
	public void clickDeactiveWithSelectedSupplier(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.DEACTIVATE_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.DEACTIVATE_SUPPLIER);
	}
	// Step 5
	@Test(dependsOnMethods="clickDeactiveWithSelectedSupplier")
	public void clickNo(){
		action = new ScreenAction(driver);
		action.clickBtn(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		action.assertTitleScreen("Maintain Suppliers");
	}
	// Step 6
	@Test(dependsOnMethods="clickNo")
	public void clickYes(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.DEACTIVATE_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEACTIVATE_SUPPLIER_SUCCESSFULLY);
		action.assertTitleScreen("Maintain Suppliers");
		assertEquals(table.getValueRow(4, i), INACTIVESTATUS);
		j=i-1;
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),true);
	}
	@Test(dependsOnMethods="clickNo")
	public void loginInactiveUser(){
		action = new ScreenAction(driver);
		action.signOut();
		action.signIn(SUPPLIEREMAIL, NEWPASSWORD);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SUPPLIER_INACTIVE);

		action.signIn("cadmin1@abb.com", "Testuser1");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Suppliers");

	}

	// Step 7, 8
	@Test(dependsOnMethods="loginInactiveUser")
	public void activeAnInactiveSupplier(){
		action = new ScreenAction(driver);
		table.selectRow(i);

		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.ACTIVATE_CSS));

		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.ACTIVATE_SUPPLIER);

		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));

		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.ACTIVATE_SUPPLIER_SUCCESSFULLY);
		action.assertTitleScreen("Maintain Suppliers");
		assertEquals(table.getValueRow(4, i), ACTIVESTATUS);
		j=i-1;
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),false);
	}
	// Step 9
	@Test(dependsOnMethods="activeAnInactiveSupplier")
	public void loginActiveUser(){
		action = new ScreenAction(driver);
		action.signOut();
		action.signIn(SUPPLIEREMAIL, NEWPASSWORD);
		action.waitObjVisible(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), "Supplier Dashboard");

	}

}

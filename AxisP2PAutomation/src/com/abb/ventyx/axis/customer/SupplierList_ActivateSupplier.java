package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "644")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_ActivateSupplier extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i;
	int j;
	String supplierName = "Yamaha8";
	String supplierEmail = "yamaha8@abb.com";
	String userSupplierEmailCreated = "cuseryamaha8@abb.com";
	String userSupplierEmailActive = "cuseractive@abb.com";
	String inactiveStatus = "Inactive";
	String activeStatus = "Active";
	String password1 = "Testuser1";
	String password2 = "Testuser2";

	// Step 1
	@Test
	public void openSupplierListScreen(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Suppliers");
		i = table.findRowByString1(6, supplierEmail);
		assertEquals(table.getValueRow(4, i), inactiveStatus);
		j=i-1;
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),true);
	}

	// Step 2
	@Test(dependsOnMethods="openSupplierListScreen")
	public void clickActiveWithoutSelectedSupplier(){
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.ACTIVATE_CSS));
		action.assertMessgeError(ScreenObjects.WARNING_MESSAGE_CSS, Messages.ACTIVATE_DEACTIVE_WITHOUTSUPPLIER);
	}

	// Step 3
	@Test(dependsOnMethods="clickActiveWithoutSelectedSupplier")
	public void deactivateAnInactiveSupplier() throws InterruptedException{
		table.selectRow(i);
		action.pause(1000);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.DEACTIVATE_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS), Messages.SUPPLIER_ALREADY_INACTIVE);
	}

	// Step 4
	@Test(dependsOnMethods="deactivateAnInactiveSupplier")
	public void activeAnInactiveSupplier(){
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.ACTIVATE_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.ACTIVATE_SUPPLIER);
	}

	// Step 5
	@Test(dependsOnMethods="activeAnInactiveSupplier")
	public void clickNoToCancelActivateAction(){
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		action.assertTitleScreen("Maintain Suppliers");
	}

	// Step 6
	@Test(dependsOnMethods="clickNoToCancelActivateAction")
	public void clickYesToActivateSupplier(){
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.ACTIVATE_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.ACTIVATE_SUPPLIER_SUCCESSFULLY);
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Suppliers");
		assertEquals(table.getValueRow(4, i), activeStatus);
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),false);
	}

	// Step 7
	@Test(dependsOnMethods="clickYesToActivateSupplier")
	public void loginAsActiveUser(){
		action.signOut();
		// Admin
		action.signIn(supplierEmail, password1);
		action.waitObjVisible(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), "Supplier Dashboard");
		
		action.signOut();
		// User with Active status
		action.signIn(userSupplierEmailActive, password1);
		action.waitObjVisible(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), "Supplier Dashboard");
		
		action.signOut();
		// User with Created status
		action.signIn(userSupplierEmailCreated, password2);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.ERROR_CSS)),false);
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		assertEquals(action.isElementPresent(By.id(ScreenObjects.NEWPASSWORD_ID)),true);
	}

}

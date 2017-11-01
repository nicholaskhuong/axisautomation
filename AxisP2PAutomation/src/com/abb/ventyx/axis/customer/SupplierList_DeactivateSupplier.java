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

@ALM(id = "630")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_DeactivateSupplier extends BaseTestCase {
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
		i = table.findRowByString(6, supplierEmail);
		assertEquals(table.getValueRow(4, i), activeStatus);
		j=i-1;
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),false);
	}

	// Step 2
	@Test(dependsOnMethods = "openSupplierListScreen", alwaysRun = true)
	public void clickDeactivateWithoutSelectedSupplier(){
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.DEACTIVATE_CSS));
		action.assertMessgeError(ScreenObjects.WARNING_MESSAGE_CSS, Messages.ACTIVATE_DEACTIVE_WITHOUTSUPPLIER);
	}

	// Step 3
	@Test(dependsOnMethods="clickDeactivateWithoutSelectedSupplier")
	public void activateAnActiveSupplier(){
		table.selectRow(i);
		action.pause(500);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.ACTIVATE_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS), Messages.SUPPLIER_ALREADY_ACTIVE);
	}

	// Step 4
	@Test(dependsOnMethods="activateAnActiveSupplier")
	public void deactivateAnInactiveSupplier(){
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.DEACTIVATE_CSS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.DEACTIVATE_SUPPLIER);
	}

	// Step 5
	@Test(dependsOnMethods="deactivateAnInactiveSupplier")
	public void clickNoToCancelDeactivateAction(){
		action.clickBtn(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		action.assertTitleScreen("Maintain Suppliers");
	}

	// Step 6
	@Test(dependsOnMethods="clickNoToCancelDeactivateAction")
	public void clickYesToDeactivateSupplier(){
		action.waitObjVisibleAndClick(By.cssSelector(SupplierList.DEACTIVATE_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEACTIVATE_SUPPLIER_SUCCESSFULLY);
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.pause(500);
		assertEquals(table.getValueRow(4, i), inactiveStatus);
		action.assertTitleScreen("Maintain Suppliers");
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),true);
	}

	@Test(dependsOnMethods="clickYesToDeactivateSupplier")
	public void loginAsInactiveUser(){
		action.signOut();

		//Admin
		action.signIn(supplierEmail, password1);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SUPPLIER_INACTIVE);
		action.clickBtn(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		// User with Active status
		action.signIn(userSupplierEmailActive, password1);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SUPPLIER_INACTIVE);
		action.clickBtn(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		// User with Created status
		action.signIn(userSupplierEmailCreated, password2);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SUPPLIER_INACTIVE);


	}

}

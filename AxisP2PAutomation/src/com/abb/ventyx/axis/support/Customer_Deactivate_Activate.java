package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerList;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "458")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Customer_Deactivate_Activate extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;
	String emailAddress = "customerDeAction458@abb.com";
	String password = "Testuser1";
	String emailAdmin = "axis_support@abb.com";
	String passwordAdmin = "Testuser1";

	@Test
	public void openCustomerScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
	}

	// Steps 02_04
	@Test(dependsOnMethods = "openCustomerScreen", alwaysRun = true)
	public void clickAddButtonOn() {
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ADD_BUTTON));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
	}

	// Step 5
	@Test(dependsOnMethods = "clickAddButtonOn", alwaysRun = true)
	public void clickDeactiveOnCustomerScreen() {
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.pause(2000);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.DEACTIVE));
		action.pause(3000);
		action.assertMessgeError(ScreenObjects.WARNING_MESSAGE_CSS, Messages.DEACTIVE_CUSTOMER);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
	}

	// Step 6
	@Test(dependsOnMethods = "clickDeactiveOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnCustomerScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		WebElement filterPermissionName = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(CustomerList.EMAIL_FILTER)));
		filterPermissionName.sendKeys(emailAddress);
	}

	// Step 7
	@Test(dependsOnMethods = "clickFiterButtonOnCustomerScreen", alwaysRun = true)
	public void clickDeactiveNoOnCustomerScreen() {
		action.pause(waitTime);
		WebElement row = driver.findElement(By.xpath(ScreenObjects.TABLE_ROW_XPATH_CUSTOMER));
		row.click();
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_DEACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
	}

	// Steps 8 9
	@Test(dependsOnMethods = "clickDeactiveNoOnCustomerScreen", alwaysRun = true)
	public void clickDeactiveYesOnCustomerScreen() {
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_DEACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEACTIVATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 10
	@Test(dependsOnMethods = "clickDeactiveYesOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonToSearchInActiveCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.pause(waitTime);
		table = new TableFunction(driver);
		table.clikFilterAndInputWithColumn(emailAddress, CustomerList.EMAIL_FILTER, true);
		action.pause(waitTime);
		WebElement cell = table.getCellObject(1, 1);
		cell.click();
		action.pause(waitTime);
		// Check to make sure its status here is Inactive too.
		// assertEquals(action.getTextField(By.id(CustomerList.STATUS)),
		// "Inactive");
	}

	// Step 11
	@Test(dependsOnMethods = "clickFiterButtonToSearchInActiveCustomer", alwaysRun = true)
	public void logoutAndLoginWithUserDeactive() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(emailAddress, password);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.USER_INACTIVE);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}

	// Step 12
	@Test(dependsOnMethods = "logoutAndLoginWithUserDeactive", alwaysRun = true)
	public void logoutAndLoginWithMailAdmin() {
		action.signIn(emailAdmin, passwordAdmin);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
	}

	@Test(dependsOnMethods = "logoutAndLoginWithMailAdmin", alwaysRun = true)
	public void clickActiveOnCustomerScreen() {
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ACTIVE));
		action.assertMessgeError(ScreenObjects.WARNING_MESSAGE_CSS, Messages.ACTIVE_CUSTOMER);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS));
	}

	@Test(dependsOnMethods = "clickActiveOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnCustomerScreen2() {
		table.clikFilterAndInputWithColumn(emailAddress, CustomerList.EMAIL_FILTER, true);
	}

	// Steps 13_14
	@Test(dependsOnMethods = "clickFiterButtonOnCustomerScreen2", alwaysRun = true)
	public void clickActiveNoOnCustomerScreen() {
		WebElement row = driver.findElement(By.xpath(ScreenObjects.TABLE_ROW_XPATH_CUSTOMER));
		row.click();
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ACTIVE));
		action.pause(waitTime);
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_ACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
	}

	// Steps 15_16
	@Test(dependsOnMethods = "clickActiveNoOnCustomerScreen", alwaysRun = true)
	public void clickActiveYesOnCustomerScreen() {
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ACTIVE));
		action.pause(waitTime);
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_ACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.ACTIVATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step17
	@Test(dependsOnMethods = "clickActiveYesOnCustomerScreen", alwaysRun = true)
	public void logoutAndLoginWithUserActive() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(emailAddress, password);
	}
}

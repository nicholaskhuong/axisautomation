package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

@ALM(id = "850")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Access_Customer_And_Supplier extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;
	String nameCustomerList = "Perla 01";
	int i;

	// Step 01
	@Test
	public void openCustomerListScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_CUSTOMERS_PAGE);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		assertEquals(table.getValueTableHeader(2), "Name");
		assertEquals(table.getValueTableHeader(3), "Status");
		assertEquals(table.getValueTableHeader(4), "Email");
		assertEquals(table.getValueTableHeader(5), "Homepage");
		assertEquals(table.getValueTableHeader(1), "ID");
	}

	// Step 2
	@Test(dependsOnMethods = "openCustomerListScreen", alwaysRun = true)
	public void clickDeactivateAnCustomer() {
		table.clikFilterAndInputWithColumn(nameCustomerList, CustomerList.NAME_FILTER, true);
		action.pause(3000);
		WebElement row = driver.findElement(By.xpath(ScreenObjects.TABLE_ROW_XPATH_CUSTOMER));
		row.click();
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_DEACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEACTIVATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		index = table.getCellObject(1, 6);
		index.isDisplayed();
	}

	@Test(dependsOnMethods = "clickDeactivateAnCustomer", alwaysRun = true)
	public void clickActivateTheCustomer() {
		action.pause(3000);
		WebElement row = driver.findElement(By.xpath(ScreenObjects.TABLE_ROW_XPATH_CUSTOMER));
		row.click();
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerList.ACTIVE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.CUSTOMER_ACTIAVTE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.ACTIVATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		index = table.getCellObject(1, 6);
		index.isEnabled();
	}
}

package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "716")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Customer_Deleting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	WebElement index;
	String password = "Testuser1";

	@Test
	public void openCustomerScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERS));
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Users.TITLE_MAINTENANCE_USERS);
		assertEquals(table.getValueTableHeader(1), "ID");
		assertEquals(table.getValueTableHeader(2), "Name");
		assertEquals(table.getValueTableHeader(3), "Status");
	}

	@Test(dependsOnMethods = "openCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnCustomerScreen() {
		table.clickFilterAndInputWithColumn("435", Users.USER_NUMBER_FILTER, true);
		action.pause(waitTime);
		assertEquals(table.getValueRow(2, 1), "QATest");
	}

	@Test(dependsOnMethods = "clickFiterButtonOnCustomerScreen", alwaysRun = true)
	public void clickCustomerIDOnCustomerScreen() {
		index = table.getCellObject(
				"//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div/div/div/div[3]/table/tbody", 1, 1);
		action.pause(waitTime);
		index.click();
	}

	@Test(dependsOnMethods = "clickCustomerIDOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerUsersScreen() {
		action.pause(waitTime);
		table.clickFilterAndInputWithColumn(User_Customer_Updating.emailUpdate, Users.EMAIL_FILTER, true);
		assertEquals(table.getValueRow(1, 1), User_Customer_Updating.emailUpdate);
	}

	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerUsersScreen", alwaysRun = true)
	public void clickDeleteButtonOnMaintainCustomerUsersScreen() {
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index = table.getCellObject(1, 5);
		index.click();
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		index.click();
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.USER_DELETE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "clickDeleteButtonOnMaintainCustomerUsersScreen", alwaysRun = true)
	public void checkDataAgainAfterDeleted() {
		action.pause(waitTime);
		table.clickFilterAndInputWithColumn(User_Customer_Updating.emailUpdate, Users.EMAIL_FILTER, true);
		Assert.assertEquals(0, table.countRow(ScreenObjects.TABLE_BODY_XPATH), "Grid is not empty");
	}

}

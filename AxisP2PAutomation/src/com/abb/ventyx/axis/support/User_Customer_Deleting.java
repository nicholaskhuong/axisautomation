package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
	String userUpdate = "customer_user1";
	String password = "Testuser1";
	String emailUpdate = "customer_user1@abb.com";
	
	@Test
	public void openCustomerScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.USERS));
	}
	
	@Test(dependsOnMethods = "openCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnCustomerScreen() {
		table = new TableFunction(driver);
		table.clikFilterAndInputWithColumn("435", Users.USER_NUMBER_FILTER, true);
		action.pause(waitTime);
		assertEquals(table.getValueRow(2, 1), "QATest");
	}
	
	@Test(dependsOnMethods = "clickFiterButtonOnCustomerScreen", alwaysRun = true)
	public void clickCustomerIDOnCustomerScreen() {
		index = table.getCellObject("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div/div/div/div[3]/table/tbody",1, 1);
		action.pause(waitTime);
		index.click();
	}
	
	@Test(dependsOnMethods = "clickCustomerIDOnCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerUsersScreen() {
		action.pause(waitTime);
		table.clikFilterAndInputWithColumn(userUpdate, Users.USER_ID_FILTER, true);
		assertEquals(table.getValueRow(2, 1), userUpdate);
	}
	
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerUsersScreen", alwaysRun = true)
	public void clickDeleteButtonOnMaintainCustomerUsersScreen() {
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index =  table.getCellObject(1, 6);
		action.pause(waitTime);
		index.click();
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		index.click();
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.USER_DELETE_SUCCESSFULLY);
	}
	
}


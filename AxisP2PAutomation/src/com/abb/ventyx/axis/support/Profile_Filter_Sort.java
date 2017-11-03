package com.abb.ventyx.axis.support;


import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "720")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Profile_Filter_Sort extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	int i;
	String customerName = "Tomatoo";
	String profileName = "All Document Types";
	WebElement index;
	
	@Test
	public void openCustomersScreen(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	
	@Test(dependsOnMethods = "openCustomersScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, customerName);
	}
	
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void checkFiterOnMaintainCustomerScreen(){
		action.pause(waitTime);
		assertEquals(table.getValueRow(1, 1), customerName);
		assertEquals(table.getValueRow(2, 1), profileName);
	}
	
	@Test(dependsOnMethods = "checkFiterOnMaintainCustomerScreen", alwaysRun = true)
	public void clickFiterToCloseOnMaintainCustomerScreen(){
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
	}
	
	@Test(dependsOnMethods = "clickFiterToCloseOnMaintainCustomerScreen", alwaysRun = true)
	public void clickSortIconOnMaintainCustomerScreen(){
		action.waitObjVisibleAndClick(By.xpath(Profiles.SORT_CUSTOMER_NAME));
	}
}


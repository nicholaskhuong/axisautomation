package com.abb.ventyx.axis.Authencation;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "472")
@Credentials(user = "axis_support_4@abb.com", password = "Testuser2")
public class AxisSupportDashboard extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String axisSupportTitleHeader = "Axis Support Dashboard";
	String axisAdmin = "Axis Administration";
	String axisConfiguration = "System Configuration";
	String customerMaintenance = "Customer Maintenance";
	String documentation = "Documentation";
	String userName = "Axis Support 4";
	String userID = "axis_support_4@abb.com";
	String axisSupportPortal = "Axis Supplier Portal";

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.assertTitleScreen(axisSupportTitleHeader);
	}

	@Test(dependsOnMethods = "openScreen")
	public void topRibbon() {
		// step 2
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AXIS_SUPPORT_PORTAL)).getText(), axisSupportPortal);
		// assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.LANGUAGE)).getText(),
		// "EN");
	}

	@Test(dependsOnMethods = "topRibbon")
	public void checkMenu() {
		// Step 3
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AXIS_ADMIN)).getText(), axisAdmin);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)).getText(), axisConfiguration);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE)).getText(), customerMaintenance);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.DOCUMENTATION)).getText(), documentation);
	}

	@Test(dependsOnMethods = "checkMenu")
	public void nameOfUser() {
		// step 4
		assertEquals(driver.findElement(By.id(UserPreferences.PROFILE_PANEL)).getText(), userName);
		action.clickBtn(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisible(By.id(ScreenObjects.SIGNOUT_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_LOGIN)).getText(), userName);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_ID)).getText(), userID);
	}

}

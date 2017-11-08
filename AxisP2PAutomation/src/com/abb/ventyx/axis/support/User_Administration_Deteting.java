package com.abb.ventyx.axis.support;


import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "205")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class User_Administration_Deteting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebElement index;
	String userUpdate = "Taumato1";
	String email = "Taumato1@abb.com";
	String newPassword = "Testuser2";
	int waitTime = 1000;
	//Step 1
	@Test
	public void openUserScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID));
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
	}
	
	@Test(dependsOnMethods = "openUserScreen", alwaysRun = true)
	public void clickFiterButton() {
		table = new TableFunction(driver);
		table.inputFilter(userUpdate, Users.USER_ID_FILTER , true);
		action.pause(3000);
	}
	
	@Test(dependsOnMethods = "clickFiterButton")
	public void clickDeleteButton() {
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index =  table.getCellObject(1, 6);
		action.pause(waitTime);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USERGROUP_CONFIRM);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_USERGROUP_CONFIRM);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.USER_DELETE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}
	
	@Test(dependsOnMethods = "clickDeleteButton", alwaysRun = true)
	public void logoutAndLoginWithNewUser() {
		action.pause(waitTime);
		action.signOut();
		action.pause(waitTime);
		action.signIn(email, newPassword);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.USERNOTFOUND);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}
	
}

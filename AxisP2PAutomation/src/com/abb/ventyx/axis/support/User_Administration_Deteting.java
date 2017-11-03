package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
@Credentials(user = "Toyota@abb.com", password = "Testuser2")
public class User_Administration_Deteting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebElement index;
	String userUpdate = "Taumato1";
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
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		WebElement filterPermissionName = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Users.USER_ID_FILTER)));
		filterPermissionName.sendKeys(userUpdate);
	}
	
	@Test(dependsOnMethods = "clickFiterButton", alwaysRun = true)
	public void clickDeleteButton() {
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

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
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Administration_Deteting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 3000;
	WebElement index;
	String userUpdate = "Taumato1";
	//Step 1
	@Test
	public void openUserScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID));
		action.waitObjVisibleAndClick(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID));
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "openUserScreen", alwaysRun = true)
	public void clickFiterButton() {
		table = new TableFunction(driver);
		action.pause(5000);
		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		WebElement filterPermissionName = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Users.USER_ID_FILTER)));
		filterPermissionName.sendKeys(userUpdate);
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "clickFiterButton", alwaysRun = true)
	public void clickDeleteButton() {
		table = new TableFunction(driver);
		action.clickHorizontalScrollBar();
		index =  table.getCellObject(1, 6);
		index.click();
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.NO_BTN_ID));
		action.pause(waitTime);
		index.click();
		action.pause(waitTime);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.USER_DELETE_SUCCESSFULLY);
	}
	
}

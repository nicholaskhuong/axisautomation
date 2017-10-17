package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
import com.ventyx.testng.TestDataKey;

@ALM(id = "631")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_Deleted_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	public static int i;
	@TestDataKey private final String CUSTOMERUSEREMAIL = "cuser2@abb.com";
	@TestDataKey private final String PASSWORD ="Testuser1";

	// Step 1 Select Users Sub Menu
	@Test
	public void selectUsersSubMenu() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement customerMaintenanceMenu = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU)));
		customerMaintenanceMenu.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(CustomerMenu.USERS_SUBMENU)));
		driver.findElement(By.cssSelector(CustomerMenu.USERS_SUBMENU)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		Thread.sleep(2000);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	@Test(dependsOnMethods = "selectUsersSubMenu")
	public void deleteCustomerAdmin() throws InterruptedException{
		assertEquals(driver.findElement(By.id(CustomerUsers.DELETE_ICON_ADMIN)).getAttribute("aria-disabled"), "true");
	}
	// Click Trash Bin icon of the user to test
	@Test(dependsOnMethods = "deleteCustomerAdmin")
	public void clickTrashBinIconOfUser() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		i = table.findRowByString1(3,CUSTOMERUSEREMAIL)-1;
		System.out.print(i+"Test test ");
		action.clickBtn(By.id("deleteItemBtn"+i));

		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(),Messages.DELETE_USER_CONFIRM);
		Thread.sleep(2000);
	}

	// Step 3 Choose No
	@Test(dependsOnMethods = "clickTrashBinIconOfUser")
	public void clickNoButton() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.CONFIRMATION)), false);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 4 Click Trash Bin and choose Yes
	@Test(dependsOnMethods = "clickNoButton")
	public void clickYesButton() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.clickBtn(By.id("deleteItemBtn"+i));
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(),Messages.USER_DELETE_SUCCESSFULLY);	
		assertEquals(table.isValueExisting(3, CUSTOMERUSEREMAIL), false);
	}

	// Step 5 check that can't login as the deleted user
	@Test(dependsOnMethods = "clickYesButton")
	public void loginAsTheDeletedUser() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.clickBtn(By.id(ScreenObjects.PROFILE_PANEL));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
		action.waitObjVisible(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID));
		action.inputTextField(LoginPageDefinition.USERNAME_TEXT_FIELD_ID, CUSTOMERUSEREMAIL);
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, PASSWORD);
		action.clickBtn(By.id(LoginPageDefinition.LOGIN_BUTTON_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_CSS)).getText(), Messages.USERNOTFOUND);
	}
}

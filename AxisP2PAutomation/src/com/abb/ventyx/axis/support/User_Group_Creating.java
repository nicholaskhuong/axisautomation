package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "162")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Group_Creating extends BaseTestCase {

	String SYSTEM_GROUP_NAME = "CUST_ADMIN";
	String CUSTOMER_NAME = "Tanya Customer 11";
	String USER_GROUP_NAME = "Manager Group";
	BaseDropDownList list;
	int row;
	ScreenAction action;
	TableFunction table;

	@Test
	public void checkScreen() throws Exception {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(UserGroup.CUSTOMERMAINTAINCE_MENU_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(UserGroup.USERGROUP_SUBMENU_CSS));

		action.waitObjVisible(By.id(UserGroup.SYSTEM_TAB_ID));

		WebElement screenTitle = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.id(UserGroup.SCREEN_TITLE_ID)));
		assertEquals(screenTitle.getText(), UserGroup.SCREEN_TITLE, "Title is wrong");
		String system_row0 = driver.findElement(By.id(UserGroup.ROW_ID + "0")).getText();
		assertEquals(system_row0, SYSTEM_GROUP_NAME);
		driver.findElement(By.id(UserGroup.USER_TAB_ID)).click();

	}

	// Step 2
	@Test(dependsOnMethods = "checkScreen")
	public void addWithoutCustomer() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.xpath(UserGroup.ADD_XPATH));

		WebElement warningMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(ScreenObjects.WARNING_MESSAGE_XPATH)));
		assertEquals(warningMessage.getText(), Messages.USERGROUP_SELECT_CUSTOMER);
	}

	// Step 3
	@Test(dependsOnMethods = "addWithoutCustomer")
	public void addWithSelectedCustomerNoUserGroupName() throws Exception {
		action = new ScreenAction(driver);
		WebElement customer = driver.findElement(By.className(UserGroup.CUSTOMER_CLASS));
		customer.sendKeys(CUSTOMER_NAME);

		/*
		 * list = new BaseDropDownList(driver,
		 * UserGroup.LIST_CSS); row =
		 * list.findItemInDropDownList(CUSTOMER_NAME); WebElement rowClick =
		 * (new WebDriverWait(driver, 60))
		 * .until(ExpectedConditions.presenceOfElementLocated(By
		 * .cssSelector(UserGroup.LIST_CSS +
		 * "> tbody > tr:nth-child(" + (row - 1) + ") > td")));
		 * rowClick.click(); (new WebDriverWait(driver, 15)).until(Exgit
		 * pectedConditions .presenceOfElementLocated(By
		 * .id(UserGroup.ROW_ID + "0")));
		 */
		action.selectStatus(ScreenObjects.DROPDOWNLIST_CSS, CUSTOMER_NAME);

		WebElement addBtn = driver.findElement(By.xpath(UserGroup.ADD_XPATH));
		addBtn.click();
		action.waitObjVisible(By.id(UserGroup.USERGROUP_NAME_ID));
		WebElement screenTitle = driver.findElement(By.id(UserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), UserGroup.SCREEN_CREATE_TITLE, "Title is wrong");
		action.inputTextField(UserGroup.USERGROUP_NAME_ID, USER_GROUP_NAME);
		driver.findElement(By.id(UserGroup.SAVE_ID)).click();
		WebElement errorMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.WARNING_MESSAGE_CSS)));
		assertEquals(errorMessage.getText(), Messages.EMPTY_PERMISSION);
	}

	// Step 4
	@Test(dependsOnMethods = "addWithSelectedCustomerNoUserGroupName")
	public void addWithSelectedCustomerHasUserGroupName() {

		WebElement userGroupName = driver.findElement(By.id(UserGroup.USERGROUP_NAME_ID));
		userGroupName.sendKeys(USER_GROUP_NAME);
		List<WebElement> list = driver.findElements(By.className(UserGroup.PERMISSION_CLASS));
		list.get(0).click();
		driver.findElement(By.id(UserGroup.SAVE_ID)).click();
		WebElement sucessMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.SUCCESS_MESSAGE)));
		assertEquals(sucessMessage.getText(), Messages.USERGROUP_CREATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "addWithSelectedCustomerHasUserGroupName")
	public void cancelWithoutdata() throws InterruptedException {
		WebElement addBtn = driver.findElement(By.xpath(UserGroup.ADD_XPATH));
		addBtn.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(UserGroup.USERGROUP_NAME_ID)));
		driver.findElement(By.id(UserGroup.CANCEL_ID)).click();

		(new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id(UserGroup.USER_CUSTOMER_ID)));
		WebElement screenTitle = driver.findElement(By.id(UserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), UserGroup.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "cancelWithoutdata")
	public void cancelClickYes() {

		WebElement addBtn = driver.findElement(By.xpath(UserGroup.ADD_XPATH));
		addBtn.click();
		WebElement userGroupName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.id(UserGroup.USERGROUP_NAME_ID)));
		userGroupName.sendKeys(USER_GROUP_NAME);
		driver.findElement(By.id(UserGroup.CANCEL_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.CONFIRMATION)));
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id(UserGroup.USER_CUSTOMER_ID)));
		WebElement screenTitle = driver.findElement(By.id(UserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), UserGroup.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "cancelClickYes")
	public void cancelClickNo() {

		WebElement addBtn = driver.findElement(By.xpath(UserGroup.ADD_XPATH));
		addBtn.click();
		WebElement userGroupName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.id(UserGroup.USERGROUP_NAME_ID)));
		userGroupName.sendKeys(USER_GROUP_NAME);
		driver.findElement(By.id(UserGroup.CANCEL_ID)).click();
		WebElement msgDialog = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
		WebElement screenTitle = driver.findElement(By.id(UserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), UserGroup.SCREEN_CREATE_TITLE);

	}

}

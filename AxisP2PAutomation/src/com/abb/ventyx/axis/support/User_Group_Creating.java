package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportCustomerUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObject;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;

@ALM(id = "162")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class User_Group_Creating extends BaseTestCase {

	String SYSTEM_GROUP_NAME = "CUST_ADMIN";
	String CUSTOMER_NAME = "Tanya Customer 11";
	String USER_GROUP_NAME = "Manager Group";
	BaseDropDownList list;
	int row;

	@Test
	public void checkScreen() throws Exception {
		WebElement customerConfiguration = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.CUSTOMERMAINTAINCE_MENU_ID)));
		customerConfiguration.click();
		WebElement userGroupsMenu = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_SUBMENU_ID)));
		userGroupsMenu.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.SYSTEM_TAB_ID)));
		WebElement screenTitle = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID)));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_TITLE, "Title is wrong");
		String system_row0 = driver.findElement(
				By.id(AxisSupportCustomerUserGroup.ROW_ID + "0")).getText();
		assertEquals(system_row0, SYSTEM_GROUP_NAME);
		driver.findElement(By.id(AxisSupportCustomerUserGroup.USER_TAB_ID))
				.click();

	}

	@Test(dependsOnMethods = "checkScreen")
	public void addWithoutCustomer() {
		WebElement addBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(AxisSupportCustomerUserGroup.ADD_XPATH)));
		addBtn.click();
		WebElement warningMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(ScreenObject.WARNING_MESSAGE_XPATH)));
		assertEquals(warningMessage.getText(),
				Messages.USERGROUP_SELECT_CUSTOMER);
	}

	@Test(dependsOnMethods = "addWithoutCustomer")
	public void addWithSelectedCustomerNoUserGroupName() throws Exception {
		WebElement customer = driver.findElement(By
				.className(AxisSupportCustomerUserGroup.CUSTOMER_CLASS));
		customer.sendKeys(CUSTOMER_NAME);
		list = new BaseDropDownList(driver,
				AxisSupportCustomerUserGroup.LIST_CSS);
		row = list.findItemInDropDownList(CUSTOMER_NAME);
		WebElement rowClick = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisSupportCustomerUserGroup.LIST_CSS
								+ "> tbody > tr:nth-child(" + (row - 1)
								+ ") > td")));
		rowClick.click();
		(new WebDriverWait(driver, 15)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.ROW_ID + "0")));
		WebElement addBtn = driver.findElement(By
				.xpath(AxisSupportCustomerUserGroup.ADD_XPATH));
		addBtn.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_CREATE_TITLE,
				"Title is wrong");
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement errorMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(errorMessage.getText(), Messages.ENTER_MANDATORY_FIELDS);
	}

	@Test(dependsOnMethods = "addWithSelectedCustomerNoUserGroupName")
	public void addWithSelectedCustomerHasUserGroupName() {

		WebElement userGroupName = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID));
		userGroupName.sendKeys(USER_GROUP_NAME);
		List<WebElement> list = driver.findElements(By
				.className(AxisSupportCustomerUserGroup.PERMISSION_CLASS));
		list.get(0).click();
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement sucessMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.SUCCESS_MESSAGE)));
		assertEquals(sucessMessage.getText(),
				Messages.USERGROUP_CREATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "addWithSelectedCustomerHasUserGroupName")
	public void cancelWithoutdata() throws InterruptedException {
		WebElement addBtn = driver.findElement(By
				.xpath(AxisSupportCustomerUserGroup.ADD_XPATH));
		addBtn.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		driver.findElement(By.id(AxisSupportCustomerUserGroup.CANCEL_ID))
				.click();

		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USER_CUSTOMER_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "cancelWithoutdata")
	public void cancelClickYes() {

		WebElement addBtn = driver.findElement(By
				.xpath(AxisSupportCustomerUserGroup.ADD_XPATH));
		addBtn.click();
		WebElement userGroupName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		userGroupName.sendKeys(USER_GROUP_NAME);
		driver.findElement(By.id(AxisSupportCustomerUserGroup.CANCEL_ID))
				.click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		driver.findElement(By.id(ScreenObject.YES_BTN_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USER_CUSTOMER_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "cancelClickYes")
	public void cancelClickNo() {

		WebElement addBtn = driver.findElement(By
				.xpath(AxisSupportCustomerUserGroup.ADD_XPATH));
		addBtn.click();
		WebElement userGroupName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		userGroupName.sendKeys(USER_GROUP_NAME);
		driver.findElement(By.id(AxisSupportCustomerUserGroup.CANCEL_ID))
				.click();
		WebElement msgDialog = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObject.NO_BTN_ID)).click();
		WebElement screenTitle = driver.findElement(By
				.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisSupportCustomerUserGroup.SCREEN_CREATE_TITLE);

	}

}

package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;

@ALM(id = "600")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Profile_Create extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	int row;
	int waitTime = 3000;
	
	@Test
	public void openMaintainCustomerDefinedProfilesScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By
				.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickAddButton(){
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
	}
	
	@Test(dependsOnMethods = "clickAddButton", alwaysRun = true)
	public void inputProfileNameandCustomerName() {
		action.pause(waitTime);
		action.inputTextField(Profiles.PROFILE_NAME_ID, "Per4");
		WebElement customer = driver.findElement(By.className(Profiles.CUSTOMER_CLASS));
		customer.sendKeys("Customer Perla");
		list = new BaseDropDownList(driver,Profiles.LIST_CSS);
		row = list.findItemInDropDownList("Customer Perla");
		WebElement rowClick = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(Profiles.LIST_CSS + "> tbody > tr:nth-child(" + (row - 1) + ") > td")));
		rowClick.click();
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ERROR_MESSAGE);
		action.waitObjVisible(By.id(Profiles.SAVE_BTN));
	}
	
	@Test(dependsOnMethods = "inputProfileNameandCustomerName", alwaysRun = true)
	public void slelectAuthorisedDocumentTypes() {
		action.pause(waitTime);
		action.clickCheckBoxN(2);
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_SUCCESSFULLY);
	}
	
	@Test(dependsOnMethods = "slelectAuthorisedDocumentTypes", alwaysRun = true)
	public void clickAddButton2(){
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
	}
	
	@Test(dependsOnMethods = "clickAddButton2", alwaysRun = true)
	public void inputProfileNameandCustomerNameOnCreatePage() {
		action.pause(waitTime);
		action.inputTextField(Profiles.PROFILE_NAME_ID, "Per3");
		action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
		action.cancelClickNo(Profiles.TITLE_MAINTAIN_CUSTOMER);
	}
	@Test(dependsOnMethods = "inputProfileNameandCustomerNameOnCreatePage", alwaysRun = true)
	public void inputMissingAllFields() {
		action.inputTextField(Profiles.PROFILE_NAME_ID, "");
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.MESSAGE_MISSING_ALL_FIELD);
		}
	
	@Test(dependsOnMethods = "inputMissingAllFields", alwaysRun = true)
	public void inputMissingCustomerName() {
		action.inputTextField(Profiles.PROFILE_NAME_ID, "Per3");
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.MESSAGE_MISSING_CUSTOMER_NAME);
		}
	
	@Test(dependsOnMethods = "inputMissingCustomerName", alwaysRun = true)
	public void inputMissingProfileName() {
		action.inputTextField(Profiles.PROFILE_NAME_ID, "");
		WebElement customer = driver.findElement(By.className(Profiles.CUSTOMER_CLASS));
		customer.sendKeys("Customer Perla");
		list = new BaseDropDownList(driver,Profiles.LIST_CSS);
		row = list.findItemInDropDownList("Customer Perla");
		WebElement rowClick = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(Profiles.LIST_CSS + "> tbody > tr:nth-child(" + (row - 1) + ") > td")));
		rowClick.click();
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.MESSAGE_MISSING_PROFILE_FIELD);
		}
	
	@Test(dependsOnMethods = "inputMissingProfileName", alwaysRun = true)
	public void inputDuplicationProfileName() {
		action.pause(waitTime);
		action.inputTextField(Profiles.PROFILE_NAME_ID, "Per4");
		action.clickCheckBoxN(2);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.pause(waitTime);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
				Messages.MESSAGE_DUPLICATED_PROFILE_NAME);
		}
	}


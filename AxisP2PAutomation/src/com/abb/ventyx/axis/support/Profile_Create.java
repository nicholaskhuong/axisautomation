package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "600")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Profile_Create extends BaseTestCase {
 ScreenAction action;
 BaseDropDownList list;
 int row;
 String nameCustomer = "QATest2";
 public static String profileName = "Profile1";
 int milliseconds = 1000;
 TableFunction table;
 WebElement saveBtn;

 @Test
 public void openMaintainCustomerDefinedProfilesScreen() {
  action = new ScreenAction(driver);
  table = new TableFunction(driver);
  action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
  action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
  action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
  assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Profiles.TITLE_PROFILES);
  action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
  assertEquals(table.getValueTableHeader(1), "Customer Name");
  assertEquals(table.getValueTableHeader(2), "Profile Name");
  assertEquals(table.getValueTableHeader(3), "Document Types");

 }

 @Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
 public void clickAddButton() {
  action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
  action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
  assertEquals(driver.findElement(By.cssSelector(Profiles.PROFILES_CSS)).getText(), Profiles.CREATE_PROFILES);
 }

 @Test(dependsOnMethods = "clickAddButton", alwaysRun = true)
 public void inputProfileNameandCustomerName() {
  Random rand = new Random();
  long drand = (long) (rand.nextDouble() * 10000L);
  profileName = String.format("Profile1 %s", drand);
  action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
  action.inputTextField(Profiles.PROFILE_NAME_ID, profileName);
  action.waitObjVisible(By.className(Profiles.CUSTOMER_CLASS));
  WebElement customerClass = driver.findElement(By.className(Profiles.CUSTOMER_CLASS));
  customerClass.sendKeys(nameCustomer);
  list = new BaseDropDownList(driver, Profiles.LIST_CSS);
  row = list.findItemInDropDownList(nameCustomer);
  WebElement rowClick = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Profiles.LIST_CSS
    + "> tbody > tr:nth-child(" + (row - 1) + ") > td")));
  rowClick.click();
  action.waitObjVisible(By.id(Profiles.SAVE_BTN));
  action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
  action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ERROR_MESSAGE);
  action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
 }

 @Test(dependsOnMethods = "inputProfileNameandCustomerName", alwaysRun = true)
 public void slelectAuthorisedDocumentTypes() {
  action.clickCheckBoxN(4);
  saveBtn = driver.findElement(By.id(Profiles.SAVE_BTN));
  ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
  action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.MESSAGE_SUCCESSFULLY);
  action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
 }

 @Test(dependsOnMethods = "slelectAuthorisedDocumentTypes", alwaysRun = true)
 public void clickAddButton2() {
  action.waitObjVisibleAndClick(By.cssSelector(Profiles.ADD_PROFILE));
 }

 @Test(dependsOnMethods = "clickAddButton2", alwaysRun = true)
 public void inputProfileNameandCustomerNameOnCreatePage() {
  action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
  action.inputTextField(Profiles.PROFILE_NAME_ID, "Profile4");
  action.pause(800);
  action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
  action.cancelClickNo(Profiles.TITLE_MAINTAIN_CUSTOMER);
 }

 @Test(dependsOnMethods = "inputProfileNameandCustomerNameOnCreatePage", alwaysRun = true)
 public void inputMissingAllFields() {
  action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
  action.inputTextField(Profiles.PROFILE_NAME_ID, "");
  action.pause(800);
  saveBtn = driver.findElement(By.id(Profiles.SAVE_BTN));
  ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
  action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.MESSAGE_MISSING_ALL_FIELD);
  action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
  ((JavascriptExecutor) driver).executeScript("window.focus();");
 }

 @Test(dependsOnMethods = "inputMissingAllFields", alwaysRun = true)
 public void inputMissingCustomerName() {
  action.inputTextField(Profiles.PROFILE_NAME_ID, "Profile4");
  action.clickCheckBoxN(0);
  saveBtn = driver.findElement(By.id(Profiles.SAVE_BTN));
  ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
  action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.MESSAGE_MISSING_CUSTOMER_NAME);
  ((JavascriptExecutor) driver).executeScript("window.focus();");
 }

 @Test(dependsOnMethods = "inputMissingCustomerName")
 public void inputMissingProfileName() {
  action.inputTextField(Profiles.PROFILE_NAME_ID, "");
  action.waitObjVisible(By.className(Profiles.CUSTOMER_CLASS));
  WebElement customerClass = driver.findElement(By.className(Profiles.CUSTOMER_CLASS));
  customerClass.sendKeys(nameCustomer);
  list = new BaseDropDownList(driver, Profiles.LIST_CSS);
  row = list.findItemInDropDownList(nameCustomer);
  WebElement rowClick = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Profiles.LIST_CSS
    + "> tbody > tr:nth-child(" + (row - 1) + ") > td")));
  rowClick.click();
  action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
  action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.MESSAGE_MISSING_PROFILE_FIELD);
  ((JavascriptExecutor) driver).executeScript("window.focus();");
 }

 @Test(dependsOnMethods = "inputMissingProfileName")
 public void inputDuplicationProfileName() {
  action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
  action.inputTextField(Profiles.PROFILE_NAME_ID, profileName);
  action.clickCheckBoxN(0);
  action.clickCheckBoxN(4);
  saveBtn = driver.findElement(By.id(Profiles.SAVE_BTN));
  ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
  action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.MESSAGE_DUPLICATED_PROFILE_NAME);
  action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
 }
}
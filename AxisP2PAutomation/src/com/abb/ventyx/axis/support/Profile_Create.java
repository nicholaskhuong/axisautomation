package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.security.acl.Permission;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportCustomerUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ProfilesAction;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
import com.ventyx.testng.TestDataKey;

@ALM(id = "106")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Profile_Create extends BaseTestCase {
	public static int numberOfRowsBeforeAdding;
	public static int numberOfRowsAfterAdding;
	@TestDataKey private final String PROFILE_NAME = "ASN";
	@TestDataKey private final String DOCUMENT_TYPES = "Advance Shipping Notice";
	@TestDataKey private final String PurchaseorderType = "PurchaseOrder";
	@TestDataKey private final String USER_TYPE_A = "CSA";
	@TestDataKey private final String ADDProfileHEADER = "Add Profile";
	@TestDataKey private final String MAINTAINProfileHEADER = "Maintain Profiles";
	TableFunction table;
	ScreenAction action;
	BaseDropDownList list;
	int row;
	ProfilesAction profileAction = new ProfilesAction(driver);
	// Step 1
	@Test
	public void openMaintainCustomerDefinedProfilesScreen() throws InterruptedException{
		
		profileAction.clickSystemConfigurationMenu();
		profileAction.clickProfileSubMenu();
//		action.waitObjVisible(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickAddButton() throws InterruptedException{
		ProfilesAction profileAction = new ProfilesAction(driver);
		profileAction.clickAddButton();
//		action.waitObjInvisible(By.cssSelector(Profiles.TITLE_CREATE));
//		profileAction.displayProfileWindowPopUp();
	}
	
	@Test(dependsOnMethods = "clickAddButton", alwaysRun = true)
	public void selectDropDownWithoutProfileName() {
		
		WebElement profileName = driver.findElement(By.id(Profiles.PROFILE_NAME_ID));
		profileName.sendKeys("Perla");
		WebElement customer = driver.findElement(By.className(Profiles.CUSTOMER_CLASS));
		customer.sendKeys("Customer Perla");
		list = new BaseDropDownList(driver,Profiles.LIST_CSS);
		row = list.findItemInDropDownList("Customer Perla");
		WebElement rowClick = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(Profiles.LIST_CSS + "> tbody > tr:nth-child(" + (row - 1) + ") > td")));
		rowClick.click();
		
		
		}
	
//	// Step 2, 3, 4
//	@Test(dependsOnMethods="openMaintainProfileScreen")
//	public void createProfilewithValidValue() throws InterruptedException{
//		ProfilesAction ProfilesAction = new ProfilesAction(driver);
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		ProfilesAction.clickAddButton();
//		ProfilesAction.enterProfileName(Profile_NAME_A);
//		ProfilesAction.selectDocTypebyText("Purchase Orders");
//		Thread.sleep(200);
//		ProfilesAction.selectUserType(Profiles.AXIS_ADMIN);
//		ProfilesAction.selectUserType(Profiles.CUSTOMER);
//		ProfilesAction.selectUserType(Profiles.SUPPLIER);
//
//		ProfilesAction.clickSaveButtonOnAddPermisisonPopUp();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By
//				.cssSelector(Messages.Profile_CREATED_SUCCESSFULLY_CSS)));
//		assertEquals(
//				driver.findElement(
//						By.cssSelector(Messages.Profile_CREATED_SUCCESSFULLY_CSS))
//						.getText(), Messages.Profile_CREATED_SUCCESSFULLY);
//		// Filter
//		ProfilesAction.enterValueTofilterProfile(Profile_NAME_A);
//		ProfilesAction.filterProfilebyDocumentType("PurchaseOrder");
//		Thread.sleep(2000);
//		numberOfRowsAfterAdding = ProfilesAction.countRow(Profiles.TABLEBODY);
//
//		assertEquals(numberOfRowsBeforeAdding + 1, numberOfRowsAfterAdding);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By
//				.cssSelector(Profiles.PNROW1)));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By
//				.cssSelector(Profiles.UTROW1)));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By
//				.cssSelector(Profiles.DTROW1)));
//
//		assertEquals(
//				driver.findElement(
//						By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["
//								+ numberOfRowsAfterAdding + "]//td[2]"))
//								.getText(), PurchaseorderType);
//		assertEquals(
//				driver.findElement(
//						By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["
//								+ numberOfRowsAfterAdding + "]//td[3]"))
//								.getText(), Profile_NAME_A);
//		assertEquals(
//				driver.findElement(
//						By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["
//								+ numberOfRowsAfterAdding + "]//td[4]"))
//								.getText(), USER_TYPE_A);
//		Thread.sleep(1000);
//	}
//	// Step 5, 6, 7
//	@Test(dependsOnMethods="createProfilewithValidValue")
//	public void addPermissonWithoutMandatoryField() throws InterruptedException{
//		ProfilesAction ProfilesAction = new ProfilesAction(driver);
//		ProfilesAction.clickAddButton();
//
//		// Step 6
//		ProfilesAction.clickSaveButtonOnAddPermisisonPopUp();
//		Thread.sleep(1000);
//		assertEquals(
//				driver.findElement(
//						By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
//						.getText(), Messages.EMPTYProfileNAME);
//
//		// Step 7
//		ProfilesAction.enterProfileName("ECHO 1");
//		ProfilesAction.clickSaveButtonOnAddPermisisonPopUp();
//		assertEquals(
//				driver.findElement(
//						By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS))
//						.getText(), Messages.EMPTYUSERTYPE);
//		Thread.sleep(2000);
//	}
//	// Step 8, 9, 10
//	@Test(dependsOnMethods="addPermissonWithoutMandatoryField")
//	public void checkUnsavedChangesDialog() throws InterruptedException{
//		// Step 8
//		ProfilesAction ProfilesAction = new ProfilesAction(driver);
//		ScreenAction action = new ScreenAction(driver);
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		ProfilesAction.clickCancelButtonOnAddPermisisonPopUp();
//		assertEquals(
//				driver.findElement(
//						By.cssSelector(Profiles.CONFIRMATION_OF_DELETION))
//						.getText(), Messages.UNSAVED_CHANGE);
//
//		// Step 9
//		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
//		Thread.sleep(1000);
//		assertEquals(
//				driver.findElement(
//						By.cssSelector(Profiles.ProfileWINDOWHEADER))
//						.getText(), ADDProfileHEADER);
//
//		// Step 10
//		ProfilesAction.clickCancelButtonOnAddPermisisonPopUp();
//		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
//		Thread.sleep(1000);
//		assertEquals(
//				driver.findElement(By.cssSelector(Profiles.ProfileHEADER))
//				.getText(), MAINTAINProfileHEADER);
//
//		// Step 11
//		ProfilesAction.clickAddButton();
//		ProfilesAction.clickCancelButtonOnAddPermisisonPopUp();
//
//		assertEquals(action.isElementPresent(By.cssSelector(Profiles.CONFIRMATION_OF_DELETION)), false);
//		assertEquals(action.isElementPresent(By.cssSelector(Profiles.ProfileWINDOWHEADER)), false);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By
//				.cssSelector(Profiles.ProfileHEADER)));
//		assertEquals(
//				driver.findElement(By.cssSelector(Profiles.ProfileHEADER))
//				.getText(), MAINTAINProfileHEADER);
	}


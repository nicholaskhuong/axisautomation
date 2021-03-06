package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

@ALM(id = "622")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Profile_Update_Customer_Defined_Step07 extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	TableFunction table;
	TableFunction table1;
	int row;
	int waitTime = 1000;
	public static String profileNameEdited2 = "Profile3";
	WebElement index;
	String userLogin = "admin_customer22@abb.com";
	String userPassword = "Testuser1";
	String adminLogin = "axis_support@abb.com";
	String adminPassword = "Testuser1";
	String profileName_AccountCustomer = "POProfile";

	// Step1
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

	// Step2
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.pause(waitTime);
		table.clickFilterAndInputWithColumn(Profile_Update_Customer_Defined_Steps01_06.profileNameEdited, Profiles.PROFILE_NAME_FILTER, true);
	}

	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickPencilIconOnMaintainCustomerScreen() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 5);
		index.click();
		action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
		assertEquals(driver.findElement(By.cssSelector(Profiles.PROFILES_CSS)).getText(), Profiles.MODIFY_PROFILES);
	}

	@Test(dependsOnMethods = "clickPencilIconOnMaintainCustomerScreen", alwaysRun = true)
	public void checkStatusProfileAndCustomerName() {
		action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
		WebElement profileName = driver.findElement(By.id(Profiles.PROFILE_NAME_ID));
		profileName.isEnabled();
		action.waitObjVisible(By.id(Profiles.CUSTOMER_NAME));
		WebElement customerName = driver.findElement(By.id(Profiles.CUSTOMER_NAME));
		customerName.isDisplayed();
	}

	@Test(dependsOnMethods = "checkStatusProfileAndCustomerName", alwaysRun = true)
	public void editProfileNameonModifyProfileSreen() {
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000L);
		profileNameEdited2 = String.format("Profile3 %s", drand);
		action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
		WebElement fileName = driver.findElement(By.id(Profiles.PROFILE_NAME_ID));
		fileName.clear();
		action.pause(waitTime);
		fileName.sendKeys(profileNameEdited2);
		action.pause(2000);
		action.clickCheckBoxN(2);
	}

	@Test(dependsOnMethods = "editProfileNameonModifyProfileSreen", alwaysRun = true)
	public void clickSaveButtonAndDisplayMessageSuccessfully() {
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}

	// Step3
	@Test(dependsOnMethods = "clickSaveButtonAndDisplayMessageSuccessfully", alwaysRun = true)
	public void logoutFromMaintainCustomerScreen1() {
		action.pause(waitTime);
		action.signOut();
	}

	@Test(dependsOnMethods = "logoutFromMaintainCustomerScreen1", alwaysRun = true)
	public void loginWithAccountCustomer1() {
		action.signIn(userLogin, userPassword);
		action.pause(waitTime);
	}

	@Test(dependsOnMethods = "loginWithAccountCustomer1", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
		action.pause(waitTime);
	}

	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer", alwaysRun = true)
	public void clickEditIconOnMaintainCustomerScreen() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(2, 4);
		index.click();
		action.waitObjVisibleAndClick(By.id(Profiles.CANCEL_BTN));
	}

	// Step 5
	@Test(dependsOnMethods = "clickEditIconOnMaintainCustomerScreen", alwaysRun = true)
	public void logoutfromAccountCustomer() {
		action.signOut();
	}

	@Test(dependsOnMethods = "logoutfromAccountCustomer", alwaysRun = true)
	public void loginfromAccountCustomer() {
		action.signIn(adminLogin, adminPassword);
	}

	@Test(dependsOnMethods = "loginfromAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerDefinedProfilesScreenAgain() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}

	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreenAgain", alwaysRun = true)
	public void clickFiterButtonAgainOnMaintainCustomerScreen() {
		action.pause(waitTime);
		table.clickFilterAndInputWithColumn(Profile_Update_Customer_Defined_Step07.profileNameEdited2, Profiles.PROFILE_NAME_FILTER, true);
	}

	@Test(dependsOnMethods = "clickFiterButtonAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void clickEditIconAgainOnMaintainCustomerScreen() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 5);
		index.click();
	}

	@Test(dependsOnMethods = "clickEditIconAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void editAuthorisedDocumentTypes() {
		action.waitObjVisibleAndClick(By.id("showConfigBtn-PurchaseOrder"));
		action.pause(4000);
		List<WebElement> checkBoxIndex0 = driver.findElements(By.xpath("//*[@id='configurationGrid-PurchaseOrder']/div[3]/table/tbody/tr[1]/td[1]"));
		checkBoxIndex0.get(0).click();
	}

	@Test(dependsOnMethods = "editAuthorisedDocumentTypes", alwaysRun = true)
	public void clickSaveBtnAndDisplayMessageSuccessfully() {
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}
}

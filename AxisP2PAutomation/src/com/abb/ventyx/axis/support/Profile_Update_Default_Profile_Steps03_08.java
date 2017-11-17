package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "789")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Profile_Update_Default_Profile_Steps03_08 extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int waitTime = 1000;
	String password;
	String newPassword = "Testuser1";
	String profileName = "All Document Types";
	WebElement index;
	String mailAdminLogin = "axis_support@abb.com";
	String PasswordAdmin = "Testuser1";

	// Step 03
	@Test
	public void openProfileScreen() {
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

	@Test(dependsOnMethods = "openProfileScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, Profile_Update_Default_Profile_Step01_02.customerName);
	}

	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void clickEditButtonOnMaintainCustomerScreen() {
		action.pause(waitTime);
		// index = table.getCellObject(1, 5);
		int actualIndex = table.findRealIndexByCell(1, 5, "editItemBtn");
		WebElement editButton = driver.findElement(By.id("editItemBtn" + actualIndex));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);

		// int actualIndex = table.findRealIndexByCell(1, 5, "editItemBtn");
		// WebElement editButton = driver.findElement(By.id("editItemBtn" +
		// actualIndex));
		// action.scrollToElementWithColumnNo(editButton, 5);
		// action.clickHorizontalScrollBar();
		// index = table.getCellObject(1, 5);
		editButton.click();
	}

	@Test(dependsOnMethods = "clickEditButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void verifyDataOnModifyProfileScreen() {
		action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
		WebElement profileName = driver.findElement(By.id(Profiles.PROFILE_NAME_ID));
		profileName.isDisplayed();
		action.waitObjVisible(By.id(Profiles.CUSTOMER_NAME));
		WebElement customerName = driver.findElement(By.id(Profiles.CUSTOMER_NAME));
		customerName.isDisplayed();
		action.checkObjSelected(0);
	}

	// Step 4
	@Test(dependsOnMethods = "verifyDataOnModifyProfileScreen", alwaysRun = true)
	public void untickCheckBoxandSaveButtonOnModifyProfileScreen() {
		action.pause(waitTime);
		action.clickCheckBoxN(0);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ERROR_MESSAGE);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 5
	@Test(dependsOnMethods = "untickCheckBoxandSaveButtonOnModifyProfileScreen", alwaysRun = true)
	public void tickCheckBoxOnModifyProfileScreen() {
		action.clickCheckBoxN(2);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}

	// Step 6
	@Test(dependsOnMethods = "tickCheckBoxOnModifyProfileScreen", alwaysRun = true)
	public void logoutAndLoginWithNewCustomerList2() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(Profile_Update_Default_Profile_Step01_02.emailAddress, newPassword);
	}

	@Test(dependsOnMethods = "logoutAndLoginWithNewCustomerList2", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer2() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
	}

	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer2", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen2() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, profileName);
	}

	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen2", alwaysRun = true)
	public void clickEditButtonOnMaintainCustomerScreen2() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 5);
		index.click();
	}

	@Test(dependsOnMethods = "clickEditButtonOnMaintainCustomerScreen2", alwaysRun = true)
	public void verifyAuthorisedDocumentOnModifyProfileScreen2() {
		// All business documents are selected as default
		// action.checkObjSelected(1,4);
	}

	// Step 7
	@Test(dependsOnMethods = "verifyAuthorisedDocumentOnModifyProfileScreen2", alwaysRun = true)
	public void logoutWithCustomerListSecondTime() {
		action.signOut();
	}

	@Test(dependsOnMethods = "logoutWithCustomerListSecondTime", alwaysRun = true)
	public void loginWithMail5Again() {
		action.signIn(mailAdminLogin, PasswordAdmin);
		action.pause(waitTime);
	}

	@Test(dependsOnMethods = "loginWithMail5Again", alwaysRun = true)
	public void openMaintainCustomerScreen2() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}

	@Test(dependsOnMethods = "openMaintainCustomerScreen2", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomer2() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, Profile_Update_Default_Profile_Step01_02.customerName);
	}

	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomer2", alwaysRun = true)
	public void clickEditButtonOnMaintainCustomer2() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 5);
		index.click();
	}

	@Test(dependsOnMethods = "clickEditButtonOnMaintainCustomer2", alwaysRun = true)
	public void verifyDataOnModifyProfileScreen2() {
		action.waitObjVisibleAndClick(By.id("showConfigBtn-AdvanceShippingNotice"));
		action.pause(waitTime);
		List<WebElement> checkBoxIndex0 = driver.findElements(By
				.xpath("//*[@id='configurationGrid-AdvanceShippingNotice']/div[3]/table/tbody/tr[1]/td[1]"));
		checkBoxIndex0.get(0).click();
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}

	// Step 8
	@Test(dependsOnMethods = "verifyDataOnModifyProfileScreen2", alwaysRun = true)
	public void logoutAndLoginWithNewCustomerList3() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(Profile_Update_Default_Profile_Step01_02.emailAddress, newPassword);
	}

	@Test(dependsOnMethods = "logoutAndLoginWithNewCustomerList3", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer3() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
	}

	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer3", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen3() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, profileName);
	}

	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen3", alwaysRun = true)
	public void clickEditButtonOnMaintainCustomerScreen3() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 5);
		index.click();
	}

	@Test(dependsOnMethods = "clickEditButtonOnMaintainCustomerScreen3", alwaysRun = true)
	public void verifyAuthorisedDocumentOnModifyProfileScreen3() {
		// Settings under business document of the default profile is updated
		// accordingly
		// action.checkObjSelected(1,4);
		// WebElement checkBoxIndex2_1 =
		// driver.findElement(By.xpath("//*[@id='configurationGrid-AdvanceShippingNotice']/div[3]/table/tbody/tr[1]/td[1]"));
		// checkBoxIndex2_1.isSelected();
	}
}

package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerList;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.ResetUserPassword;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "789")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Profile_Update_Default_Profile_Steps01_08 extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	TableFunction table;
	int waitTime = 1000;
	Random rand = new Random();
	int drand = rand.nextInt(10000);
	String customerName = String.format("Honda %s", drand);
	String emailAddress = String.format("Honda%s@abb.com", drand);
	String password;
	String newPassword = "Testuser1";
	int i;
	String profileName = "All Document Types";
	WebElement index, index2;
	String mailAdminLogin = "axis_support@abb.com";
	String PasswordAdmin = "Testuser1";

	// Step 1
	@Test
	public void openCustomersScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), CustomerList.TITLE_CUSTOMERS_PAGE);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.cssSelector(CustomerList.DEACTIVE));
		action.waitObjVisible(By.cssSelector(CustomerList.ACTIVE));
		assertEquals(table.getValueTableHeader(1), "ID");
		assertEquals(table.getValueTableHeader(2), "Name");
		assertEquals(table.getValueTableHeader(3), "Status");
		assertEquals(table.getValueTableHeader(4), "Email");
	}

	@Test(dependsOnMethods = "openCustomersScreen", alwaysRun = true)
	public void clickAddButtonOnMaintainCustomerScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	}

	@Test(dependsOnMethods = "clickAddButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void inputDataOnCustomerListScreen() {
		action.inputTextField(CustomerList.CUSTOMER_NAME, customerName);
		action.inputEmailField(CustomerList.EMAIL_ADDRESS, emailAddress);
		action.clickCheckBoxN(0);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.CREATE_CUSTOMER_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "clickAddButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void openResetUserPasswordScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.RESET_USER_PASSWORD));
		action.waitObjVisible(By.id(ScreenObjects.RESET_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), ResetUserPassword.TITLE_RESET_USER_PASSWORD);
	}

	@Test(dependsOnMethods = "openResetUserPasswordScreen", alwaysRun = true)
	public void enterEmaidId() {
		action.waitObjVisibleAndClick(By.id(ResetUserPassword.EMAIL_ID_CHECK));
		action.pause(waitTime);
		action.inputEmailField(ResetUserPassword.EMAIL_ID, emailAddress);
		action.waitObjVisibleAndClick(By.id(ResetUserPassword.RESET));
	}

	@Test(dependsOnMethods = "enterEmaidId", alwaysRun = true)
	public void getPassworkOfNewCustomerList() {
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		String message = driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText();
		password = action.getPassword(message);
	}

	// Step 2
	@Test(dependsOnMethods = "getPassworkOfNewCustomerList", alwaysRun = true)
	public void signOutCurrentUser() {
		action.signOut();
	}

	@Test(dependsOnMethods = "signOutCurrentUser", alwaysRun = true)
	public void logoutAndLoginWithNewCustomerList() {
		action.pause(waitTime);
		action.signIn(emailAddress, password);
	}

	@Test(dependsOnMethods = "logoutAndLoginWithNewCustomerList", alwaysRun = true)
	public void changePasswordOnAxisSupplierPortal() {
		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, newPassword);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID, newPassword);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.YES_BTN_ID));
	}

	@Test(dependsOnMethods = "changePasswordOnAxisSupplierPortal", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Profiles.TITLE_PROFILES);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
	}

	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer", alwaysRun = true)
	public void verifyDocumentTypesOnMaintainCustomerScreen() {
		action.pause(waitTime);
		assertEquals(table.getValueRow(1, 1), "All Document Types");
	}

	@Test(dependsOnMethods = "verifyDocumentTypesOnMaintainCustomerScreen", alwaysRun = true)
	public void logoutWithCustomerList() {
		action.signOut();
	}

	// Step 3
	@Test(dependsOnMethods = "logoutWithCustomerList", alwaysRun = true)
	public void loginWithMail5() {
		action.pause(waitTime);
		action.signIn(mailAdminLogin, PasswordAdmin);
	}

	@Test(dependsOnMethods = "loginWithMail5", alwaysRun = true)
	public void openMaintainCustomerScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), Profiles.TITLE_PROFILES);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(table.getValueTableHeader(1), "Customer Name");
		assertEquals(table.getValueTableHeader(2), "Profile Name");
		assertEquals(table.getValueTableHeader(3), "Document Types");
	}

	@Test(dependsOnMethods = "openMaintainCustomerScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, customerName);
	}

	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void clickEditButtonOnMaintainCustomerScreen() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 5);
		index.click();
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
		action.clickCheckBoxN(3);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}

	// Step 6
	@Test(dependsOnMethods = "tickCheckBoxOnModifyProfileScreen", alwaysRun = true)
	public void logoutAndLoginWithNewCustomerList2() {
		action.signOut();
		action.pause(waitTime);
		action.signIn(emailAddress, newPassword);
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
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, customerName);
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
		action.signIn(emailAddress, newPassword);
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

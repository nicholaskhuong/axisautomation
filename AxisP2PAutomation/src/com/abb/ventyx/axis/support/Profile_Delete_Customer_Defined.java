package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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

@ALM(id = "623")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Profile_Delete_Customer_Defined extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	TableFunction table;
	int waitTime = 1000;
	String customerName = "QATest2";
	int i;
	WebElement index;
	WebElement profile;
	String UserLogin = "admin_customer22@abb.com";
	String UserPassword = "Testuser1";

	// Step 1
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
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.pause(waitTime);
		table.clickFilterAndInputWithColumn(customerName, Profiles.CUSTOMER_NAME1_FILTER, true);
	}

	// #1: Default Profile
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void checkDefaultProfileOnMaintainCustomerScreen() {
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 6);
		index.isDisplayed();
	}

	// #2 Non-default Profile
	@Test(dependsOnMethods = "checkDefaultProfileOnMaintainCustomerScreen", alwaysRun = true)
	public void checkNonDefaultProfileOnMaintainCustomerScreen() {
		table.inputFilterAtIndex(Profile_Update_Customer_Defined_Step07.profileNameEdited2, Profiles.PROFILE_NAME_FILTER, true);
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(1, 6);
		index.isEnabled();
	}

	@Test(dependsOnMethods = "checkNonDefaultProfileOnMaintainCustomerScreen", alwaysRun = true)
	public void clickDeleteIconOnMaintainCustomerScreen() {
		index.click();
		action.waitObjVisibleAndClick(By.cssSelector(Profiles.DELETE_YES));
		action.checkAddSuccess(Messages.MESSAGE_DELETE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "clickDeleteIconOnMaintainCustomerScreen", alwaysRun = true)
	public void checkDataAgainAfterDeleted() {
		table.inputFilterAtIndex(Profile_Update_Customer_Defined_Step07.profileNameEdited2, Profiles.PROFILE_NAME_FILTER, true);
		action.pause(waitTime);
		Assert.assertTrue(i >= 0, String.format("Profile Name: %s not found!", Profile_Update_Customer_Defined_Step07.profileNameEdited2));
	}

	// Step3
	@Test(dependsOnMethods = "checkDataAgainAfterDeleted", alwaysRun = true)
	public void logoutFromMaintainCustomerScreen() {
		action.pause(waitTime);
		action.signOut();
	}

	@Test(dependsOnMethods = "logoutFromMaintainCustomerScreen", alwaysRun = true)
	public void loginWithAccountCustomer() {
		action.signIn(UserLogin, UserPassword);
	}

	@Test(dependsOnMethods = "loginWithAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer() {
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
	}
}

package com.abb.ventyx.axis.support;


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

@ALM(id = "623")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Profile_Delete_Customer_Defined extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	TableFunction table;
	int waitTime = 2000;
	String customerName = "QA Test";
	int i;
	WebElement index;
	WebElement profile;
	String User_Login = "mail222@abb.com";
	String User_Password = "Testuser2";
	
	//Step 1
	@Test
	public void openMaintainCustomerDefinedProfilesScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.pause(7000);
		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "QATest");
	}
	
	//#1: Default Profile
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void checkDefaultProfileOnMaintainCustomerScreen(){
		action.pause(waitTime);
		table = new TableFunction(driver);
		i = table.findRowByString(1, customerName);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(6, 1);
		action.pause(waitTime);
		index.isDisplayed();
	}	
	//#2 Non-default Profile
	@Test(dependsOnMethods = "checkDefaultProfileOnMaintainCustomerScreen", alwaysRun = true)
	public void checkNonDefaultProfileOnMaintainCustomerScreen(){
		table = new TableFunction(driver);
		i = table.findRowByString(4, customerName);
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index = table.getCellObject(4, 6);
		index.isEnabled();
	}
	
	@Test(dependsOnMethods = "checkNonDefaultProfileOnMaintainCustomerScreen", alwaysRun = true)
	public void clickDeleteIconOnMaintainCustomerScreen() {
		index.click();
		action.pause(waitTime);
		action.clickBtn(By.cssSelector(Profiles.DELETE_YES));
		action.checkAddSuccess(Messages.MESSAGE_DELETE_SUCCESSFULLY); 
	}
	
	//Step3
	@Test(dependsOnMethods = "clickDeleteIconOnMaintainCustomerScreen", alwaysRun = true)
	public void logoutFromMaintainCustomerScreen(){
		action.signOut();
	}
	
	@Test(dependsOnMethods = "logoutFromMaintainCustomerScreen", alwaysRun = true)
	public void loginWithAccountCustomer(){
		action.signIn(User_Login, User_Password);
	}
	
	@Test(dependsOnMethods = "loginWithAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
		action.pause(waitTime);
	}
}


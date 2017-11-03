package com.abb.ventyx.axis.support;


import java.util.List;

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
@Credentials(user = "Tomato@abb.com", password = "Testuser1")
public class Profile_Update_Customer_Defined_Step07 extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	TableFunction table;
	TableFunction table1;
	int row;
	int waitTime = 1000;
	String profileName = "POProfile2";
	String profileNameEdited = "POProfile3";
	int i,j;
	WebElement index;
	String userLogin = "mail222@abb.com";
	String userPassword = "Testuser2";
	String adminLogin = "axis_support@abb.com";
	String adminPassword = "Testuser1";
	String profileNameAccountCustomer = "POProfile";
	
	//Step1
	@Test
	public void openMaintainCustomerDefinedProfilesScreen(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	//Step2
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "QATest");
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickPencilIconOnMaintainCustomerScreen(){
		action.pause(waitTime);
		i = table.findRowByString(3, profileName);
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index = table.getCellObject(3, 5);
		action.pause(waitTime);
		index.click();
	}

	@Test(dependsOnMethods = "clickPencilIconOnMaintainCustomerScreen", alwaysRun = true)
	public void checkStatusProfileAndCustomerName(){
		action.pause(waitTime);
		WebElement profileName = driver.findElement(By.id(Profiles.PROFILE_NAME_ID));
		profileName.isEnabled();
		WebElement customerName = driver.findElement(By.id(Profiles.CUSTOMER_NAME));
		customerName.isDisplayed();
	}
	
	@Test(dependsOnMethods = "checkStatusProfileAndCustomerName", alwaysRun = true)
	public void editProfileNameonModifyProfileSreen(){
		action.inputTextField(Profiles.PROFILE_NAME_ID, profileNameEdited);
		action.pause(waitTime);
		action.clickCheckBoxN(2);
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "editProfileNameonModifyProfileSreen", alwaysRun = true)
	public void clickSaveButtonAndDisplayMessageSuccessfully(){
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	    action.pause(waitTime);
	}
	
	//Step3
	@Test(dependsOnMethods = "clickSaveButtonAndDisplayMessageSuccessfully", alwaysRun = true)
	public void logoutFromMaintainCustomerScreen1() {
		action.signOut();
	}
	
	@Test(dependsOnMethods = "logoutFromMaintainCustomerScreen1", alwaysRun = true)
	public void loginWithAccountCustomer1() {
		action.signIn(userLogin, userPassword);
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "loginWithAccountCustomer1", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer", alwaysRun = true)
	public void clickEditIconOnMaintainCustomerScreen(){
		i = table.findRowByString(3, "Advance Shipping Notice, Purchase Orders");
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index = table.getCellObject(3, 4);
		index.click();
		action.pause(waitTime);
		action.clickBtn(By.id(Profiles.CANCEL_BTN));
	}
	//Step 5
	@Test(dependsOnMethods = "clickEditIconOnMaintainCustomerScreen", alwaysRun = true)
	public void logoutfromAccountCustomer(){
			action.signOut();
		}
		
	@Test(dependsOnMethods = "logoutfromAccountCustomer", alwaysRun = true)
	public void loginfromAccountCustomer(){
			action.signIn(adminLogin, adminPassword);
			action.pause(waitTime);
		}
	
	@Test(dependsOnMethods = "loginfromAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerDefinedProfilesScreenAgain(){
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreenAgain", alwaysRun = true)
	public void clickFiterButtonAgainOnMaintainCustomerScreen() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "QATest");
		action.pause(waitTime);
	}

	@Test(dependsOnMethods = "clickFiterButtonAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void clickEditIconAgainOnMaintainCustomerScreen(){
		action.pause(waitTime);
		i = table.findRowByString(3, profileNameEdited);
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		index = table.getCellObject(3, 5);
		index.click();
	}
	
	@Test(dependsOnMethods = "clickEditIconAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void editAuthorisedDocumentTypes(){
		action.waitObjVisibleAndClick(By.id("showConfigBtn-AdvanceShippingNotice"));
		action.pause(4000);
		List<WebElement> checkBoxIndex0 = driver.findElements(By.xpath("//*[@id='configurationGrid-AdvanceShippingNotice']/div[3]/table/tbody/tr[1]/td[1]"));
		checkBoxIndex0.get(0).click();
	}
	
	@Test(dependsOnMethods = "editAuthorisedDocumentTypes", alwaysRun = true)
	public void clickSaveBtnAndDisplayMessageSuccessfully(){
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}
}


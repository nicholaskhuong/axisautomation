package com.abb.ventyx.axis.support;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

@ALM(id = "622")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Profile_Update_Customer_Defined_Steps01_06 extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	TableFunction table;
	TableFunction table1;
	int row;
	int waitTime = 1000;
	String profileName = "Profile3";
	String profileNameEdited = "Profile4";
	WebElement index;
	String userLogin = "mail222@abb.com";
	String userPassword = "Testuser2";
	String adminLogin = "mail5@abb.com";
	String adminPassword = "testuser";
	String profileName_AccountCustomer = "POProfile";
	
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
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "QATest");
	}
	
	@Test(dependsOnMethods = "clickFiterButtonOnMaintainCustomerScreen", alwaysRun = true)
	public void clickPencilIconOnMaintainCustomerScreen(){
		action.pause(waitTime);
		action.clickHorizontalScrollBar();
		index = table.getCellObject(4, 5);
		index.click();
	}

	@Test(dependsOnMethods = "clickPencilIconOnMaintainCustomerScreen", alwaysRun = true)
	public void checkStatusProfileAndCustomerName(){
		WebElement profileName = driver.findElement(By.id(Profiles.PROFILE_NAME_ID));
		profileName.isEnabled();
		WebElement customerName = driver.findElement(By.id(Profiles.CUSTOMER_NAME));
		customerName.isDisplayed();
	}
	
	@Test(dependsOnMethods = "checkStatusProfileAndCustomerName", alwaysRun = true)
	public void editProfileNameonModifyProfileSreen(){
		action.waitObjVisible(By.id(Profiles.PROFILE_NAME_ID));
		action.inputTextField(Profiles.PROFILE_NAME_ID, profileNameEdited);
		action.clickCheckBoxN(2);
	}
	
	@Test(dependsOnMethods = "editProfileNameonModifyProfileSreen", alwaysRun = true)
	public void clickSaveButtonAndDisplayMessageSuccessfully(){
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}
	
	//Step3
	@Test(dependsOnMethods = "clickSaveButtonAndDisplayMessageSuccessfully", alwaysRun = true)
	public void logoutFromMaintainCustomerScreen(){
		action.pause(waitTime);
		action.signOut();
	}
	
	@Test(dependsOnMethods = "logoutFromMaintainCustomerScreen", alwaysRun = true)
	public void loginWithAccountCustomer(){
		action.signIn(userLogin, userPassword);
	}
	
	@Test(dependsOnMethods = "loginWithAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer(){
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer", alwaysRun = true)
	public void clickEditIconOnMaintainCustomerScreen(){
		
		action.clickHorizontalScrollBar();
		index = table.getCellObject(4, 4);
		index.click();
		action.waitObjVisibleAndClick(By.id(Profiles.CANCEL_BTN));
	}
	//Step 5
	@Test(dependsOnMethods = "clickEditIconOnMaintainCustomerScreen", alwaysRun = true)
	public void logoutfromAccountCustomer(){
			action.signOut();
		}
		
	@Test(dependsOnMethods = "logoutfromAccountCustomer", alwaysRun = true)
	public void loginfromAccountCustomer(){
			action.signIn(adminLogin, adminPassword);
		}
	
	@Test(dependsOnMethods = "loginfromAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerDefinedProfilesScreenAgain(){
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreenAgain", alwaysRun = true)
	public void clickFiterButtonAgainOnMaintainCustomerScreen() {
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "QATest");
	}

	@Test(dependsOnMethods = "clickFiterButtonAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void clickEditIconAgainOnMaintainCustomerScreen(){
		action.clickHorizontalScrollBar();
		index = table.getCellObject(4, 5);
		index.click();
	}
	
	@Test(dependsOnMethods = "clickEditIconAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void editAuthorisedDocumentTypes(){
		action.waitObjVisibleAndClick(By.id("showConfigBtn-AdvanceShippingNotice"));
		List<WebElement> checkBoxIndex0 = driver.findElements(By.xpath("//*[@id='configurationGrid-AdvanceShippingNotice']/div[3]/table/tbody/tr[1]/td[1]"));
		checkBoxIndex0.get(0).click();
	}
	
	@Test(dependsOnMethods = "editAuthorisedDocumentTypes", alwaysRun = true)
	public void clickSaveBtnAndDisplayMessageSuccessfully(){
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}
}


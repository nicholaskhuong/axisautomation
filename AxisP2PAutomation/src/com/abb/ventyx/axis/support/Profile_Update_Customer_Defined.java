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
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Profile_Update_Customer_Defined extends BaseTestCase {
	ScreenAction action;
	BaseDropDownList list;
	TableFunction table;
	TableFunction table1;
	int row;
	int waitTime = 3000;
	String profileName = "POProfile1";
	String profileNameEdited = "POProfile2";
	int i,j;
	String User_Login = "mail222@abb.com";
	String User_Password = "Testuser2";
	String Admin_Login = "mail5@abb.com";
	String Admin_Password = "testuser";
	String profileName_AccountCustomer = "POProfile";
	
	//Step1
	@Test
	public void openMaintainCustomerDefinedProfilesScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	//Step2
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickFiterButtonOnMaintainCustomerScreen() {
		action.pause(6000);
		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "QATest");
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreen", alwaysRun = true)
	public void clickPencilIconOnMaintainCustomerScreen(){
		action.pause(waitTime);
		table = new TableFunction(driver);
		action.pause(waitTime);
		i = table.findRowByString1(2, profileName);
		//j=i-1;
		j = 58;
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		WebElement elemement= driver.findElement(By.id(Profiles.EDIT_BUTTON_INDEX + j));
		elemement.click();	
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
		action.pause(waitTime);
		action.inputTextField(Profiles.PROFILE_NAME_ID, profileNameEdited);
		action.clickCheckBoxN(2);
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
	public void logoutFromMaintainCustomerScreen(){
		action.signOut();
	}
	
	@Test(dependsOnMethods = "logoutFromMaintainCustomerScreen", alwaysRun = true)
	public void loginWithAccountCustomer(){
		action.signIn(User_Login, User_Password);
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "loginWithAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerScreenWithAccountCustomer(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE_WITH_CUSTOMER));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILE_CUSTOMER));
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerScreenWithAccountCustomer", alwaysRun = true)
	public void clickEditIconOnMaintainCustomerScreen(){
		table = new TableFunction(driver);
		i = table.findRowByString1(2, "Purchase Orders");
		j=i-1;
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		WebElement elemement= driver.findElement(By.id(Profiles.EDIT_BUTTON_INDEX + j));
		elemement.click();
	}
	
	@Test(dependsOnMethods = "clickEditIconOnMaintainCustomerScreen", alwaysRun = true)
	public void clickCancelButtonOnModifyProfile(){
		action.clickBtn(By.id(Profiles.CANCEL_BTN));
	}
	
	//Step 5
	@Test(dependsOnMethods = "clickCancelButtonOnModifyProfile", alwaysRun = true)
	public void logoutfromAccountCustomer(){
			action.signOut();
		}
		
	@Test(dependsOnMethods = "logoutfromAccountCustomer", alwaysRun = true)
	public void loginfromAccountCustomer(){
			action.signIn(Admin_Login, Admin_Password);
			action.pause(waitTime);
		}
	
	@Test(dependsOnMethods = "loginfromAccountCustomer", alwaysRun = true)
	public void openMaintainCustomerDefinedProfilesScreenAgain(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.PROFILES));
	}
	
	@Test(dependsOnMethods = "openMaintainCustomerDefinedProfilesScreenAgain", alwaysRun = true)
	public void clickFiterButtonAgainOnMaintainCustomerScreen() {
		action.pause(6000);
		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, "QATest");
		action.pause(waitTime);
	}

	@Test(dependsOnMethods = "clickFiterButtonAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void clickEditIconAgainOnMaintainCustomerScreen(){
		action.pause(waitTime);
		table = new TableFunction(driver);
		i = table.findRowByString1(2, profileName);
		j=58;
		action.clickHorizontalScrollBar();
		action.pause(waitTime);
		WebElement elemement= driver.findElement(By.id(Profiles.EDIT_BUTTON_INDEX + j));
		elemement.click();
	}
	
	@Test(dependsOnMethods = "clickEditIconAgainOnMaintainCustomerScreen", alwaysRun = true)
	public void editAuthorisedDocumentTypes(){
		action.pause(waitTime);
		action.clickBtn(By.id("showConfigBtn-AdvanceShippingNotice"));
		action.pause(waitTime);
		List<WebElement> checkBoxIndex0 = driver.findElements(By.xpath("//*[@id='configurationGrid-AdvanceShippingNotice']/div[3]/table/tbody/tr[1]/td[1]"));
		checkBoxIndex0.get(0).click();
		action.pause(waitTime);
	}
	
	@Test(dependsOnMethods = "editAuthorisedDocumentTypes", alwaysRun = true)
	public void clickSaveBtnAndDisplayMessageSuccessfully(){
		action.pause(waitTime);
		action.waitObjVisibleAndClick(By.id(Profiles.SAVE_BTN));
		action.checkAddSuccess(Messages.MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY);
	}
}


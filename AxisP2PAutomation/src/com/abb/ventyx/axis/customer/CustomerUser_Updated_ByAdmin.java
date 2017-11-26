package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "612")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_Updated_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String INVALIDCUSTOMERUSEREMAIL = "cuser1abb.com";
	String INVALIDPASSWORD = "<html>";
	String PASSWORD ="Testuser2";
	String CONFIRMPASSWORD ="Testuser3";
	String NEWPASSWORD ="Testuser4";
	public static String NEWUSERID = "Automator 1 1";
	String ACTIONSTATUS ="Active";
	String CREATEDSTATUS ="Created";
	int i;

	String userNo = " ";
	// Step 1 Select Users Sub Menu
	@Test
	public void selectUsersSubMenu() {
		table = new TableFunction(driver);
		action = new ScreenAction(driver);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.USERS_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		action.assertTitleScreen("Maintain Customer Users");
	}

	// Step 2 Click on User Number of an existing customer user
	@Test(dependsOnMethods="selectUsersSubMenu")
	public void openModifyUserScreen() {
		action.pause(1000);
		i = table.findRowByString(3, CustomerUser_Created_ByAdmin.USEREMAILADDRESS);
		Assert.assertTrue(i >= 0, String.format("Customer user: %s not found!", CustomerUser_Created_ByAdmin.USEREMAILADDRESS));
		assertEquals(table.getValueRow(2, i), CustomerUser_Created_ByAdmin.USERID);
		assertEquals(table.getValueRow(4, i), "All Permissions");
		assertEquals(table.getValueRow(5, i), CREATEDSTATUS);
		userNo = table.getValueRow(1, i);
		i = table.findRowByString(1, userNo);
		//table.clickUserNo(i);
		table.getCellObject(ScreenObjects.TABLE_BODY_XPATH, i,1).click();;

	}

	// Step 3 Update with Invalid Email
	@Test(dependsOnMethods="openModifyUserScreen")
	public void updateWithInvalidEmail() {
		action.waitObjVisible(By.id(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID));
		action.inputEmailField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, INVALIDCUSTOMERUSEREMAIL);
		action.waitObjVisibleAndClick(By.id(CustomerUsers.SAVE_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
	}

	// Step 4 Update with Invalid Password.
	@Test(dependsOnMethods="updateWithInvalidEmail")
	public void updateWithInvalidPassword() {

		action.inputEmailField(CustomerUsers.USEREMAILADDRESS_TEXTBOX_ID, CustomerUser_Created_ByAdmin.USEREMAILADDRESS);

		driver.findElement(By.cssSelector(CustomerUsers.YESUPDATEPASSWORD_RADIOBUTTON)).findElement(By.tagName("label")).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.waitObjVisible(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID));
		driver.findElement(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(),Messages.INVALID_PWD1);
	}
	// Step 5 Update with different confirm password and password
	@Test(dependsOnMethods="updateWithInvalidPassword")
	public void updateWithUnmachtedPassword() {
		action.waitObjVisible(By.id(CustomerUsers.PASSWORD_TEXTBOX_ID));

		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, PASSWORD);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).clear();

		driver.findElement(By.id(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID)).sendKeys(CONFIRMPASSWORD);
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();
		
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)).getText(),Messages.UNMATCHED_CONFIRM_PWD);
	}

	// Step 6 Update with valid data
	@Test(dependsOnMethods="updateWithUnmachtedPassword")
	public void updateWithValidData() {
		action.clickBtn(By.id(CustomerUsers.USERID_TEXTBOX_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		
		action.inputTextField(CustomerUsers.PASSWORD_TEXTBOX_ID, NEWPASSWORD);
		action.pause(1000);
		action.inputTextField(CustomerUsers.CONFIRMPASSWORD_TEXTBOX_ID, NEWPASSWORD);
		
		
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 1000L);
		NEWUSERID = String.format("updated%s", drand);
		
		action.inputTextField(CustomerUsers.USERID_TEXTBOX_ID, NEWUSERID);
		driver.findElement(By.id(CustomerUsers.SAVE_BUTTON)).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));

		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(), Messages.USER_UPDATE_SUCCESSFULLY);
		assertEquals(table.getValueRow(2, i), NEWUSERID);
		assertEquals(table.getValueRow(4, i), "All Permissions");
	}


}

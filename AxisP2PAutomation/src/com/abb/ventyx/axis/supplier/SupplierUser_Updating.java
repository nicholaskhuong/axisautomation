package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "603")
@Credentials(user = "salem5@abb.com", password = "Testuser1")
public class SupplierUser_Updating extends BaseTestCase {
	String userId = "salem 10";
	String passwordValid = "Testuser2";
	String confirmPasswordNotMap = "Testuser300";
	String confirmPasswordValid = "Testuser2";
	String email = "salem10@abb.com";
	String userNo = "";
	ScreenAction action;
	TableFunction table;
	int row;
	int milliseconds = 3000;
	String emailInvalid = "salem";
	String emailExist = "salem11@abb.com";
	String password_less_than_6_character = "test";

	@Test
	public void openScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		// step 1
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void selectUser() {
		// step 2
		table = new TableFunction(driver);
		row = table.findRowByString(Users.SUPPLIER_USERS_TABLE_CSS, 2, userId);
		Assert.assertTrue(row >= 0, String.format("User %s not found!", userId));
		table.assertValueRow(2, row, userId);
		row -= 1;
		userNo = driver.findElement(By.id(Users.USERNUMBER_LINKID + row)).getText();
		action.clickBtn(By.id(Users.USERNUMBER_LINKID + row));
		action.waitObjVisible(By.id(Users.USER_ID));
		action.assertTitleScreen(Users.TITLE_MODIFY);
	}

	@Test(dependsOnMethods = "selectUser")
	public void updateInvalidEmail() {
		// Step 3
		// update Email exist
		action.inputTextField(Users.EMAIL_ID, emailExist);
		driver.findElement(By.id(Users.USER_ID)).click();
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.UPDATE_SAME_EMAIL);
		// update Email invalid
		action.inputTextField(Users.EMAIL_ID, emailInvalid);
		action.inputTextField(Users.EMAIL_ID, emailInvalid);
		driver.findElement(By.id(Users.USER_ID)).click();
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);

	}

	@Test(dependsOnMethods = "updateInvalidEmail")
	public void updatePasswordLessThan6Character() {
		// step 4
		driver.findElement(By.cssSelector(Users.UPDATEPASSWORD_YES_CSS)).findElement(By.tagName("label")).click();
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		action.inputTextField(Users.PASSWORD_ID, password_less_than_6_character);
		action.inputTextField(Users.EMAIL_ID, email);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_PWD);
	}

	@Test(dependsOnMethods = "updateInvalidEmail")
	public void updatePasswordNotMap() {
		// step 5
		action.inputTextField(Users.PASSWORD_ID, passwordValid);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirmPasswordNotMap);
		driver.findElement(By.id(Users.USER_ID)).click();
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.MESSAGE_ERROR_PASSWORD);
	}

	@Test(dependsOnMethods = "updatePasswordNotMap")
	public void updateSuccessfully() {
		// step 6
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirmPasswordValid);
		driver.findElement(By.id(Users.USER_ID)).click();
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.USER_UPDATE_SUCCESSFULLY);
	}

	@Test(dependsOnMethods = "updateSuccessfully")
	public void withoutSavingNO() {
		// step 7,8,9
		action.clickBtn(By.id(Users.USERNUMBER_LINKID + row));
		action.inputTextField(Users.USER_ID, "any change");
		driver.findElement(By.id(ScreenObjects.CANCEL_ID)).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.NO_BTN_ID)).click();
	}

	@Test(dependsOnMethods = "updateSuccessfully")
	public void withoutSavingYES() {
		// Step 10,11
		action.inputTextField(Users.USER_ID, "any change update");
		driver.findElement(By.id(ScreenObjects.CANCEL_ID)).click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
	}

}

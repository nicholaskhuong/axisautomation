package com.abb.ventyx.axis.authencation;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "472")
@Credentials(user = "supplier_user_20@abb.com", password = "Testuser1")
public class Login_Page extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int milliseconds = 3000;
	String maintainSupplierUsers = "Maintain Supplier Users";
	String userID = "User %s";
	String userEmail = "email_supplier_user%s@abbb.com";
	Random rand = new Random();
	int drand = rand.nextInt(10000);
	String password = "Testuser2";
	String confirmPassword = "Testuser2";
	WebElement saveBtn;
	String axisSupplierPort = "Axis Supplier Portal";
	String newPassword = "Testuser1";
	String newConfirmPassword = "Testuser1";
	@Test
	public void openScreen() {
		// Pre-condition
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisible(By.id(SupplierMenu.USERS_ID));
		action.clickBtn(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(SupplierMenu.HEADER_OF_PAGE));
		action.pause(milliseconds);
		assertEquals(driver.findElement(By.cssSelector(SupplierMenu.HEADER_OF_PAGE)).getText(), maintainSupplierUsers);
		
	}

	@Test(dependsOnMethods = "openScreen")
	public void createNewUser() {
		// Pre-condition
		action.clickBtn(By.cssSelector(AxisConfigMenu.ADD_ICON));
		userID = String.format("User %s", drand);
		action.inputEmailField(Users.USER_ID, userID);
		userEmail = String.format("email_supplier_user%s@abbb.com", drand);
		action.inputEmailField(Users.EMAIL_ID, userEmail);
		action.inputTextField(Users.PASSWORD_ID, password);
		action.inputTextField(Users.CONFIMRPASSWORD_ID, confirmPassword);
		((JavascriptExecutor) driver).executeScript("window.focus();");
		action.clickCheckBoxN(1);
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.signOut();
		action.signIn(userEmail, password);
	}
	@Test(dependsOnMethods = "createNewUser")
	public void checkLoginTheFirstTime() {
		action.waitObjVisible(By.id(Users.PASSWORD_ID));
		action.pause(milliseconds);
		action.inputTextField(Users.PASSWORD_ID, password);
		action.inputTextField(Users.NEW_PASSWORD, newPassword);
		action.inputEmailField(Users.CONFIMRPASSWORD_ID, newConfirmPassword);




	}



}

package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.ventyx.testng.TestDataKey;

@ALM(id = "602")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class CustomerUser_Creating extends BaseTestCase{
	@TestDataKey private final String USERID ="Automator 1";
	@TestDataKey private final String USEREMAILADDRESS ="cuser1@abb.com";
	@TestDataKey private final String PASSWORD ="Testuser2";
	@TestDataKey private final String CONFIRMPASSWORD ="Testuser2";
	@TestDataKey private final String INVALIDEMAIL = "<HTML>";
	@TestDataKey private final String INVALIDPASSWORD = "12345";
	//@TestDataKey private final String INVALIDPASSWORD = "Testuser2";
	// Step 1 Open Users sub menu
	@Test
	public void selectUsersSubMenu() throws InterruptedException{
		// Step 1
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement customerMaintenanceMenu = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU)));
		customerMaintenanceMenu.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(CustomerMenu.USERS_SUBMENU)));
		driver.findElement(By.cssSelector(CustomerMenu.USERS_SUBMENU)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)));
		Thread.sleep(2000);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Maintain Customer Users");
	}

	// Step 2 check error message in case inputting lack of mandatory field.

	@Test(dependsOnMethods="selectUsersSubMenu")
	public void addNewUserWithoutMandatoryField() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ADD_BUTTON)));
		driver.findElement(By.cssSelector(CustomerUsers.ADD_BUTTON)).click();
		Thread.sleep(1500);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.CUSTOMERUSERS_HEADER)).getText(), "Create User");
		driver.findElement(By.cssSelector(CustomerUsers.USERID_TEXTBOX)).sendKeys(USERID);
		Thread.sleep(100);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(USEREMAILADDRESS);
		Thread.sleep(100);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(PASSWORD);
		Thread.sleep(100);
		//driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(CONFIRMPASSWORD);

		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.ENTER_MANDATORY_FIELDS);
		// There is a defect here, user group is not being displayed in this page. So can scripts more.
		
		// Focus on this field to make the error message be invisibility
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
	}

	// Step 3 Input mandatory field, don't select user group
	@Test(dependsOnMethods="addNewUserWithoutMandatoryField")
	public void addNewUserWithoutUserGroup() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(CONFIRMPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		Thread.sleep(1500);
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.USER_SELECT_USERGROUP);
	}

	// Step 4 Input invalid e-mail
	@Test(dependsOnMethods="addNewUserWithoutUserGroup")
	public void addNewUserWithInvalidEmail(){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.USEREMAILADDRESS_TEXTBOX)).sendKeys(INVALIDEMAIL);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.USER_SELECT_USERGROUP);
	}

	// Step 5 Input invalid password
	@Test(dependsOnMethods="addNewUserWithInvalidEmail")
	public void addNewUserWithInvalidPassword(){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.PASSWORD_TEXTBOX)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).clear();
		driver.findElement(By.cssSelector(CustomerUsers.CONFIRMPASSWORD_TEXTBOX)).sendKeys(INVALIDPASSWORD);
		driver.findElement(By.cssSelector(CustomerUsers.SAVE_BUTTON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CustomerUsers.ERROR)));
		assertEquals(driver.findElement(By.cssSelector(CustomerUsers.ERROR)).getText(), Messages.USER_SELECT_USERGROUP);
	}




}

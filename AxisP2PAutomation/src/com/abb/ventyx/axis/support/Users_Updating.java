package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObject;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;

@ALM(id = "204")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Users_Updating extends BaseTestCase {

	@Test
	public void checkScreen() {
		WebElement axisAdminMenu = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID)));
		axisAdminMenu.click();
		WebElement usersMenu = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.USERS_SUBMENU_ID)));
		usersMenu.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID
						+ "0")));
		WebElement screenTitle = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.SCREEN_TITLE_ID)));
		assertEquals(screenTitle.getText(),
				AxisAdministratorUsers.SCREEN_TITLE, "Title is wrong");
	}

	@Test(dependsOnMethods = "checkScreen")
	public void updateInvalidData() throws InterruptedException {
		WebElement row = driver.findElement(By.id(AxisAdministratorUsers.ROW_ID
				+ "3"));
		row.click();
		WebElement saveId = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.SAVE_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisAdministratorUsers.SCREEN_UPDATE_TITLE);
		// input invalid email
		WebElement email = driver.findElement(By
				.id(AxisAdministratorUsers.EMAIL_ID));
		email.clear();
		Thread.sleep(1000);
		email.sendKeys("mail");

		saveId.click();
		WebElement warningMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage.getText(), Messages.INVALID_EMAIL);
		Thread.sleep(2000);

		// Input invalid password
		email.clear();
		Thread.sleep(1000);
		int n = new Random().nextInt(1000);
		email.sendKeys("email" + n + "@dathy.com");

		List<WebElement> onBtn = driver.findElements(By
				.id(AxisAdministratorUsers.UPDATED_PASSWORD_ID));
		onBtn.get(0).click();
		WebElement pwd = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.PASSWORD_ID)));
		pwd.sendKeys("Testuser");

		Thread.sleep(2000);
		saveId.click();
		WebElement warningMessage1 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage1.getText(), Messages.INVALID_PWD);

		// Input Invalid confirm pwd
		pwd.clear();
		Thread.sleep(1000);
		pwd.sendKeys("Testuser1");
		WebElement confirmPwd = driver.findElement(By
				.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPwd.sendKeys("Testuser2");
		Thread.sleep(2000);
		saveId.click();
		WebElement warningMessage2 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage2.getText(), Messages.INVALID_CONFIRM_PWD);

	}

	@Test(dependsOnMethods = "updateInvalidData")
	public void updateSuccessfully() throws InterruptedException {

		WebElement confirmPwd = driver.findElement(By
				.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPwd.clear();
		Thread.sleep(1000);
		confirmPwd.sendKeys("Testuser1");
		WebElement saveId = driver.findElement(By
				.id(AxisAdministratorUsers.SAVE_ID));
		saveId.click();
		WebElement warningMessage2 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.SUCCESS_MESSAGE)));
		assertEquals(warningMessage2.getText(),
				Messages.USER_UPDATE_SUCCESSFULLY);

	}

	// Click Cancel do it again
	@Test(dependsOnMethods = "updateSuccessfully")
	public void cancelWithoutData() {

		WebElement row1 = driver.findElement(By
				.id(AxisAdministratorUsers.ROW_ID + "1"));
		row1.click();
		(new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.USER_ID)));

		WebElement cancelBtn = driver.findElement(By
				.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID
						+ "0")));
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_TITLE);
	}

	@Test(dependsOnMethods = "cancelWithoutData")
	public void cancelWithYes() throws InterruptedException {
		WebElement row1 = driver.findElement(By
				.id(AxisAdministratorUsers.ROW_ID + "1"));
		row1.click();
		WebElement email = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.EMAIL_ID)));
		email.sendKeys("DathyUser1");
		WebElement cancelBtn = driver.findElement(By
				.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();
		// Click Yes
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		driver.findElement(By.id(ScreenObject.YES_BTN_ID)).click();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID
						+ "0")));
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_TITLE);

	}

	@Test(dependsOnMethods = "cancelWithYes")
	public void cancelClickNo() {

		WebElement row1 = driver.findElement(By
				.id(AxisAdministratorUsers.ROW_ID + "1"));
		row1.click();
		WebElement email = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(AxisAdministratorUsers.EMAIL_ID)));
		email.sendKeys("DathyUser1");
		WebElement cancelBtn = driver.findElement(By
				.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();

		WebElement msgDialog = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObject.NO_BTN_ID)).click();
		WebElement screenTitle = driver.findElement(By
				.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(),
				AxisAdministratorUsers.SCREEN_UPDATE_TITLE);
	}

}

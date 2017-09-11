package com.abb.ventyx.axis.support;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

@ALM(id = "205")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Users_Deleting extends BaseTestCase {

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
		WebElement deleteBtn0 = driver.findElement(By
				.id(AxisAdministratorUsers.DELETE_ID + "0"));
		deleteBtn0.getAttribute("aria-disabled").equals("false");
	}

	@Test(dependsOnMethods = "checkScreen")
	public void deleteUserGroupClickNo() throws Exception {

		// ScrollBar
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.querySelector('table th:last-child').scrollIntoView();");

		driver.findElement(By.id(AxisAdministratorUsers.DELETE_ID + "3"))
				.click();
		String UserId = driver.findElement(
				By.id(AxisAdministratorUsers.ROW_ID + "3")).getText();
		// Click No on dialog
		WebElement deleteConfirm = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		assertThat(deleteConfirm.getText(),
				containsString(Messages.DELETE_USER_CONFIRM));
		WebElement deleteNoBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.DELETE_NO)));
		deleteNoBtn.click();
		Thread.sleep(2000);
		assertEquals(
				driver.findElement(By.id(AxisAdministratorUsers.ROW_ID + "3"))
						.getText(), UserId);

	}

	@Test(dependsOnMethods = "deleteUserGroupClickNo")
	public void deleteUserGroupClickYes() throws Exception {

		// Click Yes on dialog
		String UserId = driver.findElement(
				By.id(AxisAdministratorUsers.ROW_ID + "3")).getText();
		driver.findElement(By.id(AxisAdministratorUsers.DELETE_ID + "3"))
				.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(ScreenObject.CONFIRMATION)));
		WebElement deleteYesBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.DELETE_YES)));
		deleteYesBtn.click();
		WebElement flashMessage = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.SUCCESS_MESSAGE)));
		assertEquals(flashMessage.getText(), Messages.USER_DELETE_SUCCESSFULLY);
		assertNotEquals(
				driver.findElement(By.id(AxisAdministratorUsers.ROW_ID + "3"))
						.getText(), UserId);
	}

}

package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;

public class PermissionsAction {

	WebDriver driver;

	public PermissionsAction(WebDriver driver) {
		this.driver = driver;
	}

	public int countRow(String tableCSS) {
		WebElement baseTable = driver.findElement(By.xpath(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		return sumRow;
	}

	public void filterPermission(String filterValue) throws InterruptedException{
		// Click Filter Icon
		WebElement filterButton = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#HeaderMenuBar > span:nth-child(1)")));
		filterButton.click();
		Thread.sleep(2000);
		// Enter filter value
		WebElement filterPermissionName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.PERMISSION_NAME_FILTER)));
		filterPermissionName.sendKeys(filterValue);
		Thread.sleep(2000);

	}

	public void enterValueTofilterPermission(String filterValue) throws InterruptedException{	
		// Enter filter value
		WebElement filterPermissionName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.PERMISSION_NAME_FILTER)));
		filterPermissionName.sendKeys(filterValue);
		Thread.sleep(2000);

	}

	public void clickPermissionsSubMenu() throws InterruptedException{
		WebElement axisPermissionsMenu = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.PERMISSIONS)));
		axisPermissionsMenu.click();
		Thread.sleep(1000);

	}
	public void clickAddButton() throws InterruptedException{
		WebElement addPermissionButton = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.ADD)));
		addPermissionButton.click();
		Thread.sleep(1000);
	}
	public void enterPermissionName(String permissionName) throws InterruptedException{
		WebElement permissionNameElm = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.PERMISSION_NAME)));
		permissionNameElm.sendKeys(permissionName);
		Thread.sleep(1000);
	}
	public void clickSaveButtonOnAddPermisisonPopUp() throws InterruptedException{
		// Click Save button on Add Permission Window Pop Up
		WebElement saveButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.SAVE)));
		saveButton.click();
		Thread.sleep(1000);
	}
	
	public void clickCancelButtonOnAddPermisisonPopUp() throws InterruptedException{
		WebElement saveButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.CANCEL)));
		saveButton.click();
		Thread.sleep(1000);
	}
}

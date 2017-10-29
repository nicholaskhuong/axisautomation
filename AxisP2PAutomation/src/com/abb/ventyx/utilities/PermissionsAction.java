package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	
	public void clickSystemConfigurationMenu(){
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();
	}

	public void filterPermissionbyDocumentType(String filterValue) {
		// Enter filter value
		WebElement filterPermissionName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.DOC_TYPE_FILTER)));
		filterPermissionName.sendKeys(filterValue);
	}

	public void filterPermissionbyPermissionName(String filterValue) throws InterruptedException{
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
	
	public void enterValueTofilterPermission(String filterValue) {
		// Enter filter value
		WebElement filterPermissionName = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.PERMISSION_NAME_FILTER)));
		filterPermissionName.sendKeys(filterValue);

	}

	public void clickPermissionsSubMenu() {
		WebElement axisPermissionsMenu = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.PERMISSIONS)));
		axisPermissionsMenu.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	public void clickAddButton() {
		WebElement addPermissionButton = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.ADD)));
		addPermissionButton.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void enterPermissionName(String permissionName) throws InterruptedException{
		WebElement permissionNameElm = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.PERMISSION_NAME)));
		permissionNameElm.sendKeys(permissionName);
		Thread.sleep(1000);
	}
	
	public void selectDocumentType(String documentTypeCSS) throws InterruptedException{
		WebElement permissionDocType = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(Permissions.DOCUMENT_TYPE)));
		permissionDocType.click();
		Thread.sleep(1000);
		WebElement POAckType = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(documentTypeCSS)));
		POAckType.click();
	}
	
	public void selectUserType(String userTypeCSS){
		WebElement adminUserType = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(userTypeCSS)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", adminUserType);
	}
	public void clickSaveButtonOnAddPermisisonPopUp() {
		// Click Save button on Add Permission Window Pop Up
		WebElement saveButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.SAVE)));
		saveButton.click();
	}

	public void clickCancelButtonOnAddPermisisonPopUp() {
		WebElement cancelButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.CANCEL)));
		cancelButton.click();
	}

	
	public void selectDocTypebyText(String docType) {
		WebElement permissionDocType = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(Permissions.DOCUMENT_TYPE)));
		permissionDocType.click();
		WebElement baseTable = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table")));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		
		System.out.print(sumRow +" test");
		
		for (int i = 1; i <= sumRow; i++) {
			WebElement POAckType = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By
							.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table > tbody > tr:nth-child("+i+") > td > span")));
			if(POAckType.getText().equals(docType)){
				POAckType.click();
				break;
			}
				
		}
		permissionDocType.click();
	
	}
}

package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;

public class PermissionsAction {

	WebDriver driver;
	public PermissionsAction(WebDriver driver) {
		this.driver = driver;
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
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", POAckType);
				break;
			}
				
		}
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", permissionDocType);

	
	}
}

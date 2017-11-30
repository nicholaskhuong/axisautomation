package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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
		WebElement adminUserType = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(userTypeCSS)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", adminUserType);
	}
	
	public void selectDocTypebyText(String docType) {
		String noOfDocType = "0";
		WebElement permissionDocType = driver.findElement(By.id(Permissions.DOCUMENT_TYPE));
		permissionDocType.click();
		clickoOnDocumentType(docType);
		WebElement numberOfDocumentType;
		try {
			numberOfDocumentType = driver.findElement(By.cssSelector(Permissions.DOCUMENT_TYPE_TOTAL_NUMBER));
			if (numberOfDocumentType.isDisplayed()) {
				noOfDocType = numberOfDocumentType.getText();
				noOfDocType = noOfDocType.substring(noOfDocType.indexOf("/") + 1, noOfDocType.length());

				int numberScroll = 0;
				if (Integer.valueOf(noOfDocType) > 10) {
					numberScroll = Integer.valueOf(noOfDocType) / 10;
				}
				for (int i = 0; i < numberScroll; i++) {
					WebElement documentNextPage = driver.findElement(By.cssSelector(Permissions.DOCUMENT_TYPE_NEXT_PAGE));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", documentNextPage);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", documentNextPage);

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					clickoOnDocumentType(docType);

				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("No next page for Document type list" + e.getMessage());
		}

	}

	private void clickoOnDocumentType(String docType) {
		WebElement baseTable = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(Permissions.DOCUMENT_TYPE_LIST_TABLE)));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		
		for (int i = 1; i <= sumRow; i++) {
			WebElement POAckType = driver.findElement(By.cssSelector(String.format("%s > tbody > tr:nth-child(%s) > td > span",
					Permissions.DOCUMENT_TYPE_LIST_TABLE, i)));
			System.out.println(POAckType.getText());
			if (POAckType.getText().trim().equalsIgnoreCase(docType)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", POAckType);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
				
		}
	}
}

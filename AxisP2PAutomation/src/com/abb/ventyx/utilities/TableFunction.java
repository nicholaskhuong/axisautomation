package com.abb.ventyx.utilities;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObject;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;

public class TableFunction {
	WebDriver driver;

	public TableFunction(WebDriver driver) {
		this.driver = driver;
	}

	public int findRowByString(String tableCSS, String value, int columnindex) {
		int row = 0;
		WebElement baseTable = driver.findElement(By.cssSelector(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i < sumRow; i++) {
			WebElement columnValue = driver.findElement(By.cssSelector(tableCSS
					+ "> table > tbody > tr:nth-child(" + i
					+ ") > td:nth-child(" + columnindex + ")"));
			if (columnValue.getText().equals(value)) {
				row = i;
				break;
			}

		}
		return row;

	}

	public int countRow(String tableCSS) {
		WebElement baseTable = driver.findElement(By.xpath(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		return sumRow;
	}

	public void filterPermission(String filterValue) {
		// Click Filter Icon
		WebElement filterButton = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.FILTER_BTN_CSS)));
		filterButton.click();

		// Enter filter value
		WebElement filterPermissionName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(Permissions.PERMISSION_NAME_FILTER)));
		filterPermissionName.sendKeys(filterValue);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputFilter(String value) {

		WebElement filterButton = driver.findElement(By
				.cssSelector(ScreenObject.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObject.FILTER_FIELD_ID));
		action.inputTextField(ScreenObject.FILTER_FIELD_ID, value);

	}

	public void assertFiler(String obj, String value, int row) {
		WebElement rowFilter = driver
				.findElement(By.id(UserGroup.ROW_ID + row));
		assertEquals(rowFilter.getText(), value, "Title is wrong");
	}
}

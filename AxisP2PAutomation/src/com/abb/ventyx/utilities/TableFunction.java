package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
}

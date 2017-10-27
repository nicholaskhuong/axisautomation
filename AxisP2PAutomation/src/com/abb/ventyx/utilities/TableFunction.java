package com.abb.ventyx.utilities;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;

public class TableFunction {
	WebDriver driver;
	int timeout = 60;

	public TableFunction(WebDriver driver) {
		this.driver = driver;
	}

	public int findRowByString(String tableCSS, int columnindex, String value) {
		return findRowByString(tableCSS, columnindex, value, false);
	}

	public int findRowByString(int columnindex, String value) {
		return findRowByString(ScreenObjects.TABLE_BODY_XPATH, columnindex, value, true);
	}
	public int findRowByString(String tableBody, int columnindex, String value, boolean isXpath) {
		int row = -1;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement baseTable;
		if (isXpath) {
			baseTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableBody)));
		} else {
			baseTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(tableBody)));
		}

		if (null == baseTable)
			return row;

		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size() - 1;
		if (sumRow > 0) {
			WebElement columnValue;
			for (int i = 1; i < sumRow; i++) {
				if (isXpath) {
					columnValue = driver
							.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]", tableBody, i, columnindex)));
				} else {
					columnValue = driver.findElement(By.cssSelector(String.format(
							"%s> table > tbody > tr:nth-child(%s) > td:nth-child(%s)", tableBody, i, columnindex)));
				}
				if (columnValue.getText().equals(value)) {
					row = i;
					break;
				}
			}
		}
		return row;
	}
	public boolean isValueExisting(int columnindex, String value) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "")));
		// int row = 0;
		WebElement baseTable = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + ""));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			WebElement columnValue = driver
					.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[" + columnindex + "]"));
			if (columnValue.getText().equals(value)) {

				return true;
			}

		}
		return false;
	}

	public void clickDocType(String tableCSS, String value) {
		// int row = 0;
		WebElement baseTable = driver.findElement(By.cssSelector(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 0; i < sumRow; i++) {
			String DocTypevalue = driver.findElement(By.id("docTypeBtn" + i)).getText();
			if (DocTypevalue.equals(value)) {
				driver.findElement(By.id("docTypeBtn" + i)).click();
				// row=i;
				break;
			}
		}

	}

	// Select User Group in the grid in Create User page (Customer account)
	public void selectUserGroup(String xPath, String expectedValue) {
		WebElement baseTable = driver.findElement(By.xpath(xPath));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			String foundValue = driver.findElement(By.xpath(xPath + "//tr[" + i + "]//td[2]")).getText();
			if (foundValue.equals(expectedValue)) {
				driver.findElement(By.xpath(xPath + "//tr[" + i + "]//td[1]")).click();
				break;
			}
		}

	}

	// Click User Number in Maintain Customer User (Customer account)
	public void clickUserNumber(String value) {
		// int row = 0;
		WebElement baseTable = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + ""));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			String foundValue = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[3]"))
					.getText();
			if (foundValue.equals(value)) {
				i = i - 1;
				WebElement usrSequenceIdStrBtn = driver.findElement(By.id("usrSequenceIdStrBtn" + i));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", usrSequenceIdStrBtn);
				usrSequenceIdStrBtn.click();
				break;
			}
		}
	}

	// Click User Number in Maintain Customer User (Customer account)
	public void clickUserNo(int row) {
		WebElement usrSequenceIdStrBtn = driver.findElement(By.id("usrSequenceIdStrBtn" + (row - 1)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", usrSequenceIdStrBtn);
		usrSequenceIdStrBtn.click();
	}

	// Click User Number in Maintain Customer User (Customer account)
	public void clickSupplierIDInSupplierListGrid(String value) {
		// int row = 0;
		WebElement baseTable = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + ""));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			String foundValue = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[3]"))
					.getText();
			if (foundValue.equals(value)) {
				i = i - 1;
				driver.findElement(By.id("spIdBtn" + i)).click();
				break;
			}
		}
	}

	public int countRow(String tableCSS) {
		WebElement baseTable = driver.findElement(By.cssSelector(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		return sumRow;
	}

	public void selectRow(int rowIndex) {
		WebElement row = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + rowIndex + "]"));
		row.click();
	}

	public void filterPermission(String filterValue) {
		inputFilter(filterValue, Permissions.PERMISSION_NAME_FILTER, true);
	}

	public void filter(String columnXpath, String filterValue) {
		inputFilter(filterValue, columnXpath, true);
	}

	public void inputFilter(String value) {
		inputFilter(value, ScreenObjects.FILTER_FIELD_ID, false);
	}

	public void inputFilter(String value, String filterXPath) {
		inputFilter(value, ScreenObjects.FILTER_FIELD_ID, true);
	}

	public void inputFilter(String value, String filterPath, Boolean isXpath) {

		WebElement filterButton = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.FILTER_BTN_CSS)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
		filterButton.click();
		WebElement filterColumn;
		if (isXpath) {
			filterColumn = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(filterPath)));
		} else {
			filterColumn = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.id(filterPath)));
		}
		filterColumn.clear();
		filterColumn.sendKeys(value);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void assertRowEqual(String obj, String value, int row) {
		WebElement rowFilter = driver.findElement(By.id(obj + row));
		assertEquals(rowFilter.getText(), value, "Title is wrong");
	}

	public void assertRowEqual(By obj, String value) {
		WebElement rowFilter = driver.findElement(obj);
		assertEquals(rowFilter.getText(), value, "Title is wrong");
	}

	public void assertValueRow(int column, int row, String value) {
		assertValueRow(ScreenObjects.TABLE_BODY_XPATH, column, row, value);
	}

	public void assertValueRow(String tableBodyXpath, int column, int row, String value) {
		WebElement cell = driver
				.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]", tableBodyXpath, row, column)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cell);
		assertEquals(cell.getText(), value);
	}

	public String getValueAllRowchecked(int column, int row) {
		String allValue = "";
		for (int i = 1; i <= row; i++) {
			WebElement cell = driver
					.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[" + column + "]"));
			if (i == row) {
				allValue = allValue + cell.getText();
			} else {
				allValue = allValue + cell.getText() + ", ";
			}
		}
		return allValue;
	}

	public WebElement getCellObject(int column, int row) {
		WebElement cell = driver.findElement(
				By.xpath(String.format("%s//tr[%s]//td[%s]", ScreenObjects.TABLE_BODY_XPATH, row, column)));
		return cell;
	}

	public WebElement getCellObjectSupplierCodeSet(int column, int row) {
		WebElement cell = driver.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]//div//div",
				"//*[@id=\"codeSetGrid-AsnDeliveryCode\"]/div[3]/table/tbody", row, column)));
		// div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']";
		return cell;
	}

	public String getIDValue(int row) {
		return getIDValue(1, row);
	}

	public String getIDValue(int column, int row) {
		return getIDValue(ScreenObjects.TABLE_BODY_XPATH, column, row);
	}

	public String getIDValue(String tableXpath, int column, int row) {
		return getValueRow(String.format("%s//tr[%s]//td[%s]/div/div/span/span", tableXpath, row, column), column, row);
	}

	public String getValueRow(int column, int row) {
		return getValueRow(String.format("%s//tr[%s]//td[%s]", ScreenObjects.TABLE_BODY_XPATH, row, column), column,
				row);
	}

	private String getValueRow(String cellXpath, int column, int row) {
		WebElement cell = driver.findElement(By.xpath(cellXpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cell);
		return cell.getText();
	}

	public String getValueTableHeader(int column) {
		WebElement header = driver
				.findElement(By.xpath(ScreenObjects.TABLE_HEAD_XPATH + "//tr//th[" + column + "]//div[1]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", header);
		return header.getText();
	}
}

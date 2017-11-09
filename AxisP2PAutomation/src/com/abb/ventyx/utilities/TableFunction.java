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
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;

public class TableFunction {
	WebDriver driver;
	ScreenAction action;
	int timeout = 60;

	public TableFunction(WebDriver driver) {
		this.driver = driver;
		action = new ScreenAction(driver);
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
		int sumRow;
		if (isXpath) {
			sumRow = tableRows.size();
		} else {
			sumRow = tableRows.size() - 1;
		}
		if (sumRow > 0) {
			WebElement columnValue;
			for (int i = 1; i <= sumRow; i++) {
				if (isXpath) {
					columnValue = driver.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]", tableBody, i, columnindex)));
				} else {
					columnValue = driver.findElement(
							By.cssSelector(String.format("%s> table > tbody > tr:nth-child(%s) > td:nth-child(%s)", tableBody, i, columnindex)));
				}
				if (columnValue.getText().equals(value)) {
					row = i;
					break;
				}
			}
		}
		return row;
	}

	public int findRealIndexByCell(int row, int column, String indexString) {
		return findRealIndexByCell(getCellObject(row, column), indexString);
	}

	public int findRealIndexByCell(WebElement cell, String indexString) {
		List<WebElement> idLink = cell.findElements(By.cssSelector(String.format("div[id^=%s]", indexString)));
		int indexOfSupplier = -1;
		if (idLink.size() > 0) {
			String stringIDOfSupplier = idLink.get(0).getAttribute("id");
			stringIDOfSupplier = stringIDOfSupplier.substring(stringIDOfSupplier.indexOf(indexString) + indexString.length(),
					stringIDOfSupplier.length());
			indexOfSupplier = Integer.valueOf(stringIDOfSupplier);
		}
		return indexOfSupplier;
	}

	public boolean isValueExisting(int columnindex, String value) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "")));
		// int row = 0;
		WebElement baseTable = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + ""));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			WebElement columnValue = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[" + columnindex + "]"));
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
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("docTypeBtn" + i)));
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
			String foundValue = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[3]")).getText();
			if (foundValue.equals(value)) {
				i = i - 1;
				WebElement usrSequenceIdStrBtn = driver.findElement(By.id("usrSequenceIdStrBtn" + i));
				// action.scrollToElement(usrSequenceIdStrBtn);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", usrSequenceIdStrBtn);
				break;
			}
		}
	}

	// Click User Number in Maintain Customer User (Customer account)
	public void clickUserNo(int row) {
		WebElement usrSequenceIdStrBtn = driver.findElement(By.id("usrSequenceIdStrBtn" + (row - 1)));
		// action.scrollToElement(usrSequenceIdStrBtn);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", usrSequenceIdStrBtn);
	}

	// Click User Number in Maintain Customer User (Customer account)
	public void clickSupplierIDInSupplierListGrid(String value) {
		// int row = 0;
		WebElement baseTable = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + ""));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			String foundValue = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[3]")).getText();
			if (foundValue.equals(value)) {
				i = i - 1;
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("spIdBtn" + i)));
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

	public void filterPermission(String filterPermissionName) {
		inputFilter(filterPermissionName, Permissions.PERMISSION_NAME_FILTER, true);
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

		WebElement filterButton = (new WebDriverWait(driver, timeout))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ScreenObjects.FILTER_BTN_CSS)));

		filterButton.click();
		WebElement filterColumn;
		if (isXpath) {
			filterColumn = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(filterPath)));
		} else {
			filterColumn = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.id(filterPath)));
		}
		action.scrollToElement(filterColumn);
		filterColumn.clear();
		filterColumn.sendKeys(value);
		action.pause(1000);

	}
	
	public void inputFilterAtIndex(String value, String filterPath, Boolean isXpath) {

		WebElement filterColumn;
		if (isXpath) {
			filterColumn = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(filterPath)));
		} else {
			filterColumn = (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.id(filterPath)));
		}
		action.scrollToElement(filterColumn);
		filterColumn.clear();
		filterColumn.sendKeys(value);
		action.pause(1000);

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
		WebElement cell = driver.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]", tableBodyXpath, row, column)));
		// action.scrollToElement(cell);
		assertEquals(cell.getText(), value);
	}

	public String getValueAllRowchecked(int column, int row) {
		String allValue = "";
		for (int i = 1; i <= row; i++) {
			WebElement cell = driver.findElement(By.xpath(ScreenObjects.TABLE_BODY_XPATH + "//tr[" + i + "]//td[" + column + "]"));
			if (i == row) {
				allValue = allValue + cell.getText();
			} else {
				allValue = allValue + cell.getText() + ", ";
			}
		}
		return allValue;
	}

	public WebElement getCellObject(int row, int column) {
		WebElement cell = driver.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]", ScreenObjects.TABLE_BODY_XPATH, row, column)));
		action.scrollToElementWithColumnNo(cell, column);
		return cell;
	}

	public WebElement getCellObject(String tableXpath, int row, int column) {
		WebElement cell = driver.findElement(By.xpath(String.format("%s/tr[%s]/td[%s]/div/div", tableXpath, row, column)));
		return cell;
	}

	public WebElement getCellObjectUserGroup(String tableXpath, int row, int column) {
		WebElement cell = driver.findElement(By.xpath(String.format("%s/tr[%s]/td[%s]", tableXpath, row, column)));
		action.scrollToElement(cell);
		return cell;
	}

	public WebElement getCellObjectSupplierCodeSetDeliveryCode(int row, int column) {
		WebElement cell = driver
				.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]//div//div", ScreenObjects.BUSINESS_CODE_SET_DELIVERY_CODE, row, column)));
		return cell;
	}

	public WebElement getCellObjectSupplierCodeSetTaxCode(int row, int column) {
		WebElement cell = driver
				.findElement(By.xpath(String.format("%s//tr[%s]//td[%s]//div//div", ScreenObjects.BUSINESS_CODE_SET_TAX_CODE, row, column)));
		return cell;
	}

	// In User Group grid.
	public void clickArrowDownToShowPermission(int row, int column) {
		WebElement cell = driver
				.findElement(By.xpath(String.format("%s/tr[%s]/td[%s]/div/div/span/span", UserGroup.USERGROUP_GRID_XPATH, row, column)));
		cell.click();
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
		return getValueRow(String.format("%s//tr[%s]//td[%s]", ScreenObjects.TABLE_BODY_XPATH, row, column), column, row);
	}

	private String getValueRow(String cellXpath, int column, int row) {
		WebElement cell = driver.findElement(By.xpath(cellXpath));
		action.scrollToElementWithColumnNo(cell, column);
		return cell.getText();
	}

	public String getValueTableHeader(int column) {
		WebElement header = driver.findElement(By.xpath(ScreenObjects.TABLE_HEAD_XPATH + "//tr//th[" + column + "]//div[1]"));
		action.clickHorizontalScrollBarToElement(header);
		return header.getText();
	}

	// Check Permission under document type in User Group grid
	public boolean isPermissionExisting(String permissionName, int docIndex) {

		int i = findRowByString("//*[@id='permGrid-" + docIndex + "']//div[3]//table//tbody", 2, permissionName, true);
		System.out.println("Permission Index 1:" + i);

		WebElement baseTable = driver.findElement(By.xpath("//*[@id='permGrid-" + docIndex + "']//div[3]//table//tbody"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		System.out.println("Sum Row:" + sumRow);
		int row = 5;
		while (i == -1 && row <= sumRow) {
			action.pause(2000);
			WebElement element = driver.findElement(By.xpath("//*[@id='permGrid-" + docIndex + "']//div[3]//table//tbody//tr[" + row + "]"));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true)", element);
			i = findRowByString("//*[@id='permGrid-" + docIndex + "']//div[3]//table//tbody", 2, permissionName, true);
			row = row + 3;
		}
		System.out.println("Permission Index 2:" + i);
		/*
		 * assertEquals(action.isElementPresent(By.xpath("//*[@id='permGrid-" + docIndex
		 * + "']//div[3]//table//tbody//tr[" + i + "]//td[2]")), true,
		 * "Can't find permission, i=-1");
		 */
		// action.assertTextEqual(By.xpath("//*[@id='permGrid-" + docIndex +
		// "']//div[3]//table//tbody//tr[" + i + "]//td[2]"), permissionName);

		if (action.isElementPresent(By.xpath("//*[@id='permGrid-" + docIndex + "']//div[3]//table//tbody//tr[" + i + "]//td[2]"))
				&& driver.findElement(By.xpath("//*[@id='permGrid-" + docIndex + "']//div[3]//table//tbody//tr[" + i + "]//td[2]")).getText()
						.equals(permissionName))
			return true;
		return false;

	}
}

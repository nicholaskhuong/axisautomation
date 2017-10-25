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

	public int findRowByString(String tableCSS, String value, int columnindex) {
		int row = 0;
		WebElement baseTable = driver.findElement(By.cssSelector(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i < sumRow; i++) {
			WebElement columnValue = driver.findElement(By.cssSelector(tableCSS + "> table > tbody > tr:nth-child(" + i + ") > td:nth-child("
					+ columnindex + ")"));
			if (columnValue.getText().equals(value)) {
				row = i;
				break;
			}

		}
		return row;

	}

	public int findRowByString1(int columnindex, String value) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']")));
		int row = 0;
		WebElement baseTable = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			WebElement columnValue = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + i
					+ "]//td[" + columnindex + "]"));
			System.out.println("Value " + columnValue.getText());
			if (columnValue.getText().equals(value)) {
				System.out.print("Value1 " + columnValue.getText());
				row = i;
				break;
			}

		}
		return row;
	}

	public boolean isValueExisting(int columnindex, String value) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']")));
		// int row = 0;
		WebElement baseTable = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			WebElement columnValue = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + i
					+ "]//td[" + columnindex + "]"));
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
		WebElement baseTable = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			String foundValue = driver.findElement(
					By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + i + "]//td[3]")).getText();
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
		WebElement baseTable = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		for (int i = 1; i <= sumRow; i++) {
			String foundValue = driver.findElement(
					By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + i + "]//td[3]")).getText();
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
		WebElement row = driver
				.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + rowIndex + "]"));
		row.click();
	}

	public void filterPermission(String filterValue) {
		// Click Filter Icon
		WebElement filterButton = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.FILTER_BTN_CSS)));
		filterButton.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
		// Enter filter value
		WebElement filterPermissionName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(Permissions.PERMISSION_NAME_FILTER)));
		filterPermissionName.sendKeys(filterValue);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void filter(String columnXpath, String filterValue) {
		// Click Filter Icon
		WebElement filterButton = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.FILTER_BTN_CSS)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
		filterButton.click();

		// Enter filter value
		WebElement filterPermissionName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
.xpath(columnXpath)));
		filterPermissionName.sendKeys(filterValue);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputFilter(String value) {

		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		ScreenAction action = new ScreenAction(driver);
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, value);

	}

	public void inputFilter(String value, String filterString) {

		WebElement filterButton = driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		filterButton.click();
		WebElement filterColumn = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(filterString)));
		filterColumn.clear();
		filterColumn.sendKeys(value);

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
		WebElement cell = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + row + "]//td["
				+ column + "]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cell);
		assertEquals(cell.getText(), value);
	}

	public String getValueAllRowchecked(int column, int row) {
		String allValue = "";
		for (int i = 1; i <= row; i++) {
			WebElement cell = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + i
					+ "]//td[" + column + "]"));
			if (i == row) {
				allValue = allValue + cell.getText();
			} else {
				allValue = allValue + cell.getText() + ", ";
			}
		}
		return allValue;
	}

	public String getValueRow(int column, int row) {
		WebElement cell = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr[" + row + "]//td["
				+ column + "]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cell);
		return cell.getText();
	}

	public String getValueTableHeader(int column) {
		WebElement header = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//thead[@class='v-grid-header']//tr//th[" + column
				+ "]//div[1]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", header);
		return header.getText();
	}
}

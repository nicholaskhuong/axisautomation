package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ScreenAction {
	WebDriver driver;

	public ScreenAction(WebDriver driver) {
		this.driver = driver;
	}

	public void waitObjInvisible(By obj) {
		(new WebDriverWait(driver, 90)).until(ExpectedConditions.invisibilityOfElementLocated(obj));
	}

	public static void waitObjVisible(WebDriver driver, By obj) {
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(obj));
	}

	public void waitObjVisibleAndClick(By obj) {
		WebElement element = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(obj));
		element.click();

	}

	public void clickBtn(By obj) {
		WebElement btn = driver.findElement(obj);
		btn.click();
	}

	public void inputTextField(String obj, String value) {
		WebElement txtField = driver.findElement(By.id(obj));
		txtField.clear();
		txtField.sendKeys(value);
	}

	public void clickCheckBoxN(int n) {
		List<WebElement> listCheckbox = driver.findElements(By.xpath("//input[@type='checkbox']"));
		listCheckbox.get(n).click();
	}

	public void checkObjSelected(int start, int end) {
		List<WebElement> listCheckbox = driver.findElements(By.xpath("//input[@type='checkbox']"));
		for (int i = start; i < end; i++) {
			listCheckbox.get(i).isSelected();
		}
	}

	public boolean checkObjSelected(int start) {
		boolean isSelected = false;
		List<WebElement> listCheckbox = driver.findElements(By.xpath("//input[@type='checkbox']"));
		isSelected = listCheckbox.get(start).isSelected();
		return isSelected;

	}

	public void assertMessgeError(String msgCSS, String msg) {
		WebElement error = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(msgCSS)));
		Assert.assertEquals(error.getText(), msg);
	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			WebElement error = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));
			return error.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}

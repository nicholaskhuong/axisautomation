package com.abb.ventyx.axis.objects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserApplicationPage extends BasePage{

	public UserApplicationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public UserApplicationPage() {
		super(BasePage.driver);
	}
	
	public void selectApplication(String applicationName){
		driver.findElement(By.linkText(applicationName)).click();
	}
	
	public boolean isApplicationExist(String applicationName){
		try{
			WebElement theApplication = driver.findElement(By.linkText(applicationName));
			return theApplication.isDisplayed();
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}

}

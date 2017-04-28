package com.abb.ventyx.axis.objects.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.utilities.Constants;
import com.abb.ventyx.utilities.InputController;
import com.abb.ventyx.utilities.TestLoginCredentials;



public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	
	public LoginPage() {
		super(BasePage.driver);
	}
	
	public void login(String url,String username, String password){
		driver.navigate().to(url);
		WebElement userNameTextField = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID)));
		InputController.inputToTextFiled(userNameTextField, username);
		InputController.inputToTextFiled(getPasswordTextField(), password);
		getLoginButton().click();
	//	getContinueButtonClick();
	}
	
	public void login(String url,TestLoginCredentials credential){
		login(url,credential.getUsername(), credential.getPassword());
	}
	// Get ELELEMENTS
	
	public static WebElement getUsernameTextField(){
		return driver.findElement(By.id(LoginPageDefinition.USERNAME_TEXT_FIELD_ID));
	}
	
	public static WebElement getPasswordTextField(){
		return driver.findElement(By.id(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID));
	}
	
	public static WebElement getLoginButton(){
		return driver.findElement(By.xpath("//div[@id='signInBtn']"));
	}
	
	public static void getContinueButtonClick(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
	    WebElement continueButton = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id(LoginPageDefinition.CONTINUE_BUTTON_ID)));
	    js.executeScript("arguments[0].click();", continueButton);
	}
}

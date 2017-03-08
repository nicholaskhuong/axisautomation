package com.abb.ventyx.axis.objects.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.abb.ventyx.axis.objects.pagedefinitions.HomePageDefinition;
import com.abb.ventyx.utilities.Constants;
import com.abb.ventyx.utilities.InputController;
public class HomePage extends BasePage{

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	public void startHomePage(){
		try{
			driver.manage().window().maximize();
		}
		catch (UnsupportedCommandException e) {
			// TODO: handle exception
		}
		
		driver.navigate().to(Constants.HOME_URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	public void login(String email){
		inputEmailAddress(email);
		clickToSignInButton();
	}
	
	public void verifyCode(String theCode){
		WebElement vCode = driver.findElement(By.id(HomePageDefinition.VCODE_TEXTFIELD_ID));
		InputController.inputToTextFiled(vCode, theCode);
		clickVerifyButton();
	}
	
	public void inputEmailAddress(String email){
		WebElement emailField = driver.findElement(By.id(HomePageDefinition.EMAIL_TEXTFIELD_ID));
		InputController.inputToTextFiled(emailField, email);
	}
	
	public void clickToSignInButton(){
		driver.findElement(By.id(HomePageDefinition.LOGIN_BUTTON_ID)).click();
	}
	
	public boolean isErrorAppear(){
		try{
			WebElement errorPopup = driver.findElement(By.className("v-Notification-caption"));
			return errorPopup.isDisplayed();
		}
		catch (NoSuchElementException e) {
			return false;
		}
		
	}
	
	public void clickVerifyButton(){
		driver.findElement(By.id(HomePageDefinition.VERIFY_BUTTON_ID)).click();
	}
	
	public void loginWithCorrectEmail(){
		login("u@example.com");
		verifyCode("u");
	}
}

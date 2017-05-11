package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisAdministratorUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportCustomerUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;

import org.testng.annotations.Test;

@ALM(id = "162") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Users_Creating extends BaseTestCase{

	String SYSTEM_GROUP_NAME="CUST_ADMIN";
	String CUSTOMER_NAME="Tanya Customer 11";
	String USER_GROUP_NAME="Manager Group";
	BaseDropDownList list;
	int row;
	
	@Test
	  public void checkScreen() {
    	 WebElement axisAdminMenu = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.AXIS_ADMINISTRATION_MENU_ID)));
    	 axisAdminMenu.click();
		 WebElement usersMenu = (new WebDriverWait(driver, 15))
					.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.USERS_SUBMENU_ID)));
		 usersMenu.click();	  
		 (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID+"0")));
		 WebElement screenTitle=(new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.SCREEN_TITLE_ID)));
		 assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_TITLE, "Title is wrong");
	  }
	
	@Test(dependsOnMethods = "checkScreen")
	public void addWithoutData (){ 
		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH)).click();
		WebElement saveBtn = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.SAVE_ID)));
		WebElement screenTitle=driver.findElement(By.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_CREATE_TITLE, "Title is wrong");
		saveBtn.click();
		//Don't input all field
		WebElement warningMessage = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage.getText(), Messages.ENTER_MANDATORY_FIELDS);
		
		//Don't input User
		WebElement userId=driver.findElement(By.id(AxisAdministratorUsers.USER_ID));
		userId.clear();	
		WebElement email=driver.findElement(By.id(AxisAdministratorUsers.EMAIL_ID));
		email.sendKeys("email@dathy.com");
		WebElement passWord=driver.findElement(By.id(AxisAdministratorUsers.PASSWORD_ID));
		passWord.sendKeys("TestUser1");
		WebElement confirmPassWord=driver.findElement(By.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPassWord.sendKeys("TestUser1");
		saveBtn.click();
		WebElement warningMessage1 = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage1.getText(), Messages.ENTER_MANDATORY_FIELDS);
		
		//Don't input PWD		
		email.clear();
		userId.clear();
		userId.sendKeys("DathyUser1");
		passWord.clear();
		email.sendKeys("email@dathy.com");
		confirmPassWord.clear();
		confirmPassWord.sendKeys("TestUser1");
		saveBtn.click();
		WebElement warningMessage2 = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage2.getText(), Messages.ENTER_MANDATORY_FIELDS);
		
		//Don't input email		
		userId.clear();
		userId.sendKeys("DathyUser1");
		passWord.clear();
		passWord.sendKeys("TestUser1");
		confirmPassWord.clear();
		confirmPassWord.sendKeys("TestUser1");
		email.clear();
		saveBtn.click();
		WebElement warningMessage3 = (new WebDriverWait(driver, 10))
			  	.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage3.getText(), Messages.ENTER_MANDATORY_FIELDS);	
		
		//Don't input confirm password
		
		userId.clear();
		userId.sendKeys("DathyUser1");
		email.clear();
		email.sendKeys("email@dathy.com");
		passWord.clear();
		passWord.sendKeys("TestUser1");
		confirmPassWord.clear();
		saveBtn.click();
		WebElement warningMessage4 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage4.getText(), Messages.ENTER_MANDATORY_FIELDS);	
				
	}

	//Click Cancel do it again
	@Test(dependsOnMethods = "addWithoutData")
	public void cancelWithoutData (){ 
		
		WebElement userId=driver.findElement(By.id(AxisAdministratorUsers.USER_ID));
		userId.clear();	
		WebElement email=driver.findElement(By.id(AxisAdministratorUsers.EMAIL_ID));
		email.clear();
		WebElement passWord=driver.findElement(By.id(AxisAdministratorUsers.PASSWORD_ID));
		passWord.clear();
		WebElement confirmPassWord=driver.findElement(By.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPassWord.clear();
		WebElement cancelBtn=driver.findElement(By.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();
		(new WebDriverWait(driver, 20))
		 .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID+"0")));
		WebElement screenTitle=driver.findElement(By.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_TITLE);
	}
	
	@Test(dependsOnMethods = "cancelWithoutData")
	public void addWithoutUserGroup (){ 
		
		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH)).click();
		WebElement saveBtn = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.SAVE_ID)));
		
		//Don't select User Group
		int n=new Random().nextInt(1000);
	
		WebElement userId=driver.findElement(By.id(AxisAdministratorUsers.USER_ID));
		userId.sendKeys("DathyUser"+n);
		WebElement email=driver.findElement(By.id(AxisAdministratorUsers.EMAIL_ID));
		email.sendKeys("email"+n+ "@dathy.com");
		WebElement passWord=driver.findElement(By.id(AxisAdministratorUsers.PASSWORD_ID));
		passWord.sendKeys("TestUser1");
		WebElement confirmPassWord=driver.findElement(By.id(AxisAdministratorUsers.CONFIRM_PASSWORD_ID));
		confirmPassWord.sendKeys("TestUser1");		
		saveBtn.click();
		WebElement warningMessage5 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(warningMessage5.getText(), Messages.USER_SELECT_USERGROUP);	
	}
	
	@Test(dependsOnMethods = "addWithoutUserGroup")
	public void addWithValidData () throws InterruptedException{ 
		List<WebElement> list= driver.findElements(By.className(AxisAdministratorUsers.USERGROUP_CHECKBOX_CLASS));
		list.get(1).click();
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement sucessMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.SUCCESS_MESSAGE)));
		assertEquals(sucessMessage.getText(), Messages.USER_CREATE_SUCCESSFULLY);	
	}
		
	@Test(dependsOnMethods = "addWithValidData")
	public void cancelWithYes () throws InterruptedException{ 
		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH)).click();
		WebElement userId = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.USER_ID)));
		userId.sendKeys("DathyUser1");
		WebElement cancelBtn=driver.findElement(By.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();
		//Click Yes
		(new WebDriverWait(driver, 20))
		 	.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.CONFIRMATION)));
		driver.findElement(By.id(Messages.YES_BTN_ID)).click();
		(new WebDriverWait(driver, 20))
		 .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.ROW_ID+"0")));
		WebElement screenTitle=driver.findElement(By.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_TITLE);
				
	}
	
	@Test(dependsOnMethods = "cancelWithYes")
	public void cancelClickNo () { 
		
		driver.findElement(By.xpath(AxisAdministratorUsers.ADD_BTN_XPATH)).click();
		WebElement userId = (new WebDriverWait(driver, 15))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisAdministratorUsers.USER_ID)));
		userId.sendKeys("DathyUser1");
		WebElement cancelBtn=driver.findElement(By.id(AxisAdministratorUsers.CANCEL_ID));
		cancelBtn.click();
		
		WebElement msgDialog= (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.CONFIRMATION)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);	
		driver.findElement(By.id(Messages.NO_BTN_ID)).click();
		WebElement screenTitle=driver.findElement(By.id(AxisAdministratorUsers.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisAdministratorUsers.SCREEN_CREATE_TITLE);			
	}
	
}


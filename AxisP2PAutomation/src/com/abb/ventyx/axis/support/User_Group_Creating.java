package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportCustomerUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.utilities.ALM;

import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;


import org.testng.annotations.Test;

@ALM(id = "162") 
@Credentials(user = "5", password = "testuser")
public class User_Group_Creating extends BaseTestCase{

	String SYSTEM_GROUP_NAME="CUST_ADMIN";
	String CUSTOMER_NAME="Tanya Customer 11";
	String USER_GROUP_NAME="Manager Group";
	JavascriptExecutor js = (JavascriptExecutor)driver;
	
	@Test
	  public void checkScreen() throws Exception {
    	   WebElement customerConfiguration = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportCustomerUserGroup.CUSTOMERMAINTAINCE_MENU_ID)));
		   customerConfiguration.click();
		   WebElement userGroupsMenu = (new WebDriverWait(driver, 15))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportCustomerUserGroup.USERGROUP_SUBMENU_ID)));
		   userGroupsMenu.click();	  
		   (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportCustomerUserGroup.SYSTEM_TAB_ID)));
		   WebElement screenTitle=(new WebDriverWait(driver, 10))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID)));
		   assertEquals(screenTitle.getText(), AxisSupportCustomerUserGroup.SCREEN_TITLE, "Title is wrong");
		   String system_row0= driver.findElement(By.id(AxisSupportCustomerUserGroup.ROW0_ID)).getText();
		   assertEquals(system_row0, SYSTEM_GROUP_NAME);
		   driver.findElement(By.id(AxisSupportCustomerUserGroup.USER_TAB_ID)).click();

	  }
	
	@Test(dependsOnMethods = "checkScreen")
	public void addWithoutCustomer (){ 
		 WebElement addBtn = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath(AxisSupportCustomerUserGroup.ADD_XPATH)));
		 addBtn.click();
		 WebElement warningMessage = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Messages.WARNING_MESSAGE_XPATH)));
		 assertEquals(warningMessage.getText(), Messages.USERGROUP_SELECT_CUSTOMER);	  	
	}
	
	@Test(dependsOnMethods = "addWithoutCustomer")
	public void addWithSelectedCustomerNoUserGroupName () throws Exception {
		WebElement customer=driver.findElement(By.className("v-filterselect-input"));
		customer.sendKeys(CUSTOMER_NAME);
		driver.wait(10);
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ARROW_DOWN).build().perform();
		builder.wait(10);
		builder.sendKeys(Keys.ENTER).build().perform();
	    (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportCustomerUserGroup.ROW0_ID)));
		WebElement addBtn= driver.findElement(By.xpath(AxisSupportCustomerUserGroup.ADD_XPATH));
		addBtn.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		WebElement screenTitle=driver.findElement(By.id(AxisSupportCustomerUserGroup.SCREEN_TITLE_ID));
	    assertEquals(screenTitle.getText(), AxisSupportCustomerUserGroup.SCREEN_CREATE_TITLE, "Title is wrong");
	    driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
	    WebElement errorMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)));
		assertEquals(errorMessage.getText(), Messages.ENTER_MANDATORY_FIELDS);	  	
	}
	
	@Test(dependsOnMethods = "addWithSelectedCustomerNoUserGroupName")
	public void addWithSelectedCustomerHasUserGroupName (){
		
		WebElement userGroupName = driver.findElement(By.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID));
		userGroupName.sendKeys(USER_GROUP_NAME);
		List<WebElement> list= driver.findElements(By.className("v-grid-selection-checkbox"));
		list.get(0).click();
		driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID)).click();
		WebElement sucessMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.SUCCESS_MESSAGE)));
		assertEquals(sucessMessage.getText(), Messages.USERGROUP_CREATE_SUCCESSFULLY);	
	}
	
	
}



package com.abb.ventyx.axis.support;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportCustomerUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;

import org.testng.Assert;
import org.testng.annotations.Test;
@ALM(id = "1") 
@Credentials(user = "5", password = "testuser")
public class Create_User_Group extends BaseTestCase{
	
	String USER_GROUP_NAME="Manage Group";
	@Test
	  public void testCreate_User_Group() throws Exception {
		  	JavascriptExecutor js = (JavascriptExecutor)driver;
//		    WebElement customerConfiguration = (new WebDriverWait(driver, 20))
//		  	.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.CONFIGURATION_MENU_CSS)));
//		    customerConfiguration.click();
//		    WebElement userGroupsMenu= (new WebDriverWait(driver, 20))
//				  	.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.USERGROUP_SUBMENU_CSS)));
//		    userGroupsMenu.click();
		    WebElement addUserGroup = (new WebDriverWait(driver, 20))
		  	.until(ExpectedConditions.presenceOfElementLocated(By.xpath(AxisSupportCustomerUserGroup.ADD_XPATH)));
		    js.executeScript("arguments[0].click();", addUserGroup);  
		    WebElement userGroupName = (new WebDriverWait(driver, 20))
				  	.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportCustomerUserGroup.USERGROUP_NAME_ID)));
		    userGroupName.clear();
		    userGroupName.sendKeys(USER_GROUP_NAME);
		    List <WebElement> listCheckbox= driver.findElements(By.xpath("//input[@type='checkbox']"));
		    listCheckbox.get(1).click();
		    listCheckbox.get(2).click();
		    listCheckbox.get(3).click();
		    WebElement savebtn=driver.findElement(By.id(AxisSupportCustomerUserGroup.SAVE_ID));
		    savebtn.click();
		    WebElement flashMessage1 = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE)));
		    Assert.assertEquals(flashMessage1.getText(), Messages.USERGROUP_CREATE_SUCCESSFULLY);	   	    
	  }
	
}

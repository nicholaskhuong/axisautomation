package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisSupportUserGroup;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;

import org.testng.Assert;
import org.testng.annotations.Test;
@ALM(id = "162") 
@Credentials(user = "5", password = "testuser")
public class User_Group_Creating extends BaseTestCase{

	String USER_GROUP_NAME="Manage Group";
	JavascriptExecutor js = (JavascriptExecutor)driver;
	@Test
	  public void create_User_Group() throws Exception {
		  	
		   WebElement customerConfiguration = (new WebDriverWait(driver, 20))
				  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.CONFIGURATION_MENU_CSS)));
		   customerConfiguration.click();
		   WebElement userGroupsMenu= (new WebDriverWait(driver, 20))
				  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.USERGROUP_SUBMENU_CSS)));
		   userGroupsMenu.click();
		   WebElement addUserGroup = (new WebDriverWait(driver, 20))
				  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.ADD_CSS)));
		   WebElement screenTitle=driver.findElement(By.cssSelector(AxisSupportUserGroup.SCREEN_TITLE_CSS));
		   assertEquals(screenTitle.getText(), AxisSupportUserGroup.SCREEN_TITLE);
		   js.executeScript("arguments[0].click();", addUserGroup);  
		   WebElement userGroupName = (new WebDriverWait(driver, 20))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.USERGROUP_NAME_ID)));
		   assertEquals(screenTitle.getText(), AxisSupportUserGroup.SCREEN_CREATE_TITLE);
		   
		   //input data
		   userGroupName.clear();
		   userGroupName.sendKeys(USER_GROUP_NAME);
		   List <WebElement> listCheckbox= driver.findElements(By.xpath("//input[@type='checkbox']"));
		   listCheckbox.get(1).click();
		   listCheckbox.get(2).click();
		   listCheckbox.get(3).click();
		   WebElement saveBtn=driver.findElement(By.id(AxisSupportUserGroup.Save_ID));
		   saveBtn.click();   	    
	  }
	
	@Test(dependsOnMethods = "create_User_Group")
	public void checkSucessMessage (){ 
		 WebElement flashMessage1 = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE)));
		 Assert.assertEquals(flashMessage1.getText(), Messages.USERGROUP_CREATE_SUCCESSFULLY);	  	
	}
	
	@Test(dependsOnMethods = "checkMessage")
	public void checkDataExisting (){ 
		WebElement filter=driver.findElement(By.xpath(AxisSupportUserGroup.FILTERNAME_CSS)) ; 
		filter.click();
		filter.clear();
		filter.sendKeys(USER_GROUP_NAME);
		BaseGrid grid=new BaseGrid(driver,AxisSupportUserGroup.USERGROUP_GRID_XPATH);
		int row=grid.findItemByColumnName(AxisSupportUserGroup.COLUMN1, USER_GROUP_NAME);
		String userGroupName=grid.getGridCellByColumnName(AxisSupportUserGroup.COLUMN1, row);
		assertEquals(userGroupName, USER_GROUP_NAME);
		
	}
	
	@Test(dependsOnMethods = "checkDataExisting")
	public void createWithInvalidData (){ 
		 WebElement addUserGroup = (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.ADD_CSS)));
		 js.executeScript("arguments[0].click();", addUserGroup);  
		 WebElement userGroupName = (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.USERGROUP_NAME_ID)));
		 userGroupName.clear();
		 WebElement saveBtn=driver.findElement(By.id(AxisSupportUserGroup.Save_ID));
		saveBtn.click();   	    	
	}
	
	@Test(dependsOnMethods = "createWithInvalidData")
	public void checkInvalidMessage (){ 
		 WebElement flashMessage1 = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE)));
		 assertEquals(flashMessage1.getText(), Messages.ENTER_MANDATORY_FIELDS);	  	
	}
	
	@Test(dependsOnMethods = "checkInvalidMessage")
	public void clickCancel () throws InterruptedException{ 
		driver.findElement(By.id(AxisSupportUserGroup.USERGROUP_NAME_ID)).sendKeys("ABC");
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_ID)).click();

		WebElement msgDialog= (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.CANCEL_CONTENT_CSS)));
		assertEquals(msgDialog.getText(), Messages.UNSAVED_CHANGE);	
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_NO_ID)).click();
		assertNull(msgDialog);
		WebElement screenTitle=driver.findElement(By.cssSelector(AxisSupportUserGroup.SCREEN_TITLE_CSS));
		assertEquals(screenTitle.getText(), AxisSupportUserGroup.SCREEN_CREATE_TITLE);
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_ID)).click();;
		msgDialog.wait(20);
		msgDialog.isDisplayed();
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_YES_ID)).click();
		assertNull(msgDialog);
		(new WebDriverWait(driver, 20))
		 .until(ExpectedConditions.presenceOfElementLocated(By.xpath(AxisSupportUserGroup.USERGROUP_GRID_XPATH)));
		assertEquals(screenTitle.getText(), AxisSupportUserGroup.SCREEN_TITLE);
	}
	
	@Test(dependsOnMethods = "clickCancel")
	public void clickCancelWithoutdata () throws InterruptedException{ 
		driver.findElement(By.cssSelector(AxisSupportUserGroup.ADD_CSS)).click();
		WebElement userGroupName = (new WebDriverWait(driver, 20))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.USERGROUP_NAME_ID)));
		
		userGroupName.clear();;
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_ID)).click();
		(new WebDriverWait(driver, 20))
		 .until(ExpectedConditions.presenceOfElementLocated(By.xpath(AxisSupportUserGroup.USERGROUP_GRID_XPATH)));
		WebElement screenTitle=driver.findElement(By.cssSelector(AxisSupportUserGroup.SCREEN_TITLE_CSS));
		assertEquals(screenTitle.getText(), AxisSupportUserGroup.SCREEN_TITLE);
	}
}

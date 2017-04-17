package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

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
    	   WebElement customerConfiguration = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.CUSTOMERMAINTAINCE_MENU_ID)));
		   customerConfiguration.click();
		   WebElement userGroupsMenu = (new WebDriverWait(driver, 15))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.USERGROUP_SUBMENU_ID)));
		   userGroupsMenu.click();
		   WebElement screenTitle=(new WebDriverWait(driver, 10))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.SCREEN_TITLE_ID)));
		   WebElement addUserGroup=(new WebDriverWait(driver, 15))
					  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.ADD_CSS)));
		   Assert.assertEquals(screenTitle.getText(), "Maintain User Groups", "Title is wrong");
		   addUserGroup.click();
		   WebElement userGroupName = (new WebDriverWait(driver, 15))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.USERGROUP_NAME_ID)));
		   WebElement screenUserGroups =driver.findElement(By.id(AxisSupportUserGroup.SCREEN_TITLE_ID));
		   Assert.assertEquals(screenUserGroups.getText(), AxisSupportUserGroup.SCREEN_CREATE_TITLE);
		   //input dataBy.id
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
	
	@Test(dependsOnMethods = "checkSucessMessage")
	public void checkDataExisting (){ 
		WebElement filterBtn=driver.findElement(By.cssSelector(AxisSupportUserGroup.FILTER_CSS)) ; 
		filterBtn.click();
		WebElement filterText = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.FILTERNAME_ID)));
		filterText.clear();
		filterText.sendKeys(USER_GROUP_NAME);
		List<WebElement> listUserGroups= driver.findElements(By.xpath("tr"));
		for(int i=0; i<listUserGroups.size();i++)
		{
			WebElement UserGroup= driver.findElement(By.xpath(AxisSupportUserGroup.USERGROUP_NAME_LINK_XPATH+String.valueOf(i)+"']"));
			if(UserGroup.getText().equals(USER_GROUP_NAME))
			{
				assertTrue(UserGroup.getText().equals(USER_GROUP_NAME));
				break;
			}
		}
	}
	
	@Test(dependsOnMethods = "checkDataExisting")
	public void createWithInvalidData (){ 
		 WebElement addUserGroup = (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisSupportUserGroup.ADD_CSS)));
		 addUserGroup.click(); 
		 WebElement userGroupName = (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.id(AxisSupportUserGroup.USERGROUP_NAME_ID)));
		 userGroupName.clear();
		 WebElement saveBtn=driver.findElement(By.id(AxisSupportUserGroup.Save_ID));
		saveBtn.click();   	    	
	}
	
	@Test(dependsOnMethods = "createWithInvalidData")
	public void checkInvalidMessage (){ 
		 WebElement flashMessage1 = (new WebDriverWait(driver, 10))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.ENTER_MANDATORY_FIELDS)));
		 assertEquals(flashMessage1.getText(), Messages.ENTER_MANDATORY_FIELDS);	  	
	}
	
	/*@Test(dependsOnMethods = "checkInvalidMessage")
	public void clickCancel () throws InterruptedException{ 
		driver.findElement(By.id(AxisSupportUserGroup.USERGROUP_NAME_ID)).sendKeys("ABC");
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_ID)).click();

		WebElement msgDialog= (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.CONFIRM_MESSAGE)));
		String s=msgDialog.getText();
		assertTrue(msgDialog.getText().contains(Messages.UNSAVED_CHANGE));
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_NO_ID)).click();
		driver.wait(10);
		WebElement Dialog= driver.findElement(By.cssSelector(DocType.CONFIRM_MESSAGE));
		assertNull(Dialog);
		WebElement screenTitle=driver.findElement(By.cssSelector(AxisSupportUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisSupportUserGroup.SCREEN_CREATE_TITLE);
		
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_ID)).click();
		WebElement msgDialog1= (new WebDriverWait(driver, 20))
				 .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.CONFIRM_MESSAGE)));
		msgDialog1.isDisplayed();
		driver.findElement(By.id(AxisSupportUserGroup.CANCEL_YES_ID)).click();
		assertNull(msgDialog1);
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
		WebElement screenTitle=driver.findElement(By.cssSelector(AxisSupportUserGroup.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), AxisSupportUserGroup.SCREEN_TITLE);
	}*/
	
}



package com.abb.ventyx.axis.support;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DialogBtns;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.SearchOption;
@ALM(id = "158") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Search_Option_Creating extends BaseTestCase {

	String FIELD_TYPE = "QUANTITY";
	String FILTER_SUB_TYPE = "UNIT";
	String OPTION = "TEST_SEARCH";
	BaseDropDownList list;
	int row;
	BaseGrid grid;
	@Test
	public void filterFieldSearchOptionAdd(){
		   // Create Filter Field
			WebElement axisConfigParentButton = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		    WebElement axisFilterConfig = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.FILTER_CONFIG)));
		    axisFilterConfig.click();
		    WebElement axisFilterField = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.SEARCH_OPTION)));
		    axisFilterField.click();
		    WebElement searchOptionAdd = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.xpath(SearchOption.ADD_xpath)));
		    searchOptionAdd.click();
	
	}  
	
	 @Test(dependsOnMethods = "filterFieldSearchOptionAdd")
	 public void inputFields (){
		 
		    WebElement fieldType = (new WebDriverWait(driver, 80))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.FIELD_TYPE_ADD)));
		    fieldType.click();
		    fieldType.sendKeys(FIELD_TYPE);
		    WebElement fieldSubType = (new WebDriverWait(driver, 80))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.FILTER_SUB_TYPE_ADD)));
		    fieldSubType.click();
		    fieldSubType.sendKeys(FILTER_SUB_TYPE);
		    WebElement option = (new WebDriverWait(driver, 80))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.OPTION_ADD)));
		    option.click();
		    option.sendKeys(OPTION);
		    WebElement save = (new WebDriverWait(driver, 80))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.SAVE_ADD)));
		    save.click();
	 }
	 @Test(dependsOnMethods = "inputFields")
	 public void catchSucessMessage (){
		    WebElement flashMessage = (new WebDriverWait(driver, 60))
	 	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.SEARCH_OPTION_SUCCESSFULLY)));
		    Assert.assertEquals(driver.findElement(By.cssSelector(Messages.SEARCH_OPTION_SUCCESSFULLY)).getCssValue("background-color"), "rgba(33, 190, 137, 1)");
		    Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
		    Assert.assertEquals(flashMessage.getCssValue("display"), "block");
	 	  //  Assert.assertEquals(flashMessage.getText(), Messages.DOC_TYPE_SEARCH_OPT_SUCCESSFULLY);
		   
			}
 	
}





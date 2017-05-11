

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
		  
		    
	    	
	}  
	
	 @Test(dependsOnMethods = "filterFieldSearchOptionAdd")
	 public void inputFields (){
		  WebElement searchOptionAdd = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SearchOption.ADD)));
		    searchOptionAdd.click();
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
// 
//	@Test(dependsOnMethods = "catchSucessMessage")
//    public void duplicateSearchOption1 (){
//		 WebElement axisFilterConfig1 = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.FILTER_FIELD)));
//		    axisFilterConfig1.click();
//		    WebElement axisFilterField = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.SEARCH_OPTION)));
//		    axisFilterField.click();
//		     WebElement searchOptionAdd = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SearchOption.ADD)));
//		    searchOptionAdd.click();
//		    WebElement fieldType = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.FIELD_TYPE_ADD)));
//		    fieldType.click();
//		    fieldType.sendKeys(FIELD_TYPE);
//		    WebElement fieldSubType = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.FILTER_SUB_TYPE_ADD)));
//		    fieldSubType.click();
//		    fieldSubType.sendKeys(FILTER_SUB_TYPE);
//			}
//     @Test(dependsOnMethods = "duplicateSearchOption1")
//	 public void duplicateSearchOption2 (){
//		    WebElement option = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.OPTION_ADD)));
//		    option.click();
//		    option.sendKeys(OPTION);
//		    WebElement save = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.SAVE_ADD)));
//		    save.click();
//	}
//     
//	@Test(dependsOnMethods = "duplicateSearchOption2")
//    public void catchErrorMessage (){
//		    
//		    WebElement flashMessage = (new WebDriverWait(driver, 80))
//	 	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.DUPLICATE_FIELD_TYPE_CSS)));
//		    Assert.assertEquals(flashMessage.getText(), Messages.DUPLICATE_FIELD_TYPE);
//		    Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
//		    Assert.assertEquals(flashMessage.getCssValue("display"), "block");
//	}
//	@Test(dependsOnMethods = "catchErrorMessage")
//    public void testUnsavedChange (){
//		    
//		    WebElement cancel = (new WebDriverWait(driver, 80))
//		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(SearchOption.CANCEL_ADD)));
//		    cancel.click();
//		    WebElement unsavedChange = (new WebDriverWait(driver, 80))
//					  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Messages.UNSAVED_CHANGE_CSS)));
//		    unsavedChange.getText();
//	    	Assert.assertEquals(unsavedChange.getText(), Messages.UNSAVED_CHANGE);
//	    	WebElement yesBtn = (new WebDriverWait(driver, 60))
//					  .until(ExpectedConditions.presenceOfElementLocated(By.id(DialogBtns.YES)));
//	    	yesBtn.click();
//	}
//	@Test(dependsOnMethods = "testUnsavedChange")
//    public void testFilterOnGrid (){
//   	 WebElement searchOptionFilter = (new WebDriverWait(driver, 80))
//	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SearchOption.FILTER)));
//	 searchOptionFilter.click();
//	 WebElement field_Type_Filter = (new WebDriverWait(driver, 80))
//	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SearchOption.FIELD_TYPE_FILTER)));
//	 field_Type_Filter.click();
//	 WebElement field_Type_Filter1 = (new WebDriverWait(driver, 80))
//	  			.until(ExpectedConditions.presenceOfElementLocated(By.id("filterField")));
//	 field_Type_Filter1.sendKeys(FIELD_TYPE);
//
//		   
//	}
//	@Test(dependsOnMethods = "testFilterOnGrid")
//    public void checkOnGrid (){
//	   
//		 grid = new BaseGrid(driver, SearchOption.GRID_CSS);
//		 row = grid.findItemByColumnName("Field Type", FIELD_TYPE);
//		 Assert.assertNotEquals(row, -1, "Record not found"); 
//			
//		    }
	
}





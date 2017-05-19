package com.abb.ventyx.axis.support;

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
import com.abb.ventyx.axis.objects.pagedefinitions.SearchOption;
@ALM(id = "158") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Search_Option_Creating_part3 extends BaseTestCase {

	String FIELD_TYPE = "QUANTITY";
	String FILTER_SUB_TYPE = "UNIT";
	String OPTION = "TEST_SEARCH";
	BaseDropDownList list;
	int row;
	BaseGrid grid;
	@Test
	public void createSearchOption(){
		  // Create Search Option and cancel without saving
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

@Test(dependsOnMethods = "createSearchOption")
public void testFilterOnGrid (){
			WebElement searchOptionFilter = (new WebDriverWait(driver, 80))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.xpath(SearchOption.FILTER)));
			searchOptionFilter.click();
			WebElement field_Type_Filter = (new WebDriverWait(driver, 80))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.xpath(SearchOption.FIELD_TYPE_FILTER)));
			field_Type_Filter.click();
			WebElement field_Type_Filter1 = (new WebDriverWait(driver, 80))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.id("filterField")));
			field_Type_Filter1.sendKeys(FIELD_TYPE);

		   
	}
@Test(dependsOnMethods = "testFilterOnGrid")
public void checkOnGrid (){
	   
//		 grid = new BaseGrid(driver, SearchOption.GRID_CSS);
//		 row = grid.findItemByColumnName("Field Type", FIELD_TYPE);
// 		Assert.assertNotEquals(row, -1, "Record not found"); 
			
		    }
	
}


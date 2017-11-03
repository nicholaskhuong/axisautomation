

package com.abb.ventyx.axis.support;


import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DialogBtns;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.FilterField;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
@ALM(id = "158") 
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Filter_Field_Creating extends BaseTestCase {

	String DOCTYPE_A;
	String DESC_A;
	BaseDropDownList list;
	int row;
	@Test
	public void filterFieldAddInputDocType(){
		   // Create Filter Field
			WebElement axisConfigParentButton = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		    WebElement axisFilterConfig = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.FILTER_CONFIG)));
		    axisFilterConfig.click();
		    WebElement axisFilterField = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.FILTER_FIELD)));
		    axisFilterField.click();
		    WebElement filterFieldAdd = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(FilterField.ADD)));
		    filterFieldAdd.click();
		    WebElement filterFieldDocType = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.DOC_TYPE)));
		    filterFieldDocType.click();
		    list = new BaseDropDownList (driver, FilterField.LIST);
		    row = list.findItemInDropDownList("ABB");
		    WebElement rowClick = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table > tbody > tr:nth-child("+(row-1)+") > td")));
		    rowClick.click();
			}
 
	@Test(dependsOnMethods = "filterFieldAddInputDocType")
    public void inputFieldType (){
		    
		    WebElement filterFieldFieldType = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.FIELD_TYPE)));
		    filterFieldFieldType.click();
		    list = new BaseDropDownList (driver, FilterField.LIST);
		    row = list.findItemInDropDownList("DATE");
		    WebElement rowClick = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table > tbody > tr:nth-child("+(row-1)+") > td")));
		    rowClick.click();
		    }
		    
	@Test(dependsOnMethods = "inputFieldType")
	public void inputColumnName (){
		    
		    WebElement filterFieldColumnName = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.COLUMN_NAME)));
		    filterFieldColumnName.click();
		    list = new BaseDropDownList (driver, FilterField.LIST);
		    row = list.findItemInDropDownList("chgOrderFlg");
		    WebElement rowClick = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table > tbody > tr:nth-child("+(row-1)+") > td")));
		    rowClick.click();
		    }
		    
  @Test(dependsOnMethods = "inputColumnName")
  public void inputFieldName (){
		    WebElement filterFieldFieldName = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.FIELD_NAME)));
		    filterFieldFieldName.click();
		    filterFieldFieldName.sendKeys("Test of Filter Field");
		    
		    WebElement save = (new WebDriverWait(driver, 60))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.ADD_SAVE)));
		    save.click();
		    
            }
  
  @Test(dependsOnMethods = "inputFieldName")
  public void catchMessage (){
		    WebElement flashMessage1 = (new WebDriverWait(driver, 60))
	 	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.SUCCESS_MESSAGE)));
	 	    Assert.assertEquals(flashMessage1.getText(), Messages.DOCUMENT_FILTER_FIELD_SUCCESSFULLY);
  			}
  
  @Test(dependsOnMethods = "catchMessage")
   public void addNew (){	    
	  		WebElement filterFieldAdd = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(FilterField.ADD)));
	  		filterFieldAdd.click();
	  		WebElement save = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.ADD_SAVE)));
	  		save.click();
  			}
  
  @Test(dependsOnMethods = "addNew")
  public void catchErrorMessage (){    
	  		WebElement flashMessage = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.ENTER_MANDATORY_FIELDS)));
	  		Assert.assertEquals(flashMessage.getText(), Messages.ENTER_MANDATORY_FIELDS);		    
   }	
  
  @Test(dependsOnMethods = "catchErrorMessage")
  public void inputFieldNameAndCancel (){  
	  		WebElement filterFieldFieldName = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.FIELD_NAME)));
	  		filterFieldFieldName.click();
	  		filterFieldFieldName.sendKeys("Test of Filter Field");
	  		WebElement cancel = (new WebDriverWait(driver, 60))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.id(FilterField.ADD_CANCEL)));
	  		cancel.click();
	   
       }
  
  @Test(dependsOnMethods = "inputFieldNameAndCancel")
   public void catchUnsavedChange (){  
	  		WebElement message = (new WebDriverWait(driver, 60))
			  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.CONFIRMATION)));
	  		message.getText();
	  		Assert.assertEquals(message.getText(), Messages.UNSAVED_CHANGE);
	  		driver.findElement(By.id(DialogBtns.YES)).click();
  		}
  
}





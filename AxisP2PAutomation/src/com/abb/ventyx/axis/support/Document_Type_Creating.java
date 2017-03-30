package com.abb.ventyx.axis.support;

import java.util.List;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;

@ALM(id = "158") 
@Credentials(user = "5", password = "testuser")
public class Document_Type_Creating extends BaseTestCase {
	private final String DOCTYPE_B = "ABB";
	private final String DESC_B = "AA_MAINTAIN_DOCTYPES";
	String DOCTYPE_A;
	String DESC_A;
	@Test
	public void Document_Type_Creating(){
		   // Create Permission 
		    WebElement axisConfigParentButton = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		    axisConfigParentButton.click();
		    //Document Types is unique.
		    driver.findElement(By.cssSelector(AxisConfigMenu.DOC_TYPE)).click();
		    
		    WebElement table_element = driver.findElement(By.cssSelector("#content-component > div > div.v-panel-content.v-panel-content-borderless.v-panel-content-v-common-page-panel.v-scrollable > div > div.v-slot.v-slot-v-common-page-content-layout > div > div > div > div > div > div > div.v-grid-tablewrapper > table"));
	        List<WebElement> tr_collection=table_element.findElements(By.cssSelector("#content-component > div > div.v-panel-content.v-panel-content-borderless.v-panel-content-v-common-page-panel.v-scrollable > div > div.v-slot.v-slot-v-common-page-content-layout > div > div > div > div > div > div > div.v-grid-tablewrapper > table > tbody > tr"));

	        System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	        int row_num,col_num;
	        row_num=1;
	        for(WebElement trElement : tr_collection)
	        {
	            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
	            System.out.println("NUMBER OF COLUMNS="+td_collection.size());
	            col_num=1;
	            for(WebElement tdElement : td_collection)
	            {
	                System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
	                col_num++;
	            }
	            row_num++;
	        } 
	        
		 
		    DOCTYPE_A = driver.findElement(By.id(DocType.DTROW1)).getText();
		    DESC_A = driver.findElement(By.cssSelector(DocType.DROW1)).getText();
		    driver.findElement(By.cssSelector(DocType.ADD)).click();
		    driver.findElement(By.id(DocType.DOCTYPES)).click();
		    driver.findElement(By.id(DocType.DOCTYPES)).sendKeys(DOCTYPE_B);
		    driver.findElement(By.id(DocType.DESC)).click();
		    driver.findElement(By.id(DocType.DESC)).sendKeys(DESC_B);
		    driver.findElement(By.id(DocType.SAVE)).click();
	}
	@Test(dependsOnMethods ="Document_Type_Creating")
	public void Checking_Message(){
		    WebElement flashDialogue = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.CONFIRM_DAILOGUE)));
		    Assert.assertEquals(flashDialogue.getCssValue("visibility"), "visible");
		    WebElement flashMessage = (new WebDriverWait(driver, 10))
		  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DocType.CONFIRM_MESSAGE)));
		    Assert.assertEquals(flashMessage.getText(), "Document Types exist");
		    System.out.println(flashMessage.getText());
		    final String DOCTYPE_C = driver.findElement(By.id(DocType.DTROW1)).getText();
		    final String DESC_C = driver.findElement(By.id(DocType.DROW1)).getText();
		    Assert.assertNotEquals(DOCTYPE_C, DOCTYPE_A);
		    Assert.assertNotEquals(DESC_C, DESC_A);
		   			    
   }	    
}





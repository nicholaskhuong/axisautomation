package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseDropDownList {
	WebDriver driver;
	String tableXPath;
	String[ ] listItem;
	int rowCount  =0;
	int columnCount =0;
	public BaseDropDownList(WebDriver driver, String tableXPath)
	{
		this.driver = driver;
		this.tableXPath= tableXPath;
		getList();
	}
//	public String getGridCellByColumnName(String columnName, int rowNumber)
//	{
//		for(int i = 1; i<columnCount; i++ )
//		{
//			if (listItem[0][i].trim().equalsIgnoreCase(columnName.trim()))
//			{
//				return listItem[rowNumber][i];
//			}
//		}
//		return "N/A";
//	}
	public int findItemInDropDownList(String value)
	{
		
		for(int i = 2; i<rowCount; i++ )
		{
			if (listItem[i].trim().equals(value.trim()))
			{
				return i;
			}
		}
		return -1;
	}
	private void getList()
	{
	WebElement table_element = (new WebDriverWait(driver, 30))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(tableXPath)));
    List<WebElement> tr_collection=table_element.findElements(By.cssSelector(tableXPath + "> tbody > tr"));
    rowCount = tr_collection.size() +2;
    listItem = new String[rowCount];
    int row_num ;
    row_num=2;
    for(WebElement trElement : tr_collection)
    {
        List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
       for(WebElement tdElement : td_collection)
        {
        	if (null != tdElement.getText())
        	 {
        		listItem[row_num] = tdElement.getText();
        	 }
        }
        row_num++;
    } 
	}

}

package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseGrid {
	WebDriver driver;
	String tableXPath;
	String[ ][ ] gridItem;
	int rowCount  =0;
	int columnCount =0;
	public BaseGrid(WebDriver driver, String tableXPath)
	{
		this.driver = driver;
		this.tableXPath= tableXPath;
		getGrid();
	}
	public String getGridCellByColumnName(String columnName, int rowNumber)
	{
		for(int i = 1; i<=columnCount; i++ )
		{
			if (gridItem[0][i].trim().equalsIgnoreCase(columnName.trim()))
			{
				return gridItem[rowNumber][i];
			}
		}
		return "N/A";
	}
	public int findItemByColumnName(String columnName, String value)
	{
		int columnNo = 0;
		for(int i = 1; i<=columnCount; i++ )
		{
			if (gridItem[0][i].trim().equalsIgnoreCase(columnName.trim()))
			{
				columnNo = i;
				break;
			}
		}
		for(int i = 1; i<=rowCount; i++ )
		{
			if (gridItem[i][columnNo].trim().equals(value.trim()))
			{
				return i;
			}
		}
		return -1;
	}
	private void getGrid()
	{
	WebElement table_element = (new WebDriverWait(driver, 20))
	  			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(tableXPath)));
    List<WebElement> tr_collection=table_element.findElements(By.cssSelector(tableXPath + "> tbody > tr"));
    List<WebElement> th_collection=table_element.findElements(By.cssSelector(tableXPath + "> thead > tr > th"));
    rowCount = tr_collection.size() +1;
    columnCount = th_collection.size() +1;
    gridItem = new String[rowCount][columnCount];
    int row_num,col_num;
    row_num=1;
    col_num=1;
    for(WebElement thElement : th_collection)
    {
    	if (null != thElement.getText())
   	 	{
	    	gridItem[0][col_num] = thElement.getText();
   	 	}
    	col_num++;
    }
    for(WebElement trElement : tr_collection)
    {
        List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
        col_num=1;
        for(WebElement tdElement : td_collection)
        {
        	if (null != tdElement.getText())
        	 {
        		gridItem[row_num][col_num] = tdElement.getText();
        	 }
            col_num++;
            if (col_num> th_collection.size())
            {
            	System.out.println();
            }
        }
        row_num++;
    } 
	}

}

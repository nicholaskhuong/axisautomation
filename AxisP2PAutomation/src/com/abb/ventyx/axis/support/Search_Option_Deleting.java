package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SearchOption;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "206")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Search_Option_Deleting extends BaseTestCase {
	String fieldTypeUpdate = "QUANTITY UPDATE";
	ScreenAction action;
	int milliseconds = 1000;
	TableFunction table;
	WebElement index;

	//Step 01
	@Test
	public void openSearchOptionScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.FILTER_CONFIG));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.SEARCH_OPTION));
		action.pause(milliseconds);
	}
	//Step 02_03
	@Test(dependsOnMethods = "openSearchOptionScreen", alwaysRun = true)
	 public void clickFilterButtonAndClickTrashBin(){
		 table = new TableFunction(driver);
		 table.inputFilter(fieldTypeUpdate, SearchOption.FIELD_TYPE_FILTER, true);
		 index = table.getCellObject(1, 4);
		 index.click();
		 action.pause(milliseconds);
		 action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DELETE_CONFIRM_SEARCH_OPTION);
		 action.waitObjVisibleAndClick(By.id(SearchOption.YES));
		 action.pause(milliseconds);
		 action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DELETE_FIELD_TYPE);
		 action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	 }
}

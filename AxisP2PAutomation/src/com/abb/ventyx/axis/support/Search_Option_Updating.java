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

@ALM(id = "532")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Search_Option_Updating extends BaseTestCase {
	String fieldType = "QUANTITY UNIT";
	String filterSubType = "STATES";
	String option = "LESS THAN";
	String fieldTypeUpdate = "QUANTITY UPDATE";
	String filterSubTypeUpdate = "COUNTRY CODE";
	String filterSubTypeUpdate2 = "COUNTRY";
	ScreenAction action;
	int milliseconds = 1000;
	TableFunction table;
	WebElement index;

	// Step 01
	@Test
	public void openSearchOptionScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.FILTER_CONFIG));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.SEARCH_OPTION));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), SearchOption.TITLE_SEARCH_OPTION);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(table.getValueTableHeader(1), "Field Type");
		assertEquals(table.getValueTableHeader(2), "Filter Sub Type");
		assertEquals(table.getValueTableHeader(3), "Option");
	}

	// Step 02_03
	@Test(dependsOnMethods = "openSearchOptionScreen", alwaysRun = true)
	public void clickFilterButtonAndEditDataFilterType() {
		table.clickFilterAndInputWithColumn(fieldType, SearchOption.FIELD_TYPE_FILTER, true);
		action.pause(milliseconds);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 1);
		index.click();
		action.waitObjVisible(By.id(SearchOption.FIELD_TYPE_ADD));
		action.waitObjVisible(By.id(SearchOption.FILTER_SUB_TYPE_ADD));
		assertEquals(driver.findElement(By.cssSelector(SearchOption.SEARCH_OPTION_CSS)).getText(), SearchOption.EDIT_FILTER_FIELD_SEARCH_OPTION);
		action.waitObjVisible(By.id(SearchOption.OPTION_ADD));
		action.inputTextField(SearchOption.FIELD_TYPE_ADD, fieldTypeUpdate);
		action.waitObjVisible(By.id(SearchOption.SAVE_ADD));
		action.waitObjVisibleAndClick(By.id(SearchOption.SAVE_ADD));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.UPDATE_FIELD_TYPE);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 04_05
	@Test(dependsOnMethods = "clickFilterButtonAndEditDataFilterType", alwaysRun = true)
	public void clickFilterButtonAndEditDataFilterSubType() {
		action.waitObjVisible(By.id(ScreenObjects.FILTER_FIELD_ID));
		action.inputTextField(ScreenObjects.FILTER_FIELD_ID, fieldTypeUpdate);
		action.pause(milliseconds);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 1);
		index.click();
		action.inputTextField(SearchOption.FILTER_SUB_TYPE_ADD, filterSubTypeUpdate2);
		action.waitObjVisibleAndClick(By.id(SearchOption.CANCEL_ADD));
		action.pause(milliseconds);
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(SearchOption.YES));
	}
}

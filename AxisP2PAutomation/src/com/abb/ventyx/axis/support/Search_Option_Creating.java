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
@ALM(id = "201") 
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Search_Option_Creating extends BaseTestCase {
	String fieldType = "QUANTITY UNIT";
	String filterSubType = "STATES";
	String option = "LESS THAN";
	String fieldTypeExit = "NUMBER";
	String filterSubTypeExit = "B";
	String optionExit = "GREATER THAN";
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
	 public void clickAddButtonAndInputData (){
		 action.waitObjVisibleAndClick(By.cssSelector(SearchOption.ADD));
		 action.inputTextField(SearchOption.FIELD_TYPE_ADD, fieldType);
		 action.inputTextField(SearchOption.FILTER_SUB_TYPE_ADD, filterSubType);
		 action.inputTextField(SearchOption.OPTION_ADD, option);
		 action.waitObjVisible(By.id(SearchOption.SAVE_ADD));
		 action.waitObjVisibleAndClick(By.id(SearchOption.SAVE_ADD));
		 action.pause(milliseconds);
		 action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DOC_TYPE_SEARCH_OPT_SUCCESSFULLY);
		 action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	 }
	//Step 04_05
	@Test(dependsOnMethods = "clickAddButtonAndInputData", alwaysRun = true)
	 public void clickAddButtonAndClickCancelButton (){
		 action.waitObjVisibleAndClick(By.cssSelector(SearchOption.ADD));
		 action.inputTextField(SearchOption.FIELD_TYPE_ADD, fieldType);
		 action.inputTextField(SearchOption.FILTER_SUB_TYPE_ADD, filterSubType);
		 action.inputTextField(SearchOption.OPTION_ADD, option);
		 action.waitObjVisibleAndClick(By.id(SearchOption.CANCEL_ADD));
		 action.pause(milliseconds);
		 action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		 action.waitObjVisibleAndClick(By.id(SearchOption.YES));
	 }
	 
	//Step 06_07
	@Test(dependsOnMethods = "clickAddButtonAndClickCancelButton", alwaysRun = true)
	 public void clickFilterButtonAndNoInputMandatoryFilter (){
		 table = new TableFunction(driver);
		 table.inputFilter(fieldType, SearchOption.FIELD_TYPE_FILTER, true);
		 index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH,1, 1);
		 index.click();
		 action.inputTextField(SearchOption.FIELD_TYPE_ADD, fieldType);
		 action.inputTextField(SearchOption.FILTER_SUB_TYPE_ADD, "");
		 action.inputTextField(SearchOption.OPTION_ADD, "");
		 action.waitObjVisible(By.id(SearchOption.SAVE_ADD));
		 action.waitObjVisibleAndClick(By.id(SearchOption.SAVE_ADD));
		 action.pause(milliseconds);
		 action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		 action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	 }
	 
	//Step 08_09
	@Test(dependsOnMethods = "clickFilterButtonAndNoInputMandatoryFilter", alwaysRun = true)
	 public void inputDuplicateValue (){
		 action.inputTextField(SearchOption.FIELD_TYPE_ADD, fieldTypeExit);
		 action.pause(milliseconds);
		 action.inputTextField(SearchOption.FILTER_SUB_TYPE_ADD, filterSubTypeExit);
		 action.inputTextField(SearchOption.OPTION_ADD, optionExit);
		 action.waitObjVisible(By.id(SearchOption.SAVE_ADD));
		 action.waitObjVisibleAndClick(By.id(SearchOption.SAVE_ADD));
		 action.pause(milliseconds);
		 action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.DUPLICATE_FIELD_TYPE);
		 action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		 action.waitObjVisible(By.id(SearchOption.CANCEL_ADD));
		 action.waitObjVisibleAndClick(By.id(SearchOption.CANCEL_ADD));
		 action.pause(milliseconds);
		 action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		 action.waitObjVisibleAndClick(By.id(SearchOption.YES));
	 }
	 
	
}





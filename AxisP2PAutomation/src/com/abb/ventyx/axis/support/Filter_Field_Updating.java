package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.FilterField;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "165")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Filter_Field_Updating extends BaseTestCase {
	ScreenAction action;
	int milliseconds = 1000;
	TableFunction table;
	String fieldName = "Quantily DOC";
	String fieldNameUpdate = "Quantily Updated";
	WebElement index;

	// Step 01
	@Test
	public void openFilterField() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.FILTER_CONFIG));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.FILTER_FIELD));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), FilterField.TITLE_FILTER_FIELDS);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(table.getValueTableHeader(1), "Field Id");
		assertEquals(table.getValueTableHeader(2), "Document Type");
		assertEquals(table.getValueTableHeader(3), "Field Name");
		assertEquals(table.getValueTableHeader(4), "Location Type");
		assertEquals(table.getValueTableHeader(5), "Location");
		action.clickHorizontalScrollBar();
		assertEquals(table.getValueTableHeader(6), "Field Type");
	}

	// Step 02
	@Test(dependsOnMethods = "openFilterField", alwaysRun = true)
	public void selectOneRowOnGrid() {
		table = new TableFunction(driver);
		table.clikFilterAndInputWithColumn(fieldName, FilterField.FIELD_NAME_FILTER, true);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 1);
		index.click();
	}

	// Step 03
	@Test(dependsOnMethods = "selectOneRowOnGrid", alwaysRun = true)
	public void updateFiledNameClickCancel() {
		action.inputTextField(FilterField.FIELD_NAME, fieldNameUpdate);
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.id(FilterField.ADD_CANCEL));
		action.waitObjVisibleAndClick(By.id(FilterField.YES));
	}

	// Step 05
	@Test(dependsOnMethods = "updateFiledNameClickCancel", alwaysRun = true)
	public void selectOneRowOnGridSecondTime() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		action.pause(milliseconds);
		table.clikFilterAndInputWithColumn(fieldName, FilterField.FIELD_NAME_FILTER, true);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 1);
		index.click();
	}

	@Test(dependsOnMethods = "selectOneRowOnGridSecondTime", alwaysRun = true)
	public void updateFiledNameClickSave() {
		action.inputTextField(FilterField.FIELD_NAME, fieldNameUpdate);
		action.waitObjVisibleAndClick(By.id(FilterField.ADD_SAVE));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DOCUMENT_FILTER_FIELD_SUCCESSFULLY_UPDATED);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 06_07
	@Test(dependsOnMethods = "updateFiledNameClickSave", alwaysRun = true)
	public void clickAddButtonAndInputValueSeconeTime() {
		index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 1);
		index.click();
		action.waitObjVisible(By.id(FilterField.ADD_CANCEL));
		action.waitObjVisibleAndClick(By.id(FilterField.ADD_CANCEL));
	}
}

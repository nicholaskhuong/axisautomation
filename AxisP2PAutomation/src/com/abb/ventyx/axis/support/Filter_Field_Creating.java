package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
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

@ALM(id = "143")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Filter_Field_Creating extends BaseTestCase {
	ScreenAction action;
	String filedName = "Quantily DOC";
	TableFunction table;
	int milliseconds = 1000;
	int i;

	// Step 01_02
	@Test
	public void openFilterFieldAddInputDocType() {
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

	// Step 03_04
	@Test(dependsOnMethods = "openFilterFieldAddInputDocType", alwaysRun = true)
	public void clickAddButtonAndInputValue() {
		action.waitObjVisibleAndClick(By.cssSelector(FilterField.ADD));
		action.waitObjVisible(By.id(FilterField.FIELD_NAME));
		action.waitObjVisible(By.id(FilterField.DOC_TYPE));
		assertEquals(driver.findElement(By.cssSelector(FilterField.TITLE_FILTER_FIELDS_CSS)).getText(), FilterField.ADD_FILTER_FIELD);
		action.inputTextField(FilterField.FIELD_NAME, filedName);
		action.waitObjVisibleAndClick(By.id(FilterField.ADD_SAVE));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 05
	@Test(dependsOnMethods = "clickAddButtonAndInputValue", alwaysRun = true)
	public void inputAllFieldType() {
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.id(FilterField.DOC_TYPE));
		action.waitObjVisibleAndClick(By.xpath(FilterField.SELECT_DOCTYPE));
		action.waitObjVisibleAndClick(By.id(FilterField.FIELD_TYPE));
		action.waitObjVisibleAndClick(By.xpath(FilterField.SELECT_FIELDTYPE));
		action.waitObjVisibleAndClick(By.id(FilterField.COLUMN_NAME));
		action.waitObjVisibleAndClick(By.xpath(FilterField.SELECT_COLUMNNAME));
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.id(FilterField.ADD_SAVE));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DOCUMENT_FILTER_FIELD_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "inputAllFieldType", alwaysRun = true)
	public void checkDataAgainAfterCreated() {
		table.clickFilterAndInputWithColumn(filedName, FilterField.FIELD_NAME_FILTER, true);
		action.pause(milliseconds);
		Assert.assertTrue(i >= 0, String.format("Filed Name: %s not found!", filedName));
	}

	// Step 06_07
	@Test(dependsOnMethods = "checkDataAgainAfterCreated", alwaysRun = true)
	public void clickAddButtonAndInputValueSeconeTime() {
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.cssSelector(FilterField.ADD));
		action.inputTextField(FilterField.FIELD_NAME, "Test of Filter Field");
		action.waitObjVisibleAndClick(By.id(FilterField.DOC_TYPE));
		action.waitObjVisibleAndClick(By.xpath(FilterField.SELECT_DOCTYPE));
		action.waitObjVisibleAndClick(By.id(FilterField.FIELD_TYPE));
		action.waitObjVisibleAndClick(By.xpath(FilterField.SELECT_FIELDTYPE));
		action.waitObjVisibleAndClick(By.id(FilterField.COLUMN_NAME));
		action.waitObjVisibleAndClick(By.xpath(FilterField.SELECT_COLUMNNAME));
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.id(FilterField.ADD_CANCEL));
		action.waitObjVisibleAndClick(By.id(FilterField.NO));
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.id(FilterField.ADD_CANCEL));
		action.waitObjVisibleAndClick(By.id(FilterField.YES));
	}
}

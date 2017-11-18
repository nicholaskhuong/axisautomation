package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

@ALM(id = "166")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Filter_Field_Deleting extends BaseTestCase {
	ScreenAction action;
	int milliseconds = 1500;
	TableFunction table;
	String fieldNameUpdate = "Quantily Updated";
	WebElement index;
	int i;

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

	@Test(dependsOnMethods = "openFilterField", alwaysRun = true)
	public void clickTrashBinIcon() {
		table.clickFilterAndInputWithColumn(fieldNameUpdate, FilterField.FIELD_NAME_FILTER, true);
		action.pause(milliseconds);
		index = table.getCellObject(1, 8);
		index.click();
	}

	// Steps 02_03
	@Test(dependsOnMethods = "clickTrashBinIcon", alwaysRun = true)
	public void clickNoAndYesToDeleteData() {
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.id(FilterField.NO));
		action.pause(milliseconds);
		index = table.getCellObject(1, 8);
		index.click();
		action.waitObjVisibleAndClick(By.id(FilterField.YES));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEL_FILTER_FIELD_SUCCESS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "clickNoAndYesToDeleteData", alwaysRun = true)
	public void checkDataAgainAfterDeleted() {
		table.inputFilterAtIndex(fieldNameUpdate, FilterField.FIELD_NAME_FILTER, true);
		action.pause(milliseconds);
		Assert.assertTrue(i >= 0, String.format("Filed Name: %s not found!", fieldNameUpdate));
	}
}

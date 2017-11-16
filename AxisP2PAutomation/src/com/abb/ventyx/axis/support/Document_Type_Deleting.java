package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.BusinessCodeTypes;
import com.abb.ventyx.axis.objects.pagedefinitions.DocType;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "551")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Document_Type_Deleting extends BaseTestCase {
	int row;
	ScreenAction action;
	int milliseconds = 1000;
	TableFunction table;
	WebElement index;
	String documentTypeTitle = "Maintain Document Types";

	// Step 01
	@Test
	public void openDocumentTypesScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.DOC_TYPE));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), DocType.TITLE_MAINTAIN_DOCUMENT_TYPES);
		action.waitObjVisible(By.cssSelector(ScreenObjects.FILTER_BTN_CSS));
		assertEquals(table.getValueTableHeader(1), "Document Types");
		assertEquals(table.getValueTableHeader(2), "Description");
	}

	@Test(dependsOnMethods = "openDocumentTypesScreen", alwaysRun = true)
	public void deleteDocumentType() {
		table.clickFilterAndInputWithColumn(Document_Type_Creating.documentTypes, DocType.FIELD_TYPE_FILTER, true);
		action.pause(milliseconds);
		index = table.getCellObject(1, 3);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CONFIRM);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.NO));
		index = table.getCellObject(1, 3);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CONFIRM);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DOCUMENT_DELETE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 03
	@Test(dependsOnMethods = "deleteDocumentType", alwaysRun = true)
	public void deleteDocumentTypeWithClickCancel() {
		table.inputFilterAtIndex("Invoice", DocType.FIELD_TYPE_FILTER, true);
		action.pause(milliseconds);
		index = table.getCellObject(1, 3);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CONFIRM);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.NO));
		index = table.getCellObject(1, 3);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CONFIRM);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DEL_DOC_TYPES_ERROR);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}
}

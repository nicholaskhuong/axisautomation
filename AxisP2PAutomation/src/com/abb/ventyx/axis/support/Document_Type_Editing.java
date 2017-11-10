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

@ALM(id = "543")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Document_Type_Editing extends BaseTestCase {
	String description = "AA_MAINTAIN_DOCTYPES";
	String descriptionUpdate = "AA_MAINTAIN_DOCTYPES_UPDATE";
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

	// Step 02
	@Test(dependsOnMethods = "openDocumentTypesScreen", alwaysRun = true)
	public void clickAddButtonAndInputData() {
		table.clikFilterAndInputWithColumn(Document_Type_Creating.documentTypes, DocType.FIELD_TYPE_FILTER, true);
		action.pause(3000);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_DOCUMENT_XPATH, 1, 1);
		index.click();
		action.waitObjVisible(By.id(DocType.DESC));
		action.inputTextField(By.id(DocType.DESC), descriptionUpdate);
		action.waitObjVisibleAndClick(By.id(DocType.SAVE));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DOCUMENT_UPDATED_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 03+06
	@Test(dependsOnMethods = "clickAddButtonAndInputData", alwaysRun = true)
	public void updateValueWithCancelYesAndCheckFilterButton() {
		table.inputFilterAtIndex(Document_Type_Creating.documentTypes, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		action.pause(milliseconds);
		index = table.getCellObject(ScreenObjects.TABLE_BODY_DOCUMENT_XPATH, 1, 1);
		index.click();
		action.inputTextField(By.id(DocType.DESC), description);
		action.waitObjVisible(By.id(DocType.CANCEL));
		action.waitObjVisibleAndClick(By.id(DocType.CANCEL));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisible(By.id(DocType.NO));
		action.waitObjVisibleAndClick(By.id(DocType.NO));
		action.pause(milliseconds);
		action.waitObjVisible(By.id(DocType.CANCEL));
		action.waitObjVisibleAndClick(By.id(DocType.CANCEL));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.pause(milliseconds);
		action.waitObjVisible(By.id(DocType.YES));
		action.waitObjVisibleAndClick(By.id(DocType.YES));
		assertEquals(driver.findElement(By.xpath(DocType.MAINTAIN_DOCUMENT_TYPES_TITLE)).getText(), documentTypeTitle);
	}
}

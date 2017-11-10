package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

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

@ALM(id = "158")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Document_Type_Creating extends BaseTestCase {
	public static String documentTypes = "DocType50";
	String description = "AA_MAINTAIN_DOCTYPES";
	ScreenAction action;
	int milliseconds = 1000;
	TableFunction table;
	WebElement index;

	// Step 01
	@Test
	public void openDocumentTypesScreen() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.DOC_TYPE));
		action.pause(milliseconds);
	}

	// Step 02
	@Test(dependsOnMethods = "openDocumentTypesScreen", alwaysRun = true)
	public void clickAddButtonAndInputData() {
		action.waitObjVisibleAndClick(By.cssSelector(DocType.ADD));
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000L);
		documentTypes = String.format("DocType %s", drand);
		action.waitObjVisible(By.id(DocType.DOCTYPES));
		action.inputTextField(By.id(DocType.DOCTYPES), documentTypes);
		action.waitObjVisible(By.id(DocType.DESC));
		action.inputTextField(By.id(DocType.DESC), description);
		action.waitObjVisible(By.id(DocType.SAVE));
		action.waitObjVisibleAndClick(By.id(DocType.SAVE));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DOCUMENT_CREATE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	// Step 03
	@Test(dependsOnMethods = "clickAddButtonAndInputData", alwaysRun = true)
	public void clickAddButtonAndInputLeaveTwoFilterEmpty() {
		action.waitObjVisibleAndClick(By.cssSelector(DocType.ADD));
		action.inputTextField(By.id(DocType.DOCTYPES), documentTypes);
		action.waitObjVisible(By.id(DocType.SAVE));
		action.waitObjVisibleAndClick(By.id(DocType.SAVE));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 04_07
	@Test(dependsOnMethods = "clickAddButtonAndInputLeaveTwoFilterEmpty", alwaysRun = true)
	public void inputValueAndClickCancel() {
		action.waitObjVisible(By.id(DocType.CANCEL));
		action.waitObjVisibleAndClick(By.id(DocType.CANCEL));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisible(By.id(BusinessCodeTypes.NO));
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.NO));
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.id(DocType.CANCEL));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.pause(milliseconds);
		action.waitObjVisible(By.id(BusinessCodeTypes.YES));
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
	}

	// Step 08
	@Test(dependsOnMethods = "inputValueAndClickCancel", alwaysRun = true)
	public void inputDocumentTypeAndDecsExits() {
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.cssSelector(DocType.ADD));
		action.inputTextField(By.id(DocType.DOCTYPES), documentTypes);
		action.inputTextField(By.id(DocType.DESC), description);
		action.waitObjVisible(By.id(DocType.SAVE));
		action.waitObjVisibleAndClick(By.id(DocType.SAVE));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DOC_TYPES_EXIST);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}
}

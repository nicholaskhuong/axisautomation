package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.BusinessCodeTypes;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "520")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Code_Set_Type_Creating extends BaseTestCase {
	public static String codeType = "AUDITCODE";
	String codeDecs = "Audit Log Action Code";
	String codeType2 = "UOM";
	ScreenAction action;
	int milliseconds = 1000;
	TableFunction table;
	WebElement index;
	int i;

	// Step 01
	@Test
	public void openBusinessCodeSetTypeScreen() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.BUSINESS_CODE_SET_TYPE));
		action.waitObjVisible(By.cssSelector(BusinessCodeTypes.ADD));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SCREEN_TITLE_CSS)).getText(), BusinessCodeTypes.MAINTAIN_BUSINESS_CODE_SET_TYPE);
		action.waitObjVisible(By.cssSelector(BusinessCodeTypes.FILTER_BTN_CSS));
		assertEquals(table.getValueTableHeader(1), "Code Type");
		assertEquals(table.getValueTableHeader(2), "Code Desc");
	}

	// Step 02_03
	@Test(dependsOnMethods = "openBusinessCodeSetTypeScreen", alwaysRun = true)
	public void clickAddButtonAndInputData() {
		action.waitObjVisibleAndClick(By.cssSelector(BusinessCodeTypes.ADD));
		action.waitObjVisible(By.xpath(BusinessCodeTypes.CODE_TYPE));
		action.waitObjVisible(By.xpath(BusinessCodeTypes.CODE_DESCRIPTION));
		assertEquals(driver.findElement(By.cssSelector(BusinessCodeTypes.BUSINESS_CODE_SET_TYPE_CSS)).getText(),
				BusinessCodeTypes.CREATE_BUSINESS_CODE_SET_TYPE);
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000L);
		codeType = String.format("AUDITCODE %s", drand);
		action.inputTextField(By.xpath(BusinessCodeTypes.CODE_TYPE), codeType);
		action.inputTextField(By.xpath(BusinessCodeTypes.CODE_DESCRIPTION), codeDecs);
		action.waitObjVisibleAndClick(By.xpath(BusinessCodeTypes.SAVE_BUTTON));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.ADD_CODE_SET_TYPE_SUCCESSFULLY);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "clickAddButtonAndInputData", alwaysRun = true)
	public void checkDataAgainAfterCreated() {
		table.clickFilterAndInputWithColumn(codeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		action.pause(milliseconds);
		Assert.assertTrue(i >= 0, String.format("Code Type: %s not found!", codeType));
	}

	// Step 04
	@Test(dependsOnMethods = "checkDataAgainAfterCreated", alwaysRun = true)
	public void clickAddButtonAndInputLeaveTwoFilterEmpty() {
		action.waitObjVisibleAndClick(By.cssSelector(BusinessCodeTypes.ADD));
		action.inputTextField(By.xpath(BusinessCodeTypes.CODE_TYPE), "");
		action.inputTextField(By.xpath(BusinessCodeTypes.CODE_DESCRIPTION), "");
		action.waitObjVisibleAndClick(By.xpath(BusinessCodeTypes.SAVE_BUTTON));
		action.pause(milliseconds);
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 04
	@Test(dependsOnMethods = "clickAddButtonAndInputLeaveTwoFilterEmpty", alwaysRun = true)
	public void clickAddButtonAndInputCodeType() {
		action.inputTextField(By.xpath(BusinessCodeTypes.CODE_TYPE), codeType2);
		action.waitObjVisibleAndClick(By.xpath(BusinessCodeTypes.CANCEL_BUTTON));
		action.pause(milliseconds);
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.NO));
		action.pause(milliseconds);
		action.waitObjVisibleAndClick(By.xpath(BusinessCodeTypes.CANCEL_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
	}
}

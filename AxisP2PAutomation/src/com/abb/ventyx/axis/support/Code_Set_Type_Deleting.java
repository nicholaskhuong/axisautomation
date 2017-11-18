package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

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

@ALM(id = "624")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Code_Set_Type_Deleting extends BaseTestCase {
	String codeSetCodeType = "UOM";
	String codeDecsUpdate = "Audit Log Action Code Update";
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

	// Step 02
	@Test(dependsOnMethods = "openBusinessCodeSetTypeScreen", alwaysRun = true)
	public void deleteCodeTypeWithCodeSet() {
		table.clickFilterAndInputWithColumn(codeSetCodeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		action.pause(milliseconds);
		index = table.getCellObject(1, 3);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CODE_SET_TYPE);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.NO));
		index = table.getCellObject(1, 3);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CODE_SET_TYPE);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DEL_CODE_SET_WITH_CODE_TYPE);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}

	// Step 03
	@Test(dependsOnMethods = "deleteCodeTypeWithCodeSet", alwaysRun = true)
	public void deleteCodeTypeWithoutCodeSet() {
		table.inputFilterAtIndex(Code_Set_Type_Creating.codeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		action.pause(milliseconds);
		index = table.getCellObject(1, 3);
		index.click();
		action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CODE_SET_TYPE);
		action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEL_CODE_SET);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	}

	@Test(dependsOnMethods = "deleteCodeTypeWithoutCodeSet", alwaysRun = true)
	public void checkDataAgainAfterDeleted() {
		table.inputFilterAtIndex(Code_Set_Type_Creating.codeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		action.pause(milliseconds);
		Assert.assertTrue(i >= 0, String.format("Code Type: %s not found!", Code_Set_Type_Creating.codeType));
	}
}

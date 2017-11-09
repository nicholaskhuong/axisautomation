package com.abb.ventyx.axis.support;
import static org.testng.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.BusinessCodeTypes;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
@ALM(id = "535")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Code_Set_Type_Updating extends BaseTestCase {
	String codeType = "AUDITCODE";
	String codeDecs = "Audit Log Action Code";
	String codeDecsUpdate = "Audit Log Action Code Update";
	ScreenAction action;
	int milliseconds = 1000;
	TableFunction table;
	WebElement index;
	
	//Step 01
	@Test
	public void openBusinessCodeSetTypeScreen(){
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.BUSINESS_CODE_SET_TYPE));
		action.pause(milliseconds);
	}
	//Step 02_03
	@Test(dependsOnMethods = "openBusinessCodeSetTypeScreen", alwaysRun = true)
	 public void selectOneRecordOnGrid (){
		 table = new TableFunction(driver);
		 table.clikFilterAndInputWithColumn(codeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		 action.pause(milliseconds);
		 index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH,1, 1);
		 index.click();
		 action.waitObjVisible(By.xpath(BusinessCodeTypes.CODE_TYPE));
	 	 WebElement codeType = driver.findElement(By.xpath(BusinessCodeTypes.CODE_TYPE));
	 	 codeType.isDisplayed();
		 action.waitObjVisible(By.xpath(BusinessCodeTypes.CODE_DESCRIPTION));
		 WebElement codeDecs = driver.findElement(By.xpath(BusinessCodeTypes.CODE_DESCRIPTION));
		 codeDecs.isDisplayed();
	}
		 
	//Step 03
	@Test(dependsOnMethods = "selectOneRecordOnGrid", alwaysRun = true)
	 public void updateValueForCodeDecs(){
		 action.inputTextField(By.xpath(BusinessCodeTypes.CODE_DESCRIPTION), codeDecsUpdate);
		 action.waitObjVisible(By.xpath(BusinessCodeTypes.SAVE_BUTTON));
		 action.waitObjVisibleAndClick(By.xpath(BusinessCodeTypes.SAVE_BUTTON));
		 action.pause(milliseconds);
		 action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.UPDATE_CODE_SET_TYPE_SUCCESSFULLY);
		 action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	 }
	//Step 04+05
	@Test(dependsOnMethods = "updateValueForCodeDecs", alwaysRun = true)
	 public void updateValueWithCancelYesAndCheckFilterButton(){
		 table.inputFilterAtIndex(codeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		 action.pause(milliseconds);
		 index = table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH,1, 1);
		 index.click();
		 action.inputTextField(By.xpath(BusinessCodeTypes.CODE_DESCRIPTION), "Edit Code Decs");
		 action.waitObjVisible(By.xpath(BusinessCodeTypes.CANCEL_BUTTON));
		 action.waitObjVisibleAndClick(By.xpath(BusinessCodeTypes.CANCEL_BUTTON));
		 action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		 action.waitObjVisible(By.id(BusinessCodeTypes.NO));
		 action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.NO));
		 action.pause(milliseconds);
		 action.waitObjVisibleAndClick(By.xpath(BusinessCodeTypes.CANCEL_BUTTON));
		 action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)).getText(), Messages.UNSAVED_CHANGE);
		 action.pause(milliseconds);
		 action.waitObjVisible(By.id(BusinessCodeTypes.YES));
		 action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
	 }
}




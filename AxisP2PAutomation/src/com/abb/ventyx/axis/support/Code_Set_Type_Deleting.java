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
@ALM(id = "624")
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
public class Code_Set_Type_Deleting extends BaseTestCase {
	String codeSetCodeType = "UOM";
	String codeType = "AUDITCODE";
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
	
	//Step 02
	@Test(dependsOnMethods = "openBusinessCodeSetTypeScreen", alwaysRun = true)
	 public void deleteCodeTypeWithCodeSet (){
		 table = new TableFunction(driver);
		 table.clikFilterAndInputWithColumn(codeSetCodeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		 action.pause(milliseconds);
		 index = table.getCellObject(1, 3);
		 index.click();
		 action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CODE_SET_TYPE);
		 action.waitObjVisible(By.id(BusinessCodeTypes.NO));
		 action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.NO));
		 index = table.getCellObject(1, 3);
		 index.click();
		 action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CODE_SET_TYPE);
		 action.waitObjVisible(By.id(BusinessCodeTypes.YES));
		 action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
		 action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DEL_CODE_SET_WITH_CODE_TYPE);
		 action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	 }
	//Step 03
	@Test(dependsOnMethods = "deleteCodeTypeWithCodeSet", alwaysRun = true)
	 public void deleteCodeTypeWithoutCodeSet(){
		 table.inputFilterAtIndex(codeType, BusinessCodeTypes.CODE_TYPE_FILTER, true);
		 action.pause(milliseconds);
		 index = table.getCellObject(1, 3);
		 index.click();
		 action.waitObjVisible(By.cssSelector(ScreenObjects.CONFIRMATION));
		 assertEquals(driver.findElement(By.cssSelector(ScreenObjects.CONFIRMATION)).getText(), Messages.DEL_CODE_SET_TYPE);
		 action.waitObjVisible(By.id(BusinessCodeTypes.YES));
		 action.waitObjVisibleAndClick(By.id(BusinessCodeTypes.YES));
		 action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.DEL_CODE_SET);
		 action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	 }
}




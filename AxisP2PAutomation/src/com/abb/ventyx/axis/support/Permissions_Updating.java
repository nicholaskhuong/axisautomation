package com.abb.ventyx.axis.support;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.ventyx.testng.TestDataKey;

@ALM(id = "155") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Updating extends BaseTestCase {
	@TestDataKey private final String PERMISSION_NAME_A = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String PERMISSION_NAME_B = "BB_MAINTAIN_PERMISSION";
	BaseGrid grid;
	int row;
	@Test
	public void updatePermission() throws Exception {
		// Update Permission 
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();
		WebElement axisConfigPermission = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.PERMISSIONS)));
		axisConfigPermission.click();
		
		//Click on Search icon
		WebElement searchOptionFilter = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.FILTER_XPATH)));
		searchOptionFilter.click();

		// Filter by Permission name
		WebElement field_Type_Filter = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.FILTER_PERMISSION_NAME_CSS)));
		field_Type_Filter.click();
		WebElement field_Type_Filter1 = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.PERMISSION_NAME_FILTER)));
		field_Type_Filter1.sendKeys(PERMISSION_NAME_B);
		Thread.sleep(1000);
		
		// Get Document Type value
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(),PERMISSION_NAME_B);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(),"A");
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(),"PurchaseOrderAcknowledgement");
		// Click on Permission ID
		WebElement gridCell = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.GRID_PERMISSIONIDCELL)));
		gridCell.click();
	
		WebElement permissionName = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.PERMISSION_NAME)));
		permissionName.clear();
		Thread.sleep(1000);
		permissionName.sendKeys(PERMISSION_NAME_A);
		driver.findElement(By.id(Permissions.SAVE)).click();
		WebElement flashMessage = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.POPUP_SUCCESS)));
		Assert.assertEquals(driver.findElement(By.cssSelector(Permissions.POPUP_SUCCESS)).getCssValue("background-color"), "rgba(33, 190, 137, 1)");
		Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
		Assert.assertEquals(flashMessage.getCssValue("display"), "block");
		//		    Assert.assertEquals(flashMessage.getText(), Messages.PERMISSION_UPADTED_SUCCESSFULLY);

	}
	/*@Test(dependsOnMethods = "updatePermission")
	public void returnPermissionToOriginalValue (){
		grid = new BaseGrid(driver, Permissions.GRID); 
		row = grid.findItemByColumnName("Permission Name", PERMISSION_NAME_A);
		Assert.assertNotEquals(row, -1, "Record not found");
		driver.findElement(By.id("permissionIdBtn" + (row-1))).click();
		WebElement permissionName = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.PERMISSION_NAME)));
		permissionName.clear();
		permissionName.sendKeys(PERMISSION_NAME_B);
		driver.findElement(By.id(Permissions.SAVE)).click();
	}	    */
}




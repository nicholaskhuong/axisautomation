package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.axis.objects.pages.HomePage;
import com.ventyx.testng.TestDataKey;

@ALM(id = "106") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Creating extends BaseTestCase {
	@TestDataKey private final String PERMISSION_NAME_A = "BB_MAINTAIN_PERMISSION";
	@TestDataKey private final String PERMISSION_NAME_B = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String DOCUMENT_TYPE_A = "PurchaseOrderAcknowledgement";
	@TestDataKey private final String USER_TYPE_A = "A";

	@Test
	public void createPermission() throws Exception {
		// Create Permission 

		// Click System Configuration menu
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();

		// Click Permissions sub menu
		WebElement axisPermissionsMenu = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.PERMISSIONS)));
		axisPermissionsMenu.click();

		// Click Add permission button
		WebElement addPermissionButton = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.ADD)));
		addPermissionButton.click();

		//  Enter Permission Name 
		WebElement permissionName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.PERMISSION_NAME)));
		permissionName.sendKeys(PERMISSION_NAME_A);

		// Click Document Type field
		WebElement permissionDocType = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(Permissions.DOCUMENT_TYPE)));
		permissionDocType.click();

		// Select PO Ack document type
		WebElement POAckType = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.PURCHASE_ORDER_ACK)));
		POAckType.click();
	
		WebElement adminUserType = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.AXIS_ADMIN)));
		adminUserType.click();
		// click Save button on Add Permission screen
		WebElement saveButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#permissioncreatewindow > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-mainform-verticallayout > div > div.v-slot.v-slot-v-bottombar-button-layout > div > div > div > div > div:nth-child(3)")));
		saveButton.click();
		Thread.sleep(2000);
		// Filter
		WebElement filterButton = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#HeaderMenuBar > span:nth-child(1)")));
		filterButton.click();
		
		WebElement filterPermissionName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Permissions.PERMISSION_NAME_FILTER)));
		filterPermissionName.sendKeys(PERMISSION_NAME_A);
		//WebDriverWait wait=new WebDriverWait(driver, 60);
		Thread.sleep(4000);
		/*wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PNROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.UTROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.DTROW1)));*/
		assertEquals(driver.findElement(By.cssSelector(Permissions.PNROW1)).getText(),PERMISSION_NAME_A);
		assertEquals(driver.findElement(By.cssSelector(Permissions.UTROW1)).getText(),USER_TYPE_A);
		assertEquals(driver.findElement(By.cssSelector(Permissions.DTROW1)).getText(),DOCUMENT_TYPE_A);
	}	    
}




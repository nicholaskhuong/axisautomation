package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;
import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.TableFunction;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.ventyx.testng.TestDataKey;

@ALM(id = "106") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Creating extends BaseTestCase {
	@TestDataKey private final String PERMISSION_NAME_A = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String DOCUMENT_TYPE_A = "PurchaseOrderAcknowledgement";
	@TestDataKey private final String USER_TYPE_A = "A";

	@Test
	public void createPermission() throws Exception {
		TableFunction a = new TableFunction(driver);
		// Click System Configuration menu
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();

		// Click Permissions sub menu
		WebElement axisPermissionsMenu = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.PERMISSIONS)));
		axisPermissionsMenu.click();

		// Check there is any permission AA_MAINTAIN_PERMISSION existing
		a.filterPermission(PERMISSION_NAME_A);
		int numberOfRowsBeforeAdding = a.countRow(Permissions.tableBody);
		
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

		WebElement adminUserType = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.AXIS_ADMIN)));
		adminUserType.click();
		
		// click Save button on Add Permission screen
		WebElement saveButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#permissioncreatewindow > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-mainform-verticallayout > div > div.v-slot.v-slot-v-bottombar-button-layout > div > div > div > div > div:nth-child(3)")));
		saveButton.click();
		
		// Filter
		a.filterPermission(PERMISSION_NAME_A);
		int numberOfRowsAfterAdding = a.countRow(Permissions.tableBody);
		
		assertEquals(numberOfRowsBeforeAdding+1, numberOfRowsAfterAdding);
		WebDriverWait wait = new WebDriverWait(driver, 10);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PNROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.UTROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.DTROW1)));
		
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[2]")),DOCUMENT_TYPE_A);
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[3]")),PERMISSION_NAME_A);
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[4]")),USER_TYPE_A);
	
	}	    
}




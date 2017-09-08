package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.PermissionsAction;
import com.ventyx.testng.TestDataKey;

@ALM(id = "106") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Creating extends BaseTestCase {
	@TestDataKey private final String PERMISSION_NAME_A = "AA_MAINTAIN_PERMISSION";
	@TestDataKey private final String DOCUMENT_TYPE_A = "PurchaseOrderAcknowledgement";
	@TestDataKey private final String USER_TYPE_A = "A";

	@Test
	public void createPermission() throws Exception {
		PermissionsAction p = new PermissionsAction(driver);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		// Click System Configuration menu
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();

		// Click Permissions sub menu
		p.clickPermissionsSubMenu();

		// Check there is any permission AA_MAINTAIN_PERMISSION existing
		p.filterPermission(PERMISSION_NAME_A);
		int numberOfRowsBeforeAdding = p.countRow(Permissions.tableBody);
		System.out.print(numberOfRowsBeforeAdding + "numberOfRowsBeforeAdding");
		
		// Click Add permission button
		p.clickAddButton();

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
		p.clickSaveButtonOnAddPermisisonPopUp();
		// Filter
		Thread.sleep(2000);
		p.enterValueTofilterPermission(PERMISSION_NAME_A);
		Thread.sleep(2000);
		int numberOfRowsAfterAdding = p.countRow(Permissions.tableBody);
		
		assertEquals(numberOfRowsBeforeAdding+1, numberOfRowsAfterAdding);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PNROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.UTROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.DTROW1)));
		
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[2]")).getText(),DOCUMENT_TYPE_A);
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[3]")).getText(),PERMISSION_NAME_A);
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[4]")).getText(),USER_TYPE_A);
	
		Thread.sleep(2000);
		p.clickAddButton();
		p.clickSaveButtonOnAddPermisisonPopUp();
		Thread.sleep(2000);
		//assertEquals(driver.findElement(By.cssSelector(Permissions.EMPTYPERMISSIONNAME)).getText(), Messages.EMPTYPERMISSIONNAME);
		
	}	    
}




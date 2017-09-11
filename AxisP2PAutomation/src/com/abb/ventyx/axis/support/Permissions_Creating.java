package com.abb.ventyx.axis.support;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
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
	@TestDataKey private final String ADDPERMISSIONHEADER = "Add Permission";
	@TestDataKey private final String MAINTAINPERMISSIONHEADER = "Maintain Permissions";

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
		int numberOfRowsBeforeAdding = p.countRow(Permissions.TABLEBODY);
		System.out.print(numberOfRowsBeforeAdding + "numberOfRowsBeforeAdding");
		
		// Click Add permission button
		p.clickAddButton();

		//  Enter Permission Name
		p.enterPermissionName(PERMISSION_NAME_A);

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
		
		// Step 3, 4
		// click Save button on Add Permission screen
		p.clickSaveButtonOnAddPermisisonPopUp();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Messages.PERMISSION_CREATED_SUCCESSFULLY_CSS)).getText(), Messages.PERMISSION_CREATED_SUCCESSFULLY);
		// Filter
		Thread.sleep(2000);
		p.enterValueTofilterPermission(PERMISSION_NAME_A);
		Thread.sleep(2000);
		int numberOfRowsAfterAdding = p.countRow(Permissions.TABLEBODY);
		
		assertEquals(numberOfRowsBeforeAdding+1, numberOfRowsAfterAdding);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.PNROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.UTROW1)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Permissions.DTROW1)));
		
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[2]")).getText(),DOCUMENT_TYPE_A);
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[3]")).getText(),PERMISSION_NAME_A);
		assertEquals(driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["+numberOfRowsAfterAdding+"]//td[4]")).getText(),USER_TYPE_A);
		Thread.sleep(1000);
		
		// Step 5
		p.clickAddButton();
		
		// Step 6
		p.clickSaveButtonOnAddPermisisonPopUp();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)).getText(), Messages.EMPTYPERMISSIONNAME);
		
		// Step 7
		p.enterPermissionName("ECHO 1");
		p.clickSaveButtonOnAddPermisisonPopUp();
		assertEquals(driver.findElement(By.cssSelector(Messages.ERROR_WITHOUT_ICON_CSS)).getText(), Messages.EMPTYUSERTYPE);
		Thread.sleep(2000);
		
		// Step 8
		p.clickCancelButtonOnAddPermisisonPopUp();
		assertEquals(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(),Messages.UNSAVED_CHANGE);
		
		// Step 9
		driver.findElement(By.id(Messages.NO_BTN_ID)).click();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Permissions.ADDPERMISSIONHEADER)).getText(),ADDPERMISSIONHEADER);
	
		// Step 10
		p.clickCancelButtonOnAddPermisisonPopUp();
		driver.findElement(By.id(Messages.YES_BTN_ID)).click();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSIONHEADER)).getText(),MAINTAINPERMISSIONHEADER);
		
		// Step 11
		p.clickAddButton();
		p.clickCancelButtonOnAddPermisisonPopUp();
		assertEquals(driver.findElement(By.cssSelector(Permissions.PERMISSIONHEADER)).getText(),MAINTAINPERMISSIONHEADER);
		
	}	    
}




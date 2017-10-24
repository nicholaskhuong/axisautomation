package com.abb.ventyx.axis.support;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.PermissionsAction;
import com.abb.ventyx.utilities.ScreenAction;


@ALM(id = "156") 
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Deleting extends BaseTestCase {
	String PERMISSION_NAME_A = "AUTOMATION_PERMISSION_AA";
	ScreenAction action;
	@Test
	public void openMaintainPermissionScreen() throws Exception {
		// Step 1
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		permissionsAction.clickSystemConfigurationMenu();
		permissionsAction.clickPermissionsSubMenu();
	}
	
	@Test(dependsOnMethods="openMaintainPermissionScreen")
	public void deletePermisison() throws InterruptedException{
		action = new ScreenAction(driver);
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		permissionsAction.filterPermissionbyPermissionName(PERMISSION_NAME_A);
		permissionsAction.filterPermissionbyDocumentType("PurchaseOrder");
		Thread.sleep(2000);

		//final String PERMISION_ID_A = driver.findElement(By.id(Permissions.ROW1)).getText();
		int numberOfRowsBeforeDelete = permissionsAction.countRow(Permissions.TABLEBODY);
		WebElement trashBinIcon = driver.findElement( 
				By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["
						+ numberOfRowsBeforeDelete + "]//td[5]"));

		trashBinIcon.click();
		action.waitObjVisible(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION));
		//Make sure this is a Confirmation of deleting process
		assertThat(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(),containsString(Messages.DELETE_CONFIRM));

		WebElement yesButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Permissions.DELETE_YES)));
		yesButton.click();

		action.waitObjVisible(By.cssSelector(Messages.PERMISSION_CREATED_SUCCESSFULLY_CSS));
		assertEquals(driver.findElement(By.cssSelector(Messages.PERMISSION_CREATED_SUCCESSFULLY_CSS)).getText(), Messages.PERMISSION_DELETED_SUCCESSFULLY);	

	}
}

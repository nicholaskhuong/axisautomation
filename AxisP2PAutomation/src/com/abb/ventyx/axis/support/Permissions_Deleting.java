package com.abb.ventyx.axis.support;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.Permissions;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.PermissionsAction;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "549")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Permissions_Deleting extends BaseTestCase {
	ScreenAction action;
	TableFunction table;

	@Test
	public void openMaintainPermissionScreen() throws Exception {
		// Step 1
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		permissionsAction.clickSystemConfigurationMenu();
		permissionsAction.clickPermissionsSubMenu();
	}

	@Test(dependsOnMethods = "openMaintainPermissionScreen")
	public void deletePermisison() {
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		PermissionsAction permissionsAction = new PermissionsAction(driver);
		permissionsAction.filterPermissionbyPermissionName(Permissions_Creating.permissionName);
		permissionsAction.filterPermissionbyDocumentType(Permissions_Updating.invoiceTypeDescription);
		action.pause(2000);

		// final String PERMISION_ID_A =
		// driver.findElement(By.id(Permissions.ROW1)).getText();
		/*int numberOfRowsBeforeDelete = permissionsAction.countRow(Permissions.TABLEBODY);
		WebElement trashBinIcon = driver.findElement(By.xpath("//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']//tr["
				+ numberOfRowsBeforeDelete + "]//td[5]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", trashBinIcon);
		trashBinIcon.click();
		action.waitObjVisibleAndClick(obj);*/
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", table.getCellObject(ScreenObjects.TABLE_BODY_USER_XPATH, 1, 5));
		action.waitObjVisible(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION));
		// Make sure this is a Confirmation of deleting process
		assertThat(driver.findElement(By.cssSelector(Permissions.CONFIRMATION_OF_DELETION)).getText(), containsString(Messages.DELETE_CONFIRM));

		WebElement yesButton = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(Permissions.DELETE_YES)));
		yesButton.click();

		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
		assertEquals(driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText(),
				Messages.PERMISSION_DELETED_SUCCESSFULLY);

	}
}

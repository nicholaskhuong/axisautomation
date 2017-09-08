package com.abb.ventyx.axis.supplier;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.UserGroup;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "601")
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierUserGroup_Creating extends BaseTestCase {
	String USER_GROUP_NAME = "POGroup";

	@Test
	public void openScreen() throws InterruptedException {
		WebElement menuAdmin = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(SupplierMenu.ADMINISTRATION_ID)));
		menuAdmin.click();
		WebElement userGroup = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(SupplierMenu.USER_GROUPS_ID)));
		userGroup.click();
		WebElement addBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(UserGroup.ADD_BTN_CSS)));

		WebElement screenTitle = driver.findElement(By
				.id(SupplierMenu.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), UserGroup.TITLE, "Title is wrong");
	}

	@Test(dependsOnMethods = "openScreen")
	public void addUserGroup() {
		WebElement addBtn = driver.findElement(By
				.cssSelector(UserGroup.ADD_BTN_CSS));
		addBtn.click();
		WebElement userGroupName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(UserGroup.USERGROUP_NAME_ID)));
		WebElement screenTitle = driver.findElement(By
				.id(SupplierMenu.SCREEN_TITLE_ID));
		assertEquals(screenTitle.getText(), UserGroup.TITLE_CREATE,
				"Title is wrong");
		userGroupName.sendKeys(USER_GROUP_NAME);
		// Click PO permission
		TableFunction permission = new TableFunction(driver);
		int row = permission.findRowByString(UserGroup.PERMISSION_TABLE_CSS,
				"PurchaseOrder", 3);
		List<WebElement> listCheckbox = driver.findElements(By
				.xpath("//input[@type='checkbox']"));
		listCheckbox.get(row).click();
		WebElement saveBtn = driver.findElement(By.id(UserGroup.SAVE_BTN_ID));
		saveBtn.click();
		WebElement flashMessage1 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(Messages.SUCCESS_MESSAGE)));
		Assert.assertEquals(flashMessage1.getText(),
				Messages.USERGROUP_CREATE_SUCCESSFULLY);
	}
}

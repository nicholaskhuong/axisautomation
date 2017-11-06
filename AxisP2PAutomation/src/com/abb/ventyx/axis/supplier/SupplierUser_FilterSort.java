package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Users;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "626")
@Credentials(user = "supplier_user_2@abb.com", password = "Testuser1")
public class SupplierUser_FilterSort extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	int row;
	WebElement index;
	String userId = "salem 10";
	String email = "salem13@abb.com";
	String userGroup = "Salem 11";

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.USERS_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.assertTitleScreen(Users.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void filterFunction() {
		table = new TableFunction(driver);
		// step 2,3
		table.inputFilter("5838");
		driver.findElement(By.xpath(Users.USER_NUMBER_FILTER)).clear();
		// step 4
		action.inputTextField(By.xpath(Users.USER_ID_FILTER), userId);
		driver.findElement(By.xpath(Users.USER_ID_FILTER)).clear();
		// Step 5
		action.inputTextField(By.xpath(Users.EMAIL_FILTER), email);
		driver.findElement(By.xpath(Users.EMAIL_FILTER)).clear();

		// step 6
		action.inputTextField(By.xpath(Users.USER_GROUP_FILTER), userGroup);
		action.pause(800);
		driver.findElement(By.cssSelector(ScreenObjects.FILTER_BTN_CSS)).click();

	}

}

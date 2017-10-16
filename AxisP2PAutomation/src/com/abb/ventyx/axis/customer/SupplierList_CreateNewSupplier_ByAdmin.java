package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;
import com.ventyx.testng.TestDataKey;
@ALM(id = "607")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_CreateNewSupplier_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	public static int i;

	@TestDataKey private final String SUPPLIERNAME = "Yamaha";
	@TestDataKey private final String SUPPLIEREMAIL = "yamaha@abb.com";
	@TestDataKey private final String COMPANYREGIRATIONNO = "COMYAMAHA123";
	@TestDataKey private final String TAXREGIRATIONNO = "TAXYAMAHA123";
	@TestDataKey private final String INVALIDEMAIL = "<HTML>";
	
	@TestDataKey private final String DUPLICATEDNAME = "ENCLAVE";
	@TestDataKey private final String DUPLICATEDCOMPANYREGISTRATIONNO = "ENCLAVE";
	@TestDataKey private final String DUPLICATEDTAXREGISTRATIONNO = "ENCLAVE";
	@TestDataKey private final String DUPLICATEDSUPPLIEREMAIL = "perla@enclave.vn";

	// Step 1
	@Test
	public void openSupplierListScreen(){
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		assertEquals(table.getValueTableHeader(1), "Supplier ID");
		assertEquals(table.getValueTableHeader(2), "Company Registration No");
		assertEquals(table.getValueTableHeader(3), "Tax Registration No");
		assertEquals(table.getValueTableHeader(4), "Supplier Status");
		assertEquals(table.getValueTableHeader(5), "Supplier Name");
		assertEquals(table.getValueTableHeader(6), "Supplier Email");
		assertEquals(table.getValueTableHeader(7), "Profile Name");
	}

	// Step 2
	@Test(dependsOnMethods="openSupplierListScreen")
	public void createSupplierWithBlankMandatoryField(){
		table = new TableFunction(driver);
		action = new ScreenAction(driver);

		action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));

		// Empty Supplier Name
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, SUPPLIEREMAIL);
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, SUPPLIERNAME);
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, TAXREGIRATIONNO);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Empty Company Registration No
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, SUPPLIERNAME);
		driver.findElement(By.id(SupplierList.COMPANYREGISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Empty Tax Registration No
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, COMPANYREGIRATIONNO);
		driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		// Empty Supplier Email
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, TAXREGIRATIONNO);
		driver.findElement(By.id(SupplierList.SUPPLIEREMAIL_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
	}

	// Step 3 
	@Test(dependsOnMethods="createSupplierWithBlankMandatoryField")
	public void createSupplierWithInvalidEmail(){
		table = new TableFunction(driver);
		action = new ScreenAction(driver);

		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, INVALIDEMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.clickBtn(By.id(SupplierList.SUPPLIEREMAIL_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 4
	@Test(dependsOnMethods="createSupplierWithInvalidEmail")
	public void createSupplierWithDuplicatedValue() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		
		// Duplicated email
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, DUPLICATEDSUPPLIEREMAIL);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATEDEMAIL);
		
		
		// Duplicated Comp. Registration Number
		WebElement txtField = driver.findElement(By.id(SupplierList.COMPANYREGISTRATIONNO_ID));
		txtField.clear();
		Thread.sleep(500);
		txtField.sendKeys(DUPLICATEDCOMPANYREGISTRATIONNO);
		//action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, DUPLICATEDCOMPANYREGISTRATIONNO);
		//Thread.sleep(2000);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, SUPPLIEREMAIL);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATECOMPANYREGISTRATIONNO);
		Thread.sleep(10000);
		/*// Duplicated Tax Registration Number
		Thread.sleep(1000);
		//action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, DUPLICATEDTAXREGISTRATIONNO);
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, DUPLICATEDTAXREGISTRATIONNO);
		WebElement txtField1 = driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID));
		txtField1.clear();
		txtField1.sendKeys(DUPLICATEDTAXREGISTRATIONNO);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, COMPANYREGIRATIONNO);
		
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATEDTAXREGISTRATIONNO);*/
	}
	
	// Step 5
	@Test(dependsOnMethods="createSupplierWithInvalidEmail")
	public void createSupplierWithValidValue() throws InterruptedException{
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, COMPANYREGIRATIONNO);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.SUPPLIER_CREATED_SUCCESSFULLY);
	}
}

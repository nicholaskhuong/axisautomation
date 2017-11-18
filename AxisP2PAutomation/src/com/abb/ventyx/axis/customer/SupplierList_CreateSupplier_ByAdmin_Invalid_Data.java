package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
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
@ALM(id = "607")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_CreateSupplier_ByAdmin_Invalid_Data extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	String pendingStatus = "Pending";
	public static String profile = "All Document Types";
	String invalidEmail = "<HTML>";
	String passwordOriginally = "Testuser2";
	String newPassword = "Testuser1";

	String dupplicatedName = "ENCLAVE";
	String duplicatedCompanyRegistrationNo = "ENCLAVE";
	String duplicatedTaxRegistrationNo = "ENCLAVE";
	String duplicatedSupplierEmail = "perla@enclave.vn";
	String axisSupportEmail = "axis_support@abb.com";
	String axisSupportPWD = "Testuser1";
	String password;
	String supplierName_draft = "Yamaha1231313";
	String supplierEmail_draft = "yamaha13213@abb.com";
	String companyRegistrationNo_draft = "COMYAMAHA12313213213";
	String taxRegistrationNo_draft = "TAXYAMAHA123313213";

	// Step 1
	@Test
	public void openSupplierListScreen(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		wait = new WebDriverWait(driver, 20);
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 100000000L);
		supplierName_draft = String.format("Name %s", drand);
		supplierEmail_draft = String.format("%s@abb.com", drand);
		companyRegistrationNo_draft = String.format("NO%s", drand);
		taxRegistrationNo_draft = String.format("Tax%s", drand);

		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
	}

	// Step 2
	@Test(dependsOnMethods = "openSupplierListScreen", alwaysRun = true)
	public void clickAddButton() {
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisible(By.id(ScreenObjects.CREATE_BTN_ID));
		action.assertDocumentTitle("Check Suppliers");
	}

	// Step 3
	@Test(dependsOnMethods = "clickAddButton", alwaysRun = true)
	public void clickCreateButton() {
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.assertTextEqual(By.xpath(SupplierList.CREATENEWSUPPLIERTITLE), "Create New Supplier");
		action.pause(1000);
		action.assertTextEqual(By.id(SupplierList.SUPPLIERNAME_ID), "");
		action.assertTextEqual(By.id(SupplierList.COMPANYREGISTRATIONNO_ID), "");
		action.assertTextEqual(By.id(SupplierList.TAXREGRISTRATIONNO_ID), "");
		action.assertTextEqual(By.id(SupplierList.SUPPLIEREMAIL_ID), "");

		action.clickBtn(By.xpath(SupplierList.SUPPLIERSTATUS_XPATH));
		action.pause(1000);
		int row = table.countRow(SupplierList.COMBOBOX_CSS);
		assertEquals(row, 2);
		action.assertTextEqual(By.xpath(SupplierList.FIRSTSTATUS), "Active");
		action.assertTextEqual(By.xpath(SupplierList.SECONDSTATUS), "Inactive");
		// Close combo box 
		action.clickBtn(By.xpath(SupplierList.SUPPLIERSTATUS_XPATH));
	}
	// Step 4
	@Test(dependsOnMethods = "clickCreateButton", alwaysRun = true)
	public void clickSaveWithoutInput() {		
		// Leave all textbox empty
		// Click Save
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);

		driver.findElement(By.id(SupplierList.SUPPLIERNAME_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}


	// Step 7
	@Test(dependsOnMethods = "clickSaveWithoutInput", alwaysRun = true)
	public void createSupplierWithBlankMandatoryField() {
		// Empty Supplier Name
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, supplierEmail_draft);
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, companyRegistrationNo_draft);
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, taxRegistrationNo_draft);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		driver.findElement(By.id(SupplierList.SUPPLIERNAME_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 8
	@Test(dependsOnMethods = "createSupplierWithBlankMandatoryField", alwaysRun = true)
	public void createSupplierWithEmptyCompanyRegistrationNo() {
		// Empty Company Registration No
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, supplierName_draft);
		driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID)).clear();
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		driver.findElement(By.id(SupplierList.COMPANYREGISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 9
	@Test(dependsOnMethods = "createSupplierWithEmptyCompanyRegistrationNo", alwaysRun = true)
	public void createSupplierWithEmptyTaxRegistrationNo() {
		// Empty Tax Registration No
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, companyRegistrationNo_draft);
		driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID)).clear();
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 5
	@Test(dependsOnMethods = "createSupplierWithEmptyTaxRegistrationNo", alwaysRun = true)
	public void createSupplierWithEmptySupplierEmail() {
		// Empty Supplier Email
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, taxRegistrationNo_draft);
		driver.findElement(By.id(SupplierList.SUPPLIEREMAIL_ID)).clear();
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
		driver.findElement(By.id(SupplierList.SUPPLIEREMAIL_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 5
	@Test(dependsOnMethods = "createSupplierWithEmptySupplierEmail", alwaysRun = true)
	public void createSupplierWithInvalidEmail() {

		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, invalidEmail);
		action.clickBtn(By.id(SupplierList.TAXREGRISTRATIONNO_ID));
		action.pause(500);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		driver.findElement(By.id(SupplierList.SUPPLIEREMAIL_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 10
	@Test(dependsOnMethods = "createSupplierWithInvalidEmail", alwaysRun = true)
	public void createSupplierWithDuplicatedEmail() {

		// Duplicated email
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, duplicatedSupplierEmail);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATEDEMAIL);
		driver.findElement(By.id(SupplierList.SUPPLIEREMAIL_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}

	@Test(dependsOnMethods = "createSupplierWithDuplicatedEmail", alwaysRun = true)
	public void createSupplierWithDuplicatedCompanyRegistrationNo() {
		// Duplicated Comp. Registration Number
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, supplierEmail_draft);
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, duplicatedCompanyRegistrationNo);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATECOMPANYREGISTRATIONNO);
		driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
	}

	@Test(dependsOnMethods = "createSupplierWithDuplicatedCompanyRegistrationNo", alwaysRun = true)
	public void createSupplierWithDuplicatedTaxRegistrationNo() {
		// Duplicated Tax Registration Number
		action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, duplicatedTaxRegistrationNo);
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, companyRegistrationNo_draft);

		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATEDTAXREGISTRATIONNO);
		driver.findElement(By.id(SupplierList.COMPANYREGISTRATIONNO_ID)).clear();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));

	}

}

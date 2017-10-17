package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.CustomerUsers;
import com.abb.ventyx.axis.objects.pagedefinitions.LoginPageDefinition;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierList;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
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
	public static int j;

	@TestDataKey private final String SUPPLIERNAME = "Yamaha9";
	@TestDataKey private final String SUPPLIEREMAIL = "yamaha9@abb.com";
	@TestDataKey private final String COMPANYREGIRATIONNO = "COMYAMAHA9";
	@TestDataKey private final String TAXREGIRATIONNO = "TAXYAMAHA9";
	@TestDataKey private final String PENDINGSTATUS = "Pending";
	@TestDataKey private final String PROFILE = "All Document Types";
	@TestDataKey private final String INVALIDEMAIL = "<HTML>";
	@TestDataKey private final String PASSWORDORIGINALLY = "Testuser2";
	@TestDataKey private final String NEWPASSWORD = "Testuser1";

	@TestDataKey private final String DUPLICATEDNAME = "ENCLAVE";
	@TestDataKey private final String DUPLICATEDCOMPANYREGISTRATIONNO = "ENCLAVE";
	@TestDataKey private final String DUPLICATEDTAXREGISTRATIONNO = "ENCLAVE";
	@TestDataKey private final String DUPLICATEDSUPPLIEREMAIL = "perla@enclave.vn";
	@TestDataKey private final String AXISSUPPORTEMAIL = "mail5@abb.com";
	@TestDataKey private final String AXISSUPPORTPWD = "testuser";

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
		action = new ScreenAction(driver);

		// Duplicated email
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, DUPLICATEDSUPPLIEREMAIL);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATEDEMAIL);


		// Duplicated Comp. Registration Number
		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, SUPPLIEREMAIL);

		WebElement txtField = driver.findElement(By.id(SupplierList.COMPANYREGISTRATIONNO_ID));
		txtField.clear();
		Thread.sleep(1000);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		txtField.sendKeys(DUPLICATEDCOMPANYREGISTRATIONNO);
		//action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, DUPLICATEDCOMPANYREGISTRATIONNO);
		//Thread.sleep(2000);

		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATECOMPANYREGISTRATIONNO);
		// Duplicated Tax Registration Number
		Thread.sleep(1000);
		//action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, DUPLICATEDTAXREGISTRATIONNO);
		/*action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID, DUPLICATEDTAXREGISTRATIONNO);
		
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
		action = new ScreenAction(driver);
		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, COMPANYREGIRATIONNO);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.SUPPLIER_CREATED_SUCCESSFULLY);

		i = table.findRowByString1(6, SUPPLIEREMAIL);
		System.out.print("print i: "+i);
		assertEquals(table.getValueRow(2, i),COMPANYREGIRATIONNO);
		assertEquals(table.getValueRow(3, i),TAXREGIRATIONNO);
		assertEquals(table.getValueRow(4, i), PENDINGSTATUS);
		assertEquals(table.getValueRow(5, i),SUPPLIERNAME);
		assertEquals(table.getValueRow(6, i),SUPPLIEREMAIL);
		assertEquals(table.getValueRow(7, i),PROFILE);
		j=i-1;
		action.isFieldDisable(By.id("accessSupplierBtn"+j));
	}

	// Step 6,7 can't auto
	// Step 8 
	@Test(dependsOnMethods="createSupplierWithValidValue")
	public void checkCancelButtonWithoutInput() throws InterruptedException{
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)),false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)),true);
		//action.assertTitleScreen("Check Suppliers");
	}

	// Step 9,10 Check No button on Unsaved Changes dialog 
	@Test(dependsOnMethods="checkCancelButtonWithoutInput")
	public void checkNoButtonOnUnsavedChangesDialog() throws InterruptedException{
		action = new ScreenAction(driver);	
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));	
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));	
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, "test");
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		Thread.sleep(1000);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		//action.assertTitleScreen("Create New Supplier");
	}

	// Step 11,12 Check Cancel button on Unsaved Changes dialog
	@Test(dependsOnMethods="checkNoButtonOnUnsavedChangesDialog")
	public void checkYesButtonOnUnsavedChangesDialog() {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		//action.assertTitleScreen("Check Suppliers");
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)),true);
	}

	// Step 14
	@Test(dependsOnMethods="checkYesButtonOnUnsavedChangesDialog")
	public void loginAsTheCreatedSupplier() {
		action = new ScreenAction(driver);
		action.signOut();
		action.signIn(SUPPLIEREMAIL, PASSWORDORIGINALLY);
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.ERROR_CSS), Messages.USERNOTFOUND);
	}
	// Step 15
	@Test(dependsOnMethods="loginAsTheCreatedSupplier")
	public void getPasswordForTheNewSupplier() throws InterruptedException{
		action = new ScreenAction(driver);
		action.signIn(AXISSUPPORTEMAIL, AXISSUPPORTPWD);
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisibleAndClick(By.cssSelector(AxisConfigMenu.RESET_USER_PASSWORD));
		Thread.sleep(2000);
		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.EMAILCHECKBOX_RESETUSERPASSWORDSCREEN_ID));
		Thread.sleep(500);
		action.inputTextField(ScreenObjects.USER_LOGIN, SUPPLIEREMAIL);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.RESET_BUTTON));
		action.waitObjVisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));

		String message = driver.findElement(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE)).getText();
		System.out.println("Message: "+ message);
		String password = action.getPassword(message);
		System.out.println(password);

		action.signOut();
		Thread.sleep(1000);
		action.signIn(SUPPLIEREMAIL, password);

		action.waitObjVisible(By.id(ScreenObjects.NEWPASSWORD_ID));
		action.inputTextField(LoginPageDefinition.PASSWORD_TEXT_FIELD_ID, password);
		action.inputTextField(ScreenObjects.NEWPASSWORD_ID, NEWPASSWORD);
		action.inputTextField(ScreenObjects.CONFIRMPASSWORD_ID,NEWPASSWORD);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
	}
	// Step 16
	@Test(dependsOnMethods="getPasswordForTheNewSupplier")
	public void acceptTradingRelationshipRequest() throws InterruptedException{
		action = new ScreenAction(driver);
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.ACCEPT_BUTTON_CSS)));
		action.clickBtn(By.cssSelector(ScreenObjects.ACCEPT_BUTTON_CSS));

	}
	@Test(dependsOnMethods="acceptTradingRelationshipRequest")
	public void checkAddressAndContact() throws InterruptedException{
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id(AddressContact.COMPANY_NAME)));
		action.assertTextEqual(By.id(AddressContact.COMPANY_NAME), SUPPLIERNAME);
		action.assertTextEqual(By.id(AddressContact.COMPANY_REGISTRATION_NO), COMPANYREGIRATIONNO);
		action.assertTextEqual(By.id(AddressContact.TAX_REGISTRATION_NO), TAXREGIRATIONNO);
		action.assertTextEqual(By.id(AddressContact.SUPPLIER_EMAIL), SUPPLIEREMAIL);
	}
	
	@Test(dependsOnMethods="checkAddressAndContact")
	public void checkStatusAndRemoteIcon() throws InterruptedException{
		action = new ScreenAction(driver);
		action.signOut();
		Thread.sleep(1000);
		action.signIn("cadmin1@abb.com", "Testuser1");
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
		
		assertEquals(table.getValueRow(4, i), "Active");
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),false);
	
	}


}

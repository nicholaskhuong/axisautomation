package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
@ALM(id = "607")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_CreateNewSupplier_ByAdmin extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	WebDriverWait wait;
	int i, j;

	String SUPPLIERNAME = "Yamaha10";
	String SUPPLIEREMAIL = "yamaha10@abb.com";
	String COMPANYREGIRATIONNO = "COMYAMAHA10";
	String TAXREGIRATIONNO = "TAXYAMAHA10";
	String PENDINGSTATUS = "Pending";
	String PROFILE = "All Document Types";
	String INVALIDEMAIL = "<HTML>";
	String PASSWORDORIGINALLY = "Testuser2";
	String NEWPASSWORD = "Testuser1";

	String DUPLICATEDNAME = "ENCLAVE";
	String DUPLICATEDCOMPANYREGISTRATIONNO = "ENCLAVE";
	String DUPLICATEDTAXREGISTRATIONNO = "ENCLAVE";
	String DUPLICATEDSUPPLIEREMAIL = "perla@enclave.vn";
	String AXISSUPPORTEMAIL = "mail5@abb.com";
	String AXISSUPPORTPWD = "testuser";

	// Step 1
	@Test
	public void openSupplierListScreen(){
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		wait = new WebDriverWait(driver, 20);
		Random rand = new Random();
		long drand = (long) (rand.nextDouble() * 10000000000L);
		SUPPLIERNAME = String.format("Name %s",drand);
		SUPPLIEREMAIL = String.format("%s@abb.com", drand);
		COMPANYREGIRATIONNO = String.format("NO%s", drand);
		TAXREGIRATIONNO = String.format("Tax%s", drand);

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
	@Test(dependsOnMethods = "openSupplierListScreen")
	public void createSupplierWithBlankMandatoryField() {

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
	@Test(dependsOnMethods = "createSupplierWithBlankMandatoryField")
	public void createSupplierWithInvalidEmail() {

		action.inputTextField(SupplierList.SUPPLIEREMAIL_ID, INVALIDEMAIL);
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.INVALID_EMAIL);
		action.clickBtn(By.id(SupplierList.SUPPLIEREMAIL_ID));
		action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	}

	// Step 4
	@Test(dependsOnMethods = "createSupplierWithInvalidEmail")
	public void createSupplierWithDuplicatedValue() throws InterruptedException {

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
		// action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID,
		// DUPLICATEDCOMPANYREGISTRATIONNO);
		// Thread.sleep(2000);

		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.DUPLICATECOMPANYREGISTRATIONNO);
		// Duplicated Tax Registration Number
		// action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID,
		// DUPLICATEDTAXREGISTRATIONNO);
		/*
		 * action.inputTextField(SupplierList.TAXREGRISTRATIONNO_ID,
		 * DUPLICATEDTAXREGISTRATIONNO);
		 * 
		 * WebElement txtField1 =
		 * driver.findElement(By.id(SupplierList.TAXREGRISTRATIONNO_ID));
		 * txtField1.clear(); txtField1.sendKeys(DUPLICATEDTAXREGISTRATIONNO);
		 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		 * action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID,
		 * COMPANYREGIRATIONNO);
		 * 
		 * action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		 * action.assertMessgeError(ScreenObjects.ERROR_CSS,
		 * Messages.DUPLICATEDTAXREGISTRATIONNO);
		 */
	}

	// Step 5
	@Test(dependsOnMethods = "createSupplierWithInvalidEmail")
	public void createSupplierWithValidValue() throws InterruptedException {

		action.inputTextField(SupplierList.COMPANYREGISTRATIONNO_ID, COMPANYREGIRATIONNO);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.assertMessgeError(ScreenObjects.SUCCESS_MESSAGE, Messages.SUPPLIER_CREATED_SUCCESSFULLY);
		table.filter(SupplierList.SUPPLIER_EMAIL_FILTER_XPATH, SUPPLIEREMAIL);
		i = table.findRowByString1(6, SUPPLIEREMAIL);
		Assert.assertTrue(i>0,"Supplier doesn't exist");
		assertEquals(table.getValueRow(2, i), COMPANYREGIRATIONNO);
		assertEquals(table.getValueRow(3, i), TAXREGIRATIONNO);
		assertEquals(table.getValueRow(4, i), PENDINGSTATUS);
		assertEquals(table.getValueRow(5, i), SUPPLIERNAME);
		assertEquals(table.getValueRow(6, i), SUPPLIEREMAIL);
		assertEquals(table.getValueRow(7, i), PROFILE);
		WebElement accessColumn = driver.findElement(By
				.xpath("//*[@id='content-component']/div/div[2]/div/div/div[3]/div/div/div/div/div/div/div/div[3]/table/tbody/tr/td[8]"));
		List<WebElement> allEle = accessColumn.findElements(By.cssSelector("[id^='accessSupplierBtn']"));
		Assert.assertTrue(allEle.size() > 0, "No element found!");
		action.isFieldDisable(By.id(allEle.get(0).getAttribute("id")));
	}

	// Step 6,7 can't auto
	// Step 8
	@Test(dependsOnMethods = "createSupplierWithValidValue")
	public void checkCancelButtonWithoutInput() throws InterruptedException {

		action.waitObjVisibleAndClick(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)), true);
		// action.assertTitleScreen("Check Suppliers");
	}

	// Step 9,10 Check No button on Unsaved Changes dialog
	@Test(dependsOnMethods = "checkCancelButtonWithoutInput")
	public void checkNoButtonOnUnsavedChangesDialog() throws InterruptedException {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CREATE_BTN_ID));
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.inputTextField(SupplierList.SUPPLIERNAME_ID, "test");
		action.clickBtn(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		action.waitObjVisibleAndClick(By.id(ScreenObjects.NO_BTN_ID));
		Thread.sleep(1000);
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		// action.assertTitleScreen("Create New Supplier");
	}

	// Step 11,12 Check Cancel button on Unsaved Changes dialog
	@Test(dependsOnMethods = "checkNoButtonOnUnsavedChangesDialog")
	public void checkYesButtonOnUnsavedChangesDialog() {

		action.waitObjVisibleAndClick(By.id(ScreenObjects.CANCEL_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS), Messages.UNSAVED_CHANGE);
		driver.findElement(By.id(ScreenObjects.YES_BTN_ID)).click();
		action.waitObjInvisible(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS));
		assertEquals(action.isElementPresent(By.cssSelector(ScreenObjects.UNSAVED_CHANGE_CSS)), false);
		// action.assertTitleScreen("Check Suppliers");
		assertEquals(action.isElementPresent(By.id(SupplierList.SUPPLIERNAME_SEARCHFIELD_ID)), true);
	}

	// Step 14
	@Test(dependsOnMethods = "checkYesButtonOnUnsavedChangesDialog")
	public void loginAsTheCreatedSupplier() {

		action.signOut();
		action.signIn(SUPPLIEREMAIL, PASSWORDORIGINALLY);
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_CSS));
		action.assertTextEqual(By.cssSelector(ScreenObjects.ERROR_CSS), Messages.USERNOTFOUND);
	}
	// Step 15
	@Test(dependsOnMethods = "loginAsTheCreatedSupplier")
	public void getPasswordForTheNewSupplier() throws InterruptedException{

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


		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector(ScreenObjects.ACCEPT_BUTTON_CSS)));
		action.clickBtn(By.cssSelector(ScreenObjects.ACCEPT_BUTTON_CSS));

	}

	@Test(dependsOnMethods = "acceptTradingRelationshipRequest", alwaysRun = true)
	public void checkAddressAndContact() throws InterruptedException{

		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));

		WebElement companyName = wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id(AddressContact.COMPANY_NAME)));
		Assert.assertEquals(companyName.getAttribute("value"), SUPPLIERNAME);
		action.assertTextEqual(By.id(AddressContact.COMPANY_REGISTRATION_NO), COMPANYREGIRATIONNO);
		action.assertTextEqual(By.id(AddressContact.TAX_REGISTRATION_NO), TAXREGIRATIONNO);
		action.assertTextEqual(By.id(AddressContact.SUPPLIER_EMAIL), SUPPLIEREMAIL);
	}
	
	@Test(dependsOnMethods = "checkAddressAndContact", alwaysRun = true)
	public void checkStatusAndRemoteIcon() throws InterruptedException{

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

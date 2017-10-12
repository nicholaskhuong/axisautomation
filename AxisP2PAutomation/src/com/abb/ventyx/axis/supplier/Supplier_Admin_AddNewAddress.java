package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AddressContact;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "604")
@Credentials(user = "donna900@abb.com", password = "Testuser2")
public class Supplier_Admin_AddNewAddress extends BaseTestCase {

	String Supplier_Email = "duc800@abb.com";
	String Supplier_Multi_Email = "duc800@abb.com,donna@abb.com,hieunguyen@abb.com";
	String Supplier_Invalid_Email = "invalid";
	String Company_Registration_No = "$%#%%%(&";
	String Tax_Registration_No = "$%#%%%(&";
	String supplier_name_Valid = "Donna Nguyen Thi";
	String Company_Registration_No_Valid = "123456789";
	String Tax_Registration_No_Valid = "123456";
	ScreenAction action;
	TableFunction table;


	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		// Step 1:
		// click on Administration button
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		// Click on Address & Contact button
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADDRESS_CONTACT_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.SUPPLIER_NAME));
		action.assertTitleScreen(AddressContact.TITLE_ADDRESS_CONTACT);

	}
	// Step 2
	@Test(dependsOnMethods = "openScreen")
	public void updateSupplierEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		// input supplier ID
 		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		// update the email address
		WebElement supplier_name = driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_name.clear();
		Thread.sleep(900);
		supplier_name.sendKeys(Supplier_Email);
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
	}
	// Step 4
	@Test(dependsOnMethods = "updateSupplierEmail")
	public void InvalidEmail() throws InterruptedException {
		action = new ScreenAction(driver);
		// input supplier ID
		action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
		// update the email address
		WebElement supplier_name = driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_name.clear();
		Thread.sleep(900);
		supplier_name.sendKeys(Supplier_Invalid_Email);
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.INVALIED_EMAIL,
				Messages.INVALIED_EMAIL_MESSAGE);
		}
	// Step 3
		@Test(dependsOnMethods = "InvalidEmail")
		public void updateMulSupplierEmail() throws InterruptedException {
			action = new ScreenAction(driver);
			// input supplier ID
			action.waitObjVisible(By.id(AddressContact.SUPPLIER_EMAIL));
			// update the email address
			WebElement supplier_name = driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
			supplier_name.clear();
			Thread.sleep(900);
			supplier_name.sendKeys(Supplier_Multi_Email);
			Thread.sleep(900);
			action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
			action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
			}
	// Step 5,6
	@Test(dependsOnMethods = "updateMulSupplierEmail")
	public void CheckSpecial_Character() throws InterruptedException {
		action = new ScreenAction(driver);
		// update the COMPANY_REGISTRATION_NO 
		WebElement comapny_registration_no = driver.findElement(By.id(AddressContact.COMPANY_REGISTRATION_NO));
		comapny_registration_no.clear();
		Thread.sleep(900);
		comapny_registration_no.sendKeys(Company_Registration_No);
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SPECIAL_CHARACTER,
				Messages.NO_SPECIAL_CHARACTER_ALLOWED);
		
		WebElement tax_registration_no =  driver.findElement(By.id(AddressContact.TAX_REGISTRATION_NO));
		tax_registration_no.clear();
		Thread.sleep(900);
		tax_registration_no.sendKeys(Tax_Registration_No);
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.SPECIAL_CHARACTER,
				Messages.NO_SPECIAL_CHARACTER_ALLOWED);	
		action.waitObjInvisible(By.cssSelector(ScreenObjects.SPECIAL_CHARACTER));
		WebElement supplier_name= driver.findElement(By.id(AddressContact.COMPANY_NAME));
		supplier_name.clear();
		Thread.sleep(900);
		supplier_name.sendKeys("");
		Thread.sleep(900);
		comapny_registration_no.clear();
		Thread.sleep(900);
		comapny_registration_no.sendKeys("");
		Thread.sleep(900);
		tax_registration_no.clear();
		Thread.sleep(900);
		tax_registration_no.sendKeys("");
		WebElement supplier_email= driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_email.clear();
		Thread.sleep(900);
		supplier_email.sendKeys("");
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.assertMessgeError(ScreenObjects.EMPTY_DATE,
				Messages.NO_INPUT_DATA);
		}
	// Step 7
	@Test(dependsOnMethods = "updateMulSupplierEmail")
	public void Save_Successfully() throws InterruptedException {
		action = new ScreenAction(driver);
		WebElement supplier_name= driver.findElement(By.id(AddressContact.COMPANY_NAME));
		supplier_name.clear();
		Thread.sleep(900);
		supplier_name.sendKeys(supplier_name_Valid);
		Thread.sleep(900);
		WebElement comapny_registration_no = driver.findElement(By.id(AddressContact.COMPANY_REGISTRATION_NO));
		comapny_registration_no.clear();
		Thread.sleep(900);
		comapny_registration_no.sendKeys(Company_Registration_No_Valid);
		Thread.sleep(900);
		WebElement tax_registration_no =  driver.findElement(By.id(AddressContact.TAX_REGISTRATION_NO));
		tax_registration_no.clear();
		Thread.sleep(900);
		tax_registration_no.sendKeys(Tax_Registration_No_Valid);
		Thread.sleep(900);
		WebElement supplier_email= driver.findElement(By.id(AddressContact.SUPPLIER_EMAIL));
		supplier_email.clear();
		Thread.sleep(900);
		supplier_email.sendKeys(Supplier_Email);
		Thread.sleep(900);
		action.clickBtn(By.id(ScreenObjects.YES_BTN_ID));
		action.checkAddSuccess(Messages.ADDRESS_CONTACT_SUCCESSFULLY_UPDATED);
	
		}
	
	// Step 8
		@Test(dependsOnMethods = "Save_Successfully")
		public void CheckSupplierList() throws InterruptedException {
			/*Login to customer admin
			Click Supplier List
			Select a supplier with multiple email, update data
			
			Check data is changed at header will be updated in this screen and vice versa
			Data updated successfully
*/

		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

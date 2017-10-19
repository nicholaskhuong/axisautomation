package com.abb.ventyx.axis.customer;

import static org.testng.Assert.assertEquals;

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
import com.ventyx.testng.TestDataKey;
@ALM(id = "614")
@Credentials(user = "cadmin1@abb.com", password = "Testuser1")
public class SupplierList_UpdateSupplier_ByAdmin extends BaseTestCase {
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
	@TestDataKey private final String ACTIVESTATUS = "Active";
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
	
	@TestDataKey private final String NEWSUPPLIERNAME = "Yamaha9UPDATED";
	@TestDataKey private final String PROFILEUPDATED = "ASNOFF";

	// Step 1
	@Test
	public void openSupplierListScreen(){
		table = new TableFunction(driver);
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.CUSTOMERMAINTENANCE_MENU));
		action.waitObjVisibleAndClick(By.cssSelector(CustomerMenu.SUPPLIERLIST_SUBMENU));
		action.waitObjVisible(By.cssSelector(CustomerUsers.ADD_BUTTON));
	}

	// Step 2 Update with null data
	@Test(dependsOnMethods="openSupplierListScreen")
	public void updateSupplierWithBlankMandatoryField(){
		table = new TableFunction(driver);
		action = new ScreenAction(driver);

		i = table.findRowByString1(6, SUPPLIEREMAIL);
		System.out.println("print i: "+i);
		assertEquals(table.getValueRow(2, i), COMPANYREGIRATIONNO);
		assertEquals(table.getValueRow(3, i), TAXREGIRATIONNO);
		assertEquals(table.getValueRow(4, i), ACTIVESTATUS);
		assertEquals(table.getValueRow(5, i), SUPPLIERNAME);
		assertEquals(table.getValueRow(6, i), SUPPLIEREMAIL);
		assertEquals(table.getValueRow(7, i), PROFILE);
		j=i-1;
		System.out.println("Print J: "+ i);
		assertEquals(action.isFieldDisable(By.id("accessSupplierBtn"+j)),false);
		
		table.clickSupplierIDInSupplierListGrid(TAXREGIRATIONNO);
		action.waitObjVisible(By.id(SupplierList.SUPPLIERNAME_ID));
		action.assertTextBoxDisable(By.id(SupplierList.COMPANYREGISTRATIONNO_ID));
		action.assertTextBoxDisable(By.id(SupplierList.TAXREGRISTRATIONNO_ID));
		action.assertTextBoxDisable(By.id(SupplierList.SUPPLIEREMAIL_ID));
		//action.assertFieldReadOnly(By.id(SupplierList.SUPPLIERADMINUSERID_ID));
		// Empty Supplier Name
		driver.findElement(By.id(SupplierList.SUPPLIERNAME_ID)).clear();
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		action.waitObjVisible(By.cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
		action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS, Messages.ENTER_MANDATORY_FIELDS);
	}

	// Step 3 
	@Test(dependsOnMethods="updateSupplierWithBlankMandatoryField")
	public void updateSupplierWithValidValue(){
		action = new ScreenAction(driver);

		action.inputTextField(SupplierList.SUPPLIERNAME_ID, NEWSUPPLIERNAME);
		action.clickBtn(By.cssSelector(SupplierList.PROFILE_CSS));
		action.selectStatus(SupplierList.COMBOBOX, PROFILEUPDATED);
		action.clickBtn(By.id(SupplierList.SAVEBTN_ID));
		
		
	}


}

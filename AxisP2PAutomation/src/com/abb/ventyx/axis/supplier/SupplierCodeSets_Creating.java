package com.abb.ventyx.axis.supplier;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.BusinessCodeSets;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.SupplierMenu;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "634")
@Credentials(user = "mail232@abb.com", password = "Testuser1")
public class SupplierCodeSets_Creating extends BaseTestCase {
	String USER_ID = "BOSS";
	String PASSWORD = "Testuser1";
	String CONFIRMPASSWORD = "Testuser2";
	String EMAIL = "boss@abb.com";
	ScreenAction action;
	TableFunction table;
	String USERGROUP = "Datherine";
	int row;
	int sumRowBefore;
	int sumRowAfter;

	@Test
	public void openScreen() throws InterruptedException {
		action = new ScreenAction(driver);
		action.waitObjVisibleAndClick(By.id(SupplierMenu.ADMINISTRATION_ID));
		action.waitObjVisibleAndClick(By.id(SupplierMenu.BUSINESS_CODE_SETS_ID));
		action.waitObjVisible(By.id(BusinessCodeSets.TAXCODE_EXPANDBTN_ID));
		action.assertTitleScreen(BusinessCodeSets.TITLE);
	}

	@Test(dependsOnMethods = "openScreen")
	public void addTaxCode() {
		action.clickBtn(By.id(BusinessCodeSets.TAXCODE_EXPANDBTN_ID));
		action.waitObjVisibleAndClick(By
				.xpath(BusinessCodeSets.ADDTAXCODEBTN_XPATH));
		action.inputTextField(BusinessCodeSets.TAXTYPE_ID, "PIT");
		action.inputTextField(BusinessCodeSets.TAXRATE_ID, "10");
		action.clickBtn(By.id(ScreenObjects.SAVE_ID));
		action.checkAddSuccess(Messages.TAXCODEADD_SUCCESSFULLY);
	}
	/*
	 * @Test(dependsOnMethods = "addUsers") public void checkAddSuccessfully()
	 * throws InterruptedException {
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.SUCCESS_MESSAGE));
	 * sumRowAfter = table.countRow(Users.SUPPLIER_USERS_TABLE_CSS);
	 * assertEquals(sumRowAfter, sumRowBefore + 1); table.assertValueRow(2,
	 * sumRowBefore, USER_ID); table.assertValueRow(5, sumRowBefore, "Created");
	 * }
	 * 
	 * @Test(dependsOnMethods = "checkAddSuccessfully") public void
	 * cancelClickYes() {
	 * action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	 * action.waitObjVisible(By.id(Users.USER_ID));
	 * action.inputTextField(Users.USER_ID, "ABC");
	 * action.cancelClickYes(By.cssSelector(ScreenObjects.ADD_BTN_CSS),
	 * Users.TITLE); }
	 * 
	 * @Test(dependsOnMethods = "cancelClickYes") public void cancelClickNo() {
	 * action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	 * action.waitObjVisible(By.id(Users.USER_ID));
	 * action.inputTextField(Users.USER_ID, USER_ID);
	 * action.cancelClickNo(Users.TITLE_CREATE); }
	 * 
	 * @Test(dependsOnMethods = "cancelClickNo") public void cancelWithoutdata()
	 * throws InterruptedException {
	 * 
	 * action.inputTextField(Users.USER_ID, "");
	 * action.cancelWithoutdata(By.cssSelector(ScreenObjects.ADD_BTN_CSS),
	 * Users.TITLE); }
	 * 
	 * @Test(dependsOnMethods = "cancelWithoutdata") public void
	 * addValidationUserID() throws InterruptedException {
	 * action.clickBtn(By.cssSelector(ScreenObjects.ADD_BTN_CSS));
	 * action.waitObjVisible(By.id(Users.USER_ID));
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.ENTER_MANDATORY_FIELDS);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By
	 * .cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)); // input other data
	 * action.inputTextField(Users.PASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.EMAIL_ID, EMAIL); action.clickCheckBoxN(1);
	 * // don't input UserID action.checkValidationTextField(Users.USER_ID,
	 * USER_ID, Messages.USERS_EXISTING, ScreenObjects.ERROR_CSS); }
	 * 
	 * @Test(dependsOnMethods = "addValidationUserID") public void
	 * addValidationEmail() throws InterruptedException { // input other data
	 * action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.PASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD); // don't input
	 * UserID action.checkValidationTextField(Users.EMAIL_ID);
	 * action.inputTextField(Users.EMAIL_ID, "mail232@abb.com");
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_CSS, Messages.SAME_EMAIL);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By.cssSelector(ScreenObjects.ERROR_ICON_CSS));
	 * action.inputTextField(Users.EMAIL_ID, "Boss");
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.INVALID_EMAIL); }
	 * 
	 * @Test(dependsOnMethods = "addValidationEmail") public void
	 * addValidationPassword() throws InterruptedException {
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By
	 * .cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS)); // input other data
	 * action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.EMAIL_ID, EMAIL);
	 * 
	 * // don't input UserID action.inputTextField(Users.PASSWORD_ID, "");
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.ENTER_MANDATORY_FIELDS);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By
	 * .cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	 * action.inputTextField(Users.PASSWORD_ID, CONFIRMPASSWORD);
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.INVALID_CONFIRM_PWD);
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By
	 * .cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	 * action.inputTextField(Users.PASSWORD_ID, "ABC");
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.INVALID_PWD); }
	 * 
	 * @Test(dependsOnMethods = "addValidationPassword") public void
	 * addValidationConfirmPassword() throws InterruptedException { // input
	 * other data action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By
	 * .cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	 * action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.EMAIL_ID, EMAIL);
	 * action.inputTextField(Users.PASSWORD_ID, PASSWORD); // don't input UserID
	 * action.checkValidationTextField(Users.CONFIMRPASSWORD_ID); }
	 * 
	 * @Test(dependsOnMethods = "addValidationConfirmPassword") public void
	 * unselectUserGroup() throws InterruptedException {
	 * 
	 * action.clickBtn(By.id(ScreenObjects.SCREEN_TITLE_ID));
	 * action.waitObjInvisible(By
	 * .cssSelector(ScreenObjects.ERROR_WITHOUT_ICON_CSS));
	 * 
	 * action.inputTextField(Users.USER_ID, "BOSS_1");
	 * action.inputTextField(Users.EMAIL_ID, "boss1@abb.com");
	 * action.inputTextField(Users.PASSWORD_ID, PASSWORD);
	 * action.inputTextField(Users.CONFIMRPASSWORD_ID, PASSWORD);
	 * action.clickCheckBoxN(1); action.clickBtn(By.id(ScreenObjects.SAVE_ID));
	 * action.assertMessgeError(ScreenObjects.ERROR_WITHOUT_ICON_CSS,
	 * Messages.USER_SELECT_USERGROUP); }
	 */
}
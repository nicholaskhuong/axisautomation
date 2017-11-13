package com.abb.ventyx.axis.authencation;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObjects;
import com.abb.ventyx.axis.objects.pagedefinitions.UserPreferences;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "472")
@Credentials(user = "axis_support_4@abb.com", password = "Testuser2")
public class AxisSupportDashboard extends BaseTestCase {
	ScreenAction action;
	TableFunction table;
	String axisSupportTitleHeader = "Axis Support Dashboard";
	String axisAdmin = "Axis Administration";
	String axisConfiguration = "System Configuration";
	String customerMaintenance = "Customer Maintenance";
	String documentation = "Documentation";
	String userName = "Axis Support 4";
	String userID = "axis_support_4@abb.com";
	String axisSupportPortal = "Axis Supplier Portal";
	String users = "Users";
	String reserUserPassword = "Reset User Password";
	String configureUIXML = "Configure UI XML";
	String axisUsergroup = "Axis Usergroup";
	String supplierUsergroup = "Supplier Usergroup";
	String supplierAdministrator = "Supplier Administrator";
	String documentInError = "Documents in Error";
	String cache = "Cache";
	String axisB2BAdapters = "Axis B2B Adapters";
	String documentTypes = "Document Types";
	String permissions = "Permissions";
	String processComponents = "Process Components";
	String busniessCodeSetType = "Business Code Set Type";
	String filterConfiguration = "Filter Configuration";
	String customerList = "Customer List";
	String profiles = "Profiles";
	String busniessCodeSets = "Business Code Sets";
	String customerUserGroup = "Customer Usergroup";
	String pdfFormatParamaters = "PDF Format Parameters";
	String fopResource = "FOP Resource";
	String customiseLiterals = "Customise Literals";
	String auditLog = "Audit Log";
	String userGuide = "User Guide";
	String about = "About";

	@Test
	public void openScreen() {
		// Step 1
		action = new ScreenAction(driver);
		action.assertTitleScreen(axisSupportTitleHeader);
	}

	@Test(dependsOnMethods = "openScreen")
	public void topRibbon() {
		// step 2
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AXIS_SUPPORT_PORTAL)).getText(), axisSupportPortal);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.LANGUAGE)).getText(), "EN");
	}

	@Test(dependsOnMethods = "topRibbon")
	public void checkMenu() {
		// Step 3
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AXIS_ADMIN)).getText(), axisAdmin);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)).getText(), axisConfiguration);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE)).getText(), customerMaintenance);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.DOCUMENTATION)).getText(), documentation);
	}

	@Test(dependsOnMethods = "checkMenu")
	public void submenuOfAxisAdministration() {
		// step 4
		action.clickBtn(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.waitObjVisible(By.cssSelector(AxisConfigMenu.USER_CUSTOMER_ADMIN));
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_CUSTOMER_ADMIN)).getText(), users);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.RESET_USER_PASSWORD)).getText(), reserUserPassword);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.CONFIGURE_UI_XML)).getText(), configureUIXML);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.AXIS_USERGROUP_ID)).getText(), axisUsergroup);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.SUPPLIER_USERGROUP_ID)).getText(), supplierUsergroup);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.SUPPLIER_ADMINISTRATOR)).getText(), supplierAdministrator);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.DOCUMENT_IN_ERROR)).getText(), documentInError);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.CACHE)).getText(), cache);
	}

	@Test(dependsOnMethods = "submenuOfAxisAdministration")
	public void submenuOfSystemconfiguration() {
		// Step 5
		action.clickBtn(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.waitObjVisible(By.id(AxisConfigMenu.AXIS_B2B_ADAPTERS));
		assertEquals(driver.findElement(By.id(AxisConfigMenu.AXIS_B2B_ADAPTERS)).getText(), axisB2BAdapters);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.DOC_TYPE)).getText(), documentTypes);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.PERMISSIONS)).getText(), permissions);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.PROCESS_COMPONENTS)).getText(), processComponents);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.BUSINESS_CODE_SET_TYPE)).getText(), busniessCodeSetType);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.FILTER_CONFIG)).getText(), filterConfiguration);
	}

	@Test(dependsOnMethods = "submenuOfSystemconfiguration")
	public void submenuOfCustomerMaintenance() {
		// step 6
		action.clickBtn(By.cssSelector(AxisConfigMenu.AXIS_ADMIN));
		action.clickBtn(By.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION));
		action.pause(800);
		action.clickBtn(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.waitObjVisible(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST));
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.CUSTOMER_LIST)).getText(), customerList);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.PROFILES)).getText(), profiles);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.BUSINESS_CODE_SETS)).getText(), busniessCodeSets);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_GROUP_CUSTOMER)).getText(), customerUserGroup);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USERS)).getText(), users);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.PDF_FORMAT_PARAMETERS)).getText(), pdfFormatParamaters);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.FOP_RESOURCE_PROFILES)).getText(), fopResource);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.CUSTOMISE_LITERALS)).getText(), customiseLiterals);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.AUDIT_LOG)).getText(), auditLog);
	}

	@Test(dependsOnMethods = "submenuOfCustomerMaintenance")
	public void submenuOfDocumentation() {
		// step 7
		action.clickBtn(By.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE));
		action.clickBtn(By.cssSelector(AxisConfigMenu.DOCUMENTATION));
		action.waitObjVisible(By.id(AxisConfigMenu.USER_GUIDE));
		assertEquals(driver.findElement(By.id(AxisConfigMenu.USER_GUIDE)).getText(), userGuide);
		assertEquals(driver.findElement(By.id(AxisConfigMenu.ABOUT)).getText(), about);
	}

	@Test(dependsOnMethods = "submenuOfDocumentation")
	public void nameOfUser() {
		// step 8
		action.pause(800);
		assertEquals(driver.findElement(By.id(UserPreferences.PROFILE_PANEL)).getText(), userName);
		action.clickBtn(By.id(UserPreferences.PROFILE_PANEL));
		action.waitObjVisible(By.id(ScreenObjects.SIGNOUT_BUTTON));
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_LOGIN)).getText(), userName);
		assertEquals(driver.findElement(By.cssSelector(AxisConfigMenu.USER_ID)).getText(), userID);
	}

	@Test(dependsOnMethods = "nameOfUser")
	public void logout() {
		// step 9
		action.waitObjVisibleAndClick(By.id(ScreenObjects.SIGNOUT_BUTTON));
	}

}

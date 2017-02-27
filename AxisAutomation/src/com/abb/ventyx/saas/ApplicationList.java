package com.abb.ventyx.saas;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.abb.ventyx.saas.objects.pages.HomePage;
import com.abb.ventyx.saas.objects.pages.UserApplicationPage;
import com.abb.ventyx.utilities.BaseTestCase;

public class ApplicationList extends BaseTestCase {
	@Test(dataProvider = "ListApplications")
	public void checkApplicationInList(String applicationName) {
		super.expectedResult = "The application appears in the list";
		HomePage homepage = super.homePage;
		homepage.loginWithCorrectEmail();
		UserApplicationPage userApplicationPage = new UserApplicationPage();
		Assert.assertTrue(userApplicationPage.isApplicationExist(applicationName), "Application is not existing");
	}

	@DataProvider
	public Object[][] ListApplications() {
		Object[][] Lists = new Object[3][1];
		Lists[0][0] = "APPROVALS EL84SUP (8.4)";
		Lists[1][0] = "APPROVALS EL87ST (8.9)"; // wrong test data
		Lists[2][0] = "APPROVELEAVE EL83SUP (8.3)";
		return Lists;
	}
}

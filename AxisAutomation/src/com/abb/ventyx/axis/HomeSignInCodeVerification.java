package com.abb.ventyx.axis;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pages.HomePage;
import com.abb.ventyx.utilities.BaseTestCase;

public class HomeSignInCodeVerification extends BaseTestCase{
	
	@Test(dataProvider = "VerificationCodes")
	public void verifyCodes(String code) {
		super.expectedResult = "There is not any error message appears";
		String correctedEmail = "u@example.com";
		HomePage homepage = super.homePage;
		homepage.login(correctedEmail);
		homepage.verifyCode(code);
		homepage.clickVerifyButton();
		Assert.assertTrue(!homepage.isErrorAppear(), "Cannot verify with code: " + code);
	}

	@DataProvider
	public Object[][] VerificationCodes() {
		Object[][] Codes = new Object[3][1];
		Codes[0][0] = "a";
		Codes[1][0] = "z";
		Codes[2][0] = "u";
		return Codes;
	}

}

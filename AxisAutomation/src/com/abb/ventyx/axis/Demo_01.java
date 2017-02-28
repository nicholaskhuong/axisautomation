package com.abb.ventyx.axis;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.BaseTestCase;

public class Demo_01 extends BaseTestCase {

	@Test(dataProvider = "LoginCredentials")
	public void login(String email, String code) {

		super.homePage.login(email);
		super.homePage.verifyCode(code);

	}

	@DataProvider
	public Object[][] LoginCredentials() {
		// Created two dimensional array with 4 rows and 2 columns.
		// 4 rows represents test has to run 4 times.
		// 2 columns represents 2 data parameters.
		Object[][] Cred = new Object[3][2];

		Cred[0][0] = "u@example.com";
		Cred[0][1] = "u";

		Cred[1][0] = "u@example.com";
		Cred[1][1] = "u";

		Cred[2][0] = "u@exampl1e.com";
		Cred[2][1] = "u";
		//
		// Cred[3][0] = "UserId4";
		// Cred[3][1] = "Pass4";
		return Cred; // Returned Cred
	}

}

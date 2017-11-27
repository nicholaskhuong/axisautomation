package com.abb.ventyx.axis.authencation;

import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.ScreenAction;
import com.abb.ventyx.utilities.TableFunction;

@ALM(id = "457")
@Credentials(user = "", password = "")
public class Forgot_Password extends BaseTestCase {
	ScreenAction action;
	TableFunction table;

	@Test
	public void openScreen() {
		// Pre-condition
		action = new ScreenAction(driver);
		table = new TableFunction(driver);
		

	}

	@Test(dependsOnMethods = "openScreen")
	public void checkValidation() {
		// step 1


	}


}

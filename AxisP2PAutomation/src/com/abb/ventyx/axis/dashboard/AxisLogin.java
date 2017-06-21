package com.abb.ventyx.axis.dashboard;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Constants;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.TestData;

import org.testng.annotations.Test;
@ALM(id = "1") 
@Credentials(user = "mail5@abb.com", password = "testuser")
@TestData(fileName = "login.xls")
public class AxisLogin extends BaseTestCase {

  @Test(dataProvider="empLogin")
  public void Login(String UserName, String Password) throws Exception {
//	  	driver.navigate().to(getServerURL() + "/SupplierPortal/#!CustomerAdminDashboard");
	  	System.out.println(UserName +" " +Password);
  }
}

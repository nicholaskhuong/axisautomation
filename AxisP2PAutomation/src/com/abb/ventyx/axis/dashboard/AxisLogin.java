package com.abb.ventyx.axis.dashboard;

import com.abb.ventyx.axis.objects.pages.LoginPage;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;
import com.abb.ventyx.utilities.TestData;

import org.testng.annotations.Test;
@ALM(id = "1") 
@Credentials(user = "axis_support@abb.com", password = "Testuser1")
@TestData(fileName = "login.xls", startRow =1, endRow=2)
public class AxisLogin extends BaseTestCase{

  @Test(dataProvider="ExcelDataProvider")
  public void Login(String UserName, String Password) throws Exception {
//	  	driver.navigate().to(getServerURL() + "/SupplierPortal/#!CustomerAdminDashboard");
	  	System.out.println(UserName +" " +Password);
  }
}

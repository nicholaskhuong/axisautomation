package com.abb.ventyx.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.abb.ventyx.axis.objects.pages.HomePage;
import com.abb.ventyx.utilities.report.TestMethodResultAdapter;

public class BaseTestCase {
	public WebDriver driver;
	public HomePage homePage;
	public String expectedResult = "";

	@BeforeMethod
	public void beforeMethod() throws Exception {
		DOMConfigurator.configure("log4j.xml");
		this.expectedResult = "";
		DriverCreator driverCreator = new DriverCreator("chrome");
		driver = driverCreator.getWebDriver();
		homePage = new HomePage(driver);
		homePage.startHomePage();
	}
	
	@AfterMethod
	public void afterMethod(ITestResult testResult) throws IOException {
		String screenShotPath = "";
		String takingTime = "";
		String errorMessage = "";
		if (testResult.getStatus() == ITestResult.FAILURE || Constants.CAPTURE_SCREENSHOT) {
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			takingTime = testResult.getEndMillis() + "" + new Random().nextInt(99999);
			screenShotPath = Constants.REPORT_FOLDER + "screenshots/" + testResult.getInstanceName() + "_"
					+ testResult.getName() + "_" + takingTime + ".png";
			FileUtils.copyFile(scrFile, new File(screenShotPath));
			org.testng.Reporter.setCurrentTestResult(testResult);
			org.testng.Reporter.setCurrentTestResult(null);
			if(!Constants.CAPTURE_SCREENSHOT){
				Throwable throwable = testResult.getThrowable();
				errorMessage = throwable.getMessage();
			}
			
		}
		TestMethodResultAdapter resultAdapter = new TestMethodResultAdapter(testResult,
				"screenshots/" + testResult.getInstanceName() + "_" + testResult.getName() + "_" + takingTime + ".png",
				testResult.getTestContext().getCurrentXmlTest().getSuite().getFileName());
		resultAdapter.setActualvalue(errorMessage);
		resultAdapter.setValue(expectedResult);
		Reporter.allResults.add(resultAdapter);
		driver.quit();
	}
}

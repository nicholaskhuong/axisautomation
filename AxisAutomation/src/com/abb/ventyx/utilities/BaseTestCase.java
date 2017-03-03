package com.abb.ventyx.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Random;

import net.sourceforge.htmlunit.corejs.javascript.ast.CatchClause;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.abb.ventyx.axis.objects.pages.HomePage;
import com.abb.ventyx.utilities.report.TestMethodResultAdapter;

public class BaseTestCase {
	public WebDriver driver;
	public HomePage homePage;
	public String expectedResult = "";
	private String testCaseStatus = "pass";
	private String testCaseName = "";
	private String ALMcsvfile = "ALM.csv";

	@BeforeClass
	public void beforeClass() throws Exception {
		DOMConfigurator.configure("log4j.xml");
		this.expectedResult = "";
		DriverCreator driverCreator = new DriverCreator("chrome");
		driver = driverCreator.getWebDriver();
		homePage = new HomePage(driver);
		homePage.startHomePage();
		
	}
	
	@AfterMethod
	public void afterMethod(ITestResult testResult) throws Exception {
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
				testResult.getTestContext().getCurrentXmlTest().getSuite().getFileName(), getALMAnnotation());
		resultAdapter.setActualvalue(errorMessage);
		resultAdapter.setValue(expectedResult);
		Reporter.allResults.add(resultAdapter);
		if (testResult.getStatus() == ITestResult.FAILURE && !testCaseStatus.equals("fail"))
		{
			testCaseStatus = "fail";
		}
		if ("".equals(testCaseName))
		{
			testCaseName = testResult.getTestName();
		}
	}
	
	@AfterClass 
	public void afterClass() throws IOException {
		exportALMReferenceCsv(testCaseName, testCaseStatus);
		driver.quit();
	}
	private void exportALMReferenceCsv(String tcName, String status) {
		String almId = getALMAnnotation();
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(ALMcsvfile, true));
			String line = almId + "," + tcName + ","+ status;
			bw.write(line);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally { // always close the file
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {

				}
			}
		}
	}
	private String getALMAnnotation() {
		String ALMid = "";
		for (Annotation a : this.getClass().getAnnotations()) {
			if ((a instanceof ALM)) {
				ALM alm = (ALM) a;
				ALMid = alm.id();
			}
		}
		return ALMid;
	}
}

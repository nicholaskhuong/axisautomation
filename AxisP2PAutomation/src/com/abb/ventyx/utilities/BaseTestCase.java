package com.abb.ventyx.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.ss.formula.functions.Today;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
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
	public static final String TEST_SERVER_URL = "test.server.url";
	public static final String TEST_BROWSER = "test.browser";
	public static final String TEST_SELENIUM_SERVER = "test.selenium.server";
	public static final String TEST_SELENIUM_PORT = "test.selenium.port";
	public static final String TEST_REPORT_DIR = "test.report.directory";
	private static Properties properties = new Properties();	
	private TestLoginCredentials defaultCredentials;
	private TestLoginCredentials currentCredentials;
	public Log4JLogger newLog;
	public BaseTestCase() {
		DOMConfigurator.configure("log4j.xml");
		try {
			// load default properties
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("test.properties", BaseTestCase.class));
		} catch (Exception e) {
			newLog.debug(e.getMessage());
			throw new RuntimeException("Unable to load default test.properties");
		}
		try {
			// override with any local test.properties
			PropertiesLoaderUtils.fillProperties(properties, new FileSystemResource("test.properties"));
		} catch (Exception e) {
			newLog.debug(e.getMessage());
		}
		properties.putAll(System.getProperties());
		
		defaultCredentials = new TestLoginCredentials( getProperty("test.username"),getProperty("test.password"));
		currentCredentials = defaultCredentials;
		
	}
	@BeforeClass
	public void beforeClass() throws Exception {
		this.expectedResult = "";
		DriverCreator driverCreator = new DriverCreator(BaseTestCase.getProperties().getProperty("test.browser"));
		driver = driverCreator.getWebDriver();
//		homePage = new HomePage(driver);
//		homePage.startHomePage();
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
	
	public String getServerURL() {
		return getProperty(TEST_SERVER_URL);
	}

	public String getProperty(String key) {
		return (String) properties.get(key);
	}
	
    public static Properties getProperties() {
        return BaseTestCase.properties;
    }
    
	public TestLoginCredentials getCurrentCredentials() {
		
		return currentCredentials;
	}
	
}

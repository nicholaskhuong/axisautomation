package com.abb.ventyx.utilities;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.BufferedWriter;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pages.HomePage;
import com.abb.ventyx.axis.objects.pages.LoginPage;
import com.abb.ventyx.utilities.report.TestMethodResultAdapter;

public class BaseTestCase{
	
	public WebDriver driver;
	public HomePage homePage;
	public String expectedResult = "";
	private String testCaseStatus = "pass";
	private static String testCaseName = "";
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
	private String testDataFileName = "";
	private static int startRow = 1;
	private static int endRow = 1;
	private static int currentRow = 1;
	protected HashMap<String, String> data = new HashMap<String, String>();
	
	
	public BaseTestCase() {
		DOMConfigurator.configure("log4j.xml");
		try {
			// load default properties
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("test.properties", BaseTestCase.class));
		} catch (Exception e) {
//			System.out.println(e.getMessage());			
		}
		try {
			// override with any local test.properties
			PropertiesLoaderUtils.fillProperties(properties, new FileSystemResource("test.properties"));
		} catch (Exception e) {
//			System.out.println(e.getMessage());
		}
		properties.putAll(System.getProperties());
		defaultCredentials = new TestLoginCredentials( getProperty("test.username"),getProperty("test.password"));
		currentCredentials = defaultCredentials;
		getTestDataDriven();
	}
	@BeforeClass
	public void beforeClass() throws Exception {
		
		try {
	
		currentCredentials = getClassCredentials();
		if (null == currentCredentials)
		{
			currentCredentials=defaultCredentials;
		}
		this.expectedResult = "";
		DriverCreator driverCreator = new DriverCreator(BaseTestCase.getProperties().getProperty("test.browser"));
		driver = driverCreator.getWebDriver();
		LoginPage login = new LoginPage(driver);
		login.login(getServerURL()+"/SupplierPortal/#!dashboard", currentCredentials);
		driver.manage().window().maximize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@AfterMethod
	public void afterMethod(ITestResult testResult) throws Exception {
		String screenShotPath = "";
		String takingTime = "";
		String tempPath="screenshots/%s_%s_%s.png"; 
		if (testResult.getStatus() == ITestResult.FAILURE || Boolean.valueOf(BaseTestCase.getProperties().getProperty("test.developer.mode"))) {
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			takingTime = String.format("%s_%s",testResult.getEndMillis(),new Random().nextInt(99999));
			screenShotPath = String.format(tempPath, testResult.getInstanceName(),testResult.getName(),takingTime);
			FileUtils.copyFile(scrFile, new File(Constants.REPORT_FOLDER + screenShotPath));
			org.testng.Reporter.setCurrentTestResult(testResult);
			org.testng.Reporter.setCurrentTestResult(null);
		}
		TestMethodResultAdapter resultAdapter = new TestMethodResultAdapter(testResult,screenShotPath,
				testResult.getTestContext().getCurrentXmlTest().getSuite().getFileName(), getALMAnnotation());
		resultAdapter.setValue(testResult.getName());
		Reporter.allResults.add(resultAdapter);
				
		// Save to disk
		
		Serializion serializer = new Serializion();
		serializer.saveToDisk(resultAdapter);
		
		// End
		
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
			String line = String.format("%s,%s,%s", almId,tcName,status);
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
					System.out.println(ioe2.getMessage());
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

	public static String getProperty(String key) {
		return (String) properties.get(key);
	}
	
    public static Properties getProperties() {
        return BaseTestCase.properties;
    }
    
	public TestLoginCredentials getCurrentCredentials() {
		
		return currentCredentials;
	}
	protected TestLoginCredentials getClassCredentials() {
		for ( Annotation a : this.getClass().getAnnotations()) {
			if ( a instanceof Credentials ) {
				Credentials c = (Credentials) a;
				return new TestLoginCredentials(c.user(), c.password());
			}
		}
		return null;
	}
	
	protected void getTestDataDriven() {
		for ( Annotation a : this.getClass().getAnnotations()) {
			if ( a instanceof TestData ) {
				TestData t = (TestData) a;
				testDataFileName = t.fileName();
				startRow = t.startRow();
				endRow = t.endRow();
			}
		}
	}
	
	/**
	 * This data provider reads all lines from the CSV associated with the current test. Current test will be whatever has extended AbstractGuicedTestNGTest.
	 * Returns null if not found CSV file.
	 * @throws IOException 
	 */
	@DataProvider(name = "CSVDataProvider")
	public Object[][] CSVDataProvider() throws IOException {
		String[] keys = null;
		Vector<String[]> values = new Vector<String[]>();
		String[] line; // current line

		/* Get CSV file from the class path */
		String expectedCSVFileName = Constants.TESTDATA_FOLDER + testDataFileName;
		InputStream cSVStream = null;
		try {
			cSVStream = new FileInputStream(expectedCSVFileName);
		} catch (Exception e) {
			System.out.println("File name: "+expectedCSVFileName);
			e.printStackTrace();
			return null;
		}
		if (startRow > endRow){
			System.out.println("Start Row cant be greater than End Row  name: "+startRow +" > " + endRow);
			cSVStream.close();
			return null;
		}
		/* Some test cases do not require the test data -> don't have CSV file */
		if (cSVStream != null) {
			InputStreamReader cSVStreamReader = new InputStreamReader(cSVStream);
			try {
				CSVReader csvReader = new CSVReader(cSVStreamReader);
				// we only care about the first lines
				keys = csvReader.readNext();
				int row =1;
				while ((line = csvReader.readNext()) != null) {
						// number of values should be consistent
					if	(row>=startRow && row<=endRow)
					{
						if (line.length == keys.length) {
							values.add(line);
						} else {
							return null;
						}
					}
						row++;
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
		}
		if ( null == keys || (null == values || values.isEmpty())) {
			return null;
		}
		return new Object[][] {
				{ keys, values }
		};
	}
	
	@DataProvider(name="empLogin")
	public Object[][] loginData() {
		Object[][] arrayObject = getExcelData( Constants.TESTDATA_FOLDER + testDataFileName,"Data");//Constants.TESTDATA_FOLDER + testDataFileName;
		return arrayObject;
	}
	/**
	 * @param File Name
	 * @param Sheet Name
	 * @return
	 */
	public String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			
			for (int i= 1 ; i < totalNoOfRows; i++) {

				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}
}

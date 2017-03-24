package com.abb.ventyx.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import com.abb.ventyx.utilities.report.Error;
import com.abb.ventyx.utilities.report.ReportData;
import com.abb.ventyx.utilities.report.TestCase;
import com.abb.ventyx.utilities.report.TestClassResultAdapter;
import com.abb.ventyx.utilities.report.TestMethodResultAdapter;
import com.abb.ventyx.utilities.report.TestPackageResultAdapter;
import com.abb.ventyx.utilities.report.TestStep;
import com.abb.ventyx.utilities.report.TestSuite;
import com.abb.ventyx.utilities.report.TestSuiteResultAdapter;
import com.google.common.io.Files;
import com.google.gson.Gson;

public class Reporter implements IReporter {
	// public static int tempCount = 0;
	public static ArrayList<TestMethodResultAdapter> allResults = new ArrayList<>();
	public static ArrayList<TestClassResultAdapter> allClasses = new ArrayList<>();
	public static ArrayList<TestSuiteResultAdapter> allSuites = new ArrayList<>();
	public static ArrayList<TestPackageResultAdapter> allPackages = new ArrayList<>();

	public ReportData theFinalReportData = new ReportData();

	public String reportFolder = Constants.REPORT_FOLDER;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		// System.out.println("TK COUNT = " + tempCount);
		// Iterating over each suite included in the test
		// System.out.println(new Gson().toJson(allResults));

		//System.out.println("TK ALL RESULT = " + new Gson().toJson(allResults));
//		List<ITestResult> allTestResults;
		
		allResults = new ArrayList<>();
		Serializion serializer = new Serializion();
		allResults = serializer.getAllTestResult();

		createTestPackageAdapter();

		createTestSuitesAdapter();
		createTestClassesAdapter();
		assignTestMethodResult();

		//System.out.println(new Gson().toJson(allPackages));

		

		generateTheFinalReport();

//		for (ISuite suite : suites) {
			// Following code gets the suite name
//			String suiteName = suite.getName();

			// Suite method name
//			List<ITestNGMethod> suiteTestMethods = suite.getAllMethods();

//			for (ITestNGMethod mt : suiteTestMethods) {
//				 System.out.println(mt.getMethodName());
//			}

			// Getting the results for the said suite
//			Map<String, ISuiteResult> suiteResults = suite.getResults();
//			for (ISuiteResult sr : suiteResults.values()) {

//				ITestContext tc = sr.getTestContext();
				// //System.out.println("Object = " + gson.toJson(sr));
				// System.out.println(
				// "Passed tests for suite '" + suiteName + "' is:" +
				// tc.getPassedTests().getAllResults().size());
				// System.out.println(
				// "Failed tests for suite '" + suiteName + "' is:" +
				// tc.getFailedTests().getAllResults().size());
				// System.out.println("Skipped tests for suite '" + suiteName +
				// "' is:"
				// + tc.getSkippedTests().getAllResults().size());

				// Process passed results first

				// IResultMap passMap = tc.getPassedTests();
				// List<ITestResult> lsPassResults = new
				// ArrayList<>(passMap.getAllResults());
				//
				// IResultMap failMap = tc.getFailedTests();
				// List<ITestResult> lsFailResults = new
				// ArrayList<>(failMap.getAllResults());
				//
				// IResultMap skipMap = tc.getSkippedTests();
				// List<ITestResult> lsSkipResults = new
				// ArrayList<>(skipMap.getAllResults());
				//
				// allTestResults = new ArrayList<>(lsPassResults);
				// allTestResults.addAll(lsFailResults);
				// allTestResults.addAll(lsSkipResults);
				//
				// for (ITestResult resultk : allTestResults) {
				// TestResultAdapter adapter = new TestResultAdapter(resultk);
				// System.out.println(new Gson().toJson(new TestStep(adapter)));
				// }

//			}
//		}
	}

	private void assignTestMethodResult() {
		for (TestPackageResultAdapter pack : allPackages) {
			for (TestSuiteResultAdapter suite : pack.getSuites()) {
				for (TestClassResultAdapter classs : suite.getClasses()) {
					int methodID = 1;
					for (TestMethodResultAdapter result : allResults) {
						if (result.getTestSuite().equals(suite.getSuiteName())
								&& result.getTestName().equals(classs.getTestClassName())) {
							result.setId(String.format("%s%s%s", classs.getId(),"sp", methodID));
							result.setText(String.format("%s%s%s", methodID,"-", result.getTestClass()));
							result.setAction(result.getMethodName());
							classs.addMethod(result);
							methodID++;
						}
					}
				}
			}
		}

	}

	private void createTestClassesAdapter() {
		for (TestPackageResultAdapter pack : allPackages) {
			for (TestSuiteResultAdapter suite : pack.getSuites()) {
				int classID = 0;
				for (TestMethodResultAdapter result : allResults) {
					if (result.getTestSuite().equals(suite.getSuiteName())
							&& !isClassExists(suite, result.getTestName())) {
						TestClassResultAdapter classs = new TestClassResultAdapter();
						classs.setId(String.format("%s%s%s",suite.getId(),"tc",classID));
						classs.setTestClassName(result.getTestName());
						classs.setALMID(result.getALMID());
						suite.addClass(classs);
						classID++;
					}
				}
			}
		}

	}

	private void createTestSuitesAdapter() {
		for (TestPackageResultAdapter pack : allPackages) {
			int suiteID = 0;

			for (TestMethodResultAdapter result : allResults) {
				if (!isSuiteExists(pack, result.getTestSuite())
						&& result.getPackageName().equals(pack.getPackageName())) {
					TestSuiteResultAdapter tempSuite = new TestSuiteResultAdapter();
					tempSuite.setId(String.format("%s%s%s",pack.getId(),"ts",suiteID));
					tempSuite.setSuiteName(result.getTestSuite());
					pack.addSuite(tempSuite);
					suiteID++;
				}
			}
		}

	}

	private void createTestPackageAdapter() {
		int packID = 0;
		for (TestMethodResultAdapter result : allResults) {
			if (!isPackageExists(result.getPackageName())) {
				TestPackageResultAdapter packAdap = new TestPackageResultAdapter();
				packAdap.setId("st" + packID);
				packAdap.setPackageName(result.getPackageName());
				allPackages.add(packAdap);
				packID++;
			}
		}
	}

	private boolean isPackageExists(String packageName) {
		boolean result = false;
		for (TestPackageResultAdapter pack : allPackages) {
			if (pack.getPackageName().equals(packageName)) {
				result = true;
				break;

			}
		}

		return result;
	}

	private boolean isSuiteExists(TestPackageResultAdapter pack, String suiteName) {
		boolean result = false;
		for (TestSuiteResultAdapter suite : pack.getSuites()) {
			if (suite.getSuiteName().equals(suiteName)) {
				result = true;
				break;

			}
		}

		return result;
	}

	private boolean isClassExists(TestSuiteResultAdapter suiteResult, String testName) {
		boolean result = false;
		for (TestClassResultAdapter classs : suiteResult.getClasses()) {
			if (classs.getTestClassName().equals(testName)) {
				result = true;
				break;

			}
		}

		return result;
	}

	private void generateTheFinalReport() {
		long totalReportTime = 0;
		int totalReportPass = 0;
		int totalReportFail = 0;
		ArrayList<Package> lsPackages = new ArrayList<>();

		for (TestPackageResultAdapter packageResultAdapter : allPackages) {
			ArrayList<TestSuite> lsSuite = new ArrayList<>();
			boolean packageStatus = true;
			int totalTestSuitePass = 0;
			int totalTestSuiteFail = 0;
			long totalPackageTime = 0;
			int totalTestSuite =0;
			Package packageS = new Package();
			packageS.setText(packageResultAdapter.getPackageName());
			packageS.setId(packageResultAdapter.getId());
			// Convert TestSuite object to TestSuite Report object then add to
			// package
			for (TestSuiteResultAdapter suite : packageResultAdapter.getSuites()) {
				TestSuite testSuite = new TestSuite();
				testSuite.setId(suite.getId());
				testSuite.setText(suite.getSuiteName());
				testSuite.setSteps("0");
				testSuite.setTotal(suite.getClasses().size() + "");
				testSuite.setFail("0");
				testSuite.setPass("0");
				testSuite.setTime("0");
				testSuite.setStatus("0"); // passed or failed
				testSuite.setTimestamp("2016-03-11T02:25:40");
				// Test case of suite
				int totalTCPass = 0;
				int totalTCFail = 0;
				long totalTimeTestSuite = 0;
				int totalTestStuiteSteps = 0;

				ArrayList<TestCase> lsTCs = new ArrayList<>();
				for (TestClassResultAdapter classs : suite.getClasses()) {
					TestCase TC = new TestCase();
					TC.setId(classs.getId());
					TC.setText(String.format("%s - %s", classs.getALMID(), classs.getTestClassName()));
					TC.setStatus("0");
					TC.setTimestamp("2016-03-11T02:25:40");
					TC.setTime("68.0");
					TC.setTotal("0");
					TC.setPass("0");
					TC.setFail("0");
					TC.setRpt("1");
					Error er = new Error();
					TC.setError(er);
					TC.setOutput("");
					// Test step (each data row)
					int totalStepPass = 0;
					int totalStepFail = 0;
					long totalTimeTestCase = 0;
					ArrayList<TestStep> lsSteps = new ArrayList<>();
					for (TestMethodResultAdapter methodResultAdapter : classs.getMethods()) {
						TestStep step = new TestStep();
						step.setId(methodResultAdapter.getId());
						step.setText(methodResultAdapter.getText());
						step.setLabel(methodResultAdapter.getLabel());
						step.setValue(methodResultAdapter.getValue());
						step.setActualvalue(methodResultAdapter.getActualvalue());
						step.setAction(methodResultAdapter.getAction());
						step.setStatus(methodResultAdapter.getStatus());
						if (methodResultAdapter.getScreenshot().trim().isEmpty() || "".equals(methodResultAdapter.getScreenshot().trim()))
						{
							step.setScreenShot("");
						}
						else{
							step.setScreenShot(methodResultAdapter.getScreenshot());
						}
						lsSteps.add(step);
						totalTestStuiteSteps++;
						if (step.getStatus().equals("0")) {
							totalStepFail++;
							if (packageStatus == true) {
								packageStatus = false;
							}
						} else {
							totalStepPass++;
						}
						long timeStep = methodResultAdapter.getEndTime() - methodResultAdapter.getStartTime();
						totalTimeTestCase = totalTimeTestCase + timeStep;
					}
					float totalTimeAsSecond = totalTimeTestCase / 1000;
					TC.setTeststep(lsSteps);
					TC.setFail(String.valueOf(totalStepFail));
					TC.setPass(String.valueOf(totalStepPass));
					TC.setTotal(String.valueOf((totalStepPass + totalStepFail)));
					TC.setTime(String.valueOf(totalTimeAsSecond));
					// Set time stamp
					TC.setTimestamp(DateTimeConverter.milliSecToTimeStamp(classs.getMethods().get(0).getStartTime()));
					if (totalStepFail == 0) {
						totalTCPass++;
						totalReportPass++;
						totalTestSuitePass++;
						TC.setStatus("1");
					} else {
						totalTCFail++;
						totalReportFail++;
						totalTestSuiteFail++;
					}
					lsTCs.add(TC);
					totalTimeTestSuite = totalTimeTestSuite + totalTimeTestCase;
				}
				testSuite.setTestcase(lsTCs);
				testSuite.setPass(String.valueOf(totalTCPass));
				testSuite.setFail(String.valueOf(totalTCFail));
				testSuite.setTotal(String.valueOf((totalTCFail + totalTCPass)));
				testSuite.setTime(String.valueOf(((float) (totalTimeTestSuite / 1000))));
				testSuite.setTimestamp(lsTCs.get(0).getTimestamp());
				testSuite.setSteps(String.valueOf(totalTestStuiteSteps));
				testSuite.setStatus(totalTCFail == 0 ? "1" : "0");
				lsSuite.add(testSuite);

				totalTestSuite++;
				
				totalPackageTime = totalPackageTime + totalTimeTestSuite;

			}

			packageS.setTestsuite(lsSuite);
			packageS.setStatus(packageStatus ? "1" : "0");
			packageS.setTotal(String.valueOf((totalTestSuiteFail + totalTestSuitePass)));
			packageS.setPass(String.valueOf(totalTestSuitePass));
			packageS.setFail(String.valueOf(totalTestSuiteFail));
			packageS.setTimestamp(lsSuite.get(0).getTimestamp());
			packageS.setGtime(String.valueOf(((float) (totalPackageTime / 1000))));
			packageS.setTime(String.valueOf(((float) (totalPackageTime / 1000))));

			lsPackages.add(packageS);
			
			totalReportTime = totalReportTime + totalPackageTime;
		}

		theFinalReportData.setPackage_xyz(lsPackages);
		theFinalReportData.setTime(String.valueOf(((float) (totalReportTime / 1000))));
		try{
		theFinalReportData.setAutomation_url(System.getProperty("test.server.url"));
		theFinalReportData.setSeleniumHubURL(System.getProperty("test.selenium.server").replace("http://", ""));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			theFinalReportData.setAutomation_url("");
			theFinalReportData.setSeleniumHubURL("");
		}
		try{
			theFinalReportData.setTimestamp(lsPackages.get(0).getTimestamp());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		theFinalReportData.setTotal(String.valueOf((totalReportPass + totalReportFail)));
		theFinalReportData.setPass(String.valueOf(totalReportPass));
		theFinalReportData.setFail(String.valueOf(totalReportFail ));
		theFinalReportData.setStatus(totalReportFail == 0 ? "1" : "0");
		theFinalReportData.setGtime(theFinalReportData.getTime());

		// System.out.println(new Gson().toJson(theFinalReportData));
		

		String reportDataAsJSON = new Gson().toJson(theFinalReportData);
		
		//System.out.println("TK DEBUG DATA = " + reportDataAsJSON);

		reportDataAsJSON = reportDataAsJSON.replace("package_xyz", "package");

		reportDataAsJSON = "var reportData = " + reportDataAsJSON + ";";

		// SAVE as DATA

		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(reportFolder + "data/reportdata.js", false));
			out.println(reportDataAsJSON);
			out.close();
			// saveOverViewToPDF();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Done!!!!!");
	}
	
}


package utility;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class Listeners extends TestListenerAdapter {

	private String getMethodContext(ITestResult result) {
		// String browser = result.getTestContext().getCurrentXmlTest()
		// .getParameter("browser");
		String testClasss = result.getTestClass().getName();
		String name = result.getName();
		String rs = testClasss + " | " + name;
		return rs;
	}

	public void onTestFailure(ITestResult result) {
		String rs = getMethodContext(result);
		System.err.println(rs + " | FAILED");
		Reporter.log(rs + " | FAILED");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String rs = getMethodContext(result);
		System.out.println(rs + " | SKIPPED");
		Reporter.log(rs + " | SKIPPED");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String rs = getMethodContext(result);
		System.out.println(rs + " | PASSED");
		Reporter.log(rs + " | PASSED");
	}
}

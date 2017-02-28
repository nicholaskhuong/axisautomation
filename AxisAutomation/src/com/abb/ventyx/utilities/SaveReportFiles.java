package com.abb.ventyx.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import com.abb.ventyx.utilities.report.TestMethodResultAdapter;

public class SaveReportFiles implements IReporter {
	// public static int tempCount = 0;
	public static ArrayList<TestMethodResultAdapter> allResults = new ArrayList<>();

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {

		// create json report folder
		File reportJSONfolder = new File(Constants.REPORT_FOLDER
				+ "json_reports\\");
		if (!reportJSONfolder.exists()) {
			reportJSONfolder.mkdir();
		}

	}

}

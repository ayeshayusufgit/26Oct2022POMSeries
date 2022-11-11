package com.democart.qa.selenium.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.democart.qa.selenium.factory.DriverFactory;

public class ExtentReportListener extends DriverFactory implements ITestListener {

	// ExtentReport Listener internally using TestNG listener
	// ER uses a template in a form of .java file
	// The ER listener keeps listening/monitoring the execution of the testcases
	// in the end generates a html report
	// ExtentReportListener implements the ITestListener

	//The html report will be generated based on our config
	
	private static final String OUTPUT_FOLDER = "./build/";
	private static final String FILE_NAME = "TestExecutionReport.html";

	private static ExtentReports extent = init();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	private static ExtentReports init() {
		// intializes the ER Listener and set all the paths
		// (does basic configuration)

		Path path = Paths.get(OUTPUT_FOLDER);
		// if directory exists?
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				// fail to create directory
				e.printStackTrace();
			}
		}
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		// To set the document title
		htmlReporter.config().setDocumentTitle("Automation Test Results");
		// To set the report name
		htmlReporter.config().setReportName("Automation Test Results");
		// To set the chart location
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		// To set the theme
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);

		return extent;
	}

	// Nothin to be done when the testsuite is started just print the below message
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite started!");
	}

	// Nothin to be done when the testsuite is ending just print the below message
	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Test Suite is ending!"));
		extent.flush();
		test.remove();
	}

	// When the testcase is started then the below details have to be captured and
	// below message has to get printed, no SS required
	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid + 1, last);

		System.out.println(methodName + " started!");
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());

		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		/*
		 * methodName = StringUtils.capitalize(StringUtils.join(StringUtils.
		 * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
		 */
		extentTest.assignCategory(className);
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
	}

	// When the testcase is a success then below message has to get printed, no SS
	// required
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed");
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	// When the testcase is failed then below message has to get printed, SS
	// required(using selenium getScreenShot() in The DriverFactory class
	// init_driver() method)
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		try {
			test.get().fail(result.getThrowable(),
					MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot()).build());
			// The ss is taken an a path is returned which is attached in the report in case
			// of failures
		} catch (IOException e) {
			System.err
					.println("Exception thrown while updating test fail status " + Arrays.toString(e.getStackTrace()));
		}
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	// When the testcase is skipped then below message has to get printed, SS
	// required(using selenium getScreenShot() in The DriverFactory class
	// init_driver() method)
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		try {
			test.get().skip(result.getThrowable(),
					MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot()).build());
			// The ss is taken an a path is returned which is attached in the report in case
			// of failures
		} catch (IOException e) {
			System.err
					.println("Exception thrown while updating test skip status " + Arrays.toString(e.getStackTrace()));
		}
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}

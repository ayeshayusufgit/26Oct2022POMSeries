package com.democart.qa.selenium.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.democart.qa.selenium.factory.DriverFactory;
import com.tesults.tesults.*;

public class TesultReportListener implements ITestListener {
	
	DriverFactory df=new DriverFactory();
	List<Map<String, Object>> testCases = new ArrayList<Map<String, Object>>();

	public static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	public static Object[] getTestParams(ITestResult iTestResult) {
		return iTestResult.getParameters();
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("I am in onTestStart method " + getTestMethodName(result) + " start");

	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		Map<String, Object> testCase = new HashMap<String, Object>();
		testCase.put("name", getTestMethodName(iTestResult)); //these are the parameters which are needed to be passed on success to tesult 
		testCase.put("suite", "TesultsExample"); //these
		testCase.put("result", "pass"); //these
		testCase.put("params", getTestParams(iTestResult)); //these
		testCases.add(testCase);
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		
		Map<String, Object> testCase = new HashMap<String, Object>();
		testCase.put("name", getTestMethodName(iTestResult));
		testCase.put("suite", "TesultsExample");//standard parameters needs to be passed in tesults
		testCase.put("result", "fail");//standard parameters needs to be passed in tesults
		testCase.put("params", getTestParams(iTestResult));//standard needs to be passed in tesults
		
		List<String> files = new ArrayList<String>();
		files.add(df.getScreenShot());
		testCase.put("files", files);
		testCases.add(testCase);
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		Map<String, Object> testCase = new HashMap<String, Object>();
		testCase.put("name", getTestMethodName(iTestResult));
		testCase.put("suite", "TesultsExample");
		testCase.put("result", "fail");
		testCase.put("params", getTestParams(iTestResult));
		List<String> files = new ArrayList<String>();
		files.add(df.getScreenShot());
		testCase.put("files", files);

		testCases.add(testCase);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		// Map<String, Object> to hold your test results data.
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("target",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ImYwOGJiY2ViLTZlM2EtNDZmYi05MjA1LTIyM2Y3YWExNjQwYS0xNjczMjcyNjU0MzEzIiwiZXhwIjo0MTAyNDQ0ODAwMDAwLCJ2ZXIiOiIwIiwic2VzIjoiOTU2ZDE5MDctYmQ5Yi00NWRlLWIxYjktZjM0YjBjODE2YTVmIiwidHlwZSI6InQifQ.HZ8AKnYLlcZUXONuBLFovNGZ4NlpeXANGKKkuR5u8xc");

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("cases", testCases);
		data.put("results", results);

		// Upload
		Map<String, Object> response = Results.upload(data);
		System.out.println("success: " + response.get("success"));
		System.out.println("message: " + response.get("message"));
		System.out.println("warnings: " + ((List<String>) response.get("warnings")).size());
		System.out.println("errors: " + ((List<String>) response.get("errors")).size());
	}

}

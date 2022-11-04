package com.democart.qa.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.democart.qa.selenium.base.BaseTest;
import com.democart.qa.selenium.utils.Constants;
import com.democart.qa.selenium.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC 300: Design Login Page for Open Cart Application")
@Story("US 301: Login Page feature story")
public class LoginPageTest extends BaseTest {

	@Description("Test to verify the page title")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void verifyLoginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		System.out.println("Login Page title is=" + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Test to verify the Forgot Password link")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2)
	public void verifyForgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPasswordLinkPresent());
	}

	//@Test(priority = 3,dataProvider ="getLoginNegativeTestData")
	//Checking the actual error message with the negative cases
	public void loginNegativeTest1(String username, String password, String errorMessage) {
		String actualErrorMessage=loginPage.doLoginNegative1(username, password);
		Assert.assertEquals(actualErrorMessage, errorMessage);
	}
	
	//Checking the presence of the actual error message div with the negative login cases
	
	@Description("Test to verify the -ve cases in login using dataprovider")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 3,dataProvider ="getLoginNegativeTestData")
	public void loginNegativeTest2(String username, String password, String errorMessage) {
		Assert.assertTrue(loginPage.doLoginNegative2(username, password));
	}
	

	@DataProvider
	public Object[][] getLoginNegativeTestData() {
		return ExcelUtil.getData(Constants.LOGIN_SHEET_NAME);
	}

	@Description("Test to verify the login")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4,dependsOnMethods = "loginNegativeTest2")
	public void loginTest() {
		accountsPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accountsPage.getAccountSectionsList(), Constants.getExpectedAccountsSectionList());
	}

}

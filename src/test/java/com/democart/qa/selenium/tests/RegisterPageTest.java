package com.democart.qa.selenium.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.democart.qa.selenium.base.BaseTest;
import com.democart.qa.selenium.utils.Constants;
import com.democart.qa.selenium.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void registerPageSetUp() {
		registrationPage = loginPage.navigateToRegisterationPage();
	}

	// @Test(dataProvider = "getRegisterationData")
	public void userRegistrationTest(String firstName, String lastName, String email, String phone, String password,
			String subscribe) {
		registrationPage.accountRegistration(firstName, lastName, email, phone, password, subscribe);
	}

	// @DataProvider
	public Object[][] getRegisterationData() {
		return ExcelUtil.getData(Constants.REGISTER_SHEET_NAME);
	}
}

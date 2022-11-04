package com.democart.qa.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.democart.qa.selenium.base.BaseTest;
import com.democart.qa.selenium.utils.Constants;

public class ForgotPasswordPageTest extends BaseTest {
	
	@Test(priority = 1)
	public void forgotPasswordValidEmailTest() {
		loginPage.clickForgotPassword();
		forgotPasswordPage.resetPassword(Constants.FORGOTTEN_PWD_VALID_EMAIL);
		Assert.assertEquals(loginPage.getAlertSuccessMessage(),Constants.LOGIN_PAGE_SUCCESS_MESSAGE);
	}
	
	
	@Test(priority = 2)
	public void forgotPasswordInvalidEmailTest() {
		loginPage.clickForgotPassword();
		forgotPasswordPage.resetPassword(Constants.FORGOTTEN_PWD_INVALID_EMAIL);
		Assert.assertEquals(forgotPasswordPage.getAlertErrorMsg(),Constants.FORGOTTEN_PWD_ERROR_MESSAGE);
	}
}

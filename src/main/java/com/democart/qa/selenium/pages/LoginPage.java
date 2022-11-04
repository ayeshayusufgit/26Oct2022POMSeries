package com.democart.qa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.democart.qa.selenium.utils.Constants;
import com.democart.qa.selenium.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By emailId = By.id("input-email");
	private By passwordId = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPasswordLink = By.linkText("Forgotten Password");
	private By alertErrorMsg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	private By alertSuccessMsg = By.xpath("//div[@class='alert alert-success alert-dismissible']");
	private By registerLink = By.linkText("Register");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	
	@Step("Getting the Login Page Title")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitleToBe(Constants.LOGIN_PAGE_TITLE, 5);
	}

	@Step("Checking the presence of Forgot Password link in the Login Page")
	public boolean isForgotPasswordLinkPresent() {
		if (elementUtil.getElements(forgotPasswordLink).size() == Constants.FORGOTTEN_PWD_LINK_COUNT)
			return true;
		return false;
	}

	@Step("Returning the error message for invalid login")
	public String doLoginNegative1(String username, String password) {
		System.out.println("Login with  username:" + username + " and password:" + password);
		elementUtil.doSendKeys(emailId, username);
		elementUtil.doSendKeys(passwordId, password);
		elementUtil.doClick(loginButton);

		WebElement element = elementUtil.waitForElementPresent(alertErrorMsg, 10);
		if (element.isDisplayed()) {
			return element.getText().trim();
		}
		return null;
	}
	
	@Step("Returning boolean value for the presence of error message for invalid login")
	public boolean doLoginNegative2(String username, String password) {
		System.out.println("Login with  username:" + username + " and password:" + password);
		elementUtil.doSendKeys(emailId, username);
		elementUtil.doSendKeys(passwordId, password);
		elementUtil.doClick(loginButton);

		WebElement element = elementUtil.waitForElementPresent(alertErrorMsg, 10);
		if (element.isDisplayed()) {
			return true;
		}
		return false;
	}

	
	@Step("Login with username:{0} and password:{0}")
	public AccountsPage doLogin(String username, String password) {
		System.out.println("Login with  username:" + username + " and password:" + password);
		elementUtil.doSendKeys(emailId, username);
		elementUtil.doSendKeys(passwordId, password);
		elementUtil.doClick(loginButton);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new AccountsPage(driver);
	}

	@Step("Click on Forgot Password link")
	public void clickForgotPassword() {
		elementUtil.doClick(forgotPasswordLink);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Step("Returning message for valid Forgot Password email address")
	public String getAlertSuccessMessage() {
		elementUtil.waitForElementVisible(alertSuccessMsg, 5);
		return elementUtil.doGetText(alertSuccessMsg);
	}

	@Step("Navigate to the Registeration Page")
	public RegisterPage navigateToRegisterationPage() {
		elementUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}

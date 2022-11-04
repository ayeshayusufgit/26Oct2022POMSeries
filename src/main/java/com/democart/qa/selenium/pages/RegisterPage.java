package com.democart.qa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.democart.qa.selenium.utils.Constants;
import com.democart.qa.selenium.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id(("input-telephone"));
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[@class='radio-inline'][position()=1]/input");
	private By subscribeNo = By.xpath("//label[@class='radio-inline'][position()=2]/input");
	private By agreeCheckbox = By.name("agree");
	private By continueButton = By.xpath("//input[@value='Continue']");
	private By accountSuccessMessage = By.cssSelector("#content h1");
	private By logOutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	public boolean accountRegistration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {
		elementUtil.doSendKeys(this.firstName, firstName);
		elementUtil.doSendKeys(this.lastName, lastName);
		elementUtil.doSendKeys(this.email, email);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.password, password);
		elementUtil.doSendKeys(this.confirmPassword, password);

		if (subscribe.equals("Yes")) {
			elementUtil.doClick(subscribeYes);
		} else {
			elementUtil.doClick(subscribeNo);
		}

		elementUtil.doClick(agreeCheckbox);
		elementUtil.doClick(continueButton);
		String message=elementUtil.waitForElementVisible(accountSuccessMessage, 10).getText();
		
		if (message.equals(Constants.ACCOUNT_CREATION_SUCCESS_MESSAGE)) {
			//elementUtil.doClick(continueButton);
			elementUtil.doClick(logOutLink);
			elementUtil.doClick(registerLink);
			return true;
		}
		return false;

	}

}

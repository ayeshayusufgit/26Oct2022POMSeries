package com.democart.qa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.democart.qa.selenium.utils.ElementUtil;

public class ForgotPasswordPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By emailTextbox = By.id("input-email");
	private By continueBtn = By.xpath("//input[@value='Continue']");
	private By invalidEmailErrorMsg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");

	public ForgotPasswordPage(WebDriver driver) {
		this.driver=driver;
		elementUtil = new ElementUtil(driver);
	}

	public void resetPassword(String email) {
		elementUtil.doClick(emailTextbox);
		elementUtil.doSendKeys(emailTextbox, email);
		elementUtil.doActionsClick(continueBtn);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAlertErrorMsg() {
		elementUtil.waitForElementVisible(invalidEmailErrorMsg, 5);
		return elementUtil.doGetText(invalidEmailErrorMsg);
	}
}

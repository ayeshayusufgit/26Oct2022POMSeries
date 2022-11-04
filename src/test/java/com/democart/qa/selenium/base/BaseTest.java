package com.democart.qa.selenium.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.democart.qa.selenium.factory.DriverFactory;
import com.democart.qa.selenium.pages.AccountsPage;
import com.democart.qa.selenium.pages.ForgotPasswordPage;
import com.democart.qa.selenium.pages.LoginPage;
import com.democart.qa.selenium.pages.RegisterPage;

public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	public Properties prop;
	public LoginPage loginPage;
	public ForgotPasswordPage forgotPasswordPage;
	public AccountsPage accountsPage;
	public RegisterPage registrationPage;
	
	
	@BeforeTest
	public void setUp() {
		df=new DriverFactory();
		prop=df.init_prop();
		driver=df.init_driver(prop);
		//System.out.println(prop.getProperty("url"));
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		//driver.get(prop.getProperty("url"));
		loginPage=new LoginPage(driver);
		forgotPasswordPage=new ForgotPasswordPage(driver);
		accountsPage=new AccountsPage(driver);
		registrationPage=new RegisterPage(driver);
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}

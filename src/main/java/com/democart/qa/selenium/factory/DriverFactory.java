package com.democart.qa.selenium.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * 
 * 
 */
public class DriverFactory {
	public WebDriver driver;
	public Properties prop;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	// Every thread has the local copy of the WebDriver, which helps to generate
	// proper reports
	// ow test1 will be updated by test2 and the extent n allure reports will not be
	// proper
	// ThreadLocal has 2 methods, set() n get() which are being used

	/*
	 * This method will initialize the properties from config.properties file
	 * return @prop
	 */
	public Properties init_prop() {
		prop = null;
		try {
			FileInputStream fis = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(fis); // the key n value will be stored in the prop object

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	/*
	 * @param browserName
	 * 
	 * @returns WebDriver reference based on the browserName variable
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser");
		System.out.println("Browser Name:" + browserName);

		switch (browserName.trim()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("chrome");
			} else {
				// driver = new ChromeDriver();
				tlDriver.set(new ChromeDriver()); // The ThreadLocal is set to the WebDriver
				// The object of ChromeDriver() will be set to the ThreadLocal
			}
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox");
			} else {
				// driver = new FirefoxDriver();
				tlDriver.set(new FirefoxDriver());
			}
			break;

		case "safari":
			WebDriverManager.safaridriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("safari");
			} else {
				// driver = new SafariDriver();
				tlDriver.set(new SafariDriver());
			}
			break;

		default:
			System.out.println("Please enter a valid browser name:" + browserName);
			break;
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver(); // returns the ThreadLocal copy of the WebDriver
	}

	public void init_remoteDriver(String browserName) {
		if (browserName.equals("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			try {
				System.out.println(prop.getProperty("hubUrl"));
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), cap));
				System.out.println("done");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (browserName.equals("firefox")) {
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), cap));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * getDriver() using ThreadLocal
	 */
	public static synchronized WebDriver getDriver() {
		// every thread has a separate copy
		// synchronized keyword is used to prevent deadlock condition
		return tlDriver.get(); // =>gives 1 WebDriver using this function
	}

	/*
	 * This method will take the screenshot and will return the path of the
	 * screenshot
	 */
	public String getScreenShot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		// String path = "./screenshots/" + System.currentTimeMillis() + ".png";
		// String
		// path="C:\\Users\\ayesh\\automation\\26October2022POMSeries\\screenshots\\"+
		// System.currentTimeMillis() + ".png";
		System.out.println(path);
		File dest = new File(path);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return path;
	}

	public String getBase64Screenshot() throws IOException {
		String encodedBase64 = null;
		FileInputStream fileInputStream = null;
		TakesScreenshot screenshot = ((TakesScreenshot) getDriver());
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\FailedTestsScreenshots\\" + System.currentTimeMillis()
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);

		try {
			fileInputStream = new FileInputStream(finalDestination);
			byte[] bytes = new byte[(int) finalDestination.length()];
			fileInputStream.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return encodedBase64;
	}
}

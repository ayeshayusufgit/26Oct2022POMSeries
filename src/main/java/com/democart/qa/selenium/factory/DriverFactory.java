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
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * 
 * 
 */
public class DriverFactory {
	private static final Logger LOGGER=Logger.getLogger(DriverFactory.class);
	//This has to be used in every page class,test class,utilities class and DriverFactory class
	
	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	ProfileManager profileManager;
	

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
		LOGGER.info("Intializing Properties file......");
		
		prop = null;
		try {
			FileInputStream fis = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(fis); // the key n value will be stored in the prop object

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.error("FileNotFoundException is coming");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("IOException is coming");
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
		LOGGER.info("Browser Name is:"+browserName);
		
		highlight=prop.getProperty("highlight");
		profileManager=new ProfileManager(prop);
		
		switch (browserName.trim()) {
		case "chrome":
			LOGGER.info("Launching Chrome......");
			WebDriverManager.chromedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("chrome");
			} else {
				// driver = new ChromeDriver();
				tlDriver.set(new ChromeDriver(profileManager.getChromeOptions())); // The ThreadLocal is set to the WebDriver
				// The object of ChromeDriver() will be set to the ThreadLocal
			}
			break;

		case "firefox":
			LOGGER.info("Launching Firefox......");
			WebDriverManager.firefoxdriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox");
			} else {
				// driver = new FirefoxDriver();
				tlDriver.set(new FirefoxDriver(profileManager.getFirefoxOptions()));
			}
			break;

		case "safari":
			LOGGER.info("Launching Safari......");
			WebDriverManager.safaridriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("safari");
			} else {
				// driver = new SafariDriver();
				tlDriver.set(new SafariDriver());
			}
			break;

		default:
			LOGGER.info("Please pass the right browser:"+browserName);
			System.out.println("Please enter a valid browser name:" + browserName);
			break;
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver(); // returns the ThreadLocal copy of the WebDriver
	}

	public void init_remoteDriver(String browserName) {
		// on the bases of the capability the hub decides on which node the tcs have to
		// be executed
		if (browserName.equals("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			// Adding the capabilities for Zalenium
			// These values are by default
			// cap.setCapability("screenWidth","1920" );
			// cap.setCapability("screenHeight", "1080");
			// doesnt work by setting capabilities from here for Zalanium
			// cap.setCapability("screenWidth","1000" );
			// cap.setCapability("screenHeight", "500");

			// Adding the chrome capabilities for Selenoid
			cap.setCapability("browserName", "chrome");
			cap.setCapability("browserVersion", "91.0");
			cap.setCapability("enableVNC", true);//To visualize the testcases in selenoid
			cap.setCapability(ChromeOptions.CAPABILITY, profileManager.getChromeOptions());
			
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
			
			// Adding the firefox capabilities for Selenoid
			cap.setCapability("browserName", "firefox");
			cap.setCapability("browserVersion", "91.0");
			cap.setCapability("enableVNC", true);//To visualize the testcases in selenoid
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, profileManager.getFirefoxOptions());
			
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
		LOGGER.info("Taking Screenshot.....");
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

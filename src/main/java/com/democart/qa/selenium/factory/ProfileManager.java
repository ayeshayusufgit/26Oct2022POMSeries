package com.democart.qa.selenium.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class ProfileManager {
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;

	public ProfileManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (prop.getProperty("headless").equals("true")) {
			co.addArguments("--headless");
		}

		if (prop.getProperty("incognito").equals("true")) {
			co.addArguments("--incognito");
		}
		return co;
	}
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (prop.getProperty("headless").equals("true")) {
			fo.addArguments("--headless");
		}

		if (prop.getProperty("incognito").equals("true")) {
			fo.addArguments("--incognito");
		}
		return fo;
	}
}

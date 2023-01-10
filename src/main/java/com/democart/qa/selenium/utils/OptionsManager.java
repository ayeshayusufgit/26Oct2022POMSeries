package com.democart.qa.selenium.utils;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (prop.getProperty("headless").trim().equals("true")) {
			co.addArguments("--headless");
		}

		if (prop.getProperty("incognito").trim().equals("true")) {
			co.addArguments("--incognito");
		}
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (prop.getProperty("headless").trim().equals("true")) {
			fo.addArguments("--headless");
		}

		if (prop.getProperty("incognito").trim().equals("true")) {
			fo.addArguments("--incognito");
		}

		return fo;
	}

}

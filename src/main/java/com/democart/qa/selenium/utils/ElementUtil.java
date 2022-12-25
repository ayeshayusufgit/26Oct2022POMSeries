package com.democart.qa.selenium.utils;

import java.time.Duration;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.democart.qa.selenium.factory.DriverFactory;

public class ElementUtil {
	private static final Logger LOGGER=Logger.getLogger(ElementUtil.class);
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil=new JavaScriptUtil(this.driver);
	}

	public WebElement getElement(By locator) {
		LOGGER.info("Locator is:"+locator);
		WebElement element=driver.findElement(locator);
		LOGGER.info("Element is:"+element.toString());
		
		if(DriverFactory.highlight.equals("true")) {
			jsUtil.flash(element);
		}
		return element;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doSendKeys(By locator, String value) {
		WebElement element=getElement(locator);
		element.clear();
		element.sendKeys(value);
	}

	public void doActionsSendkeys(By locator, String value) {
		Actions action = new Actions(driver);
		// action.sendKeys(getElement(locator), value).perform();
		action.moveToElement(getElement(locator)).sendKeys(value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions action = new Actions(driver);
		// action.sendKeys(getElement(locator)).perform();
		action.moveToElement(getElement(locator)).click().build().perform();
	}

	public void doMoveToElement(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(locator)).perform();
	}

	public void clickOnSubMenu(By parentMenu, By firstLevelMenu) {
		// Actions action = new Actions(driver);
		doMoveToElement(parentMenu);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doActionsClick(firstLevelMenu);
	}

	public void clickOnSubMenu(By parentMenu, By firstSubMenu, By secondSubMenu) {
		// Actions action = new Actions(driver);
		doMoveToElement(parentMenu);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doMoveToElement(firstSubMenu);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doActionsClick(secondSubMenu);
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attribute) {
		return getElement(locator).getAttribute(attribute);
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean checkElementPresent(By locator) {

		if (getElements(locator).size() > 0) {
			return true;
		}
		return false;
	}

	public void clickElement(By locator, String value) {
		List<WebElement> elementList = getElements(locator);

		for (int i = 0; i < elementList.size(); i++) {
			WebElement curEle = elementList.get(i);
			String curText = curEle.getText();
			System.out.println(curText);

			if (curText.equals(value)) {
				curEle.click();
				break;
			}
		}
	}

	// ********************************Dropdown Utils******************************
	// Select class Dropdown utils

	public void doSelectByIndex(By locator, int index) {
		Select dropdown = new Select(getElement(locator));
		dropdown.selectByIndex(index);
	}

	public void doSelectByValue(By locator, String value) {
		Select dropdown = new Select(getElement(locator));
		dropdown.selectByValue(value);
	}

	public void doSelectByVisibleText(By locator, String value) {
		Select dropdown = new Select(getElement(locator));
		dropdown.selectByVisibleText(value);
	}

	public void getDropdownOptionsValue(By locator) {
		Select dropdown = new Select(getElement(locator));
		List<WebElement> list = dropdown.getOptions();
		// System.out.println("Total Options="+list.size());

		for (WebElement e : list) {
			String text = e.getText();
			System.out.println(text);
		}
	}

	public void selectDropDownValue(By locator, String value) {

		List<WebElement> optionsList = getElements(locator);
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();

			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	// ****************wait utils************************************

	/*
	 * An expectation for checking that all elements present on the web page that
	 * match the locatorare visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * 
	 * @param timeOut
	 */
	public List<WebElement> visiblityOfAllElements(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		List<WebElement> elementList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return elementList;
	}

	public void getPageElementText(By locator, int timeOut) {
		visiblityOfAllElements(locator, timeOut).stream().forEach(element -> System.out.println(element.getText()));
	}

	/*
	 * presenceElement=>An expectation for checking if the element is present or not
	 * in the html dom This doesnt not necessary mean that the element is visible
	 */

	public WebElement waitForElementPresent(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/*
	 * waitForElementVisible=>An expectation for checking that an element, known to
	 * be present on the DOM of a page, isvisible. Visibility means that the element
	 * is not only displayed but also has a height andwidth that is greater than 0.
	 * 
	 * @param locator
	 * 
	 * @param timeOut
	 * 
	 * @return
	 */

	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
	}

	public boolean waitForUrlToBe(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.urlContains(urlValue));
	}

	public String waitForTitleToBe(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
	}
	
	/**
	 * this methos is checking the presence of element using FluentWait
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */
	public WebElement waitForPresenceOfElementWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
								.pollingEvery(Duration.ofSeconds(pollingTime))
								.ignoring(NoSuchElementException.class)
								.ignoring(StaleElementReferenceException.class);
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
	}

	/*
	 * This method will check the page is completely loaded or not
	 * @param timeOut
	 */
	public void jsWaitForPageToLoad(int timeOut) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String state = "return document.readyState";
		if (jse.executeScript(state).toString().equals("complete")) {
			System.out.println("The page is completely loaded");
			return;
		}

		for (int i = 0; i < timeOut; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (jse.executeScript(state).equals("complete")) {
				System.out.println("The page is completely loaded");
				break;
			}
		}
	}

	/*
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * 
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptJSAlert(int timeOut) {
		waitForJSAlert(timeOut).accept();
	}

	public void dismissJSAlert(int timeOut) {
		waitForJSAlert(timeOut).dismiss();
	}

	public String getAlertText(int timeOut) {
		return waitForJSAlert(timeOut).getText();
	}
	
	public void imp() {
		System.out.println("Selenium Imp Method1...");
		System.out.println("Selenium Imp Method2...");
	}
}

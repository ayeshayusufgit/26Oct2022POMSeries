package com.democart.qa.selenium.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.democart.qa.selenium.utils.Constants;
import com.democart.qa.selenium.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage {
	//private WebDriver driver;
	private ElementUtil elementUtil;

	@FindBy(id="username")// a request is sent to the server as its a WebElement
	WebElement e1;	//as its equivalent to driver.findElement(By.id("username"));
	
	//WebElement e2=driver.findElement(By.id("username"));
	
	private By header = By.cssSelector("div#logo a");
	private By accountSectionHeaders = By.cssSelector("div#content h2");
	private By searchText = By.cssSelector("div#search input");
	private By searchButton = By.cssSelector("div#search button");
	private By searchItemsResult = By.cssSelector("div.product-layout .product-thumb");

	public AccountsPage(WebDriver driver) {
		//this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	@Step("Getting Accounts Page title")
	public String getAccountPageTitle() {
		return elementUtil.waitForTitleToBe(Constants.ACCOUNTS_PAGE_TITLE, 5);
	}

	@Step("Getting Accounts Page Header value")
	public String getHeaderValue() {
		if (elementUtil.doIsDisplayed(header)) {
			return elementUtil.doGetText(header);
		}
		return null;
	}

	@Step("Getting Accounts Page Section count for a user")
	public int getAccountSectionsCount() {
		return elementUtil.getElements(accountSectionHeaders).size();
	}

	@Step("Getting Accounts Page Section list for a user")
	public List<String> getAccountSectionsList() {
		List<WebElement> accSectionList = elementUtil.getElements(accountSectionHeaders);
		List<String> accSectionsNameList = new ArrayList<String>();
		for (WebElement element : accSectionList) {
			String sectionText = element.getText();
			accSectionsNameList.add(sectionText);
		}
		return accSectionsNameList;
	}

	@Step("")
	public boolean doSearch(String searchTerm) {
		elementUtil.doSendKeys(searchText, searchTerm);
		elementUtil.doClick(searchButton);
		if (elementUtil.getElements(searchItemsResult).size() > 0)
			return true;
		return false;
	}
}

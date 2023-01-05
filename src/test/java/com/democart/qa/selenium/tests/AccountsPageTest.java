package com.democart.qa.selenium.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.democart.qa.selenium.base.BaseTest;
import com.democart.qa.selenium.utils.Constants;
import com.democart.qa.selenium.utils.ExcelUtil;
import com.sun.xml.internal.ws.developer.UsesJAXBContext;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC 400: Design Accounts Page for Open Cart Application")
@Story("US 401: Accounts Page feature story")
public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accountsPageSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	//This is the demo comment 
	@Description("Test to verify the Accounts Page title....Hello")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void accountsPageTitleTest() {
		String title = accountsPage.getAccountPageTitle();
		System.out.println("Acc Page Title is" + title);
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE);
	}

	// @Test(priority = 2) //will not work as no text associated with the Header
	public void verifyAccPageHeader() {
		String accHeader = accountsPage.getHeaderValue();
		System.out.println("Acc Page Header is:" + accHeader);
		Assert.assertEquals(accHeader, Constants.ACCOUNTS_PAGE_HEADER);
	}

	@Description("Test to verify the Accounts Page sections count")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 3)
	public void verifyAccountsPageSectionsCountTest() {
		Assert.assertTrue(accountsPage.getAccountSectionsCount() == Constants.ACCOUNT_SECTIONS_COUNT);
	}

	@Description("Test to verify the Accounts Page sections list")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4)
	public void verifyAccountsPageSectionListTest() {
		List<String> accSecList = accountsPage.getAccountSectionsList();
		Assert.assertEquals(accSecList, Constants.getExpectedAccountsSectionList());
	}

//	@DataProvider
//	public Object[][] productTestData() {
//		return new Object[][]{ { "iMac" },
//							   { "MacBook" }, 
//							   { "iPhone" } 
//							   };
//	}

	@DataProvider
	public Object[][] productTestData() {
		return ExcelUtil.getData(Constants.SEARCH_SHEET_NAME);
	}

	@Description("Test to verify search:{0}")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, dataProvider = "productTestData")
	public void searchTest(String product) {
		Assert.assertTrue(accountsPage.doSearch(product));
	}
}

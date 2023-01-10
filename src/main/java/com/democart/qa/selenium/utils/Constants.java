package com.democart.qa.selenium.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	//The list of all the constants used in the POM FW
	
	
	public static final int ACCOUNT_SECTIONS_COUNT = 4;
	public static final int FORGOTTEN_PWD_LINK_COUNT = 2;
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String FORGOTTEN_PWD_VALID_EMAIL = "ayesha.yusuf@gmail.com";
	public static final String LOGIN_PAGE_SUCCESS_MESSAGE = "An email with a confirmation link has been sent your email address.";
	public static final String FORGOTTEN_PWD_INVALID_EMAIL = "ayesha.yusuf123@gmail.com";
	public static final String FORGOTTEN_PWD_ERROR_MESSAGE = "Warning: The E-Mail Address was not found in our records, please try again!";
	public static final String ACCOUNTS_PAGE_TITLE = "My Account";
	public static final String ACCOUNTS_PAGE_HEADER = "Your Store";
	public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Your Account Has Been Created!";
	public static final String REGISTER_SHEET_NAME = "registration";
	public static final String SEARCH_SHEET_NAME="search";
	public static final String LOGIN_SHEET_NAME="login";
	public static List<String> accSecList;

	public static List<String> getExpectedAccountsSectionList() {
		accSecList = new ArrayList<String>();
		accSecList.add("My Account");
		accSecList.add("My Orders");
		accSecList.add("My Affiliate Account");
		accSecList.add("Newsletter");
		return accSecList;
	}

}

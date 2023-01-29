package com.democart.qa.selenium.pages;

import org.openqa.selenium.By;

public class ProfilePage {

	//Profile Icon
	By profileIcon=By.id("profileIcon");
	By deleteIcon=By.id("removeIcon");
	
	public void updatingProfile() {
		System.out.println("Updated Profile!");
	}
	
	
	public void deleteProfile() {
		System.out.println("Delete Profile!");
	}
	
}

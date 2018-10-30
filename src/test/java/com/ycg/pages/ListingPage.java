package com.ycg.pages;

import com.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ListingPage extends AbstractPage {

  ListingPage(WebDriver driver) {
    super(driver);
  }

  private final static By LISTING = By.id("innercontent");


  public boolean isListingDisplayed() {
    return driver.findElement(LISTING).isDisplayed();
  }
}

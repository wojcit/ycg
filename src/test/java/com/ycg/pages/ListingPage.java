package com.ycg.pages;

import com.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ListingPage extends AbstractPage {

  private final static By APPLY_BUTTON = By.cssSelector(".onlbew_button_top");
  private final static By LISTING = By.id("innercontent");
  ListingPage(WebDriver driver) {
    super(driver);
  }

  public boolean isListingDisplayed() {
    return driver.findElement(LISTING).isDisplayed();
  }

  public void clickApplyButton() {
    driver.findElement(APPLY_BUTTON).click();
  }
}

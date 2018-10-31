package com.ycg.pages;

import com.ycg.framework.AbstractPage;
import com.ycg.framework.WaitTool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ListingPage extends AbstractPage {

  private final static By APPLY_BUTTON = By.cssSelector(".onlbew_button_top");
  private final static By LISTING = By.id("innercontent");
  private final static By LOGIN_FIELD = By.name("login");
  private final static By PASSWORD_FIELD = By.name("passwd");
  private final static By LOGIN_BUTTON = By.cssSelector(".left .button-primary");
  private final static By ACCOUNT_DROPDOWN = By.cssSelector(".header_container .right a");


  ListingPage(WebDriver driver) {
    super(driver);
  }

  public void login(String login, String password) {
    driver.findElement(LOGIN_FIELD).sendKeys(login);
    driver.findElement(PASSWORD_FIELD).sendKeys(password);
    driver.findElement(LOGIN_BUTTON).click();
    WaitTool.waitForPageToLoad(driver);
  }

  public boolean isListingDisplayed() {
    return driver.findElement(LISTING).isDisplayed();
  }

  public void clickApplyButton() {
    driver.findElement(APPLY_BUTTON).click();
  }

  public boolean isUserLoggedIn() {
  return driver.findElement(ACCOUNT_DROPDOWN).getAttribute("href").contains("HSID=");
  }
}

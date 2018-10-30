package com.ycg.pages;

import static com.ycg.framework.WaitTool.waitForPageToLoad;

import com.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class ResultListPage extends AbstractPage {

  private static By JOB_AGENT_POPOVER_C2A_TOP = By.id("jf_button_bottom");
  private static By RESULTLIST = By.className("resultlist");
  private static By LISTINGS = By.cssSelector(".resultlist li a.job");

  public ResultListPage(WebDriver driver) {
    super(driver);
  }

  public void clickJobAgentC2A() {
    driver.findElement(JOB_AGENT_POPOVER_C2A_TOP).click();
  }

  public boolean isResultlistPageDisplayed() {
    return driver.findElement(RESULTLIST).isDisplayed();
  }

  public ListingPage openRandomListing() {
    List<WebElement> listings = driver.findElements(LISTINGS);
    Random randomGenerator = new Random();
    int index = randomGenerator.nextInt(listings.size());
    listings.get(index).click();
    switchToNewOpenedWindow();
    waitForPageToLoad(driver);
    return new ListingPage(driver);
  }
}

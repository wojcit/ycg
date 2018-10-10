package com.ycg.pages;


import com.google.gson.Gson;
import com.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends AbstractPage {

  //TODO: bring url from config
  private static final String STST_HOME_URL = "https://www.hotelcareer.pl/";
  private static final By BY_SEARCH_WHAT_FIELD = By.id("taetigkeiten");
  private static final By BY_SEARCH_BUTTON = By.id("btnSearch_new");
  private static final By BY_JAPO_MODAL = By.id("Form_Jobfinder");
  private static final By BY_JAPO_EMAIL_FIELD = By.id("finder_mail");
  private static final By BY_JAPO_SAVE_BUTTON = By.cssSelector("#Form_Jobfinder .button-primary");


  public HomePage(WebDriver driver) {
    super(driver);
  }

  public void navigateToHomePage() {
    getDriver().navigate().to(STST_HOME_URL);
  }

  public void performSearch() {
    WebElement
        elSearchButton =
        driverWait(10).until(ExpectedConditions.elementToBeClickable(BY_SEARCH_BUTTON));
    elSearchButton.click();
  }

  public boolean japoIsDisplayed() {
    try {
      WebElement
          elJapuModal =
          driverWait(5).until(ExpectedConditions.elementToBeClickable(BY_JAPO_MODAL));
      return elJapuModal.isDisplayed();
    } catch (TimeoutException ex) {
      return false;
    }
  }

  public void typeEmailToJapo(String email) {
    getDriver().findElement(BY_JAPO_EMAIL_FIELD).sendKeys(email);
  }

  public void saveJobAgentFromJapo() {
    getDriver().findElement(BY_JAPO_SAVE_BUTTON).click();
  }

  public void checkPerformance() {
    JavascriptExecutor js = (JavascriptExecutor) getDriver();
    String timings = js.executeScript("return (window.performance.timing)").toString();
    Gson gson = new Gson();
    Timings timingsJson = gson.fromJson(timings, Timings.class);
    long pageLoadTime = timingsJson.loadEventStart - timingsJson.navigationStart;
    long backendTime = timingsJson.responseStart - timingsJson.navigationStart;
    logInfo("pageLoadTime: " + pageLoadTime);
    logInfo("backendTime: " + backendTime);
  }

  class Timings {

    private long loadEventStart;
    private long navigationStart;
    private long backEndTime;
    private long responseStart;

  }
}

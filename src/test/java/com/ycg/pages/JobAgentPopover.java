package com.ycg.pages;

import com.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobAgentPopover extends AbstractPage {

  private static final By BY_JAPO_MODAL = By.id("Form_Jobfinder");
  private static final By BY_JAPO_CLOSE = By.cssSelector(".ui-dialog .ui-icon-closethick");

  public JobAgentPopover(WebDriver driver) {
    super(driver);
  }

  public boolean isJapoDisplayed() {
    try {
      WebElement
          elJapuModal =
          driverWait(5).until(ExpectedConditions.elementToBeClickable(BY_JAPO_MODAL));
      return elJapuModal.isDisplayed();
    } catch (TimeoutException ex) {
      return false;
    }
  }

  public void closeJobAgentPopover() {
    driver.findElement(BY_JAPO_CLOSE).click();
  }
}

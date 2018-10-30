package com.ycg.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Scanner;

public abstract class AbstractPage {

  private static final Logger log = LogManager.getLogger(AbstractPage.class);
  public WebDriver driver;
  private String windowHandle;

  public AbstractPage(WebDriver driver) {
    this.driver = driver;
  }

  protected AbstractPage() {
    super();
  }

  public static void logInfo(String message) {
    Scanner scanner = new Scanner(message);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      log.info(line);
    }
    scanner.close();
  }

  protected WebDriver getDriver() {
    return driver;
  }

  protected WebDriverWait driverWait(long timeoutSeconds) {
    return new WebDriverWait(driver, timeoutSeconds);
  }

  public void switchToNewOpenedWindow() {
    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);
    }
  }
}

package com.ycg.framework;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Wait tool class. Provides Wait methods for an elements, and AJAX elements to load. It uses
 * WebDriverWait (explicit wait) for waiting an element or javaScript.
 *
 * To use implicitlyWait() and WebDriverWait() in the same test, we would have to nullify
 * implicitlyWait() before calling WebDriverWait(), and reset after it. This class takes care of
 * it.
 *
 *
 * Generally relying on implicitlyWait slows things down so use WaitTool's explicit wait methods as
 * much as possible. Also, consider (DEFAULT_WAIT_4_PAGE = 0) for not using implicitlyWait for a
 * certain test.
 * @author Chon Chung, Mark Collin, Andre, Tarun Kumar
 * @todo check FluentWait -- http://seleniumsimplified.com/2012/08/22/fluentwait-with-webelement/
 *
 * Copyright [2012] [Chon Chung]
 *
 * Licensed under the Apache Open Source License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
public final class WaitTool {

  /**
   * Default wait time for an element. 7 seconds.
   */
  public static final int DEFAULT_WAIT_4_ELEMENT = 7;
  /**
   * Default wait time for a page to be displayed. 12 seconds. The average webpage load time is 6
   * seconds in 2012. Based on your tests, please set this value. "0" will nullify implicitlyWait
   * and speed up a test.
   */
  public static final int DEFAULT_WAIT_4_PAGE = 12;
  private static Logger log = LogManager.getLogger(WaitTool.class);

  private WaitTool() {
  }

  /**
   * Wait for the element to be present in the DOM, and displayed on the page. And returns the first
   * WebElement using the given method.
   * @param driver The driver object to be used
   * @param by selector to find the element
   * @param timeOutInSeconds The time in seconds to wait until returning a failure
   * @return WebElement the first WebElement using the given method, or null (if the timeout is
   * reached)
   */
  public static WebElement waitForElement(WebDriver driver, final By by, int timeOutInSeconds) {
    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
      return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    } catch (Exception e) {
      log.error(e);
    }
    return null;
  }

  public static void waitForElementIsNotVisible(WebDriver driver, final By by,
                                                int timeOutInSeconds) {
    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
      wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    } catch (Exception e) {
      log.error(e);
    }
  }

  public static WebElement waitForWebElement(WebDriver driver, final WebElement webelement,
                                             int timeOutInSeconds) {
    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
      return wait.until(ExpectedConditions.visibilityOf(webelement));
    } catch (Exception e) {
      log.error(e);
    }
    return null;
  }


  /**
   * Wait for the element to be present in the DOM, regardless of being displayed or not. And
   * returns the first WebElement using the given method.
   * @param driver The driver object to be used
   * @param by selector to find the element
   * @param timeOutInSeconds The time in seconds to wait until returning a failure
   * @return WebElement the first WebElement using the given method, or null (if the timeout is
   * reached)
   */
  public static WebElement waitForElementPresent(WebDriver driver, final By by,
                                                 int timeOutInSeconds) {
    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
      return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    } catch (Exception e) {
      log.error(e);
    }
    return null;
  }

  public static WebElement waitForElementDisplayed(WebDriver driver, final By by,
                                                   int timeOutInSeconds) {
    return waitForElement(driver, by, timeOutInSeconds);
  }


  public static WebElement waitForElementVisible(WebDriver driver, WebElement element) {
    WaitTool.nullifyImplicitWait(driver);

    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.visibilityOf(element));

    WaitTool.resetImplicitWait(driver);

    return element;
  }

  /**
   * Wait for the List<WebElement> to be present in the DOM, regardless of being displayed or not.
   * Returns all elements within the current page DOM.
   * @param driver The driver object to be used
   * @param by selector to find the element
   * @param timeOutInSeconds The time in seconds to wait until returning a failure
   * @return List<WebElement> all elements within the current page DOM, or null (if the timeout is
   * reached)
   */
  public static List<WebElement> waitForListElementsPresent(WebDriver driver, final By by,
                                                            int timeOutInSeconds) {
    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
      wait.until((WebDriver driverObject) -> areElementsPresent(driverObject, by));
      return driver.findElements(by);
    } catch (Exception e) {
      log.error(e);
    }
    return Collections.<WebElement>emptyList();
  }

  /**
   * Wait for the Text to be present in the given element, regardless of being displayed or not.
   * @param driver The driver object to be used to wait and find the element
   * @param by selector of the given element, which should contain the text
   * @param text The text we are looking
   * @param timeOutInSeconds The time in seconds to wait until returning a failure
   * @return boolean
   */
  public static boolean waitForTextPresent(WebDriver driver, final By by, final String text,
                                           int timeOutInSeconds) {
    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      new WebDriverWait(driver, timeOutInSeconds).until(
          (WebDriver driverObject) -> isTextPresent(driverObject, by, text)
          // is the Text in the DOM
      );
      return isTextPresent(driver, by, text);
    } catch (Exception e) {
      log.error(e);
    }
    return false;
  }

  /**
   * Waits for the completion of Ajax jQuery processing by checking "return jQuery.active == 0"
   * condition.
   * @param driver - The driver object to be used to wait and find the element
   * @param timeOutInSeconds - The time in seconds to wait until returning a failure
   * @return boolean true or false(condition fail, or if the timeout is reached)
   */
  public static boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds) {
    boolean jQcondition = false;

    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      new WebDriverWait(driver, timeOutInSeconds).until((WebDriver driverObject) ->
                                                            (Boolean) ((JavascriptExecutor) driverObject)
                                                                .executeScript("return !!window.jQuery && window.jQuery.active == 0"));
      jQcondition =
          (Boolean) ((JavascriptExecutor) driver)
              .executeScript("return !!window.jQuery && window.jQuery.active == 0");

      return jQcondition;
    } catch (Exception e) {
      log.error(e);
    }

    return jQcondition;
  }


  /**
   * Waits for the completion of Ajax Prototype processing by checking "return
   * Ajax.activeRequestCount == 0" condition.
   * @param driver - The driver object to be used to wait and find the element
   * @param timeOutInSeconds - The time in seconds to wait until returning a failure
   * @return boolean true or false(condition fail, or if the timeout is reached)
   */
  public static boolean waitForPrototypeProcessing(WebDriver driver, int timeOutInSeconds) {
    boolean pQcondition = false;

    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      new WebDriverWait(driver, timeOutInSeconds).until((WebDriver driverObject) ->
                                                            (Boolean) ((JavascriptExecutor) driverObject)
                                                                .executeScript("return Ajax.activeRequestCount == 0"));
      pQcondition = (Boolean) ((JavascriptExecutor) driver)
          .executeScript("return Ajax.activeRequestCount == 0");

      return pQcondition;
    } catch (Exception e) {
      log.error(e);
    }

    return pQcondition;
  }

  /**
   * Coming to implicit wait, If you have set it once then you would have to explicitly set it to
   * zero to nullify it -
   */
  public static void nullifyImplicitWait(WebDriver driver) {
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify implicitlyWait()
  }


  /**
   * Set driver implicitlyWait() time.
   */
  public static void setImplicitWait(WebDriver driver, int waitTimeInSeconds) {
    driver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
  }

  /**
   * Reset ImplicitWait. To reset ImplicitWait time you would have to explicitly set it to zero to
   * nullify it before setting it with a new time value.
   */
  public static void resetImplicitWait(WebDriver driver) {
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify implicitlyWait()
    driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
    // implicitlyWait
  }

  /**
   * Checks if the text is present in the element.
   * @param driver - The driver object to use to perform this element search
   * @param by - selector to find the element that should contain text
   * @param text - The Text element you are looking for
   * @return true or false
   */
  private static boolean isTextPresent(WebDriver driver, By by, String text) {
    try {
      return driver.findElement(by).getText().contains(text);
    } catch (NullPointerException e) {
      logElementNotFoundMessage(by, e);
      return false;
    }
  }

  /**
   * Checks if the List<WebElement> are in the DOM, regardless of being displayed or not.
   * @param driver - The driver object to use to perform this element search
   * @param by - selector to find the element
   * @return boolean
   */
  private static boolean areElementsPresent(WebDriver driver, By by) {
    try {
      driver.findElements(by);
      return true;
    } catch (NoSuchElementException e) {
      logElementNotFoundMessage(by, e);
      return false;
    }
  }

  /*
   * References: 1. Mark Collin's post on:
   * https://groups.google.com/forum/?fromgroups#!topic/webdriver/V9KqskkHmIs%5B1-25%5D Mark's code
   * inspires me to write this class. Thank you! Mark. 2. Andre, and Tarun Kumar's post on:
   * https://groups.google.com/forum/?fromgroups=#!topic/selenium-users/6VO_7IXylgY 3. Explicit and
   * Implicit Waits: http://seleniumhq.org/docs/04_webdriver_advanced.html
   *
   * Note: 1. Instead of creating new WebDriverWait() instance every time in each methods, I tried to
   * reuse a single WebDriverWait() instance, but I found and tested that creating 100 WebDriverWait()
   * instances takes less than one millisecond. So, it seems not necessary.
   */


  public static void waitforAngularJS(WebDriver driver, int timeoutInSeconds) {

    final String query =
        "window.angularFinished;" + "waitForAngular =function() {"
        + "  window.angularFinished = false;"
        + "  var el = document.querySelector('body');"
        + "  var callback = (function(){window.angularFinished=1});"
        + "  angular.element(el).injector().get('$browser')."
        + "      notifyWhenNoOutstandingRequests(callback);};";

    ((JavascriptExecutor) driver).executeScript(query);

    try {
      ((JavascriptExecutor) driver).executeScript("waitForAngular();");
      new WebDriverWait(driver, timeoutInSeconds).until(
          (WebDriver d) -> {
            Object
                noAjaxRequests =
                ((JavascriptExecutor) driver).executeScript("return window.angularFinished;");
            return "1".equals(noAjaxRequests.toString());

          });
    } catch (TimeoutException e) {
      String errorMsg =
          "Failed wait for no angular JS request within timeout of : " + timeoutInSeconds
          + " seconds  at url:"
          + driver.getCurrentUrl();
      log.error(errorMsg, e);
    } catch (JavascriptException ex) {
      log.error("Javascript error for waitforAngularJS.", ex);
    }
  }

  /**
   * Method wait for page to completely load
   */
  public static void waitForPageToLoad(WebDriver driver) {
    try (ImplicitWaitNullfier iwn = new ImplicitWaitNullfier(driver)) {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      try {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("about:blank")));
      } catch (TimeoutException ex) {
        log.error("Error! After 30 seconds URL is still: 'about:blank'!!!", ex);
        throw ex;
      }

      try {
        new WebDriverWait(driver, 30) {
        }.until(
            (WebDriver webDriver) -> {
              Object
                  documentState =
                  ((JavascriptExecutor) webDriver).executeScript("return document.readyState");
              return "complete".equals(documentState.toString());
            });
      } catch (TimeoutException ex) {
        log.error("Timeout error! After 30 s page did not load!", ex);
        throw ex;
      }
    }
  }

  private static void logElementNotFoundMessage(By by, Exception e) {
    log.info("Element: " +by.toString()+ " not present", e);
  }
}

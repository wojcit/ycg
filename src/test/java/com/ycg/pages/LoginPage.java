package com.ycg.pages;

import com.ycg.framework.AbstractPage;
import com.ycg.framework.WaitTool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

  public LoginPage(WebDriver driver){
    super(driver);
  }

  private static By LOGIN_FORM = By.id("form_login");


  public boolean isLoginPageDisplayed() {
    WaitTool.waitForPageToLoad(driver);
    switchToNewOpenedWindow();
    return driver.findElement(LOGIN_FORM).isDisplayed();
  }
}

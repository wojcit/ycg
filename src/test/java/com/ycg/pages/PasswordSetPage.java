package com.ycg.pages;


import com.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class PasswordSetPage extends AbstractPage {

  PasswordSetPage(WebDriver driver){
    super(driver);
  }

  @FindBy (id = "register_password")
  WebElement elPasswordField;

  private static final By PASSWORD_FIELD = By.id("register_password");



  public boolean checkIfThisIsPasswordSetModal() {
    driverWait(10).until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
    return getDriver().findElement(PASSWORD_FIELD).isDisplayed();
  }

  public void typePassword(String password) {
    elPasswordField.clear();
    elPasswordField.sendKeys(password);
  }

}

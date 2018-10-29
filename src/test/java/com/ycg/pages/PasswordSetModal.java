package com.ycg.pages;


import com.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class PasswordSetModal extends AbstractPage {

  PasswordSetModal(WebDriver driver){
    super(driver);
  }

  private static final By PASSWORD_FIELD = By.id("register_password");
  private static final By CREATE_ACCOUNT_BUTTON = By.cssSelector(".ui-dialog .button-primary");



  public boolean checkIfThisIsPasswordSetModal() {
    driverWait(10).until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
    return getDriver().findElement(PASSWORD_FIELD).isDisplayed();
  }

  public void typePassword(String password) {
    WebElement elPasswordField = getDriver().findElement(PASSWORD_FIELD);
    elPasswordField.clear();
    elPasswordField.sendKeys(password);
  }
  public void clickCreateAccount(){
    getDriver().findElement(CREATE_ACCOUNT_BUTTON).click();
  }

}

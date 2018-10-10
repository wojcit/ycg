package com.steroids.ycg.pages;


import com.steroids.ycg.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class PasswordSetPage extends AbstractPage {

  PasswordSetPage(WebDriver driver){
    super(driver);
  }

  @FindBy(css = "body.candidateLogin, body.candidatelogin")
  private List<WebElement> elConfirmationAccountPage;

  @FindBy(id = "login_passw1")
  private WebElement elPasswordTextBox;

  @FindBy(id = "login_passw2")
  private WebElement elPasswordRepeatTextBox;

  @FindBy(id = "rememberMe")
  private WebElement elRememberMeCheckbox;

  @FindBy(xpath = "//button[@type='submit']")
  private WebElement elSubmitButton;

  @FindBy(css = "#setPassword>strong")
  private WebElement elSetPasswordSuccessMessage;

  private static final By PASSWORD_SECTION = By.id("login_passw1_text");



  public boolean checkIfThisIsPasswordSetPage() {
    driverWait(10).until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_SECTION));
    return getDriver().findElement(PASSWORD_SECTION).isDisplayed();
  }

  public void typePasswordRepeat(String value) {
    elPasswordRepeatTextBox.clear();
    elPasswordRepeatTextBox.sendKeys(value);
  }


  public void typePassword(String password) {
    elPasswordTextBox.clear();
    elPasswordTextBox.sendKeys(password);
  }

}

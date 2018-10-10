package com.ycg.pages;

import com.ycg.framework.AbstractPage;
import com.ycg.framework.EmailChecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class JobAgentConfirmationEmailPage extends AbstractPage {

  public JobAgentConfirmationEmailPage(WebDriver driver) {
    super(driver);
  }


  public PasswordSetPage confirmEmailMessageFistep(String email, String subject) {
    EmailChecker emailChecker = new EmailChecker();
    String url = emailChecker.getMessageUrl(email, subject);
    if (!Objects.equals(url, getDriver().getCurrentUrl())) {
      goToConfirmationEmailFistep(email, subject);
    }
    String confirmationUrl =
        getDriver().findElement(
            By.cssSelector(".button-container a")).getAttribute("href");
    logInfo(confirmationUrl);
    getDriver().get(confirmationUrl);
    return new PasswordSetPage(getDriver());
  }

  public void goToConfirmationEmailFistep(String email, String subject) {
    logInfo("Subject parameter: " + subject);
    logInfo("Subject from xml: " + subject);
    logInfo("Email: " + email);
    EmailChecker emailChecker = new EmailChecker();
    String url;
    do {
      url = emailChecker.getMessageUrl(email, subject);
      logInfo(url);
    }
    while (url.equals(""));
    getDriver().get(url);
  }
}

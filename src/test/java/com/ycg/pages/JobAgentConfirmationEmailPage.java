package com.steroids.ycg.pages;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.steroids.ycg.framework.AbstractPage;
import com.steroids.ycg.framework.EmailChecker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

    getDriver().get(confirmationUrl);
    return new PasswordSetPage(getDriver());
  }

  public void goToConfirmationEmailFistep(String email, String subject) {
    logInfo("Subject parameter: " + subject);
    logInfo("Subject from xml: " + subject);
    logInfo("Email: " + email);
    EmailChecker emailChecker = new EmailChecker();
    String url = emailChecker.getMessageUrl(email, subject);
    logInfo(url);
    getDriver().get(url);
  }

  public void confirmationEmailPageLayoutTest(WebDriver driver, String device) throws IOException {
    //Create a layoutReport object
    //checkLayout function checks the layout and returns a LayoutReport object
    String pathToSpecs = "/com/steroids/example/specs/email.gspec";
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      pathToSpecs = "com\\steroids\\ycg\\specs\\email.gspec";
    }
    LayoutReport layoutReport = Galen.checkLayout(driver, pathToSpecs, Arrays.asList(device));

    //Create a tests list
    List<GalenTestInfo> tests = new LinkedList<>();

    //Create a GalenTestInfo object
    GalenTestInfo test = GalenTestInfo.fromString("confirmation email layout");

    //Get layoutReport and assign to test object
    test.getReport().layout(layoutReport, "check confirmation email layout");

    //Add test object to the tests list
    tests.add(test);

    //Create a htmlReportBuilder object
    HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

    //Create a report under /target folder based on tests list
    htmlReportBuilder.build(tests, "target");

    //If layoutReport has errors Assert Fail
    if (layoutReport.errors() > 0) {
      Assert.fail("Layout test failed");
    }
  }
}

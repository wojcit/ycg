package com.steroids.ycg.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;

import com.steroids.ycg.framework.AbstractPage;
import com.steroids.ycg.framework.MailNotFoundException;
import com.steroids.ycg.pages.HomePage;
import com.steroids.ycg.pages.JobAgentConfirmationEmailPage;
import com.steroids.ycg.pages.PasswordSetPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.io.IOException;
import java.util.UUID;

public class HomePageSteps {

  private WebDriver driver;
  private HomePage homePage;
  private JobAgentConfirmationEmailPage jobAgentConfirmationEmailPage;
  private String email;
  private PasswordSetPage passwordSetPage;
  private String subject;

  @Before(value = "@automated", order = 1)
  public void initWebDriver() {
    ChromeOptions options = new ChromeOptions();
    String path;
    options.addArguments("--start-maximized");
    String os = System.getProperty("os.name").toLowerCase();
    ClassLoader classLoader = getClass().getClassLoader();

    if (os.contains("win")) {
      path = classLoader.getResource("chromedriverwin.exe").getPath();
      System.setProperty("webdriver.chrome.driver", path);
    } else if (os.contains("mac")) {
      path = classLoader.getResource("chromedriver").getPath();
      System.setProperty("webdriver.chrome.driver", path);
    } else {
      path = classLoader.getResource("chromedriverlin").getPath();
    }
    System.setProperty("webdriver.chrome.driver", path);
    driver = new ChromeDriver(options);
  }

  @Before(value = "@automated", order = 10)
  public void initHomePage() {
    homePage = new HomePage(driver);
  }

  @Given("^I am on the home page$")
  public void iAmOnTheHomePage() {
    homePage.navigateToHomePage();
  }

  @When("^I perform search$")
  public void theSearchPhraseIsEntered() {
    homePage.performSearch();
  }

  @Then("^JobAgent Popover is shown$")
  public void japoIsShown() {
    assertThat(homePage.japoIsDisplayed()).isTrue();
  }

  @Then("^I type random email into JobAgent Popover$")
  public void typeRandomEmailIntoJapo() {
    email =
        "test" + UUID.randomUUID().toString().substring(0, 10)
        + "@ec2-34-244-6-12.eu-west-1.compute.amazonaws.com";
    homePage.typeEmailToJapo(email);
    AbstractPage.logInfo(email);
  }

  @And("^I save JobAgent from JobAgent Popover")
  public void saveJobAgentFromJapo() {
    homePage.saveJobAgentFromJapo();
  }

  @Then("^Confirm JobAgent from email$")
  public void checkJobAgentConfirmationEmail() throws MailNotFoundException {
    //TODO: get subject from config
    subject = "Aktywuj swojego Agenta Pracy - HOTELCAREER";
    jobAgentConfirmationEmailPage = new JobAgentConfirmationEmailPage(driver);
    jobAgentConfirmationEmailPage.goToConfirmationEmailFistep(email, subject);
    jobAgentConfirmationEmailPage.confirmEmailMessageFistep(email, subject);
  }

  @Then("^I check layout on \"([^\"]*)\"$")
  public void checkLooksGood(String device) throws IOException {
    jobAgentConfirmationEmailPage.confirmationEmailPageLayoutTest(driver, device);
  }

  @Then("^I check performance$")
  public void checkPerf() {
    homePage.checkPerformance();
  }

  @After(value = "@automated")
  public void disposeWebDriver() {
    driver.quit();
  }

  @When("^I click confirmation button$")
  public void iClickConfirmationButton() {
    passwordSetPage = jobAgentConfirmationEmailPage
        .confirmEmailMessageFistep(email, subject);
  }

  @Then("^I am on Password Set page$")
  public void iAmOnPasswordSetPage() {
    Assert.assertTrue(passwordSetPage.checkIfThisIsPasswordSetPage());
  }
}

package com.ycg.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;

import com.ycg.framework.AbstractPage;
import com.ycg.framework.MailNotFoundException;
import com.ycg.pages.HomePage;
import com.ycg.pages.JobAgentConfirmationEmailPage;
import com.ycg.pages.JobAgentPopover;
import com.ycg.pages.ListingPage;
import com.ycg.pages.LoginPage;
import com.ycg.pages.PasswordSetModal;
import com.ycg.pages.ResultListPage;

import cucumber.api.PendingException;
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

import java.util.UUID;

public class HomePageSteps {

  private WebDriver driver;
  private HomePage homePage;
  private JobAgentPopover jobAgentPopover;
  private JobAgentConfirmationEmailPage jobAgentConfirmationEmailPage;
  private String email;
  private PasswordSetModal passwordSetModal;
  private String subject;
  private ResultListPage resultListPage;
  private ListingPage listingPage;
  private LoginPage loginPage;

  @Before(order = 1)
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

  @Before(order = 10)
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
    resultListPage = new ResultListPage(driver);

  }

  @Then("^JobAgent Popover is shown$")
  public void japoIsShown() {
    jobAgentPopover = new JobAgentPopover(driver);
    assertThat(jobAgentPopover.isJapoDisplayed()).isTrue();
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

  @Then("^I check performance$")
  public void checkPerf() {
    homePage.checkPerformance();
  }

  @After()
  public void disposeWebDriver() {
    driver.quit();
  }

  @When("^I click confirmation button$")
  public void iClickConfirmationButton() {
    passwordSetModal = jobAgentConfirmationEmailPage
        .confirmEmailMessageFistep(email, subject);
  }

  @Then("^I am on Password Set modal$")
  public void iAmOnPasswordSetModal() {
    Assert.assertTrue(passwordSetModal.checkIfThisIsPasswordSetModal());
  }


  @And("^I type password and create account on Password Set modal$")
  public void iTypePasswordAndCreateAccountOnPasswordSetModal() {
    passwordSetModal.typePassword("testtest2%");
    passwordSetModal.clickCreateAccount();
  }

  @Then("^I close JobAgent Popover$")
  public void iCloseJobAgentPopover() {
    jobAgentPopover.closeJobAgentPopover();
  }

  @And("^I click JobAgent C2A on Resultlist$")
  public void iClickJobAgentCAOnResultlist() {
    resultListPage.clickJobAgentC2A();
  }

  @Then("^I am on resultlist$")
  public void iAmOnResultlist() {
    Assert.assertTrue(resultListPage.isResultlistPageDisplayed());
  }

  @When("^I open random listing from resultlist$")
  public void iOpenRandomListingFromResultlist() {
    listingPage = resultListPage.openRandomListing();
  }

  @Then("^I am on listing$")
  public void iAmOnListing() {
    Assert.assertTrue(listingPage.isListingDisplayed());
  }

  @And("^I click Apply button$")
  public void iClickApplyButton() {
    listingPage.clickApplyButton();
  }

  @Then("^I am on login page$")
  public void iAmOnLoginPage() {
    loginPage = new LoginPage(driver);
    Assert.assertTrue(loginPage.isLoginPageDisplayed());
  }

  @And("^I am logged as user ([^\\s]+) with password ([^\\s]+)?$")
  public void iLogAsTestuserWithPasswordTesttest(String login, String password) {
    listingPage.login(login, password);
    Assert.assertTrue(listingPage.isUserLoggedIn());
  }
}

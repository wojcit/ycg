package com.steroids.ycg.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber", "junit:target/cucumber.xml"},
        features = "src/test/resources/com/steroids/ycg/features",
        glue = {"src/test/java/com.steroids.ycg.stepdefs"})
public class CucumberTest {
}

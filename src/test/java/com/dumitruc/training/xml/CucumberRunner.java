package com.dumitruc.training.xml;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: dumitruc
 * Date: 20/05/14
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"@xml","~@wip"},
        features = ".",
        format = {"json:target/report-cucumber/cucumber.json","html:report/report-cucumber"}
)
public class CucumberRunner {
}

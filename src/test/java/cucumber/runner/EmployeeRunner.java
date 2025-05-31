package cucumber.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "cucumber.definitions",
    plugin = {"pretty", "html:target/cucumber-reports.html",
                        "json:target/cucumber.json",
                        "junit:target/cucumber.xml"}
)
public class EmployeeRunner{

}

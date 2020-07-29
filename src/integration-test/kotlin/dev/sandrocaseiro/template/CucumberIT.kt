package dev.sandrocaseiro.template

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/integration-test/resources/features"],
    monochrome = true,
    plugin = ["pretty", "html:build/cucumber.html"]
)
class CucumberIT

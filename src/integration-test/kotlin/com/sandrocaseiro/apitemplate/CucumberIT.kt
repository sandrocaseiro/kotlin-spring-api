package com.sandrocaseiro.apitemplate

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/integration-test/resources/features"],
    monochrome = true,
    plugin = ["pretty", "html:target/cucumber"],
    strict = true)
class CucumberIT

package com.testproject.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.testproject.config.WebDriverConfig;
import com.testproject.steps.BrowserSteps;
import com.testproject.utils.Config;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseSimultaneousTest.class);

    @BeforeAll
    public static void start() {
        WebDriverConfig.configure();

        Configuration.reportsFolder = "target/reports/tests";

        SelenideLogger.addListener(Config.getProperty("selenide.listener"),
                new AllureSelenide().screenshots(true).savePageSource(false));

        LOG.info(String.format("Running [%s] browser", Config.getBrowser()));
        LOG.info(String.format("Base URL %s", Config.getBaseUrl()));
        LOG.info(String.format("API URL %s", Config.getApiUrl()));
    }

    @AfterAll
    public static void stop() {
        SelenideLogger.removeListener(Config.getProperty("selenide.listener"));
        BrowserSteps.closeBrowser();
    }

}

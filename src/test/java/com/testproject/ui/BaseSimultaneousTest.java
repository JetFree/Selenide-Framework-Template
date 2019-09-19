package com.testproject.ui;

import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.testproject.utils.Config;
import com.testproject.webdriver.SelenideCustomConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseSimultaneousTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseSimultaneousTest.class);

    protected SelenideDriver browser1, browser2;
    protected SelenideDriver browser3, browser4; // It's rare case when you need more then 2 browsers simultaneously

    /**
     * !!! Each test should close its browsers by itself
     */

    @BeforeAll
    public static void setUpBrowsers() {
        LOG.info(String.format("Running [%s] browser", Config.getBrowser()));
        LOG.info(String.format("Base URL %s", Config.getBaseUrl()));
        LOG.info(String.format("API URL %s", Config.getApiUrl()));
    }

    @BeforeEach
    public void addListener() {
        browser1 = new SelenideDriver(SelenideCustomConfig.get());
        browser2 = new SelenideDriver(SelenideCustomConfig.get());
        browser3 = new SelenideDriver(SelenideCustomConfig.get());
        browser4 = new SelenideDriver(SelenideCustomConfig.get());

        SelenideCustomConfig.addListener(browser1);
        SelenideCustomConfig.addListener(browser2);
        SelenideCustomConfig.addListener(browser3);
        SelenideCustomConfig.addListener(browser4);
    }

    @AfterEach
    public void removeListeners() {
        SelenideLogger.removeAllListeners();
    }
}

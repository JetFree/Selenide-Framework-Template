package com.testproject.webdriver;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.testproject.config.AllureSimultaneousSelenide;
import com.testproject.utils.Config;
import com.testproject.webdriver.capabilities.CustomCapabilities;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SelenideCustomConfig {

    /**
     * Custom Selenide config that should be used when running 2 or more browsers simultaneously
     */
    public static SelenideConfig get() {
        SelenideConfig config = new SelenideConfig();
        Browser browser = Config.getBrowser();
        if (Config.isGridUse()) {
            Capabilities capabilities = new DesiredCapabilities(CustomCapabilities.forBrowser(Config.getBrowser()).get());
            DesiredCapabilities remoteCapabilities = new DesiredCapabilities(capabilities);
            remoteCapabilities.setCapability("enableVNC", true);
            remoteCapabilities.setCapability("enableVideo", false);

            config.remote(Config.getGridHost() + "/wd/hub")
                    .browserCapabilities(remoteCapabilities)
                    .browserSize(Config.getBrowserSize())
                    .screenshots(true);
        } else {
            config.browserCapabilities(new DesiredCapabilities(CustomCapabilities.forBrowser(browser).get()))
                    .screenshots(true);
        }

        return config
                .reportsFolder("target/reports/tests")
                .browser(browser.getName());
    }

    public static void addListener(SelenideDriver browser) {
        if (browser != null) {
            SelenideLogger.addListener(Config.getProperty("selenide.listener"),
                    new AllureSimultaneousSelenide(browser));
        }
    }
}

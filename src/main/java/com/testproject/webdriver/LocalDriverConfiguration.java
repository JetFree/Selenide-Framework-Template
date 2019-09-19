package com.testproject.webdriver;


import com.codeborne.selenide.Configuration;
import com.testproject.utils.Config;
import com.testproject.webdriver.capabilities.CustomCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class LocalDriverConfiguration implements WebDriverConfiguration {

    @Override
    public void configure() {
        Configuration.browserCapabilities = new DesiredCapabilities(
                CustomCapabilities.forBrowser(Config.getBrowser()).get());
    }
}

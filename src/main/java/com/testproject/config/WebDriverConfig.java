package com.testproject.config;

import com.testproject.utils.Config;
import com.testproject.webdriver.LocalDriverConfiguration;
import com.testproject.webdriver.RemoteDriverConfiguration;
import com.testproject.webdriver.WebDriverConfiguration;

public class WebDriverConfig {

    public static void configure() {
        chooseDriver().configure();
    }

    private static WebDriverConfiguration chooseDriver() {
        return Config.isGridUse()
                ? new RemoteDriverConfiguration()
                : new LocalDriverConfiguration();
    }

}

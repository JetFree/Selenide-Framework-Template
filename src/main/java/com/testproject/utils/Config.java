package com.testproject.utils;

import com.testproject.webdriver.Browser;

import java.util.Properties;

public class Config {

    private static final Properties PROPERTIES;

    static {
        PROPERTIES = PropertyLoader.loadAll();
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }

    // --------- assistant methods ---------

    /**
     * Chrome is default if browser property was not specified or did not match available browser.
     */
    public static Browser getBrowser() {
        String browser = PROPERTIES.getProperty("selenide.browser");
        return Browser.fromString(browser);
    }

    public static boolean isGridUse() {
        String host = PROPERTIES.getProperty("grid_host");
        return !host.isEmpty();
    }

    public static String getGridHost() {
        return PROPERTIES.getProperty("grid_host");
    }

    public static String getDownloadDirectory() {
        return PROPERTIES.getProperty("download.directory");
    }

    public static String getBaseUrl() {
        return PROPERTIES.getProperty("selenide.baseUrl");
    }

    public static String getApiUrl() {
        return PROPERTIES.getProperty("restHost");
    }

    public static String getBrowserSize() {
        return PROPERTIES.getProperty("selenide.browserSize");
    }

    public static boolean isHeadless() {
        return Boolean.valueOf(PROPERTIES.getProperty("selenide.headless"));
    }
}

package com.testproject.utils;

import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Set;

public class CommonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * DO NOT USE IT WHEN IF POSSIBLE TO AVOID
     */
    @Step
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
            LOG.debug(String.format("Sleep %d second", millis));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step
    public static SelenideElement scrollIntoViewJS(SelenideDriver browser, SelenideElement element) {
        browser.executeJavaScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    @Step
    public static SelenideElement clickJS(SelenideDriver browser, SelenideElement element) {
        browser.executeJavaScript("arguments[0].click();", element);
        return element;
    }


    @Step
    public static SelenideElement hoverActions(SelenideDriver browser, SelenideElement element) {
        new Actions(browser.getWebDriver()).moveToElement(element.toWebElement()).pause(Duration.ofMillis(1500)).perform();
        return element;
    }

    @Step
    public static SelenideElement clearJS(SelenideDriver browser, SelenideElement element) {
        browser.executeJavaScript("arguments[0].value = '';", element);
        return element;
    }

    @Step
    public static void closeUnusedBrowserTabs(SelenideDriver browser) {
        WebDriver webDriver = browser.getWebDriver();
        String activeTab = webDriver.getWindowHandle();
        Set<String> windows = webDriver.getWindowHandles();
        windows.stream().filter(tab -> !tab.equals(activeTab))
                .forEach(tab -> webDriver.switchTo().window(tab).close());
        browser.switchTo().window(activeTab);
    }
}

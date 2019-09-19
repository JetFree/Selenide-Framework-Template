package com.testproject.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;

import java.util.Set;
import java.util.function.Supplier;

import static com.codeborne.selenide.Selenide.$;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class BrowserSteps {

    /**
     * Clear browser cache before opening url
     */
    @Step
    public static void openUrl(String url) {
        WebDriverRunner.clearBrowserCache();
        Selenide.open(url);
    }

    @Step
    public static String getCurrentUrl() {
        return WebDriverRunner.url();
    }

    @Step
    public static void executeInFrame(String frameIdOrTag, Runnable action) {
        Selenide.switchTo().frame($(frameIdOrTag));
        action.run();
        Selenide.switchTo().parentFrame();
    }

    @Step
    public static <T> T executeInFrame(String frameIdOrTag, Supplier<T> action) {
        Selenide.switchTo().frame($(frameIdOrTag));
        T t = action.get();
        Selenide.switchTo().parentFrame();
        return t;
    }

    @Step
    public static void executeInNewBrowserTabOrWindow(Runnable runnable) {
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        String currentWindow = webDriver.getWindowHandle();

        Set<String> windowHandles = await().atMost(3, SECONDS).until(
                webDriver::getWindowHandles,
                Matchers.hasSize(Matchers.greaterThan(1)));

        if (windowHandles.size() == 1) {
            throw new RuntimeException("Only 1 window exists. Expect 2.");
        }

        if (windowHandles.size() == 2)
            try {
                windowHandles.remove(currentWindow);
                webDriver.switchTo().window(windowHandles.iterator().next());
                runnable.run();
                webDriver.close();
                return;
            } finally {
                webDriver.switchTo().window(currentWindow);
            }

        throw new RuntimeException("More than 2 browser windows detected.");
    }

    @Step
    public static void closeBrowser() {
        Selenide.close();
    }

    // ------------------ Parallel browsers

    @Step
    public static void closeBrowser(SelenideDriver browser) {
        if (browser != null) {
            browser.clearCookies();
            browser.close();
        }
    }

    @Step
    public static void closeUnexpectedDialog(SelenideDriver browser) {
        browser.$(".popupContent .v-window-closebox").shouldBe(Condition.visible).click();
    }

    @Step
    public static void closeSessionLimitReached(SelenideDriver browser) {
        browser.$("#confirmdialog-ok-button").shouldBe(Condition.visible).click();
    }

    @Step
    public static void executeInNewBrowserTabOrWindow(SelenideDriver browser, Runnable runnable) {
        WebDriver webDriver = browser.getWebDriver();
        String currentWindow = webDriver.getWindowHandle();

        Set<String> windowHandles = await().atMost(3, SECONDS).until(
                webDriver::getWindowHandles,
                Matchers.hasSize(Matchers.greaterThan(1)));

        if (windowHandles.size() == 1) {
            throw new RuntimeException("Only 1 window exists. Expect 2.");
        }

        if (windowHandles.size() == 2)
            try {
                windowHandles.remove(currentWindow);
                browser.switchTo().window(windowHandles.iterator().next());
                runnable.run();
                webDriver.close();
                return;
            } finally {
                browser.switchTo().window(currentWindow);
            }

        throw new RuntimeException("More than 2 browser windows detected.");
    }
}

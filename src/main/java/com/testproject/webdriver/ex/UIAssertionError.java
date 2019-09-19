package com.testproject.webdriver.ex;

import com.codeborne.selenide.Driver;
import com.codeborne.selenide.impl.Cleanup;
import com.codeborne.selenide.impl.ScreenShotLaboratory;
import org.openqa.selenium.WebDriverException;

import static com.codeborne.selenide.ex.ErrorMessages.screenshot;


public class UIAssertionError extends AssertionError {
    private final Driver driver;

    private String screenshot;
    public long timeoutMs;

    public UIAssertionError(Driver driver, Throwable cause) {
        this(driver, cause.getClass().getSimpleName() + ": " + cause.getMessage(), cause);
    }

    public UIAssertionError(Driver driver, String message) {
        super(message);
        this.driver = driver;
    }

    public UIAssertionError(Driver driver, String message, Throwable cause) {
        super(message, cause);
        this.driver = driver;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getLocalizedMessage() + uiDetails();
    }

    protected String uiDetails() {
        return screenshot(driver.config(), screenshot) + timeout(timeoutMs) + causedBy(getCause());
    }

    public static Error wrapThrowable(Driver driver, String errorMessage) {
        UIAssertionError uiError = new UIAssertionError(driver, errorMessage);
        uiError.screenshot = ScreenShotLaboratory.getInstance().formatScreenShotPath(driver);
        return uiError;
    }

    private static String causedBy(Throwable cause) {
        if (cause == null) {
            return "";
        }
        if (cause instanceof WebDriverException) {
            return "\nCaused by: " + Cleanup.of.webdriverExceptionMessage(cause);
        }
        return "\nCaused by: " + cause;
    }

    private static String timeout(long timeoutMs) {
        if (timeoutMs < 1000) {
            return "\nTimeout: " + timeoutMs + " ms.";
        }
        if (timeoutMs % 1000 == 0) {
            return "\nTimeout: " + timeoutMs / 1000 + " s.";
        }

        return "\nTimeout: " + String.format("%.3f", timeoutMs / 1000.0) + " s.";
    }
}

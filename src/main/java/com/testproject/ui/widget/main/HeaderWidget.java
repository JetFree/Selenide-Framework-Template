package com.testproject.ui.widget.main;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;

public class HeaderWidget {

    private SelenideDriver browser;
    private SelenideElement root;

    public HeaderWidget() {
        this.browser = WebDriverRunner.getSelenideDriver();
        root = getRoot(browser);
    }

    public HeaderWidget(SelenideDriver browser) {
        this.browser = browser;
        root = getRoot(browser);
    }

    private SelenideElement getRoot(SelenideDriver browser) {
        return browser.$(Selectors.byId("gb"));
    }

    public SelenideElement menu() {
        return root.$(Selectors.byTitle("Приложения Google"));
    }

    public void openMail() {
        root.$(Selectors.byLinkText("Почта")).click();
    }

    public void openImages() {
        root.$(Selectors.byLinkText("Картинки")).click();
    }

    @Step
    public HeaderWidget openGoogleApps() {
        menu().click();
        return this;
    }
}

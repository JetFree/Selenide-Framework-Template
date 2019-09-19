package com.testproject.ui.page.main;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.WebDriverRunner;
import com.testproject.ui.widget.main.HeaderWidget;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;

public class MainPage {

    private SelenideDriver browser;

    public MainPage() {
        this.browser = WebDriverRunner.getSelenideDriver();
    }

    public MainPage(SelenideDriver browser) {
        this.browser = browser;
    }

    public HeaderWidget header() {
        return new HeaderWidget(browser);
    }

    @Step
    public MainPage shouldBeOpened() {
        browser.$(byId("hplogo")).shouldBe(visible);
        return this;
    }

    @Step
    public MainPage typeInSearchBox(String textToSearch) {
        browser.$(Selectors.byXpath("Поиск")).shouldBe(Condition.enabled);
        browser.$(Selectors.byTitle("Поиск")).setValue(textToSearch);
        return this;
    }

    @Step
    public MainPage clickSearchInGoogle() {
        browser.$(Selectors.byValue("Поиск в Google")).click();
        return this;
    }

    @Step
    public MainPage clickImLucky() {
        browser.$(Selectors.byValue("Мне повезёт!")).click();
        return this;
    }

}

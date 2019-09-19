package com.testproject.steps;

import com.codeborne.selenide.Selenide;
import com.testproject.ui.page.main.MainPage;
import com.testproject.utils.Config;
import io.qameta.allure.Step;

public class NavigationSteps {

    @Step("Open Main Page")
    public static MainPage openMainPage() {
        com.testproject.steps.BrowserSteps.openUrl(Config.getBaseUrl());
        return Selenide.page(MainPage.class);
    }

}

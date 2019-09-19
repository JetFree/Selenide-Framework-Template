package com.testproject.ui.search;

import com.testproject.ui.BaseTest;
import com.testproject.ui.page.main.MainPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.testproject.steps.NavigationSteps;

@Epic("Search features")
@Feature("I'm lucky feature")
public class GoogleTest extends BaseTest {

    @Test
    @DisplayName("GOOGL-01")
    @Severity(SeverityLevel.NORMAL)
    @Story("Validate New 'Flag' bubble blotter filter")
    public void validateNewFlagBlotterFilter() {
        MainPage mainPage = NavigationSteps.openMainPage();
        mainPage.shouldBeOpened();
    }
}

package com.testproject.config;

import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;

import java.util.UUID;

public class AllureSimultaneousSelenide implements LogEventListener {
    private SelenideDriver browser;
    private boolean saveScreenshots;
    private AllureLifecycle lifecycle;

    public AllureSimultaneousSelenide(SelenideDriver browser) {
        this(Allure.getLifecycle(), browser);
    }

    public AllureSimultaneousSelenide(AllureLifecycle lifecycle, SelenideDriver browser) {
        this.saveScreenshots = true;
        this.browser = browser;
        this.lifecycle = lifecycle;
    }

    public AllureSimultaneousSelenide screenshots(boolean saveScreenshots) {
        this.saveScreenshots = saveScreenshots;
        return this;
    }

    public void onEvent(LogEvent event) {
        this.lifecycle.getCurrentTestCase().ifPresent((uuid) -> {
            String stepUUID = UUID.randomUUID().toString();
            this.lifecycle.startStep(stepUUID, (new StepResult()).setName(event.toString()).setStatus(Status.PASSED));
            this.lifecycle.updateStep((stepResult) -> {
                stepResult.setStart(stepResult.getStart() - event.getDuration());
            });
            if (LogEvent.EventStatus.FAIL.equals(event.getStatus())) {

                this.lifecycle.updateStep((stepResult) -> {
                    StatusDetails details = ResultsUtils.getStatusDetails(event.getError()).orElse(new StatusDetails());
                    stepResult.setStatus(ResultsUtils.getStatus(event.getError()).orElse(Status.BROKEN));
                    stepResult.setStatusDetails(details);
                });
            }

            this.lifecycle.stopStep(stepUUID);
        });
    }

}

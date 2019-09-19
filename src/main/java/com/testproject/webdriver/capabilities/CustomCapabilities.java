package com.testproject.webdriver.capabilities;

import com.testproject.webdriver.Browser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.UnexpectedAlertBehaviour.ACCEPT;
import static org.openqa.selenium.UnexpectedAlertBehaviour.DISMISS;
import static org.openqa.selenium.remote.CapabilityType.*;

public interface CustomCapabilities {

    static CustomCapabilities forBrowser(Browser browser) {
        switch (browser) {
            case IE:
                return new IE();
            case CHROME:
                return new Chrome();
            case EDGE:
                return new Edge();
            default:
                throw new IllegalArgumentException(String.format("Not supported browser = [%s]", browser.getName()));
        }
    }

    Capabilities get();

    default Capabilities getCommonCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(UNEXPECTED_ALERT_BEHAVIOUR, ACCEPT);
        capabilities.setCapability(UNHANDLED_PROMPT_BEHAVIOUR, DISMISS);
        capabilities.setCapability(ACCEPT_INSECURE_CERTS, true);
        return capabilities;
    }

}

package com.testproject.webdriver.capabilities;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeOptions;

public class Edge implements CustomCapabilities {

    @Override
    public Capabilities get() {
        EdgeOptions chromeOptions = new EdgeOptions();

        chromeOptions.merge(getCommonCapabilities());
        return chromeOptions;
    }
}

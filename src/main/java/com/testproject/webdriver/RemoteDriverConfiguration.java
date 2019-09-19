package com.testproject.webdriver;

import com.codeborne.selenide.Configuration;
import com.testproject.utils.Config;
import com.testproject.webdriver.capabilities.CustomCapabilities;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class RemoteDriverConfiguration implements WebDriverConfiguration {

    @Override
    public void configure() {
        Configuration.remote = Config.getGridHost() + "/wd/hub";

        Capabilities capabilities = new DesiredCapabilities(
                CustomCapabilities.forBrowser(Config.getBrowser()).get());

        DesiredCapabilities remoteCapabilities = new DesiredCapabilities(capabilities);
        remoteCapabilities.setCapability("enableVNC", true);
        remoteCapabilities.setCapability("enableVideo", false);

        Configuration.browserCapabilities = remoteCapabilities;
    }
}

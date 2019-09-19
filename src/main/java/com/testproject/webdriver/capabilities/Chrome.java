package com.testproject.webdriver.capabilities;

import com.testproject.utils.Config;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

public class Chrome implements CustomCapabilities {

    @Override
    public Capabilities get() {
        ChromeOptions chromeOptions = new ChromeOptions();

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", Config.getDownloadDirectory());
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);

        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.merge(getCommonCapabilities());
        return chromeOptions;
    }
}

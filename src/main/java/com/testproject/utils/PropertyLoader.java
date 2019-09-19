package com.testproject.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.strip;

public class PropertyLoader {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);

    private static final String TYPESAFE_CONFIG_FILE = "environments.conf";
    private static final String MAIN_CONFIG_FILE = "config.properties";

    private PropertyLoader() {
    }

    static Properties loadAll() {
        Properties systemProperties = System.getProperties();
        Properties mainConfigFile = loadFromMainConfigFile();
        mainConfigFile.putAll(systemProperties);

        addEnvironments(mainConfigFile);
        addMailHost(mainConfigFile);

        System.setProperties(mainConfigFile);

        return mainConfigFile;
    }

    private static void addMailHost(Properties mainConfigFile) {
        String baseUrl = mainConfigFile.getProperty("selenide.baseUrl");
        mainConfigFile.setProperty("MAIL_HOST", baseUrl + "/mail");
    }

    private static void addEnvironments(Properties mainConfigFile) {
        if (mainConfigFile.getProperty("selenide.baseUrl") == null) {
            Config environments = ConfigFactory.load(TYPESAFE_CONFIG_FILE);
            String env = mainConfigFile.getProperty("env", "default");
            ConfigValue baseUrl = environments.getValue(String.format("environments.%s.selenide.baseUrl", env));
            ConfigValue restHost = environments.getValue(String.format("environments.%s.selenide.restHost", env));

            mainConfigFile.setProperty("selenide.baseUrl", strip(baseUrl.render(), "\""));
            mainConfigFile.setProperty("restHost", strip(restHost.render(), "\""));
        } else {
            if (mainConfigFile.getProperty("selenide.restHost") == null) {
                throw new RuntimeException("Please specify selenide.resHost URL.");
            }
        }
    }

    private static Properties loadFromMainConfigFile() {
        Properties properties = new Properties();
        URL props = ClassLoader.getSystemResource(MAIN_CONFIG_FILE);
        try {
            properties.load(props.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}

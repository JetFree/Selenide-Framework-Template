package com.testproject.utils;

import com.codeborne.selenide.SelenideDriver;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class CommonFileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CommonFileUtils.class);

    private CommonFileUtils() {
    }

    @Step
    public static void verifyDownloadDirectoryIsEmpty() {
        File directory = getDefaultDownloadDir();
        Assertions.assertThat(Objects.requireNonNull(directory.listFiles()).length).isEqualTo(0);
    }

    @Step
    public static void verifyDownloadDirectoryIsNotEmpty() {
        File directory = getDefaultDownloadDir();
        Assertions.assertThat(Objects.requireNonNull(directory.listFiles()).length).isGreaterThan(0);
    }

    @Step
    public static void cleanDefaultDownloadDir() {
        File directory = getDefaultDownloadDir();
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            LOG.info("Directory isn't clean", e);
        }
    }

    @Step
    public static void uploadFile(SelenideDriver browser, String fileName) {
        browser.$(".v-upload input[type=file]").uploadFromClasspath("importfiles/" + fileName);
    }

    private static File getDefaultDownloadDir() {
        File dir = new File(Config.getDownloadDirectory());
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    public static File getFile(String fileName) {
        return Paths.get("src", "main", "resources", "importfiles", fileName).toFile();
    }

    public static File getFileFromDownloadDir(String fileName) {
        return Arrays.stream(Objects.requireNonNull(getDefaultDownloadDir().listFiles()))
                .filter(file -> file.getName().equals(fileName))
                .findAny()
                .get();
    }

    public static String getHash(File file) {
        String hash = null;
        try {
            hash = Files.asByteSource(file).hash(Hashing.sha256()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hash;
    }
}

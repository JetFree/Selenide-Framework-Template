package com.testproject.utils;

import com.codeborne.selenide.SelenideDriver;

import java.lang.reflect.Constructor;

final public class ReflectionUtils {

    private ReflectionUtils() {

    }

    public static <T> T createPageObject(Class<T> pageObject, SelenideDriver browser) {
        try {
            Constructor<T> constructor = pageObject.getDeclaredConstructor(SelenideDriver.class);
            constructor.setAccessible(true);
            return constructor.newInstance(browser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create new instance of " + pageObject, e);
        }
    }
}

package com.testproject.api;

import com.testproject.ui.BaseTest;
import com.testproject.utils.Config;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Integrations")
@Feature("API All Tests")
public class ApiAllTests extends BaseTest {

    @Test
    @DisplayName("API_GGL-01")
    @Severity(SeverityLevel.CRITICAL)
    @Story("GET List of Specific Entity Files")
    public void verifyGetListOfAllEntityFiles() {
        ExtractableResponse<Response> response = given()
                .header("accept", "text/html")
                .get(Config.getApiUrl())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract();
        assertThat(response.header("server")).isEqualTo("Google Frontend");
    }
}

package org.master.form;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.*;
import org.master.command.form.FormCommandResource;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(FormCommandResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormCommandResourceTest {

    private static String formUUID;

    @Test
    @Order(1)
    public void testCreateForm() {
        // Step 1: Create a new screen
        String requestBody = """
                    {
                           "name": "alone_form",
                           "columns": 2,
                           "rowHeights": [
                             256, 256
                           ],
                           "primaryLanguageId": "5b26e811-9c8f-4698-927a-197560437b48",
                           "rowMaxHeights": [
                             512, 256
                           ],
                           "columnMapping": {
                             "columns": [
                               {
                                 "name": "test_input",
                                 "data_type": "String"
                               },{
                                 "name": "number_input",
                                 "data_type": "Integer"
                               }]},
                           "definition": {
                             "definition": "definition",
                             "background": "background"
                           }
                    }
                """;

        formUUID = given()
                .auth().basic("admin", "admin")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(201) // Expecting HTTP 201 Created
                .extract()
                .jsonPath()
                .get();
    }

    @Test
    @Order(2)
    public void testUpdateForm() {

        // Step 1: Create a new screen
        String requestBody = """
                    {
                           "name": "alone_form",
                           "columns": 2,
                           "rowHeights": [
                             256, 256
                           ],
                           "primaryLanguageId": "5b26e811-9c8f-4698-927a-197560437b48",
                           "rowMaxHeights": [
                             512, 256
                           ],
                           "columnMapping": {
                             "columns": [
                               {
                                 "name": "updated_test_input",
                                 "data_type": "String"
                               },{
                                 "name": "number_input",
                                 "data_type": "Integer"
                               }]},
                           "definition": {
                             "definition": "definition",
                             "background": "background"
                           }
                    }
                """;

        given()
                .auth().basic("admin", "admin")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .queryParam("form id", formUUID)
                .when()
                .put()
                .then()
                .statusCode(200); // Expecting HTTP 200 OK

    }

    @Test
    @Order(3)
    public void testPublishForm() {
        given()
                .auth().basic("admin", "admin")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("form id", formUUID)
                .when()
                .put("/publish")
                .then()
                .statusCode(200); // Expecting HTTP 200 Created
        System.out.println("Starting test: " + this.getClass().getSimpleName());
    }
}

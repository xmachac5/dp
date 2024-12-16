package org.master.screen;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.master.command.screen.ScreenCommandResource;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ScreenCommandResource.class)
public class ScreenCommandResourceTest {

    @Test
    public void testCreateScreen() {
        // Step 1: Create a new screen
        String requestBody = """
            {
                       "data": {
                         "widgets": [
                           {
                             "type": "grid",
                             "settings": {
                               "object": "list"
                             }
                           },
                           {
                             "type": "header",
                             "expression": "name"
                           }
                         ]
                       },
                       "name": "new_screen",
                       "columns": 0,
                       "rowHeights": [
                         8
                       ],
                       "primaryLanguageId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                       "url": "T",
                       "rowMaxHeights": [
                         0
                       ],
                       "locals": {
                         "variables": [
                           {
                             "name": "variable_1",
                             "data_type": "Integer",
                             "type": "input"
                           },
                           {
                             "name": "variable_2",
                             "data_type": "String",
                             "type": "local"
                           }
                         ]
                       },
                       "variableInit": {
                         "variables": [
                           {
                             "name": "variable_1",
                             "table_name": "null",
                             "where": "null"
                           },
                           {
                             "name": "variable_2",
                             "table_name": "cars",
                             "where": "id = variable_1"
                           }
                         ]
                       },
                       "variableInitMapping": {
                         "variables": [
                           {
                             "name": "variable_1",
                             "expression": "if variable_1 == null, 0, variable_1"
                           },
                           {
                             "name": "variable_2",
                             "expression": "null"
                           }
                         ]
                       },
                       "background": {
                         "background_colour": "colour",
                         "background_opacity": "opacity"
                       },
                       "title": "string"
            }
        """;

        given()
                .auth().basic("admin", "admin")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(201); // Expecting HTTP 201 Created
    }
}

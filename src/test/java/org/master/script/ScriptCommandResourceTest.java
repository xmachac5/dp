package org.master.script;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.master.command.script.ScriptCommandResource;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ScriptCommandResource.class)
public class ScriptCommandResourceTest {

    private static String scriptUUID;

    @Test
    @BeforeEach
    public void testCreateScript() {
        // Step 1: Create a new screen
        String requestBody = """
                    {
                    "variables": {
                       "variables": [
                               {
                                 "name": "input_variable",
                                 "data_type": "String",
                                 "type": "input"
                               },{
                                 "name": "output_variable",
                                 "data_type": "Integer",
                                 "type": "output"
                               }]},
                           "code": "string",
                           "name": "script_one"
                    }
                """;

        scriptUUID = given()
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
    public void testUpdateScript() {

        // Step 1: Create a new screen
        String requestBody = """
                    {
                       "variables": {
                         "variables": [
                           {
                             "name": "updated_input_variable",
                             "data_type": "String",
                             "type": "input"
                           },
                           {
                             "name": "output_variable",
                             "data_type": "Integer",
                             "type": "output"
                           }
                         ]
                       },
                       "code": "string",
                       "name": "script_one"
                     }
                """;

        given()
                .auth().basic("admin", "admin")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .queryParam("script id", scriptUUID)
                .when()
                .put()
                .then()
                .statusCode(200); // Expecting HTTP 200 Created

    }
}

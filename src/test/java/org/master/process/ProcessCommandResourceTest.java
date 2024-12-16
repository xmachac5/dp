package org.master.process;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.master.command.process.ProcessCommandResource;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ProcessCommandResource.class)
public class ProcessCommandResourceTest {

    @Test
    public void testCreateProcess() {
        // Step 1: Create a new screen
        String requestBody = """
                    {
                           "name": "new_process",
                           "variables": {
                             "variables": [
                               {
                                 "name": "input_variable",
                                 "type": "Input"
                               },{
                                 "name": "output_variable",
                                 "type": "Output"
                               },{
                                 "name": "local_variable",
                                 "type": "Local"
                               }]}
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

package org.master.process;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.master.query.process.ProcessQueryResource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ProcessQueryResource.class)
public class ProcessQueryResourceTest {

    @Test
    public void testGetProcess(){
        // Step 1: Retrieve the list of scripts
        JsonPath response = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .extract()
                .jsonPath();

        // Step 2: Get the last script ID from the list
        String lastProcessId = response.getList("id").get(response.getList("id").size() - 1).toString();

        // Step 3: Fetch details of the last screen
        given()
                .pathParam("id", lastProcessId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .body("id", is(lastProcessId))
                .body("variables.variables.size()", is(3))
                .body("variables.variables[0].name", is("input_variable")) //the changes were published so the data will be updated
                .body("variables.variables[0].type", is("Input"))
                .body("variables.variables[1].name", is("output_variable"))
                .body("variables.variables[1].type", is("Output"))
                .body("variables.variables[2].name", is("local_variable"))
                .body("variables.variables[2].type", is("Local"));
    }
}

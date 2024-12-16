package org.master.script;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.master.query.script.ScriptQueryResource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ScriptQueryResource.class)
public class ScriptQueryResourceTest {

    @Test
    public void testGetScript(){
        // Step 1: Retrieve the list of scripts
        JsonPath response = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .extract()
                .jsonPath();

        // Step 2: Get the last script ID from the list
        String lastScriptId = response.getList("id").get(response.getList("id").size() - 1).toString();

        // Step 3: Fetch details of the last screen
        given()
                .pathParam("id", lastScriptId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .body("id", is(lastScriptId))
                .body("structure.variables.size()", is(2))
                .body("structure.variables[0].name", is("input_variable")) //the changes were not published so the data will not be updated
                .body("structure.variables[0].data_type", is("String"))
                .body("structure.variables[0].type", is("input"))
                .body("structure.variables[1].name", is("output_variable"))
                .body("structure.variables[1].data_type", is("Integer"))
                .body("structure.variables[1].type", is("output"));
    }
}

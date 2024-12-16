package org.master.screen;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.master.query.screen.ScreenQueryResource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ScreenQueryResource.class)
public class ScreenQueryResourceTest {

    @Test
    public void testGetScreen(){
        // Step 1: Retrieve the list of screens
        JsonPath response = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .extract()
                .jsonPath();

        // Step 2: Get the last screen ID from the list
        String lastScreenId = response.getList("id").get(response.getList("id").size() - 1).toString();

        // Step 3: Fetch details of the last screen
        given()
                .pathParam("id", lastScreenId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .body("id", is(lastScreenId))
                .body("rowHeights", is(List.of(8)))
                .body("locals.variables.size()", is(2))
                .body("locals.variables[0].name", is("variable_1"))
                .body("locals.variables[0].data_type", is("Integer"))
                .body("locals.variables[0].type", is("input"))
                .body("locals.variables[1].name", is("variable_2"))
                .body("locals.variables[1].data_type", is("String"))
                .body("locals.variables[1].type", is("local"));
    }
}

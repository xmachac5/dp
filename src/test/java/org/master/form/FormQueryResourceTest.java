package org.master.form;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.master.query.form.FormQueryResource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(FormQueryResource.class)
public class FormQueryResourceTest {

    @Test
    public void testGetSForm(){
        // Step 1: Retrieve the list of scripts
        JsonPath response = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .extract()
                .jsonPath();

        // Step 2: Get the last script ID from the list
        String lastFormId = response.getList("id").get(response.getList("id").size() - 1).toString();

        // Step 3: Fetch details of the last screen
        given()
                .pathParam("id", lastFormId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200) // Expecting HTTP 200 OK
                .body("id", is(lastFormId))
                .body("columnMapping.columns.size()", is(2))
                .body("columnMapping.columns[0].name", is("updated_test_input")) //the changes were published so the data will be updated
                .body("columnMapping.columns[0].data_type", is("String"))
                .body("columnMapping.columns[1].name", is("number_input"))
                .body("columnMapping.columns[1].data_type", is("Integer"));
    }
}

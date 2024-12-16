package org.master.data_object;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.master.query.dataObject.DataObjectQueryResource;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(DataObjectQueryResource.class)
public class DataObjectQueryResourceTest {

    @Test
    public void testGetDO() {
        // Step 1: Retrieve the list of data objects, should be empty
        given()
                .when()
                .get("/list")
                .then()
                .statusCode(200); // Expecting HTTP 200 OK
    }
}

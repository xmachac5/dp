package org.master.data_object;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.master.command.dataObject.DataObjectCommandResource;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(DataObjectCommandResource.class)
public class DataObjectCommandResourceTest {

    private static String dataObjectUUID;

    @Test
    @Order(1)
    public void testCreateDO() {
        // Step 1: Create a new screen
        String requestBody = """
                    {
                           "name": "new_data_object",
                           "description": "new_data_object",
                           "trackChanges": true,
                           "softDelete": true,
                           "columns": {
                             "columns": [
                               {
                                 "name": "text_input",
                                 "data_type": "String",
                                 "primaryKey": "False",
                                 "isFk": "False",
                                 "description": "Text"
                               },{
                                 "name": "id",
                                 "data_type": "Integer",
                                 "primaryKey": "True",
                                 "isFk": "False",
                                 "description": "Primary Key"
                               },{
                                 "name": "other_table",
                                 "data_type": "Integer",
                                 "primaryKey": "False",
                                 "isFk": "True",
                                 "description": "Other table id"
                               }]}
                    }
                """;

        dataObjectUUID = given()
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
    public void testDeleteDO() {
        given()
                .auth().basic("admin", "admin")
                .queryParam("data object id", dataObjectUUID)
                .when()
                .delete()
                .then()
                .statusCode(200); // Expecting HTTP 200 OK
    }
}

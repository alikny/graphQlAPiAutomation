package com.aod.utilities;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class Utils {
    public void postQueries(String level) {
        String query= "{\"query\":\"query MyQuery {verticesByLevel(level: "+level+", hierarchy: pph) {id,level,name}}\"}";
        Response response = given()
                .contentType("application/json")
                .header("x-api-key",System.getenv("apikey"))
                .body(query)
                .when().log().all()
                .post("/graphql");
    }

    public void postQueries2(String id) {
        String query= "{\"query\":\"query MyQuery {vertex(id: \\\""+id+"\\\") {id,hierarchy,legacy_id,level,name}}\"}";

        String auth ="eyJ0eXAi";
        String bearer= "Bearer" + "eyJ0eXAi";
        Response response = given()
                .contentType("application/json")
                .header("authorization",auth)
                .header("Bearer",bearer )
                .body(query)
                .when().log().all()
                .post("/graphql");
    }
}

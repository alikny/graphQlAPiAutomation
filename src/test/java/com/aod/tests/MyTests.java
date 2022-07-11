package com.aod.tests;

import com.aod.data.Querries;
import com.aod.data.Ship;
import com.aod.data.User;
import com.aod.pojos.GraphQLQuery;
import com.aod.pojos.QueryLimits;
import com.aod.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MyTests {
    String Endpoint;

    @Before
    public void before(){
        Endpoint= ConfigurationReader.get("EndPoint");
    }

    @Test
    public void getCompanyData_checkCeo_shouldBeElonMusk() {

        GraphQLQuery qlquery=new GraphQLQuery();
        qlquery.setQuery(Querries.query1);

        given().
                    contentType(ContentType.JSON).
                    body(qlquery).
//                and().log().body().
                when().
                    post(Endpoint).
                then().
                    assertThat().
                    statusCode(200).
                and().
                    body("data.company.ceo", equalTo("Elon Musk"))
//                .and().log().all()
                ;
    }


    @Test
    public void testWithParameterPOJOClass(){
        GraphQLQuery query=new GraphQLQuery();
        query.setQuery(Querries.query2);

        // set the limit variable via POJO class
        QueryLimits queryLimits =new QueryLimits();
        queryLimits.setLimit(10);
        query.setVariables(queryLimits);

        given().
                contentType(ContentType.JSON).
                body(query).
//                and().log().body().
            when().
                post(Endpoint).
            then().
                assertThat().statusCode(200).
                contentType(ContentType.JSON).
//            and().log().all().
                and().assertThat().body("data.launches[0].mission_name",equalTo("Thaicom 6"))
                                    .body("data.launches[0].mission_name",is(notNullValue()));

    }

    @Test
    public void testWithParameterJSONObject() throws JSONException {
        GraphQLQuery query=new GraphQLQuery();
        query.setQuery(Querries.query2);

        // set the limit variable via JSON object
        JSONObject variables=new JSONObject();
        variables.put("limit",10);
        query.setVariables(variables.toString());

        given().
                contentType(ContentType.JSON).
                body(query).
//                and().log().body().
                when().
                post(Endpoint).
                then().
                assertThat().statusCode(200).
                contentType(ContentType.JSON).
//                and().log().all().
                and().assertThat().body("data.launches[0].mission_name",equalTo("Thaicom 6"))
                .body("data.launches[0].mission_name",is(notNullValue()));

    }

    @Test
    public void testMutation(){
        GraphQLQuery gQuery=new GraphQLQuery();
        gQuery.setQuery(Querries.insertUser);

        //used pojo to generate a new user
        User user=new User("Ali","Alis rocket");
        gQuery.setVariables(user);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(gQuery)
//                .and().log().body()
                .when().post(Endpoint)
                .then()
//                .and().log().all()
                .assertThat().statusCode(200)
                .body("data.insert_users.returning[0].name",is(notNullValue()),
                        "data.insert_users.returning[0].id",equalTo(user.getId().toString().replace("<","").replace(">","")),
                        "data.insert_users.returning[0].name",equalTo(user.getName()),
                        "data.insert_users.returning[0].rocket",equalTo(user.getRocket()))
        ;

    }

//    @Tag("regression")
    @Test
    public void getAShipTest(){
        GraphQLQuery qlQuery=new GraphQLQuery();
        qlQuery.setQuery(Querries.querytoGetAShip);

//        JSONObject variables=new JSONObject();
//        variables.put("id","AMERICANCHAMPION");
//        qlQuery.setVariables(variables.toString());

        Ship ship =new Ship();
        ship.setId("AMERICANCHAMPION");
        qlQuery.setVariables(ship);

        given().contentType(ContentType.JSON)
                .body(qlQuery)
//                .and().log().body()
                .when()
                    .post(Endpoint)
                .then()
                    .assertThat()
                    .statusCode(200)
//                .and().log().body()
                .and().assertThat().body("data.ship.id",equalTo(ship.getId()))
                .assertThat().body("data.ship.name",is(notNullValue()))
                .header("Content-Type","application/json; charset=utf-8")
//                .header("Date",new Date())
        ;

    }
}

package com.aod.tests;

import com.aod.data.Querries;
import com.aod.data.User;
import com.aod.pojos.GraphQLQuery;
import com.aod.utilities.ConfigurationReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserTests {

    GraphQLQuery graphQLQuery=new GraphQLQuery();
    Faker faker=new Faker();
    User newUser=new User(faker.name().firstName(),faker.name().username());

    @Before
    public void before(){
        baseURI= ConfigurationReader.get("EndPoint");
    }

    //insert a user
    @Test
    public void insertAUser(){
        graphQLQuery.setQuery(Querries.insertUser);
        graphQLQuery.setVariables(newUser);

        ValidatableResponse validatableResponse = given().contentType(ContentType.JSON)
                .body(graphQLQuery)
//                .and().log().all()
                .when().post().then()
                .assertThat()
                .statusCode(200)
                .body("data.insert_users.returning[0].name", equalTo(newUser.getName()))
                .body("data.insert_users.returning[0].rocket", equalTo(newUser.getRocket()))
//                .and().log().all()
                ;

        String id = validatableResponse.extract().response().jsonPath().getString("data.insert_users.returning[0].id");
        String name = validatableResponse.extract().response().jsonPath().getString("data.insert_users.returning[0].name");
        String rocket = validatableResponse.extract().response().jsonPath().getString("data.insert_users.returning[0].rocket");


        //get the user created
        graphQLQuery.setQuery(Querries.getUserByName);
        graphQLQuery.setVariables(new JSONObject().put("name",name).put("rocket",rocket).toString());
        given().contentType(ContentType.JSON)
                .body(graphQLQuery)
//                .and().log().body()
                .when().post().then()
                .assertThat()
                    .statusCode(200)
                    .body("data.users[0].id",equalTo(id))
                    .body("data.users[0].name",equalTo(name))
                    .body("data.users[0].rocket",equalTo(rocket));
    }

//    update a user
    @Test
    public void updateUser(){
        graphQLQuery.setQuery(Querries.getAUsers);
        graphQLQuery.setVariables(new JSONObject().put("limit",1).toString());

        ValidatableResponse validatableResponse = given().contentType(ContentType.JSON)
                .body(graphQLQuery)
                .when().post().then().assertThat().statusCode(200)
//                .and().log().body()
                ;

        String nameOfTheUser=validatableResponse.extract().response().jsonPath().getString("data.users[0].name");
        String newName=faker.name().firstName();


        graphQLQuery.setQuery(Querries.updateUserByName);
        graphQLQuery.setVariables(new JSONObject().put("name",nameOfTheUser).put("newName",newName).toString());

        given().contentType(ContentType.JSON).body(graphQLQuery)
//                .and().log().body()
                .when().post().then().assertThat().statusCode(200)
//                .and().log().body()
                .assertThat().body("data.update_users.returning[0].name",equalTo(newName));
//                .and().log().body();

    }
}

package com.aod.data;

import org.codehaus.groovy.runtime.StreamGroovyMethods;

public interface Querries {
    String query1="{ company { name ceo coo } }";
    String query2="query getLaunches($limit: Int!){\n" +
            "  launches(limit:$limit){\n" +
            "    mission_name\n" +
            "  }\n" +
            "}";
    String insertUser ="mutation insert_users($id: uuid!, $name: String!, $rocket: String!) {\n" +
            "  insert_users(objects: {id: $id, name: $name, rocket: $rocket}) {\n" +
            "    returning {\n" +
            "      id\n" +
            "      name\n" +
            "      rocket\n" +
            "    }\n" +
            "  }\n" +
            "}";
    String querytoGetAShip="query getAShip($id:ID!){\n" +
            "  ship(id: $id) {\n" +
            "    id\n" +
            "    name\n" +
            "    class\n" +
            "    abs\n" +
            "  }\n" +
            "}\n";

    String getAUsers="query getUsers($limit: Int!) {\n" +
            "  users(limit: $limit) {\n" +
            "    id\n" +
            "    name\n" +
            "    rocket\n" +
            "  }\n" +
            "}";
    String getUserByName="query getUsers($name:String!, $rocket:String) {\n" +
            "  users(where: {name: {_like: $name},rocket: {_eq: $rocket}}) {\n" +
            "    id\n" +
            "    name\n" +
            "    rocket\n" +
            "    twitter\n" +
            "  }\n" +
            "}\n";
    String updateUserByName="mutation updateUser($name:String!, $newName:String!){\n" +
            "  update_users(where: {name: {_eq: $name}}, _set: {name: $newName}) {\n" +
            "    affected_rows\n" +
            "    returning {\n" +
            "      id\n" +
            "      name\n" +
            "      rocket\n" +
            "      twitter\n" +
            "    }\n" +
            "  }\n" +
            "}";
}

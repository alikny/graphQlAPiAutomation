package com.aod.data;

import com.github.javafaker.Faker;
import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String name;
    private String rocket;
    private String twitter;

    public User( String name, String rocket){
        this.id=UUID.randomUUID();
        this.name=name;
        this.rocket=rocket;
        this.twitter=name+"@twitter.com";
    }

    public User( String id, String name, String rocket){
        this.id= UUID.fromString(id);
        this.name=name;
        this.rocket=rocket;
        this.twitter=name+"@twitter.com";
    }
}

package com.back.bpo.labs.ticketing.platform.user.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * @author Daniel Camilo
 */
public class User extends PanacheMongoEntity {

    @BsonProperty("userId")
    public String userId;
    @BsonProperty("name")
    public String name;
    @BsonProperty("email")
    public String email;
    @BsonProperty("password")
    public String password;
}


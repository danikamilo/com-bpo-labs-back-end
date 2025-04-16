package com.back.bpo.labs.ticketing.platform.event.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Date;


/**
 * @author Daniel Camilo
 */
public class Event extends PanacheMongoEntity {


    @BsonProperty("id")
    public Long id;
    @BsonProperty("name")
    public String name;
    @BsonProperty("location")
    public String location;
    @BsonProperty("date")
    public Date date;

}
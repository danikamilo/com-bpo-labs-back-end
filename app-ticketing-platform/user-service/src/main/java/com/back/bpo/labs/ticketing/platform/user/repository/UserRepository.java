package com.back.bpo.labs.ticketing.platform.user.repository;

import com.back.bpo.labs.ticketing.platform.user.model.User;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {
}
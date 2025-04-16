package com.back.bpo.labs.ticketing.platform.user.service;


import com.back.bpo.labs.ticketing.platform.user.model.User;

import java.util.List;

/**
 * @author Daniel Camilo
 */
public interface IUserService {

    /**
     *
     * @return
     */
    public List<User> listAllUsers();

    /**
     *
     * @param user
     */
    public void createUser(User user);

    /**
     *
     * @param id
     * @return
     */
    public User findById(String id);
}

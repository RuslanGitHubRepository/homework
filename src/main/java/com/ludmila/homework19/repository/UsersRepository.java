package com.ludmila.homework19.repository;

import com.ludmila.homework19.models.User;

import java.util.List;
/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */
public interface UsersRepository {
    List<User> findAll();
    void save(User user);
    List<User> findAllByAge(int age);
    List<User> findAllByIsWorkerIsTrue();
}
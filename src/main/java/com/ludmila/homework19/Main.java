package com.ludmila.homework19;

import com.ludmila.homework19.application.Application;
import com.ludmila.homework19.application.ApplicationContext;
import com.ludmila.homework19.models.User;
import com.ludmila.homework19.repository.UsersRepository;
import com.ludmila.homework19.repository.UsersRepositoryFile;

import java.util.HashMap;
import java.util.List;
/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */
public class Main {
    //FIXME Ludmila Kondratyeva: Для работы программы необходимо добавить в classpath все библиотеки из ресурсов проекта
    public static void main(String[] args) {
        try {
            ApplicationContext context = Application.run("com.ludmila.homework19", new HashMap<>());
            UsersRepository usersRepository = context.getObject(UsersRepositoryFile.class);
            List<User> users = usersRepository.findAll();
            for (User user : users) {
                System.out.println(user);
            }
            User user = User.newBuilder()
                    .setName("Игорь")
                    .setAge(33)
                    .setIsWorker(true)
                    .build();
            usersRepository.save(user);
            System.out.println("Найдем всех людей с возрастом 25 лет:");
            List<User> findByAge = usersRepository.findAllByAge(25);
            findByAge.forEach(System.out::println);
            System.out.println("Найдем всех людей, которые являются работниками:");
            List<User> allByIsWorkerIsTrue = usersRepository.findAllByIsWorkerIsTrue();
            allByIsWorkerIsTrue.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
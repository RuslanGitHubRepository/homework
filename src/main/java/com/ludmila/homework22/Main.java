package com.ludmila.homework22;

import com.ludmila.homework22.application.Application;
import com.ludmila.homework22.application.ApplicationContext;
import com.ludmila.homework22.models.User;
import com.ludmila.homework22.repository.UsersRepository;
import com.ludmila.homework22.repository.UsersRepositoryFile;

import java.nio.file.Path;
import java.nio.file.Paths;
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
            ApplicationContext context = Application.run("com.ludmila.homework22", new HashMap<>());
            UsersRepository usersRepository = context.getObject(UsersRepositoryFile.class);
            List<User> users = usersRepository.findAll();
            for (User user : users) {
                System.out.println(user);
            }
            User user = User.newBuilder()
                    .setId(10L)
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
            System.out.println("Найдем человека с идентификатором 3: ");
            User userById = usersRepository.findById(3L);
            System.out.println(userById);
            System.out.println("Обновим запись в базе человека с идентификатором 3: ");
            userById = User.newBuilder()
                    .setId(userById.getId())
                    .setAge(20)
                    .setName("Оксана")
                    .setIsWorker(true)
                    .build();
            usersRepository.updateUser(userById);
            userById = usersRepository.findById(3L);
            System.out.println("Имя человека с идентификатором 3 обновилось корректно.");
            System.out.println(userById.getName());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

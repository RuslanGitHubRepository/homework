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
            UsersRepository usersRepository = null;
            ApplicationContext context = Application.run("com.ludmila.homework19", new HashMap<>());
            usersRepository = context.getObject(UsersRepositoryFile.class);
            List<User> users = usersRepository.findAll();
            for (User user : users) {
                System.out.println(user.getAge() + " " + user.getName() + " " + user.isWorker());
            }
            User user = new User("Игорь", 33, true);
            usersRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

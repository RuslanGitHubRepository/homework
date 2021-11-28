package com.ludmila.homework19.repository;

import com.ludmila.homework19.application.annotation.Bean;
import com.ludmila.homework19.application.injects.InjectProperty;
import com.ludmila.homework19.models.User;

import javax.print.attribute.standard.MediaSize;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */
@Bean
@FileRepository(isResourcesFile = true)
public class UsersRepositoryFile implements UsersRepository {
    private enum Field {
        NAME(1),
        AGE(2),
        IS_WORKER(3);
        private Integer value;

        Field(Integer value) {
            this.value = value;
        }
    }

    @InjectProperty
    private File fileName;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        // объявили переменные для доступа
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            // создали читалку на основе файла
            reader = new FileReader(fileName);
            // создали буферизированную читалку
            bufferedReader = new BufferedReader(reader);
            // прочитали строку
            String line = bufferedReader.readLine();
            // пока к нам не пришла "нулевая строка"
            while (line != null) {
                // разбиваем ее по |
                String[] parts = line.split("\\|");
                // берем имя
                String name = parts[0];
                // берем возраст
                int age = Integer.parseInt(parts[1]);
                // берем статус о работе
                boolean isWorker = Boolean.parseBoolean(parts[2]);
                // создаем нового человека
                User newUser = User.newBuilder()
                        .setName(name)
                        .setAge(age)
                        .setIsWorker(isWorker)
                        .build();
                // добавляем его в список
                users.add(newUser);
                // считываем новую строку
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            // этот блок выполнится точно
            if (reader != null) {
                try {
                    // пытаемся закрыть ресурсы
                    reader.close();
                } catch (IOException ignore) {}
            }
            if (bufferedReader != null) {
                try {
                    // пытаемся закрыть ресурсы
                    bufferedReader.close();
                } catch (IOException ignore) {}
            }
        }

        return users;
    }

    @Override
    public void save(User user) {
        Writer writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(user.getName() + "|" + user.getAge() + "|" + user.isWorker());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore) {}
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException ignore) {}
            }
        }
    }

    @Override
    public List<User> findAllByAge(int age) {
        return getUsersByParameter(age, Field.AGE);
    }

    @Override
    public List<User> findAllByIsWorkerIsTrue() {
        return getUsersByParameter(true, Field.IS_WORKER);
    }

    private <T extends java.io.Serializable & Comparable<T>> List<User> getUsersByParameter(T parameter, Field field) {
        List<User> users = new ArrayList<>();
        try(InputStream fileInputStream = new FileInputStream(fileName)) {
            Predicate<MatchResult> condition = createCondition(parameter, field);
            Scanner scanner = new Scanner(fileInputStream);
            scanner = scanner.useDelimiter(Pattern.compile("\n"));
            while (scanner.hasNext(Pattern.compile("(\\w+)\\|(\\d{1,2})\\|(\\w+)", Pattern.UNICODE_CHARACTER_CLASS))) {
                MatchResult match = scanner.match();
                if(condition.test(match)) {
                    users.add(User.newBuilder()
                            .setName(match.group(Field.NAME.value))
                            .setAge(Integer.parseInt(match.group(Field.AGE.value), 10))
                            .setIsWorker(Boolean.parseBoolean(match.group(Field.IS_WORKER.value)))
                            .build());
                }
                scanner.next();
            }
        } catch (IOException exception) {
            return Collections.emptyList();
        }
        return users;
    }
    private <T extends java.io.Serializable & Comparable<T>> Predicate<MatchResult> createCondition(T parameter, Field field) {
        return matchResult -> parameter.toString().equals(matchResult.group(field.value));
    }
}
package com.ludmila.homework22.repository;

import com.ludmila.homework22.application.annotation.Bean;
import com.ludmila.homework22.application.injects.InjectProperty;
import com.ludmila.homework22.models.User;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
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
        ID(1),
        NAME(2),
        AGE(3),
        IS_WORKER(4);
        private Integer value;

        Field(Integer value) {
            this.value = value;
        }
    }

    private final int LF = 0xA;

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
                //берем идентификатор
                Long id = Long.parseLong(parts[0]);
                // берем имя
                String name = parts[1];
                // берем возраст
                int age = Integer.parseInt(parts[2]);
                // берем статус о работе
                boolean isWorker = Boolean.parseBoolean(parts[3]);
                // создаем нового человека
                User newUser = User.newBuilder()
                        .setId(id)
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

            bufferedWriter.write(user.getId() + "|" + user.getName() + "|" + user.getAge() + "|" + user.isWorker());
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

    @Override
    public User findById(Long id) {
        List<User> usersById = getUsersByParameter(id, Field.ID);
        if(usersById.isEmpty()) {
            return null;
        }
        return usersById.get(0);
    }

    @Override
    public boolean updateUser(User user) throws IOException {
        /*если размер новых данных равен размеру обновляемых, то файл НЕ переписывается целиком*/
        ByteBuffer updateBuffer = null;
        try (FileChannel fileChannel = FileChannel.open(fileName.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.SYNC)) {
            ByteBuffer userBuffer = ByteBuffer.wrap(user.getStringVersion().getBytes(StandardCharsets.UTF_8));
            ByteBuffer id = ByteBuffer.wrap(user.getId().toString().getBytes(StandardCharsets.UTF_8));
            MappedByteBuffer fileBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());
            while (true) {
                ByteBuffer nextToken = nextToken(fileBuffer);
                int position = nextToken.position();
                int limit = nextToken.limit();
                nextToken.limit(position + id.remaining());
                if(findRecordById(nextToken, id)) {
                    nextToken.limit(limit);
                    updateBuffer = replaceRecord(nextToken, userBuffer);
                    if(Objects.isNull(updateBuffer)) {
                       return true;
                    }
                    break;
                }
                if(limit == nextToken.capacity()) {
                    break;
                }
                fileBuffer.limit(nextToken.capacity());
                fileBuffer.position(limit + 1);
            }
        }
        /*Если обновляемые данные отличаются от новых данных, файл переписывается*/
        if(Objects.nonNull(updateBuffer)) {
            Files.deleteIfExists(fileName.toPath());
            try (FileChannel fileChannel = FileChannel.open(fileName.toPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.SYNC)) {
                MappedByteBuffer fileBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, updateBuffer.capacity());
                updateBuffer.clear();
                fileBuffer.put(updateBuffer);
            }
        }
        return false;
    }

    private ByteBuffer nextToken(ByteBuffer buffer) {
        buffer.mark();
        while (buffer.get() != LF) {
            if(buffer.position() == buffer.capacity()) {
                buffer.limit(buffer.capacity());
                buffer.reset();
                return buffer;
            }
        }
        buffer.limit(buffer.position()-1);
        buffer.reset();
        return buffer;
    }
    private boolean findRecordById(ByteBuffer nextToken, ByteBuffer id) {
        if(id.remaining() > nextToken.remaining()) {
            return false;
        }
        nextToken.mark();
        boolean status = false;
        for (int i = nextToken.position(); i < nextToken.limit(); i++) {
            nextToken.position(i);
            if(nextToken.remaining() < id.remaining()) {
                break;
            }
            if(findNextByte(nextToken, id) == id.capacity()) {
                status = true;
                break;
            }
            id.clear();
        }
        id.clear();
        nextToken.reset();
        return status;
    }
    private int findNextByte(ByteBuffer source, ByteBuffer unit) {
        while(source.get() == unit.get()) {
            if(!unit.hasRemaining()) {
                return unit.capacity();
            }
        }
        return -1;
    }
    private ByteBuffer replaceRecord(ByteBuffer source, ByteBuffer newRecord) {
        if(source.remaining() == newRecord.remaining()) {
            source.put(newRecord);
            return null;
        }
        int limit = source.limit();
        ByteBuffer newBuffer = ByteBuffer.allocate(newRecord.remaining() - source.remaining() + source.capacity());
        source.flip();
        newBuffer.put(source);
        newBuffer.put(newRecord);
        source.clear();
        source.position(limit);
        newBuffer.put(source);
        return newBuffer;
    }
    private <T extends java.io.Serializable & Comparable<T>> List<User> getUsersByParameter(T parameter, Field field) {
        List<User> users = new ArrayList<>();
        try(InputStream fileInputStream = new FileInputStream(fileName)) {
            Predicate<MatchResult> condition = createCondition(parameter, field);
            Scanner scanner = new Scanner(fileInputStream);
            scanner = scanner.useDelimiter(Pattern.compile("\n"));
            while (scanner.hasNext(Pattern.compile("(\\d+)\\|(\\w+)\\|(\\d{1,2})\\|(\\w+)\r?", Pattern.UNICODE_CHARACTER_CLASS))) {
                MatchResult match = scanner.match();
                if(condition.test(match)) {
                    users.add(User.newBuilder()
                            .setId(Long.parseLong(match.group(Field.ID.value)))
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
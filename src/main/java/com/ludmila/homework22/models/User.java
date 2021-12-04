package com.ludmila.homework22.models;

import java.util.StringJoiner;

/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */
public class User {
    private final Long id;
    private final String name;
    private final Integer age;
    private final Boolean isWorker;

    private User(Builder builder) {
        name = builder.name;
        age = builder.age;
        isWorker = builder.isWorker;
        id = builder.id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public boolean isWorker() {
        return isWorker;
    }
    public Long getId() {
        return id;
    }

    /**
     * {@code User} builder static inner class.
     */
    public static final class Builder {
        private Long id;
        private String name;
        private int age;
        private boolean isWorker;

        private Builder() {
        }

        /**
         * Sets the {@code id} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param id the {@code id} to set
         * @return a reference to this Builder
         */
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the {@code name} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param name the {@code name} to set
         * @return a reference to this Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the {@code age} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param age the {@code age} to set
         * @return a reference to this Builder
         */
        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        /**
         * Sets the {@code isWorker} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param isWorker the {@code isWorker} to set
         * @return a reference to this Builder
         */
        public Builder setIsWorker(boolean isWorker) {
            this.isWorker = isWorker;
            return this;
        }

        /**
         * Returns a {@code User} built from the parameters previously set.
         *
         * @return a {@code User} built with parameters of this {@code User.Builder}
         */
        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("isWorker=" + isWorker)
                .toString();
    }

    public String getStringVersion() {
        StringJoiner stringJoiner = new StringJoiner("|");
        return stringJoiner.add(id.toString())
                .add(name)
                .add(age.toString())
                .add(isWorker.toString())
                .toString();
    }
}
package model;

import java.util.Objects;
import java.util.StringJoiner;

public class Car {
    private String number;
    private String model;
    private String color;
    private Integer mileage;
    private Integer cost;

    private Car(Builder builder) {
        number = builder.number;
        model = builder.model;
        color = builder.color;
        mileage = builder.mileage;
        cost = builder.cost;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Integer getCost() {
        return cost;
    }

    /**
     * {@code Car} builder static inner class.
     */
    public static final class Builder {
        private String number;
        private String model;
        private String color;
        private Integer mileage;
        private Integer cost;

        private Builder() {
        }

        /**
         * Sets the {@code number} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param number the {@code number} to set
         * @return a reference to this Builder
         */
        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        /**
         * Sets the {@code model} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param model the {@code model} to set
         * @return a reference to this Builder
         */
        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the {@code color} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param color the {@code color} to set
         * @return a reference to this Builder
         */
        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        /**
         * Sets the {@code mileage} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param mileage the {@code mileage} to set
         * @return a reference to this Builder
         */
        public Builder setMileage(Integer mileage) {
            this.mileage = mileage;
            return this;
        }

        /**
         * Sets the {@code cost} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param cost the {@code cost} to set
         * @return a reference to this Builder
         */
        public Builder setCost(Integer cost) {
            this.cost = cost;
            return this;
        }

        /**
         * Returns a {@code Car} built from the parameters previously set.
         *
         * @return a {@code Car} built with parameters of this {@code Car.Builder}
         */
        public Car build() {
            return new Car(this);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]\n")
                .add("number='" + number + "'")
                .add("model='" + model + "'")
                .add("color='" + color + "'")
                .add("mileage=" + mileage)
                .add("cost=" + cost)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return number.equals(car.number) && model.equals(car.model) && color.equals(car.color) && mileage.equals(car.mileage) && cost.equals(car.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, model, color, mileage, cost);
    }
}

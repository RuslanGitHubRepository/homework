package models;

import java.util.Objects;

public class Coordinates {
    private int x;
    private int y;

    private Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static CoordinatesBuilder getBuilder() {
        return new CoordinatesBuilder();
    }

    public static final class CoordinatesBuilder {
        private int x;
        private int y;

        private CoordinatesBuilder() {
        }

        public static CoordinatesBuilder aCoordinates() {
            return new CoordinatesBuilder();
        }

        public CoordinatesBuilder withX(int x) {
            this.x = x;
            return this;
        }

        public CoordinatesBuilder withY(int y) {
            this.y = y;
            return this;
        }

        public Coordinates build() {
            return new Coordinates(x, y);
        }
    }
}

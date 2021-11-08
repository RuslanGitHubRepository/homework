package models.figures;

import models.Coordinates;
import models.resolvers.Moveable;

public class Circle extends Ellipse implements Moveable {
    public Circle(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getPerimeter() {
        return super.getPerimeter();
    }

    @Override
    public void move(Coordinates newCoordinate) {
        updatePosition(newCoordinate);
    }
}

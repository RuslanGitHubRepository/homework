package models.figures;

import models.Coordinates;
import models.resolvers.Moveable;

public class Square extends Rectangle implements Moveable {
    public Square(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getPerimeter() {
        return super.getPerimeter();
    }

    @Override
    public void move(int newX, int newY) {
        updateXPosition(newX);
        updateYPosition(newY);
    }
}

package models.figures;

import models.Coordinates;

public class Rectangle extends Figure {

    public Rectangle(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getPerimeter() {
        return 0;
    }
}

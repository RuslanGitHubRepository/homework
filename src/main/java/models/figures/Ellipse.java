package models.figures;

import models.Coordinates;

public class Ellipse extends Figure {
    public Ellipse(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public int getPerimeter() {
        return 0;
    }
}

package models.figures;

import models.Coordinates;

public abstract class Figure implements IFigure {
    private Coordinates coordinates;

    public Figure(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    void updateXPosition(int x) {
        coordinates.setX(x);
    }

    void updateYPosition(int y) {
        coordinates.setY(y);
    }
}

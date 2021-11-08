package models.figures;

import models.Coordinates;

public abstract class Figure implements IFigure {
    private Coordinates coordinates;

    public Figure(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    void updatePosition(Coordinates newCoordinate) {
        coordinates = newCoordinate;
    }
}

package factory;

import models.Coordinates;
import models.ModelType;
import models.figures.*;
import models.resolvers.Moveable;

public class FigureFactory implements IFigureFactory {
    @Override
    public <T extends Figure & Moveable> T createMoveAbleFigure(ModelType modelType) {
        switch (modelType) {
            case CIRCLE:
                return (T) new Circle(Coordinates.getBuilder()
                        .withX(0)
                        .withY(0)
                        .build()
                );
            case SQUARE:
                return (T) new Square(Coordinates.getBuilder()
                        .withX(0)
                        .withY(0)
                        .build());
            default:
                return null;
        }
    }

    public Figure createAnyFigure(ModelType modelType) {
        switch (modelType) {
            case CIRCLE:
                return new Circle(Coordinates.getBuilder()
                        .withX(0)
                        .withY(0)
                        .build()
                );
            case SQUARE:
                return new Square(Coordinates.getBuilder()
                        .withX(0)
                        .withY(0)
                        .build());
            case ELLLIPSE:
                return new Ellipse(Coordinates.getBuilder()
                        .withX(0)
                        .withY(0)
                        .build());
            case RECTANGLE:
                return new Rectangle(Coordinates.getBuilder()
                        .withX(0)
                        .withY(0)
                        .build());
            default:
                return null;
        }
    }
}

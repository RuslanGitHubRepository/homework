package engine;

import factory.FigureFactory;
import factory.IFigureFactory;
import logger.ILogger;
import logger.Logger;
import models.Coordinates;
import models.ModelType;
import models.resolvers.Moveable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        final Coordinates newCoordinate = Coordinates.getBuilder()
                .withX(new Random().nextInt(100))
                .withY(new Random().nextInt(100))
                .build();
        IFigureFactory figureFactory = new FigureFactory();
        ILogger logger = Logger.getInstance();

        Moveable[] moveableFigureArray = new Moveable[] {
                figureFactory.createMoveAbleFigure(ModelType.CIRCLE),
                figureFactory.createMoveAbleFigure(ModelType.CIRCLE),
                figureFactory.createMoveAbleFigure(ModelType.CIRCLE),
                figureFactory.createMoveAbleFigure(ModelType.SQUARE),
                figureFactory.createMoveAbleFigure(ModelType.SQUARE),
                figureFactory.createMoveAbleFigure(ModelType.SQUARE),
                figureFactory.createMoveAbleFigure(ModelType.RECTANGLE),
                figureFactory.createMoveAbleFigure(ModelType.RECTANGLE),
                figureFactory.createMoveAbleFigure(ModelType.RECTANGLE)};

        Arrays.stream(moveableFigureArray)
                .filter(Objects::nonNull)
                .forEach(figure -> figure.move(newCoordinate));
        logger.log("All movable figures shift to new position");
    }
}

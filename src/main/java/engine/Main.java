package engine;

import factory.FigureFactory;
import logger.ILogger;
import logger.Logger;
import models.ModelType;
import models.resolvers.Moveable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int newXcoordinate = new Random().nextInt(100);
        final int newYcoordinate = new Random().nextInt(100);
        FigureFactory figureFactory = new FigureFactory();
        ILogger logger = Logger.getInstance();

        Moveable[] moveableFigureArray = new Moveable[] {
                figureFactory.createMoveAbleFigure(ModelType.CIRCLE),
                figureFactory.createMoveAbleFigure(ModelType.CIRCLE),
                figureFactory.createMoveAbleFigure(ModelType.CIRCLE),
                figureFactory.createMoveAbleFigure(ModelType.SQUARE),
                figureFactory.createMoveAbleFigure(ModelType.SQUARE),
                figureFactory.createMoveAbleFigure(ModelType.SQUARE)};

        Arrays.stream(moveableFigureArray).forEach(figure -> figure.move(newXcoordinate, newYcoordinate));
        logger.log("All movable figures shift to new position");
    }
}

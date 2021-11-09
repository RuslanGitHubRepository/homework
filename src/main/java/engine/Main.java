package engine;

import factory.FigureFactory;
import factory.IFigureFactory;
import homework13.ByCondition;
import homework13.Sequence;
import logger.ILogger;
import logger.Logger;
import models.Coordinates;
import models.ModelType;
import models.resolvers.Moveable;

import java.util.Arrays;
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

        /*TODO Lusinda_K:add -ea to VM option to activate assert key-word*/
        Arrays.stream(moveableFigureArray)
                .filter(Objects::nonNull)
                .forEach(figure -> figure.move(newCoordinate));
        int[] array = IntStream.iterate(0, index -> index + 1)
                .limit(10)
                .toArray();
        int[] honestArray = Sequence.filter(array, element -> element % 2 == 0);
        Arrays.stream(honestArray)
                .forEach(element-> {assert(element %2 == 0);});
        ByCondition predicate = number -> {
            int x = number, z = 0;
            while(x > 0) {
                z += x % 10;
                x /= 10;
            }
            return z % 2 == 0;
        };
        int[] filterArray = Sequence.filter(array, predicate);
        assert (filterArray.length == 5);

        logger.log("All movable figures shift to new position");
    }
}

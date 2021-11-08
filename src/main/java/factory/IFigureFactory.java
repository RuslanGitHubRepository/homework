package factory;

import models.ModelType;
import models.figures.Figure;
import models.resolvers.Moveable;

public interface IFigureFactory {
    <T extends Figure & Moveable> T createMoveAbleFigure(ModelType modelType);
    Figure createAnyFigure(ModelType modelType);
}

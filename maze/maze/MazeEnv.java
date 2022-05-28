package maze;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import jason.environment.grid.Location;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeEnv extends jason.environment.Environment {

    private Logger logger = Logger.getLogger("maze.mas2j");

    WorldModel model;
    WorldView view;

    int sleep = 500;

    Term moveFwd = Literal.parseLiteral("move_fwd");
    Term turnLeft = Literal.parseLiteral("turn_left");
    Term turnRight = Literal.parseLiteral("turn_right");
    Term markCell = Literal.parseLiteral("mark_cell");
    Term markBackCell = Literal.parseLiteral("mark_back_cell");

    @Override
    public void init(String[] args) {
        initWorld();
    }

    public void setSleep(int s) {
        sleep = s;
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        boolean result = false;
        try {
            if (sleep > 0) {
                Thread.sleep(sleep);
            }

            if (action.equals(moveFwd)) {
                result = model.moveFwd();
                view.updateCellsVisited(model.getCellsVisited());
            } else if (action.equals(turnLeft)) {
                result = model.turnLeft();
            } else if (action.equals(turnRight)) {
                result = model.turnRight();
            } else if (action.equals(markCell)) {
                result = model.markCell();
            } else if (action.equals(markBackCell)) {
                result = model.markBackCell();
            } else {
                logger.info("executing: " + action + ", but not implemented!");
            }
            if (result) {
                updateAgPercept();
                return true;
            }
        } catch (InterruptedException e) {
        } catch (Exception e) {
            logger.log(Level.SEVERE, "error executing " + action + " for " + ag, e);
        }
        return false;
    }

    public void initWorld() {
        try {
            model = WorldModel.world();

            clearPercepts();

            view = new WorldView(model);
            view.setEnv(this);

            updateAgPercept();
        } catch (Exception e) {
            logger.warning("Error creating world "+e);
        }
    }

    public void endSimulation() {
        if (view != null) view.setVisible(false);
        WorldModel.destroy();
    }

    private void updateAgPercept() {
        clearPercepts("ag");

        Location l = model.getAgPos(0);
        updateAgPercept(l.x, l.y);

        switch (model.getDir()) {
            case UP:
                updateAgPercept("front", l.x, l.y - 1);
                updateAgPercept("left", l.x - 1, l.y);
                updateAgPercept("right", l.x + 1, l.y);
                break;
            case DOWN:
                updateAgPercept("front", l.x, l.y + 1);
                updateAgPercept("left", l.x + 1, l.y);
                updateAgPercept("right", l.x - 1, l.y);
                break;
            case RIGHT:
                updateAgPercept("front", l.x + 1, l.y);
                updateAgPercept("left", l.x, l.y - 1);
                updateAgPercept("right", l.x, l.y + 1);
                break;
            case LEFT:
                updateAgPercept("front", l.x - 1, l.y);
                updateAgPercept("left", l.x, l.y + 1);
                updateAgPercept("right", l.x, l.y - 1);
                break;
        }
    }
	
	private void updateAgPercept(int x, int y) {
        if (model == null || !model.inGrid(x,y)) return;
		if (model.hasObject(WorldModel.EXIT, x, y)) {
            addPercept("ag", Literal.parseLiteral("cell(exit)"));
		}
    }

    private void updateAgPercept(String arg1, int x, int y) {
        if (model == null || !model.inGrid(x,y)) return;
        if (model.hasObject(WorldModel.OBSTACLE, x, y)) {
            addPercept("ag", Literal.parseLiteral("cell(" + arg1 + "," + "obstacle)"));
        } else if (model.hasObject(WorldModel.ENTRANCE, x, y)) {
            addPercept("ag", Literal.parseLiteral("cell(" + arg1 + "," + "entrance)"));
        } else if (model.hasObject(WorldModel.EXIT, x, y)) {
            addPercept("ag", Literal.parseLiteral("cell(" + arg1 + "," + "exit)"));
        } else if (model.hasObject(WorldModel.MARKED_ONCE, x, y)) {
            addPercept("ag", Literal.parseLiteral("cell(" + arg1 + "," + "marked_once)"));
        } else if (model.hasObject(WorldModel.MARKED_TWICE, x, y)) {
            addPercept("ag", Literal.parseLiteral("cell(" + arg1 + "," + "marked_twice)"));
        }
    }

}

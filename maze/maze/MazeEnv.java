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
            } else if (action.equals(turnLeft)) {
                result = model.turnLeft();
            } else if (action.equals(turnRight)) {
                result = model.turnRight();
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
        // its location
        Location l = model.getAgPos(0);
        addPercept("ag", Literal.parseLiteral("pos(" + l.x + "," + l.y + ")"));

        switch (model.getDir()) {
            case UP:
                updateAgPercept("front_cell", l.x, l.y - 1);
                updateAgPercept("back_cell", l.x, l.y + 1);
                updateAgPercept("left_cell", l.x - 1, l.y);
                updateAgPercept("right_cell", l.x + 1, l.y);
                break;
            case DOWN:
                updateAgPercept("front_cell", l.x, l.y + 1);
                updateAgPercept("back_cell", l.x, l.y - 1);
                updateAgPercept("left_cell", l.x + 1, l.y);
                updateAgPercept("right_cell", l.x - 1, l.y);
                break;
            case RIGHT:
                updateAgPercept("front_cell", l.x + 1, l.y);
                updateAgPercept("back_cell", l.x - 1, l.y);
                updateAgPercept("left_cell", l.x, l.y - 1);
                updateAgPercept("right_cell", l.x, l.y + 1);
                break;
            case LEFT:
                updateAgPercept("front_cell", l.x - 1, l.y);
                updateAgPercept("back_cell", l.x + 1, l.y);
                updateAgPercept("left_cell", l.x, l.y + 1);
                updateAgPercept("right_cell", l.x, l.y - 1);
                break;
        }
    }

    private void updateAgPercept(String percept, int x, int y) {
        if (model == null || !model.inGrid(x,y)) return;
        if (model.hasObject(WorldModel.OBSTACLE, x, y)) {
            addPercept("ag", Literal.parseLiteral(percept + "(obstacle)"));
        } else if (model.hasObject(WorldModel.ENTRANCE, x, y)) {
            addPercept("ag", Literal.parseLiteral(percept + "(entrance)"));
        } else if (model.hasObject(WorldModel.EXIT, x, y)) {
            addPercept("ag", Literal.parseLiteral(percept + "(exit)"));
        }
    }

}

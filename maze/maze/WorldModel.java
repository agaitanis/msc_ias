package maze;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class WorldModel extends GridWorldModel {
    public static final int ENTRANCE = 16;
    public static final int EXIT = 32;

    private Logger logger = Logger.getLogger("maze.mas2j");

    private String id = "WorldModel";

    public enum Direction {
        UP, DOWN, RIGHT, LEFT
    };

    Direction dir = Direction.DOWN;

    // singleton pattern
    protected static WorldModel model = null;

    synchronized public static WorldModel create(int w, int h) {
        if (model == null) {
            model = new WorldModel(w, h);
        }
        return model;
    }

    public static WorldModel get() {
        return model;
    }

    public static void destroy() {
        model = null;
    }

    private WorldModel(int w, int h) {
        super(w, h, 1);
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction new_dir) {
        dir = new_dir;
    }

    /** Actions **/
    boolean moveFwd() throws Exception {
        Location l = getAgPos(0);

        switch (getDir()) {
            case UP:
                if (isFree(l.x, l.y - 1)) {
                    setAgPos(0, l.x, l.y - 1);
                }
                break;
            case DOWN:
                if (isFree(l.x, l.y + 1)) {
                    setAgPos(0, l.x, l.y + 1);
                }
                break;
            case RIGHT:
                if (isFree(l.x + 1, l.y)) {
                    setAgPos(0, l.x + 1, l.y);
                }
                break;
            case LEFT:
                if (isFree(l.x - 1, l.y)) {
                    setAgPos(0, l.x - 1, l.y);
                }
                break;
        }
        return true;
    }

    boolean turnLeft() throws Exception {
        switch (getDir()) {
            case UP:
                setDir(Direction.LEFT);
                break;
            case DOWN:
                setDir(Direction.RIGHT);
                break;
            case RIGHT:
                setDir(Direction.UP);
                break;
            case LEFT:
                setDir(Direction.DOWN);
                break;
        }
        Location l = getAgPos(0);
        setAgPos(0, l.x, l.y);
        return true;
    }

    boolean turnRight() throws Exception {
        switch (getDir()) {
            case UP:
                setDir(Direction.RIGHT);
                break;
            case DOWN:
                setDir(Direction.LEFT);
                break;
            case RIGHT:
                setDir(Direction.DOWN);
                break;
            case LEFT:
                setDir(Direction.UP);
                break;
        }
        Location l = getAgPos(0);
        setAgPos(0, l.x, l.y);
        return true;
    }

    private void addVerticalWall(int x, int[] y) {
        for (int i = 0; i < y.length; i += 2) {
            model.addWall(x, y[i], x, y[i + 1]);
        }
    }

    private void addHorizontalWall(int[] x, int y) {
        for (int i = 0; i < x.length; i += 2) {
            model.addWall(x[i], y, x[i + 1], y);
        }
    }

    static WorldModel world() throws Exception {
        WorldModel model = WorldModel.create(20, 20);
        int x, y;

        model.setAgPos(0, 1, 0);

        model.add(WorldModel.ENTRANCE, 1, 0);
        model.add(WorldModel.EXIT, 18, 19);

        model.addHorizontalWall(new int[] {2,19}, 0);
        model.addHorizontalWall(new int[] {2,3, 7,9, 13,17}, 2);
        model.addHorizontalWall(new int[] {2,7, 9,11, 17,19}, 4);
        model.addHorizontalWall(new int[] {0,1, 5,7, 11,15}, 6);
        model.addHorizontalWall(new int[] {2,5, 9,13, 15,19}, 8);
        model.addHorizontalWall(new int[] {0,1, 3,7, 9,17}, 10);
        model.addHorizontalWall(new int[] {2,4, 8,11, 15,19}, 12);
        model.addHorizontalWall(new int[] {4,6, 15,17}, 15);
        model.addHorizontalWall(new int[] {2,6, 10,17}, 17);
        model.addHorizontalWall(new int[] {0,17}, 19);

        model.addVerticalWall(0, new int[] {0,19});
        model.addVerticalWall(2, new int[] {2,4, 12,15, 17,19});
        model.addVerticalWall(3, new int[] {4,6, 8,10});
        model.addVerticalWall(4, new int[] {12,15});
        model.addVerticalWall(5, new int[] {0,4, 6,8});
        model.addVerticalWall(6, new int[] {10,15});
        model.addVerticalWall(7, new int[] {4,6, 8,10});
        model.addVerticalWall(8, new int[] {12,19});
        model.addVerticalWall(9, new int[] {2,4, 6,8, 10,12});
        model.addVerticalWall(10, new int[] {14,17});
        model.addVerticalWall(11, new int[] {0,2, 4,6, 8,10, 12,15});
        model.addVerticalWall(13, new int[] {2,4, 6,8, 10,15});
        model.addVerticalWall(15, new int[] {2,6, 12,15});
        model.addVerticalWall(17, new int[] {4,6, 14,15});
        model.addVerticalWall(19, new int[] {0,19});

        return model;
    }

}

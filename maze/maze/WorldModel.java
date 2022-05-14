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

    static WorldModel world() throws Exception {
        // 0: free
        // 1: obstacle
        // 2: entrance, agent
        // 3: exit
        int map[][] = new int[][] {
            {1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1},
            {1,0,1,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1},
            {1,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,1},
            {1,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
            {1,0,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1},
            {1,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
            {1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,1},
            {1,0,1,1,1,0,1,0,1,1,1,1,0,1,0,1,1,1,1,1},
            {1,0,1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,1,1,0,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1},
        };

        WorldModel model = WorldModel.create(map.length, map[0].length);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                switch (map[y][x]) {
                    case 1:
                        model.add(WorldModel.OBSTACLE, x, y);
                        break;
                    case 2:
                        model.setAgPos(0, x, y);
                        model.add(WorldModel.ENTRANCE, x, y);
                        break;
                    case 3:
                        model.add(WorldModel.EXIT, x, y);
                        break;
                }
            }
        }

        return model;
    }

}

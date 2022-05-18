package maze;

import jason.environment.grid.GridWorldView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class WorldView extends GridWorldView {

    MazeEnv env = null;

    public WorldView(WorldModel model) {
        super(model, "Maze", 600);
        setVisible(true);
        repaint();
    }

    public void setEnv(MazeEnv env) {
        this.env = env;
    }

    JSlider jSpeed;
    JLabel jCellsVisited;

    @Override
    public void initComponents(int width) {
        super.initComponents(width);

        JPanel args = new JPanel();
        args.setLayout(new BoxLayout(args, BoxLayout.Y_AXIS));

        jSpeed = new JSlider();
        jSpeed.setMinimum(0);
        jSpeed.setMaximum(1000);
        jSpeed.setValue(500);
        jSpeed.setPaintTicks(true);
        jSpeed.setPaintLabels(true);
        jSpeed.setMajorTickSpacing(250);
        jSpeed.setMinorTickSpacing(50);
        jSpeed.setInverted(true);
        Hashtable<Integer,Component> labelTable = new Hashtable<Integer,Component>();
        labelTable.put(0, new JLabel("max") );
        labelTable.put(500, new JLabel("speed") );
        labelTable.put(1000, new JLabel("min") );
        jSpeed.setLabelTable( labelTable );
        JPanel p = new JPanel(new FlowLayout());
        p.setBorder(BorderFactory.createEtchedBorder());
        p.add(jSpeed);

        args.add(p);

        JPanel msg = new JPanel();
        msg.setLayout(new BoxLayout(msg, BoxLayout.Y_AXIS));
        msg.setBorder(BorderFactory.createEtchedBorder());

        p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(new JLabel("Cells visited:"));
        jCellsVisited = new JLabel("0");
        p.add(jCellsVisited);
        msg.add(p);

        JPanel s = new JPanel(new BorderLayout());
        s.add(BorderLayout.WEST, args);
        s.add(BorderLayout.CENTER, msg);
        getContentPane().add(BorderLayout.SOUTH, s);

        // Events handling
        jSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (env != null) {
                    env.setSleep((int)jSpeed.getValue());
                }
            }
        });
    }

    public void updateCellsVisited(int cellsVisited) {
        jCellsVisited.setText(Integer.toString(cellsVisited));
    }

    @Override
    public void draw(Graphics g, int x, int y, int object) {
        switch (object) {
            case WorldModel.ENTRANCE:
                g.setColor(Color.red);
                g.fillRect(x * cellSizeW, y * cellSizeH, cellSizeW, cellSizeH);
                break;
            case WorldModel.EXIT:
                g.setColor(Color.green);
                g.fillRect(x * cellSizeW, y * cellSizeH, cellSizeW, cellSizeH);
                break;
            case WorldModel.MARKED_ONCE:
                g.setColor(Color.orange);
                g.drawOval(x * cellSizeW, y * cellSizeH, cellSizeW, cellSizeH);
                break;
            case WorldModel.MARKED_TWICE:
                g.setColor(Color.red);
                g.drawLine(x * cellSizeW, y * cellSizeH, (x+1) * cellSizeW, (y+1) * cellSizeH);
                g.drawLine(x * cellSizeW, (y+1) * cellSizeH, (x+1) * cellSizeW, y * cellSizeH);
                break;
        }
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        super.drawAgent(g, x, y, Color.blue, -1);
        g.setColor(Color.white);
        WorldModel wm = (WorldModel)model;
        switch (wm.getDir()) {
            case UP:
                drawString(g, x, y, defaultFont, "^");
                break;
            case DOWN:
                drawString(g, x, y, defaultFont, "v");
                break;
            case RIGHT:
                drawString(g, x, y, defaultFont, ">");
                break;
            case LEFT:
                drawString(g, x, y, defaultFont, "<");
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        MazeEnv env = new MazeEnv();
        env.init(new String[] {});
    }
}

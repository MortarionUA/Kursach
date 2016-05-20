package ip4323.hommClass;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dima_ on 20.05.2016.
 */
public class UnitPaint extends Component implements Runnable {

    private static final int tW = 64;
    private static final int tH = 64;

    private Image tileset;
    private Image unitset;
    private Image numberset;

    private Unit unit;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    protected int w, h;

    protected Frame f;

    private Numbers numbers[] = new Numbers[]{Numbers.zero, Numbers.one, Numbers.two, Numbers.three, Numbers.four, Numbers.five, Numbers.six, Numbers.seven, Numbers.eight, Numbers.nine};

    public UnitPaint(Frame frame, int width, int height, Unit unit) {
        w = width;
        h = height;
        f = frame;
        this.unit = unit;

        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));
        numberset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/numbers.png"));

        mInit();
    }
    public void mInit() {
        t = new Thread(this);
        t.start();
    }

    public void run() {
        repaint();
    }

    public void paint(Graphics g) {
        if (grphImage == null) {
            grphImage = (BufferedImage) createImage(576, 256);
        }
        grph = grphImage.getGraphics();
        grph.setColor(Color.WHITE);
        grph.fillRect(0, 0, getWidth(), getHeight());
        drawTile(grph, Tile.GRASS1, 0, 0);
        UnitImg unitI = null;
        if (unit == null) unit = null;
        else {
            switch(unit.getType()) {
                case 1: unitI = UnitImg.BL_SOLD1;
                    break;
                case 2: unitI = UnitImg.BL_ARCH1;
                    break;
                case 3: unitI = UnitImg.BL_COLO1;
                    break;
                case 4: unitI = UnitImg.BL_CATA1;
                    break;
                case 5: unitI = UnitImg.BL_DRAG1;
                    break;
            }
        }
        if (unit != null) {
            drawUnit(grph, unitI, 0, 0);
            if(unit.getCount()/1000 >= 1) {
                drawNumber(grph, numbers[unit.getCount()/1000], 0, 48);
                drawNumber(grph, numbers[unit.getCount()%1000/100], 16, 48);
                drawNumber(grph, numbers[unit.getCount()%100/10], 32, 48);
                drawNumber(grph, numbers[unit.getCount()%10], 48, 48);
            } else if(unit.getCount()/100 >= 1) {
                drawNumber(grph, numbers[unit.getCount()/100], 16, 48);
                drawNumber(grph, numbers[unit.getCount()%100/10], 32, 48);
                drawNumber(grph, numbers[unit.getCount()%10], 48, 48);
            } else if(unit.getCount()/10 >= 1) {
                drawNumber(grph, numbers[unit.getCount()/10], 32, 48);
                drawNumber(grph, numbers[unit.getCount()%10], 48, 48);
            } else {
                drawNumber(grph, numbers[unit.getCount()], 48, 48);
            }
        }
        g.drawImage(grphImage, 0, 0, this);
    }

    protected void drawTile(Graphics g, Tile t, int x, int y) {
        int mx = t.ordinal() % 8;
        int my = t.ordinal() / 8;
        g.drawImage(tileset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
    }

    protected void drawUnit(Graphics g, UnitImg t, int x, int y) {
        int mx = t.ordinal() % 16;
        int my = t.ordinal() / 16;
        g.drawImage(unitset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
    }

    protected void drawNumber(Graphics g, Numbers n, int x, int y) {
        int numX = 16;
        int numY = 16;
        int mx = n.ordinal() % 10;
        int my = n.ordinal() / 10;
        g.drawImage(numberset, x, y, x + numX, y + numY, mx * numX, my * numY, mx * numX + numX, my * numY + numY, this);
    }
}

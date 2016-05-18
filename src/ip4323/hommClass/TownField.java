package ip4323.hommClass;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by dima_ on 12.05.2016.
 */
public class TownField extends Component implements Runnable, MouseListener {

    private static final int tW = 64;
    private static final int tH = 64;

    private Image tileset;
    private Image particleset;
    private Image unitset;
    private Image numberset;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private int posX, posY;

    private Town town;

    protected int w, h;

    protected Frame f;

    private Numbers numbers[] = new Numbers[]{Numbers.zero, Numbers.one, Numbers.two, Numbers.three, Numbers.four, Numbers.five, Numbers.six, Numbers.seven, Numbers.eight, Numbers.nine};

    public TownField(Frame frame, int width, int height, Town newTown) {
        w = width;
        h = height;
        f = frame;
        town = newTown;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        addMouseListener(this);
        setFocusable(true);

        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        particleset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/particles.png"));
        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));
        numberset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/numbers.png"));

        mInit();
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
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
        Particle[] buildParticle = new Particle[]{Particle.BlueP_1, Particle.BlueP_2, Particle.BlueP_3, Particle.BlueP_4, Particle.BlueP_5};
        for(int i=0; i<6; i++) {
            drawTile(grph, Tile.GRASS1, i * tW, 0);
        }
        switch (town.getBuildings()[0]) {
            case 0: drawTile(grph, Tile.TOWN, 0, tH);
                drawParticle(grph, Particle.YellowP_1, 0, tH);
                break;
            case 1: drawTile(grph, Tile.TOWN, 0, tH);
                drawParticle(grph, Particle.YellowP_2, 0, tH);
                break;
            case 2: drawTile(grph, Tile.TOWN, 0, tH);
                drawParticle(grph, Particle.YellowP_3, 0, tH);
                break;
            case 3: drawTile(grph, Tile.TOWN, 0, tH);
                drawParticle(grph, Particle.YellowP_4, 0, tH);
                break;
        }
        for (int i=1; i<6; i++) {
            if (town.getBuildings()[i] != 0) {
                drawTile(grph, Tile.TOWN, i * tW, tH);
                drawParticle(grph, buildParticle[i-1], i * tW, tH);
            }
            else drawTile(grph, Tile.GRASS1, i * tW, tH);
        }
        switch (town.getBuildings()[6]) {
            case 0:
                for (int i = 6; i < 9; i++) {
                    drawTile(grph, Tile.GRASS1, i * tW, 0);
                }
                drawTile(grph, Tile.GRASS1, 6 * tW, tH);
                drawTile(grph, Tile.FORT, 7 * tW, tH);
                drawTile(grph, Tile.GRASS1, 8 * tW, tH);
                break;
            case 1:
                drawTile(grph, Tile.GRASS1, 6 * tW, 0);
                drawTile(grph, Tile.CASTLE_UP, 7 * tW, 0);
                drawTile(grph, Tile.GRASS1, 8 * tW, 0);
                drawTile(grph, Tile.GRASS1, 6 * tW, tH);
                drawTile(grph, Tile.CASTLE_DOWN, 7 * tW, tH);
                drawTile(grph, Tile.GRASS1, 8 * tW, tH);
                break;
            case 2:
                drawTile(grph, Tile.GRASS1, 6 * tW, 0);
                drawTile(grph, Tile.BIGCASTLE_UP, 7 * tW, 0);
                drawTile(grph, Tile.T93, 8 * tW, 0);
                drawTile(grph, Tile.BIGCASTLE_LEFT, 6 * tW, tH);
                drawTile(grph, Tile.BIGCASTLE_CENTER, 7 * tW, tH);
                drawTile(grph, Tile.BIGCASTLE_RIGHT, 8 * tW, tH);
        }
        for(int i=0; i<9; i++) {
            drawTile(grph, Tile.GROUND, i * tW, 2 * tH);
        }
        for(int i=0; i<9; i++) {
            if((i < 2) || (i >= 7)) drawTile(grph, Tile.GROUND, i * tW, 3 * tH);
            if((i >= 2) && (i < 7)) drawTile(grph, Tile.GRASS1, i * tW, 3 * tH);
        }
        UnitImg units[] = new UnitImg[5];
        for(int i=0; i<5; i++) {
            if (town.getArmy()[i] == null) units[i] = null;
            else {
                switch(town.getArmy()[i].getType()) {
                    case 1: units[i] = UnitImg.BL_SOLD1;
                        break;
                    case 2: units[i] = UnitImg.BL_ARCH1;
                        break;
                    case 3: units[i] = UnitImg.BL_COLO1;
                        break;
                    case 4: units[i] = UnitImg.BL_CATA1;
                        break;
                    case 5: units[i] = UnitImg.BL_DRAG1;
                        break;
                }
            }
        }
        for(int i=2; i<7; i++) {
            if (units[i-2] != null) {
                drawUnit(grph, units[i-2], i * tW, 3 * tH);
                if(town.getArmy()[i-2].getCount()/1000 >= 1) {
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()/1000], i * tW, 3 * tH + 48);
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()%1000/100], i * tW + 16, 3 * tH + 48);
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()%100/10], i * tW + 32, 3 * tH + 48);
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()%10], i * tW + 48, 3 * tH + 48);
                } else if(town.getArmy()[i-2].getCount()/100 >= 1) {
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()/100], i * tW + 16, 3 * tH + 48);
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()%100/10], i * tW + 32, 3 * tH + 48);
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()%10], i * tW + 48, 3 * tH + 48);


                } else if(town.getArmy()[i-2].getCount()/10 >= 1) {
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()/10], i * tW + 32, 3 * tH + 48);
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()%10], i * tW + 48, 3 * tH + 48);

                } else {
                    drawNumber(grph, numbers[town.getArmy()[i-2].getCount()], i * tW + 48, 3 * tH + 48);
                }
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

    protected void drawParticle(Graphics g, Particle t, int x, int y) {
        int mx = t.ordinal() % 6;
        int my = t.ordinal() / 6;
        g.drawImage(particleset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
    }

    protected void drawNumber(Graphics g, Numbers n, int x, int y) {
        int numX = 16;
        int numY = 16;
        int mx = n.ordinal() % 10;
        int my = n.ordinal() / 10;
        g.drawImage(numberset, x, y, x + numX, y + numY, mx * numX, my * numY, mx * numX + numX, my * numY + numY, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        posX = e.getX()/64;
        posY = e.getY()/64;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ((posY == 3) && ((posX == 2) || (posX == 3) || (posX == 4) || (posX == 5) || (posX == 6))) {
            Unit tmp = town.getArmy()[posX - 2];
            int nposX = e.getX()/64;
            int nposY = e.getY()/64;
            if ((nposY == 3) && ((nposX == 2) || (nposX == 3) || (nposX == 4) || (nposX == 5) || (nposX == 6))) {
                town.setArmy(town.getArmy()[nposX - 2], posX - 2);
                town.setArmy(tmp, nposX - 2);
            }
        }
        if((posY == 1) && (posX < 6)) {
            BuildingMenu bm = new BuildingMenu();
            bm.setTw(this);
            bm.setResizable(false);
            bm.showButton(posX);
        }
        else if (((posY == 0) || (posY == 1)) && ((posX > 5) && (posX < 9))) {
            BuildingMenu bm = new BuildingMenu();
            bm.setTw(this);
            bm.setResizable(false);
            bm.showButton(6);
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

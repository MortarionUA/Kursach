package ip4323.hommClass;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by dima_ on 17.05.2016.
 */
public class HeroField extends Component implements Runnable, MouseListener {
    private static final int tW = 64;
    private static final int tH = 64;

    private Image tileset;
    private Image unitset;
    private Image numberset;
    private Image paramset;
    private Image bignumberset;

    private int posX, posY;

    private UnitInfo ui = null;

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private Hero hero;

    protected int w, h;

    protected Frame f;

    private Numbers numbers[] = new Numbers[]{Numbers.zero, Numbers.one, Numbers.two, Numbers.three, Numbers.four, Numbers.five, Numbers.six, Numbers.seven, Numbers.eight, Numbers.nine};
    private NumbersBig numbersBig[] = new NumbersBig[]{NumbersBig.BIG_ZERO, NumbersBig.BIG_ONE, NumbersBig.BIG_TWO, NumbersBig.BIG_THREE, NumbersBig.BIG_FOUR, NumbersBig.BIG_FIVE, NumbersBig.BIG_SIX, NumbersBig.BIG_SEVEN, NumbersBig.BIG_EIGHT, NumbersBig.BIG_NINE};

    public HeroField (Frame frame, int width, int height, Hero hero) {
        w = width;
        h = height;
        f = frame;
        this.hero = hero;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        addMouseListener(this);
        setFocusable(true);

        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));
        numberset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/numbers.png"));
        paramset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/Params.png"));
        bignumberset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/NumbersBig.png"));

        mInit();
    }

    private void mInit() {
        t = new Thread(this);
        t.start();
    }

    public void run() {
        repaint();
    }

    public void paint(Graphics g) {
        if (grphImage == null) {
            grphImage = (BufferedImage) createImage(640, 192);
        }
        grph = grphImage.getGraphics();
        grph.setColor(Color.WHITE);
        grph.fillRect(0, 0, getWidth(), getHeight());
        for(int i=0; i<3; i++) {
            for(int j=0; j<10; j++) {
                drawTile(grph, Tile.GROUND, j * tW, i * tH);
            }
        }
        for(int i=4; i<9; i++) {
            drawTile(grph, Tile.GRASS1, i * tW, tH);
        }
        UnitImg units[] = new UnitImg[5];
        for(int i=0; i<5; i++) {
            if (hero.getArmy()[i] == null) units[i] = null;
            else {
                switch(hero.getArmy()[i].getType()) {
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
        drawParam(grph, Params.ATTACK, 0, 0);
        drawParam(grph, Params.DEFENSE, 0, tH);
        drawParam(grph, Params.HP, 0, 2 * tH);
        if(hero.getDmgBonus()/10 >= 1) {
            drawBigNumber(grph, numbersBig[hero.getDmgBonus()/10], tW, 0);
            drawBigNumber(grph, numbersBig[hero.getDmgBonus()%10], 2 * tW, 0);
        } else drawBigNumber(grph, numbersBig[hero.getDmgBonus()], tW, 0);
        if(hero.getDmgBonus()/10 >= 1) {
            drawBigNumber(grph, numbersBig[hero.getDmgBonus()/10], tW, tH);
            drawBigNumber(grph, numbersBig[hero.getDmgBonus()%10], 2 * tW, tH);
        } else drawBigNumber(grph, numbersBig[hero.getDmgBonus()], tW, tH);
        if(hero.getDmgBonus()/10 >= 1) {
            drawBigNumber(grph, numbersBig[hero.getDmgBonus()/10], tW, 2 * tH);
            drawBigNumber(grph, numbersBig[hero.getDmgBonus()%10], 2 * tW, 2 * tH);
        } else drawBigNumber(grph, numbersBig[hero.getDmgBonus()], tW, 2 * tH);
        for(int i=4; i<9; i++) {
            if (units[i-4] != null) {
                drawUnit(grph, units[i-4], i * tW, tH);
                if(hero.getArmy()[i-4].getCount()/1000 >= 1) {
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()/1000], i * tW, tH + 48);
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()%1000/100], i * tW + 16, tH + 48);
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()%100/10], i * tW + 32, tH + 48);
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()%10], i * tW + 48, tH + 48);
                } else if(hero.getArmy()[i-4].getCount()/100 >= 1) {
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()/100], i * tW + 16, tH + 48);
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()%100/10], i * tW + 32, tH + 48);
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()%10], i * tW + 48, tH + 48);

                } else if(hero.getArmy()[i-4].getCount()/10 >= 1) {
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()/10], i * tW + 32, tH + 48);
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()%10], i * tW + 48, tH + 48);

                } else {
                    drawNumber(grph, numbers[hero.getArmy()[i-4].getCount()], i * tW + 48, tH + 48);
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

    protected void drawNumber(Graphics g, Numbers n, int x, int y) {
        int numX = 16;
        int numY = 16;
        int mx = n.ordinal() % 10;
        int my = n.ordinal() / 10;
        g.drawImage(numberset, x, y, x + numX, y + numY, mx * numX, my * numY, mx * numX + numX, my * numY + numY, this);
    }

    protected void drawBigNumber(Graphics g, NumbersBig n, int x, int y) {
        int numX = 64;
        int numY = 64;
        int mx = n.ordinal() % 10;
        int my = n.ordinal() / 10;
        g.drawImage(bignumberset, x, y, x + numX, y + numY, mx * numX, my * numY, mx * numX + numX, my * numY + numY, this);
    }

    protected void drawParam(Graphics g, Params n, int x, int y) {
        int numX = 64;
        int numY = 64;
        int mx = n.ordinal() % 3;
        int my = n.ordinal() / 3;
        g.drawImage(paramset, x, y, x + numX, y + numY, mx * numX, my * numY, mx * numX + numX, my * numY + numY, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        posX = e.getX()/64;
        posY = e.getY()/64;
        if ((e.getModifiers()== InputEvent.BUTTON3_MASK) && (((posY == 1) && ((posX == 4) || (posX == 5) || (posX == 6) || (posX == 7) || (posX == 8))))){
            ui = new UnitInfo(hero.getArmy()[posX-4]);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (ui != null) {
            ui.disappear();
        }
        if ((posY == 1) && ((posX == 4) || (posX == 5) || (posX == 6) || (posX == 7) || (posX == 8))) {
            Unit tmp = hero.getArmy()[posX - 4];
            int nposX = e.getX()/64;
            int nposY = e.getY()/64;
            if ((nposY == 1) && ((nposX == 4) || (nposX == 5) || (nposX == 6) || (nposX == 7) || (nposX == 8))) {
                if ((hero.getArmy()[nposX - 4] != null) && (tmp.getType() == hero.getArmy()[nposX - 4].getType()) && (posX != nposX)) {
                    hero.getArmy()[nposX - 4].setCount(hero.getArmy()[nposX - 4].getCount() + tmp.getCount());
                    hero.getArmy()[posX - 4] = null;
                } else {
                    hero.setArmy(hero.getArmy()[nposX - 4], posX - 4);
                    hero.setArmy(tmp, nposX - 4);
                }
            }
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

package ip4323.hommClass;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by dima_ on 22.05.2016.
 */
public class GlobalMap extends Component implements Runnable, KeyListener, MouseListener, FocusListener {

    private static final int tW = 64;
    private static final int tH = 64;

    private Image unitset;
    private Image tileset;
    private Image highlightset;
    private Image numberset;
    private Image factionset;

    private boolean[][] highMap = new boolean[20][15];

    private Thread t;
    private Graphics grph;
    private BufferedImage grphImage;

    private Map workingMap;
    private Tile map2[][] = new Tile[20][15];
    private Factions map3[][] = new Factions[20][15];

    int posX, posY;

    private int playerFlag;
    private int flagHeroActive = Integer.MAX_VALUE;
    private int buyHeroFlag = 0;
    private int dayFlag;

    private Player player1 = new Player(2500);
    private Player player2 = new Player(2500);

    protected int w, h;

    protected Frame f;

    private Numbers numbers[] = new Numbers[]{Numbers.zero, Numbers.one, Numbers.two, Numbers.three, Numbers.four, Numbers.five, Numbers.six, Numbers.seven, Numbers.eight, Numbers.nine};

    public GlobalMap(Frame frame, int width, int height) throws IOException, ClassNotFoundException {
        w = width;
        h = height;
        f = frame;

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(this);
        setFocusable(true);

        tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/tileset.png"));
        factionset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/fractions.png"));
        highlightset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/highlight.png"));
        unitset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/human units.png"));
        numberset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resource/numbers.png"));

        mInit();
    }

    public void mInit() throws IOException, ClassNotFoundException {
        load("temp1.out");
        playerFlag = 1;
        dayFlag = 0;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        repaint();
    }

    public void convertMap() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                map3[i][j] = Factions.T1;
                switch (workingMap.getMapTerr()[i][j]) {
                    case 0:
                        map2[i][j] = Tile.GRASS1;
                        break;
                    case 1:
                        map2[i][j] = Tile.GRASS2;
                        break;
                    case 2:
                        map2[i][j] = Tile.GRASS3;
                        break;
                    case 3:
                        map2[i][j] = Tile.GRASS4;
                        break;
                    case 4:
                        map2[i][j] = Tile.GRASS5;
                        break;
                    case 5:
                        map2[i][j] = Tile.GRASS6;
                        break;
                    case 6:
                        map2[i][j] = Tile.GRASS7;
                        break;
                    case 7:
                        map2[i][j] = Tile.HILL1;
                        break;
                    case 8:
                        map2[i][j] = Tile.HILL2;
                        break;
                    case 9:
                        map2[i][j] = Tile.MOUNT;
                        break;
                }
            }
        }
        if (workingMap.getMapTown() != null) {
            for(Town town : workingMap.getMapTown()) {
                int tempX = town.getPosX();
                int tempY = town.getPosY();
                map2[tempX][tempY] = Tile.CASTLE_DOWN;
                map2[tempX][tempY-1] = Tile.CASTLE_UP;
                switch (town.getFaction()) {
                    case 1: {
                        map3[tempX][tempY] = Factions.BL_FLAG;
                        break;
                    }
                    case 2: {
                        map3[tempX][tempY] = Factions.RD_FLAG;
                        break;
                    }
                    default: {
                        map3[tempX][tempY] = Factions.T1;
                        break;
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        convertMap();
        if (grphImage == null) {
            grphImage = (BufferedImage) createImage(1280, 960);
        }
        grph = grphImage.getGraphics();
        grph.setColor(Color.WHITE);
        grph.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                drawTile(grph, map2[i][j], i * tW, j * tH);
                drawFaction(grph, map3[i][j], i * tW, j * tH);
            }
        }
        for(int i=0; i < workingMap.getMapHero().size(); i++) {
            if (workingMap.getMapHero().get(i).getFaction() == 1) {
                drawUnit(grph, UnitImg.BL_HERO11, workingMap.getMapHero().get(i).getPosX() * tW, workingMap.getMapHero().get(i).getPosY() * tH);
            }
            else if (workingMap.getMapHero().get(i).getFaction() == 2) {
                drawUnit(grph, UnitImg.RD_HERO11, workingMap.getMapHero().get(i).getPosX() * tW, workingMap.getMapHero().get(i).getPosY() * tH);
            }
        }
        if (flagHeroActive != Integer.MAX_VALUE) {
            cleanhigh();
            drawhigh(workingMap.getMapHero().get(flagHeroActive).getPosX() * tW, workingMap.getMapHero().get(flagHeroActive).getPosY() * tH, 7);
            drawPath(grph);
        }
        if(player1.getMoney()/10000 >= 1) {
            drawNumber(grph, numbers[player1.getMoney() / 10000], 48, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney() % 10000 / 1000], tW, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney() % 1000 / 100], tW + 16, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney() % 100 / 10], tW + 32, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney() % 10], tW + 48, (14 * tH) + 48);
        } else if(player1.getMoney()/1000 >= 1) {
            drawNumber(grph, numbers[player1.getMoney()/1000], tW, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney()%1000/100], tW + 16, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney()%100/10], tW + 32, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney()%10], tW + 48, (14 * tH) + 48);
        } else if(player1.getMoney()/100 >= 1) {
            drawNumber(grph, numbers[player1.getMoney()/100], tW + 16, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney()%100/10], tW + 32, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney()%10], tW + 48, (14 * tH) + 48);
        } else if(player1.getMoney()/10 >= 1) {
            drawNumber(grph, numbers[player1.getMoney()/10], tW + 32, (14 * tH) + 48);
            drawNumber(grph, numbers[player1.getMoney()%10], tW + 48, (14 * tH) + 48);
        } else {
            drawNumber(grph, numbers[player1.getMoney()], tW + 48, (14 * tH) + 48);
        }
        if(player2.getMoney()/10000 >= 1) {
            drawNumber(grph, numbers[player2.getMoney() / 10000], (18 * tW) + 48, 48);
            drawNumber(grph, numbers[player2.getMoney() % 10000 / 1000],  19 * tW, 48);
            drawNumber(grph, numbers[player2.getMoney() % 1000 / 100], (19 * tW) + 16, 48);
            drawNumber(grph, numbers[player2.getMoney() % 100 / 10], (19 * tW) + 32, 48);
            drawNumber(grph, numbers[player2.getMoney() % 10], (19 * tW) + 48, 48);
        } else if(player2.getMoney()/1000 >= 1) {
            drawNumber(grph, numbers[player2.getMoney()/1000], (19 * tW), 48);
            drawNumber(grph, numbers[player2.getMoney()%1000/100], (19 * tW) + 16, 48);
            drawNumber(grph, numbers[player2.getMoney()%100/10], (19 * tW) + 32, 48);
            drawNumber(grph, numbers[player2.getMoney()%10], (19 * tW) + 48, 48);
        } else if(player2.getMoney()/100 >= 1) {
            drawNumber(grph, numbers[player2.getMoney()/100], (19 * tW) + 16, 48);
            drawNumber(grph, numbers[player2.getMoney()%100/10], (19 * tW) + 32, 48);
            drawNumber(grph, numbers[player2.getMoney()%10], (19 * tW) + 48, 48);
        } else if(player2.getMoney()/10 >= 1) {
            drawNumber(grph, numbers[player2.getMoney()/10], (19 * tW) + 32, 48);
            drawNumber(grph, numbers[player2.getMoney()%10], (19 * tW) + 48, 48);
        } else {
            drawNumber(grph, numbers[player2.getMoney()], (19 * tW) + 48, 48);
        }
        g.drawImage(grphImage, 0, 0, this);
    }

    public void cleanhigh() {
        for(int i=0; i<20; i++) {
            for(int j=0; j<15; j++) {
                highMap[i][j] = false;
            }
        }
    }

    protected void drawhigh(int x, int y, int moves) {
        int tempX = x/64;
        int tempY = y/64;
        if ((x >= 1280) || (y >= 960) || (x < 0) || (y < 0)) {return;}
        if ((map2[tempX][tempY] == Tile.MOUNT) || (moves < 1)) {return;}
        if ((map2[tempX][tempY] == Tile.HILL1) || (map2[tempX][tempY] == Tile.HILL2)) {
            moves--;
            moves--;
        } else {
            moves--;
        }
        highMap[tempX][tempY] = true;
        drawhigh(x-64, y, moves);
        drawhigh(x+64, y, moves);
        drawhigh(x, y-64, moves);
        drawhigh(x, y+64, moves);
    }

    protected void drawPath(Graphics g) {
        for(int i=0; i<20; i++) {
            for(int j=0; j<15; j++) {
                int x = i * tW;
                int y = j * tH;
                if (highMap[i][j] == true) {
                    g.drawImage(highlightset, x, y, x + tW, y + tH, 0, 0, tW, tH, this);
                }
            }
        }
    }

    protected void drawNumber(Graphics g, Numbers n, int x, int y) {
        int numX = 16;
        int numY = 16;
        int mx = n.ordinal() % 10;
        int my = n.ordinal() / 10;
        g.drawImage(numberset, x, y, x + numX, y + numY, mx * numX, my * numY, mx * numX + numX, my * numY + numY, this);
    }

    protected void drawFaction(Graphics g, Factions t, int x, int y) {
        int mx = t.ordinal() % 4;
        int my = t.ordinal() / 4;
        g.drawImage(factionset, x, y, x + tW, y + tH, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
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

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("save1.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(workingMap);
        oos.flush();
        oos.close();
    }

    public void load(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        workingMap = (Map) ois.readObject();
        repaint();
    }

    public void turn() {
        playerFlag++;
        dayFlag++;
        if (playerFlag > 2) {
            playerFlag = 1;
            for(int i=0; i<workingMap.getMapTown().size(); i++) {
                workingMap.getMapTown().get(i).setBuildFlag(true);
                if(workingMap.getMapTown().get(i).getFaction() == 1) {
                    player1.addmoney(workingMap.getMapTown().get(i).getBuildings()[0] * 500 + 500);
                    System.out.println(workingMap.getMapTown().get(i).getBuildings()[0] * 500 + 500);
                } else {
                    player2.addmoney(workingMap.getMapTown().get(i).getBuildings()[0] * 500 + 500);
                    System.out.println(workingMap.getMapTown().get(i).getBuildings()[0] * 500 + 500);
                }
            }
            for(int i=0; i < workingMap.getMapHero().size(); i++) {
                workingMap.getMapHero().get(i).setMoved(true);
            }
            player1.setHeroBuy(true);
            player2.setHeroBuy(true);
        }
        if (dayFlag >= 14) {
            dayFlag = 0;
            for(int i=0; i<workingMap.getMapTown().size(); i++) {
                for(int j=0; j<5; j++) {
                    workingMap.getMapTown().get(i).setBuyFlag(true, j);
                }
            }
        }
        winCheck();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_F2) {
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (i == KeyEvent.VK_F2) {
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (i == KeyEvent.VK_F3) {
            try {
                load("save1.out");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        } else if (i == KeyEvent.VK_ENTER) {
            turn();
        } else if ((i == KeyEvent.VK_H) && (e.isControlDown())) {
            buyHeroFlag = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        posX = e.getX()/64;
        posY = e.getY()/64;
    }

    public void winCheck() {
        int temp1 = 0;
        int temp2 = 0;
        for(int i=0; i<workingMap.getMapHero().size(); i++) {
            for(int j=0; j<5; j++) {
                if(workingMap.getMapHero().get(i).getFaction() == 1) temp1++;
                if(workingMap.getMapHero().get(i).getFaction() == 2) temp2++;
            }
        }
        for(int i=0; i<workingMap.getMapTown().size(); i++) {
            for(int j=0; j<5; j++) {
                if(workingMap.getMapTown().get(i).getFaction() == 1) temp1++;
                if(workingMap.getMapTown().get(i).getFaction() == 2) temp2++;
            }
        }
        if (temp1 == 0) {
            f.dispose();
        }
        if (temp2 == 0) {
            f.dispose();
        }
    }

    public void heroRemove() {
        int temp = 0;
//        ArrayList<Hero> heroList = workingMap.getMapHero();
//        for (Iterator iterator = heroList.iterator(); iterator.hasNext(); ) {
//            Hero next = (Hero) iterator.next();
//            if (next.getArmy()[0] == null
//                    && next.getArmy()[1] == null
//                    && next.getArmy()[2] == null
//                    && next.getArmy()[3] == null
//                    && next.getArmy()[4] == null
//                    ) {
//                iterator.remove();
//            }
//
//        }
        for(int i=0; i<workingMap.getMapHero().size(); i++) {
            for(int j=0; j<5; j++) {
                if(workingMap.getMapHero().get(i).getArmy()[j] == null) temp++;
            }
            if (temp == 5) {
                workingMap.getMapHero().remove(i);
                temp=0;
            }
            temp=0;
        }
    }

    @Override
    /**
     * Invoked when a mouse button has been released.
     *
     * @param  e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        if ((flagHeroActive != Integer.MAX_VALUE) && ((workingMap.findHero(posX, posY) == Integer.MAX_VALUE) || (workingMap.findHero(posX, posY) == flagHeroActive)) && (highMap[posX][posY] == true)) {
            workingMap.getMapHero().get(flagHeroActive).setPosX(posX);
            workingMap.getMapHero().get(flagHeroActive).setPosY(posY);
            workingMap.getMapHero().get(flagHeroActive).setMoved(false);
            if ((workingMap.findHero(posX-1, posY) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX-1, posY)).getFaction() != workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //BattleFieldWindow bfw = new BattleFieldWindow("battle", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX-1, posY)));
                Command command = new BattleCommand( workingMap,flagHeroActive,posX-1, posY);
                command.execute();
            } else if ((workingMap.findHero(posX+1, posY) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX+1, posY)).getFaction() != workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //BattleFieldWindow bfw = new BattleFieldWindow("battle", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX+1, posY)));
                Command command = new BattleCommand( workingMap,flagHeroActive,posX+1, posY);
                command.execute();
            } else if ((workingMap.findHero(posX, posY+1) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX, posY+1)).getFaction() != workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //BattleFieldWindow bfw = new BattleFieldWindow("battle", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX, posY+1)));
                Command command = new BattleCommand( workingMap,flagHeroActive,posX, posY+1);
                command.execute();
            } else if ((workingMap.findHero(posX, posY-1) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX, posY-1)).getFaction() != workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //BattleFieldWindow bfw = new BattleFieldWindow("battle", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX, posY-1)));
                Command command = new BattleCommand( workingMap,flagHeroActive,posX, posY-1);
                command.execute();
            } else if ((workingMap.findHero(posX-1, posY) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX-1, posY)).getFaction() == workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //HeroToHeroFieldWindow hth = new HeroToHeroFieldWindow("herotohero", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX-1, posY)));
                Command command = new HeroToHeroCommand( workingMap,flagHeroActive,posX-1, posY);
                command.execute();
            } else if ((workingMap.findHero(posX+1, posY) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX+1, posY)).getFaction() == workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //HeroToHeroFieldWindow hth = new HeroToHeroFieldWindow("herotohero", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX+1, posY)));
                Command command = new HeroToHeroCommand( workingMap,flagHeroActive,posX+1, posY);
                command.execute();
            } else if ((workingMap.findHero(posX, posY+1) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX, posY+1)).getFaction() == workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //HeroToHeroFieldWindow hth = new HeroToHeroFieldWindow("herotohero", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX, posY+1)));
                Command command = new HeroToHeroCommand( workingMap,flagHeroActive,posX, posY+1);
                command.execute();
            } else if ((workingMap.findHero(posX, posY-1) != Integer.MAX_VALUE) && (workingMap.getMapHero().get(workingMap.findHero(posX, posY-1)).getFaction() == workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                //HeroToHeroFieldWindow hth = new HeroToHeroFieldWindow("herotohero", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX, posY-1)));
                Command command = new HeroToHeroCommand( workingMap,flagHeroActive,posX, posY-1);
                command.execute();
            } else if ((workingMap.findTown(posX, posY) != Integer.MAX_VALUE) && (workingMap.getMapTown().get(workingMap.findTown(posX, posY)).getFaction() == workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                if (playerFlag == 1) {
                    HeroToTownFieldWindow hth = new HeroToTownFieldWindow("herototown", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapTown().get(workingMap.findTown(posX, posY)), player1);
                }
                else {
                    HeroToTownFieldWindow hth = new HeroToTownFieldWindow("herototown", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapTown().get(workingMap.findTown(posX, posY)), player2);
                }
            } else if ((workingMap.findTown(posX, posY) != Integer.MAX_VALUE) && (workingMap.getMapTown().get(workingMap.findTown(posX, posY)).getFaction() != workingMap.getMapHero().get(flagHeroActive).getFaction())) {
                workingMap.getMapTown().get(workingMap.findTown(posX, posY)).setFaction(workingMap.getMapHero().get(flagHeroActive).getFaction());
            }
            flagHeroActive = Integer.MAX_VALUE;
        } else if ((workingMap.findHero(posX, posY) != Integer.MAX_VALUE) && (playerFlag == workingMap.getMapHero().get(workingMap.findHero(posX, posY)).getFaction()) && (workingMap.getMapHero().get(workingMap.findHero(posX, posY)).isMoved() == true) && (e.getModifiers()== InputEvent.BUTTON1_MASK)) {
            flagHeroActive = workingMap.findHero(posX, posY);
        } else if (buyHeroFlag == 1) {
            if ((playerFlag == 1) && (player1.isHeroBuy() == true) && (workingMap.findTown(posX, posY) != Integer.MAX_VALUE) && (playerFlag == workingMap.getMapTown().get(workingMap.findTown(posX, posY)).getFaction()) && (workingMap.findHero(posX, posY) == Integer.MAX_VALUE)) {
                workingMap.addHero(posX, posY, 1);
            } else if ((playerFlag == 2) && (player2.isHeroBuy() == true) && (workingMap.findTown(posX, posY) != Integer.MAX_VALUE) && (playerFlag == workingMap.getMapTown().get(workingMap.findTown(posX, posY)).getFaction()) && (workingMap.findHero(posX, posY) == Integer.MAX_VALUE)) {
                workingMap.addHero(posX, posY, 2);
            }
            buyHeroFlag = 0;
        } else if ((workingMap.findHero(posX, posY) != Integer.MAX_VALUE) && (playerFlag == workingMap.getMapHero().get(workingMap.findHero(posX, posY)).getFaction()) && (e.getModifiers()== InputEvent.BUTTON3_MASK)) {
            HeroFieldWindow tf = new HeroFieldWindow("Hero", workingMap.getMapHero().get(workingMap.findHero(posX, posY)));
        } else if ((workingMap.findTown(posX, posY) != Integer.MAX_VALUE) && (playerFlag == workingMap.getMapTown().get(workingMap.findTown(posX, posY)).getFaction()) && (e.getModifiers()== InputEvent.BUTTON3_MASK)) {
            if (playerFlag == 1) {
                TownFieldWindow tf = new TownFieldWindow("Town", workingMap.getMapTown().get(workingMap.findTown(posX, posY)), player1);
            } else {
                TownFieldWindow tf = new TownFieldWindow("Town", workingMap.getMapTown().get(workingMap.findTown(posX, posY)), player2);
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

    @Override
    public void focusGained(FocusEvent e) {
        heroRemove();
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}

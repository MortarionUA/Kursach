package ip4323.hommClass;

/**
 * Created by dima_ on 24.05.2016.
 */
public class BattleCommand implements Command {
    private Map workingMap;
    private int flagHeroActive;
    private int posX;
    private int posY;

    /**
     * Instantiates a new Battle command.
     *
     * @param workingMap     the working map
     * @param flagHeroActive the flag hero active
     * @param posX           the pos x
     * @param posY           the pos y
     */
    public BattleCommand(Map workingMap, int flagHeroActive, int posX, int posY) {
        this.workingMap = workingMap;
        this.flagHeroActive = flagHeroActive;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute() {
        BattleFieldWindow bfw = new BattleFieldWindow("battle", workingMap.getMapHero().get(flagHeroActive), workingMap.getMapHero().get(workingMap.findHero(posX, posY)));
    }
}

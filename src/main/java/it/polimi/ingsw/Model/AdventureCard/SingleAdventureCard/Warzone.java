package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Warzone extends AdventureCard {

    // criteri equipaggio minore, minor potenza motrice o minor potenza di fuoco
    // come fare a contenere i vari tipi di penalità
    private final ArrayList<ArrayList<Character>> warzoneCriteria = new ArrayList<>();

    private final int daysLost;

    private final int crewLost;

    private final int goodsLost;
    private final Map<String, Integer> firstPlayersParameters = new HashMap<>();
    private final Map<String, Integer> secondPlayersParameters = new HashMap<>();
    private final Map<String, Integer> thirdPlayersParameters = new HashMap<>();
    private final ArrayList<ArrayList<String>> blastArray = new ArrayList<>();
    private final ArrayList<ArrayList<String>> currentBlastArray = new ArrayList<>();
    private boolean firstControl;
    private boolean secondControl;
    private boolean thirdControl;
    private boolean wait;


    public Warzone(int id, int level, boolean turnedUp, boolean completed,
                   ArrayList<ArrayList<Character>> warzoneCriteria, int daysLost, int crewLost, int goodsLost,
                   ArrayList<ArrayList<String>> blastArray) {
        super(id, level, turnedUp, completed);
        this.warzoneCriteria.addAll(warzoneCriteria);
        this.daysLost = daysLost;
        this.crewLost = crewLost;
        this.goodsLost = goodsLost;
        this.blastArray.addAll(blastArray);
        this.currentBlastArray.addAll(blastArray);
        setHasActionOrder(true);
        firstControl = false;
        secondControl = false;
        thirdControl = false;
        wait = false;
    }

    public ArrayList<ArrayList<Character>> getWarzoneCriteria() {
        return warzoneCriteria;
    }

//	public ArrayList<ArrayList<String>> getBlastArray() {
//		return this.blastArray;
//	}

    public ArrayList<ArrayList<String>> getCurrentBlastArray() {
        return this.currentBlastArray;
    }


    public int getDaysLost() { // la penalità dei meteoriti corrisponde all'intero 1 in warzonePenalitiesTypes
        return this.daysLost;
    }

    public int getCrewLost() { // la penalità dei meteoriti corrisponde all'intero 2 in warzonePenalitiesTypes
        return this.crewLost;
    }

    public int getGoodsLost() { // la penalità dei meteoriti corrisponde all'intero 3 in warzonePenalitiesTypes
        return this.goodsLost;
    }

    public boolean getFirstControl() {
        return firstControl;
    }

    public void setFirstControl(boolean firstControl) {
        this.firstControl = firstControl;
    }

    public boolean getSecondControl() {
        return secondControl;
    }

    public void setSecondControl(boolean secondControl) {
        this.secondControl = secondControl;
    }

    public boolean getThirdControl() {
        return thirdControl;
    }

    public void setThirdControl(boolean thirdControl) {
        this.thirdControl = thirdControl;
    }

    public boolean getWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public synchronized void setFirstPlayersParameters(Player player, int parameter) {
        firstPlayersParameters.put(player.getUsername(), parameter);
    }

    public synchronized void setSecondPlayersParameters(Player player, int parameter) {
        secondPlayersParameters.put(player.getUsername(), parameter);
    }

    public synchronized void setThirdPlayersParameters(Player player, int parameter) {
        thirdPlayersParameters.put(player.getUsername(), parameter);
    }

    public Map<String, Integer> getFirstPlayersParameters() {
        return firstPlayersParameters;
    }

    public Map<String, Integer> getSecondPlayersParameters() {
        return secondPlayersParameters;
    }

    public Map<String, Integer> getThirdPlayersParameters() {
        return thirdPlayersParameters;
    }

    public String getLoser(Map<String, Integer> players) {
        int tempMin = 99999;
        String tempLoser = null;
        for (String player : players.keySet()) {
            if (tempMin > players.get(player)) {
                tempMin = players.get(player);
                tempLoser = player;
            }
        }
        return tempLoser;
    }

    @Override
    public CardType getCardType() {
        return CardType.WARZONE;
    }


    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> playerActionOrder = new ArrayList<>(playersInOrder);
        return playerActionOrder;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) throws IOException {
        visitor.visit(this, msg);
    }

    @Override
    public String getEffectDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: Warzone ⚔️\n");
        sb.append("• You've entered a dangerous warzone! ⚔️\n");
        sb.append("• The warzone poses several threats to your ship and crew:\n");

        // Dettagli penalità per equipaggio, potenza motrice, fuoco, e merci
        if (crewLost > 0) {
            sb.append("   - Crew Lost: " + crewLost + " members.\n");
        }
        if (goodsLost > 0) {
            sb.append("   - Goods Lost: " + goodsLost + " items.\n");
        }
        if (daysLost > 0) {
            sb.append("   - Flight Days Lost: " + daysLost + " days.\n");
        }

        sb.append("• You must pass the warzone's criteria to avoid these penalties!\n");
        sb.append("• Criteria: \n");

        // Mostra i criteri di guerra (minore equipaggio, motore, potenza fuoco)
        for (ArrayList<Character> key : warzoneCriteria) {
            sb.append("   - " + key.get(0) + ": " + key.get(1) + "\n");

        }

        sb.append("• Be cautious as you navigate through this hostile environment!\n");

        return sb.toString();
    }

}
